package es.gorka.edu.components;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import es.gorka.edu.dto.SnippetDTO;
import es.gorka.edu.service.SnippetService;

@MountPath("board.html")
public class BoardPage extends AbstractBoardPage implements CloseAble, Xss {

	@SpringBean(name = "snippetService")
	SnippetService service;

	private static final Logger logger = LogManager.getLogger(BoardPage.class.getName());

	public BoardPage() {
		add(new Label("title", getString("title")));
		add(new BookmarkablePageLink("mainPageLink", MainPage.class));
		Link<String> link = new Link<String>("closeApp") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				close();
			}

		};
		add(link);
		addForm();
		addListView();
		addFeedBackPanel();
	}

	private void addFeedBackPanel() {
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackMessage");
		add(feedbackPanel);
	}

	private void addListView() {
		List<SnippetDTO> list = service.listAll();
		for (SnippetDTO object : list) {
			logger.debug(object.toDebug());
		}
		addListViewFromList(list);
	}


	private void addForm() {
		Form<SnippetDTO> form = new Form<SnippetDTO>("formBoard",
				new CompoundPropertyModel<SnippetDTO>(service.newEntity())) {
			@Override
			protected void onSubmit() {
				super.onSubmit();
				if (validModel(getModelObject())) {
					service.insertNewEntityDto(getModelObject());
					logger.debug("Snippet insertado");
					setResponsePage(BoardPage.class);
				}
			}


		};
		form.add(new Label("labelForm", getString("labelForm")));
		form.add(new TextField<String>("userName"));
		form.add(new TextArea<String>("text"));
		add(form);

	}

}
