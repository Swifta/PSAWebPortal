package com.swifta.mats.web;

import com.swifta.mats.web.accountprofile.ProfileView;
import com.swifta.mats.web.report.ReportView;
import com.swifta.mats.web.settings.SettingsView;
import com.swifta.mats.web.transactions.TransView;
import com.swifta.mats.web.usermanagement.UMView;
import com.vaadin.navigator.View;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Initializer {

	private TabSheet m;
	private View crp, cum, ct, cap, cs;

	TabSheet getTS() {
		createTabSheet();
		return m;
	}

	private void createTabSheet() {
		m = new TabSheet();
		VerticalLayout u = new VerticalLayout();
		u.setId("ds_init");
		m.addTab(u, "Dashbord");

		VerticalLayout v = new VerticalLayout();
		// tf = new TextField();
		// v.addComponent(tf);
		v.setId("rp_init");
		m.addTab(v, "Reports");

		VerticalLayout um = new VerticalLayout();
		// tf = new TextField();
		// v.addComponent(tf);
		um.setId("um_init");
		m.addTab(um, "User Management");

		VerticalLayout tx = new VerticalLayout();
		// tf = new TextField();
		// v.addComponent(tf);
		tx.setId("tx_init");
		m.addTab(tx, "Transactions");

		VerticalLayout ap = new VerticalLayout();
		ap.setId("ap_init");
		m.addTab(ap, "Account Profile");
		// m.getTab(ap).setEnabled(false);

		VerticalLayout st = new VerticalLayout();
		st.setId("st_init");
		m.addTab(st, "Settings");
		// m.getSelectedTab().

		m.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				String id = m.getSelectedTab().getId();
				switch (id) {
				case "ds_init": {
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

				case "ap_init": {
					if (cap == null) {
						cap = new ProfileView(m);

						UI.getCurrent().getNavigator()
								.addView("account_profile", cap);
					}
					UI.getCurrent().getNavigator()
							.navigateTo("account_profile");
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
						UI.getCurrent().getNavigator().navigateTo("dashbord");
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
