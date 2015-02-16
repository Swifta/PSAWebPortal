package com.swifta.mats.web.dashboard;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.ui.Component;

public class BarChartDash {
	public static HashMap<String, Float> hm;
	public XAxis xAxis;
	public ListSeries serie;

	public String getDescription() {
		return "Column with rotated labels";
	}

	public BarChartDash() {
		hm = Dashboard.getChartData();
	}

	public Component getChart() {
		Chart chart = new Chart(ChartType.COLUMN);

		String user = "Agent 001";
		Configuration conf = chart.getConfiguration();
		// conf.getChart().setMargin(50, 80, 100, 50);

		conf.setTitle(new Title("Data Chart "));

		xAxis = new XAxis();
		Set<String> stxns = hm.keySet();
		String[] txns = new String[stxns.size()];
		stxns.toArray(txns);

		xAxis.setCategories(txns);

		Labels labels = new Labels();
		labels.setRotation(-45);
		labels.setAlign(HorizontalAlign.RIGHT);
		Style style = new Style();
		style.setFontSize("13px");
		style.setFontFamily("Verdana, sans-serif");
		labels.setStyle(style);
		xAxis.setLabels(labels);
		conf.addxAxis(xAxis);

		YAxis yAxis = new YAxis();
		yAxis.setMin(0);
		yAxis.setTitle(new Title("Percentage"));
		conf.addyAxis(yAxis);

		Legend legend = new Legend();
		legend.setEnabled(false);
		conf.setLegend(legend);

		Tooltip tooltip = new Tooltip();
		tooltip.setFormatter("' '+ this.x +''+'data chart: '"
				+ "+ Highcharts.numberFormat(this.y, 1) +' percent'");
		conf.setTooltip(tooltip);

		Collection<Float> sper = hm.values();
		Float[] tper = new Float[stxns.size()];
		sper.toArray(tper);
		for (int i = 0; i < tper.length; i++)
			tper[i] = Float.valueOf(BigDecimal.valueOf(tper[i])
					.setScale(1, BigDecimal.ROUND_UP).toString());

		serie = new ListSeries("Percentage", tper);

		Labels dataLabels = new Labels();
		dataLabels.setEnabled(true);
		dataLabels.setRotation(-90);
		dataLabels.setX(4);
		dataLabels.setY(10);
		dataLabels.setFormatter("this.y");
		style = new Style();
		style.setFontSize("13px");
		style.setFontFamily("Verdana, sans-serif");
		dataLabels.setStyle(style);
		PlotOptionsColumn plotOptionsColumn = new PlotOptionsColumn();
		plotOptionsColumn.setDataLabels(dataLabels);
		serie.setPlotOptions(plotOptionsColumn);

		conf.addSeries(serie);

		chart.drawChart(conf);
		chart.setWidth("400px");
		chart.setHeight("450px");
		// chart.setSizeFull();

		return chart;
	}
}
