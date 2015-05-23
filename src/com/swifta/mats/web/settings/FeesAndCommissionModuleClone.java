package com.swifta.mats.web.settings;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.Initializer;
import com.swifta.mats.web.usermanagement.BtnTabLike;
import com.swifta.mats.web.usermanagement.NotifCustom;
import com.swifta.mats.web.utils.CommissionService;
import com.swifta.mats.web.utils.FeeService;
import com.swifta.mats.web.utils.ReportingService;
import com.swifta.mats.web.utils.UserManagementService;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceCommission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceFees;
import com.swifta.sub.mats.reporting.DataServiceFault;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Commissionfee;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profile;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Servicefee;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ListenerMethod.MethodException;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class FeesAndCommissionModuleClone {

	private ArrayList<VerticalLayout> cArrLItemContent;
	private ArrayList<FieldGroup> arrLRangeFG;
	private ArrayList<FieldGroup> arrLMatFG;
	private boolean isTiered;
	private ArrayList<ArrayList<FieldGroup>> arrLAllFG;
	private HashMap<String, ArrayList<ArrayList<FieldGroup>>> hmAllFG;
	private String tabType = null;
	private String[] arrMatValues;
	private String[] arrModelValues;
	private String[] arrConValues;
	ArrayList<FieldGroup> prfg;
	ArrayList<FieldGroup> pmfg;
	FieldGroup otherfg;
	boolean isReset = false;

	private VerticalLayout cMain = new VerticalLayout();
	private HorizontalLayout cStage = new HorizontalLayout();
	private HorizontalLayout p;
	private HorizontalLayout cProfile;
	private HorizontalLayout cPerm;
	private HorizontalLayout prevL;
	private Window pop = new Window("Comfirm Fees Deletion");
	private boolean isfromsub;
	private boolean isProfileNameChanged = false;
	private ReportingService rs;

	private ComboBox comboAllFCOperatorsm;
	private ComboBox comboAllFeesOperators;
	private ComboBox comboAllCommissionOperators;
	private ComboBox comboProfiles;
	private Map<String, String> hmAllOperators;
	private Map<String, String> hmProfileTypes;
	private Map<String, String> hmThresholdTypes;
	private Map<String, String> hmTransactionTypes;
	private Map<String, Profile> hmProfIDs = new HashMap<>();

	private Map<String, LinkedHashSet<Servicefee>> hmServiceFees = new HashMap<>();
	private Map<String, LinkedHashSet<Commissionfee>> hmServiceCommission = new HashMap<>();
	private Map<String, String> hmInActivePerms = new HashMap<>();

	private TwinColSelect tcsInactiveAndActive;
	private Profile[] profiles;

	private OptionGroup optMultTrans;
	private ComboBox comboThresholds;
	private Button back;

	private HashMap<String, String> hmProfPermPermissions;
	private BtnTabLike btnProfiles;
	private BtnTabLike btnPermissions;

	private TreeMap<String, String> tmExistingTransactionTypes = new TreeMap<>();
	private ComboBox comboxTT;

	private ComboBox comboFeesTT;

	private ComboBox comboCommissionsTT;

	private HashMap<String, String> hmOp;
	private HashMap<String, Integer> hmOpx;
	private HashMap<String, Integer> hmTxType = new HashMap<>();

	private boolean ischanged = true;
	private VerticalLayout cCX;
	private Double dfamt;
	private Double dcamt;
	private NumberFormat nf;
	private DecimalFormatSymbols dfs;
	private boolean isEditFees = false;
	private Table tbFees;
	HashMap<String, FieldGroup> hmDFG;
	private static String lookedTab = null;
	private ComboBox comboTxType;

	public final static String COMMISSION = "Commission";
	public final static String FEES = "Fees";
	private static final boolean isExistingFeeConfiguration = false;

	private boolean isSettingsURL = false;
	private String curURL = null;
	private boolean isNewFeeConfig = false;
	private boolean isExistingFeeConfig = false;

	private Component cTempComponent = null;

	private Button btnEditFees = null;
	private Button btnCancelFees = null;
	private Button btnDeleteFees = null;

	private Button btnCancelCommissions = null;
	private Button btnDeleteCommissions = null;
	private Button btnEditCommissions = null;

	private boolean isCommission = false;

	private Map<String, String> hmExistingFees;

	private LinkedHashSet<String> setNoneTier = new LinkedHashSet<>();
	private LinkedHashMap<String, String[]> hmTiers = new LinkedHashMap<>();
	private LinkedHashMap<String, LinkedHashSet<String>> hmNewTiers = new LinkedHashMap<>();

	// None Tiers setupType, setupProperyid, commValue,
	// Tiers range, fcount, amount

	// private HorizontalLayout cjust;

	private boolean isOptTiered = true;
	private OptionGroup optSetupType;

	private TextField tFCommValue;

	FeesAndCommissionModuleClone(Button back,
			HashMap<String, String> hmProfPermPermissions) {

		// TODO Delete upto hmProfPermPermissions

		hmTiers.put("min", null);
		hmTiers.put("max", null);
		hmTiers.put("con", null);
		hmTiers.put("amt", null);

		hmNewTiers.put("min", null);
		hmNewTiers.put("max", null);
		hmNewTiers.put("con", null);
		hmNewTiers.put("amt", null);

		nf = NumberFormat.getCurrencyInstance(Locale.ROOT);
		dfs = new DecimalFormatSymbols();
		dfs.setCurrencySymbol("\u20A6");
		((DecimalFormat) nf).setDecimalFormatSymbols(dfs);

		hmTxType.put("CASHIN", 4);
		hmTxType.put("CASHOUT", 5);
		hmTxType.put("PAYMENT SEND", 6);
		hmTxType.put("PAYMENT RECEIVE", 7);
		hmTxType.put("3rd PARTY PAYMENT", 17);

		// comboOp = new ComboBox("Operator");
		comboTxType = new ComboBox("Transaction Type");
		comboTxType.addItem(4);
		comboTxType.setItemCaption(4, "CASHIN");
		comboTxType.addItem(5);
		comboTxType.setItemCaption(5, "CASHOUT");
		comboTxType.addItem(6);
		comboTxType.setItemCaption(6, "PAYMENT SEND");
		comboTxType.addItem(7);
		comboTxType.setItemCaption(7, "PAYMENT RECEIVE");
		comboTxType.addItem(17);
		comboTxType.setItemCaption(17, "3rd PARTY PAYMENT");
		comboTxType.select(4);
		hmAllFG = new HashMap<>();
		hmDFG = new HashMap<>();
		arrMatValues = new String[] { "PERCENT", "FIXED" };
		arrModelValues = new String[] { "TIERED", "NOTAPPLICABLE" };
		arrConValues = new String[] { "AMOUNT", "FEE" };

		hmOp = new HashMap<>();
		hmOpx = new HashMap<>();

		this.hmProfPermPermissions = hmProfPermPermissions;
		this.back = back;
		addMenu();
		HorizontalLayout cTemp = null;
		if (!btnProfiles.isEnabled()) {
			cTemp = getFeesC();
			cProfile = cTemp;
			isCommission = false;
		} else {
			cTemp = getCommissionsC();
			cPerm = cTemp;
			isCommission = true;
		}

		cStage.addComponent(cTemp);
		cMain.addComponent(cStage);
		cMain.setComponentAlignment(cStage, Alignment.TOP_CENTER);

	}

	private void addMenu() {

		VerticalLayout cMenu = new VerticalLayout();
		cMenu.setStyleName("c_u_manage_menu");
		cMenu.setSizeUndefined();

		HorizontalLayout cMainMenu = new HorizontalLayout();

		BtnTabLike btnTemp = null;

		btnProfiles = new BtnTabLike("Fees", null);
		btnPermissions = new BtnTabLike("Commission", null);
		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("edit_profile"))

				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("add_profile"))
				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("remove_profile"))
				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("add_threshold"))) {
			cMainMenu.addComponent(btnProfiles);
			btnTemp = btnProfiles;
		}

		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("remove_permissions"))
				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("set_permissions")))
			cMainMenu.addComponent(btnPermissions);

		if (btnTemp == null)
			btnTemp = btnPermissions;

		btnTemp.setStyleName("btn_tab_like btn_tab_like_active");
		btnTemp.setEnabled(false);

		cMenu.addComponent(cMainMenu);

		btnProfiles.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 8237565312571186332L;

			@Override
			public void buttonClick(ClickEvent event) {

				btnProfiles.setEnabled(false);
				btnProfiles.setStyleName("btn_tab_like btn_tab_like_active");

				if (!btnPermissions.isEnabled())
					p = cPerm;

				btnPermissions.setEnabled(true);
				btnPermissions.setStyleName("btn_tab_like");

				isCommission = false;

				if (!isfromsub) {
					if (cProfile == null)
						cProfile = getFeesC();

					p = (HorizontalLayout) cStage.getComponent(0);
					cStage.replaceComponent(p, cProfile);
					// isCommission = false;

				} else {
					// comboAllFCOperators = comboAllFeesOperators;
					prevL = (HorizontalLayout) cStage.getComponent(0);
					cStage.replaceComponent(prevL, cProfile);
					isfromsub = false;
				}

			}
		});

		btnPermissions.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 994811690918792413L;

			@Override
			public void buttonClick(ClickEvent event) {

				btnPermissions.setEnabled(false);
				btnPermissions.setStyleName("btn_tab_like btn_tab_like_active");

				if (!btnProfiles.isEnabled())
					p = cProfile;

				btnProfiles.setEnabled(true);
				btnProfiles.setStyleName("btn_tab_like");

				isCommission = true;

				if (!isfromsub) {
					if (cPerm == null)
						cPerm = getCommissionsC();
					p = (HorizontalLayout) cStage.getComponent(0);
					cStage.replaceComponent(p, cPerm);

				} else {
					if (cPerm == null)
						cPerm = getCommissionsC();
					// comboAllFCOperators = comboAllCommissionOperators;
					prevL = (HorizontalLayout) cStage.getComponent(0);
					cStage.replaceComponent(prevL, cPerm);

					isfromsub = false;

				}

			}
		});

		cMain.addComponent(cMenu);
		cMain.setComponentAlignment(cMenu, Alignment.TOP_CENTER);
		cMain.setSizeFull();
		cMain.addComponent(back);
		cMain.setComponentAlignment(back, Alignment.TOP_RIGHT);

	}

	public VerticalLayout getMainContainer() {
		return cMain;
	}

	private HorizontalLayout getFeesC() {
		isCommission = false;
		VerticalLayout cAgentInfo = new VerticalLayout();
		final HorizontalLayout cPlaceholder = new HorizontalLayout();
		cAgentInfo.setMargin(new MarginInfo(true, true, true, true));
		// cAgentInfo.setStyleName("c_details_test");

		final VerticalLayout cLBody = new VerticalLayout();

		cLBody.setStyleName("c_body_visible");

		// addLinksTable();

		final VerticalLayout cAllProf = new VerticalLayout();

		final HorizontalLayout cPermsActions = new HorizontalLayout();
		// cProfActions.setVisible(false);
		HorizontalLayout cProfNameAndAddBtn = new HorizontalLayout();
		final FormLayout cProfName = new FormLayout();

		cProfName.setStyleName("frm_profile_name");
		cProfName.setSizeUndefined();

		final Label lbProf = new Label();
		final Label lb = new Label();
		lb.setValue("Manage Fees");
		final TextField tFProf = new TextField();
		comboAllFeesOperators = new ComboBox("Select Operator: ");

		// comboAllFeesOperators = comboAllFCOperators;

		//
		// OptionGroup optSetupType = new OptionGroup("Commission setup type");
		// optSetupType.addItem("Fees");
		// optSetupType.addItem("Tiered");

		comboFeesTT = new ComboBox("Transaction Type");
		comboFeesTT.setVisible(false);

		lbProf.setCaption("Selected Operator: ");
		lbProf.setValue("None.");
		tFProf.setCaption(lbProf.getCaption());
		cAllProf.addComponent(lb);
		lb.setStyleName("label_search_user");
		cAllProf.setComponentAlignment(lb, Alignment.TOP_CENTER);
		cProfName.addComponent(comboAllFeesOperators);
		cProfName.addComponent(lbProf);

		cProfName.addComponent(comboFeesTT);

		// comboTT.setVisible(false);

		final Button btnEdit = new Button();
		btnEdit.setIcon(FontAwesome.EDIT);
		btnEdit.setStyleName("btn_link");
		btnEdit.setDescription("Edit Fees");
		btnEditFees = btnEdit;

		final Button btnDelete = new Button();
		btnDelete.setIcon(FontAwesome.RECYCLE);
		btnDelete.setStyleName("btn_link");
		btnDelete.setDescription("Delete Fees");
		btnDeleteFees = btnDelete;

		final Button btnCancel = new Button();
		btnCancel.setIcon(FontAwesome.UNDO);
		btnCancel.setStyleName("btn_link");
		btnCancel.setDescription("Cancel");
		btnCancelFees = btnCancel;

		final Button btnAdd = new Button("+");
		// btnAdd.setIcon(FontAwesome.UNDO);
		btnAdd.setStyleName("btn_link");
		btnAdd.setDescription("Add Fees Comfigurations");
		btnAdd.setVisible(false);

		// VerticalLayout cAddBtn = new VerticalLayout();
		// cAddBtn.setWidthUndefined();
		// cAddBtn.setHeight("100%");
		// cAddBtn.addComponent(btnAdd);
		// cAddBtn.setComponentAlignment(btnAdd, Alignment.BOTTOM_LEFT);

		cProfNameAndAddBtn.addComponent(cProfName);
		// cProfNameAndAddBtn.addComponent(cAddBtn);

		// cProf.addComponent(cProfName);
		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("set_permissions")))
			cPermsActions.addComponent(btnEdit);

		cPermsActions.addComponent(btnDelete);

		cPermsActions.addComponent(btnCancel);

		// cProfActions.addComponent(btnAdd);

		btnCancel.setVisible(false);

		cAllProf.addComponent(cProfNameAndAddBtn);
		cAllProf.setComponentAlignment(cProfNameAndAddBtn, Alignment.TOP_CENTER);
		cAllProf.addComponent(btnAdd);
		cAllProf.setComponentAlignment(btnAdd, Alignment.TOP_CENTER);

		// cAllProf.addComponent(cPermsActions);
		// cAllProf.setComponentAlignment(cProfActions, Alignment.TOP_CENTER);

		// cAllProf.addComponent(btnAdd);

		cLBody.addComponent(cAllProf);
		cLBody.setComponentAlignment(cAllProf, Alignment.TOP_CENTER);

		// cLBody.addComponent(tb);

		cAgentInfo.addComponent(cLBody);

		// cLBody.addComponent(btnLink);
		// cLBody.setComponentAlignment(btnLink, Alignment.TOP_LEFT);

		cPlaceholder.setVisible(false);
		// addLinkUserContainer();
		cPlaceholder.setWidth("100%");

		cLBody.addComponent(cPlaceholder);
		cLBody.setWidth("100%");
		cLBody.setComponentAlignment(cPlaceholder, Alignment.TOP_CENTER);
		HorizontalLayout c = new HorizontalLayout();
		// c.setWidth("100%");
		// cAgentInfo.setWidth("100%");
		c.addComponent(cAgentInfo);

		final Table tb = new Table();
		cPlaceholder.addComponent(tb);

		final VerticalLayout cPermsAndBtns = new VerticalLayout();
		final HorizontalLayout cPermsList = new HorizontalLayout();
		cPermsList.addComponent(tb);
		cTempComponent = tb;
		cPermsAndBtns.addComponent(cPermsList);
		cPermsAndBtns.addComponent(cPermsActions);
		cPlaceholder.addComponent(cPermsAndBtns);

		cPermsAndBtns.setComponentAlignment(cPermsActions,
				Alignment.BOTTOM_RIGHT);

		tcsInactiveAndActive = new TwinColSelect();
		tcsInactiveAndActive.setLeftColumnCaption("INACTIVE Permissions");
		tcsInactiveAndActive.setRightColumnCaption("ACTIVE Permissions");
		tcsInactiveAndActive
				.setDescription("Select permission(s) and click \'>\' or \'<\' to move from INACTIVE or ACTIVE list respectively.");

		tcsInactiveAndActive.setWidth("1200px");
		// tcsInactiveAndActive.set

		// tcsInactiveAndActive.setVisible(false);

		comboAllFeesOperators
				.addValueChangeListener(new Property.ValueChangeListener() {

					private static final long serialVersionUID = -1655803841445897977L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						hmServiceFees.clear();
						// tbFees.removeAllItems();

						Object objPname = event.getProperty().getValue();
						if (objPname == null) {
							lbProf.setValue("None.");
							lbProf.setCaption("Selected Operator: ");
							btnAdd.setVisible(false);
							cPlaceholder.setVisible(false);
							return;
						}

						System.out.println(objPname);

						String pname = objPname.toString();
						lbProf.setCaption("Selected Operator: ");
						lbProf.setValue(pname);

						if (hmAllOperators == null
								|| hmAllOperators.size() == 0) {
							hmAllOperators = getOperators();
						}

						// System.outhmAllOperators.get(pname)

						hmServiceFees = getServiceFees(Integer
								.parseInt(hmAllOperators.get(pname)));
						Integer pCount = hmServiceFees.size();

						// list.setCaption(pCount + " Active Permissions.");
						if (pCount == 0) {
							cPlaceholder.setVisible(false);
							NotifCustom.show("Fees response",
									"No existing Fees Configurations for Operator \""
											+ pname);
							btnAdd.setVisible(true);
							comboFeesTT.setVisible(false);
							isExistingFeeConfig = false;
							isNewFeeConfig = true;

							return;
						}

						isExistingFeeConfig = true;
						isNewFeeConfig = false;

						comboFeesTT.setVisible(true);
						comboFeesTT.removeAllItems();
						for (String str : hmServiceFees.keySet()) {
							comboFeesTT.addItem(str);
						}

						cPlaceholder.setVisible(false);

						btnAdd.setVisible(false);
						btnEdit.setIcon(FontAwesome.EDIT);
						btnEdit.setDescription("Edit Fees Configurations");

					}
				});

		comboFeesTT.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 8620847994138095490L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object obj = event.getProperty().getValue();
				if (obj == null) {

					cPlaceholder.setVisible(false);
					return;
				}

				if (isExistingFeeConfig) {

					displayFeesConfigurations(tb, obj.toString());
					cPermsList.removeAllComponents();
					cPermsList.addComponent(tb);
					cTempComponent = tb;
					cPlaceholder.setVisible(true);

					return;
				}

				if (isNewFeeConfig) {

					// System.out.println("Adding New Fees...........xx");
					Component c = null;

					c = getAddFeesC(FEES, false, cPlaceholder, btnAdd,
							cPermsList, tb, cPermsActions);
					cPermsList.removeAllComponents();
					cPermsList.addComponent(c);
					cTempComponent = c;
					cPlaceholder.setVisible(true);
					return;

				}

			}

		});

		comboAllFeesOperators.addFocusListener(new FocusListener() {

			private static final long serialVersionUID = 1583773393542597237L;

			@Override
			public void focus(FocusEvent event) {

				// if (!isPermProfilesAdded)

				System.out.println(comboAllFeesOperators.getCaption() + "kkkkk");

				comboAllFeesOperators.addItem("kxkm,dm,m,md");
				addOperators(comboAllFeesOperators);

				System.out.println("Are you reaching here");
				// isPermProfilesAdded = true;

			}

		});

		btnEdit.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5334429740205971979L;

			@Override
			public void buttonClick(ClickEvent event) {

				String pname = comboAllFeesOperators
						.getItemCaption(comboAllFeesOperators.getValue());
				Integer pid = Integer.parseInt(hmAllOperators.get(pname));

				if (btnEdit.getIcon().equals(FontAwesome.EDIT)) {
					btnCancel.setVisible(true);
					btnEdit.setIcon(FontAwesome.SAVE);
					btnEdit.setDescription("Save changes");

					// hmServiceFees.clear();

					Component c = getAddFeesC(FEES, true, cPlaceholder, btnAdd,
							cPermsList, tb, cPermsActions);
					cPermsList.removeAllComponents();
					cPermsList.replaceComponent(cTempComponent, c);
					cTempComponent = c;
					cPlaceholder.setVisible(true);

				}

			}
		});

		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 2567415555987260624L;

			@Override
			public void buttonClick(ClickEvent event) {
				String pn = comboAllFeesOperators.getValue().toString();
				hmServiceFees.clear();

				hmServiceFees = getServiceFees(Integer.parseInt(hmAllOperators
						.get(pn)));

				tb.removeAllItems();

				int pCount = hmServiceFees.size();

				// TODO Important swapping

				cPermsList.replaceComponent(cTempComponent, tb);
				cTempComponent = tb;
				// cPlaceholder.setVisible(false);
				btnCancel.setVisible(false);

				btnEdit.setIcon(FontAwesome.EDIT);
				btnEdit.setDescription("Edit permissions");
				btnEdit.setEnabled(true);
				btnAdd.setVisible(false);

				if (pCount == 0) {
					cPlaceholder.setVisible(false);
					NotifCustom.show("", "NO new permissions were set.");
					btnAdd.setVisible(true);
				}

			}
		});

		btnAdd.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 6681943508644191905L;

			@Override
			public void buttonClick(ClickEvent event) {
				String pn = comboAllFeesOperators.getValue().toString();
				Integer pid = Integer.parseInt(hmAllOperators.get(pn));

				btnCancel.setVisible(true);
				btnEdit.setIcon(FontAwesome.SAVE);
				btnEdit.setDescription("Save changes");
				btnAdd.setVisible(false);

				hmServiceFees.clear();
				hmInActivePerms.clear();
				hmServiceFees = getServiceFees(pid);
				hmInActivePerms = getInActivePermissions(pid);

				tcsInactiveAndActive.removeAllItems();
				comboFeesTT.removeAllItems();
				comboFeesTT.setVisible(true);

				addTransactionTypes(comboFeesTT);

				// TODO Important Swapping

				// cPermsList.replaceComponent(tb, tcsInactiveAndActive);

				isNewFeeConfig = true;

			}

		});

		btnDelete.addClickListener(new RemoveFeesHandler(pop, cPlaceholder));

		return c;
	}

	private Map<String, LinkedHashSet<Servicefee>> getServiceFees(int spid) {

		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		try {
			Map<String, LinkedHashSet<Servicefee>> hm = new HashMap<>();
			int i = 0;

			if (hmTransactionTypes == null)
				hmTransactionTypes = new HashMap<>();
			hmTransactionTypes.clear();

			for (Servicefee sf : rs.getFeesBySP(UI.getCurrent().getSession()
					.getAttribute("user").toString(), spid)) {
				String transactionType = sf.getTransactiontype();
				i++;
				if (!hm.containsKey(transactionType)) {
					LinkedHashSet<Servicefee> hset = new LinkedHashSet<>();
					hset.add(sf);
					hm.put(transactionType, hset);
					hmTransactionTypes.put(sf.getTransactiontype().toString(),
							String.valueOf(sf.getTransactiontypeid()));
					continue;
				}
				hm.get(transactionType).add(sf);

			}

			if (i == 0)
				return Collections.emptyMap();
			return hm;
		} catch (RemoteException | DataServiceFault e1) {

			e1.printStackTrace();
		}

		return Collections.emptyMap();

	}

	private Map<String, LinkedHashSet<Commissionfee>> getServiceCommissions(
			int spid) {

		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		try {
			Map<String, LinkedHashSet<Commissionfee>> hm = new HashMap<>();
			int i = 0;

			if (hmTransactionTypes == null)
				hmTransactionTypes = new HashMap<>();
			hmTransactionTypes.clear();

			if (hmExistingFees == null)
				hmExistingFees = new HashMap<>();
			hmExistingFees.clear();

			String scc = new String("TIEREDAMOUNT");

			System.out.println("Service Provide id: " + spid);
			System.out.println("Size: "
					+ rs.getCommissionBySp(UI.getCurrent().getSession()
							.getAttribute("user").toString(), spid).length);

			for (Commissionfee cf : rs.getCommissionBySp(UI.getCurrent()
					.getSession().getAttribute("user").toString(), spid)) {

				String transactionType = cf.getTransactiontype();
				i++;
				if (!hm.containsKey(transactionType)) {

					LinkedHashSet<Commissionfee> hset = new LinkedHashSet<>();
					hset.add(cf);
					hm.put(transactionType, hset);

					setNoneTier.add(cf.getCommission_fee_type());
					setNoneTier.add(cf.getServicecommissioncondition());
					setNoneTier.add(String.valueOf(cf
							.getServicefeepropertiesid()));

					hmTransactionTypes.put(cf.getTransactiontype(),
							String.valueOf(cf.getTransactiontypeid()));
					isOptTiered = cf.getServicecommissioncondition()
							.equals(scc);

					hmExistingFees.put(cf.getTransactiontype(),
							String.valueOf(cf.getServicefeepropertiesid()));

					continue;
				}
				hm.get(transactionType).add(cf);

			}

			if (i == 0)
				return Collections.emptyMap();
			return hm;
		} catch (RemoteException | DataServiceFault e1) {

			e1.printStackTrace();
		}

		return Collections.emptyMap();

	}

	private Map<String, String> getInActivePermissions(int pid) {
		try {
			return UserManagementService.getnonactiveprofilepermission(pid);
		} catch (Exception e) {
			Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
			return Collections.emptyMap();

		}

	}

	private HorizontalLayout getCommissionsC() {

		isCommission = true;

		System.out.println("IN COMMISSIONS");

		// VerticalLayout cAgentInfo = new VerticalLayout();
		// final HorizontalLayout cPlaceholder = new HorizontalLayout();
		// final HorizontalLayout cPlaceholderx = new HorizontalLayout();
		// cAgentInfo.setMargin(new MarginInfo(true, true, true, true));
		// // cAgentInfo.setStyleName("c_details_test");
		//
		// final VerticalLayout cLBody = new VerticalLayout();
		//
		// cLBody.setStyleName("c_body_visible");
		//
		// // addLinksTable();
		//
		// final VerticalLayout cAllProf = new VerticalLayout();
		//
		// final HorizontalLayout cProfActions = new HorizontalLayout();
		// cProfActions.setVisible(false);
		// HorizontalLayout cProfNameAndAddBtn = new HorizontalLayout();
		// final FormLayout cProfName = new FormLayout();
		//
		// cProfName.setStyleName("frm_profile_name");
		// cProfName.setSizeUndefined();
		//
		// final Label lbProf = new Label();
		// final Label lb = new Label();
		// lb.setValue("Manage Fees");
		// final TextField tFProf = new TextField();
		// comboProfiles = new ComboBox("Select Fees: ");
		//
		// lbProf.setCaption("Operator Name: ");
		// lbProf.setValue("None.");
		// tFProf.setCaption(lbProf.getCaption());
		// cAllProf.addComponent(lb);
		// lb.setStyleName("label_search_user");
		// cAllProf.setComponentAlignment(lb, Alignment.TOP_CENTER);
		// cProfName.addComponent(comboProfiles);
		// cProfName.addComponent(lbProf);
		//
		// final Button btnEdit = new Button();
		// btnEdit.setIcon(FontAwesome.EDIT);
		// btnEdit.setStyleName("btn_link");
		// btnEdit.setDescription("Edit Operator Fees");
		//
		// final Button btnThreshold = new Button("T");
		// // btnThreshold.setIcon(FontAwesome.T);
		// btnThreshold.setStyleName("btn_link");
		// btnThreshold.setDescription("Set profile threshold");
		//
		// final Button btnCancel = new Button();
		// btnCancel.setIcon(FontAwesome.UNDO);
		// btnCancel.setStyleName("btn_link");
		// btnCancel.setDescription("Cancel Fees editting.");
		//
		// Button btnAdd = new Button("+");
		// // btnAdd.setIcon(FontAwesome.EDIT);
		// btnAdd.setStyleName("btn_link");
		// btnAdd.setDescription("Add new fees");
		//
		// Button btnRemove = new Button("-");
		// // btnRemove.setIcon(FontAwesome.EDIT);
		// btnRemove.setStyleName("btn_link");
		// btnRemove.setDescription("Remove current profile");
		//
		// cProfNameAndAddBtn.addComponent(cProfName);
		// // cProf.addComponent(cProfName);
		// if (Initializer.setUserPermissions.contains(hmProfPermPermissions
		// .get("add_profile")))
		// cProfNameAndAddBtn.addComponent(btnAdd);
		//
		// // cProf.addComponent(cProfName);
		// if (Initializer.setUserPermissions.contains(hmProfPermPermissions
		// .get("edit_profile")))
		// cProfActions.addComponent(btnEdit);
		// cProfActions.addComponent(btnCancel);
		// // cProfActions.addComponent(btnAdd);
		// if (Initializer.setUserPermissions.contains(hmProfPermPermissions
		// .get("add_threshold")))
		// cProfActions.addComponent(btnThreshold);
		// if (Initializer.setUserPermissions.contains(hmProfPermPermissions
		// .get("remove_profile")))
		// cProfActions.addComponent(btnRemove);
		//
		// btnCancel.setVisible(false);
		//
		// cAllProf.addComponent(cProfNameAndAddBtn);
		// cAllProf.addComponent(cProfActions);
		// cAllProf.setComponentAlignment(cProfActions, Alignment.TOP_CENTER);
		//
		// cLBody.addComponent(cAllProf);
		//
		// // cLBody.addComponent(tb);
		//
		// cAgentInfo.addComponent(cLBody);
		//
		// // cLBody.addComponent(btnLink);
		// // cLBody.setComponentAlignment(btnLink, Alignment.TOP_LEFT);
		//
		// cPlaceholder.setVisible(false);
		// cPlaceholderx.setVisible(false);
		// // addLinkUserContainer();
		// cPlaceholder.setWidth("100%");
		// cPlaceholderx.setWidth("100%");
		//
		// cLBody.addComponent(cPlaceholder);
		// cLBody.addComponent(cPlaceholderx);
		// cLBody.setComponentAlignment(cPlaceholder, Alignment.TOP_CENTER);
		// cLBody.setComponentAlignment(cPlaceholderx, Alignment.TOP_CENTER);
		// HorizontalLayout c = new HorizontalLayout();
		// c.addComponent(cAgentInfo);
		//
		// comboProfiles
		// .addValueChangeListener(new Property.ValueChangeListener() {
		//
		// private static final long serialVersionUID = -1655803841445897977L;
		//
		// @Override
		// public void valueChange(ValueChangeEvent event) {
		//
		// if (event.getProperty().getValue() != null) {
		// cProfActions.setVisible(true);
		// lbProf.setValue(comboProfiles.getItemCaption(event
		// .getProperty().getValue()));
		// } else {
		// cProfActions.setVisible(false);
		// lbProf.setValue("None.");
		// cProfName.replaceComponent(tFProf, lbProf);
		//
		// }
		// }
		// });
		//
		// comboProfiles.addFocusListener(new FocusListener() {
		//
		// /**
		// *
		// */
		// private static final long serialVersionUID = 1583773393542597237L;
		//
		// @Override
		// public void focus(FocusEvent event) {
		//
		// comboProfiles.removeAllItems();
		// addOperators(comboProfiles);
		// comboProfiles.select(null);
		//
		// }
		//
		// });
		//
		// tFProf.addValueChangeListener(new Property.ValueChangeListener() {
		//
		// private static final long serialVersionUID = -1655803841445897977L;
		//
		// @Override
		// public void valueChange(ValueChangeEvent event) {
		//
		// isProfileNameChanged = true;
		//
		// }
		// });
		//
		// btnEdit.addClickListener(new Button.ClickListener() {
		//
		// private static final long serialVersionUID = -8427226211153164650L;
		//
		// @Override
		// public void buttonClick(ClickEvent event) {
		//
		// if (btnEdit.getIcon().equals(FontAwesome.EDIT)) {
		// isProfileNameChanged = false;
		// tFProf.setValue(lbProf.getValue());
		// tFProf.selectAll();
		// cProfName.replaceComponent(lbProf, tFProf);
		// btnEdit.setIcon(FontAwesome.SAVE);
		// btnEdit.setDescription("Save changes");
		// btnCancel.setVisible(true);
		// return;
		//
		// } else if (btnEdit.getIcon().equals(FontAwesome.SAVE)) {
		//
		// if (!isProfileNameChanged)
		// return;
		//
		// String pnNew = tFProf.getValue();
		// if (pnNew == null || pnNew.trim().trim().isEmpty()) {
		// Notification.show("Please provide new profile name.",
		// Notification.Type.ERROR_MESSAGE);
		// tFProf.focus();
		// return;
		// }
		//
		// if (hmAllOperators.containsKey(pnNew.trim())) {
		// Notification
		// .show("\""
		// + pnNew
		// + "\" already exists. Please provide unique profile name.",
		// Notification.Type.ERROR_MESSAGE);
		// tFProf.focus();
		// return;
		// }
		//
		// lbProf.setValue(tFProf.getValue());
		// cProfName.replaceComponent(tFProf, lbProf);
		// btnEdit.setIcon(FontAwesome.EDIT);
		// btnEdit.setDescription("Edit profile name");
		// btnCancel.setVisible(false);
		//
		// isProfileNameChanged = false;
		//
		// Object profileName = comboProfiles.getValue();
		// Integer pid = Integer.parseInt(hmAllOperators
		// .get(profileName.toString()));
		// String pnOld = profileName.toString();
		//
		// System.out.println(" New PN: " + pnNew + " Old PN: "
		// + pnOld + " PID:" + pid);
		//
		// String response = null;
		//
		// try {
		// response = UserManagementService.editProfile(pnNew,
		// pnOld, pid);
		//
		// comboProfiles.addItem(pnNew);
		// comboProfiles.select(pnNew);
		// // hmAllProfiles.remove(pnOld);
		//
		// if (response == null || response.trim().isEmpty()) {
		// Notification.show("No reponse from the server",
		// Notification.Type.ERROR_MESSAGE);
		// return;
		//
		// }
		//
		// if (response.toUpperCase().contains("SUCCESSFUL")) {
		// lbProf.setValue(tFProf.getValue());
		// cProfName.replaceComponent(tFProf, lbProf);
		// btnEdit.setIcon(FontAwesome.EDIT);
		// btnEdit.setDescription("Edit profile name");
		// btnCancel.setVisible(false);
		//
		// NotifCustom.show("Response", response);
		// hmAllOperators.put(pnNew, pid.toString());
		// comboAllFCOperators.removeAllItems();
		// comboAllFCOperators.select(null);
		//
		// return;
		// }
		//
		// Notification.show(response,
		// Notification.Type.ERROR_MESSAGE);
		//
		// } catch (Exception e) {
		//
		// Notification.show(e.getMessage(),
		// Notification.Type.ERROR_MESSAGE);
		//
		// e.printStackTrace();
		// }
		//
		// }
		//
		// }
		// });
		//
		// btnCancel.addClickListener(new Button.ClickListener() {
		// private static final long serialVersionUID = -2870045546205986347L;
		//
		// @Override
		// public void buttonClick(ClickEvent event) {
		// cProfName.replaceComponent(tFProf, lbProf);
		// btnEdit.setIcon(FontAwesome.EDIT);
		// btnEdit.setDescription("Edit profile name");
		// btnCancel.setVisible(false);
		//
		// }
		// });
		//
		// btnAdd.addClickListener(new AddProfileHandler(cAllProf,
		// cPlaceholder));
		//
		// btnRemove.addClickListener(new RemoveFeesHandler(pop, cPlaceholder));
		// btnThreshold.addClickListener(new SetThresholdHandler(cAllProf,
		// cPlaceholderx));

		VerticalLayout cAgentInfo = new VerticalLayout();
		final HorizontalLayout cPlaceholderx = new HorizontalLayout();
		cAgentInfo.setMargin(new MarginInfo(true, true, true, true));
		// cAgentInfo.setStyleName("c_details_test");

		final VerticalLayout cLBody = new VerticalLayout();

		cLBody.setStyleName("c_body_visible");

		// addLinksTable();

		final VerticalLayout cAllProf = new VerticalLayout();

		final HorizontalLayout cPermsActions = new HorizontalLayout();
		// cProfActions.setVisible(false);
		HorizontalLayout cProfNameAndAddBtn = new HorizontalLayout();
		final FormLayout cProfName = new FormLayout();

		cProfName.setStyleName("frm_profile_name");
		cProfName.setSizeUndefined();

		final Label lbProf = new Label();
		final Label lb = new Label();
		lb.setValue("Manage Commissions");
		final TextField tFProf = new TextField();
		comboAllCommissionOperators = new ComboBox("Select Operator: ");
		// comboAllCommissionOperators = comboAllFCOperators;

		comboCommissionsTT = new ComboBox("Transaction Type");
		comboCommissionsTT.setVisible(false);

		optSetupType = new OptionGroup("Commission Setup Type");
		optSetupType.addItem(1);
		optSetupType.addItem(2);

		optSetupType.setItemCaption(1, "Fees");
		optSetupType.setItemCaption(2, "Tiered");

		optSetupType.setVisible(false);

		lbProf.setCaption("Selected Operator: ");
		lbProf.setValue("None.");
		tFProf.setCaption(lbProf.getCaption());
		cAllProf.addComponent(lb);
		lb.setStyleName("label_search_user");
		cAllProf.setComponentAlignment(lb, Alignment.TOP_CENTER);
		cProfName.addComponent(comboAllCommissionOperators);
		cProfName.addComponent(lbProf);

		cProfName.addComponent(optSetupType);

		cProfName.addComponent(comboCommissionsTT);

		// comboTT.setVisible(false);

		final Button btnEdit = new Button();
		btnEdit.setIcon(FontAwesome.EDIT);
		btnEdit.setStyleName("btn_link");
		btnEdit.setDescription("Edit Commissions");
		btnEditCommissions = btnEdit;

		final Button btnDelete = new Button();
		btnDelete.setIcon(FontAwesome.RECYCLE);
		btnDelete.setStyleName("btn_link");
		btnDelete.setDescription("Delete Commissions");
		btnDeleteCommissions = btnDelete;

		final Button btnCancel = new Button();
		btnCancel.setIcon(FontAwesome.UNDO);
		btnCancel.setStyleName("btn_link");
		btnCancel.setDescription("Cancel");
		btnCancelCommissions = btnCancel;
		btnCancel.setVisible(true);

		final Button btnAdd = new Button("+");
		// btnAdd.setIcon(FontAwesome.UNDO);
		btnAdd.setStyleName("btn_link");
		btnAdd.setDescription("Add Commissions Comfigurations");
		btnAdd.setVisible(false);

		// VerticalLayout cAddBtn = new VerticalLayout();
		// cAddBtn.setWidthUndefined();
		// cAddBtn.setHeight("100%");
		// cAddBtn.addComponent(btnAdd);
		// cAddBtn.setComponentAlignment(btnAdd, Alignment.BOTTOM_LEFT);

		cProfNameAndAddBtn.addComponent(cProfName);
		// cProfNameAndAddBtn.addComponent(cAddBtn);

		// cProf.addComponent(cProfName);
		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("set_permissions")))
			cPermsActions.addComponent(btnEdit);

		cPermsActions.addComponent(btnDelete);

		cPermsActions.addComponent(btnCancel);

		// cProfActions.addComponent(btnAdd);

		btnCancel.setVisible(true);

		cAllProf.addComponent(cProfNameAndAddBtn);

		cAllProf.setComponentAlignment(cProfNameAndAddBtn, Alignment.TOP_CENTER);
		cAllProf.addComponent(btnAdd);
		cAllProf.setComponentAlignment(btnAdd, Alignment.TOP_CENTER);

		// cAllProf.addComponent(cPermsActions);
		// cAllProf.setComponentAlignment(cProfActions, Alignment.TOP_CENTER);

		// cAllProf.addComponent(btnAdd);

		cLBody.addComponent(cAllProf);
		cLBody.setComponentAlignment(cAllProf, Alignment.TOP_CENTER);

		// cLBody.addComponent(tb);

		cAgentInfo.addComponent(cLBody);

		// cLBody.addComponent(btnLink);
		// cLBody.setComponentAlignment(btnLink, Alignment.TOP_LEFT);

		cPlaceholderx.setVisible(false);
		// addLinkUserContainer();
		cPlaceholderx.setWidth("100%");

		cLBody.addComponent(cPlaceholderx);
		cLBody.setWidth("100%");
		cLBody.setComponentAlignment(cPlaceholderx, Alignment.TOP_CENTER);
		HorizontalLayout c = new HorizontalLayout();
		// c.setWidth("100%");
		// cAgentInfo.setWidth("100%");
		c.addComponent(cAgentInfo);

		final Table tb = new Table();
		cPlaceholderx.addComponent(tb);

		final VerticalLayout cPermsAndBtns = new VerticalLayout();
		final HorizontalLayout cPermsList = new HorizontalLayout();
		cPermsList.addComponent(tb);
		cTempComponent = tb;
		cPermsAndBtns.addComponent(cPermsList);
		cPermsAndBtns.addComponent(cPermsActions);
		cPlaceholderx.addComponent(cPermsAndBtns);

		cPermsAndBtns.setComponentAlignment(cPermsActions,
				Alignment.BOTTOM_RIGHT);

		tcsInactiveAndActive = new TwinColSelect();
		tcsInactiveAndActive.setLeftColumnCaption("INACTIVE Permissions");
		tcsInactiveAndActive.setRightColumnCaption("ACTIVE Permissions");
		tcsInactiveAndActive
				.setDescription("Select permission(s) and click \'>\' or \'<\' to move from INACTIVE or ACTIVE list respectively.");

		tcsInactiveAndActive.setWidth("1200px");

		final HorizontalLayout cCommValue = new HorizontalLayout();
		cCommValue.setHeight("100%");
		cCommValue.setWidth("100%");
		HorizontalLayout cBtns = new HorizontalLayout();
		// cBtns.setHeight("100%");
		// cBtns.setWidthUndefined();
		Button btnCommSave = new Button();
		btnCommSave.setIcon(FontAwesome.SAVE);

		Button btnCommCancel = new Button();
		btnCommCancel.setIcon(FontAwesome.UNDO);
		btnCommCancel.setDescription("Cancel");

		btnCommSave.setStyleName("btn_link");
		btnCommCancel.setStyleName("btn_link");
		btnCommSave.setDescription("Save Commission Configurations");

		tFCommValue = new TextField("Percentage Value");

		// cBtns.addComponent(tFCommValue);
		cBtns.addComponent(btnCommSave);
		cBtns.addComponent(btnCommCancel);

		FormLayout cFT = new FormLayout();
		// cFT.setSizeUndefined();

		cFT.addComponent(tFCommValue);
		cCommValue.addComponent(cFT);
		cCommValue.addComponent(cBtns);
		cCommValue.setComponentAlignment(cFT, Alignment.TOP_LEFT);
		cCommValue.setComponentAlignment(cBtns, Alignment.MIDDLE_RIGHT);

		// tcsInactiveAndActive.set

		// tcsInactiveAndActive.setVisible(false);

		btnCommCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -5103751946458025593L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (isExistingFeeConfig) {

					displayFeesConfigurations(tb, comboCommissionsTT.getValue()
							.toString());
					cPermsList.removeAllComponents();
					cPermsList.addComponent(tb);
					cPlaceholderx.setVisible(true);
					isEditFees = false;
					btnEditFees.setIcon(FontAwesome.EDIT);
					btnEditFees.setDescription("Edit Configurations Fees");

					cPermsActions.setVisible(true);
					optSetupType.setEnabled(false);
				} else {
					comboCommissionsTT.select(null);
					comboAllCommissionOperators.select(null);
					comboCommissionsTT.setVisible(false);
					// optSetupType.setVisible(false);
				}

			}
		});

		btnCommSave.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5773757758363492384L;

			@Override
			public void buttonClick(ClickEvent event) {

				String val = tFCommValue.getValue();
				if (val == null || val.trim().isEmpty()) {

					NotifCustom.show("Error",
							"Pleas provide a Percentage value");
					tFCommValue.focus();

					return;
				}

				try {

					if (Double.parseDouble(val) > 100) {

						NotifCustom
								.show("Error",
										"Invalid percentage value. ( Values between 0 and 100 only.");
						tFCommValue.focus();
						return;
					}
				} catch (Exception e) {

					NotifCustom.show("Error",
							"Invalid Percentage Value. Only numbers allowed.");
					tFCommValue.focus();
					return;

				}

				if (commit(cPlaceholderx)) {
					if (hmTransactionTypes == null
							|| hmTransactionTypes.size() == 0)
						hmTransactionTypes = getTransactionTypes();

					// System.out
					// .println("-------vvvvv--vv---v-v----------------");
					// String pname =
					// comboAllFCOperators.getValue().toString()
					// .trim();
					// hmServiceFees = getServiceFees(Integer
					// .parseInt(hmAllOperators.get(pname)));
					//
					// displayFeesConfigurations(tb,
					// comboTT.getValue().toString());
					// cPermsList.removeAllComponents();
					// cPermsList.addComponent(tb);
					// cPlaceholderx.setVisible(true);
					// isEditFees = false;
					// btnEditCommissions.setIcon(FontAwesome.EDIT);
					// btnEditCommissions
					// .setDescription("Edit Commissions Configurations");
					// btnCancelCommissions.setVisible(false);
					//
					// optSetupType.setVisible(false);
					// optSetupType.setEnabled(true);
					comboAllCommissionOperators.select(null);
					comboAllCommissionOperators.setValue(null);
					comboCommissionsTT.setEnabled(true);
					comboCommissionsTT.setVisible(false);
				}

			}
		});

		optSetupType.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 3040625733053462927L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object obj = event.getProperty().getValue();

				if (obj == null || !optSetupType.isEnabled())

					return;

				// cPermsActions.setVisible(true);

				if (!isEditFees) {
					if (Integer.valueOf(obj.toString()) == 1) {
						isOptTiered = false;
						addTransactionTypes(comboCommissionsTT);
						tFCommValue.setValue("");

						System.out.println("I love ....");
					} else {
						isOptTiered = true;
						addTransactionTypes(comboCommissionsTT);
						// cPermsActions.setVisible(true);

						System.out.println("I love x....");
					}
				}

				if (isEditFees) {

					if (Integer.valueOf(obj.toString()) == 1) {
						isOptTiered = false;
						// cPermsActions.setVisible(false);
						// tFCommValue.setValue("");
						cPermsList.removeAllComponents();
						cPermsList.addComponent(cCommValue);
						cTempComponent = cCommValue;
						cPlaceholderx.setVisible(true);

					} else {
						isOptTiered = true;
						Component c = getAddFeesC(COMMISSION, true,
								cPlaceholderx, btnAdd, cPermsList, tb,
								cPermsActions);
						cPermsList.removeAllComponents();
						cPermsList.replaceComponent(cTempComponent, c);
						cTempComponent = c;
						cPlaceholderx.setVisible(true);
						optSetupType.setVisible(true);
						optSetupType.setEnabled(true);
						comboCommissionsTT.setEnabled(false);

						System.out.println("I love ....");

					}

				}

				comboCommissionsTT.setVisible(true);

			}

		});

		comboAllCommissionOperators
				.addValueChangeListener(new Property.ValueChangeListener() {

					private static final long serialVersionUID = -1655803841445897977L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						hmServiceCommission.clear();
						optSetupType.setVisible(false);
						comboCommissionsTT.setEnabled(true);

						// tbFees.removeAllItems();

						Object objPname = event.getProperty().getValue();
						if (objPname == null) {
							lbProf.setValue("None.");
							lbProf.setCaption("Selected Operator: ");
							btnAdd.setVisible(false);
							cPlaceholderx.setVisible(false);
							return;
						}

						System.out.println(objPname);

						String pname = objPname.toString();
						lbProf.setCaption("Selected Operator: ");
						lbProf.setValue(pname);

						// System.outhmAllOperators.get(pname)

						hmServiceCommission = getServiceCommissions(Integer
								.parseInt(hmAllOperators.get(pname)));
						Integer pCount = hmServiceCommission.size();

						// list.setCaption(pCount + " Active Permissions.");
						if (pCount == 0) {
							cPlaceholderx.setVisible(false);
							NotifCustom.show("Commissions response",
									"No existing Commissions Configurations for Operator \""
											+ pname);
							btnAdd.setVisible(true);
							comboCommissionsTT.setVisible(false);
							isExistingFeeConfig = false;
							isNewFeeConfig = true;

							return;
						}

						isExistingFeeConfig = true;
						isNewFeeConfig = false;

						comboCommissionsTT.setVisible(true);
						comboCommissionsTT.removeAllItems();
						for (String str : hmServiceCommission.keySet()) {
							comboCommissionsTT.addItem(str);
						}

						cPlaceholderx.setVisible(false);

						btnAdd.setVisible(false);
						btnEdit.setIcon(FontAwesome.EDIT);
						btnEdit.setDescription("Edit Commissions Configurations");

					}
				});

		comboCommissionsTT.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 8620847994138095490L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object obj = event.getProperty().getValue();

				if (obj == null) {

					cPlaceholderx.setVisible(false);
					return;
				}

				if (!isOptTiered && !isExistingFeeConfig) {

					tFCommValue.setValue("");
					cPermsList.removeAllComponents();
					cPermsList.addComponent(cCommValue);
					cTempComponent = cCommValue;
					cPlaceholderx.setVisible(true);
					cPermsActions.setVisible(false);

					return;

				}

				if (isExistingFeeConfig) {

					displayFeesConfigurations(tb, obj.toString());
					cPermsActions.setVisible(true);

					if (isCommission) {
						optSetupType.setEnabled(false);
						optSetupType.setVisible(true);
						if (isOptTiered) {

							optSetupType.select(2);

						} else {

							optSetupType.select(1);

						}
					}
					cPermsList.removeAllComponents();
					cPermsList.addComponent(tb);
					cTempComponent = tb;
					cPlaceholderx.setVisible(true);

					return;
				}

				if (isNewFeeConfig) {

					System.out
							.println(" COMMISSION...............mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
					Component c = null;
					if (isCommission) {

						c = getAddFeesC(COMMISSION, false, cPlaceholderx,
								btnAdd, cPermsList, tb, cPermsActions);

					} else {
						c = getAddFeesC(FEES, false, cPlaceholderx, btnAdd,
								cPermsList, tb, cPermsActions);
					}

					System.out.println("Adding New Fees...........xx");
					cPermsList.removeAllComponents();
					cPermsList.addComponent(c);
					cTempComponent = c;
					cPlaceholderx.setVisible(true);
					cPermsActions.setVisible(false);
					return;

				}

			}

		});

		comboAllCommissionOperators.addFocusListener(new FocusListener() {

			private static final long serialVersionUID = 1583773393542597237L;

			@Override
			public void focus(FocusEvent event) {

				// if (!isPermProfilesAdded)
				addOperators(comboAllCommissionOperators);
				// isPermProfilesAdded = true;

			}

		});

		btnEdit.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5334429740205971979L;

			@Override
			public void buttonClick(ClickEvent event) {

				String pname = comboAllCommissionOperators
						.getItemCaption(comboAllCommissionOperators.getValue());
				Integer pid = Integer.parseInt(hmAllOperators.get(pname));

				if (btnEdit.getIcon().equals(FontAwesome.EDIT)) {
					cPermsActions.setVisible(false);
					isEditFees = true;
					// btnCancel.setVisible(true);
					// btnEdit.setIcon(FontAwesome.SAVE);
					// btnEdit.setDescription("Save changes");

					// hmServiceFees.clear();

					Component c = null;
					if (!isOptTiered) {
						Object rid = tbFees.getItemIds().iterator().next();
						Item r = tbFees.getItem(rid);
						tFCommValue.setValue(r.getItemProperty("Amount")
								.getValue().toString());
						c = cCommValue;
					} else {

						c = getAddFeesC(COMMISSION, true, cPlaceholderx,
								btnAdd, cPermsList, tb, cPermsActions);
					}

					cCommValue.setVisible(true);

					cPermsList.removeAllComponents();
					cPermsList.replaceComponent(cTempComponent, c);
					cTempComponent = c;
					cPlaceholderx.setVisible(true);
					optSetupType.setVisible(true);
					optSetupType.setEnabled(true);
					comboCommissionsTT.setEnabled(false);

				}

			}
		});

		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 2567415555987260624L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (isCommission) {

					comboAllCommissionOperators.select(null);
					comboAllCommissionOperators.setValue(null);
					comboCommissionsTT.setVisible(false);

					return;
				}
				String pn = comboAllCommissionOperators.getValue().toString();
				hmServiceCommission.clear();

				hmServiceCommission = getServiceCommissions(Integer
						.parseInt(hmAllOperators.get(pn)));

				tb.removeAllItems();

				int pCount = hmServiceCommission.size();

				// TODO Important swapping

				cPermsList.replaceComponent(cTempComponent, tb);
				cTempComponent = tb;
				// cPlaceholderx.setVisible(false);
				btnCancel.setVisible(false);

				btnEdit.setIcon(FontAwesome.EDIT);
				btnEdit.setDescription("Edit permissions");
				btnEdit.setEnabled(true);
				btnAdd.setVisible(false);

				if (pCount == 0) {
					cPlaceholderx.setVisible(false);
					NotifCustom.show("", "NO new permissions were set.");
					btnAdd.setVisible(true);
				}

			}
		});

		btnAdd.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 6681943508644191905L;

			@Override
			public void buttonClick(ClickEvent event) {
				optSetupType.setEnabled(true);
				optSetupType.select(null);

				ComboBox comboAllFCOperators = null;

				if (isCommission)

					comboAllFCOperators = comboAllCommissionOperators;
				else
					comboAllFCOperators = comboAllFeesOperators;

				String pn = comboAllFCOperators.getValue().toString();
				Integer pid = Integer.parseInt(hmAllOperators.get(pn));

				btnCancel.setVisible(true);
				btnEdit.setIcon(FontAwesome.SAVE);
				btnEdit.setDescription("Save changes");
				btnAdd.setVisible(false);

				hmServiceCommission.clear();
				hmInActivePerms.clear();
				hmServiceCommission = getServiceCommissions(pid);
				hmInActivePerms = getInActivePermissions(pid);

				tcsInactiveAndActive.removeAllItems();
				comboCommissionsTT.removeAllItems();
				// comboTT.setVisible(true);
				optSetupType.setVisible(true);

				addTransactionTypes(comboCommissionsTT);

				// TODO Important Swapping

				// cPermsList.replaceComponent(tb, tcsInactiveAndActive);

				isNewFeeConfig = true;

				System.out.println("-----In Configurations-------Period");

				cPermsActions.setVisible(false);

				// combo

			}

		});

		btnDelete.addClickListener(new RemoveCommissionsHandler(pop,
				cPlaceholderx));

		return c;

	}

	private class RemoveFeesHandler implements Button.ClickListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Window pop;
		private Button btnCancel;
		private Button btnComfirm;
		private Label lbComfirm;
		private HorizontalLayout cPlaceholder;

		RemoveFeesHandler(final Window pop, final HorizontalLayout cPlaceholder2) {
			this.pop = pop;
			this.cPlaceholder = cPlaceholder2;

			VerticalLayout cComfirm = new VerticalLayout();

			lbComfirm = new Label();

			btnComfirm = new Button("Remove Fees Configurations");
			btnComfirm.setStyleName("btn_link");
			btnComfirm.setDescription("Remove current Fees Configurations");

			btnCancel = new Button("Cancel");
			btnCancel.setStyleName("btn_link");
			btnCancel.setDescription("Cancel Fees removal");

			HorizontalLayout cBtns = new HorizontalLayout();
			cBtns.addComponent(btnComfirm);
			cBtns.addComponent(btnCancel);

			cBtns.setSpacing(true);

			cComfirm.addComponent(lbComfirm);
			cComfirm.addComponent(cBtns);

			cComfirm.setSpacing(true);
			cComfirm.setMargin(true);

			cComfirm.setComponentAlignment(lbComfirm, Alignment.TOP_CENTER);
			cComfirm.setComponentAlignment(cBtns, Alignment.TOP_CENTER);

			pop.setContent(cComfirm);

			btnComfirm.addClickListener(new Button.ClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {

					pop.close();

					String pn = comboAllCommissionOperators.getValue()
							.toString();
					Integer pid = Integer.parseInt(hmAllOperators.get(pn));
					String response = null;

					try {

						Integer opID = Integer.parseInt(hmAllOperators
								.get(comboAllCommissionOperators.getValue()
										.toString().trim()));

						Integer txID = Integer.valueOf(hmTransactionTypes
								.get(comboFeesTT.getValue().toString()));

						response = FeeService.deleteServicefee(UI.getCurrent()
								.getSession().getAttribute("user").toString(),
								txID, opID);

						System.out.println(response);
						NotifCustom.show("Response", response);

						if (response.equals("Service fee deleted successfully")) {

							NotifCustom.show("Response", response);//
							cPlaceholder.setVisible(false);
							comboFeesTT.setVisible(false);
							comboAllCommissionOperators.select(null);

						}
					} catch (Exception e) {
						Notification.show("Oops... Fees Deletion failed!",
								Notification.Type.ERROR_MESSAGE);
						e.printStackTrace();

					}

				}
			});

			btnCancel.addClickListener(new Button.ClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {

					pop.close();

				}
			});

		}

		@Override
		public void buttonClick(ClickEvent event) {
			lbComfirm.setValue("Please comfirm removal of  "
					+ comboAllFeesOperators.getValue() + " 's "
					+ comboFeesTT.getValue().toString()
					+ " Fees Configurations");
			UI.getCurrent().addWindow(pop);

			pop.center();

		}
	}

	private class RemoveCommissionsHandler implements Button.ClickListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Window pop;
		private Button btnCancel;
		private Button btnComfirm;
		private Label lbComfirm;
		private HorizontalLayout cPlaceholder;

		RemoveCommissionsHandler(final Window pop,
				final HorizontalLayout cPlaceholder2) {
			this.pop = pop;
			this.pop.setCaption("Delete Commissions");
			this.cPlaceholder = cPlaceholder2;

			VerticalLayout cComfirm = new VerticalLayout();

			lbComfirm = new Label();

			btnComfirm = new Button("Remove Commissions Configurations");
			btnComfirm.setStyleName("btn_link");
			btnComfirm
					.setDescription("Remove current Commissions Configurations");

			btnCancel = new Button("Cancel");
			btnCancel.setStyleName("btn_link");
			btnCancel.setDescription("Cancel Commissions removal");

			HorizontalLayout cBtns = new HorizontalLayout();
			cBtns.addComponent(btnComfirm);
			cBtns.addComponent(btnCancel);

			cBtns.setSpacing(true);

			cComfirm.addComponent(lbComfirm);
			cComfirm.addComponent(cBtns);

			cComfirm.setSpacing(true);
			cComfirm.setMargin(true);

			cComfirm.setComponentAlignment(lbComfirm, Alignment.TOP_CENTER);
			cComfirm.setComponentAlignment(cBtns, Alignment.TOP_CENTER);

			pop.setContent(cComfirm);

			btnComfirm.addClickListener(new Button.ClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {

					pop.close();

					String response = null;

					Integer setupPropertyID = 0;
					Integer txID = 0;
					if (!isOptTiered) {
						setupPropertyID = Integer.valueOf(hmExistingFees
								.get(comboCommissionsTT.getValue().toString()));

						String s = comboCommissionsTT.getValue().toString();
						if (!isExistingFeeConfig) {

							s = s.substring(s.indexOf("-") + 1, s.length())
									.trim();
						}
						txID = Integer.valueOf(hmTransactionTypes.get(s));

					} else {

						txID = Integer.valueOf(hmTransactionTypes
								.get(comboCommissionsTT.getValue().toString()));
					}

					try {

						Integer opID = Integer.parseInt(hmAllOperators
								.get(comboAllCommissionOperators.getValue()
										.toString().trim()));

						response = CommissionService.deleteCommission(UI
								.getCurrent().getSession().getAttribute("user")
								.toString(), txID, opID, setupPropertyID);

						System.out.println(response);
						// NotifCustom.show("Response", response);

						if (response
								.equals("Service commission deleted successfully")) {

							NotifCustom.show("Response", response);//
							cPlaceholder.setVisible(false);
							comboCommissionsTT.setVisible(false);
							comboAllCommissionOperators.select(null);

						}
					} catch (Exception e) {
						Notification.show(
								"Oops... Commissions Deletion failed!",
								Notification.Type.ERROR_MESSAGE);
						e.printStackTrace();

					}

				}
			});

			btnCancel.addClickListener(new Button.ClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {

					pop.close();

				}
			});

		}

		@Override
		public void buttonClick(ClickEvent event) {
			lbComfirm.setValue("Please comfirm removal of  "
					+ comboAllCommissionOperators.getValue() + " 's "
					+ comboCommissionsTT.getValue().toString()
					+ " Commissions Configurations");
			UI.getCurrent().addWindow(pop);

			pop.center();

		}
	}

	private class AddProfileHandler implements Button.ClickListener {

		private static final long serialVersionUID = -9065514577173650677L;
		private VerticalLayout cAddProf;
		private HorizontalLayout cBtns;
		private TextField tFProf;
		private ComboBox combo;
		private Button btnCancel;
		private Button btnSave;
		private VerticalLayout cAllProf;
		private HorizontalLayout cPlaceholder;

		AddProfileHandler(final VerticalLayout cAllProf,
				final HorizontalLayout cPlaceholder) {
			this.cAllProf = cAllProf;
			this.cPlaceholder = cPlaceholder;
			cAddProf = new VerticalLayout();
			cBtns = new HorizontalLayout();
			cAddProf.setMargin(new MarginInfo(true, false, false, false));

			tFProf = new TextField("Profile Name");
			combo = new ComboBox("Profile Type");
			combo.setValue(null);
			combo.setNullSelectionAllowed(false);

			btnSave = new Button();
			btnSave.setIcon(FontAwesome.SAVE);
			btnSave.setStyleName("btn_link");
			btnSave.setDescription("Save new profile");

			btnCancel = new Button();
			btnCancel.setStyleName("btn_link");
			btnCancel.setIcon(FontAwesome.UNDO);
			btnCancel.setDescription("Cancel setting new profile");

			cAddProf.addComponent(tFProf);
			cAddProf.addComponent(combo);
			cBtns.addComponent(btnSave);
			cBtns.addComponent(btnCancel);
			cAddProf.addComponent(cBtns);

			cPlaceholder.addComponent(cAddProf);

			combo.addFocusListener(new FocusListener() {
				private static final long serialVersionUID = -7814943757729564782L;

				@Override
				public void focus(FocusEvent event) {

					if (hmProfileTypes != null)
						return;
					addProfileTypes(combo);

				}

			});

			tFProf.addValueChangeListener(new ValueChangeListener() {

				private static final long serialVersionUID = 4362210247953246281L;

				@Override
				public void valueChange(ValueChangeEvent event) {

					if (hmAllOperators == null)
						addOperators(comboProfiles);

				}

			});

			btnCancel.addClickListener(new Button.ClickListener() {

				private static final long serialVersionUID = 3115121215716705673L;

				@Override
				public void buttonClick(ClickEvent event) {

					cAllProf.setVisible(true);
					cPlaceholder.setVisible(false);

				}
			});

			btnSave.addClickListener(new Button.ClickListener() {

				private static final long serialVersionUID = 7591799098570751956L;

				@Override
				public void buttonClick(ClickEvent event) {

					String pn = tFProf.getValue();
					if (pn == null || pn.trim().isEmpty()) {
						Notification.show("Please provide a profile name",
								Notification.Type.ERROR_MESSAGE);
						tFProf.focus();
						return;
					}

					if (combo.getValue() == null) {
						Notification.show("Please select a profile",
								Notification.Type.ERROR_MESSAGE);
						return;
					}

					if (hmAllOperators.containsKey(pn)) {
						Notification
								.show(pn
										+ " already exists. Please provide a unique profile name.",
										Notification.Type.ERROR_MESSAGE);
						return;
					}

					Object profileID = combo.getValue();
					Integer pid = Integer.parseInt(profileID.toString());
					String response = null;

					try {

						System.out.println(pn + " : " + pid);
						response = UserManagementService.addProfile(pn, pid);

					} catch (Exception e) {
						Notification.show(e.getMessage(),
								Notification.Type.ERROR_MESSAGE);
						e.printStackTrace();
						return;
					}
					if (response == null || response.trim().isEmpty()) {
						Notification.show("Unknown operation status",
								Notification.Type.ERROR_MESSAGE);
						return;
					}

					if (response.toUpperCase().contains("SUCCESS")) {
						NotifCustom.show("Add profile", pn
								+ " added successfully.");
						tFProf.setValue("");
						// hmAllOperators.put(pn, pid.toString());
						// comboProfiles.addItem(pn);
						cAllProf.setVisible(true);
						cPlaceholder.setVisible(false);
						combo.select(null);
						return;
					} else {
						Notification.show(response,
								Notification.Type.ERROR_MESSAGE);
					}

				}
			});

		}

		@Override
		public void buttonClick(ClickEvent event) {
			cPlaceholder.setVisible(true);
			cAllProf.setVisible(false);
			tFProf.setValue("");
			combo.select(null);

		}

	}

	private void addProfileTypes(ComboBox combo) {

		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {
				e.printStackTrace();
			}
		}

		try {
			if (hmProfileTypes == null)
				hmProfileTypes = rs.getProfileTypes();
			Set<Entry<String, String>> set = hmProfileTypes.entrySet();
			for (Entry<String, String> e : set) {

				System.out.println(e.getKey() + " : " + e.getValue());
				Object id = e.getKey();
				combo.addItem(id);
				combo.setItemCaption(id, e.getValue());

			}

		} catch (RemoteException | DataServiceFault e) {

			e.printStackTrace();
		}

	}

	private void addOperators(ComboBox combo) {

		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		try {
			hmProfIDs.clear();
			// profiles = rs.getProfiles();
			// Map<String, String> hmTemp = new HashMap<>();
			// for (Profile profile : profiles) {
			// hmTemp.put(profile.getProfilename(), profile.getProfileid());
			// hmProfIDs.put(profile.getProfilename(), profile);

			// }

			// hmAllOperators = hmTemp;

			hmAllOperators = rs.getServiceProviders();

			combo.removeAllItems();

			System.out.println(hmAllOperators.size()
					+ "::::THIS IS TRUE:::::::");

			Set<Entry<String, String>> set = hmAllOperators.entrySet();
			for (Entry<String, String> e : set) {
				Object key = e.getKey();
				// comboAllFCOperators.addItem(key);
				combo.addItem(key);
				System.out.println(key.toString());

			}

		} catch (RemoteException | DataServiceFault e) {
			Notification.show("Error", Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	private class SetThresholdHandler implements Button.ClickListener {

		private static final long serialVersionUID = -9065514577173650677L;
		private VerticalLayout cAddProf;
		private HorizontalLayout cBtns;
		private TextField tFProf;
		private TextField tFProfx;
		private ComboBox combo;
		private Button btnCancel;
		private Button btnSave;
		private VerticalLayout cAllProf;
		private HorizontalLayout cPlaceholder;
		private String ptid;
		private HorizontalLayout cTransactionTypes;

		SetThresholdHandler(final VerticalLayout cAllProf,
				final HorizontalLayout cPlaceholder) {
			this.cAllProf = cAllProf;
			this.cPlaceholder = cPlaceholder;
			cAddProf = new VerticalLayout();
			cBtns = new HorizontalLayout();
			cAddProf.setMargin(new MarginInfo(true, false, false, false));

			tFProfx = new TextField();
			tFProf = new TextField();
			tFProf.setCaption("Amount");
			tFProfx.setCaption("Selected Profile: ");
			tFProf.setCaption("Amount");
			combo = new ComboBox("Profile Type");
			combo.setValue(null);
			combo.setNullSelectionAllowed(false);

			btnSave = new Button();
			btnSave.setIcon(FontAwesome.SAVE);
			btnSave.setStyleName("btn_link");
			btnSave.setDescription("Save new profile");

			btnCancel = new Button();
			btnCancel.setStyleName("btn_link");
			btnCancel.setIcon(FontAwesome.UNDO);
			btnCancel.setDescription("Cancel setting new profile");

			cAddProf.addComponent(tFProf);
			// cAddProf.addComponent(combo);

			optMultTrans = new OptionGroup();
			optMultTrans.setCaption("Transaction Type");
			optMultTrans.setMultiSelect(true);

			comboThresholds = new ComboBox();
			comboThresholds.setCaption("Threshold");
			// optMultTransactionTypes.setMultiSelect(true);

			HorizontalLayout cThresholds = new HorizontalLayout();

			cTransactionTypes = new HorizontalLayout();

			cThresholds.addComponent(comboThresholds);

			cTransactionTypes.addComponent(optMultTrans);
			Label lb = new Label("Select Threshold and Transaction Type(s)");
			cAddProf.addComponent(lb);
			cAddProf.addComponent(tFProfx);
			tFProfx.setEnabled(false);
			tFProfx.setReadOnly(true);
			// tFProfx.set

			cAddProf.addComponent(cTransactionTypes);
			cAddProf.addComponent(cThresholds);
			cAddProf.addComponent(tFProf);

			cPlaceholder.setSpacing(true);

			cBtns.addComponent(btnSave);
			cBtns.addComponent(btnCancel);
			cAddProf.addComponent(cBtns);

			cPlaceholder.addComponent(cAddProf);

			combo.addFocusListener(new FocusListener() {
				private static final long serialVersionUID = -7814943757729564782L;

				@Override
				public void focus(FocusEvent event) {

				}

			});

			btnCancel.addClickListener(new Button.ClickListener() {

				private static final long serialVersionUID = 3115121215716705673L;

				@Override
				public void buttonClick(ClickEvent event) {

					cAllProf.setVisible(true);
					cPlaceholder.setVisible(false);

				}
			});

			btnSave.addClickListener(new Button.ClickListener() {

				private static final long serialVersionUID = 7591799098570751956L;

				@Override
				public void buttonClick(ClickEvent event) {

					Object spn = comboProfiles.getValue();
					if (spn == null || spn.toString().trim().isEmpty()) {
						Notification.show("Please provide a profile name",
								Notification.Type.ERROR_MESSAGE);

						return;
					}

					if (!ptid.equals("3"))
						if (optMultTrans.getValue() == null
								|| ((Collection<?>) optMultTrans.getValue())
										.size() == 0) {
							Notification
									.show("Please select at least a Transaction Type",
											Notification.Type.ERROR_MESSAGE);
							optMultTrans.focus();
							return;
						}

					if (comboThresholds.getValue() == null) {
						Notification.show("Please select a Threshold",
								Notification.Type.ERROR_MESSAGE);

						comboThresholds.focus();
						return;
					}

					if (tFProf.getValue() == null
							|| tFProf.getValue().toString().trim().isEmpty()) {
						Notification.show("Pleas provide amount",
								Notification.Type.ERROR_MESSAGE);

						tFProf.focus();
						return;
					}

					String pn = spn.toString();
					Integer pid = Integer.parseInt(hmAllOperators.get(pn));
					Collection<?> ctids = (Collection<?>) optMultTrans
							.getValue();

					int[] ttids = new int[ctids.size()];
					int ix = 0;
					for (Object tid : ctids) {
						ttids[ix] = Integer.valueOf(tid.toString()).intValue();
						ix++;
					}

					Profile profile = hmProfIDs.get(pn);
					String threshold = comboThresholds.getValue().toString();

					String amount = tFProf.getValue();

					String response = null;

					try {

						System.out.println(pn + " : " + pid);
						response = UserManagementService.addProfileThreshold(
								pn, ttids, (int) pid,
								Integer.parseInt(threshold),
								Integer.parseInt(profile.getProfiletypeid()),
								amount);

					} catch (Exception e) {
						Notification.show(e.getMessage(),
								Notification.Type.ERROR_MESSAGE);
						e.printStackTrace();
						return;
					}
					if (response == null || response.trim().isEmpty()) {
						Notification.show("Unknown operation status",
								Notification.Type.ERROR_MESSAGE);
						return;
					}

					if (response.toUpperCase().contains("SUCCESS")) {
						NotifCustom.show("Threshold", pn
								+ "'s Threshold was successfully configured.");

						cAllProf.setVisible(true);
						cPlaceholder.setVisible(false);

						return;
					} else {
						Notification.show(response,
								Notification.Type.ERROR_MESSAGE);
					}

				}
			});

		}

		@Override
		public void buttonClick(ClickEvent event) {

			ptid = hmProfIDs.get(comboProfiles.getValue().toString())
					.getProfiletypeid();

			if (ptid.equals("3")) {
				optMultTrans.setVisible(false);

			} else {
				optMultTrans.setVisible(true);
			}

			tFProfx.setEnabled(true);
			tFProfx.setReadOnly(false);

			tFProfx.setValue(comboProfiles.getValue().toString());

			tFProfx.setEnabled(false);
			tFProfx.setReadOnly(true);

			cPlaceholder.setVisible(true);
			cAllProf.setVisible(false);
			tFProf.setValue("");
			combo.select(null);
			addThresholds(comboThresholds, ptid);
			// addTransactionTypes(optMultTrans);

		}

		private void addThresholds(ComboBox optMult, String pid) {
			optMult.removeAllItems();

			if (rs == null) {
				try {
					rs = new ReportingService();
				} catch (AxisFault e) {

					e.printStackTrace();
				}
			}

			try {

				// System.out.println(pid);

				hmThresholdTypes = rs.getThresholdTypes(pid);

				Set<Entry<String, String>> set = hmThresholdTypes.entrySet();
				for (Entry<String, String> e : set) {

					Object key = e.getKey();
					optMult.addItem(key);
					optMult.setItemCaption(key, e.getValue());

				}

			} catch (RemoteException | DataServiceFault e) {

				e.printStackTrace();
			}

		}

		private void addTransactionTypes(ComboBox combo) {
			combo.removeAllItems();

			if (rs == null) {
				try {
					rs = new ReportingService();
				} catch (AxisFault e) {

					e.printStackTrace();
				}
			}

			try {

				hmTransactionTypes = rs.getTransactionTypes();
				Set<Entry<String, String>> set = hmTransactionTypes.entrySet();
				for (Entry<String, String> e : set) {

					Object key = e.getKey();
					combo.addItem(key);
					// combo.setItemCaption(key, e.getValue());

				}

			} catch (RemoteException | DataServiceFault e) {

				e.printStackTrace();
			}

		}

	}

	@SuppressWarnings("unchecked")
	private void displayFeesConfigurations(Table tb, String tt) {

		IndexedContainer ct = new IndexedContainer();
		ct.addContainerProperty("S/N", String.class, "");
		ct.addContainerProperty("Min.", String.class, "");
		ct.addContainerProperty("Max.", String.class, "");
		ct.addContainerProperty("Matrix", String.class, "");
		ct.addContainerProperty("Amount", String.class, "");
		// ct.addContainerProperty("Last Updated", String.class, "");

		int x = 0;
		dfamt = 0D;

		if (isCommission)
			try {

				System.out.println(hmServiceCommission.size()
						+ " :++++++++++++++++++");

				for (Commissionfee sf : hmServiceCommission.get(tt)) {

					x++;

					// sf.getServicefeepropertiesid();

					String min = Double.toString(sf.getMinimumamount());
					String max = Double.toString(sf.getMaximumamount());
					String mat = sf.getCommission_fee_type();
					String amt = Double.toString(sf.getCommission_fee());
					// String date = rs.getString("Last Updated");

					Object ctobjr = ct.addItem();
					Item rct = ct.getItem(ctobjr);

					Property<String> pct = rct.getItemProperty("Min.");
					pct.setValue(min);

					pct = rct.getItemProperty("Max.");
					pct.setValue(max);

					pct = rct.getItemProperty("Matrix");
					pct.setValue(mat);

					pct = rct.getItemProperty("Amount");
					pct.setValue(amt);

					pct = rct.getItemProperty("S/N");
					pct.setValue(Integer.toString(x));

					try {
						Double d = Double.valueOf(amt);
						dfamt = dfamt + d;
					} catch (Exception e) {

					}

				}

				if (x == 0)
					Notification
							.show("No Configurations set for selected Transaction type",
									Notification.Type.WARNING_MESSAGE);

			} catch (Exception e) {
				Notification.show(e.getMessage(),
						Notification.Type.ERROR_MESSAGE);
				e.printStackTrace();
			}
		else {

			try {

				for (Servicefee sf : hmServiceFees.get(tt)) {
					x++;
					String min = Double.toString(sf.getMinimumamount());
					String max = Double.toString(sf.getMaximumamount());
					String mat = sf.getService_fee_type();
					String amt = Double.toString(sf.getService_fee());
					// String date = rs.getString("Last Updated");

					Object ctobjr = ct.addItem();
					Item rct = ct.getItem(ctobjr);

					Property<String> pct = rct.getItemProperty("Min.");
					pct.setValue(min);

					pct = rct.getItemProperty("Max.");
					pct.setValue(max);

					pct = rct.getItemProperty("Matrix");
					pct.setValue(mat);

					pct = rct.getItemProperty("Amount");
					pct.setValue(amt);

					pct = rct.getItemProperty("S/N");
					pct.setValue(Integer.toString(x));

					try {
						Double d = Double.valueOf(amt);
						dfamt = dfamt + d;
					} catch (Exception e) {

					}

				}

				if (x == 0)
					Notification
							.show("No Configurations set for selected Transaction type",
									Notification.Type.WARNING_MESSAGE);

			} catch (Exception e) {
				Notification.show(e.getMessage(),
						Notification.Type.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}

		tb.setWidth("100%");
		tb.setHeightUndefined();
		tb.setContainerDataSource(ct);
		tb.setPageLength(0);
		tb.setSelectable(true);
		tbFees = tb;

	}

	private HorizontalLayout getAddFeesC(String type,
			final boolean isEditFeesx, final HorizontalLayout cPlaceholder,
			final Button btnx, final HorizontalLayout cPermsList,
			final Table tb, final HorizontalLayout cPermsActions) {

		cArrLItemContent = new ArrayList<>(4);
		arrLRangeFG = new ArrayList<>();
		arrLMatFG = new ArrayList<>();
		arrLAllFG = new ArrayList<>();
		isTiered = true;
		tabType = type;
		isEditFees = isEditFeesx;

		HorizontalLayout cManage = new HorizontalLayout();

		cManage.setWidthUndefined();
		cManage.setWidthUndefined();

		VerticalLayout cChoose = new VerticalLayout();
		cChoose.setSizeUndefined();
		cChoose.setStyleName("frm_add_user frm_manage_commission");

		VerticalLayout addUserHeader = new VerticalLayout();
		addUserHeader.setWidthUndefined();
		addUserHeader.setHeightUndefined();
		addUserHeader.setSpacing(true);

		cChoose.addComponent(addUserHeader);

		// Label lbAddUser = new Label("Manage " + tabType);
		// lbAddUser.setStyleName("label_add_user");
		// lbAddUser.setSizeUndefined();

		// cChoose.addComponent(lbAddUser);
		// Label lbChoose = new Label("Please choose...");

		final ComboBox comboConditionType = new ComboBox("Condition Type");
		comboConditionType.addItem(arrConValues[0]);
		comboConditionType.setItemCaption(arrConValues[0], "Amount");
		comboConditionType.addItem(arrConValues[1]);
		comboConditionType.setItemCaption(arrConValues[1], "Fee");
		comboConditionType.select(arrConValues[0]);

		final ComboBox comboModelType = new ComboBox("Model Type");
		comboModelType.addItem(arrModelValues[0]);
		comboModelType.setItemCaption(arrModelValues[0], "Tiered");
		comboModelType.addItem(arrModelValues[1]);
		comboModelType.setItemCaption(arrModelValues[1], "None");
		comboModelType.select(arrModelValues[0]);

		Item row = new PropertysetItem();

		Property<String> pconTypeID = new ObjectProperty<>(arrConValues[0]);
		Property<String> pmodelTypeID = new ObjectProperty<>(arrModelValues[0]);
		row.addItemProperty("CONID", pconTypeID);
		row.addItemProperty("MODID", pmodelTypeID);

		otherfg = new FieldGroup(row);
		otherfg.bind(comboConditionType, "CONID");
		otherfg.bind(comboModelType, "MODID");

		otherfg.addCommitHandler(new CommitHandler() {
			private static final long serialVersionUID = 6144936023943646696L;

			@Override
			public void preCommit(CommitEvent commitEvent)
					throws CommitException {

				ComboBox comboAllFCOperators = null;
				ComboBox comboTT = null;
				if (isCommission) {
					comboAllFCOperators = comboAllCommissionOperators;
					comboTT = comboCommissionsTT;
				} else {
					comboAllFCOperators = comboAllFeesOperators;
					comboTT = comboFeesTT;
				}

				comboAllFCOperators.setRequired(false);
				comboConditionType.setRequired(false);
				comboModelType.setRequired(false);
				comboTT.setRequired(false);
				if (comboAllFCOperators.getValue() == null) {
					comboAllFCOperators.setRequired(true);
					Notification.show("Field marked with (*) is required.",
							Notification.Type.WARNING_MESSAGE);
					throw new CommitException("Field is required.");
				} else if (comboConditionType.getValue() == null) {
					comboConditionType.setRequired(true);
					Notification.show("Field marked with (*) is required.",
							Notification.Type.WARNING_MESSAGE);
					throw new CommitException("Field is required.");

				} else if (comboModelType.getValue() == null) {
					comboModelType.setRequired(true);
					Notification.show("Field marked with (*) is required.",
							Notification.Type.WARNING_MESSAGE);
					throw new CommitException("Field is required.");

				} else if (comboTT.getValue() == null) {
					comboTT.setRequired(true);
					Notification.show("Field marked with (*) is required.",
							Notification.Type.WARNING_MESSAGE);
					throw new CommitException("Field is required.");
				} else {
					comboAllFCOperators.setRequired(false);
					comboConditionType.setRequired(false);
					comboModelType.setRequired(false);
					comboTT.setRequired(false);
					return;
				}
			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {

			}

		});

		// comboAllFCOperators.addFocusListener(new FocusListener() {
		// private static final long serialVersionUID = 1470960826655063334L;
		//
		// @Override
		// public void focus(FocusEvent event) {
		//
		// getOperators();
		// Set<Entry<String, String>> es = (Set<Entry<String, String>>) hmOp
		// .entrySet();
		// for (Entry<String, String> e : es) {
		// comboAllFCOperators.addItem(e.getKey());
		// comboAllFCOperators.setItemCaption(e.getKey(),
		// e.getValue());
		// }
		//
		// }
		//
		// });

		// cChoose.addComponent(lbChoose);
		// cChoose.addComponent(comboAllFCOperators);

		// cChoose.addComponent(comboConditionType);
		// cChoose.addComponent(comboModelType);
		// cChoose.addComponent(comboTT);
		// cChoose.setStyleName("c_choose");

		VerticalLayout cAttr = new VerticalLayout();
		cAttr.setSpacing(false);
		cAttr.setMargin(new MarginInfo(false, false, true, false));
		cAttr.setStyleName("c_attr");

		// final Label tFOp = new Label();
		// tFOp.setCaption("Selected Operator: ");
		// tFOp.setValue(comboAllFCOperators
		// .getItemCaption(comboAllFCOperators.getValue()));
		// tFOp.setStyleName("label_add_user");

		FormLayout cOp = new FormLayout();
		// cOp.addComponent(tFOp);
		cAttr.addComponent(cOp);

		ComboBox comboAllFCOperators = null;
		ComboBox comboTT = null;
		if (isCommission) {
			comboAllFCOperators = comboAllCommissionOperators;
			comboTT = comboCommissionsTT;
		} else {
			comboAllFCOperators = comboAllFeesOperators;
			comboTT = comboFeesTT;
		}

		HorizontalLayout cAttrItem = new HorizontalLayout();
		VerticalLayout cItemContent = new VerticalLayout();
		Label lbAttr = new Label(comboTT.getCaption());
		lbAttr.setSizeUndefined();
		cItemContent.setSizeFull();

		lbAttr.setStyleName("label_add_user attr");
		final Label lbAttrVal = new Label(comboTT.getItemCaption(comboTT
				.getValue()));

		HorizontalLayout cAttrVal = new HorizontalLayout();
		cAttrVal.addComponent(lbAttrVal);
		lbAttrVal.setStyleName("attr_val");
		cAttrVal.setStyleName("c_attr_val");
		cItemContent.addComponent(lbAttr);
		cItemContent.addComponent(cAttrVal);
		cItemContent.setComponentAlignment(cAttrVal, Alignment.MIDDLE_CENTER);
		cItemContent.setComponentAlignment(lbAttr, Alignment.TOP_LEFT);
		cAttrItem.addComponent(cItemContent);
		cAttr.addComponent(cAttrItem);

		// MIN

		VerticalLayout cItemContentMin = new VerticalLayout();
		cArrLItemContent.add(cItemContentMin);
		lbAttr = new Label("Min.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMin.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentMin);

		// MAX

		VerticalLayout cItemContentMax = new VerticalLayout();
		cArrLItemContent.add(cItemContentMax);
		lbAttr = new Label("Max.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMax.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentMax);

		// MATRIX
		VerticalLayout cItemContentMat = new VerticalLayout();
		cArrLItemContent.add(cItemContentMat);
		lbAttr = new Label("Matrix");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMat.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentMat);

		// AMOUNT

		VerticalLayout cItemContentAmt = new VerticalLayout();
		cArrLItemContent.add(cItemContentAmt);
		lbAttr = new Label("Amount (%/N)");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentAmt.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentAmt);

		HorizontalLayout cControls = new HorizontalLayout();
		final Button btnAdd = new Button("+");
		btnAdd.setDescription("Add new row");
		btnAdd.setStyleName("btn_link");

		Button btnRemove = new Button("-");
		btnRemove.setStyleName("btn_link");
		btnRemove.setDescription("Remove the last row.");

		Button btnSave = new Button(FontAwesome.SAVE);
		btnSave.setStyleName("btn_link");
		btnSave.setDescription("Save Configurations");

		Button btnCancel = new Button(FontAwesome.UNDO);
		btnCancel.setStyleName("btn_link");
		btnCancel.setDescription("Cancel");

		cControls.addComponent(btnAdd);
		cControls.addComponent(btnRemove);
		cControls.addComponent(btnSave);
		cControls.addComponent(btnCancel);

		cAttr.addComponent(cAttrItem);
		cAttr.addComponent(cControls);
		cAttr.setComponentAlignment(cControls, Alignment.BOTTOM_RIGHT);
		// cManage.addComponent(cChoose);
		cManage.addComponent(cAttr);

		comboTT.setNullSelectionAllowed(false);

		comboTT.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -4750457498573552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				ComboBox comboAllFCOperators = null;
				ComboBox comboTT = null;
				if (isCommission) {
					comboAllFCOperators = comboAllCommissionOperators;
					comboTT = comboCommissionsTT;
				} else {
					comboAllFCOperators = comboAllFeesOperators;
					comboTT = comboFeesTT;
				}

				lbAttrVal.setValue(comboTT.getItemCaption(comboTT.getValue()));
			}

		});

		comboAllFCOperators.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -4750457498573552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// tFOp.setValue(comboAllFCOperators
				// .getItemCaption(comboAllFCOperators.getValue()));
			}

		});

		btnCancel.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -7697295961876196440L;

			@Override
			public void buttonClick(ClickEvent event) {

				ComboBox comboAllFCOperators = null;
				ComboBox comboTT = null;
				if (isCommission) {
					comboAllFCOperators = comboAllCommissionOperators;
					comboTT = comboCommissionsTT;
				} else {
					comboAllFCOperators = comboAllFeesOperators;
					comboTT = comboFeesTT;
				}

				if (isCommission) {

					displayFeesConfigurations(tb, comboTT.getValue().toString());
					cPermsList.removeAllComponents();
					cPermsList.addComponent(tb);
					cPlaceholder.setVisible(true);
					isEditFees = false;
					btnEditFees.setIcon(FontAwesome.EDIT);
					btnEditFees.setDescription("Edit Configurations Fees");

					cPermsActions.setVisible(true);
					optSetupType.setEnabled(false);

					// CANCEL

					// comboAllFCOperators.select(null);
					// comboAllFCOperators.setValue(null);
					// comboTT.setVisible(false);
					return;
				}

				cPlaceholder.setVisible(false);
				comboTT.setVisible(false);
				btnx.setVisible(true);
				isEditFees = false;
			}
		});

		comboConditionType.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -5878577330071011374L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() == null)
					return;
				if (event.getProperty().getValue().toString().trim()
						.equals(arrConValues[0])) {
					comboModelType.select(arrModelValues[0]);
				} else {
					comboModelType.select(arrModelValues[1]);
				}
			}

		});

		comboModelType.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -6418325605387194047L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (comboModelType.getValue() == null)
					return;
				if (comboModelType.getValue().toString()
						.equals(arrModelValues[0])) {

					if (arrLRangeFG.size() == 0)
						return;

					removeAll();
					FieldGroup fg = arrLRangeFG.get(0);
					TextField min = (TextField) fg.getField("Min");
					TextField max = (TextField) fg.getField("Max");
					min.setValue("");
					min.setEnabled(true);

					max.setValue("");
					max.setEnabled(true);
					isTiered = true;
					comboConditionType.select(arrConValues[0]);
					return;

				} else {
					if (arrLRangeFG.size() == 0)
						return;
					removeAll();
					FieldGroup fg = arrLRangeFG.get(0);
					TextField min = (TextField) fg.getField("Min");
					TextField max = (TextField) fg.getField("Max");
					min.setValue("0.00");
					min.setEnabled(false);

					max.setValue("0.00");
					max.setEnabled(false);
					isTiered = false;
					comboConditionType.select(arrConValues[1]);
					return;
				}

			}

		});

		btnAdd.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 8105347974986024315L;

			@Override
			public void buttonClick(ClickEvent event) {
				add();

			}
		});

		btnRemove.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3921995443687711235L;

			@Override
			public void buttonClick(ClickEvent event) {
				remove();

			}
		});

		btnSave.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -7900935606088856031L;

			@Override
			public void buttonClick(ClickEvent event) {
				/*
				 * Commit to server.
				 */

				if (isReadyToCommit(tabType, otherfg)) {
					if (commit(cPlaceholder)) {

						ComboBox comboAllFCOperators = null;
						ComboBox comboTT = null;
						if (isCommission) {
							comboAllFCOperators = comboAllCommissionOperators;
							comboTT = comboCommissionsTT;
						} else {
							comboAllFCOperators = comboAllFeesOperators;
							comboTT = comboFeesTT;
						}

						if (isCommission) {
							System.out
									.println("-------vvvvv--vv---v-v----------------");
							String pname = comboAllFCOperators.getValue()
									.toString().trim();
							hmServiceFees = getServiceFees(Integer
									.parseInt(hmAllOperators.get(pname)));

							hmServiceCommission = getServiceCommissions(Integer
									.parseInt(hmAllOperators.get(pname)));

							displayFeesConfigurations(tb, comboTT.getValue()
									.toString());
							cPermsList.removeAllComponents();
							cPermsList.addComponent(tb);
							cPlaceholder.setVisible(true);

							btnEditCommissions.setIcon(FontAwesome.EDIT);
							btnEditCommissions
									.setDescription("Edit Commissions Configurations");
							btnEditCommissions.setVisible(true);
							btnDeleteCommissions.setVisible(true);

							cPermsActions.setVisible(true);

							btnCancelCommissions.setVisible(false);

							optSetupType.setVisible(true);
							optSetupType.setEnabled(false);
							comboTT.setEnabled(false);

							isEditFees = false;

							// comboAllFCOperators.select(null);
							// comboAllFCOperators.setValue(null);
							// comboTT.setVisible(false);
							comboTT.setEnabled(false);
							// comboTT.select(null);

							cPermsActions.setVisible(true);

							return;

						}

						String pname = comboAllFCOperators.getValue()
								.toString().trim();
						hmServiceFees = getServiceFees(Integer
								.parseInt(hmAllOperators.get(pname)));

						displayFeesConfigurations(tb, comboTT.getValue()
								.toString());
						cPermsList.removeAllComponents();
						cPermsList.addComponent(tb);
						cPlaceholder.setVisible(true);
						isEditFees = false;
						btnEditFees.setIcon(FontAwesome.EDIT);
						btnEditFees.setDescription("Edit Configurations Fees");
						// btnCancelCommissions.setVisible(false);

					} else {

					}
					return;
				} else {
					return;
				}

			}
		});

		// Clear fields for fresh entry.

		hmAllFG.clear();

		if (!isEditFees) {

			if (hmAllFG.size() == 0) {
				add();
				return cManage;
			}

			if (!hmAllFG.containsKey(type)) {
				add();
				return cManage;
			}

			ArrayList<ArrayList<FieldGroup>> allFG = hmAllFG.get(type);
			FieldGroup dfg = hmDFG.get(type);
			String mt = dfg.getField("MODID").getValue().toString().trim();
			String ct = dfg.getField("CONID").getValue().toString().trim();
			isTiered = mt.equals(arrModelValues[0]);
			comboModelType.select(mt);
			comboConditionType.select(ct);

			add(allFG);

			return cManage;
		} else {
			add(tbFees);
			return cManage;
		}

	}

	private void removeAll() {
		removeAllRange();
		removeAllMatrix();
	}

	private void remove() {
		removeRange();
		removeMatrix();
	}

	private void removeAllRange() {
		if (removeRange() == -1) {
			return;
		}
		removeAllRange();
	}

	private void removeAllMatrix() {
		if (removeMatrix() == -1) {
			return;
		}
		removeAllMatrix();
	}

	private int removeRange() {
		if (arrLRangeFG.size() < 2)
			return -1;

		arrLRangeFG.remove(arrLRangeFG.size() - 1);
		VerticalLayout cMin = cArrLItemContent.get(0);
		VerticalLayout cMax = cArrLItemContent.get(1);
		cMin.removeComponent(cMin.getComponent(cMin.getComponentCount() - 1));
		cMax.removeComponent(cMax.getComponent(cMax.getComponentCount() - 1));
		return 1;

	}

	private int removeMatrix() {
		if (arrLMatFG.size() < 2)
			return -1;

		arrLMatFG.remove(arrLMatFG.size() - 1);
		VerticalLayout cMat = cArrLItemContent.get(2);
		VerticalLayout cAmt = cArrLItemContent.get(3);
		cMat.removeComponent(cMat.getComponent(cMat.getComponentCount() - 1));
		cAmt.removeComponent(cAmt.getComponent(cAmt.getComponentCount() - 1));
		return 1;

	}

	private void add() {
		addRange();
		addMatrix();
	}

	private void addMatrix() {
		VerticalLayout cMat = cArrLItemContent.get(2);
		VerticalLayout cAmt = cArrLItemContent.get(3);
		final ComboBox comboMat = new ComboBox();
		comboMat.addItem(arrMatValues[0]);
		comboMat.setItemCaption(arrMatValues[0], "%");
		comboMat.addItem(arrMatValues[1]);
		comboMat.setItemCaption(arrMatValues[1], "Fixed");
		comboMat.select(arrMatValues[0]);
		comboMat.setNullSelectionAllowed(false);

		final TextField tfAmt = new TextField();
		cMat.addComponent(comboMat);
		cAmt.addComponent(tfAmt);

		Property<String> pMat = new ObjectProperty<>(arrMatValues[0]);
		Property<String> pAmt = new ObjectProperty<>("");

		Item row = new PropertysetItem();
		row.addItemProperty("Mat", pMat);
		row.addItemProperty("Amt", pAmt);
		FieldGroup fg = new FieldGroup(row);
		fg.bind(comboMat, "Mat");
		fg.bind(tfAmt, "Amt");
		arrLMatFG.add(fg);

		comboMat.addValidator(new MatrixHouseKeeper(comboMat, tfAmt));
		tfAmt.addValidator(new MatrixHouseKeeper(comboMat, tfAmt));
		comboMat.setValidationVisible(true);
		tfAmt.setValidationVisible(true);
		comboMat.setImmediate(true);
		tfAmt.setImmediate(true);

		fg.addCommitHandler(new CommitHandler() {
			private static final long serialVersionUID = -4960371331680442909L;

			@Override
			public void preCommit(CommitEvent commitEvent)
					throws CommitException {
				tfAmt.setRequired(false);
				comboMat.setRequired(false);
				if (comboMat.getValue() == null) {
					comboMat.setRequired(true);
					Notification.show("Fields marked with (*) are required.",
							Notification.Type.WARNING_MESSAGE);
					throw new CommitException("Field required.");
				} else if (tfAmt.getValue().trim().isEmpty()) {
					tfAmt.setRequired(true);
					Notification.show("Fields marked with (*) are required.",
							Notification.Type.WARNING_MESSAGE);
					throw new CommitException("Field required.");
				} else {
					tfAmt.setRequired(false);
					comboMat.setRequired(false);
					return;
				}

			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {

			}

		});

		comboMat.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -47504574989952155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					comboMat.setComponentError(null);
					tfAmt.setComponentError(null);
					comboMat.validate();
				} catch (MethodException e) {

				}
			}

		});

		tfAmt.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -4750457473552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					tfAmt.setComponentError(null);
					comboMat.setComponentError(null);
					tfAmt.validate();

				} catch (MethodException e) {

				}

			}

		});

	}

	private void addRange() {
		VerticalLayout cMin = cArrLItemContent.get(0);
		VerticalLayout cMax = cArrLItemContent.get(1);
		final TextField tfMin = new TextField();
		final TextField tfMax = new TextField();
		cMin.addComponent(tfMin);
		cMax.addComponent(tfMax);

		Property<String> pMin = new ObjectProperty<>("");
		Property<String> pMax = new ObjectProperty<>("");

		Item row = new PropertysetItem();
		row.addItemProperty("Min", pMin);
		row.addItemProperty("Max", pMax);
		FieldGroup fg = new FieldGroup(row);
		fg.bind(tfMin, "Min");
		fg.bind(tfMax, "Max");
		arrLRangeFG.add(fg);
		int index = arrLRangeFG.size() - 1;
		tfMin.addValidator(new RangeHouseKeeper(tfMin, tfMax, index));
		tfMax.addValidator(new RangeHouseKeeper(tfMin, tfMax, index));
		tfMin.setValidationVisible(true);
		tfMax.setValidationVisible(true);
		tfMin.setImmediate(true);
		tfMax.setImmediate(true);

		if (!isTiered) {
			tfMin.setValue("0.00");
			tfMin.setEnabled(false);
			tfMax.setValue("0.00");
			tfMax.setEnabled(false);
		}

		fg.addCommitHandler(new CommitHandler() {
			private static final long serialVersionUID = -4960371331680442909L;

			@Override
			public void preCommit(CommitEvent commitEvent)
					throws CommitException {
				tfMin.setRequired(false);
				tfMax.setRequired(false);
				if (tfMin.getValue().trim().isEmpty()) {
					tfMin.setRequired(true);
					Notification.show("Fields marked with (*) are required.",
							Notification.Type.WARNING_MESSAGE);
					throw new CommitException("Field required.");
				}
				if (tfMax.getValue().trim().isEmpty()) {
					tfMax.setRequired(true);
					Notification.show("Fields marked with (*) are required.",
							Notification.Type.WARNING_MESSAGE);
					throw new CommitException("Field required.");
				} else {
					tfMin.setRequired(false);
					tfMax.setRequired(false);
					return;
				}

			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {

			}

		});

		tfMin.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -47504574989952155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					tfMin.setComponentError(null);
					tfMax.setComponentError(null);
					tfMin.validate();
				} catch (MethodException e) {

				}
			}

		});

		tfMax.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -4750457473552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					tfMax.setComponentError(null);
					tfMin.setComponentError(null);
					tfMax.validate();
				} catch (MethodException e) {

				}

			}

		});

	}

	private class RangeHouseKeeper implements Validator {

		private static final long serialVersionUID = 8429528148551522733L;
		private TextField tfMin;
		private TextField tfMax;
		private Float vMin;
		private Float vMax;
		private int fgIndex;

		RangeHouseKeeper(TextField tfMin, TextField tfMax, int fgIndex) {
			this.tfMin = tfMin;
			this.tfMax = tfMax;
			this.fgIndex = fgIndex;

		}

		@Override
		public void validate(Object v) throws InvalidValueException,
				NumberFormatException {

			if (!isTiered)
				return;

			if (v.toString().trim().isEmpty())
				return;

			try {
				Float.valueOf(v.toString());
			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			String sMin = tfMin.getValue().trim();
			String sMax = tfMax.getValue().trim();

			if (sMin.isEmpty() || sMax.isEmpty())
				return;

			try {
				vMin = Float.parseFloat(sMin);

			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			try {
				vMax = Float.parseFloat(sMax);

			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			if (vMin > vMax) {
				throw new InvalidValueException("Invalid range.");

			}

			if (arrLRangeFG.size() > fgIndex + 1) {
				FieldGroup nxtFG = arrLRangeFG.get(fgIndex + 1);
				TextField nxtMin = (TextField) nxtFG.getField("Min");
				Double nxt = Double.valueOf(tfMax.getValue()) + 1;
				nxtMin.setValue(nxt.toString());
				nxtMin.setEnabled(false);

				return;

			}
		}
	}

	private class MatrixHouseKeeper implements Validator {

		private static final long serialVersionUID = 8429528148551522733L;
		private ComboBox comboMat;
		private TextField tfAmt;
		private String mat;
		private Float vAmt;

		MatrixHouseKeeper(ComboBox comboMat, TextField tfAmt) {
			this.comboMat = comboMat;
			this.tfAmt = tfAmt;
		}

		@Override
		public void validate(Object v) throws InvalidValueException,
				NumberFormatException {
			if (v == null)
				return;
			if (v.toString().trim().isEmpty())
				return;

			if (tfAmt.getValue().trim().isEmpty())
				return;

			try {
				Float.valueOf(tfAmt.getValue());
			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			mat = comboMat.getValue().toString();
			String sAmt = tfAmt.getValue().trim();

			try {
				vAmt = Float.parseFloat(sAmt);

			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			if (mat.equals(arrMatValues[0]) && vAmt > 100) {
				throw new InvalidValueException("Invalid Percentage.");
			}

		}
	}

	private void add(Table tb) {
		addRange(tb);
		addMatrix(tb);
	}

	private void add(ArrayList<ArrayList<FieldGroup>> allFG) {
		addRange(allFG.get(0));
		addMatrix(allFG.get(1));

	}

	private void addMatrix(ArrayList<FieldGroup> arrLFG) {
		VerticalLayout cMat = cArrLItemContent.get(2);
		VerticalLayout cAmt = cArrLItemContent.get(3);

		for (int i = 0; i < arrLFG.size(); i++) {
			FieldGroup mfg = arrLFG.get(i);
			cMat.addComponent(mfg.getField("Mat"));
			cAmt.addComponent(mfg.getField("Amt"));
			arrLMatFG.add(mfg);

		}

	}

	private void addRange(ArrayList<FieldGroup> arrLRFG) {
		VerticalLayout cMin = cArrLItemContent.get(0);
		VerticalLayout cMax = cArrLItemContent.get(1);

		for (int i = 0; i < arrLRFG.size(); i++) {
			FieldGroup rfg = arrLRFG.get(i);
			cMin.addComponent(rfg.getField("Min"));
			cMax.addComponent(rfg.getField("Max"));
			arrLRangeFG.add(rfg);
		}

	}

	private void addRange(Table tb) {

		VerticalLayout cMin = cArrLItemContent.get(0);
		VerticalLayout cMax = cArrLItemContent.get(1);

		// Collection<?> rids = null;
		Collection<?> rids = tb.getContainerDataSource().getItemIds();
		int t = tbFees.getContainerDataSource().size();
		int i = 0;
		String[] setMin = new String[t];
		String[] setMax = new String[t];

		LinkedHashSet<String> nSetMin = new LinkedHashSet<>();
		LinkedHashSet<String> nSetMax = new LinkedHashSet<>();

		hmTiers.put("min", setMin);
		hmTiers.put("max", setMax);

		hmNewTiers.put("min", nSetMin);
		hmNewTiers.put("max", nSetMax);

		for (Object rid : rids) {
			final TextField tfMin = new TextField();
			final TextField tfMax = new TextField();
			cMin.addComponent(tfMin);
			cMax.addComponent(tfMax);

			Item r = tbFees.getItem(rid);

			Property<String> pMin = new ObjectProperty<>(r
					.getItemProperty("Min.").getValue().toString());
			Property<String> pMax = new ObjectProperty<>(r
					.getItemProperty("Max.").getValue().toString());

			Item row = new PropertysetItem();
			row.addItemProperty("Min", pMin);
			row.addItemProperty("Max", pMax);

			FieldGroup fg = new FieldGroup(row);
			fg.bind(tfMin, "Min");
			fg.bind(tfMax, "Max");
			arrLRangeFG.add(fg);
			int index = arrLRangeFG.size() - 1;
			tfMin.addValidator(new RangeHouseKeeper(tfMin, tfMax, index));
			tfMax.addValidator(new RangeHouseKeeper(tfMin, tfMax, index));
			tfMin.setValidationVisible(true);
			tfMax.setValidationVisible(true);
			tfMin.setImmediate(true);
			tfMax.setImmediate(true);

			if (!isOptTiered) {

				tfMin.setEnabled(false);

				tfMax.setEnabled(false);
			}

			setMin[i] = pMin.getValue();
			setMax[i] = pMax.getValue();

			i++;

			nSetMin.add(pMin.getValue());
			nSetMax.add(pMax.getValue());

			fg.addCommitHandler(new CommitHandler() {
				private static final long serialVersionUID = -4960371331680442909L;

				@Override
				public void preCommit(CommitEvent commitEvent)
						throws CommitException {
					tfMin.setRequired(false);
					tfMax.setRequired(false);
					if (tfMin.getValue().trim().isEmpty()) {
						tfMin.setRequired(true);
						Notification.show(
								"Fields marked with (*) are required.",
								Notification.Type.WARNING_MESSAGE);
						throw new CommitException("Field required.");
					}
					if (tfMax.getValue().trim().isEmpty()) {
						tfMax.setRequired(true);
						Notification.show(
								"Fields marked with (*) are required.",
								Notification.Type.WARNING_MESSAGE);
						throw new CommitException("Field required.");
					} else {
						tfMin.setRequired(false);
						tfMax.setRequired(false);
						return;
					}

				}

				@Override
				public void postCommit(CommitEvent commitEvent)
						throws CommitException {

				}

			});

			tfMin.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = -47504574989952155L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					try {
						tfMin.setComponentError(null);
						tfMax.setComponentError(null);
						tfMin.validate();
					} catch (MethodException e) {

					}
				}

			});

			tfMax.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = -4750457473552155L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					try {
						tfMax.setComponentError(null);
						tfMin.setComponentError(null);
						tfMax.validate();
					} catch (MethodException e) {

					}

				}

			});
		}

	}

	private void addMatrix(Table tb) {
		VerticalLayout cMat = cArrLItemContent.get(2);
		VerticalLayout cAmt = cArrLItemContent.get(3);

		// Collection<?> rids = null;
		Collection<?> rids = tb.getContainerDataSource().getItemIds();

		int t = tbFees.getContainerDataSource().size();
		int i = 0;
		String[] setCon = new String[t];
		String[] setAmt = new String[t];

		LinkedHashSet<String> nSetCon = new LinkedHashSet<>();
		LinkedHashSet<String> nSetAmt = new LinkedHashSet<>();

		hmTiers.put("con", setCon);
		hmTiers.put("amt", setAmt);

		hmNewTiers.put("con", nSetCon);
		hmNewTiers.put("amt", nSetAmt);

		if (rids == null)
			rids = Arrays.asList(new String[] { "xy", "xm" });
		for (Object rid : rids) {

			final ComboBox comboMat = new ComboBox();
			comboMat.addItem(arrMatValues[0]);
			comboMat.setItemCaption(arrMatValues[0], "%");
			comboMat.addItem(arrMatValues[1]);
			comboMat.setItemCaption(arrMatValues[1], "Fixed");
			comboMat.select(arrMatValues[0]);

			Item r = tb.getItem(rid);

			comboMat.setNullSelectionAllowed(false);

			final TextField tfAmt = new TextField();
			cMat.addComponent(comboMat);
			cAmt.addComponent(tfAmt);

			Property<String> pMat = new ObjectProperty<>(r
					.getItemProperty("Matrix").getValue().toString());
			Property<String> pAmt = new ObjectProperty<>(r
					.getItemProperty("Amount").getValue().toString());

			Item row = new PropertysetItem();
			row.addItemProperty("Mat", pMat);
			row.addItemProperty("Amt", pAmt);
			FieldGroup fg = new FieldGroup(row);
			fg.bind(comboMat, "Mat");
			fg.bind(tfAmt, "Amt");
			arrLMatFG.add(fg);

			comboMat.addValidator(new MatrixHouseKeeper(comboMat, tfAmt));
			tfAmt.addValidator(new MatrixHouseKeeper(comboMat, tfAmt));
			comboMat.setValidationVisible(true);
			tfAmt.setValidationVisible(true);
			comboMat.setImmediate(true);
			tfAmt.setImmediate(true);

			if (!isOptTiered) {
				comboMat.setEnabled(false);
				tfAmt.setEnabled(false);
			}

			setCon[i] = pMat.getValue();
			setAmt[i] = pAmt.getValue();
			i++;

			nSetCon.add(pMat.getValue());
			nSetAmt.add(pAmt.getValue());

			fg.addCommitHandler(new CommitHandler() {
				private static final long serialVersionUID = -4960371331680442909L;

				@Override
				public void preCommit(CommitEvent commitEvent)
						throws CommitException {
					tfAmt.setRequired(false);
					comboMat.setRequired(false);
					if (comboMat.getValue() == null) {
						comboMat.setRequired(true);
						Notification.show(
								"Fields marked with (*) are required.",
								Notification.Type.WARNING_MESSAGE);
						throw new CommitException("Field required.");
					} else if (tfAmt.getValue().trim().isEmpty()) {
						tfAmt.setRequired(true);
						Notification.show(
								"Fields marked with (*) are required.",
								Notification.Type.WARNING_MESSAGE);
						throw new CommitException("Field required.");
					} else {
						tfAmt.setRequired(false);
						comboMat.setRequired(false);
						return;
					}

				}

				@Override
				public void postCommit(CommitEvent commitEvent)
						throws CommitException {

				}

			});

			comboMat.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = -47504574989952155L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					try {
						comboMat.setComponentError(null);
						tfAmt.setComponentError(null);
						comboMat.validate();
					} catch (MethodException e) {

					}
				}

			});

			tfAmt.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = -4750457473552155L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					try {
						tfAmt.setComponentError(null);
						comboMat.setComponentError(null);
						tfAmt.validate();

					} catch (MethodException e) {

					}

				}

			});

		}

	}

	private boolean isReadyToCommit(String type, FieldGroup dfg) {
		// comboAllFCOperators.setRequired(false);
		// if (comboAllFCOperators.getValue() != null) {
		// comboAllFCOperators.setRequired(true);
		// Notification.show("Field marked with (*) is required.",
		// Notification.Type.WARNING_MESSAGE);
		// return false;
		// }
		//
		// comboTxType.setRequired(false);
		// if (comboTxType.getValue() != null) {
		// comboTxType.setRequired(true);
		// Notification.show("Field marked with (*) is required.",
		// Notification.Type.WARNING_MESSAGE);
		// return false;
		// }

		// try {
		// dfg.commit();
		// } catch (CommitException e1) {
		// e1.printStackTrace();
		// return false;
		// }

		for (FieldGroup fg : arrLRangeFG) {
			try {
				fg.commit();
			} catch (CommitException e) {
				e.printStackTrace();
				return false;
			}
		}
		for (FieldGroup fg : arrLMatFG) {
			try {
				fg.commit();
			} catch (CommitException e) {
				e.printStackTrace();
				return false;
			}
		}

		/*
		 * Ready to commit valid values to the server.
		 */

		hmDFG.put(type, dfg);
		arrLAllFG.add(arrLRangeFG);
		arrLAllFG.add(arrLMatFG);
		hmAllFG.put(type, arrLAllFG);

		System.out.println("Current Type of is: " + type
				+ "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		// if (type.equals(FEES) && hmAllFG.size() < 2) {
		// lookedTab = type;
		//
		// if (!isSettingsURL) {
		// String s = UI.getCurrent().getPage().getUriFragment();
		//
		// curURL = (s.indexOf('?') == -1) ? s + "/" : s.substring(0,
		// s.indexOf('?'));
		// isSettingsURL = true;
		// }
		//
		// ManageFeesAndCommModule.btnFees.setStyleName("btn_tab_like");
		// ManageFeesAndCommModule.btnFees.setEnabled(true);
		// ManageFeesAndCommModule.btnComm
		// .setStyleName("btn_tab_like btn_tab_like_active");
		// ManageFeesAndCommModule.btnComm.setEnabled(false);
		//
		// String url = curURL + "?action=" + COMMISSION;
		//
		// if (WorkSpaceManageFeesAndComm.prevSearchFrag.contains(url))
		// WorkSpaceManageFeesAndComm.prevSearchFrag.remove(url);
		// UI.getCurrent().getPage().setUriFragment(url);
		//
		// } else if (type.equals(COMMISSION) && hmAllFG.size() < 2) {
		// lookedTab = type;
		// if (!isSettingsURL) {
		// String s = UI.getCurrent().getPage().getUriFragment();
		// curURL = (s.indexOf('?') == -1) ? s + "/" : s.substring(0,
		// s.indexOf('?'));
		// isSettingsURL = true;
		// }
		//
		// ManageFeesAndCommModule.btnComm.setStyleName("btn_tab_like");
		// ManageFeesAndCommModule.btnComm.setEnabled(true);
		// ManageFeesAndCommModule.btnFees
		// .setStyleName("btn_tab_like btn_tab_like_active");
		// ManageFeesAndCommModule.btnFees.setEnabled(false);
		//
		// String url = curURL + "?action=" + FEES;
		//
		// if (WorkSpaceManageFeesAndComm.prevSearchFrag.contains(url))
		// WorkSpaceManageFeesAndComm.prevSearchFrag.remove(url);
		// UI.getCurrent().getPage().setUriFragment(url);
		//
		// } else {
		// return true;
		// }
		return true;
	}

	private void updateFees() {

	}

	private boolean commit(HorizontalLayout cPlaceholder) {

		ComboBox comboAllFCOperators = null;
		ComboBox comboTT = null;

		if (isCommission) {

			comboAllFCOperators = comboAllCommissionOperators;
			comboTT = comboCommissionsTT;
		} else {
			comboAllFCOperators = comboAllFeesOperators;
			comboTT = comboFeesTT;
		}

		Integer opID = Integer.parseInt(hmAllOperators.get(comboAllFCOperators
				.getValue().toString().trim()));

		Integer txID = 0;

		if (hmTransactionTypes == null || hmTransactionTypes.size() == 0)
			hmTransactionTypes = getTransactionTypes();

		if (isCommission) {

			Integer setupPropertyID = 0;
			BigDecimal commValue = BigDecimal.valueOf(0D);
			Integer setupType = Integer.valueOf(optSetupType.getValue()
					.toString());

			ServiceCommission[] cf = null;
			if (!isOptTiered) {
				cf = new ServiceCommission[] {};

				setupPropertyID = Integer.valueOf(hmExistingFees.get(comboTT
						.getValue().toString()));

				commValue = BigDecimal.valueOf(Double.valueOf(tFCommValue
						.getValue()));

				String s = comboTT.getValue().toString();
				if (!isExistingFeeConfig) {

					s = s.substring(s.indexOf("-") + 1, s.length()).trim();
				}

				System.out.println(":::::::::::::::::::::::::::" + s);
				txID = Integer.valueOf(hmTransactionTypes.get(s));

				System.out.println(hmTransactionTypes.size()
						+ " :::::::::::::+++++++::::::::::::");

			} else {

				txID = Integer.valueOf(hmTransactionTypes.get(comboTT
						.getValue().toString()));

				ArrayList<ArrayList<FieldGroup>> allFeesFG = hmAllFG
						.get(COMMISSION);
				ArrayList<FieldGroup> allRange = allFeesFG.get(0);
				ArrayList<FieldGroup> allMat = allFeesFG.get(1);

				cf = new ServiceCommission[allRange.size()];

				int i = 0;

				boolean isDup = false;

				System.out.print(hmTiers.get("max")[0] + ":  I love this.");
				for (FieldGroup rfg : allRange) {
					// FieldGroup rfg = allRange.get(i);
					FieldGroup mfg = allMat.get(i);

					String min = rfg.getField("Min").getValue().toString()
							.trim();

					String max = rfg.getField("Max").getValue().toString()
							.trim();

					String con = mfg.getField("Mat").getValue().toString()
							.trim();

					String amt = mfg.getField("Amt").getValue().toString()
							.trim();
					if (isEditFees && !isDup) {

						// if (Double.valueOf(hmTiers.get("min")[i]).equals(
						// Double.valueOf(min))) {
						// isDup = true;
						// }

						if (Double.valueOf(hmTiers.get("max")[i]).equals(
								Double.valueOf(max))) {
							isDup = true;
						}

						//
						// if (hmTiers.get("amt")[i].equals(amt) && !isDup) {
						// isDup = true;
						// }
						//
						// if (hmTiers.get("con")[i].equals(con) && !isDup) {
						// isDup = true;
						// }

					}

					cf[i] = new ServiceCommission();
					cf[i].setMinimumamount(BigDecimal.valueOf(Float
							.valueOf(min)));
					cf[i].setMaximumamount(BigDecimal.valueOf(Float
							.valueOf(max)));
					cf[i].setCommissionfeetype(con);
					cf[i].setCommissionfee(BigDecimal.valueOf(Float
							.valueOf(amt)));

					i++;

				}

				if (isDup && isEditFees) {
					NotifCustom
							.show("Message",
									"No Changes have been made. Please make changes and save.");
					isDup = false;
					return false;
				}
			}

			String response;

			if (isEditFees) {

				try {
					response = CommissionService.editCommission(UI.getCurrent()
							.getSession().getAttribute("user").toString(),
							commValue, setupType, cf, setupPropertyID, txID,
							opID);

					if (response != null
							&& response
									.equals("Service commission configured successfully")) {

						NotifCustom.show("Response", response);

						return true;
					} else {
						NotifCustom
								.show("Response",
										"Configuration saving FAILED. Please try again.");
						System.err.println(response);
						return false;

					}

				} catch (Exception e) {
					Notification
							.show("Oops... Something wrong happened. Please try again.",
									Notification.Type.ERROR_MESSAGE);
					e.printStackTrace();
				}

				return false;

			} else {

				try {
					response = CommissionService.setupCommission(UI
							.getCurrent().getSession().getAttribute("user")
							.toString(), commValue, setupType, cf,
							setupPropertyID, txID, opID);
					if (response != null
							&& response
									.equals("Service commission configured successfully")) {

						NotifCustom.show("Response", response);

						return true;
					} else {
						NotifCustom
								.show("Response",
										"Configuration saving FAILED. Please try again.");
						System.err.println(response);
						return false;
					}
					// Notification.show(response);
					// System.out.println(response);

				} catch (Exception e) {
					Notification
							.show("Oops... Something wrong happened. Please try again",
									Notification.Type.ERROR_MESSAGE);
					e.printStackTrace();
				}

				return false;

			}

		}

		txID = Integer.valueOf(hmTransactionTypes.get(comboTT.getValue()
				.toString()));
		ArrayList<ArrayList<FieldGroup>> allFeesFG = hmAllFG.get(FEES);
		ArrayList<FieldGroup> allRange = allFeesFG.get(0);
		ArrayList<FieldGroup> allMat = allFeesFG.get(1);

		// System.out.println("----: " + opID + " :-----");
		// System.out.println("----: " + txID + " :-----");

		ServiceFees[] sf = new ServiceFees[allRange.size()];
		for (int i = 0; i < allRange.size(); i++) {
			FieldGroup rfg = allRange.get(i);
			FieldGroup mfg = allMat.get(i);
			sf[i] = new ServiceFees();
			sf[i].setMinimumamount(BigDecimal.valueOf(Float.valueOf(rfg
					.getField("Min").getValue().toString().trim())));
			sf[i].setMaximumamount(BigDecimal.valueOf(Float.valueOf(rfg
					.getField("Max").getValue().toString().trim())));
			sf[i].setServicefeetype(mfg.getField("Mat").getValue().toString()
					.trim());
			sf[i].setServicefee(BigDecimal.valueOf(Float.valueOf(mfg
					.getField("Amt").getValue().toString().trim())));

			// sf[i].setServicefeetype(ProvisioningStub.ServiceFeematrix.Factory
			// .fromValue(mfg.getField("Mat").getValue().toString().trim()));

			// sf[i].setServicefee(BigDecimal.valueOf(Float.valueOf(mfg
			// .getField("Amt").getValue().toString().trim())));
			// sf[i].setTransactiontypeid(txID);

		}

		// ArrayList<ArrayList<FieldGroup>> allCommFG = hmAllFG.get(COMMISSION);
		// allRange = allCommFG.get(0);
		// allMat = allCommFG.get(1);
		// FieldGroup dfg = hmDFG.get(COMMISSION);
		// // FieldGroup dfg = arrLDFG.get(0);
		// String conType = dfg.getField("CONID").getValue().toString().trim();
		// String modType = dfg.getField("MODID").getValue().toString().trim();
		//
		// ServiceCommission[] sc = new ServiceCommission[allRange.size()];
		// for (int i = 0; i < allRange.size(); i++) {
		// FieldGroup rfg = allRange.get(i);
		// FieldGroup mfg = allMat.get(i);
		// sc[i] = new ServiceCommission();
		// sc[i].setMinimumamount(BigDecimal.valueOf(Float.valueOf(rfg
		// .getField("Min").getValue().toString().trim())));
		// sc[i].setMaximumamount(BigDecimal.valueOf(Float.valueOf(rfg
		// .getField("Max").getValue().toString().trim())));
		// sc[i].setServicecommissioncondition(ProvisioningStub.ServiceCommissionConditionTypes.Factory
		// .fromValue(conType));
		// sc[i].setServicecommissionmodeltype(ProvisioningStub.ServiceCommissionModelTypes.Factory
		// .fromValue(modType));
		// sc[i].setCommissionfeetype(ProvisioningStub.ServiceCommissionmatrix.Factory
		// .fromValue(mfg.getField("Mat").getValue().toString().trim()));
		// sc[i].setCommissionfee(BigDecimal.valueOf(Float.valueOf(mfg
		// .getField("Amt").getValue().toString().trim())));
		// sc[i].setTransactiontypeid(txID);

		// }

		if (isEditFees) {
			isEditFees = false;
			// loggedInUser, Serviceconfigtype, servicefess, transactiontypeid,
			// accountholderid)
			try {

				String response = FeeService.editServicefee(UI.getCurrent()
						.getSession().getAttribute("user").toString(), 1, sf,
						txID, opID);

				System.out.println(response);
				// NotifCustom.show("Response", response);
				if (response.equals("Service fee configured successfully")) {
					isReset = true;

					NotifCustom.show("Response", response);//
					cPlaceholder.setVisible(false);

					cPlaceholder.setVisible(true);

					// TODO Cleanup after successful editing
					return true;

				} else {
					NotifCustom.show("Response", response);

					isReset = false;
					return false;

				}
			} catch (Exception e) {
				Notification
						.show("Oops... Fees Configuration did not save successfully.",
								Notification.Type.ERROR_MESSAGE);
				e.printStackTrace();
				isReset = false;
				return false;
			}

		}

		try {

			String response = FeeService.setupServicefee(UI.getCurrent()
					.getSession().getAttribute("user").toString(), 1, sf, txID,
					opID);

			if (response.equals("Service fee configured successfully")) {
				isReset = true;
				NotifCustom.show("Response", response);

				System.out
						.println("------------------------------------------kxkxkxk---------------");
				return true;

			} else {
				NotifCustom.show("Response", response);

				isReset = false;
				return false;

			}
		} catch (Exception e) {
			Notification.show(
					"Oops... Fees Configuration did not save successfully.",
					Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
			isReset = false;
		}
		return false;

	}

	private void addTransactionTypes(ComboBox combo) {
		combo.removeAllItems();

		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		try {

			if (!isOptTiered) {
				hmExistingFees = rs.getAvailableFees(UI.getCurrent()
						.getSession().getAttribute("user").toString());

				Set<Entry<String, String>> set = hmExistingFees.entrySet();

				System.out.println(set.size() + " :..........");
				for (Entry<String, String> e : set) {
					Object key = e.getKey();

					String operator = combo.getValue().toString();
					String opTT = key.toString();

					if (opTT.contains(operator)) {
						combo.addItem(key);

					}

				}

				combo.setCaption("Select Operator Transaction");

				// isOptTiered = true;

				return;

			}

			// System.out.println("---c---c---c---------------------cc-");

			hmTransactionTypes = rs.getTransactionTypes();
			combo.setCaption("Transaction Type");
			Set<Entry<String, String>> set = hmTransactionTypes.entrySet();
			for (Entry<String, String> e : set) {

				Object key = e.getKey();
				combo.addItem(key);
				// combo.setItemCaption(key, e.getValue());
				// System.out.println();

			}

		} catch (RemoteException | DataServiceFault e) {

			e.printStackTrace();
		}

	}

	private Map<String, String> getTransactionTypes() {
		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		try {

			return rs.getTransactionTypes();
		} catch (RemoteException | DataServiceFault e) {
			e.printStackTrace();
			return Collections.emptyMap();

		}

	}

	private Map<String, String> getOperators() {
		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		try {

			return rs.getServiceProviders();
		} catch (RemoteException | DataServiceFault e) {
			e.printStackTrace();
			return Collections.emptyMap();

		}

	}

}