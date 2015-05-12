package com.swifta.mats.web;

import java.util.logging.Logger;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.utils.LoginService;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class Login extends VerticalLayout implements View {
	private static final Logger logger = Logger
			.getLogger(Login.class.getName());
	private static final long serialVersionUID = 4197831781197091062L;
	public static final String LOGIN = "login";

	private TextField tfUsername;
	private PasswordField tfPassword;
	private Button btnLogin;
	private Label lPrompt;
	private Label lWelcome;
	private final FieldGroup fg;
	private final PropertysetItem item;
	private final ObjectProperty<String> pUsername;
	private final ObjectProperty<String> pPassword;
	private String curuname = null;

	private void loginPageContent() {

		setSizeFull();
		setStyleName("c_login_page");
		Responsive.makeResponsive(this);

		Panel panelLogin = new Panel();
		panelLogin.setSizeUndefined();
		panelLogin.setStyleName("panel_login");

		VerticalLayout cLogo = new VerticalLayout();
		cLogo.setSizeUndefined();
		cLogo.setStyleName("c_logo_and_slogan");
		ThemeResource rLogo = new ThemeResource("img/logo.png");
		Embedded emb = new Embedded(null, rLogo);
		emb.setDescription("MATS Logo");
		cLogo.addComponent(emb);
		cLogo.setComponentAlignment(emb, Alignment.MIDDLE_CENTER);
		VerticalLayout cLoginLabel = new VerticalLayout();
		cLoginLabel.setSizeUndefined();

		lWelcome = new Label("Welcome to MATS WEB Portal.");
		lWelcome.setStyleName("label_welcome");

		lPrompt = new Label("Please enter your credentials to login.");
		lPrompt.setStyleName("label_login_prompt");

		cLoginLabel.addComponent(lWelcome);
		cLoginLabel.setComponentAlignment(lWelcome, Alignment.MIDDLE_CENTER);
		cLoginLabel.addComponent(lPrompt);

		VerticalLayout cLogoPanel = new VerticalLayout();
		cLogoPanel.setSizeUndefined();
		cLogoPanel.setStyleName("c_logo_panel");
		// cLogoPanel .setSizeFull();
		cLogoPanel.setMargin(true);
		cLogoPanel.setSpacing(true);
		addComponent(cLogoPanel);
		setComponentAlignment(cLogoPanel, Alignment.MIDDLE_CENTER);
		cLogoPanel.addComponent(cLogo);
		cLogoPanel.setComponentAlignment(cLogo, Alignment.MIDDLE_CENTER);

		cLogoPanel.addComponent(cLoginLabel);
		cLogoPanel.setComponentAlignment(cLoginLabel, Alignment.MIDDLE_CENTER);

		cLogoPanel.addComponent(panelLogin);
		cLogoPanel.setComponentAlignment(panelLogin, Alignment.MIDDLE_CENTER);

		Responsive.makeResponsive(cLogoPanel);

		Label lbVersion = new Label("MATS Platform v.1.0");
		Label lbCopyright = new Label(
				"Copyright @ 2014-2015 Swifta Systems & Services Ltd.");
		lbVersion.setStyleName("label_version");
		lbCopyright.setStyleName("label_copyright");

		HorizontalLayout copyrightContainer = new HorizontalLayout();
		copyrightContainer.setSizeUndefined();
		copyrightContainer.addComponent(lbCopyright);

		HorizontalLayout versionContainer = new HorizontalLayout();
		versionContainer.setSizeUndefined();
		versionContainer.addComponent(lbVersion);

		// Responsive.makeResponsive(panelLoginContent);

		cLogoPanel.addComponent(versionContainer);
		cLogoPanel.setComponentAlignment(versionContainer,
				Alignment.BOTTOM_CENTER);

		cLogoPanel.addComponent(copyrightContainer);
		cLogoPanel.setComponentAlignment(copyrightContainer,
				Alignment.BOTTOM_CENTER);

		VerticalLayout cLoginFields = new VerticalLayout();
		cLoginFields.setStyleName("c_login_fields");

		cLoginFields.setMargin(true);
		cLoginFields.setSpacing(true);

		tfUsername = new TextField();
		tfUsername.setInputPrompt("Username");
		tfUsername.setDescription("Username");
		tfUsername.setStyleName("username_text_field");
		tfUsername.addValidator(new LoginValidator());

		tfUsername.setValidationVisible(false);
		tfUsername.setNullRepresentation("");

		tfPassword = new PasswordField();
		tfPassword.setInputPrompt("Username");
		tfPassword.setDescription("Password");
		tfPassword.addValidator(new LoginValidator());
		tfPassword.setValidationVisible(false);
		tfPassword.setNullRepresentation("");

		btnLogin = new Button("Login");
		btnLogin.setClickShortcut(KeyCode.ENTER);
		btnLogin.setStyleName(Reindeer.BUTTON_DEFAULT);
		cLoginFields.addComponent(tfUsername);
		cLoginFields.addComponent(tfPassword);
		cLoginFields.addComponent(btnLogin);

		panelLogin.setContent(cLoginFields);
		cLoginFields.setComponentAlignment(btnLogin, Alignment.BOTTOM_RIGHT);

		btnLogin.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6400633218319264865L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					fg.commit();
				} catch (Exception e) {
					return;
				}

				logger.info("---------------Before validating the username and password"
						+ tfUsername.getValue());
				curuname = tfUsername.getValue();
				// if (validCredentials()) {
				try {

					String status = LoginService.webauthenticate(
							tfUsername.getValue(), tfPassword.getValue());

					if (status == null) {

						lPrompt.setValue("Sorry, can not access system right now. Contact the administrator.");
						tfUsername.focus();
						lPrompt.setStyleName("label_login_prompt_err");
						lWelcome.setValue("Oops...");
						return;
					}

					if (status.equals("true")) {
						logger.info("---------------Validation successful");
						UI.getCurrent().getSession()
								.setAttribute("user", tfUsername.getValue());
						logger.info("---------------After getting session in Login");
						VaadinSession.getCurrent().getSession()
								.setMaxInactiveInterval(1800);
						// mx//UI.getCurrent().getNavigator()
						// mx//.navigateTo(WorkSpace.WORK_SPACE);

						Initializer initts = new Initializer();
						UI.getCurrent().getNavigator()
								.addView(Main.WS, new Main(initts.getTS()));
						UI.getCurrent().getNavigator().navigateTo(Main.WS);

						logger.info("---------------after getting navigator to workspace:::Login");

					} else {

						if (status.equals("false")) {
							lPrompt.setValue("Invalid Username or Password.");

						} else if (LoginService.webauthenticate(
								tfUsername.getValue(), tfPassword.getValue())
								.equals("locked")) {
							lPrompt.setContentMode(ContentMode.HTML);
							lPrompt.setValue("<div style='text-align: center; width: 100%;'>User account is locked for user: </div>"
									+ "("
									+ curuname
									+ ")"
									+ " cannot login until the account is unlocked");
						} else {

							lPrompt.setValue(status);
						}
						tfUsername.focus();
						lPrompt.setStyleName("label_login_prompt_err");
						lWelcome.setValue("Oops...");
						// lWelcome.setStyleName("label_welcome_err");
						// userLogin.setValue("Invalid Credentials");
						// userLogin.setStyleName("errorlogin");
						logger.info("---------------The authentication FAILED!!!!!!!!!!!!!!");

					}

					// Notification.show(status,
					// Notification.Type.ERROR_MESSAGE);
				} catch (AxisFault e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					lPrompt.setValue("Error in connection!");
					tfUsername.focus();
					lPrompt.setStyleName("label_login_prompt_err");
					lWelcome.setValue("Oops...");
					tfUsername.setValue(null);
					tfPassword.setValue(null);

				}
				// }
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public void enter(ViewChangeEvent event) {
		tfUsername.setValue(null);
		tfPassword.setValue(null);

	}

	public Login() {
		setMargin(true);
		loginPageContent();
		item = new PropertysetItem();
		pUsername = new ObjectProperty<String>("");
		pPassword = new ObjectProperty<String>("");
		item.addItemProperty("Username", pUsername);
		item.addItemProperty("Password", pPassword);
		fg = new FieldGroup(item);
		fg.bind(tfUsername, "Username");
		fg.bind(tfPassword, "Password");

	}

	private class LoginValidator implements Validator {

		/**
		 * 
		 */
		private static final long serialVersionUID = -784400270941792708L;

		@Override
		public void validate(Object value) throws InvalidValueException {
			pUsername.setValue(tfUsername.getValue());
			pPassword.setValue(tfPassword.getValue());
			tfUsername.setValidationVisible(false);
			tfPassword.setValidationVisible(false);

			if (value.toString().trim().equals("")) {
				tfUsername.setValidationVisible(true);
				tfPassword.setValidationVisible(true);
				throw new InvalidValueException("Required field.");

			}

		}

	}

}
