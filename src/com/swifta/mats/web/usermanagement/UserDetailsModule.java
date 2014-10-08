package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.swifta.mats.web.WorkSpace;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

public class UserDetailsModule{
	public static boolean uDetailsEditStatus = false;
	
	
	public UserDetailsModule(){
		
	}
	
	public FormLayout getDetailsForm(String strTbName, String strUID){
		return setDetailsForm(getUDetails(strTbName, strUID));
	}
	
	@SuppressWarnings("deprecation")
	private FormLayout setDetailsForm(Map<String, String[]> mappedData){
		
		
		FormLayout  cUPersonalDetails = new FormLayout();
		
		cUPersonalDetails.setMargin(true);
		cUPersonalDetails.setSpacing(false);
		cUPersonalDetails.setStyleName("frm_details_personal_info");
		cUPersonalDetails.setSizeFull();
		//if(strTbName.equals("log")){
		
		
			
			
			if(mappedData.size() == 0){
				cUPersonalDetails.addComponent(new Label(" NO Data Available!"));
			}else{
				
				String strTbName = mappedData.get("arrTbName")[0];
				if(strTbName.equals("activity_log") || strTbName.equals("account_change_log")){
					//TODO fetch specified table log
					
				}else{
					TextField tfGen;
					OptionGroup opt;
					ComboBox combo;
					PopupDateField dF;
					
					/*TODO
					 * 
					 * Fetched data
					 *
					 *
					 */
					
					//Set Data
					
					
					String strUID = mappedData.get("arrUID")[0];
					String[] arrTfCaptions = mappedData.get("arrTfCaptions");
					String[] arrOptCaptions = mappedData.get("arrOptCaptions");
					String[] arrComboCaptions = mappedData.get("arrComboCaptions");
					String[] arrDfCaptions = mappedData.get("arrDfCaptions");
					
					String[] arrTfVals = mappedData.get("arrTfVals");
					String[] arrOptVals = mappedData.get("arrOptVals");
					String[] arrComboVals = mappedData.get("arrComboVals");
					String[] arrDfVals = mappedData.get("arrDfVals");
					
					
					final String btnEditId = strUID+"_edit";
					final String btnSaveId = strUID+"_save";
					
					//Holders of editable form components
					final ArrayList<TextField> arrLTfEditable = new ArrayList<TextField>();
					final ArrayList<OptionGroup> arrLOptEditable = new ArrayList<OptionGroup>();
					final ArrayList<ComboBox> arrLComboEditable = new ArrayList<ComboBox>();
					final ArrayList<PopupDateField> arrLDfEditable = new ArrayList<PopupDateField>();
					
					//Holders of editable form components original values
					final ArrayList<String> arrLTfEditableVals = new ArrayList<String>();
					final ArrayList<String> arrLOptEditableVals = new ArrayList<String>();
					final ArrayList<String> arrLComboEditableVals = new ArrayList<String>();
					final ArrayList<String> arrLDfEditableVals = new ArrayList<String>();
					
					
					
					int iTf;
					int iOpt;
					int iCombo;
					int iDf;
					
					//For testing purposes, we assume that every first item of each for category is read-only, hence the isReadOnly = 0
					int isReadOnlyTf = 0;
					int isReadOnlyOpt = 1;
					int isReadOnlyCombo = -1;
					
					
					//lbGen = new Label();
					//lbGen.setStyleName(ValoTheme.LABEL_H4);
					//lbGen.setStyleName("label_frm_header");
					//lbGen.setCaption(strTbName);
					//cUPersonalDetails.addComponent(lbGen);
					
					//set TextField(tf) form objects
					
					for(iTf = 0; iTf < arrTfVals.length; iTf++){
						tfGen = new TextField();
						//tfGen.setPrimaryStyleName(ValoTheme.TEXTFIELD_HUGE);
						tfGen.setWidth("100%");
						tfGen.setCaption(arrTfCaptions[iTf]);
						tfGen.setValue(arrTfVals[iTf]);
						tfGen.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
						tfGen.setReadOnly(true);
						cUPersonalDetails.addComponent(tfGen);
						
						if(iTf != isReadOnlyTf){
							arrLTfEditable.add(tfGen);
							arrLTfEditableVals.add(arrTfVals[iTf]);
						}
					}
					
					//Set OptionGroup(opt) form objects
					
					for(iOpt = 0; iOpt < arrOptVals.length; iOpt++){
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
					
					
					//Set ComboBox(combo) form objects
					
					for(iCombo = 0; iCombo < arrComboVals.length; iCombo++){
						combo = new ComboBox();
						combo.setCaption(arrComboCaptions[iCombo]);
						combo.addItem(arrComboVals[iCombo]);
						combo.select(arrComboVals[iCombo]);
						combo.setReadOnly(true);
						
						if(iCombo != isReadOnlyCombo){
							arrLComboEditable.add(combo);
							arrLComboEditableVals.add(arrComboVals[iCombo]);
						}
						
						cUPersonalDetails.addComponent(combo);
					}
					
					
					
			
					//Set InlineDateField(dF) form objects
					
					for(iDf= 0; iDf < arrDfVals.length; iDf++){
						dF = new PopupDateField();
						dF.setCaption(arrDfCaptions[iDf]);
						dF.setValue(new Date(arrDfVals[iDf]));
						dF.setReadOnly(true);
						
						if(iDf != isReadOnlyCombo){
							arrLDfEditable.add(dF);
							arrLDfEditableVals.add(arrDfVals[iDf]);
						}
						
						cUPersonalDetails.addComponent(dF);
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
					
					cUPersonalDetails.addComponent(cBtnEditCancel);
					
					
					
					btnEdit.addClickListener(new Button.ClickListener() {
						
						
						private static final long serialVersionUID = -6544444429248747390L;
			
						@Override
						public void buttonClick(ClickEvent event) {
							TextField curTf;
							OptionGroup curOpt;
							ComboBox curCombo;
							PopupDateField curDf;
							/*
							 * Prepare all Editable fields (Entire form) for editing.
							 */
							if(event.getButton().getId().equals(btnEditId)){
								for(int i = 0; i < arrLTfEditable.size(); i++){
									curTf = arrLTfEditable.get(i);
									curTf.setReadOnly(false);
									curTf.removeStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
									uDetailsEditStatus = true;
									
								}
								
								for(int i = 0; i < arrLOptEditable.size(); i++){
									curOpt = arrLOptEditable.get(i);
									curOpt.setReadOnly(false);
									
									//TODO Values below are dummy. Need to fetch applicable field values for system users to use when editing.
									curOpt.addItem("Female");
									curOpt.addItem("Male");
								}
								
								for(int i = 0; i < arrLComboEditable.size(); i++){
									curCombo = arrLComboEditable.get(i);
									curCombo.setReadOnly(false);
									
									//TODO Values below are dummy. Need to fetch applicable field value for system users to choose
									curCombo.addItem("Finance Controller");
									curCombo.addItem("Customer Care Operator");
								}
								
								
								for(int i = 0; i < arrLDfEditable.size(); i++){
									curDf = arrLDfEditable.get(i);
									curDf.setReadOnly(false);
								}
								
								
								
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
									disableEditableFields(arrLTfEditable, arrLOptEditable, arrLComboEditable, arrLDfEditable);
									
									
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
							
							resetForm(arrLTfEditable,  arrLTfEditableVals, arrLOptEditable, arrLOptEditableVals, arrLComboEditable, arrLComboEditableVals, arrLDfEditable, arrLDfEditableVals);
							btnEdit.setId(btnEditId);
							btnEdit.setIcon(FontAwesome.EDIT);
							btnCancel.setVisible(false);
							uDetailsEditStatus = false;
							
						}
					});
				
				}
			}
		
		return cUPersonalDetails;
	}
	
	
	private void resetForm(ArrayList<TextField> arrLTf,  ArrayList<String> arrLTfVals, ArrayList<OptionGroup> arrLOpt, ArrayList<String> arrLOptVals, ArrayList<ComboBox> arrLCombo, ArrayList<String> arrLComboSelVals, ArrayList<PopupDateField>arrLDf, ArrayList<String>arrLDfVals){
			int i = 0;
			
			for(TextField tf: arrLTf){
				tf.setValue(arrLTfVals.get(i));
				tf.setStyleName(ValoTheme.BUTTON_BORDERLESS);
				tf.setReadOnly(true);
				i++;
			}
			
			i = 0; //reset the index
			for(OptionGroup opt: arrLOpt){
				opt.setValue(arrLOptVals.get(i));
				opt.setReadOnly(true);
				i++;
			}
			
			i = 0; //reset the index
			for(ComboBox combo: arrLCombo){
				combo.setValue(arrLComboSelVals.get(i));
				combo.setReadOnly(true);
				i++;
			}
			
			i = 0; //reset the index
			for(PopupDateField dF: arrLDf){
				//dF.setValue(new Date(arrLDfVals.get(i)));
				dF.setReadOnly(true);
				i++;
			}
			
						
	}
	
	
	private void disableEditableFields(ArrayList<TextField> arrLTf, ArrayList<OptionGroup> arrLOpt, ArrayList<ComboBox> arrLCombo, ArrayList<PopupDateField>arrLDf){
		
		
		
		for(TextField tf: arrLTf){
			
			tf.setStyleName(ValoTheme.BUTTON_BORDERLESS);
			tf.setReadOnly(true);
			
		}
		
		
		for(OptionGroup opt: arrLOpt){
			
			opt.setReadOnly(true);
			
		}
		
	
		for(ComboBox combo: arrLCombo){
			
			combo.setReadOnly(true);
			
		}
		
		for(PopupDateField dF: arrLDf){
			dF.setReadOnly(true);
			
		}
		
	}
	
	
	public Map<String, String[]> getUDetails(String strTbName, String strUID ){
		BackEndEmulator bee = new BackEndEmulator();
		Map<String, String[]> mappedData = bee.getUserPersonalInfo(strTbName, strUID);
		return mappedData;
	}
	
}
