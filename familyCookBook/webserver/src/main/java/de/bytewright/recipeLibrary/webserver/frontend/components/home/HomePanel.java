package de.bytewright.recipeLibrary.webserver.frontend.components.home;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bytewright.recipeLibrary.webserver.frontend.pages.css.Marker;
import de.bytewright.recipeLibrary.webserver.services.PropertiesService;

public class HomePanel extends Panel {
  private static final Logger LOGGER = LoggerFactory.getLogger(HomePanel.class);
  private static final long serialVersionUID = 1L;
  @SpringBean
  private PropertiesService propertiesService;

  public HomePanel(String contentId) {
    super(contentId, null);
    add(new Label("AppVersion", propertiesService.getAppVersion()));
    add(new Label("WicketVersion", propertiesService.getWicketVersion()));
    add(new Label("SpringVersion", propertiesService.getSpringVersion()));
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    PackageResourceReference cssFile = new PackageResourceReference(Marker.class, "style.css");
    response.render(CssHeaderItem.forReference(cssFile));
    cssFile = new PackageResourceReference(Marker.class, "div-as-table.css");
    response.render(CssHeaderItem.forReference(cssFile));
  }

}
