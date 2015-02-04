package com.swifta.mats.web;

import java.util.HashMap;

import com.swifta.mats.web.accountprofile.ProfileView;
import com.swifta.mats.web.report.ReportView;
import com.swifta.mats.web.settings.SettingsView;
import com.swifta.mats.web.transactions.TransView;
import com.swifta.mats.web.usermanagement.UMView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Initializer {

	private TabSheet m;

	TabSheet getTS(HashMap<String, Integer> hm) {
		createTabSheet(hm);
		return m;
	}

	private void createTabSheet(HashMap<String, Integer> hm) {
		m = new TabSheet();
		VerticalLayout u = new VerticalLayout();
		TextField tf = new TextField();
		// u.addComponent(tf);
		u.setId("ds_init");
		m.addTab(u, "Dashbord");

		VerticalLayout v = new VerticalLayout();
		tf = new TextField();
		// v.addComponent(tf);
		v.setId("rp_init");
		m.addTab(v, "Reports");

		VerticalLayout um = new VerticalLayout();
		tf = new TextField();
		// v.addComponent(tf);
		um.setId("um_init");
		m.addTab(um, "User Management");

		VerticalLayout tx = new VerticalLayout();
		tf = new TextField();
		// v.addComponent(tf);
		tx.setId("tx_init");
		m.addTab(tx, "Transactions");

		VerticalLayout ap = new VerticalLayout();
		ap.setId("ap_init");
		m.addTab(ap, "Account Profile");
		m.getTab(ap).setEnabled(false);

		VerticalLayout st = new VerticalLayout();
		st.setId("st_init");
		m.addTab(st, "Settings");
		// m.getSelectedTab().

		hm.put("dashboard", m.getTabPosition(m.getTab(u)));
		hm.put("user_management", m.getTabPosition(m.getTab(um)));
		hm.put("report", m.getTabPosition(m.getTab(v)));
		hm.put("settings", m.getTabPosition(m.getTab(st)));
		hm.put("transactions", m.getTabPosition(m.getTab(tx)));
		hm.put("account_profile", m.getTabPosition(m.getTab(ap)));

		m.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				String id = m.getSelectedTab().getId();
				switch (id) {
				case "ds":
				case "ds_init": {

					UI.getCurrent().getNavigator()
							.navigateTo(Main.WS);

					break;
				}
				case "rp":
				case "rp_init": {
					UI.getCurrent().getNavigator()
							.addView("report", new ReportView(m));
					UI.getCurrent().getNavigator().navigateTo("report");
					break;
				}

				case "um_init":
				case "um": {
					UI.getCurrent().getNavigator()
							.addView("user_management", new UMView(m));
					UI.getCurrent().getNavigator()
							.navigateTo("user_management");
					break;
				}

				case "tx_init":
				case "tx": {
					UI.getCurrent().getNavigator()
							.addView("transactions", new TransView(m));
					UI.getCurrent().getNavigator().navigateTo("transactions");
					break;
				}

				case "ap_init":
				case "ap": {
					UI.getCurrent().getNavigator()
							.addView("account_profile", new ProfileView(m));
					UI.getCurrent().getNavigator()
							.navigateTo("account_profile");
					break;
				}

				case "st_init":
				case "st": {
					UI.getCurrent().getNavigator()
							.addView("settings", new SettingsView(m));
					UI.getCurrent().getNavigator().navigateTo("settings");
					break;
				}

				default: {

					if (UI.getCurrent().getSession().getAttribute("user") != null)
						UI.getCurrent().getNavigator()
								.navigateTo(Main.WS);
					else
						UI.getCurrent().getNavigator().navigateTo(Login.LOGIN);

					break;
				}
				}

			}

		});

	}
}
