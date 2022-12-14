package de.bytewright.recipeLibrary.webserver.frontend.components.template;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.lambda.components.ComponentFactory;

import de.bytewright.contestManager.backend.persistence.entities.PageEntity;
import de.bytewright.contestManager.backend.security.GoContestManagerSession;
import de.bytewright.contestManager.backend.services.PageService;

public class HeaderPanel extends Panel {
  @SpringBean
  private PageService pageService;

  public HeaderPanel(String id) {
    super(id);
    add(new LinkListView(pageService.getAllPagesForHeader(GoContestManagerSession.get()), "headerLinkList"));
  }

  private static class LinkListView extends ListView<PageEntity> {
    public LinkListView(List<PageEntity> pages, String contentId) {
      super(contentId, pages);
    }

    @Override
    protected void populateItem(ListItem<PageEntity> item) {
      PageEntity pageEntity = item.getModelObject();
      Link<Void> href = ComponentFactory.link("href", components -> setResponsePage(pageEntity.getPageClass()));
      if (StringUtils.isNotBlank(pageEntity.getAnchorName())) {
        href.setBody(LambdaModel.of(pageEntity::getAnchorName));
      }
      item.add(href);
    }
  }
}
