package com.swifta.mats.web.usermanagement;

import java.rmi.RemoteException;
import java.util.Date;

import com.swifta.mats.web.utils.UserManagementService;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Address;
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
		cBasic.addComponent(tF);

		tF = new TextField("Middle Name");
		tFMN = tF;
		cBasic.addComponent(tF);

		tF = new TextField("Last Name");
		tFMN = tF;
		cBasic.addComponent(tF);

		OptionGroup opt = new OptionGroup("Gender");
		opt.addItem("Female");
		opt.addItem("Male");
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
		cBasic.addComponent(combo);

		combo = new ComboBox("Suffix");
		combo.addItem("Ph.D");
		combo.addItem("M.B.A");
		combo.addItem("RA");
		combo.addItem("CISA ");
		comboSuff = combo;
		cBasic.addComponent(combo);

		combo = new ComboBox("Language");
		combo.addItem("en-US");
		combo.addItem("en-UK");
		combo.addItem("fr");
		comboLang = combo;
		cBasic.addComponent(combo);

		tF = new TextField("Occupation");
		tFOcc = tF;
		cBasic.addComponent(tF);

		tF = new TextField("Employer");
		tFEmp = tF;
		cBasic.addComponent(tF);

		PopupDateField dF = new PopupDateField("DoB");
		dFDoB = dF;
		cBasic.addComponent(dF);

		combo = new ComboBox("State");
		combo.addItem("California");
		comboState = combo;
		cBasic.addComponent(combo);

		combo = new ComboBox("Local Government");
		combo.addItem("Cal LG");
		comboLG = combo;
		cBasic.addComponent(combo);

		combo = new ComboBox("Country");
		combo.addItem("USA");
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
				comboIDType = combo;
				cCompany.addComponent(combo);

				tF = new TextField("ID No.");
				tFIDNo = tF;
				cCompany.addComponent(tF);

				tF = new TextField("Issuer");
				tFIssuer = tF;
				cCompany.addComponent(tF);

				dF = new PopupDateField("Issue Date");
				dFDoI = dF;
				cCompany.addComponent(dF);

				dF = new PopupDateField("Expiry Date");
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
		tFPMNo = tF;
		pC.addComponent(tF);

		tF = new TextField("Alt. Phone No.");
		tFPANo = tF;
		pC.addComponent(tF);

		tF = new TextField("E-mail Address");
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
		tFSMNo = tF;
		sC.addComponent(tF);

		tF = new TextField("Alt. Phone No.");
		tFSANo = tF;
		sC.addComponent(tF);

		tF = new TextField("E-mail Address");
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
		tFStreet = tF;
		physicalC.addComponent(tF);

		tF = new TextField("Postal Code");
		tFPostalCode = tF;
		physicalC.addComponent(tF);

		tF = new TextField("City");
		tFCity = tF;
		physicalC.addComponent(tF);

		tF = new TextField("Province");
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
			comboHierarchy.addItem("Super " + strUserType);
			comboHierarchy.select("Super " + strUserType);
			comboHierarchy.addItem("Sub " + strUserType);
			comboProfile = comboHierarchy;
			cAcc.addComponent(comboHierarchy);
		}

		final VerticalLayout cLBody = new VerticalLayout();
		// tF = new TextField("Account ID");
		// cLBody.addComponent(tF);

		// tF = new TextField(strUserType + " ID");
		// cLBody.addComponent(tF);

		tF = new TextField("Username");
		tFUN = tF;
		cLBody.addComponent(tF);

		tF = new TextField("MSISDN");
		tFMSISDN = tF;
		cLBody.addComponent(tF);

		// / tF = new TextField("PIN");
		// / cLBody.addComponent(tF);

		tF = new TextField("Email");
		tFAccEmail = tF;
		cLBody.addComponent(tF);

		combo = new ComboBox("Bank Domain");
		combo.addItem("Stanbic Bank");
		comboBDomain = combo;
		cLBody.addComponent(combo);

		combo = new ComboBox("Bank Code ID");
		combo.addItem("001");

		comboBID = combo;
		cLBody.addComponent(combo);

		tF = new TextField("Bank Account");
		tFBAcc = tF;
		cLBody.addComponent(tF);

		combo = new ComboBox("Currency");
		combo.addItem("US Dollars");
		comboCur = combo;
		cLBody.addComponent(combo);

		tF = new TextField("Clearing Number");
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
		comboSecQn = combo;
		cLBody.addComponent(combo);

		tF = new TextField("Answer");
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
			tF = new TextField("MSISDN");
			cRBody.addComponent(tF);

			tF = new TextField("Email");
			cRBody.addComponent(tF);

			tF = new TextField("Bank Code ID");
			cRBody.addComponent(tF);

			tF = new TextField("Bank Account");
			cRBody.addComponent(tF);

			tF = new TextField("Clearing Number");
			cRBody.addComponent(tF);

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
									.getValue().toString(), comboCur.getValue()
									.toString(), tFAccEmail.getValue()
									.toString(),
							tFMSISDN.getValue().toString(),
							(Integer) comboProfile.getValue(), comboSecQn
									.getValue().toString(), tFSecAns.getValue()
									.toString(),
							chcTAndC.getValue().toString(), tFUN.getValue()
									.toString(), (Integer) comboCountry
									.getValue(), (Date) dFDoB.getValue(), tFEmp
									.getValue().toString(), tFFN.getValue()
									.toString(), (Integer) optSex.getValue(),
							(Integer) comboLang.getValue(), tFLN.getValue()
									.toString(), (Integer) comboLG.getValue(),
							tFMN.getValue().toString(), tFOcc.getValue()
									.toString(), comboPref.getValue()
									.toString(), (Integer) comboState
									.getValue(), comboSuff.getValue()
									.toString(), tFCity.getValue().toString(),
							tFPostalCode.getValue().toString(), tFStreet
									.getValue().toString(), tFProv.getValue()
									.toString(), (Date) dFDoE.getValue(),
							tFIDNo.getValue().toString(), comboIDType
									.getValue().toString(), (Date) dFDoI
									.getValue(),
							tFIssuer.getValue().toString(), tFPEmail.getValue()
									.toString(), tFPMNo.getValue(), tFPANo
									.getValue().toString(), tFSEmail.getValue()
									.toString(), tFSMNo.getValue().toString(),
							tFSANo.getValue().toString());
				} catch (RemoteException e) {

					e.printStackTrace();
				}

				Notification.show("Response: " + strResponse);

				/*
				 * registerUser(String bankAccount, int bankCodeid, String
				 * bankdomainNameid, String clearingNumber, String currencyid,
				 * String email, String msisdn, int profileid, String
				 * securityQuest, String securityAns, String termscondition,
				 * String username, int countryid, Date dateofBirth, String
				 * employer, String firstname, int genderid, int languageid,
				 * String lastname, int Lgaid, String middlename, String
				 * occupation, String prefix, int stateid, String suffix, String
				 * city, String postalcode, String streetAddress, String
				 * province, Date Expirydate, String idNumber, String idType,
				 * Date Issuedate, String Issue, String PrimaryEmail, String
				 * PrimaryMobilenumber, String PrimaryPhonenumber, String
				 * SecondaryEmail, String SecondaryMobilenumber, String
				 * SecondaryPhonenumber)
				 */

				/*
				 * ums.CreateProfile(addr, comboCountry.getValue(),
				 * dFDoB.getValue(), tFEmp.getValue(), tFFN.getValue(), idInfo,
				 * optSex.getValue(), comboLang.getValue(), tFLN.getValue(),
				 * comboLG.getValue(), tFMN.getValue(), tFOcc.getValue(),
				 * comboPref.getValue(), sContactInfo, pContactInfo,
				 * comboState.getValue(), comboSuff.getValue(),
				 * comboBDomain.getValue(), comboBID.getValue(),
				 * tFBAcc.getValue(), tFClrNo.getValue(), comboCur.getValue(),
				 * tFAccEmail.getValue(), tFMSISDN.getValue(),
				 * comboProfile.getValue(), comboSecQn.getValue(),
				 * chcTAndC.getValue(), tFUN.getValue());
				 */

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

	private void setAddressInfo(String strName, String strValue, Address addr) {
		if (strName.equals("Street"))
			addr.setStreetaddress(strValue);
		if (strName.equals("City"))
			addr.setCity(strValue);
		if (strName.equals("Province"))
			addr.setProvince(strValue);
		if (strName.equals("Postal Code"))
			addr.setPostalCode(strValue);
	}

}
