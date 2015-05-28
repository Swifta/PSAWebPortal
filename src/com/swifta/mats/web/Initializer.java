package com.swifta.mats.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.swifta.mats.web.accountprofile.ProfileView;
import com.swifta.mats.web.report.ReportView;
import com.swifta.mats.web.settings.SettingsView;
import com.swifta.mats.web.transactions.TransView;
import com.swifta.mats.web.usermanagement.UMView;
import com.swifta.mats.web.utils.LoginService;
import com.vaadin.navigator.View;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Initializer {

	private TabSheet m;
	private View crp, cum, ct, cap, cs, cd;
	private HashMap<String, String> hmTabPermissions;
	public static Set<String> setUserPermissions;

	TabSheet getTS() {

		setUserPermissions();
		setTabPermissions();
		createTabSheet();

		return m;
	}

	private void setUserPermissions() {
		setUserPermissions = new HashSet<>();
		for (String perm : LoginService.getUserPermissions()) {
			setUserPermissions.add(perm.toLowerCase());
			System.out.println(perm);
		}

		/*
		 * setUserPermissions.add("/manageusers"); //
		 * setUserPermissions.add("/setupservicefeesandcommission"); //
		 * setUserPermissions.add("/viewsetfeesandcommission");
		 * 
		 * setUserPermissions.add("/viewsettings");
		 * 
		 * setUserPermissions.add("/setdefaultaccount");
		 * setUserPermissions.add("/passwordReset");
		 * setUserPermissions.add("/lockUserAccount");
		 * 
		 * setUserPermissions.add("/activationrequest");
		 * 
		 * setUserPermissions.add("/unlinkaccount");
		 * setUserPermissions.add("/unlockUserAccount"); //
		 * setUserPermissions.add("/linkaccount");
		 * setUserPermissions.add("/manageusers");
		 * setUserPermissions.add("/registration");
		 * 
		 * setUserPermissions.add("/viewreport");
		 * setUserPermissions.add("/urn:getfeesandcommissionreport");
		 * setUserPermissions.add("/urn:getreportsummaryreport");
		 * setUserPermissions.add("/urn:gettransactiondetailreport");
		 * setUserPermissions.add("/urn:gettransactionreport");
		 * setUserPermissions.add("/exportreport");
		 * setUserPermissions.add("/urn:getfloatmanagementreport");
		 * setUserPermissions.add("/urn:getministatementreport");
		 */

	}

	private void setTabPermissions() {
		hmTabPermissions = new HashMap<>();
		hmTabPermissions.put("settings", "/viewsettings");
		hmTabPermissions.put("dashboard", "/viewdashboard");
		hmTabPermissions.put("report", "/viewreport");
		hmTabPermissions.put("manage_users", "/manageusers");
		hmTabPermissions.put("register_users", "/registration");

	}

	private void createTabSheet() {
		m = new TabSheet();

		VerticalLayout ap = new VerticalLayout();
		ap.setId("ap_init");
		ap.setData(m.getTabPosition(m.addTab(ap, "My Profile")));

		VerticalLayout u = new VerticalLayout();
		u.setId("ds_init");
		if (setUserPermissions.contains(hmTabPermissions.get("dashboard")))
			u.setData(m.getTabPosition(m.addTab(u, "Dashbord")));

		VerticalLayout v = new VerticalLayout();
		// tf = new TextField();
		// v.addComponent(tf);
		v.setId("rp_init");
		if (setUserPermissions.contains(hmTabPermissions.get("report")))
			v.setData(m.getTabPosition(m.addTab(v, "Reports")));

		VerticalLayout um = new VerticalLayout();
		// tf = new TextField();
		// v.addComponent(tf);
		um.setId("um_init");
		if (setUserPermissions.contains(hmTabPermissions.get("manage_users"))
				|| setUserPermissions.contains(hmTabPermissions
						.get("register_users")))
			um.setData(m.getTabPosition(m.addTab(um, "User Management")));

		// VerticalLayout tx = new VerticalLayout();
		// tf = new TextField();
		// v.addComponent(tf);
		// tx.setId("tx_init");
		// tx.setData(m.getTabPosition(m.addTab(tx, "Transactions")));

		VerticalLayout st = new VerticalLayout();
		st.setId("st_init");

		if (setUserPermissions.contains(hmTabPermissions.get("settings")))
			st.setData(m.getTabPosition(m.addTab(st, "Settings")));
		// m.getSelectedTab().

		m.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				String id = m.getSelectedTab().getId();
				switch (id) {
				case "ap_init": {

					UI.getCurrent().getNavigator().navigateTo(ProfileView.Prof);
					break;
				}

				case "ds_init": {

					if (cd == null) {
						cd = new Main(m);
						UI.getCurrent().getNavigator().addView("dashboard", cd);
					}
					UI.getCurrent().getNavigator().navigateTo(Main.WS);

					break;
				}

				case "rp_init": {

					if (crp == null) {
						crp = new ReportView(m);
						UI.getCurrent().getNavigator().addView("report", crp);
					}
					UI.getCurrent().getNavigator().navigateTo("report");
					break;
				}

				case "um_init": {
					if (cum == null) {
						cum = new UMView(m);
						UI.getCurrent().getNavigator()
								.addView("user_management", cum);
					}
					UI.getCurrent().getNavigator()
							.navigateTo("user_management");
					break;
				}

				case "tx_init": {

					if (ct == null) {
						ct = new TransView(m);
						UI.getCurrent().getNavigator()
								.addView("transactions", ct);
					}
					UI.getCurrent().getNavigator().navigateTo("transactions");
					break;
				}

				case "st_init": {
					if (cs == null) {
						cs = new SettingsView(m);
						UI.getCurrent().getNavigator().addView("settings", cs);
					}
					UI.getCurrent().getNavigator().navigateTo("settings");

					break;
				}

				default: {

					if (UI.getCurrent().getSession().getAttribute("user") != null) {
						UI.getCurrent().getNavigator()
								.navigateTo("account_profile");
					} else {
						UI.getCurrent().getNavigator().navigateTo(Login.LOGIN);
					}

					break;
				}
				}

			}

		});

	}
}
