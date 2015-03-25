package com.swifta.mats.web.accountprofile;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AccountProfileModule {
	private HorizontalLayout udc;

	public AccountProfileModule() {

	}

	public HorizontalLayout getProfileContainer() {

		Panel p1 = new Panel();
		Panel p2 = new Panel();

		VerticalLayout addUserContainer = new VerticalLayout();
		addUserContainer.setWidthUndefined();
		addUserContainer.setWidthUndefined();
		addUserContainer.setMargin(new MarginInfo(true, true, true, true));
		FormLayout addUserForm = new FormLayout();
		addUserForm.setSizeUndefined();
		addUserForm.setSpacing(true);
		addUserForm.setMargin(true);
		addUserForm.setStyleName("frm_user_prof");

		Embedded emb = new Embedded(null, new ThemeResource(
				"img/add_user_small.png"));
		emb.setDescription("Add new User.");
		emb.setStyleName("add_user_img");

		VerticalLayout addUserHeader = new VerticalLayout();
		addUserHeader.setWidthUndefined();
		addUserHeader.setHeightUndefined();
		// addUserHeader.setMargin(new MarginInfo(true, true, true, true));
		addUserHeader.setSpacing(true);

		// addUserHeader.addComponent(emb);
		// addUserHeader.setComponentAlignment(emb, Alignment.TOP_RIGHT);

		addUserForm.addComponent(addUserHeader);
		Label lbAddUser = new Label("Add New User...");
		lbAddUser.setStyleName("label_user_profile");
		lbAddUser.setSizeUndefined();

		TextField tfFName = new TextField();
		tfFName.setCaption("First Name");

		TextField tfLName = new TextField();
		tfLName.setCaption("Last Name");

		OptionGroup optSex = new OptionGroup("Sex");
		optSex.addItem("Female");
		optSex.addItem("Male");

		PopupDateField doc = new PopupDateField();
		doc.setCaption("Account Date of Creation");

		ComboBox comboAccountType = new ComboBox();
		comboAccountType.setCaption("Account Type");

		comboAccountType.addItem("Super User");
		comboAccountType.addItem("Finance Controller");
		comboAccountType.addItem("Transaction Manager");

		TextField tfUName = new TextField();
		tfUName.setCaption("Username");

		PasswordField tfPass = new PasswordField();
		tfPass.setCaption("Default Password");

		PasswordField tfRePass = new PasswordField();
		tfRePass.setCaption("Re-enter Default Password");

		Button btnSave = new Button("Save");
		btnSave.setWidth("100%");
		btnSave.setIcon(FontAwesome.SAVE);

		// HorizontalLayout ch = new HorizontalLayout();
		VerticalLayout cC = new VerticalLayout();
		cC.addComponent(lbAddUser);

		// addUserForm.addComponent(lbAddUser);
		addUserForm.addComponent(tfFName);
		addUserForm.addComponent(tfLName);
		addUserForm.addComponent(optSex);
		addUserForm.addComponent(doc);
		addUserForm.addComponent(comboAccountType);
		cC.addComponent(addUserForm);
		// p1.setContent(addUserForm);

		FormLayout vc = new FormLayout();
		vc.addComponent(tfUName);
		vc.addComponent(tfPass);
		vc.addComponent(tfRePass);
		vc.addComponent(btnSave);

		// p2.setContent(vc);

		addUserContainer.addComponent(cC);
		addUserContainer.addComponent(vc);

		HorizontalLayout c = new HorizontalLayout();
		c.addComponent(addUserContainer);

		// udc = udm.getDetailsForm("personal", "001",false, false);

		return c;
	}

	public void apmModifier(String strTbName, HorizontalLayout cContent) {

		if (UI.getCurrent().getSession()
				.getAttribute(WorkSpaceManageProfile.SESSION_UDM_IS_LOG) != null) {
			strTbName = (String) UI.getCurrent().getSession()
					.getAttribute(WorkSpaceManageProfile.SESSION_UDM_TABLE_LOG);
		}

		if (udc != null)
			cContent.removeComponent(udc);
		// udc = udm.getDetailsForm(strTbName, "001", false, false);
		cContent.addComponent(udc);
		return;

	}

}
