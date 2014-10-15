package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractComponent;
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
import com.vaadin.ui.Button.ClickEvent;
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
					List<Button> arrLOpBtns = new ArrayList<Button>();
					
					/*TODO
					 * 
					 * Fetched data
					 *
					 *
					 */
					
					//Set Data
					
					if(strTbName.equals("account") || strTbName.equals("auth")){
						hasOp = true;
						
					}else{
						hasOp = false;
					}
					
					
					
					
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
					
					//Holders of editable form components original values
					final ArrayList<String> arrLTfEditableVals = new ArrayList<String>();
					final ArrayList<String> arrLOptEditableVals = new ArrayList<String>();
					final ArrayList<String> arrLComboEditableVals = new ArrayList<String>();
					final ArrayList<String> arrLDfEditableVals = new ArrayList<String>();
					
					
					
					
					
					//For testing purposes, we assume that every first item of each for category is read-only, hence the isReadOnly = 0
					int isReadOnlyTf = 0;
					int isReadOnlyOpt = 1;
					int isReadOnlyCombo = -1;
					
					//set TextField(tf) form objects
					if(arrTfVals != null){
						setTfs(isReadOnlyTf, arrLAllFormFields, arrLAllEditableFields, arrLTfEditableVals);
					}
					
					//Set OptionGroup(opt) form objects
					if(arrOptVals != null){
						
						setOpts(strTbName, strUID, isReadOnlyOpt, arrLAllFormFields,arrLAllEditableFields,arrLOptEditableVals, arrLOpBtns);
					}
					//Set ComboBox(combo) form objects
					if(arrComboVals != null){
						setCombos(strTbName, strUID,isReadOnlyCombo,arrLAllFormFields,arrLAllEditableFields,arrLComboEditableVals, arrLOpBtns);
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
					
					
					
					
					if(hasOp){
						cDetailsAndOperations.addComponent(getOperationsContainer(strTbName, strUID, arrLOpBtns));
						ecbsf = new EditCancelBtnsSingleField(cUPersonalDetails);
						
					}else{
						wrapperEditCancelBtnsClickListener(btnEdit, btnCancel, btnEditId, btnSaveId, arrLAllEditableFields, cBtnEditCancel, arrLTfEditableVals, 
								arrLOptEditableVals, arrLComboEditableVals, arrLDfEditableVals);
						cUPersonalDetails.addComponent(cBtnEditCancel);
					}
					
					
					
							
					
					
					/*
					 * 
					 * If edit status is turn on, initiate editUserDetails
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
		int iDf = 0;
		uDetailsEditStatus = false;
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
			tfGen.setWidth("100%");
			arrLAllFormFields.add(tfGen);
			cUPersonalDetails.addComponent(tfGen);
			//System.out.println(boolEditStatus);
			
			if(iTf != isReadOnlyTf){
				arrLTfEditable.add(tfGen);
				arrLTfEditableVals.add(arrTfVals[iTf]);
			}
		}
		
	}
	
	private void setOpts(String strTbName, String strUID, int isReadOnlyOpt, List<Object>lAllFormFields,
			List<Object>arrLOptEditable, ArrayList<String>arrLOptEditableVals, List<Button> arrLOpBtns){
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
				Button btn = getOpBtn(strTbName,strUID, opt, "Deactivate", null);
				arrLOpBtns.add(btn);
				strOptSEditableFieldVal = arrOptVals[iOpt];
				/*if(strOptSEditableFieldVal.equals("Active")){
					opt.setValue(true);
				}else if(strOptSEditableFieldVal.equals("Deactive")){
					opt.setValue(false);
				}*/
				optSEditableField = opt;
				
				
			}
			
			cUPersonalDetails.addComponent(opt);
		}
	}
	
	private void setCombos(String strTbName, String strUID,int isReadOnlyCombo, List<Object>lAllFormFields,
			List<Object>arrLComboEditable, ArrayList<String>arrLComboEditableVals, List<Button> arrLOpBtns){
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
	
	
	
	private VerticalLayout getOperationsContainer(String strTbName, final String strUID, List<Button> arrLOpBtns){
		
		VerticalLayout cOp = new VerticalLayout();
		cOp.setSizeUndefined();
		cOp.setSpacing(true);
		cOp.setMargin(true);
		Label lbOp;
		
		
		
		
		
		if(strTbName.equals("auth")){
			lbOp = new Label("Authentication Operations");	
			cOp.addComponent(lbOp);
		}else if(strTbName.equals("account")){
			
			lbOp = new Label("Account Operations");	
			cOp.addComponent(lbOp);
			
		}
		
		/*for(Button btn: arrLOpBtns){
			cOp.addComponent(btn);
		}*/
		
		if(strTbName.equals("auth")){
			Button btnPassReset = new Button("Trigger Password Reset");
			cOp.addComponent(btnPassReset);
			btnPassReset.addClickListener(new Button.ClickListener() {
				
				
				private static final long serialVersionUID = -5995226525541204395L;

				@Override
				public void buttonClick(ClickEvent event) {
					/*
					 * 
					 * TODO Send password reset URL to user.
					 * 
					 */
					Notification.show("Password reset has been triggered for user of ID "+strUID);
					
				}
			});
		}
		
		if(strTbName.equals("account")){
					final Button btnStatus = new Button();
					
					
					
					final Button btnChangeType = new Button("Change Type");
					
					if(optSEditableField.getValue().equals("Active")){
						btnStatus.setCaption("Deactivate");
					}else if(optSEditableField.getValue().equals("Deactivate")){
						btnStatus.setCaption("Activate");
						btnChangeType.setEnabled(false);
					}
					
					
					cOp.addComponent(btnStatus);
					cOp.addComponent(btnChangeType);
					
						btnStatus.addClickListener(new Button.ClickListener() {
						
						private static final long serialVersionUID = -6318339408802346085L;
						private String strNewStatus;
						private String strOldStatus;
						@Override
						public void buttonClick(ClickEvent event) {
							
							if(optSEditableField.getValue().equals("Active")){
								strNewStatus = "Deactive";
								strOldStatus = "Active";
								optSEditableField.setEnabled(true);
								optSEditableField.setReadOnly(false);
								
								optSEditableField.setValue(false);
								optSEditableField.removeItem(strOldStatus );
								optSEditableField.addItem(strNewStatus);
								optSEditableField.select(strNewStatus);
								
								optSEditableField.setEnabled(false);
								optSEditableField.setReadOnly(true);
								btnChangeType.setEnabled(false);
								
								btnStatus.setCaption("Activate");
							 
							}else if(optSEditableField.getValue().equals("Deactive")){
									strNewStatus = "Active";
									strOldStatus = "Deactive";
									
									optSEditableField.setEnabled(true);
									optSEditableField.setReadOnly(false);
									
									optSEditableField.setValue(false);
									optSEditableField.removeItem(strOldStatus);
									optSEditableField.addItem(strNewStatus);
									optSEditableField.select(strNewStatus);
									
									optSEditableField.setEnabled(false);
									optSEditableField.setReadOnly(true);
									btnStatus.setCaption("Deactivate");
									btnChangeType.setEnabled(true);
								}
							
							
							/*
							 * 
							 * TODO Commit changes to the server.
							 * 
							 */
							Notification.show("Account status of user of ID "+strUID+" has been changed to "+strNewStatus.toUpperCase());
						}
					});
					btnChangeType.addClickListener(new Button.ClickListener() {
						
						private static final long serialVersionUID = -5995226525541204395L;
			
						@Override
						public void buttonClick(ClickEvent event) {
							enableEditableFormFields(arrLComboSEditableField);
							
						}
					});
					
					
		}
		
		
		return cOp;
	}
	
	public Button getOpBtn(String strTbName, String strUID, Object objSlaveField, String strAction, List<Button> arrLSlaveBtn){
		final String strBtnID = "opBtn_"+strTbName+"_"+strUID+"_"+strAction;
		Button btnAct = new Button(strAction);
		btnAct.setId(strBtnID);
		//btnAct.addClickListener(new BtnOpClickListener(objSlaveField, arrLSlaveBtn));
		return btnAct;
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
		
		String strBtnIDEdit = "btn_account_status_edit";
		final Button btnEditS = new Button();
		final Button btnCancel = new Button();
		final HorizontalLayout cBtnEditCancel = new HorizontalLayout();
		
		
		public EditCancelBtnsSingleField(FormLayout c){
			btnEditS.setId(strBtnIDEdit );
			btnEditS.setIcon(FontAwesome.EDIT);
			btnEditS.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
			btnEditS.setStyleName("btn_link");
			btnEditS.setVisible(false);
			btnEditS.setEnabled(false);
			
			
			btnCancel.setId(strBtnIDEdit);
			btnCancel.setIcon(FontAwesome.UNDO);
			btnCancel.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
			btnCancel.setStyleName("btn_link");
			btnCancel.setVisible(false);
			btnCancel.setEnabled(false);
			
			
			
			cBtnEditCancel.setSizeUndefined();
			cBtnEditCancel.addComponent(btnEditS);
			cBtnEditCancel.addComponent(btnCancel);
			c.addComponent(cBtnEditCancel);
			
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
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
