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

import com.swifta.mats.web.accountprofile.WorkSpaceManageProfile;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class UserDetailsModule {
	public static boolean uDetailsEditStatus = false;
	FormLayout cUPersonalDetails;
	VerticalLayout cUDetails;
	HorizontalLayout cDetailsAndOperations;
	// Declaration of form fields
	TextField tfGen;
	OptionGroup opt;
	ComboBox combo;
	PopupDateField dF;

	public static final String SESSION_UDM = "session_user_details";
	public static final String SESSION_UDM_LOG = "session_log";
	public static final String SESSION_UDM_TABLE_LOG = "session_log_table";
	public static final String SESSION_UDM_IS_LOG = "session_is_log";
	public static final String SESSION_VAR_UDM_IS_LOG_TRUE = "true";
	public static final String SESSION_VAR_UDM_IS_LOG_FALSE = "false";

	public static final String SESSION_UDM_TABLE = "session_user_details_table";
	public static final String SESSION_UDM_ID = "uid";
	public static final String SESSION_UDM_UNAME = "uname";
	// public static final String SESSION_UDM = "session_user_details";
	public static final String SESSION_VAR_UDM_PER = "personal";
	public static final String SESSION_VAR_UDM_ACC = "account";
	public static final String SESSION_VAR_UDM_AUTH = "auth";
	public static final String SESSION_VAR_UDM_ACC_LOG = "account_change_log";
	public static final String SESSION_VAR_UDM_ACT_LOG = "activity_log";

	public static final String SESSION_UDM_ACTION_EDIT_DETAILS = "session_udm_action_edit_details";

	// Declaration of data containers...

	String[] arrTfCaptions;
	String[] arrOptCaptions;
	String[] arrComboCaptions;
	String[] arrDfCaptions;

	String[] arrTfVals;
	String[] arrOptVals;
	String[] arrComboVals;
	String[] arrDfVals;

	EditCancelBtnsSingleField ecbsf;
	List<Object> arrLComboSEditableField = new ArrayList<Object>();
	ArrayList<String> arrLComboSEditableFieldVal = new ArrayList<String>();

	OptionGroup optSEditableField;
	String strOptSEditableFieldVal;
	ArrayList<BtnTabLike> arrLTabBtns;
	HorizontalLayout cPerAccAuthInfo;

	HashMap<Integer, String> profToID = new HashMap<>();

	TextField tFFN;
	TextField tFMN;
	TextField tFLN;
	TextField tFOcc;
	TextField tFEmp;
	TextField tFUN;
	TextField tFMSISDN;
	TextField tFBAcc;
	TextField tFAccEmail;
	TextField tFClrNo;
	TextField tFSecAns;
	TextField tFPMNo;
	TextField tFPANo;
	TextField tFPEmail;
	TextField tFPostalCode;
	TextField tFStreet;
	TextField tFCity;
	TextField tFProv;
	TextField tFSMNo;
	TextField tFSANo;
	TextField tFSEmail;
	TextField tFIssuer;
	TextField tFIDNo;

	CheckBox chcTAndC;
	ComboBox comboPref;
	ComboBox comboSuff;
	ComboBox comboState;
	ComboBox comboLG;
	ComboBox comboCountry;
	ComboBox comboLang;
	ComboBox comboBDomain;
	ComboBox comboBID;
	ComboBox comboCur;
	ComboBox comboSecQn;
	ComboBox comboProfile;
	ComboBox comboIDType;

	PopupDateField dFDoB;
	PopupDateField dFDoI;
	PopupDateField dFDoE;

	private OptionGroup optSex;
	private boolean isValidatorAdded = false;
	private boolean isCSelected = false;
	// private String curC = null;

	private Calendar cal = null;
	private List<Object> arrLAllFormFields;
	private List<Object> arrLAllEditableFields;
	private ArrayList<String> arrLTfEditableVals;
	private ArrayList<String> arrLOptEditableVals;
	private ArrayList<String> arrLComboEditableVals;
	// private ArrayList<String> arrLDfEditableVals;
	private boolean boolEditStatus = false;
	private HorizontalLayout cBtnEditCancel;

	private Button btnE = null;
	private Button btnC = null;

	public UserDetailsModule() {

	}

	public HorizontalLayout getDetailsForm(String strTbName, String strUID,
			boolean hasOp, boolean boolEditStatus) {
		this.boolEditStatus = boolEditStatus;
		return setDetailsForm(getUDetails(strTbName, strUID), hasOp,
				boolEditStatus);
	}

	private HorizontalLayout setDetailsForm(Map<String, String[]> mappedData,
			boolean hasOp, boolean boolEditStatus) {
		cDetailsAndOperations = new HorizontalLayout();
		cDetailsAndOperations.setSizeFull();

		// Notification.show(Boolean.toString(hasOp));

		cUPersonalDetails = new FormLayout();
		cUPersonalDetails.setMargin(true);
		cUPersonalDetails.setSpacing(false);
		cUPersonalDetails.setStyleName("frm_details_personal_info");
		cUPersonalDetails.setSizeFull();
		cDetailsAndOperations.addComponent(cUPersonalDetails);

		if (mappedData.size() == 0) {
			cUPersonalDetails.addComponent(new Label(" NO Data Available!"));
			uDetailsEditStatus = false;
		} else {

			String strTbName = mappedData.get("arrTbName")[0];
			String strUID = mappedData.get("arrUID")[0];
			if (strTbName.equals("activity_log")
					|| strTbName.equals("account_change_log")) {
				// TODO fetch specified table log

			} else {

				arrLAllFormFields = new ArrayList<Object>();
				HashMap<String, Object> mapSlaveFields = new HashMap<String, Object>();

				/*
				 * TODO
				 * 
				 * Fetched data
				 */

				// Set Data

				arrTfCaptions = mappedData.get("arrTfCaptions");
				arrOptCaptions = mappedData.get("arrOptCaptions");
				arrComboCaptions = mappedData.get("arrComboCaptions");
				arrDfCaptions = mappedData.get("arrDfCaptions");

				arrTfVals = mappedData.get("arrTfVals");
				arrOptVals = mappedData.get("arrOptVals");
				arrComboVals = mappedData.get("arrComboVals");
				arrDfVals = mappedData.get("arrDfVals");

				final String btnEditId = "edit";
				final String btnSaveId = "save";

				// Holders of editable form components

				arrLAllEditableFields = new ArrayList<Object>();

				// Holders of editable form components original values to be
				// used for undoing/resetting the form.
				arrLTfEditableVals = new ArrayList<String>();
				arrLOptEditableVals = new ArrayList<String>();
				arrLComboEditableVals = new ArrayList<String>();
				final ArrayList<String> arrLDfEditableVals = new ArrayList<String>();

				String strCurUser = (String) UI.getCurrent().getSession()
						.getAttribute(WorkSpaceManageProfile.SESSION_WSMP);
				// Notification.show(strCurUserType+" +++++++");
				// For testing purposes, we assume that every first item of each
				// for category is read-only, hence the isReadOnly = 0

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

				btnE = btnEdit;
				btnC = btnCancel;

				String strCurUserAction = (String) UI
						.getCurrent()
						.getSession()
						.getAttribute(
								WorkSpaceManageProfile.SESSION_WSMP_CUR_ACTION);
				if (strCurUserAction != null) {
					if (strCurUserAction
							.equals(WorkSpaceManageProfile.SESSION_VAR_WSMP_AUTH)) {
						btnEdit.setVisible(false);
					} else {
						btnEdit.setVisible(true);
						btnEdit.setEnabled(true);
					}
				}

				if (hasOp) {
					ecbsf = new EditCancelBtnsSingleField(cUPersonalDetails,
							false);
					cDetailsAndOperations.addComponent(getOpForm(strTbName,
							strUID, mapSlaveFields));
					btnEdit.setVisible(false);
					btnEdit.setEnabled(false);
					ecbsf.btnEditS.setVisible(false);

				} else {
					wrapperEditCancelBtnsClickListener(btnEdit, btnCancel,
							btnEditId, btnSaveId, arrLAllEditableFields,
							cBtnEditCancel, arrLTfEditableVals,
							arrLOptEditableVals, arrLComboEditableVals,
							arrLDfEditableVals);
					cUPersonalDetails.addComponent(cBtnEditCancel);
				}

				setData(strTbName, strUID, strCurUser, arrLAllFormFields,
						arrLAllEditableFields, arrLTfEditableVals,
						arrLOptEditableVals, arrLComboEditableVals,
						arrLDfEditableVals, mapSlaveFields);

				/*
				 * 
				 * If edit status is true and has no operations, initiate
				 * editUserDetails
				 */
				if (boolEditStatus && !hasOp) {
					editUserDetails(arrLAllEditableFields, btnEdit, btnCancel,
							btnSaveId, cBtnEditCancel);
				}

			}

		}

		return cDetailsAndOperations;
	}

	private void resetForm(List<Object> lAllEditableFields,
			ArrayList<String> arrLTfVals, ArrayList<String> arrLOptVals,
			ArrayList<String> arrLComboSelVals, ArrayList<String> arrLDfVals,
			ArrayList<String> arrLEV) {

		int iTf = 0;
		int iOpt = 0;
		int iCombo = 0;
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

	private void disableEditableFields(List<Object> arrLAllEditableFields) {
		uDetailsEditStatus = false;
		for (Object f : arrLAllEditableFields) {
			if (f instanceof TextField) {
				((TextField) f).setStyleName(ValoTheme.BUTTON_BORDERLESS);
				((TextField) f).setReadOnly(true);
			} else if (f instanceof OptionGroup) {
				((OptionGroup) f).setReadOnly(true);
			} else if (f instanceof ComboBox) {
				((ComboBox) f).setReadOnly(true);
			} else if (f instanceof PopupDateField) {
				((PopupDateField) f).setReadOnly(true);
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

	public Map<String, String[]> getUDetails(String strTbName, String strUID) {
		UserDetailsBackEnd bee = new UserDetailsBackEnd();
		Map<String, String[]> mappedData = bee.getUserPersonalInfo(strTbName,
				strUID);
		return mappedData;
	}

	private void setTfs(int isReadOnlyTf, List<Object> arrLAllFormFields,
			List<Object> arrLTfEditable, ArrayList<String> arrLTfEditableVals) {
		for (int iTf = 0; iTf < arrTfVals.length; iTf++) {
			tfGen = new TextField();
			tfGen.setCaption(arrTfCaptions[iTf]);
			tfGen.setValue(arrTfVals[iTf]);
			tfGen.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
			tfGen.setReadOnly(true);
			tfGen.setDescription(arrTfVals[iTf]);
			tfGen.setWidth("100%");
			arrLAllFormFields.add(tfGen);

			cUPersonalDetails.addComponent(tfGen);
			if (iTf != isReadOnlyTf) {
				arrLTfEditable.add(tfGen);
				arrLTfEditableVals.add(arrTfVals[iTf]);
			}
		}

	}

	private void setOpts(String strTbName, String strUID, int isReadOnlyOpt,
			List<Object> lAllFormFields, List<Object> arrLOptEditable,
			ArrayList<String> arrLOptEditableVals,
			HashMap<String, Object> mapSlaveFields) {
		for (int iOpt = 0; iOpt < arrOptVals.length; iOpt++) {
			opt = new OptionGroup();
			opt.setCaption(arrOptCaptions[iOpt]);
			opt.addItem(arrOptVals[iOpt]);
			opt.select(arrOptVals[iOpt]);
			opt.setReadOnly(true);
			opt.setId("opt_" + strTbName + "_" + strUID + "_"
					+ arrOptCaptions[iOpt]);
			if (iOpt != isReadOnlyOpt) {
				arrLOptEditable.add(opt);
				arrLOptEditableVals.add(arrOptVals[iOpt]);
			}
			if (strTbName.equals("account")
					&& arrOptCaptions[iOpt].equals("Status")) {
				strOptSEditableFieldVal = arrOptVals[iOpt];
				optSEditableField = opt;
				mapSlaveFields.put(arrOptCaptions[iOpt], opt);

			}

			cUPersonalDetails.addComponent(opt);
		}
	}

	private void setCombos(String strTbName, String strUID,
			int isReadOnlyCombo, List<Object> lAllFormFields,
			List<Object> arrLComboEditable,
			ArrayList<String> arrLComboEditableVals,
			HashMap<String, Object> mapSlaveFields) {
		for (int iCombo = 0; iCombo < arrComboVals.length; iCombo++) {
			combo = new ComboBox();
			combo.setCaption(arrComboCaptions[iCombo]);
			combo.addItem(arrComboVals[iCombo]);
			combo.select(arrComboVals[iCombo]);
			combo.setReadOnly(true);
			lAllFormFields.add(combo);
			if (iCombo != isReadOnlyCombo) {
				arrLComboEditable.add(combo);
				arrLComboEditableVals.add(arrComboVals[iCombo]);
			}

			if (strTbName.equals("account")
					&& arrComboCaptions[iCombo].equals("Type")) {
				arrLComboSEditableField.add(combo);
				arrLComboSEditableFieldVal.add(arrComboVals[iCombo]);

				mapSlaveFields.put(arrComboCaptions[iCombo], combo);

				combo.addValueChangeListener(new ValueChangeListener() {
					private static final long serialVersionUID = -2182355729919041184L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						if (ecbsf == null)
							return;
						ecbsf.btnEditS.setIcon(FontAwesome.SAVE);
						ecbsf.btnEditS.setVisible(true);
						ecbsf.btnCancel.setVisible(true);
						ecbsf.btnEditS.setEnabled(true);
						ecbsf.btnCancel.setEnabled(true);
						uDetailsEditStatus = true;

					}

				});

			}

			cUPersonalDetails.addComponent(combo);
		}
	}

	@SuppressWarnings("deprecation")
	private void setDfs(int isReadOnlyCombo, List<Object> lAllFormFields,
			List<Object> arrLDfEditable, ArrayList<String> arrLDfEditableVals) {
		for (int iDf = 0; iDf < arrDfVals.length; iDf++) {
			dF = new PopupDateField();
			dF.setCaption(arrDfCaptions[iDf]);
			dF.setValue(new Date(arrDfVals[iDf]));
			dF.setReadOnly(true);
			lAllFormFields.add(dF);
			if (iDf != isReadOnlyCombo) {
				arrLDfEditable.add(dF);
				arrLDfEditableVals.add(arrDfVals[iDf]);
			}
			cUPersonalDetails.addComponent(dF);
		}
	}

	private void editUserDetails(List<Object> arrLAllEditableFields,
			Button btnEdit, Button btnCancel, String btnSaveId,
			HorizontalLayout cBtnEditCancel) {
		enableEditableFormFields(arrLAllEditableFields);
		/*
		 * By Default, btnCancel is not visible, until btnEdit is clicked. Only
		 * until then is it added and visible.
		 */

		if (!btnCancel.isVisible()) {
			btnEdit.setId(btnSaveId);
			btnEdit.setIcon(FontAwesome.SAVE);
			btnCancel.setVisible(true);
			cBtnEditCancel.addComponent(btnCancel);
		}
	}

	public FormLayout getOpForm(String strTbName, String strUID,
			HashMap<String, Object> mapSlaveFields) {

		String strUserType = (String) UI.getCurrent().getSession()
				.getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE);
		// Notification.show(strUserType);
		if (strUserType
				.equals(WorkSpaceManageUser.SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE)) {
			return getAgentOpForm(strTbName, strUID, mapSlaveFields);
		} else {
			// Notification.show("Nothing.");
			return new FormLayout();
		}

	}

	private void wrapperEditCancelBtnsClickListener(final Button btnEdit,
			final Button btnCancel, final String btnEditId,
			final String btnSaveId, final List<Object> arrLAllEditableFields,
			final HorizontalLayout cBtnEditCancel,
			final ArrayList<String> arrLTfEditableVals,
			final ArrayList<String> arrLOptEditableVals,
			final ArrayList<String> arrLComboEditableVals,
			final ArrayList<String> arrLDfEditableVals) {
		btnEdit.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -6544444429248747390L;

			@Override
			public void buttonClick(ClickEvent event) {

				/*
				 * Prepare all Editable fields (Entire form) for editing.
				 */
				if (event.getButton().getId().equals(btnEditId)) {
					cUPersonalDetails.removeAllComponents();
					cUPersonalDetails.addComponent(getEUDContainer());
					editUserDetails(arrLAllEditableFields, btnEdit, btnCancel,
							btnSaveId, cBtnEditCancel);

					/*
					 * By Default, btnCancel is not visible, until btnEdit is
					 * clicked. Only until then is it added and visible.
					 */

					if (!btnCancel.isVisible()) {
						event.getButton().setId(btnSaveId);
						event.getButton().setIcon(FontAwesome.SAVE);
						btnCancel.setVisible(true);
						cBtnEditCancel.addComponent(btnCancel);
					}

				} else {
					if (event.getButton().getId().equals(btnSaveId)) {
						/*
						 * 
						 * 
						 * 
						 * 
						 * TODO commit (save) changes i.e, send changes back to
						 * the server.
						 */

						// Remove undo button (btnCancel)
						btnCancel.setVisible(false);

						// Reset all Editable fields to readOnly after saving to
						// the server
						disableEditableFields(arrLAllEditableFields);

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

			private static final long serialVersionUID = 7719883177456399112L;

			@Override
			public void buttonClick(ClickEvent event) {

				resetForm(arrLAllEditableFields, arrLTfEditableVals,
						arrLOptEditableVals, arrLComboEditableVals,
						arrLDfEditableVals, arrLTfEditableVals);
				btnEdit.setId(btnEditId);
				btnEdit.setIcon(FontAwesome.EDIT);
				btnCancel.setVisible(false);

			}
		});

	}

	private class EditCancelBtnsSingleField {

		final private Button btnEditS = new Button();
		final Button btnCancel = new Button();
		final HorizontalLayout cBtnEditCancel = new HorizontalLayout();
		private Object c;
		private boolean isInlineEdit = false;
		private ArrayList<Object> arrLEf;
		private ArrayList<String> arrLEfVal;
		private final String strEPass = "cur_user_btn_id_edit_pass";
		private final String strSPass = "cur_user_btn_id_save_pass";
		private final String strCPass = "cur_user_btn_id_cancel_pass";

		private final String strEUname = "cur_user_btn_id_edit_uname";
		private final String strSUname = "cur_user_btn_id_save_uname";
		private final String strCUname = "cur_user_btn_id_cancel_uname";

		public EditCancelBtnsSingleField(FormLayout c, boolean isInlineEdit) {
			this.c = c;
			this.isInlineEdit = isInlineEdit;
			addBtns();

		}

		public EditCancelBtnsSingleField(HorizontalLayout c,
				boolean isInlineEdit, ArrayList<Object> ef,
				ArrayList<String> arrLEfVal) {
			this.c = c;
			this.isInlineEdit = isInlineEdit;
			this.arrLEf = ef;
			this.arrLEfVal = arrLEfVal;
			addBtns();

		}

		private void addBtns() {

			btnEditS.setIcon(FontAwesome.EDIT);
			btnEditS.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
			btnEditS.setStyleName("btn_link btn_repositioned");
			// btnEditS.setVisible(false);
			// btnEditS.setEnabled(false);
			// btnEditS.setId(strE);

			btnCancel.setIcon(FontAwesome.UNDO);
			btnCancel.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
			btnCancel.setStyleName("btn_link btn_repositioned");
			btnCancel.setVisible(false);
			btnCancel.setEnabled(false);

			cBtnEditCancel.setSizeUndefined();
			cBtnEditCancel.addComponent(btnEditS);
			cBtnEditCancel.addComponent(btnCancel);
			if (c instanceof FormLayout) {
				((FormLayout) c).addComponent(cBtnEditCancel);

			} else if (c instanceof HorizontalLayout) {
				((HorizontalLayout) c).addComponent(cBtnEditCancel);
			}

			if (!isInlineEdit) {
				btnEditS.addClickListener(new Button.ClickListener() {

					private static final long serialVersionUID = 7008276156596987435L;

					@Override
					public void buttonClick(ClickEvent event) {
						/*
						 * 
						 * TODO Commit changes to the server
						 */
						disableEditableFields(arrLComboSEditableField);
						btnCancel.setVisible(false);
						btnCancel.setEnabled(false);
						btnEditS.setVisible(false);
						btnEditS.setEnabled(false);

					}
				});

				btnCancel.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 7008276156596987435L;

					@Override
					public void buttonClick(ClickEvent event) {

						resetForm(arrLComboSEditableField, null, null,
								arrLComboSEditableFieldVal, null, null);
						uDetailsEditStatus = false;
						btnCancel.setVisible(false);
						btnCancel.setEnabled(false);
						btnEditS.setVisible(false);
						btnEditS.setEnabled(false);
					}
				});

			} else if (isInlineEdit) {
				btnEditS.addClickListener(new Button.ClickListener() {
					private String strEID = null;

					private static final long serialVersionUID = 5258861566127416396L;

					@Override
					public void buttonClick(ClickEvent event) {

						strEID = btnEditS.getId();
						if (strEID.equals(strEPass) || strEID.equals(strEUname)) {

							if (strEID.equals(strEPass)) {
								// btnEditS.setId(strSPass);
								showPasswordChangeWindow();
							} else if (strEID.equals(strEUname)) {
								enableEditableFormFields(arrLEf);
								btnEditS.setId(strSUname);
								btnCancel.setVisible(true);
								btnCancel.setEnabled(true);
								btnEditS.setIcon(FontAwesome.SAVE);
							}
						} else if (strEID.equals(strSPass)
								|| strEID.equals(strSUname)) {

							disableEditableFields(arrLEf);
							btnEditS.setIcon(FontAwesome.EDIT);
							btnCancel.setVisible(false);
							btnCancel.setEnabled(false);
							if (strEID.equals(strSPass)) {
								btnEditS.setId(strEPass);

							} else if (strEID.equals(strSUname)) {
								btnEditS.setId(strEUname);
							}

						}

					}
				});

				btnCancel.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 7008276156596987435L;
					String strCID = null;

					@Override
					public void buttonClick(ClickEvent event) {

						strCID = btnCancel.getId();
						if (strCID.equals(strCPass) || strCID.equals(strCUname)) {
							resetForm(arrLEf, arrLEfVal, null, null, null, null);
							uDetailsEditStatus = false;
							btnEditS.setIcon(FontAwesome.EDIT);
							btnCancel.setVisible(false);
							btnCancel.setEnabled(false);

							if (btnEditS.getId().equals(strSPass)) {
								btnEditS.setId(strEPass);
							} else if (btnEditS.getId().equals(strSUname)) {
								btnEditS.setId(strEUname);
							}
						}

						/*
						 * resetForm(arrLEf, arrLEfVal, null, null,null);
						 * uDetailsEditStatus = false;
						 * btnCancel.setVisible(false);
						 * btnCancel.setEnabled(false);
						 * btnEditS.setIcon(FontAwesome.EDIT);
						 * btnEditS.setId(strE);
						 */

					}
				});
			}

		}

	}

	private class BtnOpClickListener implements Button.ClickListener {

		private static final long serialVersionUID = -8361209098934409391L;
		private Object objSlaveF;
		private Button btnSlave;

		public BtnOpClickListener(Object objSlaveF, Button btnSlave) {
			this.objSlaveF = objSlaveF;
			this.btnSlave = btnSlave;

		}

		@Override
		public void buttonClick(ClickEvent event) {
			Button btn = event.getButton();
			String strBtnID = btn.getId();
			String[] arrData = strBtnID.split("_");
			String strTbName = arrData[0];
			String strUID = arrData[1];
			String strAction = arrData[2];

			String strNewStatus = null;
			String strOldStatus;
			String strNewAction;
			if (strAction.equals("Activate") || strAction.equals("Deactivate")) {
				OptionGroup opt = ((OptionGroup) objSlaveF);
				if (strAction.equals("Activate")) {
					strNewStatus = "Active";
					strOldStatus = "Deactive";
					strNewAction = "Deactivate";

					opt.setEnabled(true);
					opt.setReadOnly(false);

					opt.setValue(false);
					opt.removeItem(strOldStatus);
					opt.addItem(strNewStatus);
					opt.select(strNewStatus);

					opt.setEnabled(false);
					opt.setReadOnly(true);
					btn.setCaption(strNewAction);
					btn.setId(strTbName + "_" + strUID + "_" + strNewAction);

					if (btnSlave != null)
						btnSlave.setEnabled(true);
				} else if (strAction.equals("Deactivate")) {
					strNewStatus = "Deactive";
					strOldStatus = "Active";
					strNewAction = "Activate";

					opt.setEnabled(true);
					opt.setReadOnly(false);

					opt.setValue(false);
					opt.removeItem(strOldStatus);
					opt.addItem(strNewStatus);
					opt.select(strNewStatus);

					opt.setEnabled(false);
					opt.setReadOnly(true);
					btn.setCaption(strNewAction);
					btn.setId(strTbName + "_" + strUID + "_" + strNewAction);
					if (btnSlave != null)
						btnSlave.setEnabled(false);
				}

				// TODO Commit changes to the server.
				NotifCustom.show(
						"Status",
						"Account status of user of ID " + strUID
								+ " has been changed to "
								+ strNewStatus.toUpperCase());

			} else if (strAction.equals("Change Type")) {
				ArrayList<Object> arrLEditableField = new ArrayList<Object>();
				arrLEditableField.add(combo);
				enableEditableFormFields(arrLEditableField);
				// Notification.show("Yeah!");
			}

		}

	}

	private FormLayout getAgentOpForm(String strTbName, String strUID,
			HashMap<String, Object> mapSlaveFields) {

		FormLayout frmOps = new FormLayout();
		frmOps.setSizeUndefined();
		Label lbOp = new Label();

		if (strTbName.equals("account")) {
			Button btnOpSt = new Button();
			Button btnOpTyp = new Button();

			lbOp.setValue("Account Operations");
			frmOps.addComponent(lbOp);
			String strAction;
			String strCurVal = null;

			OptionGroup opt = (OptionGroup) mapSlaveFields.get("Status");
			strCurVal = "Active";

			if (strCurVal.equals("Active")) {
				strAction = "Deactivate";
			} else {
				strAction = "Activate";
			}

			// Notification.show(strAction);

			btnOpSt.setCaption(strAction);
			btnOpSt.addClickListener(new BtnOpClickListener(opt, btnOpTyp));
			btnOpSt.setId(strTbName + "_" + strUID + "_" + strAction);
			frmOps.addComponent(btnOpSt);

			strAction = "Change Type";
			// ComboBox combo = (ComboBox) mapSlaveFields.get("Type");
			btnOpTyp.setCaption(strAction);
			btnOpTyp.addClickListener(new BtnOpClickListener(opt, null));
			btnOpTyp.setId(strTbName + "_" + strUID + "_" + strAction);
			frmOps.addComponent(btnOpTyp);
		}

		return frmOps;

	}

	private void showPasswordChangeWindow() {

		final Window popup = new Window("Change Password");
		popup.setIcon(FontAwesome.EDIT);
		popup.center();
		popup.setStyleName("w_change_pass");

		VerticalLayout cPopupMsg = new VerticalLayout();
		// cPopupMsg.setMargin(true);
		// cPopupMsg.setSpacing(true);

		VerticalLayout frm = new VerticalLayout();
		frm.setSpacing(true);
		frm.setSizeUndefined();

		Label lbChangePass = new Label("Change your Password");
		lbChangePass.setStyleName("label_search_user");
		TextField tfCurPass = new TextField("Current Password");
		TextField tfNewPass = new TextField("New Password");
		TextField tfReNewPass = new TextField("Re-Enter Password");
		frm.addComponent(lbChangePass);
		frm.addComponent(tfCurPass);
		frm.addComponent(tfNewPass);
		frm.addComponent(tfReNewPass);

		cPopupMsg.addComponent(frm);

		HorizontalLayout cPopupBtns = new HorizontalLayout();
		cPopupBtns.setSizeUndefined();
		cPopupBtns.setSpacing(true);

		Button btnSave = new Button();
		btnSave.setIcon(FontAwesome.SAVE);
		Button btnCancel = new Button("Cancel");
		cPopupBtns.addComponent(btnSave);
		cPopupBtns.addComponent(btnCancel);
		frm.addComponent(cPopupBtns);
		frm.setComponentAlignment(cPopupBtns, Alignment.BOTTOM_CENTER);

		cPopupMsg.addComponent(frm);
		cPopupMsg.setComponentAlignment(frm, Alignment.TOP_CENTER);

		btnSave.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -4241921726926290840L;

			@Override
			public void buttonClick(ClickEvent event) {

				/*
				 * TODO Verify current Password and new password. Save/indicate
				 * errors.
				 */

			}
		});

		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 7890239257486668503L;

			@Override
			public void buttonClick(ClickEvent event) {
				popup.close();
			}
		});

		popup.setContent(cPopupMsg);
		UI.getCurrent().addWindow(popup);
	}

	private void setCurUserTfs(int isReadOnlyTf,
			List<Object> arrLAllFormFields, List<Object> arrLTfEditable,
			ArrayList<String> arrLTfEditableVals) {
		VerticalLayout frm = new VerticalLayout();
		HorizontalLayout cTf;
		// EditCancelBtnsSingleField ecbsfInline;
		ArrayList<Object> arrLEf;
		ArrayList<String> arrLEfVal;
		// String strEID = null;

		for (int iTf = 0; iTf < arrTfVals.length; iTf++) {
			cTf = new HorizontalLayout();

			tfGen = new TextField();
			tfGen.setCaption(arrTfCaptions[iTf]);
			tfGen.setValue(arrTfVals[iTf]);
			tfGen.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
			tfGen.setReadOnly(true);
			tfGen.setDescription(arrTfVals[iTf]);
			tfGen.setWidth("100%");
			arrLAllFormFields.add(tfGen);
			cTf.addComponent(tfGen);

			frm.addComponent(cTf);
			cUPersonalDetails.addComponent(frm);
			if (iTf != isReadOnlyTf) {
				arrLEf = new ArrayList<>();
				arrLEfVal = new ArrayList<>();
				arrLEf.add(tfGen);
				arrLEfVal.add(arrTfVals[iTf]);

				ecbsf = new EditCancelBtnsSingleField(cTf, true, arrLEf,
						arrLEfVal);
				ecbsf.btnEditS.setVisible(true);
				ecbsf.btnEditS.setEnabled(true);

				if (arrTfCaptions[iTf].equals("Username")) {
					ecbsf.btnEditS.setId("cur_user_btn_id_edit_uname");
					ecbsf.btnCancel.setId("cur_user_btn_id_cancel_uname");
				} else if (arrTfCaptions[iTf].equals("Password")) {
					ecbsf.btnEditS.setId("cur_user_btn_id_edit_pass");
					ecbsf.btnCancel.setId("cur_user_btn_id_cancel_pass");
				}

				arrLTfEditable.add(tfGen);
				arrLTfEditableVals.add(arrTfVals[iTf]);
			}
		}

	}

	public VerticalLayout getUserDetailsContainer(String strTbName,
			String strUID, boolean hasOp, boolean boolEditStatus) {
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
			strCHeader = "Edit Details of Livepwndz";
		} else {
			strCHeader = "Showing Details of Livepwndz";
		}

		Label lbCHeader = new Label(strCHeader);
		lbCHeader.setStyleName("label_c_header");
		lbCHeader.setSizeUndefined();

		cPerAccAuthInfo = new HorizontalLayout();
		cPerAccAuthInfo.setStyleName("c_per_acc_auth_info");
		cPerAccAuthInfo.setSizeFull();

		VerticalLayout cTabLike = new VerticalLayout();
		cTabLike.setSizeUndefined();

		cTabLike = getManageUserMenu(false, boolEditStatus, hasOp, null);

		cCHeader.addComponent(lbCHeader);
		cCHeader.addComponent(cTabLike);

		cUDetails.addComponent(cCHeader);
		cUDetails.setComponentAlignment(cCHeader, Alignment.TOP_LEFT);

		cUDetails.addComponent(cPerAccAuthInfo);
		cUDetails.setComponentAlignment(cPerAccAuthInfo, Alignment.TOP_LEFT);
		cUDetails.setExpandRatio(cPerAccAuthInfo, 1.0f);

		HorizontalLayout cUDetailsAndOperations = getDetailsForm(strTbName,
				strUID, hasOp, boolEditStatus);
		cPerAccAuthInfo.addComponent(cUDetailsAndOperations);

		UI.getCurrent().getSession().setAttribute(SESSION_UDM, null);

		return cUDetails;
	}

	private VerticalLayout getManageUserMenu(boolean boolAddHeaderStatus,
			boolean boolEditStatus, boolean hasOp, Object aum) {

		VerticalLayout cManageUserMenu = new VerticalLayout();
		cManageUserMenu.setStyleName("c_u_manage_menu");
		cManageUserMenu.setSizeUndefined();

		HorizontalLayout cManageAndAddTab = new HorizontalLayout();

		BtnTabLike btnPersonal = new BtnTabLike("Basic", null);
		btnPersonal.setStyleName("btn_tab_like btn_tab_like_active");

		BtnTabLike btnAccount = new BtnTabLike("Account", null);
		BtnTabLike btnAuth = new BtnTabLike("Authentication", null);
		BtnTabLike btnLog = new BtnTabLike("Log", null);
		cManageAndAddTab.addComponent(btnPersonal);
		cManageAndAddTab.addComponent(btnAccount);
		cManageAndAddTab.addComponent(btnAuth);
		cManageAndAddTab.addComponent(btnLog);

		final ArrayList<BtnTabLike> arrLTabBtns = new ArrayList<BtnTabLike>();
		arrLTabBtns.add(btnPersonal);
		arrLTabBtns.add(btnAccount);
		arrLTabBtns.add(btnAuth);
		arrLTabBtns.add(btnLog);

		btnPersonal.setEnabled(false);

		ArrayList<HorizontalLayout> arrLSubTabs = new ArrayList<HorizontalLayout>();

		HorizontalLayout cManUserSubMenu = new HorizontalLayout();

		arrLSubTabs.add(cManUserSubMenu);

		String[] arrSessions = new String[] { ManageUserModule.SESSION_UMANAGE,
				SESSION_UDM_TABLE, SESSION_UDM_IS_LOG };

		btnPersonal.addClickListener(new BtnTabLikeClickListener(false, false,
				arrLSubTabs, arrLTabBtns, null, null, "account_change_log",
				"001", hasOp, boolEditStatus, arrSessions, new String[] {
						ManageUserModule.SESSION_VAR_UMANAGE_USER_ACTIONS,
						SESSION_VAR_UDM_PER, null }));

		btnAccount.addClickListener(new BtnTabLikeClickListener(false, false,
				arrLSubTabs, arrLTabBtns, null, null, "account_change_log",
				"001", hasOp, boolEditStatus, arrSessions, new String[] {
						ManageUserModule.SESSION_VAR_UMANAGE_USER_ACTIONS,
						SESSION_VAR_UDM_ACC, null }));

		btnAuth.addClickListener(new BtnTabLikeClickListener(false, false,
				arrLSubTabs, arrLTabBtns, null, null, "account_change_log",
				"001", hasOp, boolEditStatus, arrSessions, new String[] {
						ManageUserModule.SESSION_VAR_UMANAGE_USER_ACTIONS,
						SESSION_VAR_UDM_AUTH, null }));

		cManageUserMenu.addComponent(cManageAndAddTab);
		cManageUserMenu.addComponent(cManUserSubMenu);

		String strManBtnPref = "man";

		cManUserSubMenu = getAddUserSubMenu(btnLog, strManBtnPref, arrLTabBtns,
				cManUserSubMenu, arrLSubTabs, hasOp, boolEditStatus, null,
				null, null);
		cManUserSubMenu.setSizeUndefined();

		BtnTabLike.btnPrevMenuState = BtnTabLike.btnTabCur;

		return cManageUserMenu;

	}

	private HorizontalLayout getAddUserSubMenu(BtnTabLike btnAddUser,
			String strBtnPref, ArrayList<BtnTabLike> arrLTabBtns,
			HorizontalLayout cAddUserSubMenu,
			ArrayList<HorizontalLayout> arrLAddUserSubTabs, boolean hasOp,
			boolean boolEditStatus, Object aum, String strSessionVar,
			String strSessionSub) {

		cAddUserSubMenu.setStyleName("c_sub_menu_invisible");
		cAddUserSubMenu.setSizeUndefined();

		BtnTabLike btnAct = new BtnTabLike("Account Activity Log", null);

		BtnTabLike btnAcc = new BtnTabLike("Account Change Log", null);

		btnAct.setStyleName("btn_tab_like btn_tab_like_active");
		btnAct.setEnabled(false);
		BtnTabLike.btnTabCur = btnAct;

		cAddUserSubMenu.addComponent(btnAct);
		cAddUserSubMenu.addComponent(btnAcc);

		final ArrayList<BtnTabLike> arrLSubTabBtns = new ArrayList<BtnTabLike>();
		arrLSubTabBtns.add(btnAct);
		arrLSubTabBtns.add(btnAcc);

		/*
		 * String[] arrSessions = new String[]{
		 * ManageUserModule.SESSION_UMANAGE, SESSION_UDM_TABLE, SESSION_UDM_LOG
		 * };
		 */

		String[] arrSessions = new String[] { ManageUserModule.SESSION_UMANAGE,
				SESSION_UDM_TABLE_LOG, SESSION_UDM_IS_LOG };

		btnAct.addClickListener(new BtnTabLikeClickListener(false, false,
				arrLAddUserSubTabs, arrLSubTabBtns, null, aum,
				"account_change_log", "001", hasOp, boolEditStatus,
				arrSessions, new String[] {
						ManageUserModule.SESSION_VAR_UMANAGE_USER_ACTIONS,
						SESSION_VAR_UDM_ACT_LOG, SESSION_VAR_UDM_IS_LOG_TRUE }));

		btnAcc.addClickListener(new BtnTabLikeClickListener(false, false,
				arrLAddUserSubTabs, arrLSubTabBtns, null, aum,
				"account_change_log", "001", hasOp, boolEditStatus,
				arrSessions, new String[] {
						ManageUserModule.SESSION_VAR_UMANAGE_USER_ACTIONS,
						SESSION_VAR_UDM_ACC_LOG, SESSION_VAR_UDM_IS_LOG_TRUE }));

		btnAddUser.addClickListener(new BtnTabLikeClickListener(false, true,
				arrLAddUserSubTabs, arrLTabBtns, cAddUserSubMenu, aum,
				"activity_log", "001", hasOp, boolEditStatus, new String[] {
						ManageUserModule.SESSION_UMANAGE, SESSION_UDM_IS_LOG },
				new String[] {
						ManageUserModule.SESSION_VAR_UMANAGE_USER_ACTIONS,
						SESSION_VAR_UDM_IS_LOG_TRUE }));

		UI.getCurrent().getSession()
				.setAttribute(SESSION_UDM_TABLE_LOG, SESSION_VAR_UDM_ACT_LOG);

		return cAddUserSubMenu;
	}

	public VerticalLayout udmModifier(HorizontalLayout contentC,
			UserDetailsModule udm) {

		String strUDM = (String) UI.getCurrent().getSession()
				.getAttribute(SESSION_UDM);
		String strTbName = (String) UI.getCurrent().getSession()
				.getAttribute(SESSION_UDM_TABLE);
		// Notification.show(strTbName, Notification.Type.ERROR_MESSAGE);
		String strUID = (String) UI.getCurrent().getSession()
				.getAttribute(SESSION_UDM_ID);
		UI.getCurrent().getSession().getAttribute(SESSION_UDM_UNAME);

		String strAction = (String) UI.getCurrent().getSession()
				.getAttribute(SESSION_UDM_ACTION_EDIT_DETAILS);
		boolean hasOp = false;

		if (strTbName.equals("account") || strTbName.equals("auth")) {
			hasOp = true;
		} else {
			hasOp = false;
		}

		if (strUDM == null) {
			cUDetails = udm.getUserDetailsContainer(strTbName, strUID, hasOp,
					strAction.equals("edit"));
			contentC.addComponent(cUDetails);
			contentC.setComponentAlignment(cUDetails, Alignment.TOP_CENTER);
			contentC.setExpandRatio(cUDetails, 1.0f);
			contentC.setSizeFull();
			contentC.setMargin(new MarginInfo(true, false, true, false));
			contentC.setSpacing(false);
		} else {
			if (UI.getCurrent().getSession().getAttribute(SESSION_UDM_IS_LOG) != null) {
				strTbName = (String) UI.getCurrent().getSession()
						.getAttribute(SESSION_UDM_TABLE_LOG);
			}
			udm.cPerAccAuthInfo.removeAllComponents();

			udm.cPerAccAuthInfo.addComponent(udm.getDetailsForm(strTbName,
					strUID, hasOp, strAction.equals("edit")));

		}

		return cUDetails;

	}

	private void setData(String strTbName, String strUID, String strCurUser,
			List<Object> arrLAllFormFields, List<Object> arrLAllEditableFields,
			ArrayList<String> arrLTfEditableVals,
			ArrayList<String> arrLOptEditableVals,
			ArrayList<String> arrLComboEditableVals,
			ArrayList<String> arrLDfEditableVals,
			HashMap<String, Object> mapSlaveFields) {
		if (!strTbName.equals("personal")) {
			int isReadOnlyTf = 0;
			int isReadOnlyOpt = 1;
			int isReadOnlyCombo = -1;

			// set TextField(tf) form objects
			if (arrTfVals != null) {
				if (strCurUser != null) {
					String strCurUserAction = (String) UI
							.getCurrent()
							.getSession()
							.getAttribute(
									WorkSpaceManageProfile.SESSION_WSMP_CUR_ACTION);
					if (strCurUserAction
							.equals(WorkSpaceManageProfile.SESSION_VAR_WSMP_AUTH)) {
						setCurUserTfs(isReadOnlyTf, arrLAllFormFields,
								arrLAllEditableFields, arrLTfEditableVals);
					} else {
						setTfs(isReadOnlyTf, arrLAllFormFields,
								arrLAllEditableFields, arrLTfEditableVals);
					}

				} else {
					setTfs(isReadOnlyTf, arrLAllFormFields,
							arrLAllEditableFields, arrLTfEditableVals);
				}
			}

			// Set OptionGroup(opt) form objects
			if (arrOptVals != null) {

				setOpts(strTbName, strUID, isReadOnlyOpt, arrLAllFormFields,
						arrLAllEditableFields, arrLOptEditableVals,
						mapSlaveFields);

			}
			// Set ComboBox(combo) form objects
			if (arrComboVals != null) {
				setCombos(strTbName, strUID, isReadOnlyCombo,
						arrLAllFormFields, arrLAllEditableFields,
						arrLComboEditableVals, mapSlaveFields);
			} //
				// Set InlineDateField(dF) form objects
			if (arrDfVals != null) {
				setDfs(isReadOnlyCombo, arrLAllFormFields,
						arrLAllEditableFields, arrLDfEditableVals);
			}

			return;
		}
		if (boolEditStatus)
			cUPersonalDetails.addComponent(getEUDContainer());
		else
			cUPersonalDetails.addComponent(getUDContainer());

	}

	private VerticalLayout getUDContainer() {

		String strProf = "MATS_ADMIN_USER_PROFILE";

		VerticalLayout cAgentInfo = new VerticalLayout();

		VerticalLayout cBasic = new VerticalLayout();
		cBasic.setSpacing(true);
		// cBasic.setSpacing(true);
		Label lbB = new Label("General");
		lbB.setStyleName("lb_frm_add_user");
		cBasic.addComponent(lbB);

		TextField tF = new TextField("First Name");
		tFFN = tF;
		tFFN.setRequired(true);
		tF.setValue("Paul");

		addDatum("Username", "Livepwndz", cBasic);
		addDatum("Profile", strProf, cBasic);

		addDatum("Full Name", "Mr. Paul Pwndz Kigozi", cBasic);
		addDatum("First Name", "Paul", cBasic);

		// cBasic.addComponent(tF);

		tF = new TextField("Middle Name");

		tF.setValue("Pwndz");
		tFMN = tF;
		tFMN.setRequired(true);
		addDatum("Middle Name", "Pwndz", cBasic);
		// cBasic.addComponent(lb);

		tF = new TextField("Last Name");
		tF.setValue("Kigozi");
		tFLN = tF;
		tFLN.setRequired(true);

		addDatum("Last Name", "Kigozi", cBasic);

		OptionGroup opt = new OptionGroup("Gender");

		opt.addItem("FEMALE");
		// opt.setItemCaption(1, "Female");

		opt.addItem("MALE");
		// opt.setItemCaption(2, "Male");
		opt.select("MALE");
		optSex = opt;
		optSex.setRequired(true);

		addDatum("Gender", "Male", cBasic);

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

		addDatum("Occupation", "Software Engineer", cBasic);

		// cBasic.addComponent(lb);
		// cBasic.addComponent(tF);

		tF = new TextField("Employer");
		tF.setValue("Swifta");
		tFEmp = tF;

		addDatum("Employer", "Swifta", cBasic);

		// cBasic.addComponent(lb);
		// cBasic.addComponent(tF);

		PopupDateField dF = new PopupDateField("DoB");
		cal = Calendar.getInstance();
		cal.set(1988, 11, 12);
		dF.setValue(cal.getTime());
		dFDoB = dF;

		addDatum("DoB", "12/11/1988", cBasic);
		// cBasic.addComponent(dF);

		combo = new ComboBox("Country");

		comboCountry = combo;
		comboCountry.setRequired(true);

		// cBasic.addComponent(combo);
		addDatum("Country", "Uganda", cBasic);

		combo = new ComboBox("State");
		comboState = combo;
		comboState.setRequired(true);
		comboState.setNullSelectionAllowed(false);

		// cBasic.addComponent(combo);
		addDatum("State", "Central", cBasic);

		combo = new ComboBox("Local Government");

		// combo.addItem(1);
		// combo.setItemCaption(1, "Ca. LG");
		// combo.select(1);
		comboLG = combo;
		comboLG.setRequired(true);

		// cBasic.addComponent(lb);

		addDatum("Local Government", "KCCA", cBasic);

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
		cCompany.setSpacing(true);

		addDatum("ID Type", "Passport Number", cCompany);

		tF = new TextField("ID No.");
		tF.setValue("001");
		tFIDNo = tF;
		tFIDNo.setRequired(true);

		addDatum("ID No.", "001", cCompany);
		// cCompany.addComponent(lb);

		tF = new TextField("Issuer");
		tFIssuer = tF;
		tFIssuer.setValue("Republic of Uganda");

		// cCompany.addComponent(tF);
		addDatum("Issuer", "Republic of Uganda", cCompany);

		dF = new PopupDateField("Issue Date");
		// cal = Calendar.getInstance();
		cal.set(12, 12, 12);
		dF.setValue(cal.getTime());
		dFDoI = dF;
		// cCompany.addComponent(dF);

		addDatum("Issue Date", "12/12/12", cCompany);

		dF = new PopupDateField("Expiry Date");
		// cal = Calendar.getInstance();
		cal.set(14, 12, 12);
		dF.setValue(cal.getTime());
		dF.setValue(cal.getTime());
		dFDoE = dF;
		dFDoE.setRequired(true);
		dFDoE.setImmediate(true);

		// cCompany.addComponent(dF);
		addDatum("Expirty Date", "12/12/16", cCompany);
		cC.addComponent(cCompany);

		VerticalLayout pC = new VerticalLayout();
		pC.setSpacing(true);
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
		addDatum("Mobile Phone No.", "+256704191152", pC);

		tF = new TextField("Alt. Phone No.");
		tF.setValue("+1704191152");
		tFPANo = tF;
		// pC.addComponent(tF);

		addDatum("Alt. Phone No.", "+1704191152", pC);

		tF = new TextField("Email Address");
		tF.setValue("pwndz172@gmail.com");
		tFPEmail = tF;
		// pC.addComponent(tF);

		addDatum("Email Address", "pwndz172@gmail.com", pC);

		cC.addComponent(pC);

		VerticalLayout sC = new VerticalLayout();
		sC.setSpacing(true);
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

		addDatum("Mobile Phone No.", "+256704191152", sC);

		tF = new TextField("Alt. Phone No.");
		tF.setValue("+1804191152");
		tFSANo = tF;
		// sC.addComponent(tF);
		addDatum("Alt. Phone No.", "+1704191152", sC);

		tF = new TextField("E-mail Address");
		tF.setValue("pkigozi@swifta.com");
		tFSEmail = tF;
		// sC.addComponent(tF);

		addDatum("Email Address", "pkigozi@swifta.com", sC);

		cC.addComponent(sC);

		VerticalLayout physicalC = new VerticalLayout();
		physicalC.setSpacing(true);
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

		addDatum("Street", "Yusuf Lule Rd.", physicalC);

		tF = new TextField("Postal Code");
		tF.setValue("23");
		tFPostalCode = tF;
		// physicalC.addComponent(tF);

		addDatum("Postal Code", "23", physicalC);

		tF = new TextField("City");
		tF.setValue("Kampala");
		tFCity = tF;
		tFCity.setRequired(true);
		// physicalC.addComponent(tF);
		addDatum("City", "Kampala", physicalC);

		tF = new TextField("Province");
		tF.setValue("Central");
		tFProv = tF;
		// physicalC.addComponent(tF);

		addDatum("Province", "Central", physicalC);

		cC.addComponent(physicalC);

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
		cAgentInfo.setMargin(new MarginInfo(true, false, false, false));

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
							Notification.show("Hello");
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
				resetForm(arrLAllEditableFields, arrLTfEditableVals,
						arrLOptEditableVals, arrLComboEditableVals, null,
						arrLTfEditableVals);
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

			Connection conn = DriverManager
					.getConnection(
							"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasourcetest",
							Uname, Pword);

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

			Connection conn = DriverManager
					.getConnection(
							"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasourcetest",
							Uname, Pword);

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
