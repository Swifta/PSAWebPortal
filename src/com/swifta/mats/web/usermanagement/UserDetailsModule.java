package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.swifta.mats.web.accountprofile.ManageProfileModule;
import com.swifta.mats.web.accountprofile.WorkSpaceManageProfile;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class UserDetailsModule{
	public static boolean uDetailsEditStatus = false;
	FormLayout  cUPersonalDetails;
	HorizontalLayout cDetailsAndOperations;
	//Declaration of form fields
	TextField tfGen;
	OptionGroup opt;
	ComboBox combo;
	PopupDateField dF;
	
	
	
	//Declaration of data containers...
	
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
	
	
	public UserDetailsModule(){
		
	}
	
	public HorizontalLayout getDetailsForm(String strTbName, String strUID, boolean hasOp, boolean boolEditStatus){
		return setDetailsForm(getUDetails(strTbName, strUID), hasOp,  boolEditStatus);
	}
	
	private HorizontalLayout setDetailsForm(Map<String, String[]> mappedData, boolean hasOp, boolean boolEditStatus){
		cDetailsAndOperations = new HorizontalLayout();
		cDetailsAndOperations.setSizeFull();
		
		cUPersonalDetails = new FormLayout();
		cUPersonalDetails.setMargin(true);
		cUPersonalDetails.setSpacing(false);
		cUPersonalDetails.setStyleName("frm_details_personal_info");
		cUPersonalDetails.setSizeFull();
		cDetailsAndOperations.addComponent(cUPersonalDetails);
		
		
			
			if(mappedData.size() == 0){
				cUPersonalDetails.addComponent(new Label(" NO Data Available!"));
				uDetailsEditStatus = false;
			}else{
				
				String strTbName = mappedData.get("arrTbName")[0];
				String strUID = mappedData.get("arrUID")[0];
				if(strTbName.equals("activity_log") || strTbName.equals("account_change_log")){
					//TODO fetch specified table log
					
				}else{
					
					
					List<Object> arrLAllFormFields = new ArrayList<Object>();
					HashMap<String, Object> mapSlaveFields = new HashMap<String,Object>();
					
					/*TODO
					 * 
					 * Fetched data
					 *
					 *
					 */
					
					//Set Data
					
					
					arrTfCaptions = mappedData.get("arrTfCaptions");
					arrOptCaptions = mappedData.get("arrOptCaptions");
					arrComboCaptions = mappedData.get("arrComboCaptions");
					arrDfCaptions = mappedData.get("arrDfCaptions");
					
					arrTfVals = mappedData.get("arrTfVals");
					arrOptVals = mappedData.get("arrOptVals");
					arrComboVals = mappedData.get("arrComboVals");
					arrDfVals = mappedData.get("arrDfVals");
					
					
					final String btnEditId = strUID+"_edit";
					final String btnSaveId = strUID+"_save";
					
					//Holders of editable form components
					
					final List<Object> arrLAllEditableFields = new ArrayList<Object>();
					
					//Holders of editable form components original values to be used for undoing/resetting the form.
					final ArrayList<String> arrLTfEditableVals = new ArrayList<String>();
					final ArrayList<String> arrLOptEditableVals = new ArrayList<String>();
					final ArrayList<String> arrLComboEditableVals = new ArrayList<String>();
					final ArrayList<String> arrLDfEditableVals = new ArrayList<String>();
					
					
					
					
					
					String strCurUser  = (String) UI.getCurrent().getSession().getAttribute(WorkSpaceManageProfile.SESSION_WSMP);
					//Notification.show(strCurUserType+" +++++++");
					//For testing purposes, we assume that every first item of each for category is read-only, hence the isReadOnly = 0
					int isReadOnlyTf = 0;
					int isReadOnlyOpt = 1;
					int isReadOnlyCombo = -1;
					
					//set TextField(tf) form objects
					if(arrTfVals != null){
						if(strCurUser != null){
							String strCurUserAction  = (String) UI.getCurrent().getSession().getAttribute(WorkSpaceManageProfile.SESSION_WSMP_CUR_ACTION);
							if(strCurUserAction.equals(WorkSpaceManageProfile.SESSION_VAR_WSMP_AUTH)){
								setCurUserTfs(isReadOnlyTf, arrLAllFormFields, arrLAllEditableFields, arrLTfEditableVals);
							}else{
								setTfs(isReadOnlyTf, arrLAllFormFields, arrLAllEditableFields, arrLTfEditableVals);
							}
										
						}else{
							setTfs(isReadOnlyTf, arrLAllFormFields, arrLAllEditableFields, arrLTfEditableVals);
						}
					}
					
					//Set OptionGroup(opt) form objects
					if(arrOptVals != null){
							
							setOpts(strTbName, strUID, isReadOnlyOpt, arrLAllFormFields,arrLAllEditableFields,arrLOptEditableVals, mapSlaveFields);
						
								
					}
					//Set ComboBox(combo) form objects
					if(arrComboVals != null){
						setCombos(strTbName, strUID,isReadOnlyCombo,arrLAllFormFields,arrLAllEditableFields,arrLComboEditableVals, mapSlaveFields);
					}
					//Set InlineDateField(dF) form objects
					if(arrDfVals != null){
						setDfs(isReadOnlyCombo, arrLAllFormFields, arrLAllEditableFields, arrLDfEditableVals);
					}
				
					
					
					
					
					
					
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
					
					final HorizontalLayout cBtnEditCancel = new HorizontalLayout();
					cBtnEditCancel.setSizeUndefined();
					cBtnEditCancel.addComponent(btnEdit);
					
					
					String strCurUserAction  = (String) UI.getCurrent().getSession().getAttribute(WorkSpaceManageProfile.SESSION_WSMP_CUR_ACTION);
					if(strCurUserAction != null){
						if(strCurUserAction.equals(WorkSpaceManageProfile.SESSION_VAR_WSMP_AUTH)){
							btnEdit.setVisible(false);
						}
					}
					
					if(hasOp){
						//Notification.show(Integer.toString(arrLOpBtns.size()));
						ecbsf = new EditCancelBtnsSingleField(cUPersonalDetails, false);
						cDetailsAndOperations.addComponent(getOpForm(strTbName, strUID,mapSlaveFields));
						btnEdit.setVisible(false);
						btnEdit.setEnabled(false);
						ecbsf.btnEditS.setVisible(false);
						
					}else{
						wrapperEditCancelBtnsClickListener(btnEdit, btnCancel, btnEditId, btnSaveId, arrLAllEditableFields, cBtnEditCancel, arrLTfEditableVals, 
								arrLOptEditableVals, arrLComboEditableVals, arrLDfEditableVals);
						cUPersonalDetails.addComponent(cBtnEditCancel);
					}
					
					
					
							
					
					
					/*
					 * 
					 * If edit status is true and has no operations, initiate editUserDetails
					 * 
					 */
					if(boolEditStatus && !hasOp){
						editUserDetails(arrLAllEditableFields, btnEdit, btnCancel, btnSaveId, cBtnEditCancel);
					}
					
					
					
				
				}
				
				
			}
			
		return cDetailsAndOperations;
	}
	
	
	
	
	
	
	private void resetForm(List<Object> lAllEditableFields, ArrayList<String> arrLTfVals,  ArrayList<String> arrLOptVals, ArrayList<String> arrLComboSelVals, ArrayList<String>arrLDfVals){
		
		int iTf = 0;
		int iOpt = 0;
		int iCombo = 0;
		uDetailsEditStatus = false;
		//Notification.show(Integer.toString(arrTfVals.length)+" I have");
		for(Object f: lAllEditableFields){
			if(f instanceof TextField){
					
					
				((TextField) f).setValue(arrLTfVals.get(iTf));
				((TextField) f).setStyleName(ValoTheme.BUTTON_BORDERLESS);
				((TextField) f).setReadOnly(true);
				iTf++;
				
			}else if(f instanceof OptionGroup){
				
					((OptionGroup) f).setValue(arrLOptVals.get(iOpt));
					((OptionGroup) f).setReadOnly(true);
					iOpt++;
			
			}else if(f instanceof ComboBox){
				
					((ComboBox) f).setValue(arrLComboSelVals.get(iCombo));
					((ComboBox) f).setReadOnly(true);
					iCombo++;
				
			}else if(f instanceof PopupDateField){
					
					//((PopupDateField) f).setValue(new Date(arrLDfVals.get(iDf)));
					((PopupDateField) f).setReadOnly(true);
				
			}
			
		}
			
			
			
						
	}
	
	
			
	private void disableEditableFields(List<Object> arrLAllEditableFields){
		uDetailsEditStatus = false;
		for(Object f: arrLAllEditableFields){
			if(f instanceof TextField){
				((TextField) f).setStyleName(ValoTheme.BUTTON_BORDERLESS);
			((TextField) f).setReadOnly(true);
			}else if(f instanceof OptionGroup){
				((OptionGroup) f).setReadOnly(true);
			}else if(f instanceof ComboBox){
				((ComboBox) f).setReadOnly(true);
			}else if(f instanceof PopupDateField){
				((PopupDateField) f).setReadOnly(true);
			}
			
		}
		
		
	}
	
	private void enableEditableFormFields(List<Object> arrLAllEditableFields){
			uDetailsEditStatus = true;
			for(Object f: arrLAllEditableFields){
				if(f instanceof TextField){
					((TextField) f).removeStyleName(ValoTheme.BUTTON_BORDERLESS);
				((TextField) f).setReadOnly(false);
				}else if(f instanceof OptionGroup){
					((OptionGroup) f).setReadOnly(false);
					
					//TODO Values below are dummy. Need to fetch applicable field values for system users to use when editing.
					((OptionGroup) f).addItem("Female");
					((OptionGroup) f).addItem("Male");
					
				}else if(f instanceof ComboBox){
					((ComboBox) f).setReadOnly(false);
					//TODO Values below are dummy. Need to fetch applicable field value for system users to choose
					((ComboBox) f).addItem("Finance Controller");
					((ComboBox) f).addItem("Customer Care Operator");
				}else if(f instanceof PopupDateField){
					((PopupDateField) f).setReadOnly(false);
				}
				
			}
			
						
	}
	
	
	public Map<String, String[]> getUDetails(String strTbName, String strUID ){
		BackEndEmulator bee = new BackEndEmulator();
		Map<String, String[]> mappedData = bee.getUserPersonalInfo(strTbName, strUID);
		return mappedData;
	}
	
	private void setTfs(int isReadOnlyTf, List<Object>arrLAllFormFields, List<Object>arrLTfEditable, ArrayList<String>arrLTfEditableVals){
		for(int iTf = 0; iTf < arrTfVals.length; iTf++){
			tfGen = new TextField();
			tfGen.setCaption(arrTfCaptions[iTf]);
			tfGen.setValue(arrTfVals[iTf]);
			tfGen.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
			tfGen.setReadOnly(true);
			tfGen.setDescription(arrTfVals[iTf]);
			tfGen.setWidth("100%");
			arrLAllFormFields.add(tfGen);
			
			cUPersonalDetails.addComponent(tfGen);
			if(iTf != isReadOnlyTf){
				arrLTfEditable.add(tfGen);
				arrLTfEditableVals.add(arrTfVals[iTf]);
			}
		}
		
	}
	
	private void setOpts(String strTbName, String strUID, int isReadOnlyOpt, List<Object>lAllFormFields,
			List<Object>arrLOptEditable, ArrayList<String>arrLOptEditableVals, HashMap<String, Object> mapSlaveFields){
		for(int iOpt = 0; iOpt < arrOptVals.length; iOpt++){
			opt = new OptionGroup();
			opt.setCaption(arrOptCaptions[iOpt]);
			opt.addItem(arrOptVals[iOpt]);
			opt.select(arrOptVals[iOpt]);
			opt.setReadOnly(true);
			opt.setId("opt_"+strTbName+"_"+strUID+"_"+arrOptCaptions[iOpt]);
			if(iOpt != isReadOnlyOpt){
				arrLOptEditable.add(opt);
				arrLOptEditableVals.add(arrOptVals[iOpt]);
			}
			if(strTbName.equals("account") && arrOptCaptions[iOpt].equals("Status")){
				strOptSEditableFieldVal = arrOptVals[iOpt];
				optSEditableField = opt;
				mapSlaveFields.put(arrOptCaptions[iOpt], opt);
			
				
			}
			
			cUPersonalDetails.addComponent(opt);
		}
	}
	
	private void setCombos(String strTbName, String strUID,int isReadOnlyCombo, List<Object>lAllFormFields,
			List<Object>arrLComboEditable, ArrayList<String>arrLComboEditableVals, HashMap<String,Object> mapSlaveFields){
		for(int iCombo = 0; iCombo < arrComboVals.length; iCombo++){
			combo = new ComboBox();
			combo.setCaption(arrComboCaptions[iCombo]);
			combo.addItem(arrComboVals[iCombo]);
			combo.select(arrComboVals[iCombo]);
			combo.setReadOnly(true);
			lAllFormFields.add(combo);
			if(iCombo != isReadOnlyCombo){
				arrLComboEditable.add(combo);
				arrLComboEditableVals.add(arrComboVals[iCombo]);
			}
			
			
			if(strTbName.equals("account") && arrComboCaptions[iCombo].equals("Type")){
				arrLComboSEditableField.add(combo);
				arrLComboSEditableFieldVal.add(arrComboVals[iCombo]);
				
				//addOpBtn(strTbName,strUID, opt, "Change Type", arrLOpBtns);
				mapSlaveFields.put(arrComboCaptions[iCombo], combo);
				
				combo.addValueChangeListener(new ValueChangeListener(){
					private static final long serialVersionUID = -2182355729919041184L;
					@Override
					public void valueChange(ValueChangeEvent event) {
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
	private void setDfs(int isReadOnlyCombo, List<Object>lAllFormFields, List<Object>arrLDfEditable, ArrayList<String>arrLDfEditableVals){
		for(int iDf= 0; iDf < arrDfVals.length; iDf++){
			dF = new PopupDateField();
			dF.setCaption(arrDfCaptions[iDf]);
			dF.setValue(new Date(arrDfVals[iDf]));
			dF.setReadOnly(true);
			lAllFormFields.add(dF);
			if(iDf != isReadOnlyCombo){
				arrLDfEditable.add(dF);
				arrLDfEditableVals.add(arrDfVals[iDf]);
			}
			cUPersonalDetails.addComponent(dF);
		}
	}
	
	private void editUserDetails(List<Object> arrLAllEditableFields,Button btnEdit, Button btnCancel,
			String btnSaveId, HorizontalLayout cBtnEditCancel){
		enableEditableFormFields(arrLAllEditableFields);
		/*By Default, btnCancel is not visible, until btnEdit is clicked.
		 * Only until then is it added and visible.
		 */
		
		if(!btnCancel.isVisible()){
			btnEdit.setId(btnSaveId);
			btnEdit.setIcon(FontAwesome.SAVE);
			btnCancel.setVisible(true);
			cBtnEditCancel.addComponent(btnCancel);	
		}
	}
	
	
	
	
	
	public FormLayout getOpForm(String strTbName, String strUID, HashMap<String, Object> mapSlaveFields){
		
		String strUserType = (String) UI.getCurrent().getSession().getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE);
		//Notification.show(strUserType);
		if(strUserType.equals(WorkSpaceManageUser.SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE)){
			return getAgentOpForm(strTbName, strUID, mapSlaveFields);
		}else{
			return new FormLayout();
		}
		
	}
	
	
	
	private void wrapperEditCancelBtnsClickListener(final Button btnEdit, final Button btnCancel, final String btnEditId, final String btnSaveId, final List<Object> arrLAllEditableFields, final HorizontalLayout cBtnEditCancel, final ArrayList<String> arrLTfEditableVals, 
			final ArrayList<String> arrLOptEditableVals, final ArrayList<String> arrLComboEditableVals, final ArrayList<String> arrLDfEditableVals){
		btnEdit.addClickListener(new Button.ClickListener() {
			
			
			private static final long serialVersionUID = -6544444429248747390L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				/*
				 * Prepare all Editable fields (Entire form) for editing.
				 */
				if(event.getButton().getId().equals(btnEditId)){
					enableEditableFormFields(arrLAllEditableFields);
					
					/*By Default, btnCancel is not visible, until btnEdit is clicked.
					 * Only until then is it added and visible.
					 */
					
					if(!btnCancel.isVisible()){
						event.getButton().setId(btnSaveId);
						event.getButton().setIcon(FontAwesome.SAVE);
						btnCancel.setVisible(true);
						cBtnEditCancel.addComponent(btnCancel);	
					}
					
					
					
				}else{
					if(event.getButton().getId().equals(btnSaveId)){
						/*
						 * 
						 * 
						 * 
						 * 
						 * TODO commit (save) changes i.e, send changes back to the server.
						 * 
						 * 
						 * 
						 * 
						 */
						
						//Remove undo button (btnCancel)
						btnCancel.setVisible(false);
						
						//Reset all Editable fields to readOnly after saving to the server
						disableEditableFields(arrLAllEditableFields);
						
						
						
						//Reset btnEdit id to btnIdEdit and caption(icon) to FontAwesome.EDIT
						btnEdit.setId(btnEditId);
						btnEdit.setIcon(FontAwesome.EDIT);
						
						//Reset Edit status to false
						uDetailsEditStatus = false;
						
					}
				}
				
			}
		});
		
		
		
		
		
		btnCancel.addClickListener(new Button.ClickListener() {
			
			
			private static final long serialVersionUID = 7719883177456399112L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				resetForm(arrLAllEditableFields,  arrLTfEditableVals, arrLOptEditableVals, arrLComboEditableVals,arrLDfEditableVals);
				btnEdit.setId(btnEditId);
				btnEdit.setIcon(FontAwesome.EDIT);
				btnCancel.setVisible(false);
				
				
			}
		});

	}
	
	

	
	
	private class EditCancelBtnsSingleField{
		
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
		
		
		public EditCancelBtnsSingleField(FormLayout c, boolean isInlineEdit){
			 this.c = c;
			 this.isInlineEdit = isInlineEdit;
			 addBtns();
			
		}
		
		public EditCancelBtnsSingleField(HorizontalLayout c, boolean isInlineEdit, ArrayList<Object> ef, ArrayList<String> arrLEfVal){
			 this.c = c;
			 this.isInlineEdit = isInlineEdit;
			 this.arrLEf = ef;
			 this.arrLEfVal = arrLEfVal;
			 addBtns();
			
		}
		
		private void addBtns(){
			
			btnEditS.setIcon(FontAwesome.EDIT);
			btnEditS.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
			btnEditS.setStyleName("btn_link btn_repositioned");
			//btnEditS.setVisible(false);
			//btnEditS.setEnabled(false);
			//btnEditS.setId(strE);
			
			
			
			btnCancel.setIcon(FontAwesome.UNDO);
			btnCancel.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
			btnCancel.setStyleName("btn_link btn_repositioned");
			btnCancel.setVisible(false);
			btnCancel.setEnabled(false);
			
			
			
			
			cBtnEditCancel.setSizeUndefined();
			cBtnEditCancel.addComponent(btnEditS);
			cBtnEditCancel.addComponent(btnCancel);
			if(c instanceof FormLayout){
				((FormLayout) c).addComponent(cBtnEditCancel);
			
			}else if(c instanceof HorizontalLayout){
				((HorizontalLayout) c).addComponent(cBtnEditCancel);
			}
			
			if(!isInlineEdit){
				btnEditS.addClickListener(new Button.ClickListener() {
					
					private static final long serialVersionUID = 7008276156596987435L;
	
					@Override
					public void buttonClick(ClickEvent event) {
						/*
						 * 
						 * TODO Commit changes to the server
						 *
						 **/
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
						
						
						resetForm(arrLComboSEditableField,  null, null, arrLComboSEditableFieldVal,null);	
						uDetailsEditStatus = false;
						btnCancel.setVisible(false);
						btnCancel.setEnabled(false);
						btnEditS.setVisible(false);
						btnEditS.setEnabled(false);
					}
				});
				
			}else if(isInlineEdit){
				    btnEditS.addClickListener(new Button.ClickListener() {
					private String strEID = null;
					
					private static final long serialVersionUID = 5258861566127416396L;

					@Override
					public void buttonClick(ClickEvent event) {
						
						strEID = btnEditS.getId();
						if(strEID.equals(strEPass) || strEID.equals(strEUname) ){
							
							if(strEID.equals(strEPass)){
								//btnEditS.setId(strSPass);
								showPasswordChangeWindow();
							}else if(strEID.equals(strEUname)){
								enableEditableFormFields(arrLEf);
								btnEditS.setId(strSUname);
								btnCancel.setVisible(true);
								btnCancel.setEnabled(true);
								btnEditS.setIcon(FontAwesome.SAVE);
							}
						}else if(strEID.equals(strSPass) || strEID.equals(strSUname) ){
							
							
							disableEditableFields(arrLEf);
							btnEditS.setIcon(FontAwesome.EDIT);
							btnCancel.setVisible(false);
							btnCancel.setEnabled(false);
							if(strEID.equals(strSPass)){
								btnEditS.setId(strEPass);
								
							}else if(strEID.equals(strSUname)){
								btnEditS.setId(strEUname);
							}
							
							/*
							 * TODO commit changes to server
							 */
							
						}
						
						/*if(btnEditS.getId().equals(strEPass) ){
							btnCancel.setVisible(true);
							btnCancel.setEnabled(true);
							btnEditS.setIcon(FontAwesome.SAVE);
							btnEditS.setId(strS);
						}else if(btnEditS.getId().equals(strS)){
							disableEditableFields(arrLEf);
							//btn_link btn_repositioned btn_link_invisible
							btnEditS.setIcon(FontAwesome.EDIT);
							btnEditS.setId(strE);
							btnCancel.setVisible(false);
							btnCancel.setEnabled(false);
							
						}*/
						
						
								
						
						
					}
				});
				
				btnCancel.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 7008276156596987435L;
					String strCID = null;
	
					@Override
					public void buttonClick(ClickEvent event) {
						
						strCID = btnCancel.getId();
						if(strCID.equals(strCPass) || strCID.equals(strCUname) ){
							resetForm(arrLEf,  arrLEfVal, null, null,null);	
							uDetailsEditStatus = false;
							btnEditS.setIcon(FontAwesome.EDIT);
							btnCancel.setVisible(false);
							btnCancel.setEnabled(false);
					
							if(btnEditS.getId().equals(strSPass)){
								btnEditS.setId(strEPass);
							}else if(btnEditS.getId().equals(strSUname)){
								btnEditS.setId(strEUname);
							}
						}
						
						
						/*resetForm(arrLEf,  arrLEfVal, null, null,null);	
						uDetailsEditStatus = false;
						btnCancel.setVisible(false);
						btnCancel.setEnabled(false);
						btnEditS.setIcon(FontAwesome.EDIT);
						btnEditS.setId(strE);*/
						
					}
				});
			}
		
		}
	
			
		
	}
	
	
	
	
	
	
	private class BtnOpClickListener implements Button.ClickListener{
		
		private static final long serialVersionUID = -8361209098934409391L;
		private Object objSlaveF;
		private Button btnSlave;
		
		
			public BtnOpClickListener(Object objSlaveF, Button btnSlave){
				this.objSlaveF= objSlaveF;
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
						if(strAction.equals("Activate")||strAction.equals("Deactivate")){
							OptionGroup opt = ((OptionGroup)objSlaveF);
							if(strAction.equals("Activate")){
								strNewStatus = "Active";
								strOldStatus = "Deactive";
								strNewAction = "Deactivate";
								
								opt.setEnabled(true);
								opt.setReadOnly(false);
								
								opt.setValue(false);
								opt.removeItem(strOldStatus );
								opt.addItem(strNewStatus);
								opt.select(strNewStatus);
								
								opt.setEnabled(false);
								opt.setReadOnly(true);
								btn.setCaption(strNewAction);
								btn.setId(strTbName+"_"+strUID+"_"+strNewAction);
								
									if(btnSlave != null)
										btnSlave.setEnabled(true);
							}else if(strAction.equals("Deactivate")){
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
								btn.setId(strTbName+"_"+strUID+"_"+strNewAction);
								if(btnSlave != null)
									btnSlave.setEnabled(false);
							}
								
								 //TODO Commit changes to the server.
								Notification.show("Account status of user of ID "+strUID+" has been changed to "+strNewStatus.toUpperCase());

						}else if(strAction.equals("Change Type")){
							ArrayList<Object> arrLEditableField = new ArrayList<Object>();
							arrLEditableField.add(combo);
							enableEditableFormFields(arrLEditableField);
							//Notification.show("Yeah!");
						}
						
						
									
											
								
						
						
					
					}
		
	}
	
	private FormLayout getAgentOpForm(String strTbName, String strUID, HashMap<String, Object>mapSlaveFields){
		
		FormLayout frmOps = new FormLayout();
		frmOps.setSizeUndefined();
		Label lbOp = new Label();
		
		
		if(strTbName.equals("account")){
			Button btnOpSt = new Button();
			Button btnOpTyp = new Button();
			
			lbOp.setValue("Account Operations");
			frmOps.addComponent(lbOp);
			String strAction;
			String strCurVal = null;
			
			OptionGroup opt = (OptionGroup) mapSlaveFields.get("Status");
			strCurVal = (String) opt.getValue();
			
			if(strCurVal.equals("Active"))
			{
				strAction = "Deactivate";
			}else{
				strAction = "Activate";
			}
			
			//Notification.show(strAction);
			
			btnOpSt.setCaption(strAction);
			btnOpSt.addClickListener(new BtnOpClickListener(opt, btnOpTyp));
			btnOpSt.setId(strTbName+"_"+strUID+"_"+ strAction);
			frmOps.addComponent(btnOpSt);
			
			strAction = "Change Type";
			//ComboBox combo = (ComboBox) mapSlaveFields.get("Type");
			btnOpTyp.setCaption(strAction);
			btnOpTyp.addClickListener(new BtnOpClickListener(opt, null));
			btnOpTyp.setId(strTbName+"_"+strUID+"_"+ strAction);
			frmOps.addComponent(btnOpTyp);
		}
		
		
		return frmOps;
		
	}
	
	private void showPasswordChangeWindow(){
		
			final Window popup = new Window("Change Password");
			popup.setIcon(FontAwesome.EDIT);
			popup.center();
			popup.setStyleName("w_change_pass");
			
			VerticalLayout cPopupMsg = new VerticalLayout();
			//cPopupMsg.setMargin(true);
			//cPopupMsg.setSpacing(true);
			
			
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
					 * TODO Verify current Password and new password.
					 * Save/indicate errors.
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
		
	
	
	private void setCurUserTfs(int isReadOnlyTf, List<Object>arrLAllFormFields, List<Object>arrLTfEditable, ArrayList<String>arrLTfEditableVals){
		VerticalLayout frm = new VerticalLayout();
		HorizontalLayout cTf;
		//EditCancelBtnsSingleField ecbsfInline;
		ArrayList<Object> arrLEf;
		ArrayList<String> arrLEfVal;
		String strEID = null;
		
		
		for(int iTf = 0; iTf < arrTfVals.length; iTf++){
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
			if(iTf != isReadOnlyTf){
				 arrLEf = new ArrayList<>();
				 arrLEfVal = new ArrayList<>();
				 arrLEf.add(tfGen);
				 arrLEfVal.add(arrTfVals[iTf]);
				 
				ecbsf = new EditCancelBtnsSingleField(cTf, true, arrLEf, arrLEfVal);
				ecbsf.btnEditS.setVisible(true);
				ecbsf.btnEditS.setEnabled(true);
				
				if(arrTfCaptions[iTf].equals("Username")){
					ecbsf.btnEditS.setId("cur_user_btn_id_edit_uname");
					ecbsf.btnCancel.setId("cur_user_btn_id_cancel_uname");
				}else if(arrTfCaptions[iTf].equals("Password")){
					ecbsf.btnEditS.setId("cur_user_btn_id_edit_pass");
					ecbsf.btnCancel.setId("cur_user_btn_id_cancel_pass");
				}
				
				
				arrLTfEditable.add(tfGen);
				arrLTfEditableVals.add(arrTfVals[iTf]);
			}
		}
		
		
		
	}

	
	
	
	
	
}
