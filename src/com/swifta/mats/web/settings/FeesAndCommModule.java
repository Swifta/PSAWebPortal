package com.swifta.mats.web.settings;

import com.swifta.mats.web.usermanagement.UserDetailsModule;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class FeesAndCommModule {
	UserDetailsModule udm;
	HorizontalLayout udc;

	public FeesAndCommModule() {
		udm = new UserDetailsModule();
	}

	public HorizontalLayout getFeesContainer() {

		VerticalLayout addUserContainer = new VerticalLayout();
		addUserContainer.setWidthUndefined();
		addUserContainer.setWidthUndefined();
		// addUserContainer.setMargin(new MarginInfo(true, true, true, true));
		// addUserContainer.setStyleName("c_add_user");

		VerticalLayout addUserForm = new VerticalLayout();
		addUserForm.setSizeUndefined();
		// addUserForm.setSpacing(new MarginInfo(false, true, true,true));

		// addUserForm.setMargin(true);
		addUserForm.setStyleName("frm_add_user frm_manage_commission");

		// Embedded emb = new Embedded(null, new ThemeResource(
		// "img/add_user_small.png"));
		// emb.setDescription("Add new User.");
		// emb.setStyleName("add_user_img");

		VerticalLayout addUserHeader = new VerticalLayout();
		addUserHeader.setWidthUndefined();
		addUserHeader.setHeightUndefined();
		// addUserHeader.setMargin(new MarginInfo(true, true, true, true));
		addUserHeader.setSpacing(true);

		// addUserHeader.addComponent(emb);
		// addUserHeader.setComponentAlignment(emb, Alignment.TOP_RIGHT);

		addUserForm.addComponent(addUserHeader);
		Label lbAddUser = new Label("Manage Fees");
		lbAddUser.setStyleName("label_add_user");
		lbAddUser.setSizeUndefined();

		addUserForm.addComponent(lbAddUser);
		Label lbChoose = new Label("Please choose...");

		final ComboBox comboOp = new ComboBox("Operator");
		comboOp.addItem(1);
		comboOp.setItemCaption(1, "Teasy");
		comboOp.addItem(2);
		comboOp.setItemCaption(2, "Pocket Money");

		final ComboBox comboTxType = new ComboBox("Transaction Type");
		comboTxType.addItem(1);
		comboTxType.setItemCaption(1, "Send money to registered user");
		comboTxType.addItem(2);
		comboTxType.setItemCaption(2, "Send Modney to unregistered user");
		comboTxType.addItem(3);
		comboTxType.setItemCaption(3, "Withdraw Money from Agent");
		comboTxType.select(1);

		addUserForm.addComponent(lbChoose);
		addUserForm.addComponent(comboOp);
		addUserForm.addComponent(comboTxType);

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

		// addUserForm.addComponent(tfFName);
		// addUserForm.addComponent(tfLName);
		// addUserForm.addComponent(optSex);
		// addUserForm.addComponent(doc);
		// addUserForm.addComponent(comboAccountType);
		// addUserForm.addComponent(tfUName);

		// addUserForm.addComponent(tfPass);
		// addUserForm.addComponent(tfRePass);
		// addUserForm.addComponent(btnSave);

		HorizontalLayout cAttr = new HorizontalLayout();
		// HorizontalLayout cValues = new HorizontalLayout();
		// Label lbTxType = new Label("Transaction Type");
		// Label lbMin = new Label("Min.");
		// Label lbMax = new Label("Max.");
		// Label lbMode = new Label("Charge Mode");
		// Label lbCharge = new Label("Charge Amount");
		// cAttr.addComponent(lbTxType);
		// cAttr.addComponent(lbMin);
		// cAttr.addComponent(lbMax);
		// cAttr.addComponent(lbMode);
		// cAttr.addComponent(lbCharge);
		cAttr.setSpacing(true);
		cAttr.setMargin(new MarginInfo(true, false, true, false));
		cAttr.setStyleName("c_attr");

		final TextField tFOp = new TextField();
		tFOp.setCaption("Selected Operator");
		tFOp.setValue("None: ");
		tFOp.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		tFOp.setReadOnly(true);
		// tFOp.setStyleName("tf_op");

		addUserForm.addComponent(tFOp);

		HorizontalLayout cAttrItem = new HorizontalLayout();
		// cAttrItem.setSizeFull();
		VerticalLayout cItemContent = new VerticalLayout();
		Label lbAttr = new Label(comboTxType.getCaption());
		lbAttr.setSizeUndefined();
		cItemContent.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		final Label lbAttrVal = new Label(
				comboTxType.getItemCaption(comboTxType.getValue()));

		lbAttrVal.setStyleName("attr_val");
		cItemContent.addComponent(lbAttr);
		cItemContent.setComponentAlignment(lbAttr, Alignment.MIDDLE_CENTER);
		cItemContent.addComponent(lbAttrVal);
		cAttrItem.addComponent(cItemContent);
		cAttr.addComponent(cAttrItem);

		cItemContent = new VerticalLayout();
		lbAttr = new Label("Min.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		TextField tFAttrVal = new TextField();

		cItemContent.addComponent(lbAttr);
		cItemContent.addComponent(tFAttrVal);
		cAttrItem.addComponent(cItemContent);

		cItemContent = new VerticalLayout();
		lbAttr = new Label("Max.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		tFAttrVal = new TextField();

		cItemContent.addComponent(lbAttr);
		cItemContent.addComponent(tFAttrVal);
		cAttrItem.addComponent(cItemContent);

		cItemContent = new VerticalLayout();
		lbAttr = new Label("Charge Mode.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		ComboBox comboAttrVal = new ComboBox();
		comboAttrVal.addItem(1);
		comboAttrVal.setItemCaption(1, "%ge");
		comboAttrVal.addItem(2);
		comboAttrVal.setItemCaption(2, "Fixed");
		comboAttrVal.select(2);

		cItemContent.addComponent(lbAttr);
		cItemContent.addComponent(comboAttrVal);
		cAttrItem.addComponent(cItemContent);

		cItemContent = new VerticalLayout();
		lbAttr = new Label("Amount (%/N)");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		tFAttrVal = new TextField();

		cItemContent.addComponent(lbAttr);
		cItemContent.addComponent(tFAttrVal);
		cAttrItem.addComponent(cItemContent);

		cAttr.addComponent(cAttrItem);
		addUserForm.addComponent(cAttr);

		addUserContainer.addComponent(addUserForm);
		HorizontalLayout c = new HorizontalLayout();
		c.addComponent(addUserContainer);

		comboTxType.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -4750457498573552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				lbAttrVal.setValue(comboTxType.getItemCaption(comboTxType
						.getValue()));
			}

		});

		comboOp.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -4750457498573552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				tFOp.setReadOnly(false);
				tFOp.setValue(comboOp.getItemCaption(comboOp.getValue()));
				tFOp.setReadOnly(true);
			}

		});

		return c;
	}

	public HorizontalLayout getFeesForm(String strTbName, String strID,
			boolean a, boolean b) {
		return null;
	}

	public void apmModifier(String strTbName, HorizontalLayout cContent) {

		if (UI.getCurrent().getSession()
				.getAttribute(WorkSpaceManageFeesAndComm.SESSION_UDM_IS_LOG) != null) {
			strTbName = (String) UI
					.getCurrent()
					.getSession()
					.getAttribute(
							WorkSpaceManageFeesAndComm.SESSION_UDM_TABLE_LOG);
		}

		if (udc != null)
			cContent.removeComponent(udc);
		udc = udm.getDetailsForm(strTbName, "001", false, false);
		cContent.addComponent(udc);
		return;

	}

}
