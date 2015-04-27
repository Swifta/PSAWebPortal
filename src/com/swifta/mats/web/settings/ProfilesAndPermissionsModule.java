package com.swifta.mats.web.settings;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.usermanagement.BtnTabLike;
import com.swifta.mats.web.usermanagement.NotifCustom;
import com.swifta.mats.web.utils.ReportingService;
import com.swifta.mats.web.utils.UserManagementService;
import com.swifta.sub.mats.reporting.DataServiceFault;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ProfilesAndPermissionsModule {

	private VerticalLayout cMain = new VerticalLayout();
	private HorizontalLayout cStage = new HorizontalLayout();
	private HorizontalLayout p;
	private HorizontalLayout cProfile;
	private HorizontalLayout cPerm;
	private HorizontalLayout prevL;
	private Window pop = new Window("Comfirm profile removal");
	private boolean isfromsub;
	private boolean isProfileNameChanged = false;
	private ReportingService rs;
	private boolean isProfilesAdded = false;
	private ComboBox comboProfiles;
	private HashMap<String, String> hmAllProfiles;
	private HashMap<String, String> hmProfileTypes;

	ProfilesAndPermissionsModule() {
		addMenu();
		cProfile = getPC();
		cStage.addComponent(cProfile);
		cMain.addComponent(cStage);
		cMain.setComponentAlignment(cStage, Alignment.TOP_CENTER);

	}

	private void addMenu() {

		VerticalLayout cMenu = new VerticalLayout();
		cMenu.setStyleName("c_u_manage_menu");
		cMenu.setSizeUndefined();

		HorizontalLayout cMainMenu = new HorizontalLayout();

		final BtnTabLike btnProfiles = new BtnTabLike("Profiles", null);
		btnProfiles.setStyleName("btn_tab_like btn_tab_like_active");
		btnProfiles.setEnabled(false);

		final BtnTabLike btnPermissions = new BtnTabLike("Permissions", null);

		cMainMenu.addComponent(btnProfiles);
		cMainMenu.addComponent(btnPermissions);
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

				if (!isfromsub) {
					p = (HorizontalLayout) cStage.getComponent(0);
					cStage.replaceComponent(p, cProfile);
				} else {
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

	}

	public VerticalLayout getMainContainer() {
		return cMain;
	}

	private HorizontalLayout getPermissionsC() {
		HorizontalLayout c = new HorizontalLayout();
		c.setSizeFull();
		Label lb = new Label("No Data Available!");
		c.addComponent(lb);
		c.setMargin(true);
		c.setComponentAlignment(lb, Alignment.MIDDLE_CENTER);
		return c;
	}

	private HorizontalLayout getPC() {

		VerticalLayout cAgentInfo = new VerticalLayout();
		final HorizontalLayout cPlaceholder = new HorizontalLayout();
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

		lbProf.setCaption("Selected Profile Name: ");
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

		final Button btnCancel = new Button();
		btnCancel.setIcon(FontAwesome.UNDO);
		btnCancel.setStyleName("btn_link");
		btnCancel.setDescription("Cancel Profile name editting.");

		Button btnAdd = new Button("+");
		// btnAdd.setIcon(FontAwesome.EDIT);
		btnAdd.setStyleName("btn_link");
		btnAdd.setDescription("Set new profile");

		Button btnRemove = new Button("-");
		// btnRemove.setIcon(FontAwesome.EDIT);
		btnRemove.setStyleName("btn_link");
		btnRemove.setDescription("Remove current profile");

		cProfNameAndAddBtn.addComponent(cProfName);
		cProfNameAndAddBtn.addComponent(btnAdd);

		// cProf.addComponent(cProfName);
		cProfActions.addComponent(btnEdit);
		cProfActions.addComponent(btnCancel);
		// cProfActions.addComponent(btnAdd);
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
		// addLinkUserContainer();
		cPlaceholder.setWidth("100%");

		cLBody.addComponent(cPlaceholder);
		cLBody.setComponentAlignment(cPlaceholder, Alignment.TOP_CENTER);
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

				if (!isProfilesAdded)
					addProfiles(comboProfiles);
				isProfilesAdded = true;

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
					btnCancel.setVisible(false);

					isProfileNameChanged = false;

					Object profileName = comboProfiles.getValue();
					Integer pid = Integer.parseInt(hmAllProfiles
							.get(profileName.toString()));
					String pnOld = profileName.toString();

					System.out.println(" New PN: " + pnNew + " Old PN: "
							+ pnOld + " PID:" + pid);

					try {
						// TODO UserManagementService.editProfile(pnNew, pnOld,
						// pid);

						comboProfiles.addItem(pnNew);
						comboProfiles.removeItem(profileName);
						comboProfiles.select(pnNew);
						hmAllProfiles.remove(pnOld);
						hmAllProfiles.put(pnNew, pid.toString());

						// hmAllProfiles.replace(pnOld, pnNew);

					} catch (Exception e) {

						e.printStackTrace();
					}

					return;
				}
				lbProf.setValue(tFProf.getValue());
				cProfName.replaceComponent(tFProf, lbProf);
				btnEdit.setIcon(FontAwesome.EDIT);
				btnCancel.setVisible(false);

			}
		});

		btnCancel.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -2870045546205986347L;

			@Override
			public void buttonClick(ClickEvent event) {
				cProfName.replaceComponent(tFProf, lbProf);
				btnEdit.setIcon(FontAwesome.EDIT);
				btnCancel.setVisible(false);

			}
		});

		btnAdd.addClickListener(new AddProfileHandler(cAllProf, cPlaceholder));

		btnRemove.addClickListener(new RemoveProfileHandler(pop));

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
						comboProfiles.select(null);
						comboProfiles.removeItem(pn);
						hmAllProfiles.remove(pn);
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
			combo = new ComboBox("Profile");
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
						hmAllProfiles.put(pn, pid.toString());
						comboProfiles.addItem(pn);
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

		if (hmProfileTypes != null)
			return;

		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {
				e.printStackTrace();
			}
		}

		try {

			hmProfileTypes = rs.getProfiles();
			Set<Entry<String, String>> set = hmProfileTypes.entrySet();
			for (Entry<String, String> e : set) {

				System.out.println(e.getKey() + " : " + e.getValue());
				Object key = e.getValue();
				combo.addItem(key);
				combo.setItemCaption(key, e.getKey());

			}

		} catch (RemoteException | DataServiceFault e) {

			e.printStackTrace();
		}

	}

	private void addProfiles(ComboBox combo) {

		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		try {
			if (hmAllProfiles != null)
				return;
			hmAllProfiles = rs.getProfiles();
			Set<Entry<String, String>> set = hmAllProfiles.entrySet();
			for (Entry<String, String> e : set) {

				Object key = e.getKey();
				combo.addItem(key);
				System.out.println("-------:" + key + ":-------");

			}

		} catch (RemoteException | DataServiceFault e) {

			e.printStackTrace();
		}

	}
}
