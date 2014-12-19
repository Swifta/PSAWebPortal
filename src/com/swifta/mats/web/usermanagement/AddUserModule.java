package com.swifta.mats.web.usermanagement;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	Map<Integer, String> profToID;
	private OptionGroup optSex;
	private boolean isReset = false;

	public AddUserModule() {

		profToID = new HashMap<>();
		/*
		 * profToID.put(1, "BACK OFFICE");
		 * 
		 * profToID.put(2, "SERVICE PROVIDER"); profToID.put(3,
		 * "FINANCIAL CONTROLLER"); profToID.put(4, "CUSTOMER CARE");
		 * profToID.put(5, "MOBILE MONEY"); profToID.put(6, "SUPER AGENT");
		 * profToID.put(7, "SUB AGENT"); profToID.put(8, "DEPOSIT ONLY");
		 * profToID.put(9, "DEPOSIT & WITHDRAW"); profToID.put(10,
		 * "WITHDRAW ONLY"); profToID.put(11, "DEALER"); profToID.put(12,
		 * "CASH"); profToID.put(13, "MATS ACCOUNT"); profToID.put(14,
		 * "MATS USER");
		 */

		profToID.put(1, "MATS_ADMIN_USER_PROFILE");
		profToID.put(2, "MATS_SERVICE_PROVIDER_PROFILE");
		profToID.put(3, "MATS_FINANCIAL_CONTROLLER_USER_PROFILE");
		profToID.put(4, "MATS_CUSTOMER_CARE_USER_PROFILE");
		profToID.put(5, "MATS_MOBILEMONEY_ACCOUNT_PROFILE");
		profToID.put(6, "MATS_SUPER_AGENT_USER_PROFILE");
		profToID.put(7, "MATS_SUB_AGENT_USER_PROFILE");
		profToID.put(8, "DEPOSIT_ONLY");
		profToID.put(9, "DEPOSIT_AND_WITHDRAWAL");
		profToID.put(10, "WITHDRAWAL_ONLY");
		profToID.put(11, "MATS_DEALER_USER_PROFILE");
		profToID.put(12, "MATS_CASH_ACCOUNT_PROFILE");
		profToID.put(13, "MATS_TOTAL_SYSTEMS_ACCOUNT_PROFILE");
		profToID.put(14, "MATS_SYSTEMS_USER_PROFILE");
		profToID.put(15, "MATS_SERVICE_PROVIDER_USER_PROFILE");
		profToID.put(16, "MATS_SERVICE_PROVIDER_MOMO_ACCOUNT_PROFILE");
		profToID.put(17,
				"MATS_SERVICE_PROVIDER_MOMO_FEE_COMMISSION_ACCOUNT_PROFILE");
		profToID.put(18, "MATS_TOTAL_SYSTEMS_FEE_COMMISSION_ACCOUNT_PROFILE");

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

		int userID = 0;
		int subUserID = 0;

		final String strUserType = (String) UI.getCurrent().getSession()
				.getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE);

		if (strUserType
				.equals(WorkSpaceManageUser.SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE)) {
			userID = 6;
			subUserID = 7;
		}
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

		opt.addItem("FEMALE");
		// opt.setItemCaption(1, "Female");

		opt.addItem("MALE");
		// opt.setItemCaption(2, "Male");
		opt.select("MALE");
		optSex = opt;
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

		tF = new TextField("Email Address");
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

		comboHierarchy = new ComboBox("Profile");

		for (int i = 0; i < profToID.size(); i++) {
			comboHierarchy.addItem(i + 1);
			comboHierarchy.setItemCaption(i + 1, profToID.get(i + 1));
		}

		comboHierarchy.select(1);
		comboProfile = comboHierarchy;
		cAcc.addComponent(comboHierarchy);

		final VerticalLayout cLBody = new VerticalLayout();

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
		combo.addItem(1);
		combo.addItem(2);
		combo.addItem(3);
		combo.setItemCaption(1, "What is your grandfather's last name?");
		combo.setItemCaption(2,
				"What was your favorite junior school teacher's name?");
		combo.setItemCaption(3, "What was one of your nicknames in school?");
		combo.select(2);
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
				// Notification.show(event.getProperty().getValue().toString());
				if (chcTAndC.getValue())
					isReset = false;

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

		// if (comboHierarchy != null) {

		/*
		 * comboHierarchy.addValueChangeListener(new ValueChangeListener() {
		 * private static final long serialVersionUID = 6060653158010946535L;
		 * 
		 * @Override public void valueChange(ValueChangeEvent event) { String
		 * strHierarchy = (String) event.getProperty() .getValue(); if
		 * (strHierarchy == null) return;
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
		// } else {
		// cLBody.setStyleName("c_body_invisible");
		// cRBody.setStyleName("c_body_visible");
		// }

		btnSave.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -935880570210949227L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserManagementService ums = new UserManagementService();
				String strResponse = null;

				/*
				 * registerUser(String bankAccount, int bankCodeid, String
				 * bankdomainNameid, String clearingNumber, String currencyid,
				 * String email, String msisdn, int profileid, String
				 * securityQuest, String securityAns, String termscondition,
				 * String username, int countryid, Date dateofBirth, String
				 * employer, String firstname, String gender, int languageid,
				 * String lastname, int Lgaid, String middlename, String
				 * occupation, String prefix, int stateid, String suffix, String
				 * city, String postalcode, String streetAddress, String
				 * province, Date Expirydate, String idNumber, String idType,
				 * Date Issuedate, String Issue, String PrimaryEmail, String
				 * PrimaryMobilenumber, String PrimaryPhonenumber, String
				 * SecondaryEmail, String SecondaryMobilenumber, String
				 * SecondaryPhonenumber)
				 */

				try {
					String bacc = tFBAcc.getValue().toString();
					int bid = Integer.valueOf(comboBID.getValue().toString());
					String bd = comboBDomain.getValue().toString();
					String clrno = tFClrNo.getValue().toString();
					String cur = comboCur.getValue().toString();
					String accEmail = tFAccEmail.getValue().toString();
					String msisdn = tFMSISDN.getValue().toString();
					int profid = Integer.valueOf(comboProfile.getValue()
							.toString());
					String secQn = comboSecQn.getValue().toString();
					String secAns = tFSecAns.getValue().toString();
					String tAndC = chcTAndC.getValue().toString();
					String un = tFUN.getValue().toString();
					int country = Integer.valueOf(comboCountry.getValue()
							.toString());
					Date dob = (Date) dFDoB.getValue();
					String employer = tFEmp.getValue().toString();
					String fn = tFFN.getValue().toString();
					String gender = optSex.getItemCaption(optSex.getValue())
							.toString();
					int lang = Integer.valueOf(comboLang.getValue().toString());
					String ln = tFLN.getValue().toString();
					int lgid = Integer.valueOf(comboLG.getValue().toString());

					String mn = tFMN.getValue().toString();
					String occ = tFOcc.getValue().toString();
					String pref = comboPref.getValue().toString();
					int stateid = Integer.valueOf(comboState.getValue()
							.toString());
					String suff = comboSuff.getValue().toString();
					String city = tFCity.getValue().toString();
					String pcode = tFPostalCode.getValue().toString();
					String str = tFStreet.getValue().toString();
					String prov = tFProv.getValue().toString();
					Date doe = (Date) dFDoE.getValue();
					String idno = tFIDNo.getValue().toString();
					String idtype = comboIDType.getValue().toString();
					Date doi = (Date) dFDoI.getValue();

					String issuer = tFIssuer.getValue().toString();
					String pem = tFPEmail.getValue().toString();
					String pmno = tFPMNo.getValue().toString();

					String pamno = tFPANo.getValue().toString();
					String sem = tFSEmail.getValue().toString();
					String smno = tFSMNo.getValue().toString();
					String samno = tFSANo.getValue().toString();

					strResponse =

					ums.registerUser(bacc, bid, bd, clrno, cur, accEmail,
							msisdn, profid, secQn, secAns, tAndC, un, country,
							dob, employer, fn, gender, lang, ln, lgid, mn, occ,
							pref, stateid, suff, city, pcode, str, prov, doe,
							idno, idtype, doi, issuer, pem, pmno, pamno, sem,
							smno, samno);
				} catch (Exception e) {
					// e.printStackTrace();
					Notification.show("Response: " + e.getMessage());
					return;
				}

				Notification.show("Response: " + strResponse);

			}
		});

		btnReset.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 3212854064282339617L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (isReset)
					return;

				Notification.show("Cleared.");

				tFFN.setValue("");
				tFMN.setValue("");
				tFLN.setValue("");
				tFOcc.setValue("");
				tFEmp.setValue("");
				tFUN.setValue("");
				tFMSISDN.setValue("");
				tFBAcc.setValue("");
				tFAccEmail.setValue("");
				tFClrNo.setValue("");
				tFSecAns.setValue("");
				tFPMNo.setValue("");
				tFPANo.setValue("");
				tFPEmail.setValue("");
				tFPostalCode.setValue("");
				tFStreet.setValue("");
				tFCity.setValue("");
				tFProv.setValue("");
				tFSMNo.setValue("");
				tFSANo.setValue("");
				tFSEmail.setValue("");
				tFIssuer.setValue("");
				tFIDNo.setValue("");

				chcTAndC.setValue(false);
				comboPref.select(null);

				comboSuff.select(null);
				comboState.select(null);
				comboLG.select(null);
				comboCountry.select(null);
				comboLang.select(null);
				comboBDomain.select(null);
				comboBID.select(null);
				comboCur.select(null);
				comboSecQn.select(null);
				comboProfile.select(null);
				comboIDType.select(null);

				Calendar cal = Calendar.getInstance();
				Date date = cal.getTime();

				dFDoB.setValue(date);
				dFDoB.setValue(date);
				dFDoI.setValue(date);
				dFDoE.setValue(date);
				optSex.select(null);
				isReset = true;

				/*
				 * ArrayList<Object> arrLAllFields = new ArrayList<>();
				 * arrLAllFields.add(false); arrLAllFields.add(tFFN);
				 * arrLAllFields.add(tFMN); arrLAllFields.add(tFLN);
				 * arrLAllFields.add(tFOcc); arrLAllFields.add(tFEmp);
				 * arrLAllFields.add(tFUN); arrLAllFields.add(tFMSISDN);
				 * arrLAllFields.add(tFBAcc); arrLAllFields.add(tFAccEmail);
				 * arrLAllFields.add(tFClrNo); arrLAllFields.add(tFSecAns);
				 * arrLAllFields.add(tFPMNo); arrLAllFields.add(tFPANo);
				 * arrLAllFields.add(tFPEmail); arrLAllFields.add(tFPostalCode);
				 * arrLAllFields.add(tFStreet); arrLAllFields.add(tFCity);
				 * arrLAllFields.add(tFProv); arrLAllFields.add(tFSMNo);
				 * arrLAllFields.add(tFSANo); arrLAllFields.add(tFSEmail);
				 * arrLAllFields.add(tFIssuer); arrLAllFields.add(tFIDNo);
				 * 
				 * arrLAllFields.add(chcTAndC); arrLAllFields.add(comboPref);
				 * arrLAllFields.add(comboSuff); arrLAllFields.add(comboState);
				 * arrLAllFields.add(comboLG); arrLAllFields.add(comboCountry);
				 * arrLAllFields.add(comboLang);
				 * arrLAllFields.add(comboBDomain); arrLAllFields.add(comboBID);
				 * arrLAllFields.add(comboCur); arrLAllFields.add(comboSecQn);
				 * arrLAllFields.add(comboProfile);
				 * arrLAllFields.add(comboIDType);
				 * 
				 * arrLAllFields.add(dFDoB); arrLAllFields.add(dFDoI);
				 * arrLAllFields.add(dFDoE); arrLAllFields.add(optSex);
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

}
