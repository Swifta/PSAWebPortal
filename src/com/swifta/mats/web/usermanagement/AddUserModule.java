package com.swifta.mats.web.usermanagement;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.mats.web.utils.UserManagementService;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
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
	public static Map<Integer, String> profToID;
	private OptionGroup optSex;
	private boolean isValidatorAdded = false;
	boolean isCSelected = false;
	private Calendar cal = null;

	public AddUserModule() {

		profToID = new HashMap<>();
		profToID.put(1, "MATS_ADMIN_USER_PROFILE");
		profToID.put(3, "MATS_FINANCIAL_CONTROLLER_USER_PROFILE");
		profToID.put(4, "MATS_CUSTOMER_CARE_USER_PROFILE");
		profToID.put(6, "MATS_SUPER_AGENT_USER_PROFILE");
		profToID.put(7, "MATS_SUB_AGENT_USER_PROFILE");
		profToID.put(11, "MATS_DEALER_USER_PROFILE");
		profToID.put(15, "MATS_SERVICE_PROVIDER_USER_PROFILE");

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

		VerticalLayout cAgentInfo = new VerticalLayout();

		Embedded emb = new Embedded(null, new ThemeResource(
				"img/add_user_small.png"));
		emb.setDescription("add new user");
		emb.setStyleName("search_user_img");
		emb.setSizeUndefined();

		Label lbSearch = new Label("Add New User... ");

		// Label lbSearch = new Label("Search " + strUserType + " by: ");
		lbSearch.setSizeUndefined();
		lbSearch.setStyleName("label_search_user");
		lbSearch.setSizeUndefined();

		HorizontalLayout header = new HorizontalLayout();
		header.setHeightUndefined();
		header.setMargin(false);
		header.setSpacing(true);
		header.addComponent(emb);
		header.addComponent(lbSearch);
		header.setStyleName("search_user_header");

		cAgentInfo.addComponent(header);
		cAgentInfo.setComponentAlignment(header, Alignment.TOP_CENTER);

		VerticalLayout cBasic = new VerticalLayout();
		Label lbB = new Label("Basic");
		lbB.setStyleName("lb_frm_add_user");
		cBasic.addComponent(lbB);

		TextField tF = new TextField("First Name");
		tFFN = tF;
		tFFN.setRequired(true);
		// tF.setValue("Paul");
		cBasic.addComponent(tF);

		tF = new TextField("Middle Name");
		// tF.setValue("Pwndz");
		tFMN = tF;
		tFMN.setRequired(true);
		cBasic.addComponent(tF);

		tF = new TextField("Last Name");
		// tF.setValue("Kigozi");
		tFLN = tF;
		tFLN.setRequired(true);
		cBasic.addComponent(tF);

		OptionGroup opt = new OptionGroup("Gender");

		opt.addItem("FEMALE");
		// opt.setItemCaption(1, "Female");

		opt.addItem("MALE");
		// opt.setItemCaption(2, "Male");
		// opt.select("MALE");
		optSex = opt;
		optSex.setRequired(true);
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
		// combo.select("Ph.D");
		comboSuff = combo;
		cBasic.addComponent(combo);

		combo = new ComboBox("Language");
		combo.addItem(1);
		// combo.select(1);
		combo.setItemCaption(1, "en-US");
		combo.addItem(2);
		combo.setItemCaption(2, "en-UK");
		combo.addItem(3);
		combo.setItemCaption(3, "fr");
		comboLang = combo;
		comboLang.setRequired(true);
		cBasic.addComponent(combo);

		tF = new TextField("Occupation");
		// tF.setValue("Software Engineer");
		tFOcc = tF;
		tFOcc.setRequired(true);
		cBasic.addComponent(tF);

		tF = new TextField("Employer");
		// tF.setValue("Swifta");
		tFEmp = tF;
		cBasic.addComponent(tF);

		PopupDateField dF = new PopupDateField("DoB");
		Calendar cal = Calendar.getInstance();
		cal.set(1988, 11, 12);
		// dF.setValue(cal.getTime());
		dFDoB = dF;
		cBasic.addComponent(dF);

		combo = new ComboBox("Country");

		comboCountry = combo;
		comboCountry.setRequired(true);
		cBasic.addComponent(combo);

		combo = new ComboBox("State");
		comboState = combo;
		comboState.setRequired(true);
		comboState.setNullSelectionAllowed(false);
		cBasic.addComponent(combo);

		combo = new ComboBox("Local Government");
		// combo.addItem(1);
		// combo.setItemCaption(1, "Ca. LG");
		// combo.select(1);
		comboLG = combo;
		comboLG.setRequired(true);
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

		combo = new ComboBox("ID Type");

		combo.addItem("Passport Number");
		combo.addItem("National Registration Identification Number");
		combo.addItem("Drivers License Number");
		combo.addItem("Identification Card");
		combo.addItem("Employer Identification Number");
		// combo.select("Passport Number");
		comboIDType = combo;
		comboIDType.setRequired(true);
		cCompany.addComponent(combo);

		tF = new TextField("ID No.");
		// tF.setValue("001");
		tFIDNo = tF;
		tFIDNo.setRequired(true);
		cCompany.addComponent(tF);

		tF = new TextField("Issuer");
		tFIssuer = tF;
		// tFIssuer.setValue("Republic of Uganda");
		cCompany.addComponent(tF);

		dF = new PopupDateField("Issue Date");
		// cal = Calendar.getInstance();
		cal.set(12, 12, 12);
		// dF.setValue(cal.getTime());
		dFDoI = dF;
		cCompany.addComponent(dF);

		dF = new PopupDateField("Expiry Date");
		// cal = Calendar.getInstance();
		cal.set(14, 12, 12);
		// dF.setValue(cal.getTime());
		// dF.setValue(cal.getTime());
		dFDoE = dF;
		dFDoI.setRequired(true);
		dFDoI.setImmediate(true);

		dFDoE.setRequired(true);
		dFDoE.setImmediate(true);

		cCompany.addComponent(dF);

		cC.addComponent(cCompany);

		VerticalLayout pC = new VerticalLayout();
		lbC = new Label("Primary Contacts");
		HorizontalLayout cLbc = new HorizontalLayout();
		cLbc.setSizeUndefined();
		cLbc.setMargin(new MarginInfo(true, false, false, false));
		cLbc.addComponent(lbC);
		pC.addComponent(cLbc);

		tF = new TextField("Mobile Phone No.");
		// tF.setValue("+256704191152");
		tFPMNo = tF;

		pC.addComponent(tF);

		tF = new TextField("Alt. Phone No.");
		// tF.setValue("+1704191152");
		tFPANo = tF;
		pC.addComponent(tF);

		tF = new TextField("Email Address");
		// tF.setValue("pwndz172@gmail.com");
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
		// tF.setValue("+256804191152");
		tFSMNo = tF;
		sC.addComponent(tF);

		tF = new TextField("Alt. Phone No.");
		// tF.setValue("+1804191152");
		tFSANo = tF;
		sC.addComponent(tF);

		tF = new TextField("E-mail Address");
		// tF.setValue("pkigozi@swifta.com");
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
		// tF.setValue("Yusuf Lule Rd.");
		tFStreet = tF;
		tFStreet.setRequired(true);
		physicalC.addComponent(tF);

		tF = new TextField("Postal Code");
		// tF.setValue("23");
		tFPostalCode = tF;
		physicalC.addComponent(tF);

		tF = new TextField("City");
		// tF.setValue("Kampala");
		tFCity = tF;
		tFCity.setRequired(true);
		physicalC.addComponent(tF);

		tF = new TextField("Province");
		// tF.setValue("Central");
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

		Set<Entry<Integer, String>> set = profToID.entrySet();
		for (Entry<Integer, String> e : set) {
			comboHierarchy.addItem(e.getKey());
			comboHierarchy.setItemCaption(e.getKey(), e.getValue());
		}

		// comboHierarchy.select(1);
		comboProfile = comboHierarchy;
		comboProfile.setRequired(true);
		cAcc.addComponent(comboHierarchy);

		final VerticalLayout cLBody = new VerticalLayout();

		tF = new TextField("Username");
		// tF.setValue("Livepwndz");
		tFUN = tF;
		tFUN.setRequired(true);
		cLBody.addComponent(tF);

		tF = new TextField("MSISDN");
		// tF.setValue("+256774191152");
		tFMSISDN = tF;
		tFMSISDN.setRequired(true);
		cLBody.addComponent(tF);

		// / tF = new TextField("PIN");
		// / cLBody.addComponent(tF);

		tF = new TextField("Email");
		tFAccEmail = tF;
		tFAccEmail.setRequired(true);
		// tFAccEmail.setValue("ppounds1@gmail.com");
		cLBody.addComponent(tF);

		combo = new ComboBox("Bank Domain");
		combo.addItem("Heritage Bank");
		// combo.select("Heritage Bank");
		comboBDomain = combo;
		cLBody.addComponent(combo);

		combo = new ComboBox("Bank Code ID");
		combo.addItem("001");
		// combo.select("001");
		comboBID = combo;
		cLBody.addComponent(comboBID);

		tF = new TextField("Bank Account");
		// tF.setValue("00232333452315");
		tFBAcc = tF;
		// tFBAcc.setValidationVisible(true);
		// tFBAcc.addValidator(new NoNull());
		cLBody.addComponent(tF);

		combo.addItem(1);
		combo.setItemCaption(1, "US Dollars");
		// combo.select(1);
		comboCur = combo;
		cLBody.addComponent(combo);

		tF = new TextField("Clearing Number");
		// tF.setValue("00212");
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
		// combo.select(2);
		comboSecQn = combo;
		cLBody.addComponent(combo);

		tF = new TextField("Answer");
		// tF.setValue("Mrs. X");
		tFSecAns = tF;
		cLBody.addComponent(tF);

		CheckBox chk = new CheckBox("I accept the terms" + " and conditons.");
		chcTAndC = chk;
		chk.setStyleName("check_t_and_c");
		chk.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// Notification.show(event.getProperty().getValue().toString());

			}

		});

		comboCountry.addFocusListener(new FocusListener() {

			private static final long serialVersionUID = -5162384967736354225L;

			@Override
			public void focus(FocusEvent event) {
				if (isCSelected)
					return;
				Set<Entry<Integer, String>> es = (Set<Entry<Integer, String>>) getCountries()
						.entrySet();
				if (es.size() == 0)
					return;
				Iterator<Entry<Integer, String>> itr = es.iterator();
				comboCountry.setNullSelectionAllowed(false);
				while (itr.hasNext()) {
					Entry<Integer, String> e = (Entry<Integer, String>) itr
							.next();
					comboCountry.addItem(e.getKey());
					comboCountry.setItemCaption(e.getKey(), e.getValue());
				}

				comboCountry.select(null);

				isCSelected = true;

			}

		});

		comboCountry.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -404551290095133508L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				comboState.removeAllItems();
				comboLG.removeAllItems();

				if (comboCountry.getValue() == null)
					return;

				Set<Entry<Integer, String>> es = (Set<Entry<Integer, String>>) getStates(
						Integer.valueOf(comboCountry.getValue().toString()))
						.entrySet();

				if (es.isEmpty()) {
					return;
				}

				Iterator<Entry<Integer, String>> itr = es.iterator();
				while (itr.hasNext()) {
					Entry<Integer, String> e = itr.next();
					comboState.addItem(e.getKey());
					comboState.setItemCaption(e.getKey(), e.getValue());
				}

				comboState.select(null);

			}

		});

		comboState.addFocusListener(new FocusListener() {

			private static final long serialVersionUID = 892516817835461278L;

			@Override
			public void focus(FocusEvent event) {
				Object c = comboCountry.getValue();

				if (c == null) {
					Notification.show("Please select country first",
							Notification.Type.WARNING_MESSAGE);
					comboCountry.focus();
					return;

				}

			}

		});

		comboState.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 8849241310354979908L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				comboLG.removeAllItems();
				if (comboState.getValue() == null)
					return;
				Set<Entry<Integer, String>> esl = (Set<Entry<Integer, String>>) getLGs(
						Integer.valueOf(comboState.getValue().toString()))
						.entrySet();
				if (esl.isEmpty()) {
					return;
				}

				Iterator<Entry<Integer, String>> itrl = esl.iterator();
				while (itrl.hasNext()) {
					Entry<Integer, String> e = itrl.next();
					comboLG.addItem(e.getKey());
					comboLG.setItemCaption(e.getKey(), e.getValue());
				}

			}

		});

		comboLG.addFocusListener(new FocusListener() {

			private static final long serialVersionUID = 8925916817835461278L;

			@Override
			public void focus(FocusEvent event) {

				Object s = comboState.getValue();
				if (comboCountry.getValue() == null) {
					Notification.show("Please select country first",
							Notification.Type.WARNING_MESSAGE);
					comboCountry.focus();
					return;

				}

				if (s == null) {
					Notification.show("Please select state first",
							Notification.Type.WARNING_MESSAGE);
					comboState.focus();
					return;

				}

			}

		});

		HorizontalLayout cChk = new HorizontalLayout();
		cChk.setSizeUndefined();
		cChk.setMargin(new MarginInfo(true, false, true, false));
		cChk.addComponent(chk);
		cLBody.addComponent(cChk);

		final VerticalLayout cRBody = new VerticalLayout();
		String strNameCap = "Username";

		tF = new TextField(strNameCap);
		cRBody.addComponent(tF);

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
				String strResponse = "";

				String idtype = "";

				try {

					try {
						if (!isValidatorAdded)
							addValidators();
						validate();

					} catch (InvalidValueException e) {
						Notification.show("Message: ", e.getMessage(),
								Notification.Type.ERROR_MESSAGE);
						return;
					}

					String bacc = (tFBAcc.getValue() == null) ? "" : tFBAcc
							.getValue().toString();
					int bid = (comboBID.getValue() == null) ? 0 : (comboBID
							.getValue().toString().trim().isEmpty()) ? 0
							: Integer.valueOf(comboBID.getValue().toString());

					String bd = (comboBDomain.getValue() == null) ? ""
							: comboBDomain.getValue().toString();
					String clrno = (tFClrNo.getValue() == null) ? "" : tFClrNo
							.getValue().toString();
					String cur = (comboCur.getValue() == null) ? "" : comboCur
							.getValue().toString();
					String accEmail = (tFAccEmail.getValue() == null) ? ""
							: tFAccEmail.getValue().toString();
					String msisdn = (tFMSISDN.getValue() == null) ? ""
							: tFMSISDN.getValue().toString();
					int profid = (comboProfile.getValue() == null) ? 0
							: Integer.valueOf(comboProfile.getValue()
									.toString());
					String secQn = (comboSecQn.getValue() == null) ? ""
							: comboSecQn.getValue().toString();
					String secAns = (tFSecAns.getValue() == null) ? ""
							: tFSecAns.getValue().toString();
					String tAndC = (chcTAndC.getValue() == null) ? ""
							: chcTAndC.getValue().toString();
					String un = (tFUN.getValue() == null) ? "" : tFUN
							.getValue().toString();
					int country = (comboCountry.getValue() == null) ? 0
							: (comboCountry.getValue().toString().trim()
									.isEmpty()) ? 0
									: Integer.valueOf(comboCountry.getValue()
											.toString());
					Date dob = (dFDoB.getValue() == null) ? new Date()
							: (Date) dFDoB.getValue();
					String employer = (tFEmp.getValue() == null) ? "" : tFEmp
							.getValue().toString();
					String fn = (tFFN.getValue() == null) ? "" : tFFN
							.getValue().toString();
					String gender = (optSex.getValue() == null) ? "" : optSex
							.getItemCaption(optSex.getValue()).toString();
					int lang = (comboLang.getValue() == null) ? 0 : (comboLang
							.getValue().toString().trim().isEmpty()) ? 0
							: Integer.valueOf(comboLang.getValue().toString());
					String ln = (tFLN.getValue() == null) ? "" : tFLN
							.getValue().toString();
					int lgid = (comboLG.getValue() == null) ? 0 : (comboLG
							.getValue().toString().trim().isEmpty()) ? 0
							: Integer.valueOf(comboLG.getValue().toString());

					String mn = (tFMN.getValue() == null) ? "" : tFMN
							.getValue().toString();
					String occ = (tFOcc.getValue() == null) ? "" : tFOcc
							.getValue().toString();
					String pref = (comboPref.getValue() == null) ? ""
							: comboPref.getValue().toString();
					int stateid = (comboState.getValue() == null) ? 0
							: (comboState.getValue().toString().trim()
									.isEmpty()) ? 0 : Integer
									.valueOf(comboState.getValue().toString());
					String suff = (comboSuff.getValue() == null) ? ""
							: comboSuff.getValue().toString();
					String city = (tFCity.getValue() == null) ? "" : tFCity
							.getValue().toString();
					String pcode = (tFPostalCode.getValue() == null) ? ""
							: (tFPostalCode.getValue().isEmpty()) ? "000"
									: tFPostalCode.getValue().toString();
					String str = (tFStreet.getValue() == null) ? "" : tFStreet
							.getValue().toString();
					String prov = (tFProv.getValue() == null) ? "" : tFProv
							.getValue().toString();
					Date doe = (dFDoE.getValue() == null) ? new Date()
							: (Date) dFDoE.getValue();
					String idno = (tFIDNo.getValue() == null) ? "" : tFIDNo
							.getValue().toString();

					Date doi = (dFDoI.getValue() == null) ? new Date()
							: (Date) dFDoI.getValue();

					String issuer = (tFIssuer.getValue() == null) ? ""
							: tFIssuer.getValue().toString();
					String pem = (tFPEmail.getValue() == null) ? "" : tFPEmail
							.getValue().toString();
					String pmno = (tFPMNo.getValue() == null) ? "" : tFPMNo
							.getValue().toString();

					String pamno = (tFPANo.getValue() == null) ? "" : tFPANo
							.getValue().toString();
					String sem = (tFSEmail.getValue() == null) ? "" : tFSEmail
							.getValue().toString();
					String smno = (tFSMNo.getValue() == null) ? "" : tFSMNo
							.getValue().toString();
					String samno = (tFSANo.getValue() == null) ? "" : tFSANo
							.getValue().toString();

					// IdentificationType idtype =
					// ProvisioningStub.IdentificationType.Factory
					// .fromValue(comboIDType.getValue().toString());
					if (comboIDType.getValue() != null)
						if (comboIDType.getValue().toString()
								.equals("Passport Number")) {
							idtype = ProvisioningStub.IdentificationType.PASSP
									.toString();
							System.out.println("idtype>>>>>1 " + idtype);
						} else if (comboIDType
								.getValue()
								.toString()
								.equals("National Registration Identification Number")) {
							idtype = ProvisioningStub.IdentificationType.NRIN
									.toString();
							System.out.println("idtype>>>>>2 " + idtype);
						} else if (comboIDType.getValue().toString()
								.equals("Drivers License Number")) {
							idtype = ProvisioningStub.IdentificationType.DRLCS
									.toString();
							System.out.println("idtype>>>>>3 " + idtype);
						} else if (comboIDType.getValue().toString()
								.equals("Identification Card")) {
							idtype = ProvisioningStub.IdentificationType.IDCD
									.toString();
							System.out.println("idtype>>>>>4 " + idtype);
						} else if (comboIDType.getValue().toString()
								.equals("Employer Identification Number")) {
							idtype = ProvisioningStub.IdentificationType.EMID
									.toString();
						}

						else
							idtype = "";

					System.out.println("idtype>>>>> " + idtype);

					System.out.println("idtype>>>>> "
							+ ProvisioningStub.IdentificationType.PASSP
									.toString());

					strResponse = ums.registerUser(bacc, bid, bd, clrno, cur,
							accEmail, msisdn, profid, secQn, secAns, tAndC, un,
							country, dob, employer, fn, gender, lang, ln, lgid,
							mn, occ, pref, stateid, suff, city, pcode, str,
							prov, doe, idno, idtype, doi, issuer, pem, pmno,
							pamno, sem, smno, samno);

				} catch (Exception e) {
					e.printStackTrace();
					Notification.show("Response: ", e.getMessage(),
							Notification.Type.ERROR_MESSAGE);
					return;
				}

				if (strResponse.contains("completed")
						&& strResponse.contains("successful")) {
					NotifCustom.show("Message: ", strResponse);
					reset();
				} else {
					Notification.show("Response: " + strResponse,
							Notification.Type.ERROR_MESSAGE);
				}

			}
		});

		btnReset.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 3212854064282339617L;

			@Override
			public void buttonClick(ClickEvent event) {

				reset();

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

	private HashMap<Integer, String> getCountries() {
		HashMap<Integer, String> c = new HashMap<>();
		String qx = "SELECT countryname as cname, countryid as cid FROM "
				+ MatsWebPortalUI.conf.dbin + ".country;";
		// String Uname = "psaproduser";
		// String Pword = "psaproduser@2015";
		String drivers = "com.mysql.jdbc.Driver";
		try {
			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			Connection conn = DriverManager.getConnection(
					MatsWebPortalUI.conf.DB, MatsWebPortalUI.conf.UN,
					MatsWebPortalUI.conf.PW);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(qx);

			while (rs.next()) {
				c.put(rs.getInt("cid"), rs.getString("cname"));

			}

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {

			e.printStackTrace();
			Notification.show("DB Connection",
					"Error Establishing DBConnection:  " + e.getMessage(),
					Notification.Type.ERROR_MESSAGE);
		}

		return c;

	}

	private HashMap<Integer, String> getStates(int cid) {

		HashMap<Integer, String> s = new HashMap<>();
		String qx = "SELECT state as s, countrystateid as sid FROM "
				+ MatsWebPortalUI.conf.dbin
				+ ".countrystate where countryid = " + cid + ";";
		String Uname = "psaproduser";
		String Pword = "psaproduser@2015";
		String drivers = "com.mysql.jdbc.Driver";
		try {
			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			Connection conn = DriverManager.getConnection(
					MatsWebPortalUI.conf.DB, MatsWebPortalUI.conf.UN,
					MatsWebPortalUI.conf.PW);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(qx);

			while (rs.next()) {
				s.put(rs.getInt("sid"), rs.getString("s"));

			}

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show("DB Connection",
					"Error Establishing DBConnection:  " + e.getMessage(),
					Notification.Type.ERROR_MESSAGE);
		}

		return s;

	}

	private HashMap<Integer, String> getLGs(int sid) {

		HashMap<Integer, String> s = new HashMap<>();
		// = 1
		String qx = "SELECT countrystatelgaid as lgid, lganame as lg FROM "
				+ MatsWebPortalUI.conf.dbin
				+ ".countrystatelga where countrystateid = " + sid + ";";

		String drivers = "com.mysql.jdbc.Driver";
		try {
			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			Connection conn = DriverManager.getConnection(
					MatsWebPortalUI.conf.DB, MatsWebPortalUI.conf.UN,
					MatsWebPortalUI.conf.PW);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(qx);

			while (rs.next()) {
				s.put(rs.getInt("lgid"), rs.getString("lg"));

			}

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show("DB Connection",
					"Error Establishing DBConnection:  " + e.getMessage(),
					Notification.Type.ERROR_MESSAGE);
		}

		return s;

	}

	private class NoNull implements Validator {
		private static final long serialVersionUID = -5634103226093178664L;

		@Override
		public void validate(Object value) throws InvalidValueException {
			if (value == null || value.toString().isEmpty()) {

				throw new InvalidValueException(
						"Please fill in all fields marked with (!).");

			}

		}

	}

	private void reset() {

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

		cal = Calendar.getInstance();
		Date date = cal.getTime();

		dFDoB.setValue(date);
		dFDoB.setValue(date);
		dFDoI.setValue(date);
		dFDoE.setValue(date);
		optSex.select(null);

		Notification.show("Fields reset.");

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
		 * arrLAllFields.add(comboLang); arrLAllFields.add(comboBDomain);
		 * arrLAllFields.add(comboBID); arrLAllFields.add(comboCur);
		 * arrLAllFields.add(comboSecQn); arrLAllFields.add(comboProfile);
		 * arrLAllFields.add(comboIDType);
		 * 
		 * arrLAllFields.add(dFDoB); arrLAllFields.add(dFDoI);
		 * arrLAllFields.add(dFDoE); arrLAllFields.add(optSex);
		 */

	}

	private void addValidators() {

		// tFBAcc.addValidator(new NoNull());
		// tFBAcc.validate();
		tFFN.setRequired(false);
		tFMN.setRequired(false);
		tFLN.setRequired(false);
		tFOcc.setRequired(false);
		// tFEmp.setRequired(false);
		tFUN.setRequired(false);
		tFMSISDN.setRequired(false);
		// tFBAcc.setRequired(false);
		tFAccEmail.setRequired(false);
		// tFClrNo.setRequired(false);
		// tFSecAns.setRequired(false);
		// tFPMNo.setRequired(false);
		// tFPANo.setRequired(false);
		// tFPEmail.setRequired(false);
		// tFPostalCode.setRequired(false);
		tFStreet.setRequired(false);
		tFCity.setRequired(false);
		// tFProv.setRequired(false);
		// tFSMNo.setRequired(false);
		// tFSANo.setRequired(false);
		// tFSEmail.setRequired(false);
		// tFIssuer.setRequired(false);
		tFIDNo.setRequired(false);

		// chcTAndC.setRequired(false);
		// comboPref.setRequired(false);

		// comboSuff.setRequired(false);
		comboState.setRequired(false);
		comboLG.setRequired(false);
		comboCountry.setRequired(false);
		comboLang.setRequired(false);
		// comboBDomain.setRequired(false);
		// comboBID.setRequired(false);
		// comboCur.setRequired(false);
		// comboSecQn.setRequired(false);
		comboProfile.setRequired(false);
		comboIDType.setRequired(false);

		// dFDoB.setRequired(false);
		dFDoI.setRequired(false);
		dFDoE.setRequired(false);
		optSex.setRequired(false);

		tFFN.addValidator(new NoNull());
		tFMN.addValidator(new NoNull());
		tFLN.addValidator(new NoNull());
		tFOcc.addValidator(new NoNull());
		// tFEmp.addValidator(new NoNull());
		tFUN.addValidator(new NoNull());
		tFMSISDN.addValidator(new NoNull());
		// tFBAcc.addValidator(new NoNull());
		tFAccEmail.addValidator(new NoNull());
		// tFClrNo.addValidator(new NoNull());
		// tFSecAns.addValidator(new NoNull());
		// tFPMNo.addValidator(new NoNull());
		// tFPANo.addValidator(new NoNull());
		// tFPEmail.addValidator(new NoNull());
		// tFPostalCode.addValidator(new NoNull());
		tFStreet.addValidator(new NoNull());
		tFCity.addValidator(new NoNull());
		// tFProv.addValidator(new NoNull());
		// tFSMNo.addValidator(new NoNull());
		// tFSANo.addValidator(new NoNull());
		// tFSEmail.addValidator(new NoNull());
		// tFIssuer.addValidator(new NoNull());
		tFIDNo.addValidator(new NoNull());

		// chcTAndC.addValidator(new NoNull());
		// comboPref.addValidator(new NoNull());

		// comboSuff.addValidator(new NoNull());
		comboState.addValidator(new NoNull());
		comboLG.addValidator(new NoNull());
		comboCountry.addValidator(new NoNull());
		comboLang.addValidator(new NoNull());
		// comboBDomain.addValidator(new NoNull());
		// comboBID.addValidator(new NoNull());
		// comboCur.addValidator(new NoNull());
		// comboSecQn.addValidator(new NoNull());
		comboProfile.addValidator(new NoNull());
		comboIDType.addValidator(new NoNull());

		// dFDoB.addValidator(new NoNull());
		// dFDoI.addValidator(new NoNull());
		dFDoE.addValidator(new NoNull());
		dFDoI.addValidator(new NoNull());
		optSex.addValidator(new NoNull());
		isValidatorAdded = true;

	}

	private void validate() {

		tFFN.validate();
		tFMN.validate();
		tFLN.validate();
		tFOcc.validate();
		tFEmp.validate();
		tFUN.validate();
		tFMSISDN.validate();
		tFBAcc.validate();
		tFAccEmail.validate();
		tFClrNo.validate();
		tFSecAns.validate();
		tFPMNo.validate();
		tFPANo.validate();
		tFPEmail.validate();
		tFPostalCode.validate();
		tFStreet.validate();
		tFCity.validate();
		tFProv.validate();
		tFSMNo.validate();
		tFSANo.validate();
		tFSEmail.validate();
		tFIssuer.validate();
		tFIDNo.validate();

		chcTAndC.validate();
		comboPref.validate();

		comboSuff.validate();
		comboState.validate();
		comboLG.validate();
		comboCountry.validate();
		comboLang.validate();
		comboBDomain.validate();
		comboBID.validate();
		comboCur.validate();
		comboSecQn.validate();
		comboProfile.validate();
		comboIDType.validate();

		dFDoB.validate();
		dFDoI.validate();
		dFDoE.validate();
		optSex.validate();

	}

}
