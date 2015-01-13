package com.swifta.mats.web.usermanagement;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.swifta.mats.web.utils.UserManagementService;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class BE2 {
	private BtnActions btnDetails;
	private BtnActions btnEdit;
	private BtnActions btnLink;
	private BtnActions btnDelete;
	private BtnActions btnActivate;
	private BtnActions btnMoreActions;
	private Button btnSetParent;
	private Button btnSetDefaultAcc;

	private List<CheckBox> arrLChkBulk;
	private List<ComboBox> arrLCombos;
	HorizontalLayout actionsC;
	private boolean isPopupShowing;
	private boolean isSent = false;
	UserManagementService ums;
	private int rowCount;
	private int chkCount;
	private boolean isSingleChange = false;
	private boolean isValidatorAdded = false;
	PagedTableCustom tb;

	ArrayList<String> arrLBulkIDs;

	ThemeResource icDelete;
	HashMap<String, Integer> hm;
	HashMap<String, String> hmFilter;
	IndexedContainer container;

	BE2() {
		icDelete = new ThemeResource("img/ic_delete_small.png");
		isPopupShowing = false;
		hm = new HashMap<>();
		hm.put("MATS_ADMIN_USER_PROFILE", 1);
		hm.put("MATS_FINANCIAL_CONTROLLER_USER_PROFILE", 3);
		hm.put("MATS_CUSTOMER_CARE_USER_PROFILE", 4);
		hm.put("MATS_SUPER_AGENT_USER_PROFILE", 6);
		hm.put("MATS_SUB_AGENT_USER_PROFILE", 7);
		hm.put("MATS_DEALER_USER_PROFILE", 11);
		hm.put("MATS_SERVICE_PROVIDER_USER_PROFILE", 15);

	}

	public VerticalLayout queryBackEnd(String strSearchParams) {
		rowCount = 0;
		if (container == null)
			container = getTable();
		hmFilter = new HashMap<>();
		VerticalLayout searchResultsContainer = new VerticalLayout();
		searchResultsContainer.setSizeUndefined();
		searchResultsContainer.setSpacing(true);
		searchResultsContainer
				.setMargin(new MarginInfo(false, true, true, true));

		String[] arrAllParams = strSearchParams.split("&");

		StringBuilder strBuilder = new StringBuilder();

		String[] arrP = null;
		for (String strParam : arrAllParams) {
			arrP = strParam.split("=");
			if (arrP.length == 2) {
				hmFilter.put(arrP[0], arrP[1]);
				strBuilder.append(arrP[0]);
				strBuilder.append(" of ");
				strBuilder.append(arrP[1]);
				strBuilder.append(", ");
			}
		}

		int iLastIndexOfComma = strBuilder.lastIndexOf(",");
		if (iLastIndexOfComma != -1) {
			strBuilder.delete(iLastIndexOfComma, iLastIndexOfComma + 1);
		}
		String strSearchResultsParams = null;
		iLastIndexOfComma = strBuilder.lastIndexOf(",");
		if (iLastIndexOfComma != -1) {
			strBuilder
					.replace(iLastIndexOfComma, iLastIndexOfComma + 1, " and");
			strSearchResultsParams = strBuilder.toString();
		} else {
			strSearchResultsParams = strBuilder.toString();
		}
		// Notification.show(Integer.toString(strSearchResultsParams.length()));
		strSearchResultsParams = strBuilder.toString();
		if (strSearchResultsParams.length() == 0) {
			strSearchResultsParams = "Nothing.";
		}

		Label lbSearch = new Label();
		lbSearch.setSizeUndefined();
		lbSearch.setStyleName("label_search_user");
		lbSearch.setSizeUndefined();

		VerticalLayout searchUserHeader = new VerticalLayout();
		searchUserHeader.setHeightUndefined();
		searchUserHeader.setWidthUndefined();
		searchUserHeader.setMargin(false);
		searchUserHeader.setSpacing(true);
		// searchUserHeader.addComponent(emb);
		searchUserHeader.addComponent(lbSearch);
		searchUserHeader.setStyleName("search_results_header");
		searchResultsContainer.addComponent(searchUserHeader);

		addFilters(hmFilter, container);

		lbSearch.setValue("(" + container.size() + ")Match for: "
				+ strSearchResultsParams);

		tb = new PagedTableCustom("Results (Summary)");

		tb.setContainerDataSource(container);
		tb.setColumnIcon(" ", FontAwesome.CHECK_SQUARE_O);
		tb.setWidth("900px");
		tb.setSelectable(true);

		int t = tb.size();
		rowCount = t;

		if (t > 30) {
			t = 30;
		}

		tb.setPageLength(t);

		// HorizontalLayout ctb = new HorizontalLayout();
		// ctb.setSizeUndefined();
		// ctb.addComponent(tb);
		// ctb.setWidth(width);
		// tb.setWidth("800px");
		// Panel panel = new Panel();
		// panel.setWidth("800");
		// panel.setContent(tb);
		// panel.sets
		// ctb.setWidth(width);
		// ctb.setStyleName("tb_s_r");

		// HorizontalLayout upperControls = getControls(tb);
		// HorizontalLayout lowerControls = upperControls;
		searchResultsContainer.addComponent(getBulkActionsC());
		searchResultsContainer.addComponent(getControls(tb));
		searchResultsContainer.addComponent(tb);
		searchResultsContainer.addComponent(getControls(tb));
		searchResultsContainer.addComponent(getBulkActionsC());

		// searchResultsContainer.setStyleName("s_r_c");

		return searchResultsContainer;
	}

	private void showDeleteUserContainer(final ArrayList<String> arrLBulkIDs,
			final String[] arrID) {

		isPopupShowing = true;
		isSent = false;
		String username = null;
		if (arrID != null) {
			username = arrID[3] + "'s Account";
		} else {
			username = "Selected Accounts";
		}

		final Window popup = new Window("Delete " + username);
		popup.setStyleName("w_delete_user");
		ThemeResource r = new ThemeResource("img/ic_delete_small.png");
		popup.setIcon(r);

		popup.center();

		VerticalLayout cDeletePrompt = new VerticalLayout();
		cDeletePrompt.setSpacing(true);
		cDeletePrompt.setMargin(true);

		Label lbDeleteMsg = new Label("Are you sure you want to delete "
				+ username + "?");
		cDeletePrompt.addComponent(lbDeleteMsg);

		FormLayout frmDeleteReason = new FormLayout();
		frmDeleteReason.setSizeUndefined();
		frmDeleteReason.setSpacing(true);
		frmDeleteReason.setMargin(true);
		cDeletePrompt.addComponent(frmDeleteReason);

		Label lbReason = new Label("Choose reason below:");
		CheckBox chkNolongerExists = new CheckBox("User no longer exists.");
		CheckBox chkDuplicate = new CheckBox("Duplicate user.");
		TextArea taOthers = new TextArea();

		frmDeleteReason.addComponent(lbReason);
		frmDeleteReason.addComponent(chkNolongerExists);
		frmDeleteReason.addComponent(chkDuplicate);

		Label lbOthers = new Label("Others(Specify)");
		frmDeleteReason.addComponent(lbOthers);
		frmDeleteReason.addComponent(taOthers);

		VerticalLayout cPopupBtns = new VerticalLayout();
		cPopupBtns.setSizeUndefined();
		cPopupBtns.setSpacing(true);

		Button btnCancel = new Button("Cancel");
		Button btnDelete = new Button("Delete");
		btnDelete.setIcon(r);
		cPopupBtns.addComponent(btnDelete);
		cPopupBtns.addComponent(btnCancel);

		frmDeleteReason.addComponent(cPopupBtns);
		cDeletePrompt.addComponent(frmDeleteReason);
		cDeletePrompt.setComponentAlignment(frmDeleteReason,
				Alignment.MIDDLE_CENTER);

		/*
		 * cDeletePrompt .setComponentAlignment(cPopupBtns,
		 * Alignment.BOTTOM_CENTER);
		 */

		popup.setContent(cDeletePrompt);

		UI.getCurrent().addWindow(popup);
		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -9071850366625898895L;

			@Override
			public void buttonClick(ClickEvent event) {
				popup.close();

			}
		});

		popup.addCloseListener(new CloseListener() {
			private static final long serialVersionUID = 3911244162638119820L;

			@Override
			public void windowClose(CloseEvent e) {
				isPopupShowing = false;
			}

		});

		btnDelete.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -6318666715385643538L;

			@Override
			public void buttonClick(ClickEvent event) {
				/*
				 * 
				 * TODO send user id to server for deletion and update the
				 * table.
				 */
				popup.close();

			}
		});

	}

	private void showActivateUserContainer(final ArrayList<String> arrLBulkIDs,
			final String[] arrID, Button btn) {

		String cap = btn.getCaption();

		if (cap != null && cap.equals("R")) {
			showResetPINContainer(arrLBulkIDs, arrID, btn);
			return;
		}

		isPopupShowing = true;
		isSent = false;

		String username = null;
		if (arrID != null) {
			username = arrID[3] + "'s Account";
		} else {
			username = "Selected Accounts";
		}
		/*
		 * StringBuilder builderDesc = null; if (arrLBulkIDs != null) for
		 * (String id : arrLBulkIDs) builderDesc.append(id);
		 */

		final Window popup = new Window("Activate " + username);
		popup.setStyleName("w_delete_user");
		popup.setIcon(FontAwesome.KEY);

		popup.center();

		VerticalLayout cDeletePrompt = new VerticalLayout();
		cDeletePrompt.setSpacing(true);
		// cDeletePrompt.setMargin(true);

		Label lbActivationPrompt = new Label(
				"Please enter and confirm PIN for " + username);
		lbActivationPrompt.setWidth("200px");

		VerticalLayout frmDeleteReason = new VerticalLayout();
		frmDeleteReason.setSizeUndefined();
		frmDeleteReason.setSpacing(true);
		frmDeleteReason.setMargin(true);
		cDeletePrompt.addComponent(frmDeleteReason);

		final PasswordField tFPIN = new PasswordField("Enter PIN");
		final PasswordField tFPINConf = new PasswordField("Confirm PIN");

		tFPIN.setRequired(true);
		tFPINConf.setRequired(true);
		tFPIN.setMaxLength(30);
		tFPINConf.setMaxLength(30);

		final TextField tFBID = new TextField("Bank Domain ID");
		final TextField tFCurrency = new TextField("Currency");

		final TextField tFSecAns = new TextField("Security Answer");
		final TextField tFIDD = new TextField("ID Number");
		final TextField tFUserRID = new TextField("User Resource ID");
		tFUserRID.setRequired(true);
		tFUserRID.setValue(arrID[3]);
		tFUserRID.setEnabled(false);

		frmDeleteReason.addComponent(lbActivationPrompt);
		frmDeleteReason.setComponentAlignment(lbActivationPrompt,
				Alignment.TOP_LEFT);
		frmDeleteReason.addComponent(tFBID);
		frmDeleteReason.addComponent(tFCurrency);
		frmDeleteReason.addComponent(tFSecAns);
		frmDeleteReason.addComponent(tFIDD);
		frmDeleteReason.addComponent(tFUserRID);

		frmDeleteReason.addComponent(tFPIN);
		frmDeleteReason.addComponent(tFPINConf);

		VerticalLayout cPopupBtns = new VerticalLayout();
		cPopupBtns.setSizeUndefined();
		cPopupBtns.setSpacing(true);

		Button btnCancel = new Button("Cancel");
		final Button btnActivate = new Button("Activate");
		btnActivate.setIcon(FontAwesome.KEY);
		cPopupBtns.addComponent(btnActivate);
		cPopupBtns.addComponent(btnCancel);
		frmDeleteReason.addComponent(cPopupBtns);
		cDeletePrompt.setComponentAlignment(frmDeleteReason,
				Alignment.MIDDLE_CENTER);
		/*
		 * cDeletePrompt .setComponentAlignment(cPopupBtns,
		 * Alignment.BOTTOM_CENTER);
		 */
		popup.setContent(cDeletePrompt);
		UI.getCurrent().addWindow(popup);
		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -9071850366625898895L;

			@Override
			public void buttonClick(ClickEvent event) {
				popup.close();

			}
		});

		btnActivate.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -6318666715385643538L;
			String strSxs = new String(
					"ACCOUNT_HOLDER_ACCOUNT_ACTIVATION_SUCCESSFUL");

			@Override
			public void buttonClick(ClickEvent event) {

				/*
				 * String bankdomainid, String currency, String IDnumber, String
				 * resourceid, String SecurityAns, String firstPin, String
				 * confirmPin
				 */

				if (isSent)
					return;
				if (ums == null)
					ums = new UserManagementService();

				String strResponse = null;
				// StringBuilder builderDesc = null;
				// Notification.show(String.valueOf(arrLBulkIDs.get(0)));

				String userresourceid = tFUserRID.getValue();
				String bankdomainid = tFBID.getValue();
				String IDnumber = tFIDD.getValue();
				String SecurityAns = tFSecAns.getValue();

				// TODO Please remember to update id.in the for loop.

				String currency = tFCurrency.getValue();
				if (arrLBulkIDs != null)
					for (String id : arrLBulkIDs) {

						try {
							strResponse = ums.activateUser(bankdomainid,
									currency, IDnumber, userresourceid,
									SecurityAns, tFPIN.getValue(),
									tFPINConf.getValue());

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							NotifCustom.show("Activation", e.getMessage());
						}
					}
				isSent = true;

				if (strResponse != null && strResponse.equals(strSxs)) {
					strResponse = "Activation successful!";
					// btnActivate.setVisible(false);
					// btnActivate.setEnabled(false);
					btnActivate.setIcon(null);
					btnActivate.setCaption("R");
					btnActivate.setStyleName("btn_link");
					btnActivate.setDescription("Reset PIN");

				}

				NotifCustom.show("Activation", strResponse);
				/*
				 * 
				 * TODO on positive response, update the table.
				 */

				popup.close();

			}
		});

		popup.addCloseListener(new CloseListener() {
			private static final long serialVersionUID = 3911244162638119820L;

			@Override
			public void windowClose(CloseEvent e) {
				isPopupShowing = false;
			}

		});
	}

	private void showSetParentUserContainer(String[] arrID) {

		isPopupShowing = true;
		isSent = false;
		String username = arrID[3];
		final Window popup = new Window("Set Parent Account ID of " + username
				+ "'s Account");
		popup.setStyleName("w_delete_user");
		// popup.setIcon(FontAwesome.KEY);

		popup.center();

		VerticalLayout cDeletePrompt = new VerticalLayout();
		cDeletePrompt.setSpacing(true);
		// cDeletePrompt.setMargin(true);

		Label lbActivationPrompt = new Label(
				"Please enter parent Account ID of user " + username);
		lbActivationPrompt.setWidth("200px");

		VerticalLayout frmDeleteReason = new VerticalLayout();
		frmDeleteReason.setSizeUndefined();
		frmDeleteReason.setSpacing(true);
		frmDeleteReason.setMargin(true);
		cDeletePrompt.addComponent(frmDeleteReason);

		final TextField tFU = new TextField("User Resource ID");
		tFU.setValue(arrID[3]);
		tFU.setEnabled(false);
		final TextField tFP = new TextField("Parent Account ID");
		tFP.setRequired(true);
		// final TextArea taReason = new TextArea();
		final TextField tFInitUser = new TextField("Initiating User Name");
		tFInitUser.setValue(UI.getCurrent().getSession().getAttribute("user")
				.toString());
		tFInitUser.setEnabled(false);

		// m/ taReason.setCaption("Reason");
		// m/ taReason.setInputPrompt("Please enter reason here.");
		// m/ tFU.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		// tFU.setEnabled(true);
		// tFU.setValue(arrID[2]);
		// m// tFU.setEnabled(false);
		// tFPIN.setMaxLength(4);
		// tFU.setMaxLength(4);

		frmDeleteReason.addComponent(lbActivationPrompt);
		frmDeleteReason.setComponentAlignment(lbActivationPrompt,
				Alignment.TOP_LEFT);
		frmDeleteReason.addComponent(tFU);
		frmDeleteReason.addComponent(tFP);
		frmDeleteReason.addComponent(tFInitUser);

		VerticalLayout cPopupBtns = new VerticalLayout();
		cPopupBtns.setSizeUndefined();
		cPopupBtns.setSpacing(true);

		Button btnCancel = new Button("Cancel");
		Button btnSet = new Button("Set");
		// btnActivate.setIcon(FontAwesome.Y);
		cPopupBtns.addComponent(btnSet);
		cPopupBtns.addComponent(btnCancel);
		frmDeleteReason.addComponent(cPopupBtns);
		cDeletePrompt.setComponentAlignment(frmDeleteReason,
				Alignment.MIDDLE_CENTER);
		/*
		 * cDeletePrompt .setComponentAlignment(cPopupBtns,
		 * Alignment.BOTTOM_CENTER);
		 */
		popup.setContent(cDeletePrompt);
		UI.getCurrent().addWindow(popup);
		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -9071850366625898895L;

			@Override
			public void buttonClick(ClickEvent event) {
				popup.close();

			}
		});

		btnSet.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -6318666715385643538L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (isSent)
					return;
				if (ums == null)
					ums = new UserManagementService();

				String strResponse = null;
				try {
					strResponse = ums.setParent(tFP.getValue(),
							tFInitUser.getValue(), tFU.getValue());
					isSent = true;
				} catch (RemoteException e) {

					e.printStackTrace();
				}

				// Notification.show(strResponse);
				NotifCustom.show("Set Parent", strResponse);
				/*
				 * 
				 * TODO on positive response, update the table.
				 */

				popup.close();

			}
		});

		popup.addCloseListener(new CloseListener() {
			private static final long serialVersionUID = 3911244162638119820L;

			@Override
			public void windowClose(CloseEvent e) {
				isPopupShowing = false;
			}

		});
	}

	private void showSetDefaultUserContainer(String[] arrID) {

		isPopupShowing = true;
		isSent = false;

		String username = null;
		String uid = null;
		if (arrID != null) {
			username = arrID[3] + "'s Account";
			uid = arrID[2];
		} else {
			username = "Selected Accounts";
		}

		final Window popup = new Window("Set Default Account for " + username
				+ "'s Account");
		popup.setStyleName("w_delete_user");
		// popup.setIcon(FontAwesome.KEY);

		popup.center();

		VerticalLayout cDeletePrompt = new VerticalLayout();
		cDeletePrompt.setSpacing(true);
		// cDeletePrompt.setMargin(true);

		Label lbActivationPrompt = new Label(
				"Please enter Default Account ID of " + username);
		lbActivationPrompt.setWidth("200px");

		VerticalLayout frmDeleteReason = new VerticalLayout();
		frmDeleteReason.setSizeUndefined();
		frmDeleteReason.setSpacing(true);
		frmDeleteReason.setMargin(true);
		cDeletePrompt.addComponent(frmDeleteReason);

		final TextField tFC = new TextField("Child Account ID");
		tFC.setValue(arrID[3]);
		tFC.setEnabled(false);
		final TextField tFD = new TextField("Default Account ID");
		tFD.setRequired(true);

		// m/final TextArea taReason = new TextArea();

		final TextField tFInitUser = new TextField("Initiating User");
		tFInitUser.setValue(UI.getCurrent().getSession().getAttribute("user")
				.toString());
		tFInitUser.setEnabled(false);

		// m/ taReason.setCaption("Reason");
		// taReason.setInputPrompt("Please enter reason here.");
		// m/tFP.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		// m/tFP.setEnabled(true);
		// m/tFP.setValue(arrID[2]);
		// m/tFP.setEnabled(false);
		// tFPIN.setMaxLength(4);
		// tFU.setMaxLength(4);

		frmDeleteReason.addComponent(lbActivationPrompt);
		frmDeleteReason.setComponentAlignment(lbActivationPrompt,
				Alignment.TOP_LEFT);
		frmDeleteReason.addComponent(tFC);
		frmDeleteReason.addComponent(tFD);
		frmDeleteReason.addComponent(tFInitUser);

		VerticalLayout cPopupBtns = new VerticalLayout();
		cPopupBtns.setSizeUndefined();
		cPopupBtns.setSpacing(true);

		Button btnCancel = new Button("Cancel");
		Button btnSet = new Button("Set");
		// btnActivate.setIcon(FontAwesome.Y);
		cPopupBtns.addComponent(btnSet);
		cPopupBtns.addComponent(btnCancel);
		frmDeleteReason.addComponent(cPopupBtns);
		cDeletePrompt.setComponentAlignment(frmDeleteReason,
				Alignment.MIDDLE_CENTER);
		/*
		 * cDeletePrompt .setComponentAlignment(cPopupBtns,
		 * Alignment.BOTTOM_CENTER);
		 */
		popup.setContent(cDeletePrompt);
		UI.getCurrent().addWindow(popup);
		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -9071850366625898895L;

			@Override
			public void buttonClick(ClickEvent event) {
				popup.close();

			}
		});

		btnSet.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -6318666715385643538L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (isSent)
					return;
				if (ums == null)
					ums = new UserManagementService();

				String strResponse = null;
				try {
					strResponse = ums.setDefaultAccount(tFInitUser.getValue(),
							tFC.getValue(), tFD.getValue());
					isSent = true;
				} catch (RemoteException e) {

					e.printStackTrace();
				}

				// Notification.show(strResponse);
				NotifCustom.show("Set Default", strResponse);
				/*
				 * 
				 * TODO on positive response, update the table.
				 */

				popup.close();

			}
		});

		popup.addCloseListener(new CloseListener() {
			private static final long serialVersionUID = 3911244162638119820L;

			@Override
			public void windowClose(CloseEvent e) {
				isPopupShowing = false;
			}

		});
	}

	private void showLinkUserContainer(String[] arrID) {
		// for (String s : arrID)
		// Notification.show(s);

		isPopupShowing = true;
		isSent = false;
		String username = arrID[3];
		String userID = arrID[2];
		/*
		 * TODO ensure that userProfID is retrieved from user data (Table).
		 * However, for now we shall assume all Linked users are sub Agents.
		 * However, they could be Super Agents or something.
		 */
		String userProfID = "7";
		final Window popup = new Window("Link " + username + "'s Account");
		popup.setStyleName("w_delete_user");
		popup.setIcon(FontAwesome.LINK);

		popup.center();

		VerticalLayout cDeletePrompt = new VerticalLayout();
		cDeletePrompt.setSpacing(true);
		// cDeletePrompt.setMargin(true);

		Label lbActivationPrompt = new Label(
				"Please enter Account ID of Parent to link " + username
						+ "'s Account");
		lbActivationPrompt.setWidth("200px");

		VerticalLayout frmDeleteReason = new VerticalLayout();
		frmDeleteReason.setSizeUndefined();
		frmDeleteReason.setSpacing(true);
		frmDeleteReason.setMargin(true);
		cDeletePrompt.addComponent(frmDeleteReason);

		final TextField tFU = new TextField("Account Resource ID");
		tFU.setValue(arrID[3]);
		tFU.setEnabled(false);

		final ComboBox comboUProf = new ComboBox("Select Profile");
		comboUProf.setNullSelectionAllowed(false);
		comboUProf.setRequired(true);
		comboUProf.addItem(8);
		comboUProf.setItemCaption(8, "DEPOSIT_ONLY");

		comboUProf.addItem(9);
		comboUProf.setItemCaption(9, "DEPOSIT_AND_WITHDRAWAL");

		comboUProf.select(8);

		// tFUProf.setEnabled(false);

		final TextField tFP = new TextField("Parent Account ID");
		tFP.setRequired(true);
		// m/final TextArea taReason = new TextArea();
		// m/taReason.setCaption("Reason");
		// m/taReason.setInputPrompt("Please enter reason here.");

		final TextField tFInitUser = new TextField("Initiating User");
		tFInitUser.setValue(UI.getCurrent().getSession().getAttribute("user")
				.toString());
		tFInitUser.setEnabled(false);

		// taReason.setCaption("Reason");
		// taReason.setInputPrompt("Please enter reason here.");

		// m/tFU.setValue(userID);
		// m/tFU.setEnabled(false);

		// m/tFUProf.setValue(userProfID);
		// m/tFUProf.setEnabled(false);

		// tFPIN.setMaxLength(4);
		// tFU.setMaxLength(4);

		frmDeleteReason.addComponent(lbActivationPrompt);
		frmDeleteReason.setComponentAlignment(lbActivationPrompt,
				Alignment.TOP_LEFT);
		frmDeleteReason.addComponent(tFU);
		frmDeleteReason.addComponent(comboUProf);
		frmDeleteReason.addComponent(tFP);
		frmDeleteReason.addComponent(tFInitUser);

		VerticalLayout cPopupBtns = new VerticalLayout();
		cPopupBtns.setSizeUndefined();
		cPopupBtns.setSpacing(true);

		Button btnCancel = new Button("Cancel");
		Button btnSet = new Button("Link");

		// btnActivate.setIcon(FontAwesome.Y);
		cPopupBtns.addComponent(btnSet);
		cPopupBtns.addComponent(btnCancel);
		frmDeleteReason.addComponent(cPopupBtns);
		cDeletePrompt.setComponentAlignment(frmDeleteReason,
				Alignment.MIDDLE_CENTER);
		/*
		 * cDeletePrompt .setComponentAlignment(cPopupBtns,
		 * Alignment.BOTTOM_CENTER);
		 */
		popup.setContent(cDeletePrompt);
		UI.getCurrent().addWindow(popup);
		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -9071850366625898895L;

			@Override
			public void buttonClick(ClickEvent event) {
				popup.close();

			}
		});

		btnSet.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -6318666715385643538L;

			@Override
			public void buttonClick(ClickEvent event) {

				/*
				 * String parentresourceid, String profileid, String reason,
				 * String userresourceid
				 */
				if (isSent)
					return;
				if (ums == null)
					ums = new UserManagementService();

				String strResponse = null;
				try {
					strResponse = ums.linkUser(tFP.getValue(), comboUProf
							.getValue().toString(), tFInitUser.getValue(), tFU
							.getValue());
					isSent = true;
				} catch (RemoteException e) {

					e.printStackTrace();
				}

				NotifCustom.show("Link", strResponse);
				/*
				 * 
				 * TODO on positive response, update the table.
				 */

				popup.close();

			}
		});

		popup.addCloseListener(new CloseListener() {
			private static final long serialVersionUID = 3911244162638119820L;

			@Override
			public void windowClose(CloseEvent e) {
				isPopupShowing = false;
			}

		});
	}

	private HorizontalLayout getControls(final PagedTableCustom tb) {
		HorizontalLayout pnUserSearchResults = tb.createControls();
		pnUserSearchResults.setSizeFull();
		pnUserSearchResults.setMargin(false);
		pnUserSearchResults.setSpacing(false);
		ComboBox combo = tb.getControlCombox();

		arrLCombos.add(combo);
		combo.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -905852552423055726L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				for (ComboBox c : arrLCombos)
					c.select(tb.getPageLength());

			}

		});

		tb.getNextPageBtn().addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				tb.setPageLength(tb.getVisibleItemIds().size());
				chkCount = 0;
				isSingleChange = true;
				for (CheckBox c : arrLChkBulk)
					c.setValue(false);
				isSingleChange = false;
			}
		});

		tb.getPrevPageBtn().addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				tb.setPageLength(rowCount);
				chkCount = 0;
				isSingleChange = true;
				for (CheckBox c : arrLChkBulk)
					c.setValue(false);
				isSingleChange = false;
			}
		});

		tb.getFirstPageBtn().addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				tb.setPageLength(rowCount);
				chkCount = 0;
				isSingleChange = true;
				for (CheckBox c : arrLChkBulk)
					c.setValue(false);
				isSingleChange = false;
			}
		});

		tb.getLastPageBtn().addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				tb.setPageLength(tb.getVisibleItemIds().size());
				chkCount = 0;
				isSingleChange = true;
				for (CheckBox c : arrLChkBulk)
					c.setValue(false);
				isSingleChange = false;
			}
		});

		return pnUserSearchResults;
	}

	private VerticalLayout getBulkActionsC() {
		VerticalLayout actionBulkC = new VerticalLayout();
		actionBulkC.setWidth("100%");
		actionBulkC.setStyleName("c_action_bulk");

		final CheckBox chkAll = new CheckBox();
		arrLChkBulk.add(chkAll);
		chkAll.setCaption("Select All");

		final ComboBox comboBulk = new ComboBox("Bulk Action");

		comboBulk.addItem("Activate");
		comboBulk.addItem("Delete");

		Button btnBulkOk = new Button("Apply");
		btnBulkOk.setStyleName("btn_link");
		btnBulkOk.setEnabled(false);
		HorizontalLayout cBulk = new HorizontalLayout();
		cBulk.setSizeUndefined();
		cBulk.addComponent(comboBulk);
		cBulk.addComponent(btnBulkOk);
		cBulk.setComponentAlignment(btnBulkOk, Alignment.BOTTOM_RIGHT);

		actionBulkC.addComponent(chkAll);
		actionBulkC.addComponent(cBulk);

		btnBulkOk.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 7212247824070811111L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (comboBulk.getValue() == null) {
					Notification.show("Please select Bulk Action");
					return;
				}
				arrLBulkIDs = new ArrayList<>();
				Collection<?> arrLCurItemIDs = tb.getVisibleItemIds();
				for (Object ids : arrLCurItemIDs) {
					Item row = tb.getItem(ids);
					Property<?> col = row.getItemProperty(" ");
					CheckBox curChk = (CheckBox) col.getValue();
					boolean isChecked = curChk.getValue();
					if (isChecked) {
						arrLBulkIDs.add(curChk.getId());
					}
				}

				if (arrLBulkIDs.size() <= 1) {
					Notification.show("Please select Multiple users.");
					return;
				}

				String strAction = (String) comboBulk.getValue();
				if (strAction.equals("Activate")) {
					showActivateUserContainer(arrLBulkIDs, null, null);
					return;
				} else if (strAction.equals("Delete")) {

					showDeleteUserContainer(arrLBulkIDs, null);
					return;
				}/*
				 * else if (strAction.equals("Link")) {
				 * showActivateUserContainer(arrLBulkIDs, null); return; } else
				 * if (strAction.equals("Set Parent")) {
				 * showActivateUserContainer(arrLBulkIDs, null); return; } else
				 * if (strAction.equals("Set Default")) {
				 * showActivateUserContainer(arrLBulkIDs, null); return; }
				 */

			}
		});

		chkAll.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -461485923323812803L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				boolean isChecked = chkAll.getValue();

				if (!isSingleChange) {
					Collection<?> arrLCurItemIDs = tb.getVisibleItemIds();
					for (Object ids : arrLCurItemIDs) {
						Item row = tb.getItem(ids);
						Property<CheckBox> col = (row.getItemProperty(" "));
						CheckBox curChk = col.getValue();
						curChk.setValue(isChecked);
					}
				}
			}

		});

		return actionBulkC;
	}

	@SuppressWarnings("unchecked")
	private void addRow(IndexedContainer container, String sn, String strUID,
			String strUname, String strFname, String strLname, String strProf,
			String status, String email, String msisdn) {
		rowCount++;
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		actionsC.setStyleName("c_actions");

		Item trItem;
		container.addItem(strUID);
		trItem = container.getItem(strUID);

		Property<CheckBox> tdPropertyCheck = trItem.getItemProperty(" ");
		Property<String> tdPropertySN = trItem.getItemProperty("S/N");
		// Property<String> tdPropertyUID = trItem.getItemProperty("UID");
		Property<String> tdPropertyEmail = trItem.getItemProperty("Email");
		Property<String> tdPropertyMSISDN = trItem.getItemProperty("MSISDN");
		Property<String> tdPropertyUname = trItem.getItemProperty("Username");
		Property<String> tdPropertyFname = trItem.getItemProperty("First Name");
		Property<String> tdPropertyLname = trItem.getItemProperty("Last Name");
		Property<String> tdPropertyACCType = trItem
				.getItemProperty("Profile Type");
		Property<HorizontalLayout> tdPropertyActions = trItem
				.getItemProperty("Actions");
		final CheckBox chk = new CheckBox();
		chk.setId(strUID);

		tdPropertyCheck.setValue(chk);
		tdPropertySN.setValue(sn);
		// tdPropertyUID.setValue(strUID);
		tdPropertyUname.setValue(strUname);
		tdPropertyFname.setValue(strFname);
		tdPropertyLname.setValue(strLname);
		tdPropertyACCType.setValue(strProf);
		tdPropertyEmail.setValue(email);
		tdPropertyMSISDN.setValue(msisdn);
		tdPropertyActions.setValue(actionsC);

		btnDetails = new BtnActions("Details");
		btnDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);

		btnEdit = new BtnActions("Edit");
		btnEdit.setIcon(FontAwesome.EDIT);

		btnLink = new BtnActions("Link");
		btnLink.setIcon(FontAwesome.LINK);

		btnDelete = new BtnActions("Delete");
		btnDelete.setIcon(icDelete);

		btnActivate = new BtnActions("Activate");
		btnActivate.setIcon(FontAwesome.KEY);

		btnMoreActions = new BtnActions("More...");
		btnMoreActions.setIcon(FontAwesome.ELLIPSIS_H);

		btnSetParent = new Button("P");

		btnSetDefaultAcc = new Button("D");

		// StringBuilder sb = new StringBuilder();
		// sb.append("users_personal_");

		btnDetails.setId("users_personal_" + hm.get(strProf) + "_" + strUname
				+ "_details");
		btnEdit.setId("user_personal_" + hm.get(strProf) + "_" + strUname
				+ "_edit");
		btnLink.setId("user_account_" + hm.get(strProf) + "_" + strUname
				+ "_link");
		btnActivate.setId("user_account_" + hm.get(strProf) + "_" + strUname
				+ "_activate");
		btnDelete.setId("user_account_" + hm.get(strProf) + "_" + strUname
				+ "_delete");
		btnMoreActions.setId("user_account_" + hm.get(strProf) + "_" + strUname
				+ "_moreActions");

		btnSetParent.setId("user_account_" + hm.get(strProf) + "_" + strUname
				+ "_parent");
		btnSetDefaultAcc.setId("user_account_" + hm.get(strProf) + "_"
				+ strUname + "_default");

		btnSetDefaultAcc.setDescription("Set Default Account");
		btnSetParent.setDescription("Set Parent Account");
		btnSetDefaultAcc.setStyleName("btn_link");
		btnSetParent.setStyleName("btn_link");

		actionsC.addComponent(btnActivate);
		// actionsC.addComponent(btnSetParent);
		actionsC.addComponent(btnSetDefaultAcc);
		actionsC.addComponent(btnLink);
		actionsC.addComponent(btnDetails);
		actionsC.addComponent(btnEdit);
		actionsC.addComponent(btnDelete);

		// Notification.show(ACCOUNTSTATUS.ACTIVE + " : This.");
		// Notification.show(status);

		if (status.equals("ACTIVE")) {
			// btnActivate.setVisible(false);
			// btnActivate.setEnabled(false);
			// btnActivate.setStyleName("btn_link btn_a");
			btnActivate.setIcon(null);
			btnActivate.setCaption("R");
			btnActivate.setStyleName("btn_link");
			btnActivate.setDescription("Reset PIN");

			// btnActivate.setIcon(null);
			// btnActivate.removeStyleName(ValoTheme.BUTTON_ICON_ONLY);

			// btnActivate.setStyleName("btn_link");
			// btnActivate.setCaption("R");

		}
		// actionsC.addComponent(btnMoreActions);

		btnDetails.addClickListener(new BtnActionsClickListener(false, null));
		btnEdit.addClickListener(new BtnActionsClickListener(false, null));

		btnDelete.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -3840990560267229588L;

			@Override
			public void buttonClick(ClickEvent event) {
				// Notification.show(String.valueOf(isPopupShowing));
				if (isPopupShowing)
					return;
				String[] arrID = event.getButton().getId().split("_");
				arrLBulkIDs = new ArrayList<>();
				arrLBulkIDs.add(arrID[2]);
				showDeleteUserContainer(arrLBulkIDs, arrID);

			}
		});

		btnActivate.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -3840990560264229588L;

			@Override
			public void buttonClick(ClickEvent event) {
				// Notification.show(String.valueOf(isPopupShowing));
				if (isPopupShowing)
					return;
				String[] arrID = event.getButton().getId().split("_");
				arrLBulkIDs = new ArrayList<>();
				arrLBulkIDs.add(arrID[2]);
				showActivateUserContainer(arrLBulkIDs, arrID, event.getButton());

			}
		});

		btnSetParent.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -3840990560264229588L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (isPopupShowing)
					return;
				String[] arrID = event.getButton().getId().split("_");
				showSetParentUserContainer(arrID);

			}
		});

		btnSetDefaultAcc.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -3840990560264229588L;

			@Override
			public void buttonClick(ClickEvent event) {
				// Notification.show(String.valueOf(isPopupShowing));
				if (isPopupShowing)
					return;
				String[] arrID = event.getButton().getId().split("_");
				showSetDefaultUserContainer(arrID);

			}
		});

		btnLink.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -3840990560264229588L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (isPopupShowing)
					return;
				String[] arrID = event.getButton().getId().split("_");

				showLinkUserContainer(arrID);

			}
		});

		chk.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 2437293114933803756L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				boolean isChecked = chk.getValue();
				if (isChecked) {
					chkCount++;
				} else {
					chkCount--;
				}

				if (chkCount == tb.getVisibleItemIds().size()) {
					isSingleChange = true;
					for (CheckBox c : arrLChkBulk)
						c.setValue(isChecked);
					isSingleChange = false;

				} else if (chkCount == (tb.getVisibleItemIds().size() - 1)) {
					isSingleChange = true;
					for (CheckBox c : arrLChkBulk)
						c.setValue(false);
					isSingleChange = false;

				}

			}

		});

	}

	private IndexedContainer getTable() {
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty(" ", CheckBox.class, null);
		container.addContainerProperty("S/N", String.class, "000");
		// container.addContainerProperty("UID", String.class, "000");
		container.addContainerProperty("Username", String.class, "");

		container.addContainerProperty("First Name", String.class, "");
		container.addContainerProperty("Last Name", String.class, "");

		container.addContainerProperty("Profile Type", String.class, "");

		container.addContainerProperty("Email", String.class, "");

		container.addContainerProperty("MSISDN", String.class, "");

		container.addContainerProperty("Actions", HorizontalLayout.class, null);
		arrLChkBulk = new ArrayList<>();
		arrLCombos = new ArrayList<>();

		/*
		 * IndexedContainer container, String strUID, String strUname, String
		 * strFname, String strLname, String strProf
		 */

		String Uname = "psatestuser";
		String Pword = "psatest_2015";
		String drivers = "com.mysql.jdbc.Driver";
		try {
			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			Connection conn = DriverManager
					.getConnection(
							"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasourcetest",
							Uname, Pword);

			String qx = "SELECT acth.username as un, acth.msisdn as msisdn, acth.email as email,pf.profilename as prof, acths.accountholderstatusname as status,acthd.firstname as fn ,acthd.lastname as ln,id.identificationnumber as id,ad.streetaddress as street from accountholders acth, accountholderdetails acthd, accountholderstatus acths, identificationattribute id, address ad, profiles pf where acth.accountholderdetailid = acthd.accountdetailsid and acth.accountholderstatusid = acths.accountholderstatusid and acthd.identificationid = id.identificationattrid and acthd.addressid = ad.addressid and pf.profileid = acth.profileid and pf.profiletypeid = 1;";

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(qx);
			int x = 0;

			while (rs.next()) {
				x++;
				String id = rs.getString("id");
				String sn = String.valueOf(x);
				String un = rs.getString("un");
				String prof = rs.getString("prof");
				String msisdn = rs.getString("msisdn");
				String email = rs.getString("email");
				String fn = rs.getString("fn");

				String ln = rs.getString("ln");

				String status = rs.getString("status");
				addRow(container, sn, id, un, fn, ln, prof, status, email,
						msisdn);

			}

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show("DB Connection",
					"Error Establishing DBConnection:  " + e.getMessage(),
					Notification.Type.ERROR_MESSAGE);
		}

		return container;
	}

	private void addFilters(HashMap<String, String> hmFilter,
			IndexedContainer container) {
		container.removeAllContainerFilters();

		Filter f = null;

		String prof = hmFilter.remove("Profile Type");

		if (!prof.equals("ALL")) {
			f = new Compare.Equal("Profile Type", prof);
			container.addContainerFilter(f);
		}

		Entry<String, String> e = null;
		Iterator<Entry<String, String>> itr = hmFilter.entrySet().iterator();

		while (itr.hasNext()) {
			e = itr.next();
			f = new Compare.Equal(e.getKey(), e.getValue());
			// NotifCustom.show("Field: ", e.getKey());
			// NotifCustom.show("Value: ", e.getValue());
			container.addContainerFilter(f);

		}

	}

	private void showResetPINContainer(final ArrayList<String> arrLBulkIDs,
			final String[] arrID, Button btn) {

		isPopupShowing = true;
		isSent = false;
		isValidatorAdded = false;

		String username = null;
		if (arrID != null) {
			username = arrID[3] + "'s Account";
		} else {
			username = "Selected Accounts";
		}
		/*
		 * StringBuilder builderDesc = null; if (arrLBulkIDs != null) for
		 * (String id : arrLBulkIDs) builderDesc.append(id);
		 */

		final Window popup = new Window("Reset PIN for " + username);

		popup.setStyleName("w_delete_user");
		popup.setIcon(FontAwesome.KEY);

		popup.center();

		VerticalLayout cDeletePrompt = new VerticalLayout();
		cDeletePrompt.setSpacing(true);
		// cDeletePrompt.setMargin(true);

		Label lbActivationPrompt = new Label(
				"Please enter and confirm PIN for " + username);
		lbActivationPrompt.setWidth("200px");

		VerticalLayout frmDeleteReason = new VerticalLayout();
		frmDeleteReason.setSizeUndefined();
		frmDeleteReason.setSpacing(true);
		frmDeleteReason.setMargin(true);
		cDeletePrompt.addComponent(frmDeleteReason);

		final PasswordField tFPIN = new PasswordField("Enter PIN");
		final PasswordField tFPINConf = new PasswordField("Confirm PIN");

		tFPIN.setRequired(true);
		tFPINConf.setRequired(true);
		tFPIN.setMaxLength(30);
		tFPINConf.setMaxLength(30);

		tFPIN.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -2549609367159498845L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				tFPIN.setComponentError(null);
				tFPINConf.setComponentError(null);

			}

		});

		tFPINConf.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -2549609367159498845L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				tFPIN.setComponentError(null);
				tFPINConf.setComponentError(null);

			}

		});

		final TextField tFInitUser = new TextField("Initiating User");
		tFInitUser.setValue(UI.getCurrent().getSession().getAttribute("user")
				.toString());
		tFInitUser.setReadOnly(true);
		tFInitUser.setEnabled(false);
		tFInitUser.setRequired(true);

		final TextField tFUserRID = new TextField("User Resource ID");
		tFUserRID.setRequired(true);
		tFUserRID.setValue(arrID[3]);
		tFUserRID.setEnabled(false);

		frmDeleteReason.addComponent(lbActivationPrompt);
		frmDeleteReason.setComponentAlignment(lbActivationPrompt,
				Alignment.TOP_LEFT);
		// frmDeleteReason.addComponent(tFBID);
		// frmDeleteReason.addComponent(tFCurrency);
		// frmDeleteReason.addComponent(tFSecAns);

		frmDeleteReason.addComponent(tFUserRID);

		frmDeleteReason.addComponent(tFPIN);
		frmDeleteReason.addComponent(tFPINConf);

		frmDeleteReason.addComponent(tFInitUser);

		VerticalLayout cPopupBtns = new VerticalLayout();
		cPopupBtns.setSizeUndefined();
		cPopupBtns.setSpacing(true);

		Button btnCancel = new Button("Cancel");
		final Button btnActivate = new Button("Reset PIN");
		btnActivate.setIcon(FontAwesome.KEY);
		cPopupBtns.addComponent(btnActivate);
		cPopupBtns.addComponent(btnCancel);
		frmDeleteReason.addComponent(cPopupBtns);
		cDeletePrompt.setComponentAlignment(frmDeleteReason,
				Alignment.MIDDLE_CENTER);
		/*
		 * cDeletePrompt .setComponentAlignment(cPopupBtns,
		 * Alignment.BOTTOM_CENTER);
		 */
		popup.setContent(cDeletePrompt);
		UI.getCurrent().addWindow(popup);
		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -9071850366625898895L;

			@Override
			public void buttonClick(ClickEvent event) {
				popup.close();

			}
		});

		btnActivate.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -6318666715385643538L;
			String strSxs = new String(
					"ACCOUNT_HOLDER_ACCOUNT_ACTIVATION_SUCCESSFUL");

			@Override
			public void buttonClick(ClickEvent event) {

				/*
				 * String bankdomainid, String currency, String IDnumber, String
				 * resourceid, String SecurityAns, String firstPin, String
				 * confirmPin
				 */

				if (isSent)
					return;
				// if (ums == null)
				// ums = new UserManagementService();

				String strResponse = null;
				// StringBuilder builderDesc = null;
				// Notification.show(String.valueOf(arrLBulkIDs.get(0)));

				if (!isValidatorAdded) {
					tFPIN.setRequired(false);
					tFPINConf.setRequired(false);
					tFPIN.addValidator(new PINMatch(tFPIN, tFPINConf));
					tFPINConf.addValidator(new PINMatch(tFPIN, tFPINConf));
					isValidatorAdded = true;
				}
				try {
					tFPIN.validate();
				} catch (InvalidValueException e) {
					tFPINConf.setComponentError(null);
					Notification.show("PIN Error!",
							Notification.Type.ERROR_MESSAGE);
					return;
				}

				try {
					tFPINConf.validate();
				} catch (InvalidValueException e) {
					tFPIN.setComponentError(null);
					Notification.show("PIN Error!",
							Notification.Type.ERROR_MESSAGE);
					return;
				}

				String userresourceid = tFUserRID.getValue();
				String inituser = tFInitUser.getValue();
				String newPIN = tFPIN.getValue();
				// String SecurityAns = tFSecAns.getValue();

				// TODO Please remember to update id.in the for loop.

				// String currency = tFCurrency.getValue();
				if (arrLBulkIDs != null)
					for (String id : arrLBulkIDs) {

						try {
							strResponse = UserManagementService
									.passwordResetByAdmin(inituser,
											userresourceid, newPIN);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							NotifCustom.show("Activation", e.getMessage());
						}
					}
				isSent = true;

				if (strResponse != null && strResponse.equals(strSxs)) {
					strResponse = "Activation successful!";
					// btnActivate.setVisible(false);
					// btnActivate.setEnabled(false);
					btnActivate.setIcon(null);
					btnActivate.setCaption("R");
					btnActivate.setStyleName("btn_link");
					btnActivate.setDescription("Reset PIN");

				}

				if (strResponse == null || strResponse.trim().isEmpty()) {
					strResponse = "Invalid PIN! (Only 4-30 No. of characters).";
					Notification.show(strResponse,
							Notification.Type.ERROR_MESSAGE);
					isSent = false;
					return;
				}

				NotifCustom.show("Activation", strResponse);
				/*
				 * 
				 * TODO on positive response, update the table.
				 */

				popup.close();

			}
		});

		popup.addCloseListener(new CloseListener() {
			private static final long serialVersionUID = 3911244162638119820L;

			@Override
			public void windowClose(CloseEvent e) {
				isPopupShowing = false;
			}

		});

	}

	private class PINMatch implements Validator {

		PasswordField tFPIN;
		PasswordField tFPINConf;

		PINMatch(PasswordField tF1, PasswordField tF2) {
			tFPIN = tF1;
			tFPINConf = tF2;
		}

		private static final long serialVersionUID = 9154387852025910994L;

		@Override
		public void validate(Object value) throws InvalidValueException {

			if (tFPIN.getValue().isEmpty()) {
				tFPIN.focus();
				throw new InvalidValueException("Please enter PIN.");

			}

			if (tFPINConf.getValue().isEmpty()) {
				tFPINConf.focus();
				throw new InvalidValueException("Please comfirm PIN.");

			}

			if (!tFPIN.getValue().trim().equals(tFPINConf.getValue().trim())) {
				tFPINConf.focus();
				throw new InvalidValueException("PIN mismatch!");

			}
		}

	}

}
