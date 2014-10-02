package com.swifta.mats.web;

import java.util.Collection;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class UserDetailsModule{
	public UserDetailsModule(){
		
	}
	
	@SuppressWarnings("unchecked")
	public Table getUserDetails(){
		
		Table tb = new Table("Results for: \""+UI.getCurrent().getSession().getAttribute(ManageUserModule.UMANAGE_SESSION_SEARCH)+"\"  (Summary)");
		tb.addContainerProperty("UID", String.class, "000");
		tb.addContainerProperty("Username", String.class, "");
		tb.addContainerProperty("First Name", String.class, "");
		tb.addContainerProperty("Last Name", String.class, "");
		tb.addContainerProperty("Account Type", String.class, "");
		
		
		Item trItem;
		tb.addItem("row1");
		trItem = tb.getItem("row1");
		Property<String> tdPropertyUID =trItem.getItemProperty("UID");
		Property<String> tdPropertyUname =trItem.getItemProperty("Username");
		Property<String> tdPropertyFname =trItem.getItemProperty("First Name");
		Property<String> tdPropertyLname =trItem.getItemProperty("Last Name");
		Property<String> tdPropertyACCType =trItem.getItemProperty("Account Type");
		
		tdPropertyUID.setValue("001");
		tdPropertyUname.setValue("Prince");
		tdPropertyFname.setValue("Kachi");
		tdPropertyLname.setValue("Onyu");
		tdPropertyACCType.setValue("Administrator");
		
		FormLayout detailsForm = new FormLayout();
		//Label lbFieldName = new Label("User Name");
		TextField tfUName = new TextField("Username");
		tfUName.setValue("Prince");
		detailsForm.addComponent(tfUName);
		
		Item sItem = tb.getItem("row1");
		Collection<?> fieldNameIds = sItem.getItemPropertyIds();
		Object fieldNameIdsArray[] = fieldNameIds.toArray();
		Property<String> sProperty;
		Property<String> dPropertyFieldName;
		Property<String> dPropertyFieldValue;
		
		Table tDetails = new Table ("Details of Kachi");
		tDetails.setPageLength(0);
		//tDetails.setSelectable(true);
		
		tDetails.addContainerProperty("Field Name", String.class, null);
		tDetails.addContainerProperty("Field Value", String.class, null);
		//tDetails.setEditable(true);
		
		
		
		for(int i = 0; i < fieldNameIds.size(); i++){
			Object itemId = tDetails.addItem();
			Item dItem = tDetails.getItem(itemId);
			
			dPropertyFieldName = dItem.getItemProperty("Field Name");
			dPropertyFieldName.setReadOnly(true);
			dPropertyFieldValue = dItem.getItemProperty("Field Value");
			sProperty = sItem.getItemProperty(fieldNameIdsArray[i]);
			dPropertyFieldName.setValue((String) fieldNameIdsArray[i]);
			dPropertyFieldValue.setValue(sProperty.getValue());
			//dPropertyFieldValue.setReadOnly(false);
	
		}
		
		
		
		return tDetails;
	}
}
