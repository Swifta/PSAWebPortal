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
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
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
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Main extends VerticalLayout implements View {

	private static final long serialVersionUID = 7453202011100914519L;
	public static final String WS = "dashboard";

	private boolean isCriteriaChanged = false;

	private TabSheet ts;
	private VerticalLayout tab = null;

	private PiechartDash pie = new PiechartDash();
	private BarChartDash bar = new BarChartDash();

	// private Timeline barChart = (Timeline) bar.getChart();
	private Chart pieChart = (Chart) pie.getChart();
	private HashMap<String, HashMap<String, Float>> hmSS;
	private VerticalLayout cDrill = new VerticalLayout();
	private Panel pDrill = new Panel();
	private Button btnClose;
	private ComboBox comboGF;
	private DateField dat, dat2;

	public Main(TabSheet ts) {
		this.ts = ts;
		tab = (VerticalLayout) ts.getSelectedTab();
		addHeader();
		addMenu();
		d();
		hmSS = Dashboard.getStatusStats();

	}

	@Override
	public void enter(ViewChangeEvent event) {
		ts.setSelectedTab(0);
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

		dat = new DateField();
		dat2 = new DateField();
		final Button filter = new Button("Filter");
		filter.setDescription("Filter chart data.");
		final HorizontalLayout pi;

		Label la = new Label("Filter by: ");
		comboGF = new ComboBox("Please select...");

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
		pie = new PiechartDash();
		pi = new HorizontalLayout();
		pi.setSizeFull();
		HorizontalLayout lut = new HorizontalLayout();
		VerticalLayout former = new VerticalLayout();
		bar = new BarChartDash();

		dat.setCaption("Start Date");
		dat2.setCaption("End Date");
		comboGF.addItem("Transaction Type");
		comboGF.addItem("Fees Account");
		comboGF.select("Transaction Type");

		former.addComponent(la);
		la.setWidth("100%");
		former.addComponent(comboGF);
		former.addComponent(dat);
		former.addComponent(dat2);
		former.addComponent(filter);
		Button btnReload = new Button();
		btnReload.setIcon(FontAwesome.REPEAT);
		former.addComponent(btnReload);
		btnReload.setDescription("Reload chart data");
		former.setWidth("100px");

		pieChart = (Chart) pie.getChart();

		pieChart.addPointClickListener(new PointClickListener() {
			@Override
			public void onClick(PointClickEvent event) {
				if (comboGF.getValue() == null)
					return;
				if (!comboGF.getValue().toString().equals("Transaction Type"))
					return;

				DataSeriesItem dsi = ((DataSeries) event.getSeries()).get(event
						.getPointIndex());

				String cat = dsi.getName();
				if (cat == null)
					return;
				Float n = Float.valueOf(dsi.getY().toString());

				cDrill.removeAllComponents();

				cDrill.addComponent(btnClose);
				cDrill.setComponentAlignment(btnClose, Alignment.TOP_RIGHT);

				Label lbT = new Label(cat + "'s Details");
				cDrill.addComponent(lbT);
				lbT.setSizeUndefined();
				cDrill.setComponentAlignment(lbT, Alignment.MIDDLE_CENTER);

				Label lbP = new Label(
						"<span style='padding-left: 5px; font-weight:bold;'>("
								+ BigDecimal.valueOf(n).setScale(2,
										BigDecimal.ROUND_DOWN) + "% )</span>");

				lbP.setContentMode(ContentMode.HTML);

				cDrill.addComponent(lbP);
				lbP.setSizeUndefined();
				cDrill.setComponentAlignment(lbP, Alignment.MIDDLE_CENTER);

				HorizontalLayout c = null;

				HashMap<String, Float> hms = hmSS.get(cat);
				Label lb = null;
				Iterator<Entry<String, Float>> itr = hms.entrySet().iterator();
				Float t = 0F;

				while (itr.hasNext()) {
					Entry<String, Float> e = itr.next();
					t = t + e.getValue();
				}

				itr = hms.entrySet().iterator();
				while (itr.hasNext()) {
					lb = new Label();
					lb.setSizeUndefined();
					c = new HorizontalLayout();
					c.setSizeUndefined();
					Entry<String, Float> e = itr.next();
					// lb.setStyleName("x_m_n");
					lb.setContentMode(ContentMode.HTML);
					lb.setValue(e.getKey()
							+ "  : <span style='padding-left: 20px; font-weight:bold;'> "
							+ BigDecimal.valueOf(((e.getValue() / t) * n))
									.setScale(2, BigDecimal.ROUND_DOWN)
							+ "% </span>");
					c.addComponent(lb);
					cDrill.addComponent(c);
				}

				pDrill.setVisible(true);

			}

		});

		VerticalLayout cPie = new VerticalLayout();

		btnClose = new Button("x");
		btnClose.setStyleName("btn_link");
		cDrill.addComponent(btnClose);
		cDrill.setComponentAlignment(btnClose, Alignment.TOP_RIGHT);
		btnClose.setDescription("Close");
		btnClose.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5224520640943009840L;

			@Override
			public void buttonClick(ClickEvent event) {
				pDrill.setVisible(false);

			}
		});

		// barChart.setWidth("100%");
		// cPie.addComponent(barChart);

		cPie.addComponent(pieChart);
		cPie.addComponent(pDrill);
		cPie.setStyleName("c_pie");
		cDrill.setSizeUndefined();
		cDrill.setMargin(true);
		pDrill.setContent(cDrill);
		pDrill.setSizeUndefined();
		pDrill.setStyleName("c_drill");
		pDrill.setVisible(false);

		pi.addComponent(cPie);
		// pi.addComponent(barChart);

		btnReload.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -1405690945608678270L;

			@Override
			public void buttonClick(ClickEvent event) {
				Dashboard.updateOtb();
				Object t = comboGF.getValue();
				if (t == null) {
					t = new String("Transaction Type");
					comboGF.select(t);
				}
				redrawCharts();

			}
		});

		filter.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 7867132630286287368L;

			@Override
			public void buttonClick(ClickEvent event) {
				pDrill.setVisible(false);
				addFilters();
			}
		});

		comboGF.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 2950767419735127495L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				isCriteriaChanged = true;
				Date d = null;
				dat.setValue(d);
				dat2.setValue(d);
				Dashboard.otb.removeAllContainerFilters();

			}

		});

		lut.setSizeFull();
		lut.addComponent(former);
		former.setSpacing(true);
		// former.setMargin(true);
		lut.addComponent(pi);
		// lut.addComponent(bar.getChart());
		lut.setExpandRatio(former, 2);
		lut.setExpandRatio(pi, 8);
		tab.addComponent(lut);
		// former.setMargin(true);

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

	private void redrawCharts() {
		addFilters();

	}

	@SuppressWarnings("unchecked")
	private void addFilters() {

		if (!isCriteriaChanged) {
			return;
		} else {
			isCriteriaChanged = false;
		}
		Object cat = comboGF.getValue();

		if (cat == null)
			return;

		if (Dashboard.otb == null)
			return;

		Date start = dat.getValue();
		Date end = dat2.getValue();
		Filter dfilter = null;

		if (start != null && end != null) {

			dat2.validate();
			Dashboard.otb.removeAllContainerFilters();
			dfilter = new And(new GreaterOrEqual("DoT", start),
					new LessOrEqual("DoT", end));
			Dashboard.otb.addContainerFilter(dfilter);
		}

		Iterator<Integer> itr = (Iterator<Integer>) Dashboard.otb.getItemIds()
				.iterator();

		HashMap<String, Float> hm = new HashMap<>();
		hmSS.clear();

		while (itr.hasNext()) {
			int rid = itr.next();
			Item r = Dashboard.otb.getItem(rid);
			Property<String> f = r.getItemProperty(cat.toString());
			String s = (String) r.getItemProperty("Status").getValue();
			String param = f.getValue();
			if (!hm.containsKey(param)) {
				HashMap<String, Float> hmStat = new HashMap<>();
				hm.put(param, 1F);
				hmStat.put(s, 1F);
				hmSS.put(param, hmStat);
			} else {
				hm.put(param, hm.get(param) + 1);
				HashMap<String, Float> hmStat = hmSS.get(param);
				if (!hmStat.containsKey(s)) {
					hmStat.put(s, 1F);
				} else {
					hmStat.put(s, hmStat.get(s) + 1);

				}
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

		Iterator<Entry<String, Float>> itrSet = hm.entrySet().iterator();
		while (itrSet.hasNext()) {
			Entry<String, Float> e = itrSet.next();

			DataSeriesItem item = new DataSeriesItem(e.getKey(),
					Float.valueOf(BigDecimal.valueOf(e.getValue())
							.setScale(2, BigDecimal.ROUND_DOWN).toString()));

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
					.setScale(2, BigDecimal.ROUND_DOWN).toString());

		PiechartDash.conf.setSeries(series);

		pieChart.drawChart();
		bar.xAxis.setCategories(type);
		bar.serie.setData(val);

	}

}
