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
import java.util.List;

import com.swifta.mats.web.utils.UserManagementService;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
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
	PagedTableCustom tb;

	ArrayList<String> arrLBulkIDs;

	ThemeResource icDelete;

	BE2() {
		icDelete = new ThemeResource("img/ic_delete_small.png");
		isPopupShowing = false;
	}

	public VerticalLayout queryBackEnd(String strSearchParams) {
		IndexedContainer container = getTable();
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

		Label lbSearch = new Label("Match results for: "
				+ strSearchResultsParams);
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

		if (!(rowCount < 10)) {
			rowCount = 10;
		}

		tb = new PagedTableCustom("Results (Summary)");
		tb.setPageLength(rowCount);
		tb.setContainerDataSource(container);
		tb.setColumnIcon(" ", FontAwesome.CHECK_SQUARE_O);
		tb.setStyleName("tb_u_search_results");

		// HorizontalLayout upperControls = getControls(tb);
		// HorizontalLayout lowerControls = upperControls;
		searchResultsContainer.addComponent(getBulkActionsC());
		searchResultsContainer.addComponent(getControls(tb));
		searchResultsContainer.addComponent(tb);
		searchResultsContainer.addComponent(getControls(tb));
		searchResultsContainer.addComponent(getBulkActionsC());

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
			final String[] arrID) {

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
		tFPIN.setMaxLength(4);
		tFPINConf.setMaxLength(4);

		final TextField tFBID = new TextField("Bank Domain ID");
		final TextField tFCurrency = new TextField("Currency");

		final TextField tFSecAns = new TextField("Security Answer");
		final TextField tFIDD = new TextField("ID Number");
		final TextField tFUserRID = new TextField("User Resource ID");

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
		Button btnActivate = new Button("Activate");
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
				Notification.show(String.valueOf(arrLBulkIDs.get(0)));

				String userresourceid = tFUserRID.getValue();
				String bankdomainid = tFBID.getValue();
				String IDnumber = tFIDD.getValue();
				String SecurityAns = tFSecAns.getValue();

				// TODO Please remember to update id.in the for loop.

				String currency = tFCurrency.getValue();
				String ret = "No response";
				if (arrLBulkIDs != null)
					for (String id : arrLBulkIDs) {

						try {
							strResponse = ums.activateUser(bankdomainid,
									currency, IDnumber, userresourceid,
									SecurityAns, tFPIN.getValue(),
									tFPINConf.getValue());
							Notification.show("");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Notification.show("Response: " + e.getMessage());
						}
					}
				isSent = true;

				if (strResponse == null || strResponse.trim() == null)
					strResponse = "Activation successful!";

				Notification.show("Response: " + strResponse);
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

		final TextField tFU = new TextField("User Account ID");
		final TextField tFP = new TextField("Parent Account ID");
		// final TextArea taReason = new TextArea();
		final TextField taReason = new TextField("Initiating User Name");
		// m/ taReason.setCaption("Reason");
		// m/ taReason.setInputPrompt("Please enter reason here.");
		// m/ tFU.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		tFU.setEnabled(true);
		tFU.setValue(arrID[2]);
		// m// tFU.setEnabled(false);
		// tFPIN.setMaxLength(4);
		// tFU.setMaxLength(4);

		frmDeleteReason.addComponent(lbActivationPrompt);
		frmDeleteReason.setComponentAlignment(lbActivationPrompt,
				Alignment.TOP_LEFT);
		frmDeleteReason.addComponent(tFU);
		frmDeleteReason.addComponent(tFP);
		frmDeleteReason.addComponent(taReason);

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
							taReason.getValue(), tFU.getValue());
					isSent = true;
				} catch (RemoteException e) {

					e.printStackTrace();
				}

				Notification.show(strResponse);
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

		final TextField tFP = new TextField("Parent Account ID");
		final TextField tFD = new TextField("Default Account ID");
		// m/final TextArea taReason = new TextArea();

		final TextField taReason = new TextField("Initiating User");
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
		frmDeleteReason.addComponent(tFP);
		frmDeleteReason.addComponent(tFD);
		frmDeleteReason.addComponent(taReason);

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
					strResponse = ums.setDefaultAccount(tFP.getValue(),
							taReason.getValue(), tFD.getValue());
					isSent = true;
				} catch (RemoteException e) {

					e.printStackTrace();
				}

				Notification.show(strResponse);
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

		final TextField tFU = new TextField("User Account ID");
		final TextField tFUProf = new TextField("User Profile ID");
		final TextField tFP = new TextField("Parent Account ID");
		// m/final TextArea taReason = new TextArea();
		// m/taReason.setCaption("Reason");
		// m/taReason.setInputPrompt("Please enter reason here.");

		final TextField taReason = new TextField("Initiating User");
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
		frmDeleteReason.addComponent(tFUProf);
		frmDeleteReason.addComponent(tFP);
		frmDeleteReason.addComponent(taReason);

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
					strResponse = ums.linkUser(tFP.getValue(),
							tFUProf.getValue(), taReason.getValue(),
							tFU.getValue());
					isSent = true;
				} catch (RemoteException e) {

					e.printStackTrace();
				}

				Notification.show(strResponse);
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
					Property<CheckBox> col = row.getItemProperty(" ");
					CheckBox curChk = col.getValue();
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
					showActivateUserContainer(arrLBulkIDs, null);
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
	private void addRow(IndexedContainer container, String strUID,
			String strUname, String strFname, String strLname, String strProf) {
		rowCount++;
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		actionsC.setStyleName("c_actions");

		Item trItem;
		container.addItem(strUID);
		trItem = container.getItem(strUID);

		Property<CheckBox> tdPropertyCheck = trItem.getItemProperty(" ");
		Property<String> tdPropertyUID = trItem.getItemProperty("S/N");
		Property<String> tdPropertyUname = trItem.getItemProperty("Username");
		Property<String> tdPropertyFname = trItem.getItemProperty("First Name");
		Property<String> tdPropertyLname = trItem.getItemProperty("Last Name");
		Property<String> tdPropertyACCType = trItem
				.getItemProperty("Account Type");
		Property<HorizontalLayout> tdPropertyActions = trItem
				.getItemProperty("Actions");
		final CheckBox chk = new CheckBox();
		chk.setId(strUID);

		tdPropertyCheck.setValue(chk);
		tdPropertyUID.setValue(strUID);
		tdPropertyUname.setValue(strUname);
		tdPropertyFname.setValue(strFname);
		tdPropertyLname.setValue(strLname);
		tdPropertyACCType.setValue(strProf);
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

		btnDetails.setId("users_personal_" + strUID + "_" + strUname
				+ "_details");
		btnEdit.setId("user_personal_" + strUID + "_" + strUname + "_edit");
		btnLink.setId("user_account_" + strUID + "_" + strUname + "_link");
		btnActivate.setId("user_account_" + strUID + "_" + strUname
				+ "_activate");
		btnDelete.setId("user_account_" + strUID + "_" + strUname + "_delete");
		btnMoreActions.setId("user_account_" + strUID + "_" + strUname
				+ "_moreActions");

		btnSetParent.setId("user_account_" + strUID + "_" + strUname
				+ "_parent");
		btnSetDefaultAcc.setId("user_account_" + strUID + "_" + strUname
				+ "_default");

		btnSetDefaultAcc.setDescription("Set Default Account");
		btnSetParent.setDescription("Set Parent Account");
		btnSetDefaultAcc.setStyleName("btn_link");
		btnSetParent.setStyleName("btn_link");

		actionsC.addComponent(btnActivate);
		actionsC.addComponent(btnSetParent);
		actionsC.addComponent(btnSetDefaultAcc);
		actionsC.addComponent(btnLink);
		actionsC.addComponent(btnDetails);
		actionsC.addComponent(btnEdit);
		actionsC.addComponent(btnDelete);
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
				showActivateUserContainer(arrLBulkIDs, arrID);

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
		container.addContainerProperty("Username", String.class, "");
		container.addContainerProperty("First Name", String.class, "");
		container.addContainerProperty("Last Name", String.class, "");
		container.addContainerProperty("Account Type", String.class, "");
		container.addContainerProperty("Actions", HorizontalLayout.class, null);
		arrLChkBulk = new ArrayList<>();
		arrLCombos = new ArrayList<>();

		/*
		 * IndexedContainer container, String strUID, String strUname, String
		 * strFname, String strLname, String strProf
		 */
		rowCount = 0;

		String qx = "SELECT acth.username as un, acth.msisdn as msisdn, acth.email as email,pf.profilename as prof, acths.accountholderstatusname as status,acthd.firstname as fn ,acthd.lastname as ln,id.identificationnumber as id,ad.streetaddress as street from accountholders acth, accountholderdetails acthd, accountholderstatus acths, identificationattribute id, address ad, profiles pf where acth.accountholderdetailid = acthd.accountdetailsid and acth.accountholderstatusid = acths.accountholderstatusid and acthd.identificationid = id.identificationattrid and acthd.addressid = ad.addressid and pf.profileid = acth.profileid and pf.profiletypeid = 1;";
		String Uname = "gomint";
		String Pword = "gomint";
		String drivers = "com.mysql.jdbc.Driver";
		try {
			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			Connection conn = DriverManager
					.getConnection(
							"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasourcetest",
							Uname, Pword);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(qx);
			int x = 0;

			while (rs.next()) {
				x++;
				// String id = rs.getString("id");
				String idp = String.valueOf(x);
				String un = rs.getString("un");
				String fn = rs.getString("fn");
				String ln = rs.getString("fn");
				String prof = rs.getString("prof");
				addRow(container, idp, un, fn, ln, prof);
				// String status = rs.getString("status");

			}

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show("Error Establishing DBConnection:  " + e);
		}

		return container;
	}
}
