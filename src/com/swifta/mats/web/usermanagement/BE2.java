package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class BE2 {
	private BtnActions btnDetails;
	private BtnActions btnEdit;
	private BtnActions btnLink;
	private BtnActions  btnDelete;
	private BtnActions  btnMoreActions;
	private List<Object> arrLPopupParentClasses; 
	HorizontalLayout actionsC;
	BE2(){
		ThemeResource icDelete = new ThemeResource("img/ic_delete_small.png");
		btnDetails = new BtnActions("Details");
		btnDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		btnEdit = new BtnActions("Edit");
		btnEdit.setIcon(FontAwesome.EDIT);
		btnLink = new BtnActions("Link");
		btnLink.setIcon(FontAwesome.LINK);
		btnDelete = new BtnActions("Delete");
		btnDelete.setIcon(icDelete);
		btnMoreActions = new BtnActions("More...");
		btnMoreActions.setIcon(FontAwesome.ELLIPSIS_H);
		btnMoreActions.addClickListener(new BtnActionsClickListener(false, null));
		arrLPopupParentClasses = new ArrayList<Object>();
		arrLPopupParentClasses.add(this);
		
		btnDetails.addClickListener(new BtnActionsClickListener(false, null));
		btnEdit.addClickListener(new BtnActionsClickListener(false, null));
		//btnLink.addClickListener(new BtnActionsClickListener());
		btnDelete.addClickListener(new Button.ClickListener() {
			
			
			private static final long serialVersionUID = -3840990560267229588L;

			@Override
			public void buttonClick(ClickEvent event) {
				
					String[] arrID = event.getButton().getId().split("_");
					showDeleteUserContainer(arrID);
					
				
				
			}
		});
		//btnMoreActions.addClickListener(new BtnActionsClickListener(true, null));
		
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		actionsC.setStyleName("c_actions");
	}
	
	
	
	
	
	protected void showPopup() {
		// TODO Auto-generated method stub
		
	}





	@SuppressWarnings("unchecked")
	public IndexedContainer queryBackEnd(String strSearchParams){
		IndexedContainer container = new IndexedContainer();	
	
		container.addContainerProperty(" ", CheckBox.class, null);
		container.addContainerProperty("UID", String.class, "000");
		container.addContainerProperty("Username", String.class, "");
		container.addContainerProperty("First Name", String.class, "");
		container.addContainerProperty("Last Name", String.class, "");
		container.addContainerProperty("Account Type", String.class, "");
		container.addContainerProperty("Actions", HorizontalLayout.class, null);
		
		//tb.setColumnIcon(" ", FontAwesome.CHECK_SQUARE_O);
		
		
		Item trItem;
		container.addItem("row1");
		trItem = container.getItem("row1");
		Property<CheckBox> tdPropertyCheck =trItem.getItemProperty(" ");
		Property<String> tdPropertyUID =trItem.getItemProperty("UID");
		Property<String> tdPropertyUname =trItem.getItemProperty("Username");
		Property<String> tdPropertyFname =trItem.getItemProperty("First Name");
		Property<String> tdPropertyLname =trItem.getItemProperty("Last Name");
		Property<String> tdPropertyACCType =trItem.getItemProperty("Account Type");
		Property<HorizontalLayout> tdPropertyActions =trItem.getItemProperty("Actions");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("001");
		tdPropertyUname.setValue("Sevo");
		tdPropertyFname.setValue("Yoweri");
		tdPropertyLname.setValue("Amama");
		tdPropertyACCType.setValue("Administrator");
		tdPropertyActions.setValue(actionsC);
		String strUID = tdPropertyUID.getValue();
		String strUname = tdPropertyUname.getValue();
		
		btnDetails.setId("users_personal_"+strUID+"_details");
		btnEdit.setId("user_personal_"+strUID+"_edit");
		btnLink.setId("user_account_"+strUID+"_link");
		btnDelete.setId("user_account_"+strUID+"_"+strUname+"_delete");
		btnMoreActions.setId("user_account_"+strUID+"_moreActions");
		
		actionsC.addComponent(btnDetails);
		actionsC.addComponent(btnEdit);
		actionsC.addComponent(btnLink);
		actionsC.addComponent(btnDelete);
		actionsC.addComponent(btnMoreActions);
		return container;
	}
	
	
	
	private void showDeleteUserContainer(String[] arrID){
		String username = arrID[3];
		final Window popup = new Window("Delete "+username);
		popup.setStyleName("w_delete_user");
		ThemeResource r = new ThemeResource("img/ic_delete_small.png");
		
		popup.center();
		
		VerticalLayout cDeletePrompt = new VerticalLayout();
		cDeletePrompt.setSpacing(true);
		cDeletePrompt.setMargin(true);
		
		Label lbDeleteMsg = new Label("Are you sure you want to delete "+username+"?");
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
		
		HorizontalLayout cPopupBtns = new HorizontalLayout();
		cPopupBtns.setSizeUndefined();
		cPopupBtns.setSpacing(true);
		
		Button btnCancel = new Button("Cancel");
		Button btnDelete = new Button("Delete");
		btnDelete.setIcon(r);
		cPopupBtns.addComponent(btnCancel);
		cPopupBtns.addComponent(btnDelete);
		cDeletePrompt.addComponent(cPopupBtns);
		cDeletePrompt.setComponentAlignment(frmDeleteReason, Alignment.MIDDLE_CENTER);
		cDeletePrompt.setComponentAlignment(cPopupBtns, Alignment.BOTTOM_CENTER);
		popup.setContent(cDeletePrompt);
		UI.getCurrent().addWindow(popup);
		btnCancel.addClickListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = -9071850366625898895L;

			@Override
			public void buttonClick(ClickEvent event) {
				popup.close();
				
			}
		});
		
		btnDelete.addClickListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = -6318666715385643538L;

			@Override
			public void buttonClick(ClickEvent event) {
				/*
				 * 
				 * TODO send user id to server for deletion and update the table.
				 */
				popup.close();
				
			}
		});
	}
	
	
}