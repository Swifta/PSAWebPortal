package com.swifta.mats.web.dashboard;

import java.awt.Color;
import java.util.HashMap;

import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.timeline.Timeline;
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

		Timeline tml = new Timeline("MATS Activity Timeline");

		tml.addZoomLevel("Week", 7 * 24 * 60 * 60 * 60L);
		tml.addZoomLevel("Day", 24 * 60 * 60 * 60L);
		tml.addZoomLevel("Hour", 60 * 60 * 60L);
		tml.addZoomLevel("Minute", 60 * 60L);

		tml.addGraphDataSource(Dashboard.otb, "DoT", "Count");
		tml.setGraphOutlineColor(Dashboard.otb, Color.GREEN);
		tml.setGraphFillColor(Dashboard.otb, Color.CYAN);

		return tml;
	}
}
