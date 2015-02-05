package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;

import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class WorkSpaceManageUser {
	/**
	 * 
	 */

	private HorizontalLayout contentC;

	private AddUserModule aum;

	private VerticalLayout cParentLayout;

	private VerticalLayout mm;
	private VerticalLayout auf;
	private SearchUserModule sum;

	private HorizontalLayout csman = new HorizontalLayout();
	private HorizontalLayout caddman = new HorizontalLayout();
	private FormLayout cs;
	public static ArrayList<String> prevSearchFrag = new ArrayList<>();

	public WorkSpaceManageUser() {
		setCoreUI();
		addModifier();

	}

	private void setCoreUI() {

		cParentLayout = new VerticalLayout();

		contentC = new HorizontalLayout();
		contentC.setWidthUndefined();
		contentC.setHeightUndefined();
		contentC.setStyleName("content_c");

		sum = new SearchUserModule();
		mm = getManageUserMenu(false);
		mm.setSizeUndefined();
		cs = sum.getSearchForm("x");
		csman.addComponent(cs);
		csman.setSizeFull();
		csman.setComponentAlignment(cs, Alignment.TOP_CENTER);

		contentC.addComponent(csman);

		contentC.setSizeFull();

		cParentLayout.addComponent(mm);
		cParentLayout.setComponentAlignment(mm, Alignment.TOP_CENTER);
		cParentLayout.addComponent(contentC);

	}

	public VerticalLayout getWorkSpaceManageUser() {
		return cParentLayout;

	}

	private VerticalLayout getManageUserMenu(boolean boolEditStatus) {

		VerticalLayout cManageUserMenu = new VerticalLayout();
		cManageUserMenu.setStyleName("c_u_manage_menu");
		cManageUserMenu.setSizeUndefined();

		HorizontalLayout cManageAndAddTab = new HorizontalLayout();

		final BtnTabLike btnManUser = new BtnTabLike("Manage", "man");
		btnManUser.setStyleName("btn_tab_like btn_tab_like_active");

		final BtnTabLike btnAddUser = new BtnTabLike("Add New", "add");
		cManageAndAddTab.addComponent(btnManUser);
		cManageAndAddTab.addComponent(btnAddUser);
		btnManUser.setEnabled(false);
		cManageUserMenu.addComponent(cManageAndAddTab);

		btnManUser.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				btnAddUser.setEnabled(true);
				btnManUser.setEnabled(false);
				btnManUser.setStyleName("btn_tab_like btn_tab_like_active");
				btnAddUser.setStyleName("btn_tab_like");
				caddman = (HorizontalLayout) contentC.getComponent(0);
				contentC.replaceComponent(caddman, csman);

			}
		});

		btnAddUser.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				btnAddUser.setEnabled(false);
				btnManUser.setEnabled(true);
				btnAddUser.setStyleName("btn_tab_like btn_tab_like_active");
				btnManUser.setStyleName("btn_tab_like");

				csman = (HorizontalLayout) contentC.getComponent(0);
				contentC.replaceComponent(csman, caddman);

				if (aum == null) {
					aum = new AddUserModule();
					auf = aum.getAddUserForm();
					caddman.addComponent(auf);
					caddman.setSizeFull();
					caddman.setComponentAlignment(auf, Alignment.TOP_CENTER);
				}

			}
		});

		return cManageUserMenu;

	}

	private void addModifier() {
		UI.getCurrent()
				.getPage()
				.addUriFragmentChangedListener(
						new Page.UriFragmentChangedListener() {

							private static final long serialVersionUID = 1L;

							@Override
							public void uriFragmentChanged(
									UriFragmentChangedEvent event) {

								modifier(UI.getCurrent().getPage()
										.getUriFragment());
							}
						});
	}

	private void modifier(String frag) {

		String param = frag.substring(frag.indexOf('?') + 1);
		if (param == null)
			return;

		String aParam[] = param.split("&");
		if (aParam.length < 2)
			return;
		String action = aParam[0].split("=")[1];

		StringBuilder strb = new StringBuilder();
		for (int i = 1; i < aParam.length; i++) {
			strb.append(aParam[i]);
			strb.append("&");
		}
		if (!prevSearchFrag.contains(frag)) {
			switch (action) {
			case "search": {

				VerticalLayout csr = sum.getSearchResults(strb.toString());
				csman.addComponent(csr);
				csman.setExpandRatio(csr, 1.0f);
				csman.setStyleName("csman");
				cs.setVisible(false);
				break;
			}
			case "search_results": {
				csman.removeAllComponents();
				VerticalLayout csr = sum.getSearchResults(strb.toString());
				csman.addComponent(csr);
				csman.setExpandRatio(csr, 1.0f);
				break;
			}
			case "filter_search_results": {

				sum.addFilters(strb.toString());
				break;
			}
			}

			prevSearchFrag.add(frag);
		}

	}
}