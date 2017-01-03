package es.gorka.edu.components;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@AuthorizeInstantiation("SIGNED_IN")
@AuthorizeAction(action = "ENABLE", roles = { "ADMIN", "USER" })
public class BaseUserPage extends WebPage {

	public BaseUserPage() {
		super();
	}

	public BaseUserPage(IModel<?> model) {
		super(model);
	}

	public BaseUserPage(PageParameters parameters) {
		super(parameters);
	}

	protected void fillUserForm(Form form) {
		form.add(new Label("userLabel", getString("username")));
		form.add(new Label("passwordLabel", getString("password")));
		form.add(new RequiredTextField("user"));
		form.add(new RequiredTextField("password"));

		add(new FeedbackPanel("feedbackMessage"));
	}

}