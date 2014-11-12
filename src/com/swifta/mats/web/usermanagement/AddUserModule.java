package com.swifta.mats.web.usermanagement;

import java.rmi.RemoteException;
import java.util.Date;

import com.swifta.mats.web.utils.UserManagementService;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AddUserModule {

	private TextField tFFN;
	private TextField tFMN;
	private TextField tFLN;
	private TextField tFOcc;
	private TextField tFEmp;
	private TextField tFUN;
	private TextField tFMSISDN;
	private TextField tFBAcc;
	private TextField tFAccEmail;
	private TextField tFClrNo;
	private TextField tFSecAns;
	private TextField tFPMNo;
	private TextField tFPANo;
	private TextField tFPEmail;
	private TextField tFPostalCode;
	private TextField tFStreet;
	private TextField tFCity;
	private TextField tFProv;
	private TextField tFSMNo;
	private TextField tFSANo;
	private TextField tFSEmail;
	private TextField tFIssuer;
	private TextField tFIDNo;

	private CheckBox chcTAndC;
	private ComboBox comboPref;
	private ComboBox comboSuff;
	private ComboBox comboState;
	private ComboBox comboLG;
	private ComboBox comboCountry;
	private ComboBox comboLang;
	private ComboBox comboBDomain;
	private ComboBox comboBID;
	private ComboBox comboCur;
	private ComboBox comboSecQn;
	private ComboBox comboProfile;
	private ComboBox comboIDType;

	private PopupDateField dFDoB;
	private PopupDateField dFDoI;
	private PopupDateField dFDoE;

	private OptionGroup optSex;

	public AddUserModule() {

	}

	public VerticalLayout getAddUserForm() {

		VerticalLayout addUserContainer = new VerticalLayout();
		addUserContainer.setWidthUndefined();
		addUserContainer.setWidthUndefined();
		addUserContainer.setMargin(new MarginInfo(false, true, true, true));
		addUserContainer.setStyleName("c_add_user");
		addUserContainer.addComponent(getNewUserContainer());

		return addUserContainer;
	}

	private VerticalLayout getNewUserContainer() {

		final String strUserType = (String) UI.getCurrent().getSession()
				.getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE);
		VerticalLayout cAgentInfo = new VerticalLayout();

		VerticalLayout cBasic = new VerticalLayout();
		Label lbB = new Label("Basic");
		lbB.setStyleName("lb_frm_add_user");
		cBasic.addComponent(lbB);

		TextField tF = new TextField("First Name");
		tFFN = tF;
		tF.setValue("Paul");
		cBasic.addComponent(tF);

		tF = new TextField("Middle Name");
		tF.setValue("Pwndz");
		tFMN = tF;
		cBasic.addComponent(tF);

		tF = new TextField("Last Name");
		tF.setValue("Kigozi");
		tFLN = tF;
		cBasic.addComponent(tF);

		OptionGroup opt = new OptionGroup("Gender");

		opt.addItem(1);
		opt.setItemCaption(1, "Female");

		opt.addItem(2);
		opt.setItemCaption(2, "Male");
		opt.select(2);
		optSex = opt;
		opt.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Notification.show(event.getProperty().getValue().toString());

			}

		});
		cBasic.addComponent(opt);

		ComboBox combo = new ComboBox("Prefix");
		combo.addItem("Mr. ");
		combo.addItem("Mrs. ");
		combo.addItem("Dr. ");
		combo.addItem("Eng. ");
		combo.addItem("Prof. ");
		comboPref = combo;
		comboPref.select("Eng. ");
		cBasic.addComponent(combo);

		combo = new ComboBox("Suffix");
		combo.addItem("Ph.D");
		combo.addItem("M.B.A");
		combo.addItem("RA");
		combo.addItem("CISA ");
		combo.select("Ph.D");
		comboSuff = combo;
		cBasic.addComponent(combo);

		combo = new ComboBox("Language");
		combo.addItem(1);
		combo.select(1);
		combo.setItemCaption(1, "en-US");
		combo.addItem(2);
		combo.setItemCaption(2, "en-UK");
		combo.addItem(3);
		combo.setItemCaption(3, "fr");
		comboLang = combo;
		cBasic.addComponent(combo);

		tF = new TextField("Occupation");
		tF.setValue("Software Engineer");
		tFOcc = tF;
		cBasic.addComponent(tF);

		tF = new TextField("Employer");
		tF.setValue("Swifta");
		tFEmp = tF;
		cBasic.addComponent(tF);

		PopupDateField dF = new PopupDateField("DoB");
		dF.setValue(new Date("12/11/88"));
		dFDoB = dF;
		cBasic.addComponent(dF);

		combo = new ComboBox("State");
		combo.addItem(1);
		combo.setItemCaption(1, "California");
		combo.select(1);
		comboState = combo;
		cBasic.addComponent(combo);

		combo = new ComboBox("Local Government");
		combo.addItem(1);
		combo.setItemCaption(1, "Ca. LG");
		combo.select(1);
		comboLG = combo;
		cBasic.addComponent(combo);

		combo = new ComboBox("Country");
		combo.addItem(1);
		combo.setItemCaption(1, "USA");
		combo.select(1);
		comboCountry = combo;
		cBasic.addComponent(combo);

		/*
		 * if (!(strUserType.equals("CCO") || strUserType.equals("BA"))) {
		 * 
		 * cBasic.addComponent(dF);
		 * 
		 * combo.addItem("Passport"); combo.addItem("Voter's Card");
		 * combo.addItem("Driving License"); combo.addItem("National ID");
		 * combo.addItem("Residential ID"); cBasic.addComponent(combo);
		 * 
		 * tF = new TextField("ID No."); cBasic.addComponent(tF);
		 * 
		 * combo = new ComboBox("State"); cBasic.addComponent(combo);
		 * 
		 * combo = new ComboBox("Country"); cBasic.addComponent(combo); }
		 */

		VerticalLayout cC = new VerticalLayout();

		HorizontalLayout cBAndCAndAcc = new HorizontalLayout();
		cBAndCAndAcc.addComponent(cBasic);
		cBAndCAndAcc.addComponent(cC);

		VerticalLayout cCompany = new VerticalLayout();
		// Label lbC = new Label("Company");
		Label lbC = new Label("Identification");
		lbC.setStyleName("lb_frm_add_user");
		if (!(strUserType.equals("CCO") || strUserType.equals("BA"))) {

			cCompany.addComponent(lbC);
			/*
			 * tF = new TextField("Name"); cCompany.addComponent(tF);
			 * 
			 * dF = new PopupDateField("DoR"); cCompany.addComponent(dF);
			 * 
			 * tF = new TextField("TIN"); cCompany.addComponent(tF);
			 * 
			 * combo = new ComboBox("CoO");
			 */
			combo = new ComboBox("ID Type");
			if (!(strUserType.equals("CCO") || strUserType.equals("BA"))) {

				combo.addItem("Passport");
				combo.addItem("Voter's Card");
				combo.addItem("Driving License");
				combo.addItem("National ID");
				combo.addItem("Residential ID");
				combo.select("Passport");
				comboIDType = combo;
				cCompany.addComponent(combo);

				tF = new TextField("ID No.");
				tF.setValue("001");
				tFIDNo = tF;
				cCompany.addComponent(tF);

				tF = new TextField("Issuer");
				tFIssuer = tF;
				tFIssuer.setValue("Republic of Uganda");
				cCompany.addComponent(tF);

				dF = new PopupDateField("Issue Date");
				dF.setValue(new Date("12/12/12"));
				dFDoI = dF;
				cCompany.addComponent(dF);

				dF = new PopupDateField("Expiry Date");
				dF.setValue(new Date("12/12/14"));
				dFDoE = dF;
				cCompany.addComponent(dF);

			}

			cC.addComponent(cCompany);
		}

		VerticalLayout pC = new VerticalLayout();
		lbC = new Label("Primary Contacts");
		HorizontalLayout cLbc = new HorizontalLayout();
		cLbc.setSizeUndefined();
		cLbc.setMargin(new MarginInfo(true, false, false, false));
		cLbc.addComponent(lbC);
		pC.addComponent(cLbc);

		tF = new TextField("Mobile Phone No.");
		tF.setValue("+256704191152");
		tFPMNo = tF;

		pC.addComponent(tF);

		tF = new TextField("Alt. Phone No.");
		tF.setValue("+1704191152");
		tFPANo = tF;
		pC.addComponent(tF);

		tF = new TextField("E-mail Address");
		tF.setValue("pwndz172@gmail.com");
		tFPEmail = tF;
		pC.addComponent(tF);
		cC.addComponent(pC);

		VerticalLayout sC = new VerticalLayout();
		lbC = new Label("Secondary Contacts");
		cLbc = new HorizontalLayout();
		cLbc.setSizeUndefined();
		cLbc.setMargin(new MarginInfo(true, false, false, false));
		cLbc.addComponent(lbC);
		sC.addComponent(cLbc);

		tF = new TextField("Mobile Phone No.");
		tF.setValue("+256804191152");
		tFSMNo = tF;
		sC.addComponent(tF);

		tF = new TextField("Alt. Phone No.");
		tF.setValue("+1804191152");
		tFSANo = tF;
		sC.addComponent(tF);

		tF = new TextField("E-mail Address");
		tF.setValue("pkigozi@swifta.com");
		tFSEmail = tF;
		sC.addComponent(tF);

		cC.addComponent(sC);

		VerticalLayout physicalC = new VerticalLayout();
		lbC = new Label("Physical Address");
		cLbc = new HorizontalLayout();
		cLbc.setSizeUndefined();
		cLbc.setMargin(new MarginInfo(true, false, false, false));
		cLbc.addComponent(lbC);
		physicalC.addComponent(cLbc);

		tF = new TextField("Street");
		tF.setValue("Yusuf Lule Rd.");
		tFStreet = tF;
		physicalC.addComponent(tF);

		tF = new TextField("Postal Code");
		tF.setValue("23");
		tFPostalCode = tF;
		physicalC.addComponent(tF);

		tF = new TextField("City");
		tF.setValue("Kampala");
		tFCity = tF;
		physicalC.addComponent(tF);

		tF = new TextField("Province");
		tF.setValue("Central");
		tFProv = tF;
		physicalC.addComponent(tF);

		cC.addComponent(physicalC);

		/*
		 * // String strAccTypeCaption = null; if (!(strUserType.equals("CCO")
		 * || strUserType.equals("BA"))) { tF = new TextField("Fax");
		 * cC.addComponent(tF); // strAccTypeCaption = Hierarch }
		 */

		/*
		 * tF = new TextField("E-mail Address"); cC.addComponent(tF);
		 * 
		 * tF = new TextField("Physical Address"); cC.addComponent(tF);
		 */

		VerticalLayout cAcc = new VerticalLayout();
		Label lbAcc = new Label("Account");
		lbAcc.setStyleName("lb_frm_add_user");
		cAcc.addComponent(lbAcc);

		ComboBox comboHierarchy = null;

		if (!(strUserType.equals("CCO") || strUserType.equals("BA"))) {
			comboHierarchy = new ComboBox("Hierarchy");
			comboHierarchy.addItem(1);
			comboHierarchy.setItemCaption(1, "Super " + strUserType);
			comboHierarchy.select(1);
			comboHierarchy.addItem(2);
			comboHierarchy.setItemCaption(2, "Sub " + strUserType);
			comboProfile = comboHierarchy;
			cAcc.addComponent(comboHierarchy);
		}

		final VerticalLayout cLBody = new VerticalLayout();
		// tF = new TextField("Account ID");
		// cLBody.addComponent(tF);

		// tF = new TextField(strUserType + " ID");
		// cLBody.addComponent(tF);

		tF = new TextField("Username");
		tF.setValue("Livepwndz");
		tFUN = tF;
		cLBody.addComponent(tF);

		tF = new TextField("MSISDN");
		tF.setValue("+256774191152");
		tFMSISDN = tF;
		cLBody.addComponent(tF);

		// / tF = new TextField("PIN");
		// / cLBody.addComponent(tF);

		tF = new TextField("Email");
		tFAccEmail = tF;
		tFAccEmail.setValue("ppounds1@gmail.com");
		cLBody.addComponent(tF);

		combo = new ComboBox("Bank Domain");
		combo.addItem("Stanbic Bank");
		combo.select("Stanbic Bank");
		comboBDomain = combo;
		cLBody.addComponent(combo);

		combo = new ComboBox("Bank Code ID");
		combo.addItem("001");
		combo.select("001");
		comboBID = combo;
		cLBody.addComponent(combo);

		tF = new TextField("Bank Account");
		tF.setValue("00232333452315");
		tFBAcc = tF;
		cLBody.addComponent(tF);

		combo.addItem(1);
		combo.setItemCaption(1, "US Dollars");
		combo.select(1);
		comboCur = combo;
		cLBody.addComponent(combo);

		tF = new TextField("Clearing Number");
		tF.setValue("00212");
		tFClrNo = tF;
		cLBody.addComponent(tF);

		Label lbAccRec = new Label("Account Recovery");

		HorizontalLayout cLbAccRec = new HorizontalLayout();
		cLbAccRec.setSizeUndefined();
		cLbAccRec.setMargin(new MarginInfo(true, false, false, false));
		cLbAccRec.addComponent(lbAccRec);
		cLBody.addComponent(cLbAccRec);

		combo = new ComboBox("Security Question");
		combo.addItem("What is your grandfather's last name?");
		combo.addItem("What was your favorite teacher's name?");
		combo.addItem("What was one of your nicknames in school?");
		combo.select("What was your favorite teacher's name?");
		comboSecQn = combo;
		cLBody.addComponent(combo);

		tF = new TextField("Answer");
		tF.setValue("Mrs. X");
		tFSecAns = tF;
		cLBody.addComponent(tF);

		CheckBox chk = new CheckBox("I accept the terms" + " and conditons.");
		chcTAndC = chk;
		chk.setStyleName("check_t_and_c");
		chk.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Notification.show(event.getProperty().getValue().toString());

			}

		});
		HorizontalLayout cChk = new HorizontalLayout();
		cChk.setSizeUndefined();
		cChk.setMargin(new MarginInfo(true, false, true, false));
		cChk.addComponent(chk);
		cLBody.addComponent(cChk);

		final VerticalLayout cRBody = new VerticalLayout();
		String strNameCap = null;
		if (!(strUserType.equals("CCO") || strUserType.equals("BA"))) {
			// /tF = new TextField("Parent ID");
			// /cRBody.addComponent(tF);
			/*
			 * tF = new TextField("MSISDN"); cRBody.addComponent(tF);
			 * 
			 * tF = new TextField("Email"); cRBody.addComponent(tF);
			 * 
			 * tF = new TextField("Bank Code ID"); cRBody.addComponent(tF);
			 * 
			 * tF = new TextField("Bank Account"); cRBody.addComponent(tF);
			 * 
			 * tF = new TextField("Clearing Number"); cRBody.addComponent(tF);
			 */

			strNameCap = "Username";

		} else {
			strNameCap = "Username";
			tF = new TextField("Account ID");
			cRBody.addComponent(tF);
		}

		/*
		 * combo = new ComboBox("Account Type");
		 * combo.addItem("Commission Account");
		 * combo.addItem("Ordinary Account"); cRBody.addComponent(combo);
		 */

		tF = new TextField(strNameCap);
		cRBody.addComponent(tF);

		if (!(strUserType.equals("CCO") || strUserType.equals("BA"))) {
			/*
			 * tF = new TextField("MSISDN"); cRBody.addComponent(tF);
			 * 
			 * tF = new TextField("Email"); cRBody.addComponent(tF);
			 * 
			 * tF = new TextField("Bank Code ID"); cRBody.addComponent(tF);
			 * 
			 * tF = new TextField("Bank Account"); cRBody.addComponent(tF);
			 * 
			 * tF = new TextField("Clearing Number"); cRBody.addComponent(tF);
			 */
			// tF = new TextField("PIN");
			// cRBody.addComponent(tF);

		} else {
			strNameCap = "Username";
			PasswordField pf = new PasswordField("Password");
			PasswordField rPf = new PasswordField("Re-enter Password");

			cRBody.addComponent(pf);
			cRBody.addComponent(rPf);

			pf.setInputPrompt("Password");
			rPf.setInputPrompt("Password");
		}

		HorizontalLayout cAccBody = new HorizontalLayout();
		cAccBody.addComponent(cLBody);
		cAccBody.addComponent(cRBody);
		cLBody.setStyleName("c_body_visible");
		cRBody.setStyleName("c_body_invisible");
		cAcc.addComponent(cAccBody);

		cBAndCAndAcc.addComponent(cAcc);

		cC.setMargin(new MarginInfo(false, true, false, true));
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

		if (comboHierarchy != null) {

			/*
			 * comboHierarchy.addValueChangeListener(new ValueChangeListener() {
			 * private static final long serialVersionUID =
			 * 6060653158010946535L;
			 * 
			 * @Override public void valueChange(ValueChangeEvent event) {
			 * String strHierarchy = (String) event.getProperty() .getValue();
			 * if (strHierarchy == null) return;
			 * 
			 * if (strHierarchy.equals("Sub " + strUserType)) {
			 * cRBody.setStyleName("c_body_visible");
			 * cLBody.setStyleName("c_body_invisible");
			 * 
			 * for (int i = 0; i < cRBody.getComponentCount(); i++) {
			 * cRBody.getComponent(i).setEnabled(true); }
			 * 
			 * for (int i = 0; i < cLBody.getComponentCount(); i++) {
			 * cLBody.getComponent(i).setEnabled(false); }
			 * 
			 * } else if (strHierarchy.equals("Parent " + strUserType)) {
			 * cRBody.setStyleName("c_body_invisible");
			 * cLBody.setStyleName("c_body_visible");
			 * 
			 * for (int i = 0; i < cLBody.getComponentCount(); i++) {
			 * cLBody.getComponent(i).setEnabled(true); }
			 * 
			 * for (int i = 0; i < cRBody.getComponentCount(); i++) {
			 * cRBody.getComponent(i).setEnabled(false); } } }
			 * 
			 * });
			 */
		} else {
			cLBody.setStyleName("c_body_invisible");
			cRBody.setStyleName("c_body_visible");
		}

		btnSave.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -935880570210949227L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserManagementService ums = new UserManagementService();
				String strResponse = null;

				try {
					strResponse = ums.registerUser(tFBAcc.getValue(), Integer
							.parseInt((String) comboBID.getValue()),
							comboBDomain.getValue().toString(), tFClrNo
									.getValue(),
							comboCur.getValue().toString(), tFAccEmail
									.getValue(), tFMSISDN.getValue(),
							(Integer) comboProfile.getValue(), comboSecQn
									.getValue().toString(),
							tFSecAns.getValue(),
							chcTAndC.getValue().toString(), tFUN.getValue(),
							(Integer) comboCountry.getValue(), (Date) dFDoB
									.getValue(), tFEmp.getValue(), tFFN
									.getValue(),

							(Integer) optSex.getValue(), (Integer) comboLang
									.getValue(), tFLN.getValue(),
							(Integer) comboLG.getValue(), tFMN.getValue()
									.toString(), tFOcc.getValue(), comboPref
									.getValue().toString(),
							(Integer) comboState.getValue(), comboSuff
									.getValue().toString(), tFCity.getValue(),
							tFPostalCode.getValue(), tFStreet.getValue(),
							tFProv.getValue(), (Date) dFDoE.getValue(), tFIDNo
									.getValue(), comboIDType.getValue()
									.toString(), (Date) dFDoI.getValue(),
							tFIssuer.getValue(), tFPEmail.getValue(), tFPMNo
									.getValue(), tFPANo.getValue(), tFSEmail
									.getValue(), tFSMNo.getValue(), tFSANo
									.getValue()

					);
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				Notification.show("Response: " + strResponse);

			}
		});

		return cAgentInfo;
	}

	public VerticalLayout aumModifier(HorizontalLayout contentC) {
		contentC.removeAllComponents();
		VerticalLayout uf = getAddUserForm();
		contentC.addComponent(uf);
		contentC.setComponentAlignment(uf, Alignment.MIDDLE_CENTER);
		contentC.setSpacing(false);
		contentC.setMargin(true);
		contentC.setSizeFull();
		return uf;
	}

}
