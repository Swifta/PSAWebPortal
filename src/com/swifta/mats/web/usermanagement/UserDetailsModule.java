package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractComponent;
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
import com.vaadin.ui.themes.ValoTheme;

public class UserDetailsModule{
	public static boolean uDetailsEditStatus = false;
	FormLayout  cUPersonalDetails;
	//Declaration of form fields
	TextField tfGen;
	OptionGroup opt;
	ComboBox combo;
	PopupDateField dF;
	
	//Declaration of data containers...
	String strUID;
	String[] arrTfCaptions;
	String[] arrOptCaptions;
	String[] arrComboCaptions;
	String[] arrDfCaptions;
	
	String[] arrTfVals;
	String[] arrOptVals;
	String[] arrComboVals;
	String[] arrDfVals;
	
	
	public UserDetailsModule(){
		
	}
	
	public FormLayout getDetailsForm(String strTbName, String strUID, boolean boolEditStatus){
		return setDetailsForm(getUDetails(strTbName, strUID), boolEditStatus);
	}
	
	private FormLayout setDetailsForm(Map<String, String[]> mappedData, boolean boolEditStatus){
		
		
		cUPersonalDetails = new FormLayout();
		cUPersonalDetails.setMargin(true);
		cUPersonalDetails.setSpacing(false);
		cUPersonalDetails.setStyleName("frm_details_personal_info");
		cUPersonalDetails.setSizeFull();
			
			if(mappedData.size() == 0){
				cUPersonalDetails.addComponent(new Label(" NO Data Available!"));
				uDetailsEditStatus = false;
			}else{
				
				String strTbName = mappedData.get("arrTbName")[0];
				if(strTbName.equals("activity_log") || strTbName.equals("account_change_log")){
					//TODO fetch specified table log
					
				}else{
					TextField tfGen;
					OptionGroup opt;
					ComboBox combo;
					PopupDateField dF;
					
					List<Object> arrLAllFormFields = new ArrayList<Object>();
					
					/*TODO
					 * 
					 * Fetched data
					 *
					 *
					 */
					
					//Set Data
					
					
					strUID = mappedData.get("arrUID")[0];
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
					setTfs(isReadOnlyTf, arrLAllFormFields, arrLAllEditableFields, arrLTfEditableVals);
					
					//Set OptionGroup(opt) form objects
					setOpts(isReadOnlyOpt, arrLAllFormFields,arrLAllEditableFields,arrLOptEditableVals);
					
					//Set ComboBox(combo) form objects
					setCombos(isReadOnlyCombo,arrLAllFormFields,arrLAllEditableFields,arrLComboEditableVals);

					//Set InlineDateField(dF) form objects
					setDfs(isReadOnlyCombo, arrLAllFormFields, arrLAllEditableFields, arrLDfEditableVals);

					
				
					
					
					
					
					
					
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
					
					cUPersonalDetails.addComponent(cBtnEditCancel);
					
					
					
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
					
					
					
					/*
					 * 
					 * If edit status is turn on, initiate editUserDetails
					 * 
					 */
					if(boolEditStatus){
						editUserDetails(arrLAllEditableFields, btnEdit, btnCancel, btnSaveId, cBtnEditCancel);
					}
					
				
				}
				
			}
			
			
			

			
		
		return cUPersonalDetails;
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
			
			if(iTf != isReadOnlyTf){
				arrLTfEditable.add(tfGen);
				arrLTfEditableVals.add(arrTfVals[iTf]);
			}
		}
		
	}
	
	private void setOpts(int isReadOnlyOpt, List<Object>lAllFormFields, List<Object>arrLOptEditable, ArrayList<String>arrLOptEditableVals){
		for(int iOpt = 0; iOpt < arrOptVals.length; iOpt++){
			opt = new OptionGroup();
			opt.setCaption(arrOptCaptions[iOpt]);
			opt.addItem(arrOptVals[iOpt]);
			opt.select(arrOptVals[iOpt]);
			opt.setReadOnly(true);
			if(iOpt != isReadOnlyOpt){
				arrLOptEditable.add(opt);
				arrLOptEditableVals.add(arrOptVals[iOpt]);
			}
			
			cUPersonalDetails.addComponent(opt);
		}
	}
	
	private void setCombos(int isReadOnlyCombo, List<Object>lAllFormFields, List<Object>arrLComboEditable, ArrayList<String>arrLComboEditableVals){
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
	
	private void editUserDetails(List<Object> arrLAllEditableFields,Button btnEdit, Button btnCancel, String btnSaveId, HorizontalLayout cBtnEditCancel){
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
	
	
	
	
	
	
	
}
