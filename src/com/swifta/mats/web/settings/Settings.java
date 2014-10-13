package com.swifta.mats.web.settings;



import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.swifta.mats.web.usermanagement.BtnActions;
import com.swifta.mats.web.usermanagement.PagedTableCustom;

public class Settings extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2467595854268829523L;
	
	public HorizontalLayout Addlabel() {
		// TODO Auto-generated method stub
		//setSizeFull();
		setMargin(true);
		AddIcons icon1 = new AddIcons();
		final HorizontalLayout laying = new HorizontalLayout();
		VerticalLayout het1 = icon1.ImagesClicking("Account Management","img/use.png");
		VerticalLayout het2 = icon1.ImagesClicking("Permissions","img/permission.png");
		VerticalLayout het3 = icon1.ImagesClicking("Authentication","img/auth.png");
		VerticalLayout het4 = icon1.ImagesClicking("Fees/ Commission","img/fees.png");
		VerticalLayout het5 = icon1.ImagesClicking("Transfer","img/transfer.png");
		VerticalLayout het6 = icon1.ImagesClicking("Threshold","img/threshold.png");
		
		
		laying.addComponent(het1);
		laying.addComponent(het2);
		laying.addComponent(het3);
		laying.addComponent(het4);
		laying.addComponent(het5);
		laying.addComponent(het6);
		
		het1.addLayoutClickListener(new LayoutClickListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void layoutClick(LayoutClickEvent event) {
				Button adduser = new Button("+Add User");
				Button filter = new Button("Filter");
				Button BulkImport = new Button("Bulk Import");
				Button back = new Button("Back");
				ComboBox selection = new ComboBox();
				
				VerticalLayout searchResultsContainer = new VerticalLayout();
				searchResultsContainer.setSizeUndefined();
				searchResultsContainer.setSpacing(true);
				searchResultsContainer.setMargin(new MarginInfo(false, true, true, true));
				
				PagedTableCustom tb = new PagedTableCustom("Search results for: Accounts");
				
				tb.setWidth("900px");
				tb.setContainerDataSource(het1click());
				tb.setColumnIcon(" ", FontAwesome.CHECK_SQUARE_O);
				//tb.setPageLength(10);
				//tb.setStyleName("tble");
				
				HorizontalLayout pnUserSearchResults = tb.createControls();
				//pnUserSearchResults.setSizeFull();
				pnUserSearchResults.setMargin(false);
				pnUserSearchResults.setSpacing(false);
				
				HorizontalLayout laybut = new HorizontalLayout();
				laybut.setMargin(false);
				//laybut.setSizeFull();
				laybut.addComponent(adduser);
				laybut.addComponent(BulkImport);
				
				HorizontalLayout laybut1 = new HorizontalLayout();
				laybut1.setMargin(new MarginInfo(false, false, true, false));
				//laybut1.setSizeFull();
				laybut1.addComponent(selection);
				laybut1.addComponent(filter);
				Label lbel1 = new Label("Account Management");
				
				searchResultsContainer.addComponent(lbel1);
				searchResultsContainer.addComponent(laybut1);
				searchResultsContainer.setComponentAlignment(laybut1, Alignment.TOP_LEFT);
				
				searchResultsContainer.addComponent(laybut);
				searchResultsContainer.addComponent(pnUserSearchResults);
				searchResultsContainer.addComponent(tb);
				searchResultsContainer.addComponent(back);
				searchResultsContainer.setComponentAlignment(back, Alignment.BOTTOM_LEFT);
				
				laying.removeAllComponents();
				laying.addComponent(searchResultsContainer);
            }
        });
		
		return laying;
		
	}
	
	public IndexedContainer het1click() {
		//Table table = new Table();
		
		Button actionsC = new Button("Details");
		
		
		
		IndexedContainer container = new IndexedContainer();//"Results for: \""+UI.getCurrent().getSession().getAttribute(ManageUserModule.UMANAGE_SESSION_SEARCH)+"\"  (Summary)");
		container.addContainerProperty(" ", CheckBox.class, null);
		container.addContainerProperty("Date Created", String.class, "");
		container.addContainerProperty("Name", String.class, "");
		container.addContainerProperty("Code", String.class, "");
		container.addContainerProperty("Description", String.class, "");
		container.addContainerProperty("Status", Button.class, null);
		
		
		Item trItem;
		container.addItem("row1");
		trItem = container.getItem("row1");
		Property<CheckBox> tdPropertyCheck =trItem.getItemProperty(" ");
		Property<String> tdPropertyUID =trItem.getItemProperty("Date Created");
		Property<String> tdPropertyUname =trItem.getItemProperty("Name");
		Property<String> tdPropertyFname =trItem.getItemProperty("Code");
		Property<String> tdPropertyLname =trItem.getItemProperty("Description");
		Property<Button> tdPropertyActions =trItem.getItemProperty("Status");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue("09/12/2014");
		tdPropertyUname.setValue("Control Account");
		tdPropertyFname.setValue("CON01");
		tdPropertyLname.setValue("Active");
		tdPropertyActions.setValue(actionsC);
		
	
		
		return container;
		}
	

}
