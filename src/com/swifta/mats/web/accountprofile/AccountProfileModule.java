package com.swifta.mats.web.accountprofile;

import java.util.Map;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.usermanagement.NotifCustom;
import com.swifta.mats.web.utils.LoginService;
import com.swifta.mats.web.utils.UserManagementService;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AccountProfileModule {
	private HorizontalLayout udc;
	private Label lbPass;
	private HorizontalLayout cModify;

	public AccountProfileModule() {

	}

	public HorizontalLayout getProfileContainer() {

		Map<String, String> hm = getUD();
		HorizontalLayout c = new HorizontalLayout();
		HorizontalLayout cC = new HorizontalLayout();

		Label lbAddUser = new Label("My Profile");
		lbAddUser.setStyleName("label_user_profile_head");
		lbAddUser.setSizeUndefined();

		lbAddUser = new Label("Account Details");
		lbAddUser.setStyleName("label_user_profile");
		lbAddUser.setSizeUndefined();
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
		addDatum("Username", hm.get("Username"), cAcc);

		lbPass = new Label();
		lbPass.setSizeUndefined();
		// lbPass.setCaption("was set and active.");
		lbPass.setCaption(null);
		lbPass.setValue("**************");

		final Button btnEdit = new Button();
		btnEdit.setIcon(FontAwesome.EDIT);
		btnEdit.setStyleName("btn_link");
		btnEdit.setDescription("Reset your password.");

		lbPass.setStyleName("lb_tst");

		cModify = new HorizontalLayout();
		cModify.setStyleName("c_modifyx");
		cModify.setCaption("Password: ");

		HorizontalLayout cLb = new HorizontalLayout();
		cLb.addComponent(lbPass);
		cLb.setStyleName("c_modify");

		cModify.addComponent(cLb);
		cModify.addComponent(btnEdit);

		final HorizontalLayout cPlaceHolder = new HorizontalLayout();
		cPlaceHolder.setStyleName("c_edit_pass_placeholder_hidden");

		FormLayout cFormLow = new FormLayout();

		addDatum("Email", hm.get("Email"), cFormLow);
		addDatum("MSISDN", hm.get("MSISDN"), cFormLow);

		cAcc.addComponent(cModify);
		cC.addComponent(cAcc);

		VerticalLayout cWrapper = new VerticalLayout();
		cWrapper.setStyleName("my_profile_spacing");
		cWrapper.addComponent(lbAddUser);
		cWrapper.addComponent(cAcc);
		cWrapper.addComponent(cPlaceHolder);
		cC.addComponent(cWrapper);

		// cC.addComponent(cPlaceHolder);
		cPlaceHolder.addComponent(cEditPass);

		lbAddUser = new Label("Email & MSISDN");
		lbAddUser.setStyleName("label_user_profile");
		cC.addComponent(lbAddUser);

		cWrapper.addComponent(lbAddUser);
		cWrapper.addComponent(cFormLow);

		// cC.addComponent(cFormLow);

		lbAddUser = new Label("Basic Details");
		lbAddUser.setStyleName("label_user_profile");

		cC.addComponent(lbAddUser);
		cWrapper = new VerticalLayout();
		cWrapper.setStyleName("my_profile_spacing");
		cWrapper.addComponent(lbAddUser);

		FormLayout cBasic = new FormLayout();
		cWrapper.addComponent(cBasic);
		cC.addComponent(cWrapper);

		addDatum("Profile", hm.get("Profile Type"), cBasic);
		addDatum("Account Status", hm.get("Status"), cBasic);
		addDatum("First Name", hm.get("First Name"), cBasic);
		addDatum("Last Name", hm.get("Last Name"), cBasic);
		// addDatum("Middle Name", hm.get("Middle Name"), cBasic);
		addDatum("Gender", hm.get("Gender"), cBasic);
		addDatum("Occupation", hm.get("Occupation"), cBasic);
		addDatum("Date of Birth", hm.get("Date of Birth"), cBasic);
		addDatum("Country", hm.get("Country"), cBasic);
		addDatum("State", hm.get("State"), cBasic);
		addDatum("Local Government", hm.get("Local Government"), cBasic);

		addDatum("ID Type", hm.get("ID Type"), cBasic);
		addDatum("ID No.", hm.get("ID No."), cBasic);
		// addDatum("Issuer", hm.get("Issuer"), cBasic);
		// addDatum("Issue Date", hm.get("Issue Date"), cBasic);
		// addDatum("Expiry Date", hm.get("Expiry Date"), cBasic);

		cWrapper = new VerticalLayout();
		cWrapper.setStyleName("my_profile_spacing");
		lbAddUser = new Label("Contact & Address Details");
		lbAddUser.setStyleName("label_user_profile");
		cC.addComponent(lbAddUser);

		FormLayout cContact = new FormLayout();
		cWrapper.addComponent(lbAddUser);
		cWrapper.addComponent(cContact);
		cC.addComponent(cWrapper);

		// cContact.setStyleName("frm_user_prof");
		addDatum("Street", hm.get("Street"), cContact);
		addDatum("Mobile Phone No.", hm.get("P-Mobile Phone No."), cContact);
		addDatum("Alt. Phone No.", hm.get("P-Alt. Phone No."), cContact);
		addDatum("Email Address", hm.get("Email"), cContact);

		// addDatum("Mobile Phone No.", hm.get("S-Mobile Phone No."), cContact);
		// addDatum("Alt. Phone No.", hm.get("S-Alt. Phone No."), cContact);
		// addDatum("Email Address", hm.get("Email"), cContact);

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

				try {
					String status = LoginService.webauthenticate(
							user.toString(), tFCurPass.getValue());
					if (status.equals("true")) {

						String initu = UI.getCurrent().getSession()
								.getAttribute("user").toString();
						String response = UserManagementService.passwordReset(
								initu, tFNewPass.getValue());
						if (response != null
								&& response
										.equals("PASSWORD_RESET_WAS_SUCCESSFUL")) {
							lbPass.setValue("**************");
							NotifCustom.show("Password Reset",
									"Password was successfully reset.");
						} else {

							lbError.setStyleName("lb_edit_pass_error");
							lbError.setVisible(true);
							lbError.setValue("Oops... Update failed.("
									+ response + ")");

							btnSave.setEnabled(true);
							return;

						}

					} else if (status.equals("false")) {
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

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				cPlaceHolder.setStyleName("c_edit_pass_placeholder_hidden");
				cModify.setVisible(true);
				btnEdit.setVisible(true);
				lbPass.setVisible(true);
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
				cModify.setVisible(true);
				lbPass.setVisible(true);
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
				lbPass.setVisible(false);
				cModify.setVisible(false);

			}
		});

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

	private Map<String, String> getUD() {
		return LoginService.getUserDetails();

	}

	private void addDatum(String cap, String val, Object container) {

		Label lb = new Label();
		lb.setImmediate(true);
		lb.setStyleName("label_ud");
		lb.setCaption(cap + ": ");
		lb.setValue(val);
		if (container instanceof HorizontalLayout) {
			((HorizontalLayout) container).addComponent(lb);
		} else if (container instanceof FormLayout) {
			((FormLayout) container).addComponent(lb);
			((FormLayout) container).setSpacing(false);
		} else {
			((VerticalLayout) container).addComponent(lb);
			((VerticalLayout) container).setSpacing(false);
		}

	}
}
