package com.swifta.mats.web.dashboard;


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
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.ui.Component;

public class BarChartDash {
	
	  
	    public String getDescription() {
	        return "Column with rotated labels";
	    }

	   
	    public Component getChart() {
	        Chart chart = new Chart(ChartType.COLUMN);
	        String user = "Agent 001";
	        Configuration conf = chart.getConfiguration();
	        conf.getChart().setMargin(50, 80, 100, 50);

	        conf.setTitle(new Title("Data Chart "+user));

	        XAxis xAxis = new XAxis();
	        xAxis.setCategories(new String[] { "Cash in", "Cash out","Airtime" });
	        
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
	        tooltip.setFormatter("''+ this.x +''+'data chart: '"
	                + "+ Highcharts.numberFormat(this.y, 1) +' percent'");
	        conf.setTooltip(tooltip);

	        ListSeries serie = new ListSeries("Percentage", new Number[] { 50,32,28});
	        Labels dataLabels = new Labels();
	        dataLabels.setEnabled(true);
	        dataLabels.setRotation(-90);
	        dataLabels.setColor(new SolidColor(255, 255, 255));
	        dataLabels.setAlign(HorizontalAlign.RIGHT);
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
	        chart.setWidth("100%");
	        chart.setHeight("450px");

	        return chart;
	    }

}
