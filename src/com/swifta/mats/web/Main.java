package com.swifta.mats.web;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.swifta.mats.web.dashboard.BarChartDash;
import com.swifta.mats.web.dashboard.Dashboard;
import com.swifta.mats.web.dashboard.PiechartDash;
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
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Main extends VerticalLayout implements View {

	private static final long serialVersionUID = 7453202011100914519L;
	public static final String WS = "dashboard";

	boolean isCriteriaChanged = false;
	String dCat;
	private TabSheet ts;
	VerticalLayout tab = null;
	String id = null;
	private boolean isc = false;

	public Main() {

		isc = true;
		ts = MatsWebPortalUI.ts;
		ts.setSelectedTab(0);
		id = ts.getSelectedTab().getId();
		tab = (VerticalLayout) ts.getSelectedTab();

		if (id != null && id.equals("ds")) {
			addHeader();
			addMenu();
		} else {
			addHeader();
			addMenu();
			d();
			tab.setId("ds");

		}

	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (isc) {
			ts.setSelectedTab(0);
			addMenu();
			isc = false;
		}
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
				VaadinSession.getCurrent().close();

			}
		});

	}

	private void addMenu() {
		addComponent(ts);

	}

	private void d() {

		final DateField dat = new DateField();
		final DateField dat2 = new DateField();
		final Button filter = new Button("Filter");
		final HorizontalLayout pi;
		Label la = new Label("Filter by: ");
		final ComboBox comboGF = new ComboBox("Please select...");

		dat.addValidator(new ValidateRange(dat, dat2));
		dat2.addValidator(new ValidateRange(dat, dat2));

		dat.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -1020854682753361028L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				dat.setComponentError(null);
				dat2.setComponentError(null);
				isCriteriaChanged = true;

			}

		});

		dat2.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -1020854682753361028L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				dat.setComponentError(null);
				dat2.setComponentError(null);
				isCriteriaChanged = true;

			}

		});

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

				if (!isCriteriaChanged)
					return;
				else
					isCriteriaChanged = false;
				Object cat = comboGF.getValue();
				// if (cat == null || cat.toString().equals(dCat))
				// return;
				if (cat == null)
					return;

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

		comboGF.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 2950767419735127495L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				isCriteriaChanged = true;

			}

		});

		lut.setSizeFull();
		lut.addComponent(former);
		lut.addComponent(pi);
		// lut.addComponent(bar.getChart());
		lut.setExpandRatio(former, 2);
		lut.setExpandRatio(pi, 6);
		tab.addComponent(lut);

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

}
