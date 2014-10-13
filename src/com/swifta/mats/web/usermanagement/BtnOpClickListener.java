package com.swifta.mats.web.usermanagement;

import java.util.List;

import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Button.ClickEvent;



public class BtnOpClickListener implements Button.ClickListener{
	
	private static final long serialVersionUID = -8361209098934409391L;
	private Object ff;
	private List<Button> arrLSlaveBtn;
	//private String strNewValue;
	
	
		public BtnOpClickListener(Object ff,List<Button> arrLSlaveBtn){
			this.ff = ff;
			//this.strNewValue = strNewValue;
			this.arrLSlaveBtn = arrLSlaveBtn;
				
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
					Button btn = event.getButton();
					String strBtnID = btn.getId();
					String[] arrData = strBtnID.split("_");
					String strTbName = arrData[1];
					String strUID = arrData[2];
					String strAction = arrData[3];
					if(strAction.equals("Deactivate")){
						strAction = "Activate";
						String strNewValue = "Deactive";
						((OptionGroup)ff).setReadOnly(false);
						((OptionGroup)ff).setValue(strNewValue);
						((OptionGroup)ff).addItem(strNewValue);
						((OptionGroup)ff).select(strNewValue);
						((OptionGroup)ff).setReadOnly(true);
						((OptionGroup)ff).setEnabled(false);
						btn.setCaption(strAction);
						btn.setId("opBtn_"+strTbName+"_"+strUID+"_"+strAction);
						
					}else if(strAction.equals("Activate")){
						strAction = "Deactivate";
						String strNewValue = "Active";
						((OptionGroup)ff).setReadOnly(false);
						((OptionGroup)ff).setValue(strNewValue);
						((OptionGroup)ff).addItem(strNewValue);
						((OptionGroup)ff).select(strNewValue);
						((OptionGroup)ff).setReadOnly(true);
						((OptionGroup)ff).setEnabled(false);
						btn.setCaption(strAction);
						btn.setId("opBtn_"+strTbName+"_"+strUID+"_"+strAction);
					}else if(strAction.equals("Change Type")){
						((ComboBox)ff).setReadOnly(false);
						
						
					}
					
				
				}
	
}
