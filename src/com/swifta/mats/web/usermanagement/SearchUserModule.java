package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;
import com.swifta.mats.web.WorkSpace;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

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
	
	public static final String SESSION_SEARCH_USER = "search_user";
	public static final String SESSION_VAR_SEARCH_USER_DEFAULT = "agent";
	public static final String SESSION_SEARCH_USER_PARAM = "search_user_param";
	/*public static final String SESSION_VAR_SEARCH_USER = "merchant";
	public static final String SESSION_VAR_SEARCH_USER = "agent";
	public static final String SESSION_VAR_SEARCH_USER = "agent";
	public static final String SESSION_VAR_SEARCH_USER = "agent";
	public static final String SESSION_VAR_SEARCH_USER = "agent";
	public static final String SESSION_VAR_SEARCH_USER = "agent";*/
	
	
	
	
	
	private FormLayout searchC;
	private VerticalLayout searchResultsC;
	
	Window popup;
	
	public SearchUserModule(){
		
		
	}
	
	
	
	
	
	public FormLayout getSearchForm(String strUserType){
		
		
		FormLayout searchForm = new FormLayout();
		searchForm.setSizeUndefined();
		searchForm.setSpacing(true);
		searchForm.setMargin(false);
		searchForm.setStyleName("search_user_form");
		
		Embedded emb = new Embedded(null,new ThemeResource("img/search_user_icon.png"));
		emb.setDescription("Search users");
		emb.setStyleName("search_user_img");
		emb.setSizeUndefined();
		
		
		Label lbSearch = new Label("Search "+strUserType+" by: ");
		lbSearch.setSizeUndefined();
		lbSearch.setStyleName("label_search_user");
		lbSearch.setSizeUndefined();
		
		VerticalLayout searchUserHeader = new VerticalLayout();
		searchUserHeader.setHeightUndefined();
		searchUserHeader.setMargin(false);
		searchUserHeader.setSpacing(true);
		searchUserHeader.addComponent(emb);
		searchUserHeader.addComponent(lbSearch);
		searchUserHeader.setStyleName("search_user_header");
		
	    
		
		ArrayList<String> arrLTfCaptions = new ArrayList<String>();
		arrLTfCaptions.add(strUserType+" ID");
		arrLTfCaptions.add("Username");
		arrLTfCaptions.add("MSISDN");
		arrLTfCaptions.add("Company");
		arrLTfCaptions.add("First Name");
		arrLTfCaptions.add("Last Name");
		arrLTfCaptions.add("Others");
		
		searchForm.addComponent(searchUserHeader);
		final ArrayList<TextField> arrLTfs = addTfs(arrLTfCaptions, searchForm);
		Button btnSearch = new Button("Search");
		searchForm.addComponent(btnSearch);
		
		
		
		btnSearch.setIcon(FontAwesome.SEARCH);
		
		
		btnSearch.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -5894920456172825127L;

			@Override
			public void buttonClick(ClickEvent event) {
				StringBuilder strBuilder = new StringBuilder();
				for(TextField tF: arrLTfs){
					
					strBuilder.append(tF.getCaption());
					strBuilder.append("=");
					strBuilder.append(tF.getValue());
					strBuilder.append("&");
				}
				
				String strParams = strBuilder.toString();
				
				UI.getCurrent().getSession().setAttribute(ManageUserModule.SESSION_VAR_UMANAGE_SEARCH, null);
				UI.getCurrent().getSession().setAttribute(UserDetailsModule.SESSION_UDM, null);
				UI.getCurrent().getSession().setAttribute(ManageUserModule.SESSION_UMANAGE, ManageUserModule.SESSION_VAR_UMANAGE_SEARCH_RESULTS);
				UI.getCurrent().getSession().setAttribute(SESSION_SEARCH_USER_PARAM, strParams);

				if(WorkSpace.wsmu != null)
					WorkSpace.wsmu.wsmuModifier();
			}
		});
		
	



return searchForm;
}
	
	

