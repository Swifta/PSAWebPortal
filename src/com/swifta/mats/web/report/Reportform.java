package com.swifta.mats.web.report;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.swifta.mats.web.usermanagement.PagedTableCustom;
import com.vaadin.addon.tableexport.ExcelExport;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class Reportform extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 252829471857525213L;
	VerticalLayout searchform = new VerticalLayout();

	PagedTableCustom table = new PagedTableCustom();
	HorizontalLayout pnUserSearchResults;
	HorizontalLayout pnUserSearchResults2;
	IndexedContainer container = new IndexedContainer();
	IndexedContainer container2 = new IndexedContainer();
	IndexedContainer container3 = new IndexedContainer();
	ComboBox agent;

	// PagedTableContainerCustom container = new
	// PagedTableContainerCustom(contain);
	// PagedTableContainerCustom container2 = new PagedTableContainerCustom(
	// contain2);
	// PagedTableContainerCustom container3 = new PagedTableContainerCustom(
	// contain3);

	public void reportformat() {
		// Float Management

		container.addContainerProperty("S/N", String.class, "");
		// container.addContainerProperty("Transaction ID", String.class, "");

		container.addContainerProperty("Transaction Date", String.class, "");
		container.addContainerProperty("Agent ID", String.class, "");
		container.addContainerProperty("Dealer ID", String.class, "");
		container.addContainerProperty("Amount", String.class, "");

		// Transaction

		container2.addContainerProperty("S/N", String.class, "");
		container2.addContainerProperty("Name", String.class, "");
		container2.addContainerProperty("Transaction Date", String.class, "");
		container2.addContainerProperty("Amount", String.class, "");
		container2.addContainerProperty("Opening Balance", String.class, "");
		container2.addContainerProperty("Closing Balance", String.class, "");
		container2.addContainerProperty("Transaction Type", String.class, "");
		container2.addContainerProperty("Account Type", String.class, "");

		// Summary
		container3.addContainerProperty("S/N", String.class, "");
		container3.addContainerProperty("Name", String.class, "");
		container3.addContainerProperty("Transaction Date", String.class, "");
		container3.addContainerProperty("Transaction Type", String.class, "");
		container3.addContainerProperty("Sum of Transaction Amount",
				String.class, "");

		setMargin(true);
		final ComboBox reportType = new ComboBox("Search by Report Type");
		Button export = new Button("Export result");

		Button Add = new Button("Add");

		reportType.addItem("Float Management Report");
		reportType.addItem("Transaction Report");
		reportType.addItem("Summary Report");

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
				searchform.removeAllComponents();
				String selectedId = (String) reportType.getValue();
				if (selectedId != null) {

					if (selectedId == "Float Management Report") {

						String Uname = "gomint";
						String Pword = "gomint";
						String drivers = "com.mysql.jdbc.Driver";
						try {

							Class driver_class = Class.forName(drivers);
							Driver driver = (Driver) driver_class.newInstance();
							DriverManager.registerDriver(driver);

							Connection conn = DriverManager
									.getConnection(
											"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasource",
											Uname, Pword);

							Statement stmt = conn.createStatement();
							ResultSet rs;
							int x = 0;
							Object itemId;
							Item trItem;
							container.removeAllItems();
							rs = stmt
									.executeQuery("SELECT operatorid,sum(amount),createdon,transactionstatusid,dealerid FROM cashtransactions group by operatorid");
							while (rs.next()) {
								x = x + 1;

								itemId = container.addItem();

								trItem = container.getItem(itemId);

								Property<String> tdPropertyserial = trItem
										.getItemProperty("S/N");

								Property<String> tdPropertytransactiondate = trItem
										.getItemProperty("Transaction Date");
								Property<String> tdPropertyagentid = trItem
										.getItemProperty("Agent ID");
								Property<String> tdPropertydealerid = trItem
										.getItemProperty("Dealer ID");
								Property<String> tdPropertyamount = trItem
										.getItemProperty("Amount");

								String agentid = rs.getString("operatorid");
								String amount = rs.getString("sum(amount)");
								String createdon = rs.getString("createdon");
								String transactionstatusid = rs
										.getString("transactionstatusid");
								String dealerid = rs.getString("dealerid");

								tdPropertyserial.setValue(String.valueOf(x));

								tdPropertytransactiondate.setValue(createdon);
								tdPropertyagentid.setValue(agentid);
								tdPropertydealerid.setValue(dealerid);
								tdPropertyamount.setValue(amount);

							}
							conn.close();
							Notification.show(x + " result(s) found");

							table.setContainerDataSource(container);

						} catch (SQLException | InstantiationException
								| IllegalAccessException
								| ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Notification
									.show("Error Establishing DBConnection = "
											+ e);
						}

						// searchform.removeAllComponents();
						// searchform.addComponent(FloatManagementForm());
					} else if (selectedId == "Transaction Report") {

						searchform.removeAllComponents();
						searchform.addComponent(Transactions());
						container2.removeAllItems();
						String Uname = "gomint";
						String Pword = "gomint";
						String drivers = "com.mysql.jdbc.Driver";
						try {

							Class driver_class = Class.forName(drivers);
							Driver driver = (Driver) driver_class.newInstance();
							DriverManager.registerDriver(driver);

							Connection conn = DriverManager
									.getConnection(
											"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasource",
											Uname, Pword);

							Statement stmt = conn.createStatement();
							Statement stmt2 = conn.createStatement();
							ResultSet rs, rs2;
							int x = 0;
							Object itemId;
							Item trItem;
							rs2 = stmt2
									.executeQuery("select txn.userresourceid as 'Username', txnt.name as 'Transaction Type', txn.lastupdate as 'Timestamp', acct.openingbalance as 'Opening Balance', acct.closingbalance as 'Closing Balance', acct.amount as 'Amount',accts.name as 'Account Type'  from transactions txn join accounttransactions acct on txn.transactionid = acct.transactionid join transactiontypes txnt on txnt.transactiontypeid = txn.transactiontypeid join accounttypes accts on accts.accounttypeid = acct.accounttypeid   join accountholders ah on ah.username = txn.userresourceid group by txn.userresourceid order by txn.lastupdate");
							while (rs2.next()) {
								agent.addItem(rs2.getString("Username"));
								agent.setNullSelectionAllowed(false);
								agent.setTextInputAllowed(false);
								agent.setInputPrompt("Select");

							}
							rs = stmt
									.executeQuery("select txn.userresourceid as 'Username', txnt.name as 'Transaction Type', txn.lastupdate as 'Timestamp', acct.openingbalance as 'Opening Balance', acct.closingbalance as 'Closing Balance', acct.amount as 'Amount',accts.name as 'Account Type'  from transactions txn join accounttransactions acct on txn.transactionid = acct.transactionid join transactiontypes txnt on txnt.transactiontypeid = txn.transactiontypeid join accounttypes accts on accts.accounttypeid = acct.accounttypeid   join accountholders ah on ah.username = txn.userresourceid  order by ah.username, txn.lastupdate");
							while (rs.next()) {
								x = x + 1;

								String transactiontype = rs
										.getString("Transaction Type");
								String amount = rs.getString("Amount");
								String createdon = rs.getString("Timestamp");
								String transactionID = rs.getString("Username");
								String Sender = rs.getString("Opening Balance");
								String Receiver = rs
										.getString("Closing Balance");
								String Status = rs.getString("Account Type");
								itemId = container2.addItem();

								trItem = container2.getItem(itemId);

								@SuppressWarnings("unchecked")
								Property<String> tdPropertyserial = trItem
										.getItemProperty("S/N");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertytransactiondate = trItem
										.getItemProperty("Transaction Date");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertytransactionid = trItem
										.getItemProperty("Name");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertytransactiontype = trItem
										.getItemProperty("Transaction Type");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertyamount = trItem
										.getItemProperty("Amount");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertysender = trItem
										.getItemProperty("Opening Balance");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertyreceiver = trItem
										.getItemProperty("Closing Balance");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertystatus = trItem
										.getItemProperty("Account Type");

								tdPropertyserial.setValue(String.valueOf(x));
								tdPropertytransactionid.setValue(transactionID);
								tdPropertytransactiondate.setValue(createdon);
								tdPropertytransactiontype
										.setValue(transactiontype);
								tdPropertyamount.setValue(amount);
								tdPropertysender.setValue(Sender);
								tdPropertyreceiver.setValue(Receiver);
								tdPropertystatus.setValue(Status);
							}

							conn.close();
							Notification.show(x + " result(s) found");

							table.setContainerDataSource(container2);

						} catch (SQLException | ClassNotFoundException
								| InstantiationException
								| IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Notification
									.show("Error Establishing DBConnection = "
											+ e);
						}

						// searchform.removeAllComponents();
						// searchform.addComponent(SettlementForm());

					} else if (selectedId == "Summary Report") {

						container3.removeAllItems();
						String Uname = "gomint";
						String Pword = "gomint";
						String drivers = "com.mysql.jdbc.Driver";
						try {

							Class driver_class = Class.forName(drivers);
							Driver driver = (Driver) driver_class.newInstance();
							DriverManager.registerDriver(driver);

							Connection conn = DriverManager
									.getConnection(
											"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasource",
											Uname, Pword);

							Statement stmt = conn.createStatement();
							ResultSet rs;
							int x = 0;
							Object itemId;
							Item trItem;

							rs = stmt
									.executeQuery(" select acct.transactionid as 'Transaction ID', ah.username as 'Username',prf.profilename as 'Profile Name', sum(acct.amount) as 'Total Amount', acct.datecreated as 'Date Created', tnst.name as 'Transaction Name' from accounttransactions acct join transactions tns on tns.transactionid = acct.transactionid join accountholders ah on ah.accountholderid = acct.userresourceid join transactiontypes tnst on tnst.transactiontypeid = tns.transactiontypeid join profiles prf on prf.profileid = ah.profileid where prf.profileid = 11 or prf.profileid = 7 or prf.profileid = 6 group by acct.userresourceid, CAST(acct.datecreated AS DATE), tns.transactiontypeid order by acct.userresourceid,tns.transactiontypeid");
							while (rs.next()) {
								x = x + 1;

								String transactiontype = rs
										.getString("Transaction Name");
								String amount = rs.getString("Total Amount");
								String createdon = rs.getString("Date Created");
								String name = rs.getString("Username");

								itemId = container3.addItem();

								trItem = container3.getItem(itemId);

								Property<String> tdPropertyserial = trItem
										.getItemProperty("S/N");
								Property<String> tdPropertyname = trItem
										.getItemProperty("Name");
								Property<String> tdPropertytransactiondate = trItem
										.getItemProperty("Transaction Date");
								Property<String> tdPropertytransactiontype = trItem
										.getItemProperty("Transaction Type");
								Property<String> tdPropertyamount = trItem
										.getItemProperty("Sum of Transaction Amount");

								tdPropertyname.setValue(name);
								tdPropertyserial.setValue(String.valueOf(x));
								tdPropertytransactiondate.setValue(createdon);
								tdPropertytransactiontype
										.setValue(transactiontype);
								tdPropertyamount.setValue(amount);
							}

							conn.close();
							Notification.show(x + "result(s) found");

							table.setContainerDataSource(container3);

						} catch (SQLException | ClassNotFoundException
								| InstantiationException
								| IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Notification
									.show("Error Establishing DBConnection = "
											+ e);
						}

						// searchform.removeAllComponents();
						// searchform.addComponent(SettlementForm());

					}
				}
			}

		});

		export.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				ExcelExport excelExport = new ExcelExport(table);
				excelExport.setReportTitle("PSA Report");
				excelExport.setExportFileName("PSA.xls");
				excelExport.setDisplayTotals(false);
				excelExport.export();

			}
		});

		setSpacing(true);

		table.setSizeFull();

		// table.setContainerDataSource(container);

		pnUserSearchResults = table.createControls();
		pnUserSearchResults2 = table.createControls();

		addComponent(searchform);

		addComponent(pnUserSearchResults);
		addComponent(table);
		addComponent(pnUserSearchResults2);

		addComponent(export);
		setComponentAlignment(export, Alignment.BOTTOM_RIGHT);

	}

	public FormLayout SettlementForm() {
		final DateField from = new DateField("Settlement Report From");
		final DateField to = new DateField("Settlement Report To");
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
		// DateField from = new DateField("Transaction Report From");
		// DateField to = new DateField("Transaction Report To");
		FormLayout transactions = new FormLayout();
		Button search = new Button("Search");
		agent = new ComboBox("Agent/Dealer");

		agent.addItem("dolapo");
		Label lab3 = new Label("Search for Transaction Report");

		transactions.addComponent(lab3);
		// transactions.addComponent(from);
		// transactions.addComponent(to);
		transactions.addComponent(agent);
		// transactions.addComponent(partner);
		transactions.addComponent(search);

		search.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				container2.removeAllContainerFilters();
				Filter filter = new SimpleStringFilter("Name", agent.getValue()
						.toString(), true, true);

				container2.addContainerFilter(filter);
				table.setContainerDataSource(container2);
				// TODO Auto-generated method stub
				agent.setInputPrompt("Select");

			}
		});

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
			String transactionid, String transactiondate, String agentid,
			String dealerid, String amount, IndexedContainer tabContainer) {

		Object itemId;
		Item trItem;

		itemId = tabContainer.addItem();

		trItem = tabContainer.getItem(itemId);

		Property<String> tdPropertyserial = trItem.getItemProperty("S/N");
		Property<String> tdPropertytransactionid = trItem
				.getItemProperty("Transaction ID");
		Property<String> tdPropertytransactiondate = trItem
				.getItemProperty("Transaction Date");
		Property<String> tdPropertyagentid = trItem.getItemProperty("Agent ID");
		Property<String> tdPropertydealerid = trItem
				.getItemProperty("Dealer ID");
		Property<String> tdPropertyamount = trItem.getItemProperty("Amount");

		tdPropertyserial.setValue(serialnumber);
		tdPropertytransactionid.setValue(transactionid);
		tdPropertytransactiondate.setValue(transactiondate);
		tdPropertyagentid.setValue(agentid);
		tdPropertydealerid.setValue(dealerid);
		tdPropertyamount.setValue(amount);

		return tabContainer;

	}

}
