package com.swifta.mats.web.usermanagement;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.user.client.rpc.core.java.util.Collections;
import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.mats.web.utils.UserManagementService;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.EmailValidator;
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
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
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
	private TextField tFTerritory;

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
	private boolean isCSelected = false;
	private Component cxPC;
	private Component cxSC;
	private VerticalLayout cCompany;

	private ArrayList<Field<?>> arrLDFields = new ArrayList<>();
	private ArrayList<Field<?>> arrLGFields = new ArrayList<>();
	private ArrayList<Field<?>> arrLAllFields = new ArrayList<>();
	private ArrayList<TextField> arrLPAddr = new ArrayList<>();
	private ArrayList<Field<?>> arrLValidatable;

	private Button btnSave;

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
		arrLDFields.add(tF);
		arrLAllFields.add(tF);
		arrLGFields.add(tF);
		tFFN = tF;
		tF.setImmediate(true);
		tFFN.setRequired(true);
		cBasic.addComponent(tF);

		tF = new TextField("Middle Name");
		tFMN = tF;
		// tF.setImmediate(true);
		tFMN.setRequired(false);
		cBasic.addComponent(tF);
		arrLDFields.add(tF);
		arrLAllFields.add(tF);

		tF = new TextField("Last Name");
		tFLN = tF;
		tF.setImmediate(true);
		tFLN.setRequired(true);
		cBasic.addComponent(tF);
		arrLDFields.add(tF);
		arrLAllFields.add(tF);
		arrLGFields.add(tF);

		tF = new TextField("Territory");
		tFTerritory = tF;
		tFTerritory.setImmediate(true);
		tFTerritory.setRequired(true);
		tFTerritory.setVisible(false);
		cBasic.addComponent(tF);
		arrLDFields.add(tF);
		arrLAllFields.add(tF);
		// arrLGFields.add(tF);

		OptionGroup opt = new OptionGroup("Gender");

		opt.addItem("FEMALE");
		opt.addItem("MALE");
		optSex = opt;
		optSex.setRequired(true);
		optSex.setImmediate(true);
		cBasic.addComponent(opt);
		// arrLDFields.add(opt);
		arrLAllFields.add(opt);
		arrLGFields.add(opt);

		ComboBox combo = new ComboBox("Prefix");
		combo.addItem("Mr. ");
		combo.addItem("Mrs. ");
		combo.addItem("Dr. ");
		combo.addItem("Eng. ");
		combo.addItem("Prof. ");
		comboPref = combo;
		comboPref.select("Eng. ");
		//combo.addItems();
		cBasic.addComponent(combo);
		// arrLDFields.add(combo);
		arrLAllFields.add(combo);

		combo = new ComboBox("Suffix");
		combo.addItem("Ph.D");
		combo.addItem("M.B.A");
		combo.addItem("RA");
		combo.addItem("CISA ");
		// combo.select("Ph.D");
		comboSuff = combo;
		cBasic.addComponent(combo);
		// arrLDFields.add(combo);
		arrLAllFields.add(combo);

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
		comboLang.setImmediate(true);
		cBasic.addComponent(combo);
		// arrLDFields.add(combo);
		arrLAllFields.add(combo);
		arrLGFields.add(combo);

		tF = new TextField("Occupation");
		// tF.setValue("Software Engineer");
		tFOcc = tF;
		tFOcc.setRequired(true);
		tFOcc.setImmediate(true);
		cBasic.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);
		arrLGFields.add(tF);

		tF = new TextField("Employer");
		// tF.setValue("Swifta");
		tFEmp = tF;
		cBasic.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);

		PopupDateField dF = new PopupDateField("DoB");
		Calendar cal = Calendar.getInstance();
		cal.set(1988, 11, 12);
		dFDoB = dF;
		cBasic.addComponent(dF);
		// arrLDFields.add(dF);
		arrLAllFields.add(dF);

		combo = new ComboBox("Country");
		comboCountry = combo;
		comboCountry.setRequired(true);
		cBasic.addComponent(combo);
		arrLDFields.add(combo);
		arrLAllFields.add(combo);
		arrLGFields.add(combo);

		combo = new ComboBox("State");
		comboState = combo;
		comboState.setRequired(true);
		comboState.setNullSelectionAllowed(false);
		cBasic.addComponent(combo);
		arrLDFields.add(combo);
		arrLAllFields.add(combo);
		arrLGFields.add(combo);

		combo = new ComboBox("Local Government");
		comboLG = combo;
		comboLG.setRequired(true);
		cBasic.addComponent(combo);
		arrLDFields.add(combo);
		arrLAllFields.add(combo);
		arrLGFields.add(combo);

		VerticalLayout cC = new VerticalLayout();

		HorizontalLayout cBAndCAndAcc = new HorizontalLayout();
		cBAndCAndAcc.addComponent(cBasic);
		cBAndCAndAcc.addComponent(cC);

		cCompany = new VerticalLayout();
		Label lbC = new Label("Identification");
		lbC.setStyleName("lb_frm_add_user");

		combo = new ComboBox("ID Type");
		combo.addItem("Passport Number");
		combo.addItem("National Registration Identification Number");
		combo.addItem("Drivers License Number");
		combo.addItem("Identification Card");
		combo.addItem("Employer Identification Number");
		comboIDType = combo;
		comboIDType.setRequired(true);
		comboIDType.setImmediate(true);
		cCompany.addComponent(combo);
		// arrLDFields.add(combo);
		arrLAllFields.add(combo);
		arrLGFields.add(combo);

		tF = new TextField("ID No.");
		// tF.setValue("001");
		tFIDNo = tF;
		tFIDNo.setRequired(true);
		tFIDNo.setImmediate(true);
		cCompany.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);
		arrLGFields.add(tF);

		tF = new TextField("Issuer");
		tFIssuer = tF;
		cCompany.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);

		dF = new PopupDateField("Issue Date");
		// cal = Calendar.getInstance();
		cal.set(12, 12, 12);
		// dF.setValue(cal.getTime());
		dFDoI = dF;
		// cal.clear();

		cal = Calendar.getInstance();
		Date dToday = cal.getTime();

		cal.set(1970, 0, 1);
		Date dMin = cal.getTime();

		dFDoI.addValidator(new DateRangeValidator(
				"Invalid issue date. Please select a date Earlier/Today.",
				dMin, dToday, null));
		cCompany.addComponent(dF);
		// arrLDFields.add(dF);
		arrLAllFields.add(dF);
		arrLGFields.add(dF);

		dFDoI.setImmediate(true);

		dF = new PopupDateField("Expiry Date");
		cal.set(14, 12, 12);
		dFDoE = dF;
		DateRangeValidator drv = new DateRangeValidator("ID is Expired",
				dToday, null, null);
		dFDoE.addValidator(drv);

		dFDoI.setRequired(true);
		dFDoI.setImmediate(true);

		dFDoE.setRequired(true);
		dFDoE.setImmediate(true);

		cCompany.addComponent(dF);
		// arrLDFields.add(dF);
		arrLAllFields.add(dF);
		arrLGFields.add(dF);

		cC.addComponent(cCompany);

		VerticalLayout pC = new VerticalLayout();
		lbC = new Label("Primary Contacts");
		HorizontalLayout cLbc = new HorizontalLayout();
		cLbc.setSizeUndefined();
		cLbc.setMargin(new MarginInfo(true, false, false, false));
		cLbc.addComponent(lbC);
		pC.addComponent(cLbc);
		cxPC = pC;

		tF = new TextField("Mobile Phone No.");
		tFPMNo = tF;
		pC.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);

		tF = new TextField("Alt. Phone No.");
		tFPANo = tF;
		pC.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);

		tF = new TextField("Email Address");
		// tF.setValue("pwndz172@gmail.com");
		tFPEmail = tF;
		tFPEmail.addValidator(new EmailValidator("Invalid Email address."));
		tFPEmail.setImmediate(true);
		pC.addComponent(tF);
		cC.addComponent(pC);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);
		tFPEmail.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 6060653158010946535L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() == null
						|| event.getProperty().getValue().toString().isEmpty()) {
					arrLGFields.remove(tFPEmail);
				} else {
					arrLGFields.add(tFPEmail);
				}

			}

		});

		VerticalLayout sC = new VerticalLayout();
		lbC = new Label("Secondary Contacts");
		cLbc = new HorizontalLayout();
		cLbc.setSizeUndefined();
		cLbc.setMargin(new MarginInfo(true, false, false, false));
		cLbc.addComponent(lbC);
		// arrLDFields.add(lbC);
		// arrLAllFields.add(lbC);
		cxSC = sC;
		sC.addComponent(cLbc);

		tF = new TextField("Mobile Phone No.");
		tFSMNo = tF;
		sC.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);

		tF = new TextField("Alt. Phone No.");
		// tF.setValue("+1804191152");
		tFSANo = tF;
		sC.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);

		tF = new TextField("E-mail Address");
		tFSEmail = tF;
		tFSEmail.addValidator(new EmailValidator("Invalid Email Address."));
		tFSEmail.setImmediate(true);
		sC.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);

		tFSEmail.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 6060653158010946535L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() == null
						|| event.getProperty().getValue().toString().isEmpty()) {

					arrLGFields.remove(tFSEmail);
				} else {
					arrLGFields.add(tFSEmail);
				}

			}

		});

		cC.addComponent(sC);

		VerticalLayout physicalC = new VerticalLayout();
		lbC = new Label("Physical Address");
		cLbc = new HorizontalLayout();
		cLbc.setSizeUndefined();
		cLbc.setMargin(new MarginInfo(true, false, false, false));
		cLbc.addComponent(lbC);
		physicalC.addComponent(cLbc);

		// arrLDFields.add(lbC);
		// arrLAllFields.add(lbC);

		tF = new TextField("Postal Code");
		tFPostalCode = tF;
		physicalC.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);

		tF = new TextField("Street");
		// tF.setValue("Yusuf Lule Rd.");
		tFStreet = tF;
		tFStreet.setRequired(true);
		tFStreet.setImmediate(true);
		physicalC.addComponent(tF);
		arrLDFields.add(tF);
		arrLAllFields.add(tF);
		arrLPAddr.add(tF);

		tF = new TextField("Province");
		tFProv = tF;
		physicalC.addComponent(tF);
		arrLDFields.add(tF);
		arrLAllFields.add(tF);
		arrLPAddr.add(tF);

		tF = new TextField("City");
		tFCity = tF;
		tFCity.setRequired(true);
		tFCity.setImmediate(true);
		physicalC.addComponent(tF);
		arrLDFields.add(tF);
		arrLAllFields.add(tF);
		arrLPAddr.add(tF);

		cC.addComponent(physicalC);
		tFPostalCode.setImmediate(true);

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
		comboProfile.setImmediate(true);
		comboProfile.select(1);
		cAcc.addComponent(comboHierarchy);

		final VerticalLayout cLBody = new VerticalLayout();

		tF = new TextField("Username");
		// tF.setValue("Livepwndz");
		tFUN = tF;
		tFUN.setRequired(true);
		cLBody.addComponent(tF);
		arrLDFields.add(tF);
		arrLAllFields.add(tF);
		arrLGFields.add(tF);

		tF = new TextField("MSISDN");
		// tF.setValue("+256774191152");
		tFMSISDN = tF;
		tFMSISDN.setRequired(true);
		tFMSISDN.setImmediate(true);
		cLBody.addComponent(tF);
		arrLDFields.add(tF);
		arrLAllFields.add(tF);
		arrLGFields.add(tF);

		// / tF = new TextField("PIN");
		// / cLBody.addComponent(tF);

		tF = new TextField("Email");
		tFAccEmail = tF;
		tFAccEmail.addValidator(new EmailValidator("Invalid Email Address."));
		tFAccEmail.setRequired(true);
		tFAccEmail.setImmediate(true);
		cLBody.addComponent(tF);
		arrLDFields.add(tF);
		arrLAllFields.add(tF);
		arrLGFields.add(tF);

		combo = new ComboBox("Bank Domain");
		combo.addItem("Heritage Bank");
		// combo.select("Heritage Bank");
		comboBDomain = combo;
		cLBody.addComponent(combo);
		// arrLDFields.add(combo);
		arrLAllFields.add(combo);

		combo = new ComboBox("Bank Code ID");
		combo.addItem("001");
		// combo.select("001");
		comboBID = combo;
		cLBody.addComponent(comboBID);
		// arrLDFields.add(combo);
		arrLAllFields.add(combo);

		tF = new TextField("Bank Account");
		tFBAcc = tF;
		cLBody.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);

		combo = new ComboBox("Currency");
		combo.addItem(1);
		combo.setItemCaption(1, "US Dollars");
		comboCur = combo;
		cLBody.addComponent(combo);
		// arrLDFields.add(combo);
		arrLAllFields.add(combo);

		tF = new TextField("Clearing Number");
		tFClrNo = tF;
		cLBody.addComponent(tF);
		// arrLDFields.add(tF);
		arrLAllFields.add(tF);

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

		comboProfile.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				isValidatorAdded = false;

				/*
				 * COMMENTED OUT BECAUSE THE FEATURE FOR CHANGING THE UI BASED
				 * ON USER PROFILEL SELECTED HAS NOT BEEN FULLY TESTED if
				 * (comboProfile.getValue() != null &&
				 * comboProfile.getValue().equals(11)) {
				 * btnSave.setEnabled(true); for (Field<?> f : arrLAllFields) {
				 * f.setVisible(false); } for (Field<?> f : arrLDFields) {
				 * f.setVisible(true); f.setRequired(true); }
				 * 
				 * tFFN.setCaption("Station Name"); tFMN.setCaption("Zone");
				 * tFLN.setCaption("Sales Area");
				 */
				/*
				 * arrLPAddr.get(0).setCaption("Zone");
				 * arrLPAddr.get(1).setCaption("Sales Area");
				 * arrLPAddr.get(1).setRequired(true);
				 * arrLPAddr.get(2).setCaption("Territory");
				 */
				/*
				 * cxSC.setVisible(false); cxPC.setVisible(false);
				 * cCompany.setVisible(false); arrLValidatable = arrLDFields;
				 * reset(); // btnSave.setEnabled(false);
				 * 
				 * return; }
				 */
				btnSave.setEnabled(true);
				tFFN.setCaption("First Name");
				tFMN.setCaption("Middle Name");
				tFLN.setCaption("Last Name");

				for (Field<?> f : arrLAllFields) {
					f.setVisible(true);
					f.setRequired(false);
				}

				for (Field<?> f : arrLGFields) {
					f.setRequired(true);

				}

				/*
				 * arrLPAddr.get(0).setCaption("Street");
				 * arrLPAddr.get(1).setCaption("Province");
				 * arrLPAddr.get(2).setCaption("City");
				 */

				cxSC.setVisible(true);
				cxPC.setVisible(true);
				cCompany.setVisible(true);
				arrLValidatable = arrLGFields;
				reset();

			}

		});
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

		tFPostalCode.addValidator(new Validator() {

			private static final long serialVersionUID = 9193817369890607387L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value.toString().trim().isEmpty())
					return;

				try {
					Long.parseLong(tFPostalCode.getValue());
				} catch (Exception e) {
					tFPostalCode.focus();
					throw new InvalidValueException(
							"Only digits in Postal Code field.");

				}

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
					Entry<Integer, String> e = itr.next();
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

		btnSave = new Button("Save");
		btnSave.setIcon(FontAwesome.SAVE);
		btnSave.setStyleName("btn_link");

		Button btnReset = new Button("Reset");
		btnReset.setIcon(FontAwesome.UNDO);
		btnReset.setStyleName("btn_link");
		HorizontalLayout cBtnSR = new HorizontalLayout();
		cBtnSR.addComponent(btnSave);
		cBtnSR.addComponent(btnReset);

		cAcc.addComponent(cBtnSR);

		arrLValidatable = arrLGFields;

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
							addValidators(arrLValidatable);
						validate(arrLValidatable);

					} catch (InvalidValueException e) {
						Notification.show("Message: ", e.getMessage(),
								Notification.Type.ERROR_MESSAGE);
						return;
					}

					String bacc = (tFBAcc.getValue() == null) ? "" : tFBAcc
							.getValue().toString();
					int bid = (comboBID.getValue() == null) ? 0 : Integer
							.valueOf(comboBID.getValue().toString());

					String bd = (comboBDomain.getValue() == null) ? ""
							: comboBDomain.getValue().toString();
					String clrno = (tFClrNo.getValue() == null) ? "" : tFClrNo
							.getValue().toString();
					String cur = (comboCur.getValue() == null) ? "000"
							: comboCur.getValue().toString();
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
					Date dob = (dFDoB.getValue() == null) ? new Date() : dFDoB
							.getValue();
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
					Date doe = (dFDoE.getValue() == null) ? new Date() : dFDoE
							.getValue();
					String idno = (tFIDNo.getValue() == null) ? "" : tFIDNo
							.getValue().toString();

					Date doi = (dFDoI.getValue() == null) ? new Date() : dFDoI
							.getValue();

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

					System.out.println(e.getMessage());
					return;
				}

				if (strResponse.contains("completed")
						&& strResponse.contains("successful")) {
					NotifCustom.show("Message: ", strResponse);
					reset();
				} else {
					Notification.show("Response: " + strResponse,
							Notification.Type.ERROR_MESSAGE);

					System.out.println(strResponse);
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

		Field<?> f;

		NoNull(Field<?> f) {
			this.f = f;
		}

		@Override
		public void validate(Object value) throws InvalidValueException {

			if (value == null || value.toString().isEmpty()) {

				f.focus();

				throw new InvalidValueException(
						"Please fill in all fields marked with (!).");

			}

		}

	}

	private void reset() {

		tFPANo.setValue("");
		tFPEmail.setValue("");
		tFPostalCode.setValue("");

		tFProv.setValue("");
		tFSMNo.setValue("");
		tFSANo.setValue("");
		tFSEmail.setValue("");
		tFIssuer.setValue("");

		chcTAndC.setValue(false);
		comboPref.select(null);
		comboSuff.select(null);

		comboBDomain.select(null);
		comboBID.select(null);
		comboCur.select(null);
		comboSecQn.select(null);
		tFSecAns.setValue("");
		Date date = null;

		dFDoB.setValue(date);
		tFPEmail.setComponentError(null);
		tFSEmail.setComponentError(null);
		tFPostalCode.setComponentError(null);
		tFMN.setValue("");

		clearFields();

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
		tFPostalCode.setRequired(false);
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

		tFFN.addValidator(new NoNull(tFFN));
		// tFMN.addValidator(new NoNull(tFMN));
		tFLN.addValidator(new NoNull(tFLN));
		tFOcc.addValidator(new NoNull(tFOcc));
		// tFEmp.addValidator(new NoNull());
		tFUN.addValidator(new NoNull(tFUN));
		tFMSISDN.addValidator(new NoNull(tFMSISDN));
		// tFBAcc.addValidator(new NoNull());
		tFAccEmail.addValidator(new NoNull(tFAccEmail));
		// tFClrNo.addValidator(new NoNull());
		// tFSecAns.addValidator(new NoNull());
		// tFPMNo.addValidator(new NoNull());
		// tFPANo.addValidator(new NoNull());
		// tFPEmail.addValidator(new NoNull());
		// tFPostalCode.addValidator(new NoNull());
		tFStreet.addValidator(new NoNull(tFStreet));
		tFCity.addValidator(new NoNull(tFCity));
		// tFProv.addValidator(new NoNull());
		// tFSMNo.addValidator(new NoNull());
		// tFSANo.addValidator(new NoNull());
		// tFSEmail.addValidator(new NoNull());
		// tFIssuer.addValidator(new NoNull());
		tFIDNo.addValidator(new NoNull(tFIDNo));

		// chcTAndC.addValidator(new NoNull());
		// comboPref.addValidator(new NoNull());

		// comboSuff.addValidator(new NoNull());
		comboState.addValidator(new NoNull(comboState));
		comboLG.addValidator(new NoNull(comboLG));
		comboCountry.addValidator(new NoNull(comboLG));
		comboLang.addValidator(new NoNull(comboLang));
		// comboBDomain.addValidator(new NoNull());
		// comboBID.addValidator(new NoNull());
		// comboCur.addValidator(new NoNull());
		// comboSecQn.addValidator(new NoNull());
		comboProfile.addValidator(new NoNull(comboProfile));
		comboIDType.addValidator(new NoNull(comboIDType));

		// dFDoB.addValidator(new NoNull());
		// dFDoI.addValidator(new NoNull());
		dFDoE.addValidator(new NoNull(dFDoE));
		dFDoI.addValidator(new NoNull(dFDoI));
		optSex.addValidator(new NoNull(optSex));
		isValidatorAdded = true;

	}

	private void addValidators(ArrayList<Field<?>> arrL) {
		for (Field<?> f : arrL) {
			f.addValidator(new NoNull(f));
			f.setRequired(false);
		}
		isValidatorAdded = true;

	}

	private void validate(ArrayList<Field<?>> arrL) {
		for (Field<?> f : arrL)
			f.validate();

	}

	private void validate() {

		tFFN.validate();
		// tFMN.validate();
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
		tFPEmail.setComponentError(null);
		tFSEmail.setComponentError(null);
		tFPostalCode.validate();

		if (tFPEmail.getValue() != null
				&& !tFPEmail.getValue().trim().isEmpty())
			tFPEmail.validate();
		if (tFSEmail.getValue() != null
				&& !tFSEmail.getValue().trim().isEmpty())
			tFSEmail.validate();

	}

	private void clearFields() {
		isValidatorAdded = false;
		for (Field<?> f : arrLAllFields) {

			if (f instanceof TextField) {
				TextField tf = ((TextField) f);
				tf.setValue("");
				tf.setComponentError(null);

			}
			if (f instanceof ComboBox) {

				ComboBox combo = ((ComboBox) f);
				combo.setComponentError(null);
				combo.select(null);
				continue;
			}

			if (f instanceof OptionGroup) {

				OptionGroup opt = ((OptionGroup) f);
				opt.select(null);
				opt.setComponentError(null);

				continue;

			}
			if (f instanceof CheckBox) {

				CheckBox chk = ((CheckBox) f);
				chk.setValue(false);
				chk.setComponentError(null);

				continue;

			}
			if (f instanceof PopupDateField) {
				Date date = null;
				PopupDateField d = ((PopupDateField) f);
				d.setValue(date);
				d.setComponentError(null);

				continue;

			}
		}

	}

}
