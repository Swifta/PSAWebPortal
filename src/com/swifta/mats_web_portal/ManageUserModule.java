package com.swifta.mats_web_portal;

import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

public class ManageUserModule {
	private String strSessionVarSearch;
	private String strSessionVarDetails;
	public static final String DEFAULT_UMANAGE_SESSION_VAR = null;
	public static final String UMANAGE_SESSION_SEARCH = "umanage_session_search";
	public static final String UMANAGE_SESSION_DETAILS = "umanage_session_details";
	private TextField tfUname;
	
	
	public ManageUserModule(){
		this.strSessionVarSearch = (String) UI.getCurrent().getSession().getAttribute(UMANAGE_SESSION_SEARCH);
		this.strSessionVarDetails = (String) UI.getCurrent().getSession().getAttribute(UMANAGE_SESSION_DETAILS);
	}
	public VerticalLayout getSearchContainer(){
				VerticalLayout searchContainer = new VerticalLayout();
				searchContainer.setSizeUndefined();
				searchContainer.setSpacing(false);
				searchContainer.setStyleName("c_search_user");
				searchContainer.setMargin(new MarginInfo(false, true, false, true));
		
				FormLayout searchForm = new FormLayout();
				searchForm.setSizeUndefined();
				searchForm.setSpacing(true);
				searchForm.setMargin(false);
				searchForm.setStyleName("search_user_form");
				
				Embedded emb = new Embedded(null,new ThemeResource("img/search_user_icon.png"));
				emb.setDescription("Search users");
				emb.setStyleName("search_user_img");
				emb.setSizeUndefined();
				
				
				Label lbSearch = new Label("Search users by: ");
				lbSearch.setSizeUndefined();
				lbSearch.setStyleName("label_search_user");
				lbSearch.setSizeUndefined();
				
				VerticalLayout searchUserHeader = new VerticalLayout();
				searchUserHeader.setHeightUndefined();
				searchUserHeader.setMargin(false);
				searchUserHeader.setSpacing(true);
				searchUserHeader.addComponent(emb);
				searchUserHeader.addComponent(lbSearch);
				searchContainer.addComponent(searchUserHeader);
				
				
				TextField tfUid = new TextField();
				tfUid.setCaption("User ID");
				
				tfUname = new TextField();
				tfUname.setCaption("Username");
				
				TextField tfFn = new TextField();
				tfFn.setCaption("First Name");
				
				TextField tfLn = new TextField();
				tfLn.setCaption("Last Name");
				
				TextField tfOthers = new TextField();
				tfOthers.setCaption("Others");
				
				Button btnSearch = new Button("Search");
				ThemeResource r = new ThemeResource("img/search_small.png");
				
				btnSearch.setIcon(r);
				//btnSearch.setHeight("60px");
				//btnSearch.setWidth("183px");
				//searchForm.addComponent(lbSearch);
				searchForm.addComponent(searchUserHeader);
				searchForm.addComponent(tfUid);
				searchForm.addComponent(tfUname);
				searchForm.addComponent(tfFn);
				searchForm.addComponent(tfLn);
				searchForm.addComponent(tfOthers);
				searchForm.addComponent(btnSearch);
				searchContainer.addComponent(searchForm);
				searchContainer.setComponentAlignment(searchForm, Alignment.TOP_RIGHT);
				
				
				
				btnSearch.addClickListener(new Button.ClickListener() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = -5894920456172825127L;
		
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().getSession().setAttribute(ManageUserModule.UMANAGE_SESSION_SEARCH, tfUname.getValue());
						UI.getCurrent().getNavigator().navigateTo(WorkAreax.WORK_AREA+"/search_user_results");
					}
				});
				
			

		
		
		return searchContainer;
	}
	
	
	public VerticalLayout getSearchResults(){
		VerticalLayout searchContainer = new VerticalLayout();
		
		VerticalLayout searchResultsContainer = new VerticalLayout();
		searchResultsContainer.setSizeUndefined();
		searchResultsContainer.setSpacing(true);
		searchResultsContainer.setMargin(new MarginInfo(false, true, true, true));
		
		
		VerticalLayout searchUserHeader = new VerticalLayout();
		searchUserHeader.setWidth("100%");
		searchUserHeader.setHeightUndefined();
		searchUserHeader.setMargin(true);
		searchUserHeader.setSpacing(true);
		searchContainer.setStyleName("c_u_search_results");
		
		Embedded emb = new Embedded(null,new ThemeResource("img/search_user_1.png"));
		emb.setDescription("Search users");
		emb.setStandby("search_user_img");
		searchUserHeader.addComponent(emb);
		searchUserHeader.setComponentAlignment(emb, Alignment.TOP_RIGHT);
		
		Button btnSearch = new Button();
		btnSearch.setDescription("Search");
		ThemeResource r = new ThemeResource("img/search_small.png");
		btnSearch.setIcon(r);
		searchUserHeader.addComponent(btnSearch);
		searchUserHeader.setComponentAlignment(btnSearch, Alignment.TOP_RIGHT);
		
		Label lbSearchResults = new Label("Search Results...");
		lbSearchResults.setSizeUndefined();
		lbSearchResults.setStyleName("label_search_results");
		searchUserHeader.addComponent(lbSearchResults);
		searchUserHeader.setComponentAlignment(lbSearchResults, Alignment.TOP_RIGHT);
		
		//searchContainer.addComponent(searchUserHeader);
		
		
		SearchUserModule sum = new SearchUserModule();
		IndexedContainer container = sum.queryBackEnd();
		
		
		PagedTable tb = new PagedTable("Search results for: \"admin\"(Summary)");
		tb.setContainerDataSource(container);
		tb.setColumnIcon(" ", FontAwesome.CHECK_SQUARE_O);
		tb.setPageLength(5);
		tb.setStyleName("tb_u_search_results");
		
		HorizontalLayout pnUserSearchResults = tb.createControls();
		pnUserSearchResults.setSizeFull();
		pnUserSearchResults.setMargin(false);
		pnUserSearchResults.setSpacing(false);
		
		searchResultsContainer.addComponent(pnUserSearchResults);
		searchResultsContainer.addComponent(tb);
		//tb.setHeight("219px");
		
		VerticalLayout actionBulkC = new VerticalLayout();
		actionBulkC.setWidth("100%");
		actionBulkC.setStyleName("c_action_bulk");
		
		CheckBox chkAll = new CheckBox();
		chkAll.setCaption("Select All");
		
		ComboBox cmbBulk = new ComboBox("Bulk Action");
		cmbBulk.addItem("Delete");
		cmbBulk.addItem("Link");
		
		actionBulkC.addComponent(chkAll);
		actionBulkC.addComponent(cmbBulk);
		
		
		searchResultsContainer.addComponent(actionBulkC);
		searchContainer.addComponent(searchResultsContainer);
		searchContainer.setComponentAlignment(searchResultsContainer, Alignment.TOP_RIGHT);
		
		
		
		
		
		btnSearch.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 7182067560976379938L;

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getSession().setAttribute(ManageUserModule.UMANAGE_SESSION_SEARCH, null);
				UI.getCurrent().getNavigator().navigateTo(WorkAreax.WORK_AREA+"/manage_user");
				
			}
		});
		
		
		
		return searchContainer;
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public VerticalLayout getUserOperationContainer(){
		VerticalLayout userOperationContainer = new VerticalLayout();
		
		HorizontalLayout formContainer = new HorizontalLayout();
		formContainer.setSizeUndefined();
		formContainer.setSpacing(true);
		formContainer.setMargin(new MarginInfo(false, true, true, true));
		
		FormLayout operationFormUserProfile = new FormLayout();
		operationFormUserProfile.setSizeUndefined();
		operationFormUserProfile.setSpacing(true);
		operationFormUserProfile.setMargin(true);
		operationFormUserProfile.setStyleName("operation_form_user_profile");
		
		FormLayout operationFormAccountProfile = new FormLayout();
		operationFormAccountProfile.setSizeUndefined();
		operationFormAccountProfile.setSpacing(true);
		operationFormAccountProfile.setMargin(true);
		operationFormAccountProfile.setStyleName("operation_form_account_profile");
		
		formContainer.addComponent(operationFormUserProfile);
		formContainer.addComponent(operationFormAccountProfile);
		
		Embedded emb = new Embedded(null,new ThemeResource("img/user_operation_1.png"));
		emb.setDescription("Profile operations.");
		emb.setStyleName("user_operation_img");
		
		Label lbOperationU = new Label("User");
		Label lbOperationA = new Label("Account");
		Label lbOperationHeader = new Label("Choose Operation...");
		lbOperationHeader.setStyleName("label_operation_header");
		lbOperationHeader.setSizeUndefined();
		
		VerticalLayout userOperationHeader = new VerticalLayout();
		userOperationHeader.setWidthUndefined();
		userOperationHeader.setHeightUndefined();
		userOperationHeader.setMargin(new MarginInfo(true, true, false, true));
		userOperationHeader.setSpacing(true);
		
		userOperationHeader.addComponent(emb);
		userOperationHeader.addComponent(lbOperationHeader);
		userOperationHeader.setComponentAlignment(lbOperationHeader, Alignment.TOP_CENTER);
		userOperationHeader.setComponentAlignment(emb, Alignment.TOP_LEFT);
		userOperationContainer.addComponent(userOperationHeader);
		
		
		Button btnDetails = new Button("Details");
		btnDetails.setStyleName(ValoTheme.BUTTON_PRIMARY);
		Button btnEdit = new Button("Edit");
		Button btnLink = new Button("Link");
		Button btnULog = new Button("Activity Log");
		Button btnDelete = new Button("Delete");
		
		btnDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		btnEdit.setIcon(FontAwesome.EDIT);
		btnLink.setIcon(FontAwesome.LINK);
		btnDelete.setIcon(FontAwesome.RECYCLE);
		
		Button btnAuthentication = new Button("Authentication");
		Button btnAuthorization = new Button("Authorization");
		Button btnACLog = new Button("Change Log");
		
		operationFormUserProfile.addComponent(lbOperationU);
		operationFormUserProfile.addComponent(btnDetails);
		operationFormUserProfile.addComponent(btnEdit);
		operationFormUserProfile.addComponent(btnLink);
		operationFormUserProfile.addComponent(btnDelete);
		operationFormUserProfile.addComponent(btnULog);
		
		
		
		operationFormAccountProfile.addComponent(lbOperationA);
		operationFormAccountProfile.addComponent(btnAuthentication);
		operationFormAccountProfile.addComponent(btnAuthorization);
		operationFormAccountProfile.addComponent(btnACLog);
		
		
		
		userOperationContainer.addComponent(formContainer);
		//userOperationContainer.setWidth("100%");
		//userOperationContainer.setHeight("100%");
		userOperationContainer.setSizeUndefined();
		userOperationContainer.setStyleName("u_operation_container");
		
		
		if(strSessionVarSearch == null){
			operationFormUserProfile.setEnabled(false);
			operationFormAccountProfile.setEnabled(false);
			emb.setEnabled(false);
			lbOperationHeader.setEnabled(false);
			btnDetails.setEnabled(false);
			btnEdit.setEnabled(false);
			btnLink.setEnabled(false);
			btnDelete.setEnabled(false);
			btnULog.setEnabled(false);
			btnAuthentication.setEnabled(false);
			btnAuthorization.setEnabled(false);
			btnACLog.setEnabled(false);
		}
		
		btnDetails.addClickListener(new Button.ClickListener(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -4281417789039287912L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				UI.getCurrent().getSession().setAttribute(UMANAGE_SESSION_DETAILS, "details");
				UI.getCurrent().getNavigator().navigateTo(WorkAreax.WORK_AREA+"/user_details");
				
			}
			
		});
		

		return userOperationContainer;
	}
	
	
	
	
	
	
	
	
	public VerticalLayout getDetailsContainer(){
	
			VerticalLayout detailsContainer = new VerticalLayout();
			detailsContainer.setSizeUndefined();
			detailsContainer.setSpacing(true);
			detailsContainer.setMargin(false);
			
			
			VerticalLayout searchUserHeader = new VerticalLayout();
			searchUserHeader.setWidth("100%");
			searchUserHeader.setHeightUndefined();
			searchUserHeader.setMargin(true);
			searchUserHeader.setSpacing(true);
			
			Embedded emb = new Embedded(null,new ThemeResource("img/user_details_1.png"));
			emb.setDescription("User details");
			emb.setStandby("search_user_img");
			searchUserHeader.addComponent(emb);
			searchUserHeader.setComponentAlignment(emb, Alignment.TOP_RIGHT);
			
			Button btnSearch = new Button();
			btnSearch.setDescription("Search");
			ThemeResource r = new ThemeResource("img/search_small.png");
			btnSearch.setIcon(r);
			searchUserHeader.addComponent(btnSearch);
			searchUserHeader.setComponentAlignment(btnSearch, Alignment.TOP_RIGHT);
			
			Label lbSearchResults = new Label("User Details");
			lbSearchResults.setSizeUndefined();
			lbSearchResults.setStyleName("label_search_results");
			searchUserHeader.addComponent(lbSearchResults);
			searchUserHeader.setComponentAlignment(lbSearchResults, Alignment.TOP_RIGHT);
			
			detailsContainer.addComponent(searchUserHeader);
			
			
			UserDetailsModule udm = new UserDetailsModule();
			Table tDetails = udm.getUserDetails();
			tDetails.setSizeUndefined();
			tDetails.setSelectable(true);
			
			detailsContainer.addComponent(tDetails);
			
			detailsContainer.setComponentAlignment(tDetails, Alignment.TOP_RIGHT);
			
			
			
			
			btnSearch.addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 7182067560976379938L;

				@Override
				public void buttonClick(ClickEvent event) {
					UI.getCurrent().getSession().setAttribute(ManageUserModule.UMANAGE_SESSION_SEARCH, null);
					UI.getCurrent().getNavigator().navigateTo(WorkAreax.WORK_AREA+"/manage_user");
					
				}
			});
		
		return detailsContainer;
	}
}
