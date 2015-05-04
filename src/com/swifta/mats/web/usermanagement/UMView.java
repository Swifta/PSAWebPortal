package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;
import java.util.HashMap;

import com.swifta.mats.web.Initializer;
import com.swifta.mats.web.Login;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class UMView extends VerticalLayout implements View {

	private static final long serialVersionUID = 7453202011100914519L;

	boolean isCriteriaChanged = false;
	private TabSheet ts;
	private VerticalLayout tab = null;
	private String id = null;
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
	private VerticalLayout csr;
	private WorkSpaceManageUser wsmu;
	public static String UM = "user_management";
	private HashMap<String, String> hmPerms;

	public UMView(TabSheet ts) {
		this.ts = ts;
		tab = (VerticalLayout) ts.getSelectedTab();
		setPerms();
		addHeader();
		addMenu();
		d();

	}

	private void setPerms() {
		hmPerms = new HashMap<>();

		hmPerms.put("set_default", "/setdefaultaccount");
		hmPerms.put("activate", "/activationrequest");
		hmPerms.put("reset", "/passwordreset");

		hmPerms.put("unlink", "/unlinkaccount");
		hmPerms.put("link", "/linkaccount");

		hmPerms.put("unlock", "/unlockUserAccount");
		hmPerms.put("lock", "/lockUserAccount");

		hmPerms.put("man", "/manageusers");
		hmPerms.put("register", "/registration");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// ts.setSelectedTab(2);
		ts.setSelectedTab(Integer.parseInt(tab.getData().toString()));
		addMenu();

	}

	private void addHeader() {
		setMargin(true);
		Object user = UI.getCurrent().getSession().getAttribute("user");
		if (user == null)
			return;
		Button btnLogout = new Button("Logout");
		Label lbUsername = new Label("Hi, " + user.toString());
		lbUsername.setStyleName("lbel3");
		HorizontalLayout logoutC = new HorizontalLayout();
		logoutC.addComponent(lbUsername);
		logoutC.addComponent(btnLogout);
		logoutC.setStyleName("thy");

		addComponent(logoutC);
		setComponentAlignment(logoutC, Alignment.TOP_RIGHT);

		btnLogout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 3110441023351142376L;

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getSession().setAttribute("user", null);
				UI.getCurrent().getNavigator().navigateTo(Login.LOGIN);

			}
		});

	}

	private void addMenu() {
		addComponent(ts);
	}

	private void d() {
		if (wsmu == null)
			wsmu = new WorkSpaceManageUser();
		tab.addComponent(wsmu.getWorkSpaceManageUser());
	}

	private class WorkSpaceManageUser {

		private WorkSpaceManageUser() {
			setCoreUI();
			addModifier();

		}

		private void setCoreUI() {

			cParentLayout = new VerticalLayout();

			contentC = new HorizontalLayout();
			contentC.setWidthUndefined();
			contentC.setHeightUndefined();
			contentC.setStyleName("content_c");

			sum = new SearchUserModule(hmPerms);
			mm = getManageUserMenu(false);
			mm.setSizeUndefined();

			FormLayout cTemp = null;

			if (Initializer.setUserPermissions.contains(hmPerms.get("man"))) {
				cTemp = sum.getSearchForm("x");
				cs = cTemp;
			} else {
				if (aum == null) {
					aum = new AddUserModule();
					auf = aum.getAddUserForm();
					caddman.addComponent(auf);
					caddman.setSizeFull();
					caddman.setComponentAlignment(auf, Alignment.TOP_CENTER);

					FormLayout f = new FormLayout();
					f.addComponent(caddman);
					cTemp = f;
				}

			}
			csman.addComponent(cTemp);
			csman.setSizeFull();
			csman.setComponentAlignment(cTemp, Alignment.TOP_CENTER);

			contentC.addComponent(csman);

			contentC.setSizeFull();

			cParentLayout.addComponent(mm);
			cParentLayout.setComponentAlignment(mm, Alignment.TOP_CENTER);
			cParentLayout.addComponent(contentC);

		}

		private VerticalLayout getWorkSpaceManageUser() {
			return cParentLayout;

		}

		private VerticalLayout getManageUserMenu(boolean boolEditStatus) {

			VerticalLayout cManageUserMenu = new VerticalLayout();
			cManageUserMenu.setStyleName("c_u_manage_menu");
			cManageUserMenu.setSizeUndefined();

			HorizontalLayout cManageAndAddTab = new HorizontalLayout();

			final BtnTabLike btnManUser = new BtnTabLike("Manage", "man");
			// btnManUser.setStyleName("btn_tab_like btn_tab_like_active");

			final BtnTabLike btnAddUser = new BtnTabLike("Add New", "add");
			// cManageAndAddTab.addComponent(btnManUser);
			// cManageAndAddTab.addComponent(btnAddUser);

			BtnTabLike btnTemp = null;

			if (Initializer.setUserPermissions.contains(hmPerms.get("man"))) {
				cManageAndAddTab.addComponent(btnManUser);
				btnTemp = btnManUser;
			}
			if (Initializer.setUserPermissions
					.contains(hmPerms.get("register"))) {
				cManageAndAddTab.addComponent(btnAddUser);
			}

			if (btnTemp == null)
				btnTemp = btnAddUser;

			btnTemp.setEnabled(false);
			btnTemp.setStyleName("btn_tab_like btn_tab_like_active");

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
									modifier(event.getUriFragment());
								}
							});
		}

		private void modifier(String frag) {

			if (frag == null)
				return;
			if (frag.isEmpty())
				return;

			String vN = null;

			if (frag.indexOf('/') == -1)
				return;
			vN = frag.substring(0, frag.indexOf('/'));

			if (vN.indexOf('!') != -1) {
				if (!vN.equals("!" + UMView.UM)) {
					return;
				}

			} else if (!vN.equals(UMView.UM))
				return;

			String action = null;
			String aParam[] = new String[] {};

			if (frag.indexOf('?') == -1) {

				if (csr != null)
					csr.setVisible(false);
				cs.setVisible(true);

				return;
			} else {
				String param = frag.substring(frag.indexOf('?') + 1);
				if (param == null) {
					if (csr != null)
						csr.setVisible(false);
					cs.setVisible(true);
					return;
				} else if (param.trim().isEmpty()) {

					if (csr != null)
						csr.setVisible(false);
					cs.setVisible(true);

					return;
				} else {
					aParam = param.split("&");
					String arr[] = aParam[0].split("=");

					if (arr.length < 2) {

						if (csr != null)
							csr.setVisible(false);
						cs.setVisible(true);
						return;
					} else {
						action = aParam[0].split("=")[1];
					}
				}

			}

			StringBuilder strb = new StringBuilder();
			if (aParam.length < 0) {

				if (csr != null)
					csr.setVisible(false);
				cs.setVisible(true);
				return;
			}
			for (int i = 1; i < aParam.length; i++) {
				strb.append(aParam[i]);
				strb.append("&");
			}

			switch (action) {
			case "search_form": {
				if (csr != null)
					csr.setVisible(false);
				cs.setVisible(true);

				break;
			}
			case "search": {
				if (csr == null)
					csr = sum.getSearchResults(strb.toString());
				csr.setVisible(true);
				csman.addComponent(csr);
				csman.setExpandRatio(csr, 1.0f);
				csman.setStyleName("csman");
				cs.setVisible(false);
				break;
			}
			case "search_results": {
				if (csr == null)
					csr = sum.getSearchResults(strb.toString());
				csr.setVisible(true);
				csman.addComponent(csr);
				csman.setExpandRatio(csr, 1.0f);
				cs.setVisible(false);
				sum.search(strb.toString());
				break;
			}
			case "filter_search_results": {
				if (csr == null)
					csr = sum.getSearchResults(strb.toString());
				csr.setVisible(true);
				sum.addFilters(strb.toString());
				break;
			}

			}

		}
	}

}
