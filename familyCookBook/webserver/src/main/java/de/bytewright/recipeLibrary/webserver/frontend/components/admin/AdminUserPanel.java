package de.bytewright.recipeLibrary.webserver.frontend.components.admin;

import java.util.List;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.bytewright.recipeLibrary.api.generated.UserInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bytewright.recipeLibrary.webserver.frontend.pages.css.Marker;
import de.bytewright.recipeLibrary.webserver.services.AppUserService;

public class AdminUserPanel extends Panel {
  private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserPanel.class);

  @SpringBean
  private AppUserService userService;

  public AdminUserPanel(String contentId) {
    super(contentId);
    List<UserInformation> allUsers = userService.getAllUsers();
    add(new UsersListView(allUsers, "userList"));
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    PackageResourceReference cssFile = new PackageResourceReference(Marker.class, "style.css");
    response.render(CssHeaderItem.forReference(cssFile));
    cssFile = new PackageResourceReference(Marker.class, "div-as-table.css");
    response.render(CssHeaderItem.forReference(cssFile));
  }

  private static class UsersListView extends ListView<UserInformation> {
    public UsersListView(List<UserInformation> allUsers, String contentId) {
      super(contentId, allUsers);
    }

    @Override
    protected void populateItem(ListItem<UserInformation> item) {
      UserInformation user = item.getModelObject();
      var nameLabel = new Label("userName", LambdaModel.of(user::getName));
      /*
      var permsLabel = new Label("userPerms",
          LambdaModel.of(() -> user.getPermissions().stream().map(Permission::getPerm).collect(Collectors.joining())));
          */
      Form<String> selection = new Form<>("userSelection", LambdaModel.of(user::getId)) {
        @Override
        protected void onSubmit() {
          LOGGER.info("Form submitted. user: {}", getModelObject());
        }
      };
      item.add(nameLabel);
      //item.add(permsLabel);
      item.add(selection);
    }
  }
}
