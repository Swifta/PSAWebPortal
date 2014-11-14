package com.swifta.mats.web.report;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;

public class Reporttype {
	int number = 0;

	public FormLayout SettlementForm() {
		DateField from = new DateField("Settlement Report From");
		DateField to = new DateField("Settlement Report To");
		FormLayout settlement = new FormLayout();
		Button search = new Button("Search");
		Label lab = new Label("Search for Settlement Report");

		settlement.setMargin(true);
		settlement.addComponent(lab);
		settlement.addComponent(from);
		settlement.addComponent(to);
		settlement.addComponent(search);

		return settlement;
	}

	public FormLayout FloatManagementForm() {
		DateField from = new DateField("Float Report From");
		DateField to = new DateField("Float Report To");
		FormLayout floatmanagement = new FormLayout();
		Button search = new Button("Search");
		Label lab2 = new Label("Search for Float Management Report");

		floatmanagement.setMargin(true);
		floatmanagement.addComponent(lab2);
		floatmanagement.addComponent(from);
		floatmanagement.addComponent(to);
		floatmanagement.addComponent(search);

		return floatmanagement;
	}

	public FormLayout Transactions() {
		DateField from = new DateField("Transaction Report From");
		DateField to = new DateField("Transaction Report To");
		FormLayout transactions = new FormLayout();
		Button search = new Button("Search");
		ComboBox agent = new ComboBox("Agent ID");
		agent.addItem("All");
		ComboBox partner = new ComboBox("Partner ID");
		partner.addItem("All");
		Label lab3 = new Label("Search for Transaction Report");

		transactions.addComponent(lab3);
		transactions.addComponent(from);
		transactions.addComponent(to);
		transactions.addComponent(agent);
		transactions.addComponent(partner);
		transactions.addComponent(search);

		return transactions;
	}

	public FormLayout Commission() {
		DateField from = new DateField("Commission Report From");
		DateField to = new DateField("Commission Report To");
		FormLayout commission = new FormLayout();
		Button search = new Button("Search");
		ComboBox agent = new ComboBox("Agent ID");
		agent.addItem("All");
		ComboBox partner = new ComboBox("Partner ID");
		partner.addItem("All");
		Label lab3 = new Label("Search for Commission Report");

		commission.addComponent(lab3);
		commission.addComponent(from);
		commission.addComponent(to);
		commission.addComponent(agent);
		commission.addComponent(partner);
		commission.addComponent(search);

		return commission;
	}

	public FormLayout Fees() {
		DateField from = new DateField("Fees Report From");
		DateField to = new DateField("Fees Report To");
		FormLayout fees = new FormLayout();
		Button search = new Button("Search");
		ComboBox agent = new ComboBox("Agent ID");
		agent.addItem("All");
		ComboBox partner = new ComboBox("Partner ID");
		partner.addItem("All");
		Label lab3 = new Label("Search for Fees Report");

		fees.addComponent(lab3);
		fees.addComponent(from);
		fees.addComponent(to);
		fees.addComponent(agent);
		fees.addComponent(partner);
		fees.addComponent(search);

		return fees;
	}

	public IndexedContainer populateTable(String serialnumber,
			String transactionid, String date, String amount, String agentid,
			IndexedContainer tabContainer) {

		Object itemId;
		Item trItem;

		itemId = tabContainer.addItem();

		trItem = tabContainer.getItem(itemId);

		Property<String> tdPropertyserial = trItem.getItemProperty("S/N");
		Property<String> tdPropertytransaction = trItem
				.getItemProperty("Transaction ID");
		Property<String> tdPropertydate = trItem.getItemProperty("Date");
		Property<String> tdPropertyamount = trItem.getItemProperty("Amount");
		Property<String> tdPropertyagent = trItem.getItemProperty("Agent");
		// Property<String> tdPropertydesc =
		// trItem.getItemProperty("Description");

		tdPropertyserial.setValue(serialnumber);
		tdPropertytransaction.setValue(transactionid);
		tdPropertydate.setValue(date);
		tdPropertyamount.setValue(amount);
		tdPropertyagent.setValue(agentid);

		return tabContainer;
	}

}
