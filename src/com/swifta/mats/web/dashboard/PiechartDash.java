package com.swifta.mats.web.dashboard;


import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

 
public class PiechartDash {

	 public String getDescription() {
	        return "Pie chart";
	    }

	    public Component getChart() {
	        Component ret = createChart();
	        ret.setWidth("100%");
	        ret.setHeight("450px");
	        return ret;
	    }

	    public static Chart createChart() {
	        Chart chart = new Chart(ChartType.PIE);
	        String user = "Agent 001";
	        Configuration conf = chart.getConfiguration();

	        conf.setTitle("Data Chart "+user );

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

	        final DataSeries series = new DataSeries();
	        series.add(new DataSeriesItem("Cash in", 50));
	        series.add(new DataSeriesItem("Cash out", 32));
	        DataSeriesItem chrome = new DataSeriesItem("Airtime", 18);
	        chrome.setSliced(true);
	        chrome.setSelected(true);
	        series.add(chrome);
	       
	        conf.setSeries(series);

	        chart.addPointClickListener(new PointClickListener() {

	            @Override
	            public void onClick(PointClickEvent event) {
	                Notification.show("Click: "
	                        + series.get(event.getPointIndex()).getName());
	            }
	        });

	        chart.drawChart(conf);

	        return chart;
	    }

}
