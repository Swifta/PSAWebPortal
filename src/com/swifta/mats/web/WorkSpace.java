package com.swifta.mats.web;

import com.swifta.mats.web.accountprofile.WorkSpaceManageProfile;
import com.swifta.mats.web.dashboard.BarChartDash;
import com.swifta.mats.web.dashboard.Dashboard;
import com.swifta.mats.web.dashboard.PiechartDash;
import com.swifta.mats.web.report.Report;
import com.swifta.mats.web.settings.Settings;
import com.swifta.mats.web.transactions.Transactions;
import com.swifta.mats.web.usermanagement.WorkSpaceManageUser;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class WorkSpace extends VerticalLayout implements View,
		TabSheet.SelectedTabChangeListener {

	public static WorkSpaceManageUser wsmu;
	public static WorkSpaceManageProfile wsmp;
	VerticalLayout cwsmu;
	VerticalLayout cwsmp;
	VerticalLayout dashboard3;
	private Embedded emb;
	private Button btnLogout;
	Label la = new Label("Filter by: ");
	ComboBox comboGF = new ComboBox("Please select...");
	DateField dat = new DateField();
	DateField dat2 = new DateField();
	Button filter = new Button("Filter");
	HorizontalLayout pi;

	private static final long serialVersionUID = 4370608410523325533L;

	public static final String WORK_SPACE = "";
	VerticalLayout dashboard6 = new VerticalLayout();
	TabSheet tabsheet1 = new TabSheet();

	public WorkSpace() {
		wsmu = new WorkSpaceManageUser();
		wsmp = new WorkSpaceManageProfile();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// Notification.show("In WorkSpace");
		// setSizeFull();
		setMargin(true);
		// setStyleName("parent_layout");

		btnLogout = new Button("Logout");

		Label lbUsername = new Label("Hi, "
				+ UI.getCurrent().getSession().getAttribute("user").toString());
		lbUsername.setStyleName("lbel3");
		HorizontalLayout logoutC = new HorizontalLayout();

		logoutC.addComponent(lbUsername);
		logoutC.addComponent(btnLogout);
		logoutC.setStyleName("thy");

		addComponent(logoutC);
		setComponentAlignment(logoutC, Alignment.TOP_RIGHT);

		final VerticalLayout layout = new VerticalLayout();

		// tabsheet1.setHeight("560px");
		tabsheet1.setSizeFull();
		tabsheet1.addStyleName("tabref");

		// Tab1 Dashboard
		VerticalLayout dashboard1 = new VerticalLayout();
		dashboard1.setImmediate(true);
		dashboard1.setCaption("Test1");
		Dashboard dash = new Dashboard();
		PiechartDash pie = new PiechartDash();
		pi = new HorizontalLayout();
		HorizontalLayout lut = new HorizontalLayout();
		FormLayout former = new FormLayout();
		final BarChartDash bar = new BarChartDash();

		dat.setCaption("Start Date");
		dat2.setCaption("End Date");
		comboGF.addItem("Transaction Type");
		comboGF.addItem("Fees Account");

		former.addComponent(la);
		former.addComponent(comboGF);
		former.addComponent(dat);
		former.addComponent(dat2);
		former.addComponent(filter);
		former.setWidth("100px");
		final Chart barChart = (Chart) bar.getChart();
		final Chart pieChart = (Chart) pie.getChart();
		pi.addComponent(pieChart);
		pi.addComponent(barChart);

		filter.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 7867132630286287368L;

			@Override
			public void buttonClick(ClickEvent event) {

				// XAxis xAxis = new XAxis();
				// Set<String> stxns = hm.keySet();
				// String[] txns = new String[stxns.size()];
				// stxns.toArray(txns);

				DataSeries series = new DataSeries();
				series.add(new DataSeriesItem("xccd", 20));
				series.add(new DataSeriesItem("kksksd", 30));
				series.add(new DataSeriesItem("orri", 50));

				// while (itr.hasNext()) {
				// Entry<String, Double> e = itr.next();
				// series.add(new DataSeriesItem(e.getKey(), e.getValue()));
				// }
				// series.add(new DataSeriesItem("Cash out", 32));
				// DataSeriesItem chrome = new DataSeriesItem("Airtime", 18);
				// chrome.setSliced(true);
				// chrome.setSelected(true);
				// series.add(chrome);

				PiechartDash.conf.setSeries(series);
				pieChart.drawChart();
				bar.xAxis
						.setCategories(new String[] { "xxmmx", "kkxkx", "skks" });
				bar.serie.setData(new Number[] { 20, 50, 30 });
				barChart.drawChart();

			}
		});

		lut.setSizeFull();
		lut.addComponent(former);
		lut.addComponent(pi);
		// lut.addComponent(bar.getChart());
		lut.setExpandRatio(former, 2);
		lut.setExpandRatio(pi, 6);

		// dashboard1.addComponent(former, 0);
		dashboard1.addComponent(lut);
		tabsheet1.addTab(dashboard1, "Dashboard", null);

		// Tab2 Report
		VerticalLayout dashboard2 = new VerticalLayout();
		dashboard2.setImmediate(true);
		dashboard2.setCaption("Test2");
		Report rep = new Report();
		dashboard2.addComponent(rep.Addlabel());
		tabsheet1.addTab(dashboard2, "Report", null);

		// Tab3 UserManager

		cwsmu = wsmu.getWorkSpaceManageUser();
		tabsheet1.addTab(cwsmu, "User Management", null);

		// VerticalLayout dashboard4 = new VerticalLayout();
		// dashboard4.setImmediate(true);
		// dashboard4.setHeight("500px");
		// dashboard4.setWidth("1000px");
		// dashboard4.setCaption("Test4");
		// AccountProfile account = new AccountProfile();
		// dashboard4.addComponent(account.Addlabel());

		cwsmp = WorkSpace.wsmp.getWorkSpaceAccountProfile();
		tabsheet1.addTab(cwsmp, "Account Profile", null);

		VerticalLayout dashboard5 = new VerticalLayout();
		dashboard5.setImmediate(true);
		// dashboard5.setHeight("500px");
		// dashboard5.setWidth("1000px");
		dashboard5.setCaption("Test5");
		Transactions transaction = new Transactions();
		dashboard5.addComponent(transaction.Addlabel());

		tabsheet1.addTab(dashboard5, "Transactions", null);

		dashboard6.setImmediate(true);
		dashboard6.setMargin(true);
		// dashboard6.setSizeUndefined();
		// dashboard6.setHeight("500px");
		// dashboard6.setWidth("1000px");
		dashboard6.setCaption("Test6");
		Settings setting = new Settings();
		HorizontalLayout layoutnew = setting.Addlabel();
		layoutnew.setSizeUndefined();
		dashboard6.addComponent(layoutnew);
		tabsheet1.addTab(dashboard6, "Settings", null);
		dashboard6.setComponentAlignment(layoutnew, Alignment.TOP_LEFT);

		layout.addComponent(tabsheet1);
		layout.setSizeFull();

		btnLogout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 3110441023351142376L;

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getSession().setAttribute("user", null);
				// UI.getCurrent().getSession().setAttribute(ManageUserModule.SESSION_UMANAGE,
				// null);
				// UI.getCurrent().getSession().setAttribute(WorkSpaceManageUser.SESSION_WORK_AREA,
				// null);
				UI.getCurrent().getNavigator().navigateTo(WORK_SPACE);

			}
		});

		comboGF.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 2950767419735127495L;

			@Override
			public void valueChange(ValueChangeEvent event) {

			}

		});

		addComponent(layout);
		setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

		tabsheet1.addListener(this);

	}

	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		Object cCurTab = event.getTabSheet().getSelectedTab();
		if (cwsmu.equals(cCurTab)) {
			UI.getCurrent().getSession()
					.setAttribute(WorkSpaceManageProfile.SESSION_WSMP, null);

		} else if (cwsmp.equals(cCurTab)) {
			UI.getCurrent()
					.getSession()
					.setAttribute(WorkSpaceManageProfile.SESSION_WSMP,
							"cur_user");
		}
	}

}
