package com.swifta.mats.web.report;

import com.swifta.mats.web.usermanagement.PagedTableCustom;
import com.vaadin.addon.tableexport.ExcelExport;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class Reportform extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 252829471857525213L;
	VerticalLayout searchform = new VerticalLayout();
	Reporttype form = new Reporttype();
	PagedTableCustom table = new PagedTableCustom();
	HorizontalLayout pnUserSearchResults;
	HorizontalLayout pnUserSearchResults2;
	IndexedContainer container = new IndexedContainer();

	public void reportformat() {

		container.addContainerProperty("S/N", String.class, "");
		container.addContainerProperty("Transaction ID", String.class, "");
		container.addContainerProperty("Transaction Type", String.class, "");
		container.addContainerProperty("Date", String.class, "");
		container.addContainerProperty("Amount", String.class, "");
		container.addContainerProperty("Agent", String.class, "");

		// form.populateTable("Hi", "Hi", "Hi","Hi", "Hi", "Hi", container);

		setMargin(true);
		final ComboBox reportType = new ComboBox("Search by Report Type");
		Button export = new Button("Export result");
		reportType.addItem("Settlement");
		reportType.addItem("Float Management");
		reportType.addItem("Transactions");
		reportType.addItem("Commission");
		reportType.addItem("Fees");
		reportType.setNullSelectionAllowed(false);
		reportType.setTextInputAllowed(false);
		reportType.setInputPrompt("Select Report Type");

		addComponent(reportType);

		reportType.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				String selectedId = (String) reportType.getValue();
				if (selectedId != null) {
					if (selectedId == "Settlement") {
						searchform.removeAllComponents();
						searchform.addComponent(form.SettlementForm());
					} else if (selectedId == "Float Management") {
						searchform.removeAllComponents();
						searchform.addComponent(form.FloatManagementForm());
					} else if (selectedId == "Transactions") {
						searchform.removeAllComponents();
						searchform.addComponent(form.Transactions());
					} else if (selectedId == "Commission") {
						searchform.removeAllComponents();
						searchform.addComponent(form.Commission());
					} else if (selectedId == "Fees") {
						searchform.removeAllComponents();
						searchform.addComponent(form.Fees());
					}
				}
			}

		});

		export.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private ExcelExport excelExport;

			@Override
			public void buttonClick(ClickEvent event) {

				excelExport = new ExcelExport(table);
				excelExport.setReportTitle("PSA Report");
				excelExport.setExportFileName("PSA.xls");
				excelExport.setDisplayTotals(false);
				excelExport.export();

			}
		});

		setSpacing(true);

		table.setSizeFull();

		table.setContainerDataSource(container);

		pnUserSearchResults = table.createControls();
		pnUserSearchResults2 = table.createControls();

		addComponent(searchform);

		addComponent(pnUserSearchResults);
		addComponent(table);
		addComponent(pnUserSearchResults2);
		addComponent(export);
		setComponentAlignment(export, Alignment.BOTTOM_RIGHT);

	}

}
