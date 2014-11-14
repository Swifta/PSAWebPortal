package com.swifta.mats.web.usermanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.swifta.mats.web.utils.UserManagementService;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
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
import com.vaadin.ui.themes.ValoTheme;

public class BE2 {
	private BtnActions btnDetails;
	private BtnActions btnEdit;
	private BtnActions btnLink;
	private BtnActions btnDelete;
	private BtnActions btnActivate;
	private BtnActions btnMoreActions;
	private Button btnSetParent;
	private Button btnSetDefaultAcc;

	private List<Object> arrLPopupParentClasses;
	HorizontalLayout actionsC;
	private boolean isPopupShowing;
	private boolean isSent = false;
	UserManagementService ums;

	BE2() {
		ThemeResource icDelete = new ThemeResource("img/ic_delete_small.png");
		isPopupShowing = false;
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
		/*
		 * btnMoreActions .addClickListener(new BtnActionsClickListener(false,
		 * null));
		 */

		btnMoreActions.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -3840990560267229588L;

			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show(String.valueOf(event.getClientX()));
				// .setStyleName("btn_hide");
				// actionsC.addComponent(btnSetParent);
			}
		});
		arrLPopupParentClasses = new ArrayList<Object>();
		arrLPopupParentClasses.add(this);

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
				showDeleteUserContainer(arrID);

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
				showActivateUserContainer(arrID);

			}
		});

		btnSetParent.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -3840990560264229588L;

			@Override
			public void buttonClick(ClickEvent event) {
				// Notification.show(String.valueOf(isPopupShowing));
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
				// Notification.show(String.valueOf(isPopupShowing));
				if (isPopupShowing)
					return;
				String[] arrID = event.getButton().getId().split("_");
				showLinkUserContainer(arrID);

			}
		});

		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		actionsC.setStyleName("c_actions");
	}

	@SuppressWarnings("unchecked")
	public IndexedContainer queryBackEnd(String strSearchParams) {
		IndexedContainer container = new IndexedContainer();

		container.addContainerProperty(" ", CheckBox.class, null);
		container.addContainerProperty("UID", String.class, "000");
		container.addContainerProperty("Username", String.class, "");
		container.addContainerProperty("First Name", String.class, "");
		container.addContainerProperty("Last Name", String.class, "");
		container.addContainerProperty("Account Type", String.class, "");
		container.addContainerProperty("Actions", HorizontalLayout.class, null);

		// tb.setColumnIcon(" ", FontAwesome.CHECK_SQUARE_O);

		Item trItem;
		container.addItem("row1");
		trItem = container.getItem("row1");
		Property<CheckBox> tdPropertyCheck = trItem.getItemProperty(" ");
		Property<String> tdPropertyUID = trItem.getItemProperty("UID");
		Property<String> tdPropertyUname = trItem.getItemProperty("Username");
		Property<String> tdPropertyFname = trItem.getItemProperty("First Name");
		Property<String> tdPropertyLname = trItem.getItemProperty("Last Name");
		Property<String> tdPropertyACCType = trItem
				.getItemProperty("Account Type");
		Property<HorizontalLayout> tdPropertyActions = trItem
				.getItemProperty("Actions");

		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("000012211");
		tdPropertyUname.setValue("Sevo");
		tdPropertyFname.setValue("Yoweri");
		tdPropertyLname.setValue("Amama");
		tdPropertyACCType.setValue("Administrator");
		tdPropertyActions.setValue(actionsC);
		String strUID = tdPropertyUID.getValue();
		String strUname = tdPropertyUname.getValue();

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

		VerticalLayout cMore = new VerticalLayout();
		cMore.addComponent(new Button("Set Parent"));
		cMore.addComponent(new Button("set Default Acc."));
		cMore.setStyleName("c_more");

		actionsC.addComponent(cMore);

		btnSetDefaultAcc.setDescription("Set Default Account");
		btnSetParent.setDescription("Set Parent Account");
		btnSetDefaultAcc.setStyleName("btn_link");
		btnSetParent.setStyleName("btn_link btn_hide");

		actionsC.addComponent(btnActivate);
		actionsC.addComponent(btnSetParent);
		actionsC.addComponent(btnSetDefaultAcc);
		actionsC.addComponent(btnLink);
		actionsC.addComponent(btnDetails);
		actionsC.addComponent(btnEdit);
		actionsC.addComponent(btnDelete);
		// actionsC.addComponent(btnMoreActions);

		return container;
	}

	private void showDeleteUserContainer(String[] arrID) {

		isPopupShowing = true;
		isSent = false;

		String username = arrID[3];
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

	private void showActivateUserContainer(final String[] arrID) {

		isPopupShowing = true;
		isSent = false;
		String username = arrID[3];
		final Window popup = new Window("Activate " + username + "'s Account");
		popup.setStyleName("w_delete_user");
		popup.setIcon(FontAwesome.KEY);

		popup.center();

		VerticalLayout cDeletePrompt = new VerticalLayout();
		cDeletePrompt.setSpacing(true);
		// cDeletePrompt.setMargin(true);

		Label lbActivationPrompt = new Label(
				"Please enter and confirm PIN for " + username + "?");
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

		frmDeleteReason.addComponent(lbActivationPrompt);
		frmDeleteReason.setComponentAlignment(lbActivationPrompt,
				Alignment.TOP_LEFT);
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
				try {
					strResponse = ums.activateUser("001", "001", "001",
							arrID[2], "001", tFPIN.getValue(),
							tFPINConf.getValue());
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

	private void showSetParentUserContainer(String[] arrID) {

		isPopupShowing = true;
		isSent = false;
		String username = arrID[3];
		final Window popup = new Window("Set Parent Account ID of " + username
				+ "'s Account");
		popup.setStyleName("w_delete_user");
		popup.setIcon(FontAwesome.KEY);

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
		final TextArea taReason = new TextArea();
		taReason.setCaption("Reason");
		taReason.setInputPrompt("Please enter reason here.");
		tFU.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		tFU.setEnabled(true);
		tFU.setValue(arrID[2]);
		tFU.setEnabled(false);
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
		String username = arrID[3];
		final Window popup = new Window("Set Default Account for " + username
				+ "'s Account");
		popup.setStyleName("w_delete_user");
		popup.setIcon(FontAwesome.KEY);

		popup.center();

		VerticalLayout cDeletePrompt = new VerticalLayout();
		cDeletePrompt.setSpacing(true);
		// cDeletePrompt.setMargin(true);

		Label lbActivationPrompt = new Label(
				"Please enter Default Account ID of user " + username);
		lbActivationPrompt.setWidth("200px");

		VerticalLayout frmDeleteReason = new VerticalLayout();
		frmDeleteReason.setSizeUndefined();
		frmDeleteReason.setSpacing(true);
		frmDeleteReason.setMargin(true);
		cDeletePrompt.addComponent(frmDeleteReason);

		final TextField tFP = new TextField("Parent Account ID");
		final TextField tFD = new TextField("Default Account ID");
		final TextArea taReason = new TextArea();
		taReason.setCaption("Reason");
		taReason.setInputPrompt("Please enter reason here.");
		tFP.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		tFP.setEnabled(true);
		tFP.setValue(arrID[2]);
		tFP.setEnabled(false);
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
		popup.setIcon(FontAwesome.KEY);

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
		final TextArea taReason = new TextArea();
		taReason.setCaption("Reason");
		taReason.setInputPrompt("Please enter reason here.");
		tFU.setValue(userID);
		tFU.setEnabled(false);

		tFUProf.setValue(userProfID);
		tFUProf.setEnabled(false);

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

}
