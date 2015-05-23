package com.swifta.mats.web.settings;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.Initializer;
import com.swifta.mats.web.usermanagement.BtnTabLike;
import com.swifta.mats.web.usermanagement.NotifCustom;
import com.swifta.mats.web.utils.ReportingService;
import com.swifta.mats.web.utils.UserManagementService;
import com.swifta.sub.mats.reporting.DataServiceFault;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profile;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ProfilesAndPermissionsModule {

	private Label lbThreshold;

	private HorizontalLayout cNewThresholdBtns;

	private TextField tfThresholdAmount;

	private String curThresholdValue = null;

	private VerticalLayout cBeforePlaceHolder;
	private ComboBox comboXTH;

	private ComboBox comboX;

	private VerticalLayout cMain = new VerticalLayout();
	private HorizontalLayout cStage = new HorizontalLayout();
	private HorizontalLayout p;
	private HorizontalLayout cProfile;
	private HorizontalLayout cThreshold;
	private HorizontalLayout cPerm;
	private HorizontalLayout prevL;
	private Window pop = new Window("Comfirm profile removal");
	private boolean isfromsub;
	private boolean isProfileNameChanged = false;
	private ReportingService rs;

	private ComboBox comboPermProfiles;
	private ComboBox comboProfiles;
	private ComboBox comboThresholdProfiles;
	private ComboBox comboExistingThresholds;
	private Map<String, String> hmAllProfiles;
	private Map<String, String> hmProfileTypes;
	private Map<String, String> hmThresholdTypesByID = new HashMap<>();
	private Map<String, String> hmThresholdTypes = new HashMap<>();
	private Map<String, String> hmTransactionTypes;
	private Map<String, Profile> hmProfIDs = new HashMap<>();

	private HashMap<String, String> hmTransactionTypesByID = new HashMap<>();

	private Map<String, String> hmActivePerms = new HashMap<>();
	private Map<String, String> hmInActivePerms = new HashMap<>();

	private Map<String, ArrayList<HashMap<String, String>>> hmExistingThresholdTypes = new HashMap<>();

	private TwinColSelect tcsInactiveAndActive;
	private Profile[] profiles;

	private OptionGroup optMultTrans;
	private OptionGroup optMultT;
	// private ComboBox comboThresholds;
	private Button back;

	private HashMap<String, String> hmProfPermPermissions;
	private BtnTabLike btnProfiles;
	private BtnTabLike btnThresholds;
	private BtnTabLike btnPermissions;

	private Button btnAddThreshold;

	private String profileTID = null;

	private HashSet<String> setExistingTT = new HashSet<>();
	private HashMap<String, HashSet<String>> hmETH = new HashMap<>();
	private HashMap<String, HashSet<String>> hmNONETH = new HashMap<>();

	private HashMap<String, HashMap<String, String>> hmThresholdProperties;

	private boolean isEditThresholdPossible = false;

	// private HorizontalLayout cjust;

	ProfilesAndPermissionsModule(Button back,
			HashMap<String, String> hmProfPermPermissions) {

		this.hmProfPermPermissions = hmProfPermPermissions;
		this.back = back;
		addMenu();
		HorizontalLayout cTemp = null;
		if (!btnProfiles.isEnabled()) {
			cTemp = getProfileC();
			cProfile = cTemp;
		} else if (!btnThresholds.isEnabled() && cTemp == null) {
			cTemp = getThresholdC();
			cThreshold = cTemp;
		} else {
			cTemp = getPermissionsC();
			cPerm = cTemp;
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

		btnProfiles = new BtnTabLike("Profiles", null);
		btnThresholds = new BtnTabLike("Thresholds", null);
		btnPermissions = new BtnTabLike("Permissions", null);
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
				.get("edit_profile"))

				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("add_profile"))
				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("remove_profile"))
				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("add_threshold"))) {
			cMainMenu.addComponent(btnThresholds);
			if (btnTemp == null)
				btnTemp = btnThresholds;
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

				if (!btnThresholds.isEnabled())
					p = cThreshold;

				btnPermissions.setEnabled(true);
				btnPermissions.setStyleName("btn_tab_like");

				btnThresholds.setEnabled(true);
				btnThresholds.setStyleName("btn_tab_like");

				if (!isfromsub) {
					if (cProfile == null)
						cProfile = getProfileC();
					p = (HorizontalLayout) cStage.getComponent(0);
					cStage.replaceComponent(p, cProfile);
				} else {
					prevL = (HorizontalLayout) cStage.getComponent(0);
					cStage.replaceComponent(prevL, cProfile);
					isfromsub = false;
				}

			}
		});

		btnThresholds.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 8237565312571186332L;

			@Override
			public void buttonClick(ClickEvent event) {

				btnThresholds.setEnabled(false);
				btnThresholds.setStyleName("btn_tab_like btn_tab_like_active");

				if (!btnPermissions.isEnabled())
					p = cPerm;

				if (!btnProfiles.isEnabled())
					p = cProfile;

				btnPermissions.setEnabled(true);
				btnPermissions.setStyleName("btn_tab_like");

				btnProfiles.setEnabled(true);
				btnProfiles.setStyleName("btn_tab_like");

				if (!isfromsub) {
					if (cThreshold == null)
						cThreshold = getThresholdC();
					p = (HorizontalLayout) cStage.getComponent(0);
					cStage.replaceComponent(p, cThreshold);
				} else {
					prevL = (HorizontalLayout) cStage.getComponent(0);
					cStage.replaceComponent(prevL, cThreshold);
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

				if (!btnThresholds.isEnabled())
					p = cThreshold;

				btnProfiles.setEnabled(true);
				btnProfiles.setStyleName("btn_tab_like");

				btnThresholds.setEnabled(true);
				btnThresholds.setStyleName("btn_tab_like");

				if (!isfromsub) {
					if (cPerm == null)
						cPerm = getPermissionsC();
					p = (HorizontalLayout) cStage.getComponent(0);
					cStage.replaceComponent(p, cPerm);

				} else {
					if (cPerm == null)
						cPerm = getPermissionsC();
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

	private HorizontalLayout getPermissionsC() {
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
		lb.setValue("Manage Profile Permissions");
		final TextField tFProf = new TextField();
		comboPermProfiles = new ComboBox("Select Profile: ");

		lbProf.setCaption("Selected Profile Name: ");
		lbProf.setValue("None.");
		tFProf.setCaption(lbProf.getCaption());
		cAllProf.addComponent(lb);
		lb.setStyleName("label_search_user");
		cAllProf.setComponentAlignment(lb, Alignment.TOP_CENTER);
		cProfName.addComponent(comboPermProfiles);
		cProfName.addComponent(lbProf);

		final Button btnEdit = new Button();
		btnEdit.setIcon(FontAwesome.EDIT);
		btnEdit.setStyleName("btn_link");
		btnEdit.setDescription("Edit Permsissions");

		final Button btnCancel = new Button();
		btnCancel.setIcon(FontAwesome.UNDO);
		btnCancel.setStyleName("btn_link");
		btnCancel.setDescription("Cancel editting permissions");

		final Button btnAdd = new Button("+");
		// btnAdd.setIcon(FontAwesome.UNDO);
		btnAdd.setStyleName("btn_link");
		btnAdd.setDescription("Add permissions");
		btnAdd.setVisible(false);

		cProfNameAndAddBtn.addComponent(cProfName);

		// cProf.addComponent(cProfName);
		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("set_permissions")))
			cPermsActions.addComponent(btnEdit);
		cPermsActions.addComponent(btnCancel);

		// cProfActions.addComponent(btnAdd);

		btnCancel.setVisible(false);

		cAllProf.addComponent(cProfNameAndAddBtn);
		cAllProf.setComponentAlignment(cProfNameAndAddBtn, Alignment.TOP_CENTER);
		// cAllProf.addComponent(cPermsActions);
		// cAllProf.setComponentAlignment(cProfActions, Alignment.TOP_CENTER);
		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("set_permissions"))) {
			cAllProf.addComponent(btnAdd);
			cAllProf.setComponentAlignment(btnAdd, Alignment.TOP_CENTER);
		}

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

		final ListSelect list = new ListSelect();
		cPlaceholder.addComponent(list);

		final VerticalLayout cPermsAndBtns = new VerticalLayout();
		final HorizontalLayout cPermsList = new HorizontalLayout();
		cPermsList.addComponent(list);
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

		comboPermProfiles
				.addValueChangeListener(new Property.ValueChangeListener() {

					private static final long serialVersionUID = -1655803841445897977L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						hmActivePerms.clear();

						Object objPname = event.getProperty().getValue();
						if (objPname == null) {
							lbProf.setValue("None.");
							lbProf.setCaption("Selected Profile: ");
							btnAdd.setVisible(false);
							cPlaceholder.setVisible(false);
							return;
						}

						String pname = objPname.toString();
						lbProf.setCaption("Selected Profile: ");
						lbProf.setValue(pname);

						hmActivePerms = getActivePermissions(Integer
								.parseInt(hmAllProfiles.get(pname)));
						Integer pCount = hmActivePerms.size();

						list.setCaption(pCount + " Active Permissions.");
						if (pCount == 0) {
							cPlaceholder.setVisible(false);
							NotifCustom.show("No Permissions", "Profile \""
									+ pname + "\" has NO active permissions");
							btnAdd.setVisible(true);
							return;
						}

						btnAdd.setVisible(false);
						btnEdit.setIcon(FontAwesome.EDIT);
						btnEdit.setDescription("Edit permissions");

						cPermsList.replaceComponent(tcsInactiveAndActive, list);

						list.setCaption("Total active permissions: " + pCount);
						list.removeAllItems();
						cPlaceholder.setVisible(true);

						for (Entry<String, String> e : hmActivePerms.entrySet()) {
							String id = e.getKey();
							list.addItems(id);
							list.setItemCaption(id, e.getValue());

						}

					}
				});

		comboPermProfiles.addFocusListener(new FocusListener() {

			private static final long serialVersionUID = 1583773393542597237L;

			@Override
			public void focus(FocusEvent event) {

				// if (!isPermProfilesAdded)
				addProfiles(comboPermProfiles);
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

				String pname = comboPermProfiles
						.getItemCaption(comboPermProfiles.getValue());
				Integer pid = Integer.parseInt(hmAllProfiles.get(pname));

				if (btnEdit.getIcon().equals(FontAwesome.EDIT)) {
					btnCancel.setVisible(true);
					btnEdit.setIcon(FontAwesome.SAVE);
					btnEdit.setDescription("Save changes");

					hmActivePerms.clear();
					hmInActivePerms.clear();
					hmActivePerms = getActivePermissions(pid);
					hmInActivePerms = getInActivePermissions(pid);

					tcsInactiveAndActive.removeAllItems();

					for (Entry<String, String> e : hmActivePerms.entrySet()) {
						String id = e.getKey();
						tcsInactiveAndActive.addItem(id);
						tcsInactiveAndActive.setItemCaption(id, e.getValue());
						tcsInactiveAndActive.select(id);

						System.out.println(id
								+ "------.................--------"
								+ e.getValue());
					}

					for (Entry<String, String> e : hmInActivePerms.entrySet()) {
						String id = e.getKey();
						tcsInactiveAndActive.addItem(id);
						tcsInactiveAndActive.setItemCaption(id, e.getValue());

						System.out.println(id
								+ "------.................--------"
								+ e.getValue());

					}
					cPermsList.replaceComponent(list, tcsInactiveAndActive);

				} else {
					Set<?> ids = (Set<?>) tcsInactiveAndActive.getValue();
					Set<String> originalIDs = hmActivePerms.keySet();

					if (originalIDs.containsAll(ids)
							&& ids.containsAll(originalIDs)) {
						NotifCustom.show("Save Permissions",
								"No Permission Changes to save.");
						return;
					}

					String response = null;

					System.out.println("Profile Name: " + pname
							+ "=====ccc=====c===c== Profile ID: " + pid);

					System.out.println("ACTIONS ARRAY");
					for (Object px : ids)
						System.out.println(px.toString());

					try {
						btnEdit.setEnabled(false);

						response = UserManagementService.setProfilePermission(
								pname, pid, ids.toArray(new String[ids.size()]));

					} catch (Exception e) {

						Notification.show(e.getMessage(),
								Notification.Type.ERROR_MESSAGE);
						e.printStackTrace();
					}

					btnEdit.setEnabled(true);

					if (response == null || response.trim().isEmpty()) {
						Notification.show("No response from the Server.",
								Notification.Type.ERROR_MESSAGE);

						return;
					}

					if (response.toUpperCase().contains("SUCCESS")) {
						NotifCustom.show("Response", response);
						response = null;
					}

					hmActivePerms.clear();

					hmActivePerms = getActivePermissions(pid);
					if (hmActivePerms.size() != 0) {

						list.removeAllItems();

						for (Entry<String, String> e : hmActivePerms.entrySet()) {
							String id = e.getKey();
							list.addItem(id);
							list.setItemCaption(id, e.getValue());

						}

						list.setCaption("Total active permissions: "
								+ hmActivePerms.size());
						cPermsList.replaceComponent(tcsInactiveAndActive, list);
						btnCancel.setVisible(false);
						btnEdit.setIcon(FontAwesome.EDIT);
						btnEdit.setDescription("Edit permissions");
						btnAdd.setVisible(false);
						if (response != null)
							NotifCustom.show("Response", response);

						return;

					}

					cPlaceholder.setVisible(false);
					if (response != null)
						Notification.show(response,
								Notification.Type.ERROR_MESSAGE);
					btnAdd.setVisible(true);

				}

			}
		});

		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 2567415555987260624L;

			@Override
			public void buttonClick(ClickEvent event) {
				String pn = comboPermProfiles.getValue().toString();
				hmActivePerms.clear();

				hmActivePerms = getActivePermissions(Integer
						.parseInt(hmAllProfiles.get(pn)));

				list.removeAllItems();

				int pCount = hmActivePerms.size();

				for (Entry<String, String> e : hmActivePerms.entrySet()) {
					String pid = e.getKey();
					list.addItem(pid);
					list.setItemCaption(pid, e.getValue());

				}
				cPermsList.replaceComponent(tcsInactiveAndActive, list);
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
				String pn = comboPermProfiles.getValue().toString();
				Integer pid = Integer.parseInt(hmAllProfiles.get(pn));

				btnCancel.setVisible(true);
				btnEdit.setIcon(FontAwesome.SAVE);
				btnEdit.setDescription("Save changes");
				btnAdd.setVisible(false);
				cPlaceholder.setVisible(true);

				hmActivePerms.clear();
				hmInActivePerms.clear();
				hmActivePerms = getActivePermissions(pid);
				hmInActivePerms = getInActivePermissions(pid);

				tcsInactiveAndActive.removeAllItems();

				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");

				for (Entry<String, String> e : hmActivePerms.entrySet()) {
					String id = e.getKey();
					tcsInactiveAndActive.addItem(id);
					tcsInactiveAndActive.setItemCaption(id, e.getValue());
					tcsInactiveAndActive.select(id);

					System.out.println(id + "------.................--------"
							+ e.getValue());
				}

				for (Entry<String, String> e : hmInActivePerms.entrySet()) {
					String id = e.getKey();
					tcsInactiveAndActive.addItem(id);
					tcsInactiveAndActive.setItemCaption(id, e.getValue());

					System.out.println(id + "------..................-------"
							+ e.getValue());
				}

				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
				System.out
						.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");

				cPermsList.replaceComponent(list, tcsInactiveAndActive);

			}

		});

		return c;
	}

	private Map<String, String> getActivePermissions(int pid) {
		try {
			return UserManagementService.getactiveprofilepermission(pid);
		} catch (Exception e) {
			Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
			return Collections.emptyMap();

		}

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

	private HorizontalLayout getProfileC() {

		VerticalLayout cAgentInfo = new VerticalLayout();
		final HorizontalLayout cPlaceholder = new HorizontalLayout();
		final HorizontalLayout cPlaceholderx = new HorizontalLayout();
		cAgentInfo.setMargin(new MarginInfo(true, true, true, true));
		// cAgentInfo.setStyleName("c_details_test");

		final VerticalLayout cLBody = new VerticalLayout();

		cLBody.setStyleName("c_body_visible");

		// addLinksTable();

		final VerticalLayout cAllProf = new VerticalLayout();

		final HorizontalLayout cProfActions = new HorizontalLayout();
		cProfActions.setVisible(false);
		HorizontalLayout cProfNameAndAddBtn = new HorizontalLayout();
		final FormLayout cProfName = new FormLayout();

		cProfName.setStyleName("frm_profile_name");
		cProfName.setSizeUndefined();

		final Label lbProf = new Label();
		final Label lb = new Label();
		lb.setValue("Manage Profiles");
		final TextField tFProf = new TextField();
		comboProfiles = new ComboBox("Select Profile: ");

		lbProf.setCaption("Profile Name: ");
		lbProf.setValue("None.");
		tFProf.setCaption(lbProf.getCaption());
		cAllProf.addComponent(lb);
		lb.setStyleName("label_search_user");
		cAllProf.setComponentAlignment(lb, Alignment.TOP_CENTER);
		cProfName.addComponent(comboProfiles);
		cProfName.addComponent(lbProf);

		final Button btnEdit = new Button();
		btnEdit.setIcon(FontAwesome.EDIT);
		btnEdit.setStyleName("btn_link");
		btnEdit.setDescription("Edit profile name");

		final Button btnThreshold = new Button("T");
		// btnThreshold.setIcon(FontAwesome.T);
		btnThreshold.setStyleName("btn_link");
		btnThreshold.setDescription("Set profile threshold");

		final Button btnCancel = new Button();
		btnCancel.setIcon(FontAwesome.UNDO);
		btnCancel.setStyleName("btn_link");
		btnCancel.setDescription("Cancel Profile name editting.");

		Button btnAdd = new Button("+");
		// btnAdd.setIcon(FontAwesome.EDIT);
		btnAdd.setStyleName("btn_link");
		btnAdd.setDescription("Add new profile");

		Button btnRemove = new Button("-");
		// btnRemove.setIcon(FontAwesome.EDIT);
		btnRemove.setStyleName("btn_link");
		btnRemove.setDescription("Remove current profile");

		cProfNameAndAddBtn.addComponent(cProfName);
		// cProf.addComponent(cProfName);
		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("add_profile")))
			cProfNameAndAddBtn.addComponent(btnAdd);

		// cProf.addComponent(cProfName);
		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("edit_profile")))
			cProfActions.addComponent(btnEdit);
		cProfActions.addComponent(btnCancel);
		// cProfActions.addComponent(btnAdd);
		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("add_threshold")))
			cProfActions.addComponent(btnThreshold);
		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("remove_profile")))
			cProfActions.addComponent(btnRemove);

		btnCancel.setVisible(false);

		cAllProf.addComponent(cProfNameAndAddBtn);
		cAllProf.addComponent(cProfActions);
		cAllProf.setComponentAlignment(cProfActions, Alignment.TOP_CENTER);

		cLBody.addComponent(cAllProf);

		// cLBody.addComponent(tb);

		cAgentInfo.addComponent(cLBody);

		// cLBody.addComponent(btnLink);
		// cLBody.setComponentAlignment(btnLink, Alignment.TOP_LEFT);

		cPlaceholder.setVisible(false);
		cPlaceholderx.setVisible(false);
		// addLinkUserContainer();
		cPlaceholder.setWidth("100%");
		cPlaceholderx.setWidth("100%");

		cLBody.addComponent(cPlaceholder);
		cLBody.addComponent(cPlaceholderx);
		cLBody.setComponentAlignment(cPlaceholder, Alignment.TOP_CENTER);
		cLBody.setComponentAlignment(cPlaceholderx, Alignment.TOP_CENTER);
		HorizontalLayout c = new HorizontalLayout();
		c.addComponent(cAgentInfo);

		comboProfiles
				.addValueChangeListener(new Property.ValueChangeListener() {

					private static final long serialVersionUID = -1655803841445897977L;

					@Override
					public void valueChange(ValueChangeEvent event) {

						if (event.getProperty().getValue() != null) {
							cProfActions.setVisible(true);
							lbProf.setValue(comboProfiles.getItemCaption(event
									.getProperty().getValue()));
						} else {
							cProfActions.setVisible(false);
							lbProf.setValue("None.");
							cProfName.replaceComponent(tFProf, lbProf);

						}
					}
				});

		comboProfiles.addFocusListener(new FocusListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1583773393542597237L;

			@Override
			public void focus(FocusEvent event) {

				comboProfiles.removeAllItems();
				addProfiles(comboProfiles);
				comboProfiles.select(null);

			}

		});

		tFProf.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = -1655803841445897977L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				isProfileNameChanged = true;

			}
		});

		btnEdit.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -8427226211153164650L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (btnEdit.getIcon().equals(FontAwesome.EDIT)) {
					isProfileNameChanged = false;
					tFProf.setValue(lbProf.getValue());
					tFProf.selectAll();
					cProfName.replaceComponent(lbProf, tFProf);
					btnEdit.setIcon(FontAwesome.SAVE);
					btnEdit.setDescription("Save changes");
					btnCancel.setVisible(true);
					return;

				} else if (btnEdit.getIcon().equals(FontAwesome.SAVE)) {

					if (!isProfileNameChanged)
						return;

					String pnNew = tFProf.getValue();
					if (pnNew == null || pnNew.trim().trim().isEmpty()) {
						Notification.show("Please provide new profile name.",
								Notification.Type.ERROR_MESSAGE);
						tFProf.focus();
						return;
					}

					if (hmAllProfiles.containsKey(pnNew.trim())) {
						Notification
								.show("\""
										+ pnNew
										+ "\" already exists. Please provide unique profile name.",
										Notification.Type.ERROR_MESSAGE);
						tFProf.focus();
						return;
					}

					lbProf.setValue(tFProf.getValue());
					cProfName.replaceComponent(tFProf, lbProf);
					btnEdit.setIcon(FontAwesome.EDIT);
					btnEdit.setDescription("Edit profile name");
					btnCancel.setVisible(false);

					isProfileNameChanged = false;

					Object profileName = comboThresholdProfiles.getValue();
					Integer pid = Integer.parseInt(hmAllProfiles
							.get(profileName.toString().trim()));
					String pnOld = profileName.toString();

					System.out.println(" New PN: " + pnNew + " Old PN: "
							+ pnOld + " PID:" + pid);

					String response = null;

					try {
						response = UserManagementService.editProfile(pnNew,
								pnOld, pid);

						comboThresholdProfiles.addItem(pnNew);
						comboThresholdProfiles.select(pnNew);
						// hmAllProfiles.remove(pnOld);

						if (response == null || response.trim().isEmpty()) {
							Notification.show("No reponse from the server",
									Notification.Type.ERROR_MESSAGE);
							return;

						}

						if (response.toUpperCase().contains("SUCCESSFUL")) {
							lbProf.setValue(tFProf.getValue());
							cProfName.replaceComponent(tFProf, lbProf);
							btnEdit.setIcon(FontAwesome.EDIT);
							btnEdit.setDescription("Edit profile name");
							btnCancel.setVisible(false);

							NotifCustom.show("Response", response);
							hmAllProfiles.put(pnNew, pid.toString());
							comboPermProfiles.removeAllItems();
							comboPermProfiles.select(null);

							return;
						}

						Notification.show(response,
								Notification.Type.ERROR_MESSAGE);

					} catch (Exception e) {

						Notification.show(e.getMessage(),
								Notification.Type.ERROR_MESSAGE);

						e.printStackTrace();
					}

				}

			}
		});

		btnCancel.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -2870045546205986347L;

			@Override
			public void buttonClick(ClickEvent event) {
				cProfName.replaceComponent(tFProf, lbProf);
				btnEdit.setIcon(FontAwesome.EDIT);
				btnEdit.setDescription("Edit profile name");
				btnCancel.setVisible(false);

			}
		});

		btnAdd.addClickListener(new AddProfileHandler(cAllProf, cPlaceholder));

		btnRemove.addClickListener(new RemoveProfileHandler(pop));
		// btnThreshold.addClickListener(new SetThresholdHandler(cAllProf,
		// cPlaceholderx));

		return c;

	}

	private HorizontalLayout getThresholdC() {

		VerticalLayout cAgentInfo = new VerticalLayout();
		final HorizontalLayout cPlaceholder = new HorizontalLayout();
		final HorizontalLayout cPlaceholderx = new HorizontalLayout();
		cAgentInfo.setMargin(new MarginInfo(true, true, true, true));
		// cAgentInfo.setStyleName("c_details_test");

		final VerticalLayout cLBody = new VerticalLayout();

		cLBody.setStyleName("c_body_visible");

		// addLinksTable();

		final VerticalLayout cAllProf = new VerticalLayout();

		final HorizontalLayout cProfActions = new HorizontalLayout();
		cProfActions.setVisible(false);
		final FormLayout cProfName = new FormLayout();

		cProfName.setStyleName("frm_profile_name");
		cProfName.setSizeUndefined();

		lbThreshold = new Label();
		final Label lbProf = new Label();

		final Label lb = new Label();
		lb.setValue("Manage Thresholds");
		final TextField tfThreshold = new TextField();
		comboThresholdProfiles = new ComboBox("Select Profile: ");

		optMultT = new OptionGroup();
		optMultT.setCaption("Transaction Type");
		optMultT.setMultiSelect(true);

		comboX = new ComboBox();
		comboX.setCaption("Select Transaction Type: ");
		comboX.setVisible(false);

		comboXTH = new ComboBox();
		comboXTH.setCaption("Select Threshold Type: ");
		comboXTH.setVisible(false);

		comboExistingThresholds = new ComboBox("Select Threshold: ");

		lbThreshold.setCaption("Threshold Name: ");
		lbThreshold.setVisible(false);
		lbThreshold.setValue("None.");
		lbProf.setCaption("Profile Name: ");
		lbProf.setValue("None.");

		tfThresholdAmount = new TextField();
		tfThresholdAmount.setCaption("Threshold Amount");

		tfThreshold.setCaption(lbThreshold.getCaption());
		cAllProf.addComponent(lb);
		lb.setStyleName("label_search_user");
		cAllProf.setComponentAlignment(lb, Alignment.TOP_CENTER);
		cProfName.addComponent(comboThresholdProfiles);
		cProfName.addComponent(lbProf);
		cProfName.addComponent(comboX);
		cProfName.addComponent(comboXTH);
		cProfName.addComponent(optMultT);
		cProfName.addComponent(comboExistingThresholds);
		// cProfName.addComponent(lbThreshold);
		cProfName.addComponent(tfThresholdAmount);

		Button btnNewSave = new Button();
		btnNewSave.setIcon(FontAwesome.SAVE);
		btnNewSave.setStyleName("btn_link");
		btnNewSave.setDescription("Save new profile");

		Button btnNewCancel = new Button();
		btnNewCancel.setStyleName("btn_link");
		btnNewCancel.setIcon(FontAwesome.UNDO);
		btnNewCancel.setDescription("Cancel setting new profile");

		HorizontalLayout cBtnsNew = new HorizontalLayout();
		cBtnsNew.addComponent(btnNewSave);
		cBtnsNew.addComponent(btnNewCancel);

		cNewThresholdBtns = cBtnsNew;

		cBeforePlaceHolder = new VerticalLayout();

		cBeforePlaceHolder.addComponent(cProfName);
		cBeforePlaceHolder.addComponent(cBtnsNew);
		cBeforePlaceHolder.setComponentAlignment(cBtnsNew, Alignment.TOP_RIGHT);

		final Button btnEdit = new Button();
		btnEdit.setIcon(FontAwesome.EDIT);
		btnEdit.setStyleName("btn_link");
		btnEdit.setDescription("Edit Threshold name");

		final Button btnThreshold = new Button("T");
		// btnThreshold.setIcon(FontAwesome.T);
		btnThreshold.setStyleName("btn_link");
		btnThreshold.setDescription("Set profile threshold");

		final Button btnCancel = new Button();
		btnCancel.setIcon(FontAwesome.UNDO);
		btnCancel.setStyleName("btn_link");
		btnCancel.setDescription("Cancel Threshold name editting.");

		Button btnAdd = new Button("+");
		// btnAdd.setIcon(FontAwesome.EDIT);
		btnAdd.setStyleName("btn_link");
		btnAdd.setDescription("Add new Threshold");

		Button btnRemove = new Button("-");
		// btnRemove.setIcon(FontAwesome.EDIT);
		btnRemove.setStyleName("btn_link");
		btnRemove.setDescription("Remove current Threshold");

		// cProfNameAndAddBtn.addComponent(cProfName);
		// cProf.addComponent(cProfName);
		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("add_profile")))
			// cProfNameAndAddBtn.addComponent(btnAdd);

			// cProf.addComponent(cProfName);
			if (Initializer.setUserPermissions.contains(hmProfPermPermissions
					.get("edit_profile")))
				// cProfActions.addComponent(btnEdit);
				// cProfActions.addComponent(btnCancel);
				// cProfActions.addComponent(btnAdd);
				if (Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("add_threshold")))
					// cProfActions.addComponent(btnThreshold);
					if (Initializer.setUserPermissions
							.contains(hmProfPermPermissions
									.get("remove_profile")))
						// cProfActions.addComponent(btnRemove);

						btnCancel.setVisible(false);

		btnAddThreshold = btnAdd;
		btnAdd.setVisible(false);
		cBeforePlaceHolder.addComponent(btnAdd);
		cBeforePlaceHolder.setComponentAlignment(btnAdd, Alignment.TOP_CENTER);

		cAllProf.addComponent(cBeforePlaceHolder);
		// cAllProf.addComponent(cProfActions);
		// cAllProf.setComponentAlignment(cProfActions, Alignment.TOP_CENTER);

		cLBody.addComponent(cAllProf);

		// cLBody.addComponent(tb);

		cAgentInfo.addComponent(cLBody);

		// cLBody.addComponent(btnLink);
		// cLBody.setComponentAlignment(btnLink, Alignment.TOP_LEFT);

		cPlaceholder.setVisible(false);
		cPlaceholderx.setVisible(false);
		// addLinkUserContainer();
		cPlaceholder.setWidth("100%");
		cPlaceholderx.setWidth("100%");

		cLBody.addComponent(cPlaceholder);
		cLBody.addComponent(cPlaceholderx);
		cLBody.setComponentAlignment(cPlaceholder, Alignment.TOP_CENTER);
		cLBody.setComponentAlignment(cPlaceholderx, Alignment.TOP_CENTER);
		HorizontalLayout c = new HorizontalLayout();
		c.addComponent(cAgentInfo);

		comboExistingThresholds.setVisible(false);

		lbProf.setValue("None.");
		comboExistingThresholds.setValue(null);
		comboExistingThresholds.setVisible(false);
		lbThreshold.setVisible(false);
		tfThresholdAmount.setVisible(false);
		cNewThresholdBtns.setVisible(false);
		optMultT.setVisible(false);

		tfThresholdAmount.setValue("");
		cNewThresholdBtns.setVisible(false);
		optMultT.setVisible(false);
		optMultT.setValue(null);

		btnAdd.setVisible(false);

		// lbThreshold.setVisible(false);

		// VerticalLayout cThreshold = new VerticalLayout();
		// cThreshold.addComponent(comboExistingThresholds);
		// cThreshold.addComponent(lbThreshold);

		optMultT.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 978654770702305394L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				if (isEditThresholdPossible) {

					Collection<?> vals = null;

					comboExistingThresholds.removeAllItems();

					vals = (Collection<?>) optMultT.getValue();

					if (vals.size() == 1) {

						Iterator<?> itr = vals.iterator();
						while (itr.hasNext()) {
							String ttid = itr.next().toString();

							System.out.println("Transaction Type: " + ttid);

							Set<String> set = hmNONETH.get(ttid);
							if (set == null) {

								return;

							}

							for (String e : set) {
								System.out.println("THTID " + e);
								comboExistingThresholds.addItem(e);

								System.out.println("THT CAPTION "
										+ hmThresholdTypesByID.get(e));
								comboExistingThresholds.setItemCaption(e,
										hmThresholdTypesByID.get(e));
							}
						}
					}

				}

			}

		});

		btnAdd.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -3725874207089776839L;

			@Override
			public void buttonClick(ClickEvent event) {

				String pn = comboThresholdProfiles.getValue().toString();
				lbProf.setValue(pn);
				profileTID = hmProfIDs.get(pn).getProfiletypeid();
				initNew(pn);
				cPlaceholder.setVisible(false);
				cPlaceholder.removeAllComponents();
				btnAddThreshold.setVisible(false);

			}
		});

		btnNewSave.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8811206447199920643L;

			@Override
			public void buttonClick(ClickEvent event) {

				String threshold_type_id = null;

				Object spn = comboThresholdProfiles.getValue();
				if (spn == null || spn.toString().trim().isEmpty()) {
					Notification.show("Please provide a profile name",
							Notification.Type.ERROR_MESSAGE);

					return;
				}

				// if (!profileTID.equals("3"))
				if (optMultT.getValue() == null
						|| ((Collection<?>) optMultT.getValue()).size() == 0) {
					Notification.show(
							"Please select at least a Transaction Type",
							Notification.Type.ERROR_MESSAGE);
					optMultT.focus();
					return;
				}

				if (comboExistingThresholds.getValue() == null) {
					Notification.show("Please select a Threshold",
							Notification.Type.ERROR_MESSAGE);

					comboExistingThresholds.focus();
					return;
				}

				if (tfThresholdAmount.getValue() == null
						|| tfThresholdAmount.getValue().toString().trim()
								.isEmpty()) {
					Notification.show("Pleas provide amount",
							Notification.Type.ERROR_MESSAGE);

					tfThresholdAmount.focus();
					return;
				}

				try {

					Double.parseDouble(tfThresholdAmount.getValue());

				} catch (Exception e) {

					Notification.show(
							"Invalid Amount. Enter only Numbers please.",
							Notification.Type.ERROR_MESSAGE);

					tfThresholdAmount.focus();
					return;
				}

				String pn = spn.toString();
				Integer pid = Integer.parseInt(hmAllProfiles.get(pn));
				Collection<?> ctids = (Collection<?>) optMultT.getValue();

				int[] ttids = new int[ctids.size()];
				int ix = 0;
				for (Object tid : ctids) {
					ttids[ix] = Integer.valueOf(tid.toString()).intValue();
					ix++;
				}

				if (ix == 0) {
					ttids = new int[0];
				}

				Profile profile = hmProfIDs.get(pn);

				threshold_type_id = comboExistingThresholds.getValue()
						.toString();

				String amount = tfThresholdAmount.getValue();

				String response = null;

				try {

					System.out.println(pn + " : " + pid);

					System.out.println("Profile Name: " + pn);
					System.out.println("Profile ID: " + pid);
					System.out.println("Threshold Type ID: "
							+ threshold_type_id);
					System.out.println("Profile Type ID: "
							+ profile.getProfiletypeid());
					System.out.println("Transaction Type ID 1: "
							+ ((ttids.length == 1) ? ttids[0] : "NONE"));

					System.out.println("Transaction Type ID 2: "
							+ ((ttids.length == 2) ? ttids[1] : "NONE"));
					System.out.println("THRESHOLD VALUE: " + amount);

					System.out.println("SAVE THRESHOLD RESPONSE: " + response);

					response = UserManagementService.addProfileThreshold(pn,
							ttids, (int) pid,
							Integer.parseInt(threshold_type_id),
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

					// cAllProf.setVisible(true);
					cPlaceholder.setVisible(false);

					// Object temp = comboThresholdProfiles.getValue();
					comboThresholdProfiles.select(null);
					// comboThresholdProfiles.select(temp);
					// comboXTH.select(ctids.iterator().next());
					// comboX.select(ctids.iterator().next());

					for (int ttid : ttids)

						if (!hmNONETH.containsKey(String.valueOf(ttid))) {
							HashSet<String> hs = new HashSet<>();
							hs.add(threshold_type_id);

							hmNONETH.put(String.valueOf(ttid), hs);
						} else {
							hmNONETH.get(String.valueOf(ttid)).add(
									threshold_type_id);
						}

					return;
				} else {
					Notification
							.show(response, Notification.Type.ERROR_MESSAGE);
				}

			}

		});

		btnNewCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 8811206447199920643L;

			@Override
			public void buttonClick(ClickEvent event) {

				comboExistingThresholds.setVisible(false);
				tfThresholdAmount.setVisible(false);
				optMultT.setVisible(false);
				cPlaceholder.setVisible(false);
				btnAddThreshold.setVisible(true);
				cBeforePlaceHolder.addComponent(btnAddThreshold);
				cBeforePlaceHolder.setComponentAlignment(btnAddThreshold,
						Alignment.TOP_CENTER);
				comboExistingThresholds.setVisible(false);
				comboExistingThresholds.setVisible(false);
				cNewThresholdBtns.setVisible(false);
				Object temp = comboThresholdProfiles.getValue();
				comboThresholdProfiles.select(null);
				comboThresholdProfiles.select(temp);

			}
		});

		comboExistingThresholds.addFocusListener(new FocusListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5247357255633826292L;

			@Override
			public void focus(FocusEvent event) {

				Object obj = comboThresholdProfiles.getValue();
				if (obj == null)
					return;

			}

		});

		comboThresholdProfiles.setValue(null);

		comboThresholdProfiles
				.addValueChangeListener(new Property.ValueChangeListener() {

					private static final long serialVersionUID = -1655803841445897977L;

					@Override
					public void valueChange(ValueChangeEvent event) {

						Object obj = event.getProperty().getValue();

						isEditThresholdPossible = false;
						// setExistingTH.clear();

						if (obj == null) {
							// TODO
							lbProf.setValue("None.");
							comboExistingThresholds.setValue(null);
							comboExistingThresholds.setVisible(false);
							lbThreshold.setVisible(false);
							tfThresholdAmount.setVisible(false);
							cNewThresholdBtns.setVisible(false);
							optMultT.setVisible(false);

							tfThresholdAmount.setValue("");
							cNewThresholdBtns.setVisible(false);
							optMultT.setVisible(false);
							optMultT.setValue(null);
							cPlaceholder.setVisible(false);
							comboXTH.setVisible(false);
							comboX.setVisible(false);

							btnAddThreshold.setVisible(false);

							// TODO

							return;
						}

						lbProf.setValue(obj.toString());
						comboXTH.setVisible(true);
						comboX.setVisible(true);

						String pn = comboThresholdProfiles.getValue()
								.toString();

						System.out.println(pn);
						profileTID = hmProfIDs.get(pn).getProfiletypeid();

						initExisting(pn);

					}
				});

		comboX.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = -1655803841445897977L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				comboXTH.removeAllItems();

				Iterator<Entry<String, ArrayList<HashMap<String, String>>>> itr = hmExistingThresholdTypes
						.entrySet().iterator();

				Iterator<String> itrx = hmExistingThresholdTypes.keySet()
						.iterator();

				while (itrx.hasNext()) {
					String e = itrx.next();

					if (!e.equals(comboX.getValue())) {
						continue;
					}
					for (HashMap<String, String> hm : hmExistingThresholdTypes
							.get(e)) {
						String ex = hm.get("threshold_type_id");
						comboXTH.addItem(ex);
						comboXTH.setItemCaption(ex,
								hmThresholdTypesByID.get(ex));
					}

				}

			}
		});

		comboXTH.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = -1655803841445897977L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				if (event.getProperty().getValue() == null) {

					// TODO

					// comboExistingThresholds.setValue(null);
					// comboExistingThresholds.setVisible(false);
					lbThreshold.setVisible(true);
					tfThresholdAmount.setVisible(false);
					cNewThresholdBtns.setVisible(false);
					optMultT.setVisible(false);

					tfThresholdAmount.setValue("");
					cNewThresholdBtns.setVisible(false);
					optMultT.setVisible(false);
					optMultT.setValue(null);
					cPlaceholder.setVisible(false);

					// TODO

					lbThreshold.setValue("None.");

					cPlaceholder.setVisible(false);
					cPlaceholder.removeAllComponents();

				} else {

					if (comboXTH.getValue() == null) {
						NotifCustom.show("Promt",
								"Please select a Transaction Types.");
						return;
					}

					// TODO
					// lbProf.setValue("None.");
					// comboExistingThresholds.setValue(null);
					// comboExistingThresholds.setVisible(false);
					// lbThreshold.setVisible(false);
					// tfThresholdAmount.setVisible(false);
					// cNewThresholdBtns.setVisible(false);
					// optMultT.setVisible(false);
					//
					// tfThresholdAmount.setValue("");
					// cNewThresholdBtns.setVisible(false);
					// optMultT.setVisible(false);
					// optMultT.setValue(null);
					// cPlaceholder.setVisible(false);
					// TODO

					// if (profileTID.equals("3")) {
					// optMultT.setVisible(false);
					//
					// } else {
					// optMultT.setVisible(true);
					// }

					displayExistingThresholds(cPlaceholder, comboX.getValue()
							.toString(), comboXTH.getValue().toString());

					comboExistingThresholds.setVisible(false);
					lbThreshold.setVisible(false);
					// comboExistingThresholds.setVisible(false);
					comboXTH.setVisible(true);
					comboX.setVisible(true);
					tfThresholdAmount.setVisible(false);
					optMultT.setVisible(false);
					cNewThresholdBtns.setVisible(false);

				}
			}
		});

		comboThresholdProfiles.addFocusListener(new FocusListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1583773393542597237L;

			@Override
			public void focus(FocusEvent event) {

				if (comboThresholdProfiles.getItemIds().size() == 0)
					addProfiles(comboThresholdProfiles);
				// comboThresholdProfiles.select(null);

			}

		});

		tfThreshold.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = -1655803841445897977L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				isProfileNameChanged = true;

			}
		});

		btnEdit.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -8427226211153164650L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (btnEdit.getIcon().equals(FontAwesome.EDIT)) {
					isProfileNameChanged = false;
					tfThreshold.setValue(lbThreshold.getValue());
					tfThreshold.selectAll();
					cProfName.replaceComponent(lbThreshold, tfThreshold);
					btnEdit.setIcon(FontAwesome.SAVE);
					btnEdit.setDescription("Save changes");
					btnCancel.setVisible(true);
					return;

				} else if (btnEdit.getIcon().equals(FontAwesome.SAVE)) {

					if (!isProfileNameChanged)
						return;

					String pnNew = tfThreshold.getValue();
					if (pnNew == null || pnNew.trim().trim().isEmpty()) {
						Notification.show("Please provide new profile name.",
								Notification.Type.ERROR_MESSAGE);
						tfThreshold.focus();
						return;
					}

					if (hmAllProfiles.containsKey(pnNew.trim())) {
						Notification
								.show("\""
										+ pnNew
										+ "\" already exists. Please provide unique profile name.",
										Notification.Type.ERROR_MESSAGE);
						tfThreshold.focus();
						return;
					}

					lbThreshold.setValue(tfThreshold.getValue());
					cProfName.replaceComponent(tfThreshold, lbThreshold);
					btnEdit.setIcon(FontAwesome.EDIT);
					btnEdit.setDescription("Edit Threshold name");
					btnCancel.setVisible(false);

					isProfileNameChanged = false;

					Object profileName = comboThresholdProfiles.getValue();
					Integer pid = Integer.parseInt(hmAllProfiles
							.get(profileName.toString()));
					String pnOld = profileName.toString();

					System.out.println(" New PN: " + pnNew + " Old PN: "
							+ pnOld + " PID:" + pid);

					String response = null;

					try {
						response = UserManagementService.editProfile(pnNew,
								pnOld, pid);

						comboThresholdProfiles.addItem(pnNew);
						comboThresholdProfiles.select(pnNew);
						// hmAllProfiles.remove(pnOld);

						if (response == null || response.trim().isEmpty()) {
							Notification.show("No reponse from the server",
									Notification.Type.ERROR_MESSAGE);
							return;

						}

						if (response.toUpperCase().contains("SUCCESSFUL")) {
							lbThreshold.setValue(tfThreshold.getValue());
							cProfName
									.replaceComponent(tfThreshold, lbThreshold);
							btnEdit.setIcon(FontAwesome.EDIT);
							btnEdit.setDescription("Edit Threshold name");
							btnCancel.setVisible(false);

							NotifCustom.show("Response", response);
							hmAllProfiles.put(pnNew, pid.toString());
							comboPermProfiles.removeAllItems();
							comboPermProfiles.select(null);

							return;
						}

						Notification.show(response,
								Notification.Type.ERROR_MESSAGE);

					} catch (Exception e) {

						Notification.show(e.getMessage(),
								Notification.Type.ERROR_MESSAGE);

						e.printStackTrace();
					}

				}

			}
		});

		btnCancel.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -2870045546205986347L;

			@Override
			public void buttonClick(ClickEvent event) {
				cProfName.replaceComponent(tfThreshold, lbThreshold);
				btnEdit.setIcon(FontAwesome.EDIT);
				btnEdit.setDescription("Edit Threshold name");
				btnCancel.setVisible(false);

			}
		});

		return c;

	}

	private class RemoveProfileHandler implements Button.ClickListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Window pop;
		private Button btnCancel;
		private Button btnComfirm;
		private Label lbComfirm;

		RemoveProfileHandler(final Window pop) {
			this.pop = pop;

			VerticalLayout cComfirm = new VerticalLayout();

			lbComfirm = new Label();

			btnComfirm = new Button("Remove Profile");
			btnComfirm.setStyleName("btn_link");
			btnComfirm.setDescription("Remove current profile");

			btnCancel = new Button("Cancel");
			btnCancel.setStyleName("btn_link");
			btnCancel.setDescription("Cancel Profile removal");

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

					String pn = comboProfiles.getValue().toString();
					Integer pid = Integer.parseInt(hmAllProfiles.get(pn));
					String response = null;
					try {
						response = UserManagementService.removeProfile(pn, pid);
					} catch (Exception e) {
						Notification.show(e.getMessage(),
								Notification.Type.ERROR_MESSAGE);
						e.printStackTrace();
						return;
					}

					if (response == null || response.trim().isEmpty()) {
						Notification.show("No response from the server.",
								Notification.Type.ERROR_MESSAGE);
						return;
					}

					if (response.toUpperCase().contains("SUCCESSFUL")) {
						comboProfiles.removeAllItems();
						comboPermProfiles.removeAllItems();
						comboProfiles.select(null);
						comboPermProfiles.select(null);

						NotifCustom.show("Remove response", pn
								+ " removed successfully.");

					} else {
						Notification.show(response,
								Notification.Type.ERROR_MESSAGE);
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
			lbComfirm.setValue("Please comfirm removal  Profile \""
					+ comboProfiles.getValue().toString() + "\"");
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

					if (hmAllProfiles == null)
						addProfiles(comboProfiles);

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

					if (hmAllProfiles.containsKey(pn)) {
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
						// hmAllProfiles.put(pn, pid.toString());
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

	private void addProfiles(ComboBox combo) {
		hmProfIDs.clear();
		initProfiles();

		Set<Entry<String, String>> set = hmAllProfiles.entrySet();
		for (Entry<String, String> e : set) {

			Object key = e.getKey();
			combo.addItem(key);

		}

	}

	private void initProfiles() {

		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		try {
			hmProfIDs.clear();
			profiles = rs.getProfiles();
			Map<String, String> hmTemp = new HashMap<>();
			for (Profile profile : profiles) {
				hmTemp.put(profile.getProfilename(), profile.getProfileid());
				hmProfIDs.put(profile.getProfilename(), profile);

			}

			hmAllProfiles = hmTemp;

		} catch (RemoteException | DataServiceFault e) {

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

		@Override
		public void buttonClick(ClickEvent event) {

			Object obj = comboThresholdProfiles.getValue();
			if (obj == null || obj.toString().trim().isEmpty()) {
				NotifCustom.show("Prompt", "Please select a profile Type.");
				return;
			}

			if (hmProfIDs == null || hmProfIDs.size() == 0)
				initProfiles();

			ptid = hmProfIDs.get(
					comboThresholdProfiles.getValue().toString().trim())
					.getProfiletypeid();

			if (ptid.equals("3")) {
				optMultTrans.setVisible(false);

			} else {
				optMultTrans.setVisible(true);
			}

			tFProfx.setEnabled(true);
			tFProfx.setReadOnly(false);

			tFProfx.setValue(comboThresholdProfiles.getValue().toString());

			tFProfx.setEnabled(false);
			tFProfx.setReadOnly(true);

			cPlaceholder.setVisible(true);
			cAllProf.setVisible(false);
			tFProf.setValue("");
			combo.select(null);
			addThresholds(combo, ptid);
			addTransactionTypes(optMultTrans);

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

				hmThresholdTypesByID = rs.getThresholdTypes(pid);

				Set<Entry<String, String>> set = hmThresholdTypesByID
						.entrySet();
				for (Entry<String, String> e : set) {

					Object key = e.getKey();
					optMult.addItem(key);
					optMult.setItemCaption(key, e.getValue());

				}

			} catch (RemoteException | DataServiceFault e) {

				e.printStackTrace();
			}

		}

	}

	private void initTransactionTypes() {

		try {

			if (isEditThresholdPossible) {

				optMultT.removeAllItems();
				comboExistingThresholds.removeAllItems();

				Iterator<Entry<String, HashSet<String>>> i = hmETH.entrySet()
						.iterator();

				while (i.hasNext()) {
					Entry<String, HashSet<String>> e = i.next();

					System.out.print(e.getKey());
					System.out.print("[");
					for (String s : e.getValue())
						System.out.print(", " + s);

					System.out.println("]");

				}

				Iterator<Entry<String, String>> itrx = hmTransactionTypes
						.entrySet().iterator();

				System.out.println("THE OTHER Total THT: "
						+ hmThresholdTypes.size());

				int x = 0;

				while (itrx.hasNext()) {

					Iterator<Entry<String, String>> itr = hmThresholdTypes
							.entrySet().iterator();

					Entry<String, String> exTT = itrx.next();

					String TTID = exTT.getValue();

					System.out.print(TTID);
					System.out.print("[");

					while (itr.hasNext()) {
						x++;

						Entry<String, String> eTHT = itr.next();

						String THTID = eTHT.getValue();

						System.out.print(", " + THTID);
						//
						// System.out.println(THTID + " THTID");
						//
						// System.out.println(TTID + " TTID");

						HashSet<String> set = hmETH.get(TTID);

						if (set == null || !set.contains(THTID)) {
							optMultT.addItem(TTID);
							optMultT.setItemCaption(TTID,
									hmTransactionTypesByID.get(TTID));

							comboExistingThresholds.addItem(THTID);
							comboExistingThresholds.setItemCaption(THTID,
									hmThresholdTypesByID.get(THTID));

							// System.out.println("Vallue: " + TTID);
							// System.out.println("Caption: "
							// + hmThresholdTypesByID.get(TTID));

							if (!hmNONETH.containsKey(TTID)) {
								HashSet<String> hs = new HashSet<>();
								hs.add(THTID);

								hmNONETH.put(TTID, hs);
							} else {
								hmNONETH.get(TTID).add(THTID);
							}
						}

					}

					System.out.println("]");

				}

				System.out.println("FINAL COUNT: " + x);

				return;
			}

			optMultT.removeAllItems();

			hmTransactionTypesByID.clear();
			hmTransactionTypes = rs.getTransactionTypes();

			Iterator<Entry<String, String>> itr = hmTransactionTypes.entrySet()
					.iterator();
			while (itr.hasNext()) {
				Entry<String, String> e = itr.next();
				hmTransactionTypesByID.put(e.getValue(), e.getKey());
				// if (setExistingTT.contains(e.getKey())) {
				optMultT.addItem(e.getValue());
				optMultT.setItemCaption(e.getValue(), e.getKey());
				// }

			}

		} catch (RemoteException | DataServiceFault e) {

			e.printStackTrace();
		}

	}

	private class RemoveThresholdHandler implements Button.ClickListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Window pop;
		private Button btnCancel;
		private Button btnComfirm;
		private Label lbC;
		private IndexedContainer containerxyz;

		RemoveThresholdHandler(final Window pop, final IndexedContainer tb) {
			this.pop = pop;
			this.containerxyz = tb;
			pop.setCaption("Delete Threshold");

			VerticalLayout cComfirm = new VerticalLayout();

			lbC = new Label();

			btnComfirm = new Button("Remove Threshold");
			btnComfirm.setStyleName("btn_link");
			btnComfirm.setDescription("Remove current Threshold");

			btnCancel = new Button("Cancel");
			btnCancel.setStyleName("btn_link");
			btnCancel.setDescription("Cancel Theshold removal");

			HorizontalLayout cBtns = new HorizontalLayout();
			cBtns.addComponent(btnComfirm);
			cBtns.addComponent(btnCancel);

			cBtns.setSpacing(true);

			cComfirm.addComponent(lbC);
			cComfirm.addComponent(cBtns);

			cComfirm.setSpacing(true);
			cComfirm.setMargin(true);

			cComfirm.setComponentAlignment(lbC, Alignment.TOP_CENTER);
			cComfirm.setComponentAlignment(cBtns, Alignment.TOP_CENTER);

			pop.setContent(cComfirm);

			btnComfirm.addClickListener(new Button.ClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@SuppressWarnings("unchecked")
				@Override
				public void buttonClick(ClickEvent event) {

					pop.close();

					String pn = comboThresholdProfiles.getValue().toString();
					Integer pid = Integer.parseInt(hmAllProfiles.get(pn));
					String response = null;
					try {

						ArrayList<String> ctids = new ArrayList<String>();

						for (Object rid : containerxyz.getItemIds()) {

							String idsx = tb.getItem(rid)
									.getItemProperty("Transaction Type")
									.getValue().toString();

							ctids.add(hmTransactionTypes.get(idsx));
							System.out.println(idsx);

						}

						int[] ttids = new int[ctids.size()];
						int ix = 0;
						for (Object tid : ctids) {
							ttids[ix] = Integer.valueOf(tid.toString())
									.intValue();
							ix++;
						}
						response = UserManagementService
								.deleteProfileThreshold(ttids, pid);
					} catch (Exception e) {
						Notification.show(e.getMessage(),
								Notification.Type.ERROR_MESSAGE);
						e.printStackTrace();
						return;
					}

					if (response == null || response.trim().isEmpty()) {
						Notification.show("No response from the server.",
								Notification.Type.ERROR_MESSAGE);
						return;
					}

					if (response.toUpperCase().contains("SUCCESSFUL")) {
						// comboProfiles.removeAllItems();
						// comboPermProfiles.removeAllItems();
						comboThresholdProfiles.select(null);
						// comboPermProfiles.select(null);

						NotifCustom.show("Remove response",
								"Threshold removed successfully.");

					} else {
						Notification.show(response,
								Notification.Type.ERROR_MESSAGE);
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
			lbC.setValue("Please comfirm removal of Threshold from Profile: \""
					+ comboThresholdProfiles.getValue().toString() + "\"");
			UI.getCurrent().addWindow(pop);

			pop.center();

		}
	}

	@SuppressWarnings("unchecked")
	private void displayExistingThresholds(HorizontalLayout cPlaceholder,
			String ttid, String thtid) {

		isEditThresholdPossible = true;

		initTransactionTypes();

		System.out.println("VALUE OF NONETH IN DISPLAY: " + hmNONETH.size());

		// String ttid = hmTransactionTypes.get(tt);
		cPlaceholder.removeAllComponents();

		cPlaceholder.setVisible(true);

		int x = 1;

		ArrayList<HashMap<String, String>> arr = hmExistingThresholdTypes
				.get(ttid);

		System.out.println(arr.size());

		VerticalLayout cAllTables = new VerticalLayout();
		cPlaceholder.addComponent(cAllTables);
		cAllTables.setSpacing(true);

		String tt = hmTransactionTypesByID.get(ttid);

		for (HashMap<String, String> hm : arr) {

			String thtidx = hm.get("threshold_type_id");

			if (!thtidx.equals(thtid)) {
				continue;
			}

			VerticalLayout cT = new VerticalLayout();
			cAllTables.addComponent(cT);

			final Table tb = new Table(tt + " Threshold configurations");
			final IndexedContainer c = new IndexedContainer();
			c.addContainerProperty("S/N", String.class, "");
			c.addContainerProperty("Transaction Type", String.class, "");
			c.addContainerProperty("Threshold Type", String.class, "");
			c.addContainerProperty("Threshold Value", String.class, "");

			Object rid = c.addItem();
			Item r = c.getItem(rid);
			r.getItemProperty("S/N").setValue(String.valueOf(x));
			r.getItemProperty("Transaction Type").setValue(tt);

			r.getItemProperty("Threshold Type").setValue(
					hmThresholdTypesByID.get(thtidx));

			r.getItemProperty("Threshold Value").setValue(hm.get("limit"));

			tb.setContainerDataSource(c);
			if (x > 30)
				x = 30;
			tb.setPageLength(1);
			tb.setSelectable(true);

			Button btnDelete = new Button();
			btnDelete.setDescription("Delete Threshold");
			btnDelete.setStyleName("btn_link");
			btnDelete.setIcon(FontAwesome.RECYCLE);

			final Button btnEdit = new Button();
			btnEdit.setDescription("Edit Threshold");
			btnEdit.setStyleName("btn_link");
			btnEdit.setIcon(FontAwesome.EDIT);

			Button btnCancel = new Button();
			btnCancel.setDescription("Cancel");
			btnCancel.setStyleName("btn_link");
			btnCancel.setIcon(FontAwesome.UNDO);

			HorizontalLayout cBtns = new HorizontalLayout();
			cBtns.addComponent(btnEdit);
			if (hmNONETH.size() == 0) {
				btnAddThreshold.setVisible(false);
			} else {
				cBtns.addComponent(btnAddThreshold);
				btnAddThreshold.setVisible(true);

			}
			cBtns.addComponent(btnDelete);
			cBtns.addComponent(btnCancel);

			cT.addComponent(tb);
			cT.addComponent(cBtns);

			cT.setComponentAlignment(cBtns, Alignment.BOTTOM_RIGHT);

			btnEdit.addClickListener(new Button.ClickListener() {

				/**
	 *
	 */
				private static final long serialVersionUID = -3936125405952974484L;

				@Override
				public void buttonClick(ClickEvent event) {

					if (btnEdit.getIcon().equals(FontAwesome.ELLIPSIS_H)) {
						return;
					}

					if (btnEdit.getIcon().equals(FontAwesome.EDIT)) {
						reloadFields(c);
						btnAddThreshold.setVisible(false);
						btnEdit.setIcon(FontAwesome.SAVE);
						btnEdit.setDescription("Save New Threshold Value");

						return;
					}

					if (tfThresholdAmount.getValue() == null
							|| tfThresholdAmount.getValue().toString().trim()
									.isEmpty()) {
						Notification.show("Pleas provide amount",
								Notification.Type.ERROR_MESSAGE);

						tfThresholdAmount.focus();
						return;
					}

					if (Double.parseDouble(curThresholdValue) == Double
							.parseDouble(tfThresholdAmount.getValue()
									.toString().trim())) {

						NotifCustom
								.show("Message",
										"No changes to save. Please make changes and save");

						return;
					}

					btnEdit.setEnabled(false);
					btnEdit.setIcon(FontAwesome.ELLIPSIS_H);
					btnEdit.setImmediate(true);
					String amount = tfThresholdAmount.getValue();
					tfThresholdAmount.setReadOnly(true);
					tfThresholdAmount.setImmediate(true);

					String pn = comboThresholdProfiles.getValue().toString();
					Integer pid = Integer.parseInt(hmAllProfiles.get(pn));
					ArrayList<String> ctids = new ArrayList<String>();

					String transactionType = null;
					String transactionTypeID = null;
					String thresholdType = null;
					String thresholdTypeID = null;

					for (Object rid : c.getItemIds()) {

						String idsx = c.getItem(rid)
								.getItemProperty("Transaction Type").getValue()
								.toString();

						transactionTypeID = hmTransactionTypes.get(idsx);

						ctids.add(transactionTypeID);
						System.out.println(idsx);

						transactionType = idsx;

						thresholdType = c.getItem(rid)
								.getItemProperty("Threshold Type").getValue()
								.toString();

					}

					System.out.println("ThresholdType: " + thresholdType);

					int[] ttids = new int[ctids.size()];
					int ix = 0;
					for (Object tid : ctids) {
						ttids[ix] = Integer.valueOf(tid.toString()).intValue();
						ix++;
					}

					// HashMap<String, String> hm = hmExistingThresholdTypes
					// .get(hmTransactionTypes.get(transactionType));

					System.out.println("Any transaction type: "
							+ transactionType);

					// System.out.println(hm.size() + " Are null?");

					String profiletypeid = profileTID;
					thresholdTypeID = hmThresholdTypes.get(thresholdType);

					String response = null;

					try {

						System.out.println(pn + " : " + pid);
						response = UserManagementService.editProfileThreshold(
								pn, ttids, (int) pid,
								Integer.parseInt(thresholdTypeID),
								Integer.parseInt(profiletypeid), amount);

					} catch (Exception e) {
						Notification.show(e.getMessage(),
								Notification.Type.ERROR_MESSAGE);
						btnEdit.setEnabled(true);
						btnEdit.setIcon(FontAwesome.EDIT);
						btnAddThreshold.setVisible(true);
						tfThresholdAmount.setReadOnly(false);
						e.printStackTrace();
						return;
					}
					if (response == null || response.trim().isEmpty()) {
						Notification.show("Unknown operation status",
								Notification.Type.ERROR_MESSAGE);
						tfThresholdAmount.setReadOnly(false);
						return;
					}

					btnEdit.setEnabled(false);
					tfThresholdAmount.setReadOnly(true);

					if (response.toUpperCase().contains("SUCCESS")) {
						NotifCustom.show("Threshold", pn
								+ "'s Threshold was successfully configured.");

						btnEdit.setIcon(FontAwesome.EDIT);
						tfThresholdAmount.setVisible(false);
						tfThresholdAmount.setReadOnly(false);
						btnAddThreshold.setVisible(true);
						btnEdit.setEnabled(true);

						c.getItem(c.getItemIds().iterator().next())
								.getItemProperty("Threshold Value")
								.setValue(tfThresholdAmount.getValue());
						tb.setContainerDataSource(c);
						tb.setPageLength(c.size());

						return;
					} else {
						tfThresholdAmount.setReadOnly(false);
						btnAddThreshold.setVisible(true);
						btnEdit.setEnabled(true);
						btnEdit.setIcon(FontAwesome.EDIT);

						Notification.show(response,
								Notification.Type.ERROR_MESSAGE);
					}

					// UserManagementService.editProfileThreshold(profilename,
					// transactiontypeids, profileid, thresholdtypeid,
					// profiletypeid, value);

				}
			});

			btnDelete.addClickListener(new RemoveThresholdHandler(new Window(),
					c));

			btnCancel.addClickListener(new Button.ClickListener() {

				/**
	 *
	 */
				private static final long serialVersionUID = -5230632418750433091L;

				@Override
				public void buttonClick(ClickEvent event) {

					if (btnEdit.getIcon().equals(FontAwesome.SAVE)) {
						btnEdit.setIcon(FontAwesome.EDIT);
						tfThresholdAmount.setVisible(false);
						tfThresholdAmount.setReadOnly(false);
						btnAddThreshold.setVisible(true);

					} else {

						comboXTH.select(null);
						comboX.select(null);
						tfThresholdAmount.setVisible(false);
						tfThresholdAmount.setReadOnly(false);
						cBeforePlaceHolder.addComponent(btnAddThreshold);
						cBeforePlaceHolder.setComponentAlignment(
								btnAddThreshold, Alignment.TOP_CENTER);
						btnAddThreshold.setVisible(false);
						isEditThresholdPossible = false;

					}

				}
			});

		}

	}

	private void initThresholdTypes(String pn) {

		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		try {

			Map<String, String> hm = hmThresholdTypesByID = rs
					.getThresholdTypes(hmProfIDs.get(pn).getProfiletypeid());
			if (hm == null || hm.size() == 0) {
				hmThresholdTypesByID.clear();
				hmThresholdTypes.clear();
				return;
			}

			hmThresholdTypesByID = hm;
			hmThresholdTypes.clear();

			comboExistingThresholds.removeAllItems();

			Iterator<Entry<String, String>> itr = hmThresholdTypesByID
					.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<String, String> e = itr.next();
				hmThresholdTypes.put(e.getValue(), e.getKey());

				// if (!isEditThresholdPossible) {

				comboExistingThresholds.addItem(e.getKey());
				comboExistingThresholds
						.setItemCaption(e.getKey(), e.getValue());
				// }

			}

		} catch (RemoteException | DataServiceFault e) {

			e.printStackTrace();
		}

	}

	private void initExisting(String pn) {
		initExistingThresholdTypes(pn);
		initThresholdTypes(pn);
		initComponentsForExisting();

	}

	private void initNew(String pn) {
		initThresholdTypes(pn);
		initTransactionTypes();
		initComponentsForNew();
	}

	private void initComponentsForNew() {
		comboXTH.setVisible(false);
		comboX.setVisible(false);
		tfThresholdAmount.setValue("");
		// if (profileTID.equals("3")) {
		// optMultT.setVisible(false);
		// optMultT.removeAllItems();
		// optMultT.select(null);
		// } else {
		// optMultT.setVisible(true);
		// }
		optMultT.setVisible(true);
		tfThresholdAmount.setVisible(true);

		lbThreshold.setVisible(true);
		comboExistingThresholds.setVisible(true);
		cNewThresholdBtns.setVisible(true);

		// comboExistingThresholds.removeAllItems();lll

	}

	private void initComponentsForExisting() {

		comboXTH.removeAllItems();
		comboX.removeAllItems();
		comboXTH.setVisible(false);
		comboX.setVisible(false);
		tfThresholdAmount.setValue("");
		tfThresholdAmount.setVisible(false);
		optMultT.removeAllItems();
		optMultT.setVisible(false);
		lbThreshold.setVisible(false);
		comboExistingThresholds.setVisible(false);
		comboExistingThresholds.removeAllItems();
		cNewThresholdBtns.setVisible(false);

		if (hmExistingThresholdTypes.size() == 0) {

			cBeforePlaceHolder.addComponent(btnAddThreshold);
			cBeforePlaceHolder.setComponentAlignment(btnAddThreshold,
					Alignment.TOP_CENTER);
			btnAddThreshold.setVisible(true);

			hmETH.clear();
			hmNONETH.clear();
			setExistingTT.clear();

			NotifCustom
					.show("Message",
							"No Existing Threshold Configurations for selected Profile");

			return;
		}

		initTransactionTypes();
		Iterator<Entry<String, ArrayList<HashMap<String, String>>>> itr = hmExistingThresholdTypes
				.entrySet().iterator();

		Iterator<String> itrx = hmExistingThresholdTypes.keySet().iterator();

		/*
		 * Clear previous existing Transaction Types
		 */

		setExistingTT.clear();
		hmETH.clear();
		hmNONETH.clear();

		while (itrx.hasNext()) {

			String e = itrx.next();

			for (HashMap<String, String> hm : hmExistingThresholdTypes.get(e)) {
				String ex = hm.get("threshold_type_id");
				comboXTH.addItem(ex);
				comboXTH.setItemCaption(ex, hmThresholdTypesByID.get(ex));
				System.out.println("Vallue: " + ex);
				System.out.println("Caption: " + hmThresholdTypesByID.get(ex));
				if (!hmETH.containsKey(e)) {
					HashSet<String> set = new HashSet<>();
					set.add(ex);
					hmETH.put(e, set);
				} else {
					hmETH.get(e).add(ex);
				}

			}

			// System.out.println("Caption: " + hmTransactionTypesByID.get(e));
			// System.out.println("Value: " + e);

			comboX.addItem(e);
			comboX.setItemCaption(e, hmTransactionTypesByID.get(e));
			setExistingTT.add(e);

		}
		comboXTH.setVisible(true);
		comboX.setVisible(true);

	}

	private void initExistingThresholdTypes(String pn) {
		comboXTH.removeAllItems();
		comboX.removeAllItems();
		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		try {

			hmThresholdTypes.clear();

			String pid = hmAllProfiles.get(pn);
			hmExistingThresholdTypes = rs.getExistingThresholds(pid);
			// System.out.println("RETURN: " + hmExistingThresholdTypes.size());
			if (hmExistingThresholdTypes == null) {
				hmExistingThresholdTypes = new HashMap<>();
				return;

			}

		} catch (RemoteException | DataServiceFault e) {

			e.printStackTrace();
		}

	}

	private void addTransactionTypes(OptionGroup optMult) {
		optMult.removeAllItems();

		System.out.println(optMult.getCaption() + ": -------");

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

				Object key = e.getValue();
				optMult.addItem(key);
				optMult.setItemCaption(key, e.getKey());

				System.out.println(key + " : " + e.getValue());

			}

		} catch (RemoteException | DataServiceFault e) {

			e.printStackTrace();
		}

	}

	private void addThresholdThresholdTypes(ComboBox optMult, String pid) {
		optMult.removeAllItems();

		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		Set<Entry<String, String>> set = hmThresholdTypesByID.entrySet();
		for (Entry<String, String> e : set) {

			Object key = e.getKey();
			optMult.addItem(key);
			optMult.setItemCaption(key, e.getValue());

		}

	}

	private void reloadFields(IndexedContainer container) {
		curThresholdValue = container
				.getItem(container.getItemIds().iterator().next())
				.getItemProperty("Threshold Value").getValue().toString();
		tfThresholdAmount.setValue(curThresholdValue);

		comboExistingThresholds.setVisible(false);
		tfThresholdAmount.setVisible(true);
		optMultT.setVisible(false);
		cNewThresholdBtns.setVisible(false);

		return;
	}

	private void addExistingTransactionTypes(ComboBox comb) {

	}

}
