package com.swifta.mats.web.accountprofile;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.utils.LoginService;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AccountProfileModule {
	private HorizontalLayout udc;
	private boolean issaving = false;

	public AccountProfileModule() {

	}

	public HorizontalLayout getProfileContainer() {

		HorizontalLayout c = new HorizontalLayout();
		VerticalLayout cC = new VerticalLayout();
		VerticalLayout addUserHeader = new VerticalLayout();

		Label lbAddUser = new Label("My Profile");
		lbAddUser.setStyleName("label_user_profile_head");
		lbAddUser.setSizeUndefined();

		Embedded emb = new Embedded(null, new ThemeResource(
				"img/add_user_small.png"));
		emb.setDescription("Add new User.");
		emb.setStyleName("add_user_img");

		addUserHeader.addComponent(lbAddUser);
		cC.addComponent(lbAddUser);

		lbAddUser = new Label("Account Details");
		lbAddUser.setStyleName("label_user_profile");
		lbAddUser.setSizeUndefined();
		// lbAddUser.setWidth("100%");
		cC.addComponent(lbAddUser);

		FormLayout cAcc = new FormLayout();

		VerticalLayout cEditPass = new VerticalLayout();
		cEditPass.setStyleName("c_edit_password");
		Label lbEditPass = new Label("Edit Password");
		cEditPass.addComponent(lbEditPass);
		cEditPass.setComponentAlignment(lbEditPass, Alignment.TOP_LEFT);

		final Label lbError = new Label("Error");
		lbError.setContentMode(ContentMode.HTML);
		lbError.setStyleName("lb_edit_pass_error");
		cEditPass.addComponent(lbError);
		cEditPass.setComponentAlignment(lbError, Alignment.TOP_CENTER);
		lbError.setVisible(false);

		FormLayout cEditPassForm = new FormLayout();
		final PasswordField tFCurPass = new PasswordField("Current: ");
		final PasswordField tFNewPass = new PasswordField("New: ");
		final PasswordField tFNewRePass = new PasswordField("Re-type New: ");
		cEditPassForm.addComponent(tFCurPass);
		cEditPassForm.addComponent(tFNewPass);
		cEditPassForm.addComponent(tFNewRePass);

		tFCurPass.setImmediate(false);

		tFNewPass.addValidator(new Validator() {
			private static final long serialVersionUID = 1L;

			@Override
			public void validate(Object value) throws InvalidValueException {

				tFNewRePass.setComponentError(null);
				tFNewPass.setComponentError(null);

				if (value != null && !value.toString().trim().isEmpty()) {
					if (value.toString().length() <= 4) {
						tFNewPass.setComponentError(null);
						tFNewRePass.setComponentError(null);
						tFNewPass.focus();
						tFNewRePass.focus();
						throw new InvalidValueException(
								"Password is too short.");
					}
				}

			}

		});

		tFNewRePass.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void validate(Object value) throws InvalidValueException {

				tFNewRePass.setComponentError(null);
				tFNewPass.setComponentError(null);

				if (value == null || value.toString().trim().isEmpty())
					return;

				if (tFNewPass.getValue() == null)
					tFNewPass.setValue("");

				if (tFNewRePass.getValue().length() != tFNewPass.getValue()
						.length()) {
					tFNewRePass.focus();
					throw new InvalidValueException(
							"New passwords do not match.");
				}

				if (!tFNewRePass.getValue().equals(tFNewPass.getValue())) {
					tFNewRePass.focus();
					throw new InvalidValueException(
							"New passwords do not match.");
				}
			}

		});

		// tFNewPass.addValidator(new EditPassValidator(tFCurPass, tFNewPass,
		// tFNewRePass));
		// tFNewRePass.addValidator(new EditPassValidator(tFCurPass, tFNewPass,
		// tFNewRePass));

		HorizontalLayout cBtns = new HorizontalLayout();
		cBtns.setStyleName("c_edit_pass_btns");
		cBtns.setSpacing(true);
		final Button btnSave = new Button();
		btnSave.setIcon(FontAwesome.SAVE);

		Button btnCancel = new Button();
		btnCancel.setIcon(FontAwesome.UNDO);

		btnCancel.setDescription("Cancel");
		btnSave.setDescription("Save new password.");

		btnSave.setStyleName("btn_link");
		btnCancel.setStyleName("btn_link");

		cBtns.addComponent(btnSave);
		cBtns.addComponent(btnCancel);

		cEditPass.addComponent(cEditPassForm);
		cEditPass.addComponent(cBtns);
		cEditPass.setComponentAlignment(cBtns, Alignment.BOTTOM_RIGHT);
		// cEditPass.setSpacing(t);

		Label lb = new Label();
		lb.setCaption("Username: ");
		lb.setValue("livepwndz");

		Label lbPass = new Label();
		lbPass.setSizeUndefined();
		// lbPass.setCaption("was set and active.");
		lbPass.setValue("was set and active.");

		final Button btnEdit = new Button();
		btnEdit.setIcon(FontAwesome.EDIT);
		btnEdit.setStyleName("btn_link");

		lbPass.setStyleName("lb_tst");

		HorizontalLayout cModify = new HorizontalLayout();
		cModify.setStyleName("c_modifyx");
		cModify.setCaption("Password: ");

		HorizontalLayout cLb = new HorizontalLayout();
		cLb.addComponent(lbPass);
		cLb.setStyleName("c_modify");

		// cLb.setComponentAlignment(lbPass, Alignment.MIDDLE_LEFT);

		cModify.addComponent(cLb);
		cModify.addComponent(btnEdit);

		Label lbEmail = new Label();
		lbEmail.setCaption("Email: ");
		lbEmail.setValue("pkkgkkdd@swifta.com");

		Label lbMSISDN = new Label();
		lbMSISDN.setCaption("MSISDN: ");
		lbMSISDN.setValue("+2337041827367");

		final HorizontalLayout cPlaceHolder = new HorizontalLayout();
		cPlaceHolder.setStyleName("c_edit_pass_placeholder_hidden");
		// HorizontalLayout cDivider = new HorizontalLayout();
		FormLayout cFormLow = new FormLayout();

		cAcc.addComponent(lb);
		cAcc.addComponent(cModify);
		cC.addComponent(cAcc);

		cC.addComponent(cPlaceHolder);
		cPlaceHolder.addComponent(cEditPass);

		cFormLow.addComponent(lbEmail);
		cFormLow.addComponent(lbMSISDN);
		cC.addComponent(cFormLow);

		tFCurPass.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				tFCurPass.validate();

			}

		});

		tFNewPass.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				tFNewPass.validate();

			}

		});

		tFNewRePass.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				tFNewRePass.validate();

			}

		});

		btnSave.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1626092932482012474L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					finalEditPassValidator(tFCurPass, tFNewPass, tFNewRePass);
				} catch (Exception e) {
					lbError.setStyleName("lb_edit_pass_error");
					lbError.setVisible(true);
					lbError.setValue(e.getMessage());
					return;

				}

				lbError.setValue("");
				lbError.setVisible(false);
				btnSave.setEnabled(false);

				Object user = UI.getCurrent().getSession().getAttribute("user");
				if (user == null) {
					Notification.show("No valid logged in user.",
							Notification.Type.ERROR_MESSAGE);
					return;
				}

				lbError.setStyleName("lb_edit_pass_normal");
				lbError.setValue("Saving new Password. Pleas wait...");

				LoginService ls = new LoginService();
				try {
					String status = ls.authenticateUser(user.toString(),
							tFCurPass.getValue());
					if (status.equals(true)) {

					} else if (status.equals("false")) {

						// Notification
						// .show("The specified \"Current password\" is INVALID.",
						// Notification.Type.ERROR_MESSAGE);
						tFCurPass.focus();
						lbError.setStyleName("lb_edit_pass_error");
						lbError.setVisible(true);
						lbError.setValue("Error: INVALID <span style = 'font-weight: bold;'>Current password</span>.");
						btnSave.setEnabled(true);
						return;

					} else if (status.equals("locked")) {
						lbError.setStyleName("lb_edit_pass_error");
						lbError.setVisible(true);
						lbError.setValue("Info: Account is Locked. Password change is not possible.");
						btnSave.setEnabled(true);
						return;

					} else {
						lbError.setStyleName("lb_edit_pass_error");
						lbError.setVisible(true);
						lbError.setValue("Info: Sorry...You can not change password right now.");
						btnSave.setEnabled(true);
						return;
					}
				} catch (AxisFault e) {
					lbError.setStyleName("lb_edit_pass_error");
					lbError.setVisible(true);
					lbError.setValue("Error: Oops... Error occured updating password. Please try again later.");
					e.printStackTrace();
					btnSave.setEnabled(true);
					return;

				}

				cPlaceHolder.setStyleName("c_edit_pass_placeholder_hidden");
				btnEdit.setVisible(true);
				lbError.setVisible(false);
				lbError.setStyleName("lb_edit_pass_error");
				tFCurPass.setValue("");
				tFNewPass.setValue("");
				tFNewRePass.setValue("");
				btnSave.setEnabled(true);

			}
		});

		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -1759770635537799499L;

			@Override
			public void buttonClick(ClickEvent event) {
				cPlaceHolder.setStyleName("c_edit_pass_placeholder_hidden");
				btnEdit.setVisible(true);
				lbError.setValue("");
				lbError.setVisible(false);
				tFCurPass.setValue("");
				tFNewPass.setValue("");
				tFNewRePass.setValue("");

			}
		});

		btnEdit.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1626092932482012474L;

			@Override
			public void buttonClick(ClickEvent event) {
				cPlaceHolder.setStyleName("c_edit_pass_placeholder_visible");
				btnEdit.setVisible(false);

			}
		});

		lbAddUser = new Label("Basic Details");
		lbAddUser.setStyleName("label_user_profile");

		cC.addComponent(lbAddUser);

		FormLayout cBasic = new FormLayout();
		// cBasic.setStyleName("frm_user_prof");
		lb = new Label();
		lb.setCaption("Names: ");
		lb.setValue("Paul Kigozi Livepwndz");
		cBasic.addComponent(lb);
		cC.addComponent(cBasic);

		lbAddUser = new Label("Contact Details");
		lbAddUser.setStyleName("label_user_profile");
		cC.addComponent(lbAddUser);

		FormLayout cContact = new FormLayout();
		// cContact.setStyleName("frm_user_prof");
		lb = new Label();
		lb.setCaption("Street: ");
		lb.setValue("William str.");
		cContact.addComponent(lb);
		cC.addComponent(cContact);

		c.addComponent(cC);
		return c;
	}

	private void finalEditPassValidator(PasswordField tFCurPass,
			PasswordField tFNewPass, PasswordField tFNewRePass)
			throws Exception {

		if (tFCurPass.getValue() == null) {
			tFCurPass.focus();
			throw new Exception("Please enter current password.");
		}

		if (tFCurPass.getValue().trim().isEmpty()) {
			tFCurPass.focus();
			throw new Exception("Please enter current password.");
		}

		if (tFNewPass.getValue() == null
				|| tFNewPass.getValue().trim().isEmpty()) {
			tFNewPass.focus();
			throw new Exception("Please enter new password.");
		}

		if (tFNewRePass.getValue() == null
				|| tFNewRePass.getValue().trim().isEmpty()) {
			tFNewRePass.focus();
			throw new Exception("Please retype new password.");
		}

		tFCurPass.validate();
		tFNewPass.validate();
		tFNewRePass.validate();

	}

	public void apmModifier(String strTbName, HorizontalLayout cContent) {

		if (UI.getCurrent().getSession()
				.getAttribute(WorkSpaceManageProfile.SESSION_UDM_IS_LOG) != null) {
			strTbName = (String) UI.getCurrent().getSession()
					.getAttribute(WorkSpaceManageProfile.SESSION_UDM_TABLE_LOG);
		}

		if (udc != null)
			cContent.removeComponent(udc);
		// udc = udm.getDetailsForm(strTbName, "001", false, false);
		cContent.addComponent(udc);
		return;

	}
}
