package de.bytewright.recipeLibrary.webserver.frontend.pages;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.shiro.component.LoginPanel;

import com.giffing.wicket.spring.boot.context.scan.WicketSignInPage;

import de.bytewright.recipeLibrary.webserver.frontend.components.template.PageTemplate;
import de.bytewright.recipeLibrary.webserver.frontend.pages.css.Marker;

@WicketSignInPage
public class LoginPage extends PageTemplate {

  public static String getMountPath() {
    return "/login";
  }

  @Override
  protected Component getContent(String contentId) {
    return new LoginPanel(contentId, true);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    PackageResourceReference cssFile = new PackageResourceReference(Marker.class, "style.css");
    CssHeaderItem cssItem = CssHeaderItem.forReference(cssFile);
    response.render(cssItem);
  }

  @Override
  protected List<ResourceReference> getHeaderRenderContent(IHeaderResponse response) {
    return List.of(new PackageResourceReference(Marker.class, "style.css"));
  }

  @Override
  protected Component getContent(String contentId, PageParameters parameters) {
    return getContent(contentId);
  }
}
