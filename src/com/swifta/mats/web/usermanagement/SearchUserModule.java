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
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

public class SearchUserModule {
	public static  final String tbUsers = "user";
	public static final String SESSION_USER_ACTION = "session_action";
	public static final String SESSION_USER_TABLE = "users_table";
	public static final String SESSION_USER_TABLE_ROW_ID = "tb_rw_id";
	public static final String ACTION_DETAILS = "details";
	public static final String ACTION_EDIT = "edit";
	public static final String ACTION_LINK = "link";
	public static final String ACTION_DELETE = "delete";
	public static final String ACTION_MORE = "moreActions";
	
	BtnActions btnDetails;
	BtnActions btnEdit;
	BtnActions btnLink;
	BtnActions  btnDelete;
	BtnActions  btnMoreActions;
	HorizontalLayout actionsC;
	List<Object> arrLPopupParentClasses; 
	
	Window popup;
	
	public SearchUserModule(){
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
		btnDelete.addClickListener(new BtnActionsClickListener(true, arrLPopupParentClasses));
		//btnMoreActions.addClickListener(new BtnActionsClickListener(true, null));
		
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		actionsC.setStyleName("c_actions");
		
	}
	
	@SuppressWarnings("unchecked")
	public IndexedContainer queryBackEnd(){
		IndexedContainer container = new IndexedContainer();//"Results for: \""+UI.getCurrent().getSession().getAttribute(ManageUserModule.UMANAGE_SESSION_SEARCH)+"\"  (Summary)");
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
		
		btnDetails.setId("user_"+strUID+"_details");
		btnEdit.setId("user_"+strUID+"_edit");
		btnLink.setId("user_"+strUID+"_link");
		btnDelete.setId("user_"+strUID+"_"+strUname+"_delete");
		btnMoreActions.setId("user_"+strUID+"_moreActions");
		
		actionsC.addComponent(btnDetails);
		actionsC.addComponent(btnEdit);
		actionsC.addComponent(btnLink);
		actionsC.addComponent(btnDelete);
		actionsC.addComponent(btnMoreActions);
	
		
		
		
		/*container.addItem("row2");
		trItem = container.getItem("row2");
		
		tdPropertyCheck =trItem.getItemProperty(" ");
		tdPropertyUID =trItem.getItemProperty("UID");
		tdPropertyUname =trItem.getItemProperty("Username");
		tdPropertyFname =trItem.getItemProperty("First Name");
		tdPropertyLname =trItem.getItemProperty("Last Name");
		tdPropertyACCType =trItem.getItemProperty("Account Type");
		tdPropertyActions =trItem.getItemProperty("Actions");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("002");
		tdPropertyUname.setValue("Prince");
		tdPropertyFname.setValue("Kachi");
		tdPropertyLname.setValue("Onyu");
		tdPropertyACCType.setValue("Administrator");
		
		//Embedded iconDetails = new Embedded("null", new ThemeResource("img/search_small.png"));
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		
		icDetails = new Button();
		icDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		icDetails.setStyleName(ValoTheme.BUTTON_TINY);
		icDetails.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		//icDetails.setStyleName(ValoTheme.LINK_SMALL);
		actionsC.addComponent(icDetails);
		
		icEdit = new Button();
		icEdit.setIcon(FontAwesome.EDIT);
		icEdit.setStyleName(ValoTheme.BUTTON_TINY);
		icEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icEdit);
		
		icLink = new Button();
		icLink.setIcon(FontAwesome.LINK);
		icLink.setStyleName(ValoTheme.BUTTON_TINY);
		icLink.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icLink);
		
		icDelete = new Button();
		icDelete.setIcon(new ThemeResource("img/ic_delete_small.png"));
		icDelete.setStyleName(ValoTheme.BUTTON_TINY);
		icDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icDelete);
		
		icMoreActions = new Button("...");
		icMoreActions.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		icMoreActions.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icMoreActions);
		tdPropertyActions.setValue(actionsC);
		
		
		
		
		
		container.addItem("row3");
		trItem = container.getItem("row3");
		
		tdPropertyCheck =trItem.getItemProperty(" ");
		tdPropertyUID =trItem.getItemProperty("UID");
		tdPropertyUname =trItem.getItemProperty("Username");
		tdPropertyFname =trItem.getItemProperty("First Name");
		tdPropertyLname =trItem.getItemProperty("Last Name");
		tdPropertyACCType =trItem.getItemProperty("Account Type");
		tdPropertyActions =trItem.getItemProperty("Actions");
		
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("001");
		tdPropertyUname.setValue("Opeyemi");
		tdPropertyFname.setValue("Ope");
		tdPropertyLname.setValue("Adekugbe");
		tdPropertyACCType.setValue("Administrator");
		
		//Embedded iconDetails = new Embedded("null", new ThemeResource("img/search_small.png"));
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		
		icDetails = new Button();
		icDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		icDetails.setStyleName(ValoTheme.BUTTON_TINY);
		icDetails.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		//icDetails.setStyleName(ValoTheme.LINK_SMALL);
		actionsC.addComponent(icDetails);
		
		icEdit = new Button();
		icEdit.setIcon(FontAwesome.EDIT);
		icEdit.setStyleName(ValoTheme.BUTTON_TINY);
		icEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icEdit);
		
		icLink = new Button();
		icLink.setIcon(FontAwesome.LINK);
		icLink.setStyleName(ValoTheme.BUTTON_TINY);
		icLink.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icLink);
		
		icDelete = new Button();
		icDelete.setIcon(new ThemeResource("img/ic_delete_small.png"));
		icDelete.setStyleName(ValoTheme.BUTTON_TINY);
		icDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icDelete);
		
		icMoreActions = new Button("...");
		icMoreActions.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		icMoreActions.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icMoreActions);
		tdPropertyActions.setValue(actionsC);
		
		
		
		
		container.addItem("row4");
		trItem = container.getItem("row4");
		
		tdPropertyCheck =trItem.getItemProperty(" ");
		tdPropertyUID =trItem.getItemProperty("UID");
		tdPropertyUname =trItem.getItemProperty("Username");
		tdPropertyFname =trItem.getItemProperty("First Name");
		tdPropertyLname =trItem.getItemProperty("Last Name");
		tdPropertyACCType =trItem.getItemProperty("Account Type");
		tdPropertyActions =trItem.getItemProperty("Actions");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("004");
		tdPropertyUname.setValue("Adi");
		tdPropertyFname.setValue("Adija");
		tdPropertyLname.setValue("Uzo");
		tdPropertyACCType.setValue("Administrator");
		
		//Embedded iconDetails = new Embedded("null", new ThemeResource("img/search_small.png"));
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		
		icDetails = new Button();
		icDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		icDetails.setStyleName(ValoTheme.BUTTON_TINY);
		icDetails.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		//icDetails.setStyleName(ValoTheme.LINK_SMALL);
		actionsC.addComponent(icDetails);
		
		icEdit = new Button();
		icEdit.setIcon(FontAwesome.EDIT);
		icEdit.setStyleName(ValoTheme.BUTTON_TINY);
		icEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icEdit);
		
		icLink = new Button();
		icLink.setIcon(FontAwesome.LINK);
		icLink.setStyleName(ValoTheme.BUTTON_TINY);
		icLink.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icLink);
		
		icDelete = new Button();
		icDelete.setIcon(new ThemeResource("img/ic_delete_small.png"));
		icDelete.setStyleName(ValoTheme.BUTTON_TINY);
		icDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icDelete);
		
		icMoreActions = new Button("...");
		icMoreActions.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		icMoreActions.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icMoreActions);
		tdPropertyActions.setValue(actionsC);
		actionsC.setStyleName("c_actions");
		
		
		container.addItem("row5");
		trItem = container.getItem("row5");
		
		tdPropertyCheck =trItem.getItemProperty(" ");
		tdPropertyUID =trItem.getItemProperty("UID");
		tdPropertyUname =trItem.getItemProperty("Username");
		tdPropertyFname =trItem.getItemProperty("First Name");
		tdPropertyLname =trItem.getItemProperty("Last Name");
		tdPropertyACCType =trItem.getItemProperty("Account Type");
		tdPropertyActions =trItem.getItemProperty("Actions");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("004");
		tdPropertyUname.setValue("Adi");
		tdPropertyFname.setValue("Adija");
		tdPropertyLname.setValue("Uzo");
		tdPropertyACCType.setValue("Administrator");
		
		//Embedded iconDetails = new Embedded("null", new ThemeResource("img/search_small.png"));
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		
		icDetails = new Button();
		icDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		icDetails.setStyleName(ValoTheme.BUTTON_TINY);
		icDetails.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		//icDetails.setStyleName(ValoTheme.LINK_SMALL);
		actionsC.addComponent(icDetails);
		
		icEdit = new Button();
		icEdit.setIcon(FontAwesome.EDIT);
		icEdit.setStyleName(ValoTheme.BUTTON_TINY);
		icEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icEdit);
		
		icLink = new Button();
		icLink.setIcon(FontAwesome.LINK);
		icLink.setStyleName(ValoTheme.BUTTON_TINY);
		icLink.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icLink);
		
		icDelete = new Button();
		icDelete.setIcon(new ThemeResource("img/ic_delete_small.png"));
		icDelete.setStyleName(ValoTheme.BUTTON_TINY);
		icDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icDelete);
		
		icMoreActions = new Button("...");
		icMoreActions.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		icMoreActions.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icMoreActions);
		tdPropertyActions.setValue(actionsC);
		actionsC.setStyleName("c_actions");
		
		
		
		
		container.addItem("row6");
		trItem = container.getItem("row6");
		
		tdPropertyCheck =trItem.getItemProperty(" ");
		tdPropertyUID =trItem.getItemProperty("UID");
		tdPropertyUname =trItem.getItemProperty("Username");
		tdPropertyFname =trItem.getItemProperty("First Name");
		tdPropertyLname =trItem.getItemProperty("Last Name");
		tdPropertyACCType =trItem.getItemProperty("Account Type");
		tdPropertyActions =trItem.getItemProperty("Actions");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("004");
		tdPropertyUname.setValue("Adi");
		tdPropertyFname.setValue("Adija");
		tdPropertyLname.setValue("Uzo");
		tdPropertyACCType.setValue("Administrator");
		
		//Embedded iconDetails = new Embedded("null", new ThemeResource("img/search_small.png"));
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		
		icDetails = new Button();
		icDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		icDetails.setStyleName(ValoTheme.BUTTON_TINY);
		icDetails.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		//icDetails.setStyleName(ValoTheme.LINK_SMALL);
		actionsC.addComponent(icDetails);
		
		icEdit = new Button();
		icEdit.setIcon(FontAwesome.EDIT);
		icEdit.setStyleName(ValoTheme.BUTTON_TINY);
		icEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icEdit);
		
		icLink = new Button();
		icLink.setIcon(FontAwesome.LINK);
		icLink.setStyleName(ValoTheme.BUTTON_TINY);
		icLink.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icLink);
		
		icDelete = new Button();
		icDelete.setIcon(new ThemeResource("img/ic_delete_small.png"));
		icDelete.setStyleName(ValoTheme.BUTTON_TINY);
		icDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icDelete);
		
		icMoreActions = new Button("...");
		icMoreActions.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		icMoreActions.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icMoreActions);
		tdPropertyActions.setValue(actionsC);
		actionsC.setStyleName("c_actions");
		
		
		container.addItem("row4");
		trItem = container.getItem("row4");
		
		tdPropertyCheck =trItem.getItemProperty(" ");
		tdPropertyUID =trItem.getItemProperty("UID");
		tdPropertyUname =trItem.getItemProperty("Username");
		tdPropertyFname =trItem.getItemProperty("First Name");
		tdPropertyLname =trItem.getItemProperty("Last Name");
		tdPropertyACCType =trItem.getItemProperty("Account Type");
		tdPropertyActions =trItem.getItemProperty("Actions");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("004");
		tdPropertyUname.setValue("Adi");
		tdPropertyFname.setValue("Adija");
		tdPropertyLname.setValue("Uzo");
		tdPropertyACCType.setValue("Administrator");
		
		//Embedded iconDetails = new Embedded("null", new ThemeResource("img/search_small.png"));
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		
		icDetails = new Button();
		icDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		icDetails.setStyleName(ValoTheme.BUTTON_TINY);
		icDetails.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		//icDetails.setStyleName(ValoTheme.LINK_SMALL);
		actionsC.addComponent(icDetails);
		
		icEdit = new Button();
		icEdit.setIcon(FontAwesome.EDIT);
		icEdit.setStyleName(ValoTheme.BUTTON_TINY);
		icEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icEdit);
		
		icLink = new Button();
		icLink.setIcon(FontAwesome.LINK);
		icLink.setStyleName(ValoTheme.BUTTON_TINY);
		icLink.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icLink);
		
		icDelete = new Button();
		icDelete.setIcon(new ThemeResource("img/ic_delete_small.png"));
		icDelete.setStyleName(ValoTheme.BUTTON_TINY);
		icDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icDelete);
		
		icMoreActions = new Button("...");
		icMoreActions.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		icMoreActions.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icMoreActions);
		tdPropertyActions.setValue(actionsC);
		actionsC.setStyleName("c_actions");
		
		
		
		container.addItem("row7");
		trItem = container.getItem("row7");
		
		tdPropertyCheck =trItem.getItemProperty(" ");
		tdPropertyUID =trItem.getItemProperty("UID");
		tdPropertyUname =trItem.getItemProperty("Username");
		tdPropertyFname =trItem.getItemProperty("First Name");
		tdPropertyLname =trItem.getItemProperty("Last Name");
		tdPropertyACCType =trItem.getItemProperty("Account Type");
		tdPropertyActions =trItem.getItemProperty("Actions");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("004");
		tdPropertyUname.setValue("Adi");
		tdPropertyFname.setValue("Adija");
		tdPropertyLname.setValue("Uzo");
		tdPropertyACCType.setValue("Administrator");
		
		//Embedded iconDetails = new Embedded("null", new ThemeResource("img/search_small.png"));
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		
		icDetails = new Button();
		icDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		icDetails.setStyleName(ValoTheme.BUTTON_TINY);
		icDetails.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		//icDetails.setStyleName(ValoTheme.LINK_SMALL);
		actionsC.addComponent(icDetails);
		
		icEdit = new Button();
		icEdit.setIcon(FontAwesome.EDIT);
		icEdit.setStyleName(ValoTheme.BUTTON_TINY);
		icEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icEdit);
		
		icLink = new Button();
		icLink.setIcon(FontAwesome.LINK);
		icLink.setStyleName(ValoTheme.BUTTON_TINY);
		icLink.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icLink);
		
		icDelete = new Button();
		icDelete.setIcon(new ThemeResource("img/ic_delete_small.png"));
		icDelete.setStyleName(ValoTheme.BUTTON_TINY);
		icDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icDelete);
		
		icMoreActions = new Button("...");
		icMoreActions.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		icMoreActions.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icMoreActions);
		tdPropertyActions.setValue(actionsC);
		actionsC.setStyleName("c_actions");
		
		
		
		container.addItem("row8");
		trItem = container.getItem("row8");
		
		tdPropertyCheck =trItem.getItemProperty(" ");
		tdPropertyUID =trItem.getItemProperty("UID");
		tdPropertyUname =trItem.getItemProperty("Username");
		tdPropertyFname =trItem.getItemProperty("First Name");
		tdPropertyLname =trItem.getItemProperty("Last Name");
		tdPropertyACCType =trItem.getItemProperty("Account Type");
		tdPropertyActions =trItem.getItemProperty("Actions");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("004");
		tdPropertyUname.setValue("Adi");
		tdPropertyFname.setValue("Adija");
		tdPropertyLname.setValue("Uzo");
		tdPropertyACCType.setValue("Administrator");
		
		//Embedded iconDetails = new Embedded("null", new ThemeResource("img/search_small.png"));
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		
		icDetails = new Button();
		icDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		icDetails.setStyleName(ValoTheme.BUTTON_TINY);
		icDetails.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		//icDetails.setStyleName(ValoTheme.LINK_SMALL);
		actionsC.addComponent(icDetails);
		
		icEdit = new Button();
		icEdit.setIcon(FontAwesome.EDIT);
		icEdit.setStyleName(ValoTheme.BUTTON_TINY);
		icEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icEdit);
		
		icLink = new Button();
		icLink.setIcon(FontAwesome.LINK);
		icLink.setStyleName(ValoTheme.BUTTON_TINY);
		icLink.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icLink);
		
		icDelete = new Button();
		icDelete.setIcon(new ThemeResource("img/ic_delete_small.png"));
		icDelete.setStyleName(ValoTheme.BUTTON_TINY);
		icDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icDelete);
		
		icMoreActions = new Button("...");
		icMoreActions.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		icMoreActions.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icMoreActions);
		tdPropertyActions.setValue(actionsC);
		actionsC.setStyleName("c_actions");
		
		
		
		container.addItem("row9");
		trItem = container.getItem("row9");
		
		tdPropertyCheck =trItem.getItemProperty(" ");
		tdPropertyUID =trItem.getItemProperty("UID");
		tdPropertyUname =trItem.getItemProperty("Username");
		tdPropertyFname =trItem.getItemProperty("First Name");
		tdPropertyLname =trItem.getItemProperty("Last Name");
		tdPropertyACCType =trItem.getItemProperty("Account Type");
		tdPropertyActions =trItem.getItemProperty("Actions");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("004");
		tdPropertyUname.setValue("Adi");
		tdPropertyFname.setValue("Adija");
		tdPropertyLname.setValue("Uzo");
		tdPropertyACCType.setValue("Administrator");
		
		//Embedded iconDetails = new Embedded("null", new ThemeResource("img/search_small.png"));
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		
		icDetails = new Button();
		icDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		icDetails.setStyleName(ValoTheme.BUTTON_TINY);
		icDetails.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		//icDetails.setStyleName(ValoTheme.LINK_SMALL);
		actionsC.addComponent(icDetails);
		
		icEdit = new Button();
		icEdit.setIcon(FontAwesome.EDIT);
		icEdit.setStyleName(ValoTheme.BUTTON_TINY);
		icEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icEdit);
		
		icLink = new Button();
		icLink.setIcon(FontAwesome.LINK);
		icLink.setStyleName(ValoTheme.BUTTON_TINY);
		icLink.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icLink);
		
		icDelete = new Button();
		icDelete.setIcon(new ThemeResource("img/ic_delete_small.png"));
		icDelete.setStyleName(ValoTheme.BUTTON_TINY);
		icDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icDelete);
		
		icMoreActions = new Button("...");
		icMoreActions.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		icMoreActions.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icMoreActions);
		tdPropertyActions.setValue(actionsC);
		actionsC.setStyleName("c_actions");
		
		
		
		container.addItem("row10");
		trItem = container.getItem("row10");
		
		tdPropertyCheck =trItem.getItemProperty(" ");
		tdPropertyUID =trItem.getItemProperty("UID");
		tdPropertyUname =trItem.getItemProperty("Username");
		tdPropertyFname =trItem.getItemProperty("First Name");
		tdPropertyLname =trItem.getItemProperty("Last Name");
		tdPropertyACCType =trItem.getItemProperty("Account Type");
		tdPropertyActions =trItem.getItemProperty("Actions");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("004");
		tdPropertyUname.setValue("Adi");
		tdPropertyFname.setValue("Adija");
		tdPropertyLname.setValue("Uzo");
		tdPropertyACCType.setValue("Administrator");
		
		//Embedded iconDetails = new Embedded("null", new ThemeResource("img/search_small.png"));
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		
		icDetails = new Button();
		icDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		icDetails.setStyleName(ValoTheme.BUTTON_TINY);
		icDetails.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		//icDetails.setStyleName(ValoTheme.LINK_SMALL);
		actionsC.addComponent(icDetails);
		
		icEdit = new Button();
		icEdit.setIcon(FontAwesome.EDIT);
		icEdit.setStyleName(ValoTheme.BUTTON_TINY);
		icEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icEdit);
		
		icLink = new Button();
		icLink.setIcon(FontAwesome.LINK);
		icLink.setStyleName(ValoTheme.BUTTON_TINY);
		icLink.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icLink);
		
		icDelete = new Button();
		icDelete.setIcon(new ThemeResource("img/ic_delete_small.png"));
		icDelete.setStyleName(ValoTheme.BUTTON_TINY);
		icDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icDelete);
		
		icMoreActions = new Button("...");
		icMoreActions.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		icMoreActions.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		actionsC.addComponent(icMoreActions);
		tdPropertyActions.setValue(actionsC);
		actionsC.setStyleName("c_actions");*/
		
		
		
		
		
		
		
		return container;
	}
	
	
	public void showDeleteUserContainer(String username){
		popup = new Window("Delete "+username);
		ThemeResource r = new ThemeResource("img/ic_delete_small.png");
		//popup.setIcon(r);
		//popup.setStyleName(Reindeer.WINDOW_BLACK);
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
