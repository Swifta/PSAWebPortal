package com.swifta.mats.web.dashboard;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.CreditPosition;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.Component;

public class PiechartDash {
	public static Configuration conf;

	public String getDescription() {
		return "Data Pie Chart";
	}

	public Component getChart() {
		Component ret = createChart();
		ret.setWidth("100%");
		ret.setHeight("100%");
		return ret;
	}

	public static Chart createChart() {
		Chart chart = new Chart(ChartType.PIE);

		conf = chart.getConfiguration();

		conf.setTitle("MATS Activity Pie Chart");

		Credits credits = new Credits(true);
		credits.setPosition(new CreditPosition());
		credits.getPosition().setHorizontalAlign(HorizontalAlign.CENTER);
		credits.getPosition().setVerticalAlign(VerticalAlign.MIDDLE);
		credits.getPosition().setX(0);
		credits.getPosition().setY(10);
		// conf.setCredits(credits);

		PlotOptionsPie plotOptions = new PlotOptionsPie();
		plotOptions.setCursor(Cursor.POINTER);
		plotOptions.setShowInLegend(true);
		Labels dataLabels = new Labels();
		dataLabels.setEnabled(true);
		dataLabels.setColor(new SolidColor(0, 0, 0));
		dataLabels.setConnectorColor(new SolidColor(0, 0, 0));

		dataLabels
				.setFormatter("''+this.point.name +': '+ this.percentage.toFixed(2) +'% '");

		plotOptions.setDataLabels(dataLabels);

		conf.setPlotOptions(plotOptions);

		DataSeries series = new DataSeries();

		int max = BarChartDash.hm.keySet().size();

		String[] category = new String[max];
		Number[] no = new Number[max];
		BarChartDash.hm.values().toArray(no);

		BarChartDash.hm.keySet().toArray(category);
		series.setData(category, no);

		conf.setSeries(series);

		chart.drawChart(conf);

		return chart;
	}
}
