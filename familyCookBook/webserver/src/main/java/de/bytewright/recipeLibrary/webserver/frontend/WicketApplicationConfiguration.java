package de.bytewright.recipeLibrary.webserver.frontend;

import java.time.Instant;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.resource.loader.BundleStringResourceLoader;
import org.apache.wicket.settings.ResourceSettings;
import org.apache.wicket.settings.SecuritySettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.authz.ShiroUnauthorizedComponentListener;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.starter.app.WicketBootSecuredWebApplication;

import de.bytewright.recipeLibrary.webserver.frontend.pages.LoginPage;
import de.bytewright.recipeLibrary.webserver.frontend.pages.PageMountRegistry;
import de.bytewright.recipeLibrary.webserver.frontend.pages.admin.SecuredPage;
import de.bytewright.recipeLibrary.webserver.security.ApplicationSession;

@ApplicationInitExtension
public class WicketApplicationConfiguration extends WicketBootSecuredWebApplication {
  private static final Logger LOGGER = LoggerFactory.getLogger(WicketApplicationConfiguration.class);
  public static final String BUNDLE_NAME = "de.bytewright.recipeLibrary.WicketApplicationGCM";

  @Override
  public Session newSession(Request request, Response response) {
    /* class set by org.bytewright.backend.security.ShiroContextConfiguration.MyAuthenticatedWebSessionConfig.getAuthenticatedWebSessionClass */
    ApplicationSession session = (ApplicationSession) super.newSession(request, response);
    Instant now = Instant.now();
    session.setCreationInstant(now);
    session.setUserDbId(-1);
    return session;
  }

  @Override
  protected void init() {
    super.init();

    LOGGER.info("Init of WicketApplication, context is: {}", getApplicationContext());

    // Enable Shiro security
    AnnotationsShiroAuthorizationStrategy authz = new AnnotationsShiroAuthorizationStrategy();
    SecuritySettings securitySettings = getSecuritySettings();
    securitySettings.setAuthorizationStrategy(authz);
    ShiroUnauthorizedComponentListener listener =
        new ShiroUnauthorizedComponentListener(LoginPage.class, AccessDeniedPage.class, authz);
    securitySettings.setUnauthorizedComponentInstantiationListener(listener);

    PageMountRegistry.getMountables()
        .stream()
        .peek(mountable -> LOGGER.info("registering mount: {}", mountable))
        .forEach(mountable -> mountPage(mountable.getMountPath(), mountable.getPageClass()));
    mountPage("secure", SecuredPage.class);

    ResourceSettings resourceSettings = getResourceSettings();
    BundleStringResourceLoader bundleStringResourceLoader = new BundleStringResourceLoader(BUNDLE_NAME);
    LOGGER.info("Loading translations from bundle {}", BUNDLE_NAME);
    String testKeyMustBePresent =
        bundleStringResourceLoader.loadStringResource(String.class, "testKeyMustBePresent", null, null, null);
    if (testKeyMustBePresent == null) {
      throw new IllegalStateException("Failed to access Message keys at " + BUNDLE_NAME);
    }
    resourceSettings.getStringResourceLoaders().add(bundleStringResourceLoader);
  }
}
