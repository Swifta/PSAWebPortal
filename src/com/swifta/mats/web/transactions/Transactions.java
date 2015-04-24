package com.swifta.mats.web.transactions;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class Transactions extends VerticalLayout {

	/**
	 * 
	 */
	TabSheet tab = new TabSheet();
	VerticalLayout container = new VerticalLayout();
	FormLayout transfer1;
	FormLayout transfer2;
	private static final long serialVersionUID = 6777931988784969925L;

	public VerticalLayout Addlabel() {
		// TODO Auto-generated method stub
		// setSizeFull();
		tab.setSizeFull();
		tab.setWidthUndefined();
		// tab.setStyleName("tabref2");

		FormLayout tab1 = new FormLayout();
		Adjustment adjust = new Adjustment();
		tab1.addComponent(adjust.AddAdjustmentPanel());
		tab1.setImmediate(true);
		tab1.setCaption("Adjustment");
		tab.addTab(tab1, "Adjustment", null);
		container.addComponent(tab);

		final FormLayout tab2 = new FormLayout();
		final ComboBox type = new ComboBox("Select Float Transaction type");
		type.addItem("Transfer to Bank Account");
		type.addItem("Transfer between Super Agent and Dealer");
		type.setNullSelectionAllowed(false);
		type.setTextInputAllowed(false);
		// type.setValue(type.getItemIds().iterator().next());
		type.setInputPrompt("Select");

		tab2.addComponent(type);
		final Transfer transfer = new Transfer();
		transfer1 = transfer.AddTransferPanel();
		transfer2 = transfer.AddTransferPanel2();
		type.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				String selectedId = (String) type.getValue();
				if (selectedId != null) {
					if (selectedId == "Transfer to Bank Account") {
						tab2.removeAllComponents();
						tab2.addComponent(type);
						tab2.addComponent(transfer1);
					} else if (selectedId == "Transfer between Super Agent and Dealer") {
						tab2.removeAllComponents();
						tab2.addComponent(type);
						tab2.addComponent(transfer2);
					}
				}
			}

		});

		tab2.setImmediate(true);
		tab2.setCaption("Transfer Float");
		tab.addTab(tab2, "Transfer Float", null);
		container.addComponent(tab);
		container.setComponentAlignment(tab, Alignment.TOP_LEFT);

		return container;
	}

}
