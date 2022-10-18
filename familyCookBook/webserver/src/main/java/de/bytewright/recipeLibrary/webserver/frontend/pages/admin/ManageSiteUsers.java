package de.bytewright.recipeLibrary.webserver.frontend.pages.admin;

import org.apache.wicket.Component;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import de.bytewright.recipeLibrary.webserver.frontend.components.admin.AdminUserPanel;
import de.bytewright.recipeLibrary.webserver.frontend.components.template.PageTemplate;

@ShiroSecurityConstraint(constraint = ShiroConstraint.HasRole, value = "admin", loginMessage = "Login message", unauthorizedMessage = "Not authorized message")
public class ManageSiteUsers extends PageTemplate {

  public static String getMountPath() {
    return "/admin/users/manage";
  }

  @Override
  protected Component getContent(String contentId, PageParameters parameters) {
    return new AdminUserPanel(contentId);
  }

  @Override
  protected Component getContent(String contentId) {
    return getContent(contentId, null);
  }
}