private VerticalLayout getSearchResults(String strSearchParams){
	VerticalLayout searchResultsContainer = new VerticalLayout();
	searchResultsContainer.setSizeUndefined();
	searchResultsContainer.setSpacing(true);
	searchResultsContainer.setMargin(new MarginInfo(false, true, true, true));
	
	String[] arrAllParams = strSearchParams.split("&");
	
	StringBuilder strBuilder = new StringBuilder();
	
	
	String[] arrP = null;
	for(String strParam: arrAllParams){
		arrP = strParam.split("=");
		if(arrP.length == 2){
			strBuilder.append(arrP[0]);
			strBuilder.append(" of ");
			strBuilder.append(arrP[1]);
			strBuilder.append(", ");
		}
	}
	
	
	int iLastIndexOfComma = strBuilder.lastIndexOf(",");
	if(iLastIndexOfComma != -1){
		strBuilder.delete(iLastIndexOfComma, iLastIndexOfComma+1);
	}
	String strSearchResultsParams = null;
	iLastIndexOfComma = strBuilder.lastIndexOf(",");
	if(iLastIndexOfComma != -1){
		strBuilder.replace(iLastIndexOfComma, iLastIndexOfComma+1, " and");
		strSearchResultsParams = strBuilder.toString();
	}else{
		strSearchResultsParams = strBuilder.toString();
	}
	//Notification.show(Integer.toString(strSearchResultsParams.length()));
	strSearchResultsParams = strBuilder.toString();
	if(strSearchResultsParams.length()== 0){
		strSearchResultsParams = "Nothing.";
	}
	
	
	Label lbSearch = new Label("Match results for: "+strSearchResultsParams);
	lbSearch.setSizeUndefined();
	lbSearch.setStyleName("label_search_user");
	lbSearch.setSizeUndefined();
	
	VerticalLayout searchUserHeader = new VerticalLayout();
	searchUserHeader.setHeightUndefined();
	searchUserHeader.setWidthUndefined();
	searchUserHeader.setMargin(false);
	searchUserHeader.setSpacing(true);
	//searchUserHeader.addComponent(emb);
	searchUserHeader.addComponent(lbSearch);
	searchUserHeader.setStyleName("search_results_header");
	searchResultsContainer.addComponent(searchUserHeader);
	
	BE2 be = new BE2();
	
	IndexedContainer container = be.queryBackEnd(strSearchParams);
	
	PagedTableCustom tb = new PagedTableCustom("Results (Summary)");
	tb.setContainerDataSource(container);
	tb.setColumnIcon(" ", FontAwesome.CHECK_SQUARE_O);
	tb.setStyleName("tb_u_search_results");
	
	HorizontalLayout pnUserSearchResults = tb.createControls();
	pnUserSearchResults.setSizeFull();
	pnUserSearchResults.setMargin(false);
	pnUserSearchResults.setSpacing(false);
	
	searchResultsContainer.addComponent(pnUserSearchResults);
	searchResultsContainer.addComponent(tb);
	
	VerticalLayout actionBulkC = new VerticalLayout();
	actionBulkC.setWidth("100%");
	actionBulkC.setStyleName("c_action_bulk");
	
	CheckBox chkAll = new CheckBox();
	chkAll.setCaption("Select All");
	
	ComboBox cmbBulk = new ComboBox("Bulk Action");
	cmbBulk.addItem("Delete");
	cmbBulk.addItem("Link");
	
	Button btnBulkOk = new Button("Apply");
	btnBulkOk.setStyleName("btn_link");
	HorizontalLayout cBulk = new HorizontalLayout();
	cBulk.setSizeUndefined();
	cBulk.addComponent(cmbBulk);
	cBulk.addComponent(btnBulkOk);
	cBulk.setComponentAlignment(btnBulkOk, Alignment.BOTTOM_RIGHT);
	
	actionBulkC.addComponent(chkAll);
	actionBulkC.addComponent(cBulk);
	
	searchResultsContainer.addComponent(actionBulkC);
	return searchResultsContainer;
}

	
	
	
private ArrayList<TextField>addTfs(ArrayList<String> arrLTfCaptions, FormLayout searchForm){

	TextField tF;
	ArrayList<TextField> arrLTfs = new ArrayList<TextField>();
	for(String tFCaption: arrLTfCaptions){
			tF = new TextField(tFCaption);
			arrLTfs.add(tF);
			
			searchForm.addComponent(tF);
	}
	return arrLTfs;
}

	
	
	
	
	
	
	
	
	public Object sumModifier(String strSessionSearch, HorizontalLayout contentC){
		
		String strUserType = (String) UI.getCurrent().getSession().getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE);
			
			
			
		
		if(strSessionSearch.equals(ManageUserModule.SESSION_VAR_UMANAGE_SEARCH)){
			
			if(searchC != null){
				contentC.removeComponent(searchC);
			}
			
			searchC = getSearchForm(strUserType);
			contentC.addComponent(searchC);
			
			searchC.removeStyleName("c_search_user");
			contentC.setComponentAlignment(searchC, Alignment.MIDDLE_CENTER);
			contentC.setSizeFull();
			contentC.setSpacing(false);
			contentC.setMargin(true);
			return searchC;
			
		}else if(strSessionSearch.equals(ManageUserModule.SESSION_VAR_UMANAGE_SEARCH_RESULTS)){
			searchC.setSizeUndefined();
			searchC.setStyleName("c_search_user");
			contentC.setComponentAlignment(searchC, Alignment.TOP_RIGHT);
			
			String strSessionSearchParam = (String) UI.getCurrent().getSession().getAttribute(SearchUserModule.SESSION_SEARCH_USER_PARAM);
			if(strSessionSearchParam != null){
				
				if(searchResultsC != null){
					contentC.removeComponent(searchResultsC);
				}
				searchResultsC = getSearchResults(strSessionSearchParam);
				contentC.addComponent(searchResultsC);
				searchResultsC.setSizeUndefined();
				contentC.setComponentAlignment(searchResultsC, Alignment.TOP_LEFT);
				contentC.setSizeUndefined();
				contentC.setMargin(new MarginInfo(true, false, true, false));
				contentC.setSpacing(false);
				
			}
			
			return searchResultsC;
		}
		
		return null;
		
		
	}
	


}
