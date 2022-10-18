package de.bytewright.recipeLibrary.webserver.frontend.pages;

import java.util.List;
import java.util.Optional;

import org.apache.wicket.Component;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import de.bytewright.recipeLibrary.webserver.frontend.components.home.HomePanel;
import de.bytewright.recipeLibrary.webserver.frontend.components.template.PageTemplate;
import de.bytewright.recipeLibrary.webserver.security.ApplicationSession;

@WicketHomePage
public class HomePage extends PageTemplate {
  private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);
  private static final long serialVersionUID = 1L;

  public static String getMountPath() {
    return "/home";
  }

  @Override
  protected Component getContent(String contentId, PageParameters parameters) {
    return new HomePanel(CONTENT_ID);
  }

  @Override
  protected Component getContent(String contentId) {
    return getContent(contentId, null);
  }
}
