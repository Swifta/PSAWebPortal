package com.swifta.mats_web_portal;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;


public class Login extends VerticalLayout implements View {
	/**
	 * 
	 */
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
	
	private void loginPageContent(){
		
		setSizeFull();
		setStyleName("c_login_page");
		Panel panelLogin = new Panel();
		panelLogin.setSizeUndefined();
		panelLogin.setStyleName("panel_login");
		VerticalLayout panelLoginContent = new VerticalLayout();
		panelLoginContent.setStyleName("panel_login_content");
		//VerticalLayout hLayout = new VerticalLayout();
		VerticalLayout logoContainer = new VerticalLayout();
		logoContainer.setSizeUndefined();
		logoContainer.setStyleName("logo_and_slogan_container");
		//logoAndSloganContainer.setMargin(true);
		ThemeResource rLogo = new ThemeResource("img/logo.jpg");
		//System.out.println(rLogo.toString());
		Embedded emb = new Embedded(null, rLogo);
		emb.setWidth("100px");
		emb.setHeight("100px");
		emb.setDescription("MATS Logo");
		logoContainer.addComponent(emb);
		logoContainer.setComponentAlignment(emb, Alignment.MIDDLE_CENTER);
		VerticalLayout loginLabelContainer = new VerticalLayout();
		loginLabelContainer.setSizeUndefined();
		
		lWelcome = new Label("Welcome to MATS WEB Portal.");
		lWelcome.setStyleName("label_welcome");
		
		lPrompt = new Label("Please enter your credentials to login.");
		lPrompt.setStyleName("label_login_prompt");
		
		loginLabelContainer.addComponent(lWelcome);
		loginLabelContainer.addComponent(lPrompt);
		
		
		//panelLoginContent.addComponent(logoAndSloganContainer);
		//panelLoginContent.setComponentAlignment(logoAndSloganContainer, Alignment.MIDDLE_CENTER);
		VerticalLayout panelLoginAndLogoAndSloganContainer = new VerticalLayout();
		panelLoginAndLogoAndSloganContainer.setSizeUndefined();
		panelLoginAndLogoAndSloganContainer.setMargin(true);
		panelLoginAndLogoAndSloganContainer.setSpacing(true);
		addComponent(panelLoginAndLogoAndSloganContainer);
		setComponentAlignment(panelLoginAndLogoAndSloganContainer, Alignment.MIDDLE_CENTER);
		panelLoginAndLogoAndSloganContainer.addComponent(logoContainer);
		panelLoginAndLogoAndSloganContainer.setComponentAlignment(logoContainer, Alignment.MIDDLE_CENTER);
		
		panelLoginAndLogoAndSloganContainer.addComponent(loginLabelContainer);
		panelLoginAndLogoAndSloganContainer.setComponentAlignment(loginLabelContainer, Alignment.MIDDLE_CENTER);
		
		
		panelLoginAndLogoAndSloganContainer.addComponent(panelLogin);
		panelLoginAndLogoAndSloganContainer.setComponentAlignment(panelLogin, Alignment.MIDDLE_CENTER);
		//VerticalLayout versionAndCopyrightContainer = new VerticalLayout();
		//versionAndCopyrightContainer.setSizeFull();
		
		Label lbVersion = new Label("MATS Platform v.1.0");
		Label lbCopyright = new Label("Copyright © 2014-2015 Swifta Systems & Services Ltd.");
		lbVersion.setStyleName("label_version");
		lbCopyright.setStyleName("label_copyright");
		
		
		//versionAndCopyrightContainer.addComponent(lbVersion);
		//versionAndCopyrightContainer.addComponent(lbCopyright);
		
		HorizontalLayout copyrightContainer = new HorizontalLayout();
		copyrightContainer.setSizeUndefined();
		copyrightContainer.addComponent(lbCopyright);
		//copyrightContainer.setComponentAlignment(lbCopyright, Alignment.MIDDLE_CENTER);
		
		HorizontalLayout versionContainer = new HorizontalLayout();
		versionContainer.setSizeUndefined();
		versionContainer.addComponent(lbVersion);
		//versionContainer.setComponentAlignment(lbVersion, Alignment.MIDDLE_CENTER);
		
		//versionAndCopyrightContainer.addComponent(versionContainer);
		//versionAndCopyrightContainer.setComponentAlignment(versionContainer, Alignment.MIDDLE_CENTER);
		
		//versionAndCopyrightContainer.addComponent(copyrightContainer);
		//versionAndCopyrightContainer.setComponentAlignment(copyrightContainer, Alignment.MIDDLE_CENTER);
		
		//panelLoginAndLogoAndSloganContainer.addComponent(versionAndCopyrightContainer);
		panelLoginAndLogoAndSloganContainer.addComponent(versionContainer);
		panelLoginAndLogoAndSloganContainer.setComponentAlignment(versionContainer, Alignment.MIDDLE_CENTER);
		
		panelLoginAndLogoAndSloganContainer.addComponent(copyrightContainer);
		panelLoginAndLogoAndSloganContainer.setComponentAlignment(copyrightContainer, Alignment.MIDDLE_CENTER);
		//panelLoginAndLogoAndSloganContainer.setComponentAlignment(versionAndCopyrightContainer, Alignment.MIDDLE_CENTER);
		
		/*panelLoginAndLogoAndSloganContainer.addComponent(lPrompt);
		panelLoginAndLogoAndSloganContainer.setComponentAlignment(lPrompt, Alignment.MIDDLE_CENTER);*/
		
		//make logoAndSloganContainer to be exactly the same width as paneLogin
		//System.out.println(panelLoginContent.getWidth());
		//System.out.println(panelLogin.getWidth());
		//System.out.println(panelLoginContent.getWidthUnits());
		//logoAndSloganContainer.setWidth('"'+panelLogin.getWidth() + ""+panelLogin.getWidthUnits());
		
		
		
		/*hLayout.addComponent(panelLogin);
		hLayout.setSizeFull();
		hLayout.setComponentAlignment(panelLogin, Alignment.MIDDLE_CENTER);
		hLayout.setStyleName("panel_container");*/
		
		panelLoginContent.setSizeUndefined();
		panelLoginContent.setMargin(true);
		panelLoginContent.setSpacing(true);
		//panelLogin.setStyleName("login_panel");
		tfUsername = new TextField();
		tfUsername.setInputPrompt("Username");
		tfUsername.setDescription("Username");
		tfUsername.setStyleName("username_text_field");
		tfUsername.addValidator(new LoginValidator());
		//tfUsername.validate();
		//tfUsername.setImmediate(true);
		//tfUsername.setRequired(true);
		tfUsername.setValidationVisible(false);
		tfUsername.setNullRepresentation("");
		
		tfPassword = new PasswordField();
		tfPassword.setInputPrompt("Username");
		tfPassword.setDescription("Password");
		tfPassword.addValidator(new LoginValidator());
		tfPassword.setValidationVisible(false);
		tfPassword.setNullRepresentation("");
		//tfPassword.setRequired(true);
		//tfPassword.addValidator(new);
		
		
		
		btnLogin = new Button("Login") ;
		panelLoginContent.addComponent(tfUsername);
		panelLoginContent.addComponent(tfPassword);
		panelLoginContent.addComponent(btnLogin);
		panelLogin.setContent(panelLoginContent);
		panelLoginContent.setComponentAlignment(btnLogin, Alignment.BOTTOM_RIGHT);
		//hLayout.setComponentAlignment(btnLogin, Alignment.);
		//panelLogin.
		
		//layout.addComponent(button);
		/*layout.setStyleName("hLayout_container");*/
		
		setSizeFull();
		//layout.setSpacing(true);
		/*layout.setComponentAlignment(hLayout, Alignment.MIDDLE_CENTER);*/
		
		btnLogin.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 6400633218319264865L;

			@Override
			public void buttonClick(ClickEvent event) {
				try{
					fg.commit();
				}catch(Exception e){
					//Notification.show("Invalid value");
					return;
				}
				//if(validCredentials()){
					UI.getCurrent().getSession().setAttribute("user", tfUsername.getValue());
					UI.getCurrent().getNavigator().navigateTo(WorkAreax.WORK_AREA);
			//	}
			
				
				
			}
		});
		
		
	}

	private boolean validCredentials(){
		//tfUsername.addValidator(new Validator.());
		String username = tfUsername.getValue();
		System.out.println(username);
		String password = tfPassword.getValue();
		String dbUser = "admin";
		String dbPass = "sameboy";
		String db = "mats";
		String table = "users";
		String dbDriver = "com.mysql.jdbc.Driver";
		String dbUrl = "jdbc:mysql://localhost:3306/"+db;
		try{
			
			
			JDBCConnectionPool pool = new SimpleJDBCConnectionPool(dbDriver, dbUrl, dbUser, dbPass);
			TableQuery tableQ = new TableQuery(table, pool);
			//FreeformQuery ffq = new FreeformQuery(table, pool);
			//SQLContainer tableFFQ = new SQLContainer(ffq);
			
			SQLContainer container = new SQLContainer(tableQ);
		
			//Filter filterUser = new And(new SimpleStringFilter("Username", username, false, false), new SimpleStringFilter("Password", password, false, false));
			
			//Filter filterUser = new SimpleStringFilter("", username, false, true);
			Filter filter = new And(new Compare.Equal("Username", username), new Compare.Equal("Password", password));
			
			///container.removeAllContainerFilters();
			container.addContainerFilter(filter);
			if(container.size() != 1){
				lPrompt.setValue("Invalid Username or Password.");
				tfUsername.focus();
				lPrompt.setStyleName("label_login_prompt_err");
				lWelcome.setValue("Oops..");
				lWelcome.setStyleName("label_welcome_err");
				
				return false;
			}
				return true;
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
			
	}
	
	public Login(){
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
	
	private class LoginValidator implements Validator{
		
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
				
				if(value.toString().trim().equals("")){
					tfUsername.setValidationVisible(true);
					tfPassword.setValidationVisible(true);
					throw new InvalidValueException("Required field.");
					
				}
				
		}
		
	}
	
	
}





