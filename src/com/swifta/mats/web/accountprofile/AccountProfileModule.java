package com.swifta.mats.web.accountprofile;

import com.swifta.mats.web.usermanagement.UserDetailsModule;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AccountProfileModule{

	public AccountProfileModule(){
		
	}
	
	public HorizontalLayout getProfileContainer(){
	
			VerticalLayout addUserContainer = new VerticalLayout();
			addUserContainer.setWidthUndefined();
			addUserContainer.setWidthUndefined();
			addUserContainer.setMargin(new MarginInfo(true, true, true, true));
			//addUserContainer.setStyleName("c_add_user");
			
			
			FormLayout addUserForm = new FormLayout();
			addUserForm.setSizeUndefined();
			addUserForm.setSpacing(true);
			addUserForm.setMargin(true);
			addUserForm.setStyleName("frm_add_user");
			
			Embedded emb = new Embedded(null,new ThemeResource("img/add_user_small.png"));
			emb.setDescription("Add new User.");
			emb.setStyleName("add_user_img");
			
			
			
			VerticalLayout addUserHeader = new VerticalLayout();
			addUserHeader.setWidthUndefined();
			addUserHeader.setHeightUndefined();
			//addUserHeader.setMargin(new MarginInfo(true, true, true, true));
			addUserHeader.setSpacing(true);
			
			addUserHeader.addComponent(emb);
			addUserHeader.setComponentAlignment(emb, Alignment.TOP_RIGHT);
			
			
			addUserForm.addComponent(addUserHeader);
			Label lbAddUser = new Label("Add New User...");
			lbAddUser.setStyleName("label_add_user");
			lbAddUser.setSizeUndefined();
			
			TextField tfFName = new TextField();
			tfFName.setCaption("First Name");
			
			TextField tfLName = new TextField();
			tfLName.setCaption("Last Name");
			
			OptionGroup optSex = new OptionGroup ("Sex");
			optSex.addItem("Female");
			optSex.addItem("Male");
		
			PopupDateField doc = new PopupDateField ();
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
			
			addUserForm.addComponent(lbAddUser);
			addUserForm.addComponent(tfFName);
			addUserForm.addComponent(tfLName);
			addUserForm.addComponent(optSex);
			addUserForm.addComponent(doc);
			addUserForm.addComponent(comboAccountType);
			addUserForm.addComponent(tfUName);
			
			addUserForm.addComponent(tfPass);
			addUserForm.addComponent(tfRePass);
			addUserForm.addComponent(btnSave);
		
			addUserContainer.addComponent(addUserForm);
			
			
			
			/*if(strSearchSessionVar== null){
				operationFormUserProfile.setEnabled(false);
				operationFormAccountProfile.setEnabled(false);
				emb.setEnabled(false);
				lbOperationHeader.setEnabled(false);
				btnDetails.setEnabled(false);
				btnEdit.setEnabled(false);
				btnLink.setEnabled(false);
				btnDelete.setEnabled(false);
				btnULog.setEnabled(false);
				btnAuthentication.setEnabled(false);
				btnAuthorization.setEnabled(false);
				btnACLog.setEnabled(false);
			}*/
			
			
			UserDetailsModule udm = new UserDetailsModule();
				

	return udm.getDetailsForm("personal", "001",false, false);
}
	
	
	
	
}
