package com.swifta.mats.web;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

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
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare.GreaterOrEqual;
import com.vaadin.data.util.filter.Compare.LessOrEqual;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
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
	private Button btnLogout;
	private boolean isCreteriaChanged = false;
	Label la = new Label("Filter by: ");
	ComboBox comboGF = new ComboBox("Please select...");
	DateField dat = new DateField();
	DateField dat2 = new DateField();

	Button filter = new Button("Filter");
	HorizontalLayout pi;
	String dCat;

	private static final long serialVersionUID = 4370608410523325533L;

	public static final String WORK_SPACE = "";
	VerticalLayout dashboard6 = new VerticalLayout();
	TabSheet tabsheet1 = new TabSheet();

	public WorkSpace() {
		wsmu = new WorkSpaceManageUser();
		wsmp = new WorkSpaceManageProfile();
		dat.addValidator(new ValidateRange(dat, dat2));
		dat2.addValidator(new ValidateRange(dat, dat2));
		dat.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -1020854682753361028L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				dat.setComponentError(null);
				dat2.setComponentError(null);
				isCreteriaChanged = true;

			}

		});

		dat2.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -1020854682753361028L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				dat.setComponentError(null);
				dat2.setComponentError(null);
				isCreteriaChanged = true;

			}

		});

	}

	private class ValidateRange implements Validator {
		private static final long serialVersionUID = -5454180295673067279L;
		DateField start;
		DateField end;

		ValidateRange(DateField dat, DateField dat2) {
			this.start = dat;
			this.end = dat2;
		}

		@Override
		public void validate(Object value) throws InvalidValueException {
			Date s = start.getValue();
			Date e = end.getValue();

			if (s != null && e != null)
				if (s.compareTo(e) > 0) {
					throw new InvalidValueException("Invalid date range");
				}
		}

	}

	@SuppressWarnings("deprecation")
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

		dCat = "Transaction Type";

		filter.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 7867132630286287368L;

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {

				if (!isCreteriaChanged)
					return;
				else
					isCreteriaChanged = false;
				Object cat = comboGF.getValue();
				// if (cat == null || cat.toString().equals(dCat))
				// return;
				if (Dashboard.otb == null)
					return;

				dCat = cat.toString();
				Date start = dat.getValue();
				Date end = dat2.getValue();
				Filter dfilter = null;

				if (start != null && end != null) {

					dat2.validate();
					Dashboard.otb.removeAllContainerFilters();
					dfilter = new And(
							new GreaterOrEqual("DoT", start.getTime()),
							new LessOrEqual("DoT", end.getTime()));
					Dashboard.otb.addContainerFilter(dfilter);
				}

				Iterator<Integer> itr = (Iterator<Integer>) Dashboard.otb
						.getItemIds().iterator();

				HashMap<String, Float> hm = new HashMap<>();

				while (itr.hasNext()) {
					int rid = itr.next();
					Item r = Dashboard.otb.getItem(rid);
					Property<String> f = r.getItemProperty(cat.toString());
					String param = f.getValue();
					if (!hm.containsKey(param)) {
						hm.put(param, 1F);
					} else {
						hm.put(param, hm.get(param) + 1);
					}
				}

				Float t = 0F;
				Iterator<Entry<String, Float>> itrx = hm.entrySet().iterator();
				while (itrx.hasNext()) {
					Entry<String, Float> e = itrx.next();
					t = t + e.getValue();
				}

				itrx = hm.entrySet().iterator();
				while (itrx.hasNext()) {
					Entry<String, Float> e = itrx.next();
					hm.put(e.getKey(), (e.getValue() / t) * 100);
				}

				DataSeries series = new DataSeries();

				Iterator<Entry<String, Float>> itrSet = hm.entrySet()
						.iterator();
				while (itrSet.hasNext()) {
					Entry<String, Float> e = itrSet.next();

					DataSeriesItem item = new DataSeriesItem(e.getKey(), Math
							.round(e.getValue()));

					series.add(item);

				}

				itrx = hm.entrySet().iterator();

				Set<String> types = hm.keySet();
				String[] type = new String[types.size()];
				types.toArray(type);

				Collection<Float> vals = hm.values();
				Float[] val = new Float[vals.size()];
				vals.toArray(val);

				for (int i = 0; i < val.length; i++)
					val[i] = Float.valueOf(BigDecimal.valueOf(val[i])
							.setScale(1, BigDecimal.ROUND_UP).toString());

				PiechartDash.conf.setSeries(series);

				pieChart.drawChart();
				bar.xAxis.setCategories(type);
				bar.serie.setData(val);
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
				UI.getCurrent().getNavigator().navigateTo(WORK_SPACE);
				getSession().close();

			}
		});

		comboGF.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 2950767419735127495L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				isCreteriaChanged = true;

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
