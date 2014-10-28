package com.swifta.mats.web.usermanagement;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AddUserModule{

	public AddUserModule(){
		
	}
	
	public VerticalLayout getAddUserForm(){
	
			VerticalLayout addUserContainer = new VerticalLayout();
			addUserContainer.setWidthUndefined();
			addUserContainer.setWidthUndefined();
			addUserContainer.setMargin(new MarginInfo(false, true, true, true));
			addUserContainer.setStyleName("c_add_user");
			
			
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
		
			//addUserContainer.addComponent(addUserForm);
			
			addUserContainer.addComponent(getNewUserContainer());
			
			
			

	return addUserContainer;
}
	
	private void addNewUser(){
		
		
	}
	
	private VerticalLayout getNewUserContainer(){
		
		String strUserType = (String) UI.getCurrent().getSession().getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE);
		VerticalLayout cAgentInfo = new VerticalLayout();
		
		VerticalLayout cBasic = new VerticalLayout();
		Label lbB = new Label("Basic");
		cBasic.addComponent(lbB);
		
		TextField tF = new TextField("First Name");
		cBasic.addComponent(tF);
		
		tF = new TextField("Last Name");
		cBasic.addComponent(tF);
		
		OptionGroup opt = new OptionGroup("Gender");
		opt.addItem("Female");
		opt.addItem("Male");
		cBasic.addComponent(opt);
		
		PopupDateField dF = new PopupDateField("DoB");
		cBasic.addComponent(dF);
		
		ComboBox combo = new ComboBox("ID Type");
		combo.addItem("Passport");
		combo.addItem("Voter's Card");
		combo.addItem("Driving License");
		combo.addItem("National ID");
		combo.addItem("Residential ID");
		cBasic.addComponent(combo);
		
		tF = new TextField("ID No.");
		cBasic.addComponent(tF);
		
		combo = new ComboBox("State");
		cBasic.addComponent(combo);
		
		combo = new ComboBox("Country");
		cBasic.addComponent(combo);
		
		VerticalLayout cC = new VerticalLayout();
		
		HorizontalLayout cBAndCAndAcc = new HorizontalLayout();
		cBAndCAndAcc.addComponent(cBasic);
		cBAndCAndAcc.addComponent(cC);
		
		VerticalLayout cCompany = new VerticalLayout();
		Label lbC = new Label("Company");
		cCompany.addComponent(lbC);
		
		tF = new TextField("Name");
		cCompany.addComponent(tF);
		
		dF = new PopupDateField("DoR");
		cCompany.addComponent(dF);
		
		tF = new TextField("TIN");
		cCompany.addComponent(tF);
		
		combo = new ComboBox("CoO");
		cCompany.addComponent(combo);
		cC.addComponent(cCompany);
		
		
		lbC = new Label("Contacts");
		cC.addComponent(lbC);
		
		tF = new TextField("Phone");
		cC.addComponent(tF);
		
		tF = new TextField("Fax");
		cC.addComponent(tF);
		
		tF = new TextField("E-mail Address");
		cC.addComponent(tF);
		
		tF = new TextField("Physical Address");
		cC.addComponent(tF);
		
		
		
		VerticalLayout cAcc = new VerticalLayout();
		Label lbAcc = new Label("Account");
		cAcc.addComponent(lbAcc);
		
		ComboBox comboHierarchy = new ComboBox("Hierarchy");
		comboHierarchy.addItem("Parent "+strUserType);
		comboHierarchy.select("Parent Agent");
		comboHierarchy.addItem("Sub Agent");
		cAcc.addComponent(comboHierarchy);
		
		final VerticalLayout cLBody = new VerticalLayout();
		tF = new TextField("Account ID");
		cLBody.addComponent(tF);
		
		tF = new TextField("Agent ID");
		cLBody.addComponent(tF);
		
		tF = new TextField("Name");
		cLBody.addComponent(tF);
		
		tF = new TextField("MSISDN");
		cLBody.addComponent(tF);
		
		tF = new TextField("PIN");
		cLBody.addComponent(tF);
		
		final VerticalLayout cRBody = new VerticalLayout();
		tF = new TextField("Parent ID");
		cRBody.addComponent(tF);
		
		combo = new ComboBox("Account Type");
		combo.addItem("Commission Account");
		combo.addItem("Ordinary Account");
		cRBody.addComponent(combo);
		
		
		tF = new TextField("Name");
		cRBody.addComponent(tF);
		
		tF = new TextField("MSISDN");
		cRBody.addComponent(tF);
		
		tF = new TextField("PIN");
		cRBody.addComponent(tF);
		
		HorizontalLayout cAccBody = new HorizontalLayout();
		cAccBody.addComponent(cLBody);
		cAccBody.addComponent(cRBody);
		cLBody.setStyleName("c_body_visible");
		cRBody.setStyleName("c_body_invisible");
		cAcc.addComponent(cAccBody);
		
		
		cBAndCAndAcc.addComponent(cAcc);
		
		cC.setMargin(new MarginInfo(false, true, false,true));
		cAgentInfo.addComponent(cBAndCAndAcc);
		
		Button btnSave = new Button("Save");
		btnSave.setIcon(FontAwesome.SAVE);
		btnSave.setStyleName("btn_link");
		
		Button btnReset = new Button("Reset");
		btnReset.setIcon(FontAwesome.UNDO);
		btnReset.setStyleName("btn_link");
		
		HorizontalLayout cBtnSR = new HorizontalLayout();
		cBtnSR.addComponent(btnSave);
		cBtnSR.addComponent(btnReset);
		
		
		cAcc.addComponent(cBtnSR);
		
		comboHierarchy.addValueChangeListener(new ValueChangeListener(){
			private static final long serialVersionUID = 6060653158010946535L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				String strHierarchy = (String) event.getProperty().getValue();
				if(strHierarchy==null)return;
				
				if(strHierarchy.equals("Sub Agent")){
					cRBody.setStyleName("c_body_visible");
					cLBody.setStyleName("c_body_invisible");
					
					for(int i = 0; i < cRBody.getComponentCount(); i++){
						cRBody.getComponent(i).setEnabled(true);
					}
					
					for(int i = 0; i < cLBody.getComponentCount(); i++){
						cLBody.getComponent(i).setEnabled(false);
					}
					
				}else if(strHierarchy.equals("Parent Agent")){
					cRBody.setStyleName("c_body_invisible");
					cLBody.setStyleName("c_body_visible");
					
					for(int i = 0; i < cLBody.getComponentCount(); i++){
						cLBody.getComponent(i).setEnabled(true);
					}
					
					for(int i = 0; i < cRBody.getComponentCount(); i++){
						cRBody.getComponent(i).setEnabled(false);
					}
				}
				//Notification.show(strHierarchy);
			}
			
		});
		
		
		
		return cAgentInfo;	
	}
	
	private void addNewMerchant(){
		
	}
	
	private void addNewDealer(){
		
	}
	
	private void addNewPartner(){
		
	}
	
	private void addNewAdministrator(){
		
	}
	
	private void addNewCCO(){
		
	}
	
	
	
	
}
