package de.bytewright.recipeLibrary.webserver.frontend.pages;

import java.util.List;

import de.bytewright.recipeLibrary.webserver.frontend.pages.admin.ManageSiteUsers;

public class PageMountRegistry {
  private static final List<Mountable> mountables = List.of(
      new Mountable(HomePage.getMountPath(), "Home", HomePage.class, "*:*", true),
      new Mountable(LoginPage.getMountPath(), "Login", LoginPage.class, "*:*", true),
      new Mountable(ManageSiteUsers.getMountPath(), "admin", ManageSiteUsers.class, "siteusers:manage", true));

  public static List<Mountable> getMountables() {
    return mountables;
  }
}
