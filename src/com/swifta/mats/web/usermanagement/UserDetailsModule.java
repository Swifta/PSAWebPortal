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
import java.util.List;
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
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class UserDetailsModule {
	public static boolean uDetailsEditStatus = false;
	private FormLayout cUPersonalDetails;
	private VerticalLayout cUDetails;
	private HorizontalLayout cDetailsAndOperations;
	private HorizontalLayout cP;
	private HorizontalLayout cA;
	private HorizontalLayout prevL;
	private boolean isfromsub = false;

	private HorizontalLayout cLP;
	private HorizontalLayout cLA;

	private ComboBox combo;
	private HorizontalLayout cPerAccAuthInfo;

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
	private boolean isValidatorAdded = false;
	private boolean isCSelected = false;
	// private String curC = null;

	private Calendar cal = null;
	private List<Object> arrLAllFormFields;
	private List<Object> arrLAllEditableFields;
	private ArrayList<String> arrLTfEditableVals;

	private boolean boolEditStatus = false;
	private HorizontalLayout cBtnEditCancel;
	UserDetailsBackEnd bee;
	private Map<String, String> hm;

	public UserDetailsModule() {

	}

	private HorizontalLayout setDetailsForm(String strUID, String strAction) {
		cDetailsAndOperations = new HorizontalLayout();
		cDetailsAndOperations.setSizeUndefined();

		cUPersonalDetails = new FormLayout();
		cUPersonalDetails.setMargin(true);
		cUPersonalDetails.setSpacing(false);
		cUPersonalDetails.setStyleName("frm_details_personal_info");
		cUPersonalDetails.setSizeUndefined();
		cDetailsAndOperations.addComponent(cUPersonalDetails);

		/*
		 * TODO Do not Delete brother.
		 * 
		 * cUDetails = new VerticalLayout(); cUDetails.setMargin(new
		 * MarginInfo(false, true, true, true));
		 * 
		 * cUDetails.setStyleName("c_u_details"); cUDetails.setSizeUndefined();
		 * cUDetails.addComponent(new Label(" NO Data Available!"));
		 * 
		 * cUPersonalDetails.addComponent(cUDetails); uDetailsEditStatus =
		 * false;
		 */

		arrLAllFormFields = new ArrayList<Object>();

		final String btnEditId = "edit";

		// Holders of editable form components

		arrLAllEditableFields = new ArrayList<Object>();

		// Holders of editable form components original values to be
		// used for undoing/resetting the form.
		arrLTfEditableVals = new ArrayList<String>();

		final Button btnEdit = new Button();
		btnEdit.setId(btnEditId);
		btnEdit.setIcon(FontAwesome.EDIT);
		btnEdit.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		btnEdit.setStyleName("btn_link");

		final Button btnCancel = new Button();
		btnCancel.setId(btnEditId);
		btnCancel.setIcon(FontAwesome.UNDO);
		btnCancel.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		btnCancel.setStyleName("btn_link");
		btnCancel.setVisible(false);

		cBtnEditCancel = new HorizontalLayout();
		cBtnEditCancel.setSizeUndefined();
		cBtnEditCancel.addComponent(btnEdit);

		setData(strUID, strAction);
		return cDetailsAndOperations;
	}

	private void resetForm(List<Object> lAllEditableFields,
			ArrayList<String> arrLEV) {

		int iTf = 0;
		uDetailsEditStatus = false;
		// Notification.show(Integer.toString(arrTfVals.length)+" I have");
		for (iTf = 0; iTf < lAllEditableFields.size(); iTf++) {
			if (lAllEditableFields.get(iTf) instanceof TextField) {

				((TextField) lAllEditableFields.get(iTf)).setValue(arrLEV
						.get(iTf));
				((TextField) lAllEditableFields.get(iTf))
						.setStyleName(ValoTheme.BUTTON_BORDERLESS);
				((TextField) lAllEditableFields.get(iTf)).setReadOnly(true);

			} else if (lAllEditableFields.get(iTf) instanceof OptionGroup) {

				((OptionGroup) lAllEditableFields.get(iTf)).setValue(arrLEV
						.get(iTf));
				((OptionGroup) lAllEditableFields.get(iTf)).setReadOnly(true);

			} else if (lAllEditableFields.get(iTf) instanceof ComboBox) {

				((ComboBox) lAllEditableFields.get(iTf)).setValue(arrLEV
						.get(iTf));
				((ComboBox) lAllEditableFields.get(iTf)).setReadOnly(true);

			} else if (lAllEditableFields.get(iTf) instanceof PopupDateField) {

				// ((PopupDateField) f).setValue(new Date(arrLDfVals.get(iDf)));
				((PopupDateField) lAllEditableFields.get(iTf))
						.setReadOnly(true);

			}

		}

	}

	private void enableEditableFormFields(List<Object> arrLAllEditableFields) {
		uDetailsEditStatus = true;
		for (Object f : arrLAllEditableFields) {
			if (f instanceof TextField) {
				((TextField) f).removeStyleName(ValoTheme.BUTTON_BORDERLESS);
				((TextField) f).setReadOnly(false);
			} else if (f instanceof OptionGroup) {
				((OptionGroup) f).setReadOnly(false);

			} else if (f instanceof ComboBox) {
				((ComboBox) f).setReadOnly(false);
			} else if (f instanceof PopupDateField) {
				((PopupDateField) f).setReadOnly(false);
			}

		}

	}

	public VerticalLayout getUserDetailsContainer(String strUID,
			String strAction) {
		cUDetails = new VerticalLayout();
		cUDetails.setMargin(new MarginInfo(false, true, true, true));

		cUDetails.setStyleName("c_u_details");
		cUDetails.setSizeFull();

		VerticalLayout cCHeader = new VerticalLayout();
		cCHeader.setSizeUndefined();
		cCHeader.setStyleName("c_c_header");
		cCHeader.setMargin(new MarginInfo(false, true, false, false));
		String strCHeader;

		if (boolEditStatus) {
			strCHeader = "Edit Details of " + strUID;
		} else {
			strCHeader = "Showing Details of " + strUID;

		}

		Label lbCHeader = new Label(strCHeader);
		lbCHeader.setStyleName("label_c_header");
		lbCHeader.setSizeUndefined();
		cCHeader.addComponent(lbCHeader);

		cPerAccAuthInfo = new HorizontalLayout();
		cPerAccAuthInfo.setStyleName("c_per_acc_auth_info");
		cPerAccAuthInfo.setSizeUndefined();

		VerticalLayout cTabLike = null;

		cTabLike = getManageUserMenu();
		cTabLike.setSizeUndefined();

		cCHeader.addComponent(cTabLike);
		cUDetails.addComponent(cCHeader);
		cUDetails.setComponentAlignment(cCHeader, Alignment.MIDDLE_CENTER);

		cUDetails.addComponent(cPerAccAuthInfo);
		cUDetails.setComponentAlignment(cPerAccAuthInfo,
				Alignment.MIDDLE_CENTER);
		// cUDetails.setExpandRatio(cPerAccAuthInfo, 1.0f);

		HorizontalLayout cUDetailsAndOperations = setDetailsForm(strUID,

		strAction);

		cP = cUDetailsAndOperations;
		cPerAccAuthInfo.addComponent(cUDetailsAndOperations);

		return cUDetails;
	}

	private VerticalLayout getManageUserMenu() {

		VerticalLayout cManageUserMenu = new VerticalLayout();
		cManageUserMenu.setStyleName("c_u_manage_menu");
		cManageUserMenu.setSizeUndefined();

		HorizontalLayout cManageAndAddTab = new HorizontalLayout();

		final BtnTabLike btnPersonal = new BtnTabLike("Basic", null);
		btnPersonal.setStyleName("btn_tab_like btn_tab_like_active");
		btnPersonal.setEnabled(false);

		final BtnTabLike btnAccount = new BtnTabLike("Account", null);
		// BtnTabLike btnAuth = new BtnTabLike("Authentication", null);
		final BtnTabLike btnLog = new BtnTabLike("Log", null);
		cManageAndAddTab.addComponent(btnPersonal);
		cManageAndAddTab.addComponent(btnAccount);
		// cManageAndAddTab.addComponent(btnAuth);
		cManageAndAddTab.addComponent(btnLog);

		// arrLTabBtns.add(btnAuth);

		btnPersonal.setEnabled(false);

		ArrayList<HorizontalLayout> arrLSubTabs = new ArrayList<HorizontalLayout>();

		final HorizontalLayout cManUserSubMenu = getAddUserSubMenu(btnLog,
				btnPersonal, btnAccount);

		arrLSubTabs.add(cManUserSubMenu);

		cManageUserMenu.addComponent(cManageAndAddTab);
		cManageUserMenu.addComponent(cManUserSubMenu);

		btnPersonal.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				btnPersonal.setEnabled(false);
				btnPersonal.setStyleName("btn_tab_like btn_tab_like_active");

				btnAccount.setEnabled(true);
				btnAccount.setStyleName("btn_tab_like");

				if (!isfromsub) {
					cA = (HorizontalLayout) cPerAccAuthInfo.getComponent(0);
					cPerAccAuthInfo.replaceComponent(cA, cP);

					return;
				} else {
					prevL = (HorizontalLayout) cPerAccAuthInfo.getComponent(0);
					cPerAccAuthInfo.replaceComponent(prevL, cP);

					btnLog.setEnabled(true);
					btnLog.setStyleName("btn_tab_like");
					cManUserSubMenu.setStyleName("c_sub_menu_invisible");
					isfromsub = false;
					return;
				}

			}
		});

		btnAccount.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				btnAccount.setEnabled(false);
				btnAccount.setStyleName("btn_tab_like btn_tab_like_active");

				btnPersonal.setEnabled(true);
				btnPersonal.setStyleName("btn_tab_like");
				if (!isfromsub) {
					if (cA == null)
						cA = getADC();

					cP = (HorizontalLayout) cPerAccAuthInfo.getComponent(0);
					cPerAccAuthInfo.replaceComponent(cP, cA);

				} else {
					if (cA == null)
						cA = getADC();
					prevL = (HorizontalLayout) cPerAccAuthInfo.getComponent(0);
					cPerAccAuthInfo.replaceComponent(prevL, cA);

					isfromsub = false;
					btnLog.setEnabled(true);
					btnLog.setStyleName("btn_tab_like");
					cManUserSubMenu.setStyleName("c_sub_menu_invisible");
					return;
				}

			}
		});

		cManUserSubMenu.setSizeUndefined();

		return cManageUserMenu;

	}

	private HorizontalLayout getAddUserSubMenu(final BtnTabLike btnAddUser,
			final BtnTabLike btnPersonal, final BtnTabLike btnAccount) {
		final HorizontalLayout cAddUserSubMenu = new HorizontalLayout();
		cAddUserSubMenu.setStyleName("c_sub_menu_invisible");
		cAddUserSubMenu.setSizeUndefined();

		final BtnTabLike btnAct = new BtnTabLike("Account Activity Log", null);

		final BtnTabLike btnAcc = new BtnTabLike("Account Change Log", null);

		btnAct.setStyleName("btn_tab_like btn_tab_like_active");
		btnAct.setEnabled(false);

		cAddUserSubMenu.addComponent(btnAct);
		cAddUserSubMenu.addComponent(btnAcc);

		btnAddUser.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				cAddUserSubMenu.setStyleName("c_sub_menu_visible");
				isfromsub = true;
				btnAddUser.setEnabled(false);
				btnAddUser.setStyleName("btn_tab_like btn_tab_like_active");

				HorizontalLayout p = (HorizontalLayout) cPerAccAuthInfo
						.getComponent(0);

				if (!btnPersonal.isEnabled()) {
					btnPersonal.setEnabled(true);
					btnPersonal.setStyleName("btn_tab_like");

					cP = p;
					if (cLP == null && prevL == null) {
						btnAct.setEnabled(false);
						btnAct.setStyleName("btn_tab_like btn_tab_like_active");

						btnAcc.setEnabled(true);
						btnAcc.setStyleName("btn_tab_like");

						cLP = getAccLog();
						cPerAccAuthInfo.replaceComponent(p, cLP);
					} else {
						cPerAccAuthInfo.replaceComponent(p, prevL);
					}
					return;
				} else if (!btnAccount.isEnabled()) {

					btnAccount.setEnabled(true);
					btnAccount.setStyleName("btn_tab_like");
					cA = p;
					if (cLP == null && prevL == null) {
						btnAct.setEnabled(false);
						btnAct.setStyleName("btn_tab_like btn_tab_like_active");

						btnAcc.setEnabled(true);
						btnAcc.setStyleName("btn_tab_like");

						cLP = getActLog();
						cPerAccAuthInfo.replaceComponent(p, cLP);
					} else {
						cPerAccAuthInfo.replaceComponent(p, prevL);
					}
					return;

				}

			}
		});

		btnAcc.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				btnAcc.setEnabled(false);
				btnAcc.setStyleName("btn_tab_like btn_tab_like_active");

				btnAct.setEnabled(true);
				btnAct.setStyleName("btn_tab_like");

				cLA = (HorizontalLayout) cPerAccAuthInfo.getComponent(0);
				cPerAccAuthInfo.replaceComponent(cLA, cLP);

			}
		});

		btnAct.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				btnAct.setEnabled(false);
				btnAct.setStyleName("btn_tab_like btn_tab_like_active");

				btnAcc.setEnabled(true);
				btnAcc.setStyleName("btn_tab_like");
				if (cLA == null)
					cLA = getActLog();

				cLP = (HorizontalLayout) cPerAccAuthInfo.getComponent(0);
				cPerAccAuthInfo.replaceComponent(cLP, cLA);

			}
		});

		return cAddUserSubMenu;
	}

	private HorizontalLayout getAccLog() {
		HorizontalLayout c = new HorizontalLayout();
		c.setSizeFull();
		Label lb = new Label("No Data Available!");
		c.addComponent(lb);
		c.setMargin(true);
		c.setComponentAlignment(lb, Alignment.MIDDLE_CENTER);
		return c;
	}

	private HorizontalLayout getActLog() {
		HorizontalLayout c = new HorizontalLayout();
		c.setSizeFull();
		Label lb = new Label("No Data Available!");
		c.addComponent(lb);
		c.setComponentAlignment(lb, Alignment.MIDDLE_CENTER);
		c.setMargin(true);
		return c;
	}

	private void setData(String strUID, String strAction) {
		if (strAction.equals("view_details")) {
			VerticalLayout c = getUDContainer(strUID);
			cUPersonalDetails.addComponent(c);
		} else {
			VerticalLayout c = getEUDContainer();
			cUPersonalDetails.addComponent(c);

		}

	}

	private VerticalLayout getUDContainer(String strUID) {

		if (bee == null)
			bee = new UserDetailsBackEnd();

		hm = bee.getUD(strUID);

		String strProf = hm.get("Profile Type");

		VerticalLayout cAgentInfo = new VerticalLayout();
		cAgentInfo.setMargin(new MarginInfo(true, true, true, true));
		cAgentInfo.setStyleName("c_details_test");
		cAgentInfo.setSizeUndefined();

		VerticalLayout cBasic = new VerticalLayout();
		// cBasic.setSpacing(true);
		// cBasic.setSpacing(true);
		Label lbB = new Label("General");
		lbB.setStyleName("lb_frm_add_user");
		cBasic.addComponent(lbB);
		String cap = "First Name";
		TextField tF = new TextField(cap);
		tFFN = tF;
		tFFN.setRequired(true);
		tF.setValue(hm.get(cap));

		addDatum("Username", hm.get("Username"), cBasic);
		addDatum("Profile", strProf, cBasic);

		addDatum("Full Name", hm.get("Full Name"), cBasic);
		addDatum("First Name", hm.get("First Name"), cBasic);

		// cBasic.addComponent(tF);

		tF = new TextField("Middle Name");

		tF.setValue("Pwndz");
		tFMN = tF;
		tFMN.setRequired(true);
		addDatum("Middle Name", hm.get("Middle Name"), cBasic);
		// cBasic.addComponent(lb);

		tF = new TextField("Last Name");
		tF.setValue("Kigozi");
		tFLN = tF;
		tFLN.setRequired(true);

		addDatum("Last Name", hm.get("Last Name"), cBasic);

		OptionGroup opt = new OptionGroup("Gender");

		opt.addItem("FEMALE");
		// opt.setItemCaption(1, "Female");

		opt.addItem("MALE");
		// opt.setItemCaption(2, "Male");
		opt.select("MALE");
		optSex = opt;
		optSex.setRequired(true);

		addDatum("Gender", hm.get("Gender"), cBasic);

		ComboBox combo = new ComboBox("Prefix");
		combo.addItem("Mr. ");
		combo.addItem("Mrs. ");
		combo.addItem("Dr. ");
		combo.addItem("Eng. ");
		combo.addItem("Prof. ");
		comboPref = combo;
		comboPref.select("Eng. ");
		// cBasic.addComponent(combo);

		combo = new ComboBox("Suffix");
		combo.addItem("Ph.D");
		combo.addItem("M.B.A");
		combo.addItem("RA");
		combo.addItem("CISA ");
		combo.select("Ph.D");
		comboSuff = combo;

		// lb = new Label();
		// lb.setValue("Last Name: " + "Kigozi");
		// cBasic.addComponent(lb);
		// cBasic.addComponent(combo);

		combo = new ComboBox("Language");
		combo.addItem(1);
		combo.select(1);
		combo.setItemCaption(1, "en-US");
		combo.addItem(2);
		combo.setItemCaption(2, "en-UK");
		combo.addItem(3);
		combo.setItemCaption(3, "fr");
		comboLang = combo;
		comboLang.setRequired(true);

		addDatum("Language", "en-UK", cBasic);
		// cBasic.addComponent(combo);

		tF = new TextField("Occupation");
		tF.setValue("Software Engineer");
		tFOcc = tF;
		tFOcc.setRequired(true);

		addDatum("Occupation", hm.get("Occupation"), cBasic);

		// cBasic.addComponent(lb);
		// cBasic.addComponent(tF);

		tF = new TextField("Employer");
		tF.setValue("Swifta");
		tFEmp = tF;

		addDatum("Employer", hm.get("Employer"), cBasic);

		// cBasic.addComponent(lb);
		// cBasic.addComponent(tF);

		PopupDateField dF = new PopupDateField("DoB");
		cal = Calendar.getInstance();
		cal.set(1988, 11, 12);
		dF.setValue(cal.getTime());
		dFDoB = dF;

		addDatum("Date of Birth", hm.get("Date of Birth"), cBasic);
		// cBasic.addComponent(dF);

		combo = new ComboBox("Country");

		comboCountry = combo;
		comboCountry.setRequired(true);

		// cBasic.addComponent(combo);
		addDatum("Country", hm.get("Country"), cBasic);

		combo = new ComboBox("State");
		comboState = combo;
		comboState.setRequired(true);
		comboState.setNullSelectionAllowed(false);

		// cBasic.addComponent(combo);
		addDatum("State", hm.get("State"), cBasic);

		combo = new ComboBox("Local Government");

		// combo.addItem(1);
		// combo.setItemCaption(1, "Ca. LG");
		// combo.select(1);
		comboLG = combo;
		comboLG.setRequired(true);

		// cBasic.addComponent(lb);

		addDatum("Local Government", hm.get("Local Government"), cBasic);

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
		combo.select("Passport Number");
		comboIDType = combo;
		comboIDType.setRequired(true);

		cCompany.addComponent(lbC);
		// cCompany.setSpacing(true);

		addDatum("ID Type", hm.get("ID Type"), cCompany);

		tF = new TextField("ID No.");
		tF.setValue("001");
		tFIDNo = tF;
		tFIDNo.setRequired(true);

		addDatum("ID No.", hm.get("ID No."), cCompany);
		// cCompany.addComponent(lb);

		tF = new TextField("Issuer");
		tFIssuer = tF;
		tFIssuer.setValue("Republic of Uganda");

		// cCompany.addComponent(tF);
		addDatum("Issuer", hm.get("Issuer"), cCompany);

		dF = new PopupDateField("Issue Date");
		// cal = Calendar.getInstance();
		cal.set(12, 12, 12);
		dF.setValue(cal.getTime());
		dFDoI = dF;
		// cCompany.addComponent(dF);

		addDatum("Issue Date", hm.get("Issue Date"), cCompany);

		dF = new PopupDateField("Expiry Date");
		// cal = Calendar.getInstance();
		cal.set(14, 12, 12);
		dF.setValue(cal.getTime());
		dF.setValue(cal.getTime());
		dFDoE = dF;
		dFDoE.setRequired(true);
		dFDoE.setImmediate(true);

		// cCompany.addComponent(dF);
		addDatum("Expiry Date", hm.get("Expiry Date"), cCompany);
		cC.addComponent(cCompany);

		VerticalLayout pC = new VerticalLayout();
		// pC.setSpacing(true);
		lbC = new Label("Primary Contacts");
		HorizontalLayout cLbc = new HorizontalLayout();
		cLbc.setSizeUndefined();
		cLbc.setMargin(new MarginInfo(true, false, false, false));
		cLbc.addComponent(lbC);
		pC.addComponent(cLbc);

		tF = new TextField("Mobile Phone No.");
		tF.setValue("+256704191152");
		tFPMNo = tF;

		// pC.addComponent(tF);
		addDatum("Mobile Phone No.", hm.get("P-Mobile Phone No."), pC);

		tF = new TextField("Alt. Phone No.");
		tF.setValue("+1704191152");
		tFPANo = tF;
		// pC.addComponent(tF);

		addDatum("Alt. Phone No.", hm.get("P-Alt. Phone No."), pC);

		tF = new TextField("Email Address");
		tF.setValue("pwndz172@gmail.com");
		tFPEmail = tF;
		// pC.addComponent(tF);

		addDatum("Email Address", hm.get("Email"), pC);

		cC.addComponent(pC);

		VerticalLayout sC = new VerticalLayout();
		// sC.setSpacing(true);
		lbC = new Label("Secondary Contacts");
		cLbc = new HorizontalLayout();
		cLbc.setSizeUndefined();
		cLbc.setMargin(new MarginInfo(true, false, false, false));
		cLbc.addComponent(lbC);
		sC.addComponent(cLbc);

		tF = new TextField("Mobile Phone No.");
		tF.setValue("+256804191152");
		tFSMNo = tF;
		// sC.addComponent(tF);

		addDatum("Mobile Phone No.", hm.get("S-Mobile Phone No."), sC);

		tF = new TextField("Alt. Phone No.");
		tF.setValue("+1804191152");
		tFSANo = tF;

		// sC.addComponent(tF);

		addDatum("Alt. Phone No.", hm.get("S-Alt. Phone No."), sC);

		tF = new TextField("E-mail Address");
		tF.setValue("pkigozi@swifta.com");
		tFSEmail = tF;
		// sC.addComponent(tF);

		addDatum("Email Address", hm.get("Email"), sC);

		cC.addComponent(sC);

		VerticalLayout physicalC = new VerticalLayout();
		// physicalC.setSpacing(true);
		lbC = new Label("Physical Address");
		cLbc = new HorizontalLayout();
		cLbc.setSizeUndefined();
		cLbc.setMargin(new MarginInfo(true, false, false, false));
		cLbc.addComponent(lbC);
		physicalC.addComponent(cLbc);

		tF = new TextField("Street");
		tF.setValue("Yusuf Lule Rd.");
		tFStreet = tF;
		tFStreet.setRequired(true);
		// physicalC.addComponent(tF);

		addDatum("Street", hm.get("Street"), physicalC);

		tF = new TextField("Postal Code");
		tF.setValue("23");
		tFPostalCode = tF;
		// physicalC.addComponent(tF);

		addDatum("Postal Code", hm.get("Postal Code"), physicalC);

		tF = new TextField("City");
		tF.setValue("Kampala");
		tFCity = tF;
		tFCity.setRequired(true);
		// physicalC.addComponent(tF);
		addDatum("City", hm.get("City"), physicalC);

		tF = new TextField("Province");
		tF.setValue("Central");
		tFProv = tF;
		// physicalC.addComponent(tF);

		addDatum("Province", hm.get("Province"), physicalC);

		cC.addComponent(physicalC);
		cC.addComponent(cBtnEditCancel);

		// TODO Need to copy this for accounts

		/*
		 * VerticalLayout cAcc = new VerticalLayout(); Label lbAcc = new
		 * Label("Account"); lbAcc.setStyleName("lb_frm_add_user");
		 * cAcc.addComponent(lbAcc); ComboBox comboHierarchy = null;
		 * 
		 * comboHierarchy = new ComboBox("Profile");
		 * 
		 * comboHierarchy.addItem(1); comboHierarchy.setItemCaption(1,
		 * "MATS_ADMIN_USER_PROFILE"); comboHierarchy.select(1); comboProfile =
		 * comboHierarchy; /*comboProfile.setRequired(true); //
		 * cAcc.addComponent(comboHierarchy);
		 * 
		 * // addDatum("Profile", "MATS_ADMIN_USER_PROFILE", cAcc);
		 * 
		 * /* final VerticalLayout cLBody = new VerticalLayout();
		 * 
		 * tF = new TextField("Username"); tF.setValue("Livepwndz"); tFUN = tF;
		 * tFUN.setRequired(true); // cLBody.addComponent(tF);
		 * 
		 * addDatum("Username", "Livepwndz", cLBody);
		 * 
		 * tF = new TextField("MSISDN"); tF.setValue("+256774191152"); tFMSISDN
		 * = tF; tFMSISDN.setRequired(true); // cLBody.addComponent(tF);
		 * 
		 * addDatum("MSISDN", "+256774191152", cLBody);
		 * 
		 * // / tF = new TextField("PIN"); // / cLBody.addComponent(tF);
		 * 
		 * tF = new TextField("Email"); tFAccEmail = tF;
		 * tFAccEmail.setRequired(true);
		 * tFAccEmail.setValue("ppounds1@gmail.com"); //
		 * cLBody.addComponent(tF); addDatum("Email", "ppounds1@gmail.com",
		 * cLBody);
		 * 
		 * combo = new ComboBox("Bank Domain"); combo.addItem("Stanbic Bank");
		 * combo.select("Stanbic Bank"); comboBDomain = combo; //
		 * cLBody.addComponent(combo); addDatum("Bank Domain", "Heritage Bank",
		 * cLBody);
		 * 
		 * combo = new ComboBox("Bank Code ID"); combo.addItem("001");
		 * combo.select("001"); comboBID = combo; // cLBody.addComponent(combo);
		 * addDatum("Bank Code ID", "001", cLBody);
		 * 
		 * tF = new TextField("Bank Account"); tF.setValue("00232333452315");
		 * tFBAcc = tF; // tFBAcc.setValidationVisible(true); //
		 * tFBAcc.addValidator(new NoNull()); // cLBody.addComponent(tF);
		 * addDatum("Bank Account", "00122269066745682", cLBody);
		 * 
		 * combo.addItem(1); combo.setItemCaption(1, "US Dollars");
		 * combo.select(1); comboCur = combo; // cLBody.addComponent(combo);
		 * 
		 * addDatum("Currency", "US Dollars", cLBody);
		 * 
		 * tF = new TextField("Clearing Number"); tF.setValue("00212"); tFClrNo
		 * = tF; // cLBody.addComponent(tF); addDatum("Clearing No. ", "00212",
		 * cLBody);
		 * 
		 * String strNameCap = "Username";
		 * 
		 * tF = new TextField(strNameCap);
		 * 
		 * HorizontalLayout cAccBody = new HorizontalLayout();
		 * cAccBody.addComponent(cLBody);
		 * 
		 * cLBody.setStyleName("c_body_visible");
		 * 
		 * cAcc.addComponent(cAccBody);
		 */

		// cBAndCAndAcc.addComponent(cAcc);

		// TODO End of copy....................z..............xxx

		cC.setMargin(new MarginInfo(false, true, false, true));
		cAgentInfo.addComponent(cBAndCAndAcc);

		return cAgentInfo;
	}

	private HorizontalLayout getADC() {

		// Notification.show(strTbName);

		VerticalLayout cAgentInfo = new VerticalLayout();
		cAgentInfo.setMargin(new MarginInfo(true, true, true, true));
		cAgentInfo.setStyleName("c_details_test");
		cAgentInfo.setSizeUndefined();

		VerticalLayout cAcc = new VerticalLayout();
		Label lbAcc = new Label("Account");
		lbAcc.setStyleName("lb_frm_add_user");
		cAcc.addComponent(lbAcc);
		ComboBox comboHierarchy = null;

		comboHierarchy = new ComboBox("Profile");

		final VerticalLayout cLBody = new VerticalLayout();
		// cLBody.setSpacing(true);

		comboHierarchy.addItem(1);
		comboHierarchy.setItemCaption(1, "MATS_ADMIN_USER_PROFILE");
		comboHierarchy.select(1);
		comboProfile = comboHierarchy;
		comboProfile.setRequired(true);
		// cAcc.addComponent(comboHierarchy);

		addDatum("Profile", hm.get("Profile Type"), cLBody);

		TextField tF = new TextField("Username");
		tF.setValue("Livepwndz");
		tFUN = tF;
		tFUN.setRequired(true); // cLBody.addComponent(tF);

		addDatum("Username", hm.get("Username"), cLBody);

		tF = new TextField("MSISDN");
		tF.setValue("+256774191152");
		tFMSISDN = tF;
		tFMSISDN.setRequired(true); // cLBody.addComponent(tF);

		addDatum("MSISDN", hm.get("MSISDN"), cLBody);

		tF = new TextField("PIN"); // / cLBody.addComponent(tF);

		tF = new TextField("Email");
		tFAccEmail = tF;
		tFAccEmail.setRequired(true);
		tFAccEmail.setValue("ppounds1@gmail.com"); //
		// cLBody.addComponent(tF);
		addDatum("Email", hm.get("Email"), cLBody);

		combo = new ComboBox("Bank Domain");
		combo.addItem("Stanbic Bank");
		combo.select("Stanbic Bank");
		comboBDomain = combo; //
		// cLBody.addComponent(combo);
		addDatum("Bank Domain", hm.get("Bank"), cLBody);

		combo = new ComboBox("Bank Code ID");
		combo.addItem("001");
		combo.select("001");
		comboBID = combo; // cLBody.addComponent(combo);
		addDatum("Bank Code ID", hm.get("Bank Code"), cLBody);

		tF = new TextField("Bank Account");
		tF.setValue("00232333452315");
		tFBAcc = tF; // tFBAcc.setValidationVisible(true); //
		tFBAcc.addValidator(new NoNull()); // cLBody.addComponent(tF);
		addDatum("Bank Account", hm.get("Bank Account"), cLBody);

		combo.addItem(1);
		combo.setItemCaption(1, "US Dollars");
		combo.select(1);
		comboCur = combo; // cLBody.addComponent(combo);

		addDatum("Currency", hm.get("Currency"), cLBody);

		tF = new TextField("Clearing Number");
		tF.setValue("00212");
		tFClrNo = tF; // cLBody.addComponent(tF);
		addDatum("Clearing No. ", hm.get("Clearing No."), cLBody);

		String strNameCap = "Username";

		tF = new TextField(strNameCap);

		HorizontalLayout cAccBody = new HorizontalLayout();
		cAccBody.addComponent(cLBody);
		cLBody.addComponent(cBtnEditCancel);

		cLBody.setStyleName("c_body_visible");

		cAcc.addComponent(cAccBody);
		cAgentInfo.addComponent(cAcc);

		// cBAndCAndAcc.addComponent(cAcc);
		HorizontalLayout c = new HorizontalLayout();
		c.addComponent(cAgentInfo);

		return c;

	}

	private VerticalLayout getEUDContainer() {

		if (cBtnEditCancel != null)
			cBtnEditCancel.setVisible(false);

		HashMap<Integer, String> profToID = new HashMap<>();

		profToID.put(1, "MATS_ADMIN_USER_PROFILE");
		profToID.put(3, "MATS_FINANCIAL_CONTROLLER_USER_PROFILE");
		profToID.put(4, "MATS_CUSTOMER_CARE_USER_PROFILE");
		profToID.put(6, "MATS_SUPER_AGENT_USER_PROFILE");
		profToID.put(7, "MATS_SUB_AGENT_USER_PROFILE");
		profToID.put(11, "MATS_DEALER_USER_PROFILE");
		profToID.put(15, "MATS_SERVICE_PROVIDER_USER_PROFILE");

		VerticalLayout cAgentInfo = new VerticalLayout();
		cAgentInfo.setMargin(new MarginInfo(true, true, true, true));
		cAgentInfo.setStyleName("c_details_test");
		cAgentInfo.setSizeUndefined();
		VerticalLayout cBasic = new VerticalLayout();
		Label lbB = new Label("Basic");
		lbB.setStyleName("lb_frm_add_user");
		cBasic.addComponent(lbB);

		TextField tF = new TextField("First Name");
		tFFN = tF;
		tFFN.setRequired(true);
		tF.setValue("Paul");
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		cBasic.addComponent(tF);

		tF = new TextField("Middle Name");
		tF.setValue("Pwndz");
		tFMN = tF;
		tFMN.setRequired(true);
		cBasic.addComponent(tF);

		tF = new TextField("Last Name");
		tF.setValue("Kigozi");
		tFLN = tF;
		tFLN.setRequired(true);
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		cBasic.addComponent(tF);

		OptionGroup opt = new OptionGroup("Gender");

		opt.addItem("FEMALE");
		// opt.setItemCaption(1, "Female");

		opt.addItem("MALE");
		// opt.setItemCaption(2, "Male");
		opt.select("MALE");
		optSex = opt;
		optSex.setRequired(true);
		arrLAllFormFields.add(opt);
		arrLAllEditableFields.add(opt);
		arrLTfEditableVals.add(opt.getValue().toString());
		cBasic.addComponent(opt);

		ComboBox combo = new ComboBox("Prefix");
		combo.addItem("Mr. ");
		combo.addItem("Mrs. ");
		combo.addItem("Dr. ");
		combo.addItem("Eng. ");
		combo.addItem("Prof. ");
		comboPref = combo;
		comboPref.select("Eng. ");

		arrLAllFormFields.add(combo);
		arrLAllEditableFields.add(combo);
		arrLTfEditableVals.add((combo.getValue() == null) ? "" : combo
				.getValue().toString());
		cBasic.addComponent(combo);

		combo = new ComboBox("Suffix");
		combo.addItem("Ph.D");
		combo.addItem("M.B.A");
		combo.addItem("RA");
		combo.addItem("CISA ");
		combo.select("Ph.D");
		comboSuff = combo;
		arrLAllFormFields.add(combo);
		arrLAllEditableFields.add(combo);
		arrLTfEditableVals.add((combo.getValue() == null) ? "" : combo
				.getValue().toString());
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
		comboLang.setRequired(true);
		arrLAllFormFields.add(combo);
		arrLAllEditableFields.add(combo);
		arrLTfEditableVals.add((combo.getValue() == null) ? "" : combo
				.getValue().toString());
		cBasic.addComponent(combo);

		tF = new TextField("Occupation");
		tF.setValue("Software Engineer");
		tFOcc = tF;
		tFOcc.setRequired(true);
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		cBasic.addComponent(tF);

		tF = new TextField("Employer");
		tF.setValue("Swifta");
		tFEmp = tF;
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		cBasic.addComponent(tF);

		PopupDateField dF = new PopupDateField("DoB");
		cal = Calendar.getInstance();
		cal.set(1988, 11, 12);
		dF.setValue(cal.getTime());
		dFDoB = dF;
		arrLAllFormFields.add(dF);
		arrLAllEditableFields.add(dF);
		arrLTfEditableVals.add(dF.getValue().toString());
		cBasic.addComponent(dF);

		combo = new ComboBox("Country");

		comboCountry = combo;
		comboCountry.setRequired(true);
		cBasic.addComponent(combo);

		combo = new ComboBox("State");
		comboState = combo;
		comboState.setRequired(true);
		comboState.setNullSelectionAllowed(false);
		arrLAllFormFields.add(combo);
		arrLAllEditableFields.add(combo);
		arrLTfEditableVals.add((combo.getValue() == null) ? "" : combo
				.getValue().toString());
		cBasic.addComponent(combo);

		combo = new ComboBox("Local Government");
		// combo.addItem(1);
		// combo.setItemCaption(1, "Ca. LG");
		// combo.select(1);
		comboLG = combo;
		comboLG.setRequired(true);
		arrLAllFormFields.add(combo);
		arrLAllEditableFields.add(combo);
		arrLTfEditableVals.add((combo.getValue() == null) ? "" : combo
				.getValue().toString());
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
		combo.select("Passport Number");
		comboIDType = combo;
		comboIDType.setRequired(true);

		arrLAllFormFields.add(combo);
		arrLAllEditableFields.add(combo);
		arrLTfEditableVals.add((combo.getValue() == null) ? "" : combo
				.getValue().toString());
		cCompany.addComponent(combo);

		tF = new TextField("ID No.");
		tF.setValue("001");
		tFIDNo = tF;
		tFIDNo.setRequired(true);
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		cCompany.addComponent(tF);

		tF = new TextField("Issuer");
		tFIssuer = tF;
		tFIssuer.setValue("Republic of Uganda");
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		cCompany.addComponent(tF);

		dF = new PopupDateField("Issue Date");
		// cal = Calendar.getInstance();
		cal.set(12, 12, 12);
		dF.setValue(cal.getTime());
		dFDoI = dF;
		arrLAllFormFields.add(dF);
		arrLAllEditableFields.add(dF);
		arrLTfEditableVals.add(dF.getValue().toString());
		cCompany.addComponent(dF);

		dF = new PopupDateField("Expiry Date");
		// cal = Calendar.getInstance();
		cal.set(14, 12, 12);
		dF.setValue(cal.getTime());
		dF.setValue(cal.getTime());
		dFDoE = dF;
		dFDoE.setRequired(true);
		dFDoE.setImmediate(true);
		arrLAllFormFields.add(dF);
		arrLAllEditableFields.add(dF);
		arrLTfEditableVals.add(dF.getValue().toString());
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
		tF.setValue("+256704191152");
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		tFPMNo = tF;

		pC.addComponent(tF);

		tF = new TextField("Alt. Phone No.");
		tF.setValue("+1704191152");
		tFPANo = tF;
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		pC.addComponent(tF);

		tF = new TextField("Email Address");
		tF.setValue("pwndz172@gmail.com");
		tFPEmail = tF;
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
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
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		sC.addComponent(tF);

		tF = new TextField("Alt. Phone No.");
		tF.setValue("+1804191152");
		tFSANo = tF;
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		sC.addComponent(tF);

		tF = new TextField("E-mail Address");
		tF.setValue("pkigozi@swifta.com");
		tFSEmail = tF;
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
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
		tFStreet.setRequired(true);
		physicalC.addComponent(tF);

		tF = new TextField("Postal Code");
		tF.setValue("23");
		tFPostalCode = tF;
		physicalC.addComponent(tF);

		tF = new TextField("City");
		tF.setValue("Kampala");
		tFCity = tF;
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		tFCity.setRequired(true);
		physicalC.addComponent(tF);

		tF = new TextField("Province");
		tF.setValue("Central");
		tFProv = tF;
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
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

		comboHierarchy.select(1);
		comboProfile = comboHierarchy;
		comboProfile.setRequired(true);
		arrLAllFormFields.add(comboProfile);
		arrLAllEditableFields.add(comboProfile);
		arrLTfEditableVals.add((comboProfile.getValue() == null) ? ""
				: comboProfile.getValue().toString());
		cAcc.addComponent(comboHierarchy);

		final VerticalLayout cLBody = new VerticalLayout();

		tF = new TextField("Username");
		tF.setValue("Livepwndz");
		tFUN = tF;
		tFUN.setRequired(true);
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		cLBody.addComponent(tF);

		tF = new TextField("MSISDN");
		tF.setValue("+256774191152");
		tFMSISDN = tF;
		tFMSISDN.setRequired(true);
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		cLBody.addComponent(tF);

		// / tF = new TextField("PIN");
		// / cLBody.addComponent(tF);

		tF = new TextField("Email");
		tFAccEmail = tF;
		tFAccEmail.setRequired(true);
		tFAccEmail.setValue("ppounds1@gmail.com");
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		cLBody.addComponent(tF);

		combo = new ComboBox("Bank Domain");
		combo.addItem("Stanbic Bank");
		combo.select("Stanbic Bank");
		comboBDomain = combo;
		arrLAllFormFields.add(combo);
		arrLAllEditableFields.add(combo);
		arrLTfEditableVals.add((combo.getValue() == null) ? "" : combo
				.getValue().toString());
		cLBody.addComponent(combo);

		combo = new ComboBox("Bank Code ID");
		combo.addItem("001");
		combo.select("001");
		comboBID = combo;
		arrLAllFormFields.add(combo);
		arrLAllEditableFields.add(combo);
		arrLTfEditableVals.add((combo.getValue() == null) ? "" : combo
				.getValue().toString());
		cLBody.addComponent(combo);

		tF = new TextField("Bank Account");
		tF.setValue("00232333452315");
		tFBAcc = tF;

		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		// tFBAcc.setValidationVisible(true);
		// tFBAcc.addValidator(new NoNull());
		cLBody.addComponent(tF);

		combo.addItem(1);
		combo.setItemCaption(1, "US Dollars");
		combo.select(1);
		comboCur = combo;
		arrLAllFormFields.add(combo);
		arrLAllEditableFields.add(combo);
		arrLTfEditableVals.add((combo.getValue() == null) ? "" : combo
				.getValue().toString());
		cLBody.addComponent(combo);

		tF = new TextField("Clearing Number");
		tF.setValue("00212");
		tFClrNo = tF;
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		cLBody.addComponent(tF);

		Label lbAccRec = new Label("Account Recovery");

		HorizontalLayout cLbAccRec = new HorizontalLayout();
		cLbAccRec.setSizeUndefined();
		cLbAccRec.setMargin(new MarginInfo(true, false, false, false));
		cLbAccRec.addComponent(lbAccRec);
		// cLBody.addComponent(cLbAccRec);

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
		arrLAllFormFields.add(combo);
		arrLAllEditableFields.add(combo);
		arrLTfEditableVals.add((combo.getValue() == null) ? "" : combo
				.getValue().toString());
		// cLBody.addComponent(combo);

		tF = new TextField("Answer");
		tF.setValue("Mrs. X");
		tFSecAns = tF;
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
		// cLBody.addComponent(tF);

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
		// cLBody.addComponent(cChk);

		final VerticalLayout cRBody = new VerticalLayout();
		String strNameCap = "Username";

		tF = new TextField(strNameCap);
		arrLAllFormFields.add(tF);
		arrLAllEditableFields.add(tF);
		arrLTfEditableVals.add(tF.getValue());
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

		final String btnSaveId = "save";
		final String btnEditId = "edit";

		final Button btnEdit = new Button();
		btnEdit.setIcon(FontAwesome.SAVE);
		btnEdit.setId(btnSaveId);
		btnEdit.setStyleName("btn_link");

		final Button btnCancel = new Button();
		btnCancel.setVisible(true);
		btnCancel.setIcon(FontAwesome.UNDO);
		btnCancel.setStyleName("btn_link");

		final HorizontalLayout cBtnSR = new HorizontalLayout();
		cBtnSR.addComponent(btnEdit);
		cBtnSR.addComponent(btnCancel);

		// cBtnEditCancel

		cAcc.addComponent(cBtnSR);

		btnEdit.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -935880570210949227L;

			@Override
			public void buttonClick(ClickEvent event) {

				/*
				 * Prepare all Editable fields (Entire form) for editing.
				 */
				if (event.getButton().getId().equals(btnEditId)) {

					/*
					 * By Default, btnCancel is not visible, until btnEdit is
					 * clicked. Only until then is it added and visible.
					 */

					if (!btnCancel.isVisible()) {
						event.getButton().setId(btnSaveId);
						event.getButton().setIcon(FontAwesome.SAVE);
						btnCancel.setVisible(true);
						cBtnSR.addComponent(btnCancel);
					}

					enableEditableFormFields(arrLAllEditableFields);

				} else {
					if (event.getButton().getId().equals(btnSaveId)) {
						/*
						 * 
						 * 
						 * 
						 * 
						 * commit (save) changes i.e, send changes back to the
						 * server.
						 */

						try {

							validateAndSave();
							// cUPersonalDetails.removeAllComponents();
							// cUPersonalDetails.addComponent(getUDContainer());
							// Notification.show("Details successfully saved.",
							// Notification.Type.WARNING_MESSAGE);

						} catch (Exception e) {
							// Notification.show("Hello");
							return;
						}

						// Remove undo button (btnCancel)
						btnCancel.setVisible(false);

						// Reset all Editable fields to readOnly after saving to
						// the server
						// disableEditableFields(arrLAllEditableFields);

						// Reset btnEdit id to btnIdEdit and caption(icon) to
						// FontAwesome.EDIT
						btnEdit.setId(btnEditId);
						btnEdit.setIcon(FontAwesome.EDIT);

						// Reset Edit status to false
						uDetailsEditStatus = false;

					}
				}

			}
		});

		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -8179030387969880920L;

			@Override
			public void buttonClick(ClickEvent event) {
				resetForm(arrLAllEditableFields, arrLTfEditableVals);
				btnEdit.setId(btnEditId);
				btnEdit.setIcon(FontAwesome.EDIT);
				btnCancel.setVisible(false);

			}
		});

		return cAgentInfo;
	}

	private HashMap<Integer, String> getCountries() {
		HashMap<Integer, String> c = new HashMap<>();
		String qx = "SELECT countryname as cname, countryid as cid FROM psadatasourcetest.country;";
		String Uname = "psaproduser";
		String Pword = "psaproduser@2015";
		String drivers = "com.mysql.jdbc.Driver";
		try {
			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			Connection conn = DriverManager.getConnection(
					MatsWebPortalUI.dbconn, Uname, Pword);

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
		// dFDoI.setRequired(false);
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

	private HashMap<Integer, String> getLGs(int sid) {

		HashMap<Integer, String> s = new HashMap<>();
		// = 1
		String qx = "SELECT countrystatelgaid as lgid, lganame as lg FROM psadatasourcetest.countrystatelga where countrystateid = "
				+ sid + ";";
		String Uname = "psatestuser";
		String Pword = "psatest_2015";
		String drivers = "com.mysql.jdbc.Driver";
		try {
			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://173.194.251.79:3306/psadatasource", Uname,
					Pword);

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

	private HashMap<Integer, String> getStates(int cid) {

		HashMap<Integer, String> s = new HashMap<>();
		String qx = "SELECT state as s, countrystateid as sid FROM psadatasourcetest.countrystate where countryid = "
				+ cid + ";";
		String Uname = "psatestuser";
		String Pword = "psatest_2015";
		String drivers = "com.mysql.jdbc.Driver";
		try {
			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://173.194.251.79:3306/psadatasource", Uname,
					Pword);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(qx);

			while (rs.next()) {
				s.put(rs.getInt("sid"), rs.getString("s"));

			}

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
			Notification.show("DB Connection",
					"Error Establishing DBConnection:  " + e.getMessage(),
					Notification.Type.ERROR_MESSAGE);
		}

		return s;

	}

	private void addDatum(String cap, String val, Object container) {

		Label lb = new Label();
		lb.setImmediate(true);
		lb.setStyleName("label_ud");
		lb.setContentMode(ContentMode.HTML);
		lb.setValue("<span class = 'label_cap_custom_ud'> " + cap
				+ ":  </span>" + "<div class = 'label_val_custom_ud'>" + val
				+ "</div>");
		if (container instanceof HorizontalLayout)
			((HorizontalLayout) container).addComponent(lb);
		else
			((VerticalLayout) container).addComponent(lb);

	}

	private void validateAndSave() {

		String strResponse = "";
		String idtype = "";
		UserManagementService ums = new UserManagementService();

		try {

			try {
				if (!isValidatorAdded)
					addValidators();
				validate();

			} catch (InvalidValueException e) {
				throw new InvalidValueException(e.getMessage());
			}

			String bacc = (tFBAcc.getValue() == null) ? "" : tFBAcc.getValue()
					.toString();
			int bid = (comboBID.getValue() == null) ? 0 : Integer
					.valueOf(comboBID.getValue().toString());
			String bd = (comboBDomain.getValue() == null) ? "" : comboBDomain
					.getValue().toString();
			String clrno = (tFClrNo.getValue() == null) ? "" : tFClrNo
					.getValue().toString();
			String cur = (comboCur.getValue() == null) ? "" : comboCur
					.getValue().toString();
			String accEmail = (tFAccEmail.getValue() == null) ? "" : tFAccEmail
					.getValue().toString();
			String msisdn = (tFMSISDN.getValue() == null) ? "" : tFMSISDN
					.getValue().toString();
			int profid = (comboProfile.getValue() == null) ? 0 : Integer
					.valueOf(comboProfile.getValue().toString());
			String secQn = (comboSecQn.getValue() == null) ? "" : comboSecQn
					.getValue().toString();
			String secAns = (tFSecAns.getValue() == null) ? "" : tFSecAns
					.getValue().toString();
			String tAndC = (chcTAndC.getValue() == null) ? "" : chcTAndC
					.getValue().toString();
			String un = (tFUN.getValue() == null) ? "" : tFUN.getValue()
					.toString();
			int country = (comboCountry.getValue() == null) ? 0 : Integer
					.valueOf(comboCountry.getValue().toString());
			Date dob = (dFDoB.getValue() == null) ? new Date() : (Date) dFDoB
					.getValue();
			String employer = (tFEmp.getValue() == null) ? "" : tFEmp
					.getValue().toString();
			String fn = (tFFN.getValue() == null) ? "" : tFFN.getValue()
					.toString();
			String gender = (optSex.getValue() == null) ? "" : optSex
					.getItemCaption(optSex.getValue()).toString();
			int lang = (comboLang.getValue() == null) ? 0 : Integer
					.valueOf(comboLang.getValue().toString());
			String ln = (tFLN.getValue() == null) ? "" : tFLN.getValue()
					.toString();
			int lgid = (comboLG.getValue() == null) ? 0 : Integer
					.valueOf(comboLG.getValue().toString());

			String mn = (tFMN.getValue() == null) ? "" : tFMN.getValue()
					.toString();
			String occ = (tFOcc.getValue() == null) ? "" : tFOcc.getValue()
					.toString();
			String pref = (comboPref.getValue() == null) ? "" : comboPref
					.getValue().toString();
			int stateid = (comboState.getValue() == null) ? 0 : Integer
					.valueOf(comboState.getValue().toString());
			String suff = (comboSuff.getValue() == null) ? "" : comboSuff
					.getValue().toString();
			String city = (tFCity.getValue() == null) ? "" : tFCity.getValue()
					.toString();
			String pcode = (tFPostalCode.getValue() == null) ? ""
					: tFPostalCode.getValue().toString();
			String str = (tFStreet.getValue() == null) ? "" : tFStreet
					.getValue().toString();
			String prov = (tFProv.getValue() == null) ? "" : tFProv.getValue()
					.toString();
			Date doe = (dFDoE.getValue() == null) ? new Date() : (Date) dFDoE
					.getValue();
			String idno = (tFIDNo.getValue() == null) ? "" : tFIDNo.getValue()
					.toString();

			Date doi = (dFDoI.getValue() == null) ? new Date() : (Date) dFDoI
					.getValue();

			String issuer = (tFIssuer.getValue() == null) ? "" : tFIssuer
					.getValue().toString();
			String pem = (tFPEmail.getValue() == null) ? "" : tFPEmail
					.getValue().toString();
			String pmno = (tFPMNo.getValue() == null) ? "" : tFPMNo.getValue()
					.toString();

			String pamno = (tFPANo.getValue() == null) ? "" : tFPANo.getValue()
					.toString();
			String sem = (tFSEmail.getValue() == null) ? "" : tFSEmail
					.getValue().toString();
			String smno = (tFSMNo.getValue() == null) ? "" : tFSMNo.getValue()
					.toString();
			String samno = (tFSANo.getValue() == null) ? "" : tFSANo.getValue()
					.toString();

			// IdentificationType idtype =
			// ProvisioningStub.IdentificationType.Factory
			// .fromValue(comboIDType.getValue().toString());

			if (comboIDType.getValue() != null)
				if (comboIDType.getValue().toString().equals("Passport Number")) {
					idtype = ProvisioningStub.IdentificationType.PASSP
							.toString();
					System.out.println("idtype>>>>>1 " + idtype);
				} else if (comboIDType.getValue().toString()
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
					+ ProvisioningStub.IdentificationType.PASSP.toString());

			strResponse = ums.registerUser(bacc, bid, bd, clrno, cur, accEmail,
					msisdn, profid, secQn, secAns, tAndC, un, country, dob,
					employer, fn, gender, lang, ln, lgid, mn, occ, pref,
					stateid, suff, city, pcode, str, prov, doe, idno, idtype,
					doi, issuer, pem, pmno, pamno, sem, smno, samno);

		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Response: ", e.getMessage(),
					Notification.Type.ERROR_MESSAGE);
			return;
		}

		if (strResponse.contains("completed")
				&& strResponse.contains("successful"))
			NotifCustom.show("Message: ", strResponse);
		else
			Notification.show("Response: " + strResponse,
					Notification.Type.ERROR_MESSAGE);

	}

}
