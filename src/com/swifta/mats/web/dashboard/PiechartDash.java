package com.swifta.mats.web.dashboard;

import java.util.Iterator;
import java.util.Map.Entry;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.CreditPosition;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.Component;

public class PiechartDash {
	public static Configuration conf;

	public String getDescription() {
		return "Pie chart with customized credits";
	}

	public Component getChart() {
		Component ret = createChart();
		ret.setWidth("450px");
		ret.setHeight("450px");
		return ret;
	}

	public static Chart createChart() {
		Chart chart = new Chart(ChartType.PIE);
		String user = "Agent 001";
		conf = chart.getConfiguration();

		conf.setTitle("Data Chart ");

		Credits credits = new Credits(true);
		credits.setPosition(new CreditPosition());
		credits.getPosition().setHorizontalAlign(HorizontalAlign.CENTER);
		credits.getPosition().setVerticalAlign(VerticalAlign.MIDDLE);
		credits.getPosition().setX(0);
		credits.getPosition().setY(10);
		conf.setCredits(credits);

		PlotOptionsPie plotOptions = new PlotOptionsPie();
		plotOptions.setCursor(Cursor.POINTER);
		plotOptions.setShowInLegend(true);
		Labels dataLabels = new Labels();
		dataLabels.setEnabled(true);
		dataLabels.setColor(new SolidColor(0, 0, 0));
		dataLabels.setConnectorColor(new SolidColor(0, 0, 0));
		dataLabels
				.setFormatter("''+ this.point.name +': '+ this.percentage +' %'");
		plotOptions.setDataLabels(dataLabels);
		conf.setPlotOptions(plotOptions);

		DataSeries series = new DataSeries();

		Iterator<Entry<String, Float>> itr = BarChartDash.hm.entrySet()
				.iterator();

		while (itr.hasNext()) {
			Entry<String, Float> e = itr.next();
			series.add(new DataSeriesItem(e.getKey(), Math.round(e.getValue())));
		}
		// series.add(new DataSeriesItem("Cash out", 32));
		// DataSeriesItem chrome = new DataSeriesItem("Airtime", 18);
		// chrome.setSliced(true);
		// chrome.setSelected(true);
		// series.add(chrome);

		conf.setSeries(series);

		chart.drawChart(conf);

		return chart;
	}
}
