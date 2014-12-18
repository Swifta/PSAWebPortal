package com.swifta.mats.web.report;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

import com.swifta.mats.web.usermanagement.PagedTableCustom;
import com.vaadin.addon.tableexport.ExcelExport;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
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
	IndexedContainer feesCommissionContainer = new IndexedContainer();
	ComboBox agent;
	HashMap<String, HashSet<String>> ht = new HashMap<>();
	ComboBox comboF;
	ComboBox comboVal;

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
		container.addContainerProperty("Dealer's Balance (\u20A6)",
				String.class, "");

		// Transaction

		container2.addContainerProperty("S/N", String.class, "");
		container2.addContainerProperty("Transaction ID", String.class, "");
		container2.addContainerProperty("Transaction Date", String.class, "");
		container2.addContainerProperty("Amount (\u20A6)", String.class, "");
		container2.addContainerProperty("Sender", String.class, "");
		container2.addContainerProperty("Reciever", String.class, "");
		container2.addContainerProperty("Transaction Type", String.class, "");
		container2.addContainerProperty("Status", String.class, "");

		// Summary
		container3.addContainerProperty("S/N", String.class, "");
		// container3.addContainerProperty("Name", String.class, "");
		container3.addContainerProperty("Transaction Date", String.class, "");
		container3.addContainerProperty("Transaction Type", String.class, "");
		container3.addContainerProperty("Sum of Transaction Amount (\u20A6)",
				String.class, "");

		// Commission

		feesCommissionContainer.addContainerProperty("S/N", String.class, "");
		feesCommissionContainer.addContainerProperty("Trans. ID", String.class,
				"");
		feesCommissionContainer.addContainerProperty("Transaction Type",
				String.class, "");
		feesCommissionContainer.addContainerProperty("Commission Account",
				String.class, "");
		feesCommissionContainer.addContainerProperty("Fees Account",
				String.class, "");
		// feesCommissionContainer.addContainerProperty("Transaction Date",
		// String.class, "");

		// feesCommissionContainer.addContainerProperty(
		// "Opening Balance (\u20A6)", String.class, "");

		feesCommissionContainer.addContainerProperty("Adjusted Fees (\u20A6)",
				String.class, "");
		feesCommissionContainer.addContainerProperty("Original Fees (\u20A6)",
				String.class, "");

		feesCommissionContainer.addContainerProperty("Commission (\u20A6)",
				String.class, "");

		feesCommissionContainer.addContainerProperty("Amount (\u20A6)",
				String.class, "");
		// container2.addContainerProperty("Account Type", String.class, "");

		setMargin(true);
		final ComboBox reportType = new ComboBox("Search by Report Type");
		Button export = new Button("Export result");

		Button Add = new Button("Add");

		reportType.addItem("Float Management Report");
		reportType.addItem("Transaction Report");
		reportType.addItem("Summary Report");
		reportType.addItem("Fees / Commission Report");

		reportType.setNullSelectionAllowed(false);
		reportType.setTextInputAllowed(false);
		reportType.setInputPrompt("Select Report Type");

		VerticalLayout cF = new VerticalLayout();
		final HorizontalLayout cByAndVal = new HorizontalLayout();
		cF.addComponent(cByAndVal);

		comboF = new ComboBox("Filter by: ");

		cByAndVal.addComponent(comboF);
		cByAndVal.setSpacing(true);

		addComponent(reportType);
		addComponent(cF);

		comboVal = new ComboBox("Select " + comboF.getValue());
		cByAndVal.addComponent(comboVal);
		comboVal.setVisible(false);

		comboF.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 4792221698725213906L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() == null) {
					comboVal.setVisible(false);
					return;
				}
				String creteria = event.getProperty().getValue().toString();
				HashSet<String> arrL = ht.get(creteria);
				if (arrL == null)
					return;

				comboVal.removeAllItems();
				for (String s : arrL) {
					comboVal.addItem(s);
				}

				comboVal.setCaption("Select " + creteria);
				comboVal.setVisible(true);

			}

		});

		reportType.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				searchform.removeAllComponents();
				String selectedId = (String) reportType.getValue();
				if (selectedId != null) {

					IndexedContainer ds = null;
					if (ht != null) {
						ht.clear();
					}

					if (selectedId.equalsIgnoreCase("Float Management Report")) {

						String Uname = "gomint";
						String Pword = "gomint";
						String drivers = "com.mysql.jdbc.Driver";
						try {

							Class driver_class = Class.forName(drivers);
							Driver driver = (Driver) driver_class.newInstance();
							DriverManager.registerDriver(driver);

							Connection conn = DriverManager
									.getConnection(
											"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasourcetest",
											Uname, Pword);

							Statement stmt = conn.createStatement();
							ResultSet rs;
							int x = 0;
							Object itemId;
							Item trItem;
							container.removeAllItems();
							rs = stmt
									.executeQuery("SELECT count(amount) as 'transactioncount',operatorid,format(sum(amount / 100),2) as 'amount',CAST(createdon as DATE) as 'created',dealerid FROM cashtransactions group by operatorid,CAST(createdon as DATE),dealerid order by created,operatorid");
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
										.getItemProperty("Dealer's Balance (\u20A6)");

								String agentid = rs.getString("operatorid");
								String amount = rs.getString("amount");
								String createdon = rs.getString("created");
								// String transactionstatusid = rs
								// .getString("transactionstatusid");
								String dealerid = rs.getString("dealerid");

								if (!ht.containsKey("Agent ID")) {
									HashSet<String> arrL = new HashSet<>();
									arrL.add(agentid);
									ht.put("Agent ID", arrL);
								} else {
									ht.get("Agent ID").add(agentid);
								}

								if (!ht.containsKey("Dealer ID")) {
									HashSet<String> arrL = new HashSet<>();
									arrL.add(dealerid);
									ht.put("Dealer ID", arrL);
								} else {
									ht.get("Dealer ID").add(dealerid);
								}

								tdPropertyserial.setValue(String.valueOf(x));

								tdPropertytransactiondate.setValue(createdon);
								tdPropertyagentid.setValue(agentid);
								tdPropertydealerid.setValue(dealerid);
								tdPropertyamount.setValue(amount);

							}
							conn.close();
							Notification.show(x + " result(s) found");

							if (x > 30) {
								x = 30;
							}

							table.setPageLength(x);

							table.setContainerDataSource(container);
							ds = container;

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
					} else if (selectedId
							.equalsIgnoreCase("Transaction Report")) {

						searchform.removeAllComponents();
						searchform.addComponent(Transactions());
						container2.removeAllItems();
						ds = container2;
						String Uname = "gomint";
						String Pword = "gomint";
						String drivers = "com.mysql.jdbc.Driver";
						try {

							Class driver_class = Class.forName(drivers);
							Driver driver = (Driver) driver_class.newInstance();
							DriverManager.registerDriver(driver);

							Connection conn = DriverManager
									.getConnection(
											"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasourcetest",
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
							String sql = "select txn.transactionid as 'Transaction ID', tvo.fromamount as 'Amount', fromah.username as 'From', toah.username as 'To',txnt.name as 'Transaction Type', CAST(txn.lastupdate AS DATE) as 'Timestamp', txnst.transactionstatusname as 'Status' from transactions txn join transactionvalueoperations tvo on tvo.transactionid = txn.transactionid join transactionstatus txnst on txnst.transactionstatusid = txn.transactionstatusid join transactiontypes txnt on txnt.transactiontypeid = txn.transactiontypeid join accountholders ah on ah.username = txn.userresourceid join accountholders fromah on fromah.accountholderid = tvo.fromaccountholderuserid join accountholders toah on toah.accountholderid = tvo.toaccountholderresourceid   order by tvo.transactionid, ah.username, txn.lastupdate";
							rs = stmt.executeQuery(sql);
							// .executeQuery("select txn.userresourceid as 'Username', txnt.name as 'Transaction Type', CAST(txn.lastupdate AS DATE) as 'Timestamp', format(acct.openingbalance /100,2) as 'Opening Balance', format(acct.closingbalance / 100,2) as 'Closing Balance', format(acct.amount / 100 , 2) as 'Amount',accts.name as 'Account Type'  from transactions txn join accounttransactions acct on txn.transactionid = acct.transactionid join transactiontypes txnt on txnt.transactiontypeid = txn.transactiontypeid join accounttypes accts on accts.accounttypeid = acct.accounttypeid   join accountholders ah on ah.username = txn.userresourceid  order by ah.username, txn.lastupdate");
							while (rs.next()) {
								x = x + 1;

								String transactiontype = rs
										.getString("Transaction Type");
								String amount = rs.getString("Amount");
								String createdon = rs.getString("Timestamp");
								String transactionID = rs
										.getString("Transaction ID");
								String sender = rs.getString("From");
								String receiver = rs.getString("To");
								String status = rs.getString("Status");

								String fida = "Commission Account";
								String fido = "Fees Account";

								if (!ht.containsKey("Transaction Type")) {
									HashSet<String> arrL = new HashSet<>();
									arrL.add(transactiontype);
									ht.put("Transaction Type", arrL);
								} else {
									ht.get("Transaction Type").add(
											transactiontype);
								}

								if (!ht.containsKey("Status")) {
									HashSet<String> arrL = new HashSet<>();
									arrL.add(status);
									ht.put("Status", arrL);
								} else {
									ht.get("Status").add(status);
								}

								System.out
										.println("-----------------------Print Data :::\nTransaction Type : "
												+ transactiontype
												+ "\nAmount : "
												+ amount
												+ "\nTimestamp : "
												+ createdon
												+ "\nTransaction ID : "
												+ transactionID
												+ "\nFrom : "
												+ sender
												+ "\nTo : "
												+ receiver
												+ "\nStatus : " + status);
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
										.getItemProperty("Transaction ID");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertytransactiontype = trItem
										.getItemProperty("Transaction Type");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertyamount = trItem
										.getItemProperty("Amount (\u20A6)");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertysender = trItem
										.getItemProperty("Sender");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertyreceiver = trItem
										.getItemProperty("Reciever");
								@SuppressWarnings("unchecked")
								Property<String> tdPropertystatus = trItem
										.getItemProperty("Status");

								tdPropertyserial.setValue(String.valueOf(x));
								tdPropertytransactionid.setValue(transactionID);
								tdPropertytransactiondate.setValue(createdon);
								tdPropertytransactiontype
										.setValue(transactiontype);
								tdPropertyamount.setValue(amount);
								tdPropertysender.setValue(sender);
								tdPropertyreceiver.setValue(receiver);
								tdPropertystatus.setValue(status);
							}

							conn.close();
							Notification.show(x + " result(s) found");

							if (x > 30) {
								x = 30;
							}

							table.setPageLength(x);

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

					} else if (selectedId.equalsIgnoreCase("Summary Report")) {

						container3.removeAllItems();
						ds = container3;
						String Uname = "gomint";
						String Pword = "gomint";
						String drivers = "com.mysql.jdbc.Driver";
						try {

							Class driver_class = Class.forName(drivers);
							Driver driver = (Driver) driver_class.newInstance();
							DriverManager.registerDriver(driver);

							Connection conn = DriverManager
									.getConnection(
											"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasourcetest",
											Uname, Pword);

							Statement stmt = conn.createStatement();
							ResultSet rs;
							int x = 0;
							Object itemId;
							Item trItem;

							rs = stmt
									.executeQuery("select acct.transactionid as 'Transaction ID', ah.username as 'Username',prf.profilename as 'Profile Name', format(sum(acct.amount / 100),2) as 'Total Amount', CAST(acct.datecreated AS DATE) as 'Date Created', tnst.name as 'Transaction Name' from accounttransactions acct join transactions tns on tns.transactionid = acct.transactionid join accountholders ah on ah.accountholderid = acct.userresourceid join transactiontypes tnst on tnst.transactiontypeid = tns.transactiontypeid join profiles prf on prf.profileid = ah.profileid where prf.profileid = 11 or prf.profileid = 7 or prf.profileid = 6 group by tns.transactiontypeid, CAST(acct.datecreated AS DATE) order by acct.datecreated,tns.transactiontypeid");
							while (rs.next()) {
								x = x + 1;

								String transactiontype = rs
										.getString("Transaction Name");
								String amount = rs.getString("Total Amount");
								String createdon = rs.getString("Date Created");
								// String name = rs.getString("Username");

								if (!ht.containsKey("Transaction Type")) {
									HashSet<String> arrL = new HashSet<>();
									arrL.add(transactiontype);
									ht.put("Transaction Type", arrL);
								} else {
									ht.get("Transaction Type").add(
											transactiontype);
								}

								itemId = container3.addItem();

								trItem = container3.getItem(itemId);

								Property<String> tdPropertyserial = trItem
										.getItemProperty("S/N");
								// Property<String> tdPropertyname = trItem
								// .getItemProperty("Name");
								Property<String> tdPropertytransactiondate = trItem
										.getItemProperty("Transaction Date");
								Property<String> tdPropertytransactiontype = trItem
										.getItemProperty("Transaction Type");
								Property<String> tdPropertyamount = trItem
										.getItemProperty("Sum of Transaction Amount (\u20A6)");

								// tdPropertyname.setValue(name);
								tdPropertyserial.setValue(String.valueOf(x));
								tdPropertytransactiondate.setValue(createdon);
								tdPropertytransactiontype
										.setValue(transactiontype);
								tdPropertyamount.setValue(amount);
							}

							conn.close();
							Notification.show(x + "result(s) found");

							if (x > 30) {
								x = 30;
							}

							table.setPageLength(x);

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

					} else if (selectedId
							.equalsIgnoreCase("Fees / Commission Report")) {

						searchform.removeAllComponents();
						searchform.addComponent(Transactions());
						feesCommissionContainer.removeAllItems();
						ds = feesCommissionContainer;
						String Uname = "gomint";
						String Pword = "gomint";
						String drivers = "com.mysql.jdbc.Driver";
						try {

							Class driver_class = Class.forName(drivers);
							Driver driver = (Driver) driver_class.newInstance();
							DriverManager.registerDriver(driver);

							Connection conn = DriverManager
									.getConnection(
											"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasourcetest",
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

							// rs = stmt
							// .executeQuery("select trx1.transactionid as txid,trxtyp.name as 'Transaction Type',acth2.username as 'Agent/Dealer',acts1.amount as commission ,acth.username as 'MM Operator',acts2.amount as Fees,acts3.amount as amount from accounttransactions acts1,  transactions trx1,transactiontypes trxtyp, accounttransactions acts2, accounttransactions acts3, accountholders acth, accountholders acth2 where acts1.transactionid = trx1.transactionid and acts2.transactionid = trx1.transactionid and acts2.userresourceid = acth.accountholderid and acts3.userresourceid = acth2.accountholderid and acts3.transactionid = trx1.transactionid and trx1.transactiontypeid = trxtyp.transactiontypeid and acts1.accountresourceid = 12 and acts2.accountresourceid in (select distinct(accountresourceid) from accounttransactions  where userresourceid in (select accountholderid from accountholders where profileid = 15 and accounttypeid = 2)) and acts3.accountresourceid in (select distinct(accountresourceid) from accounttransactions  where userresourceid in (select accountholderid from accountholders where (profileid = 11 or profileid = 6) and accounttypeid = 1))");
							// Notification.show(rs.);
							// rs = stmt
							// .executeQuery("select txn.userresourceid as 'Username', txnt.name as 'Transaction Type', CAST(txn.lastupdate AS DATE) as 'Timestamp', format(acct.openingbalance /100,2) as 'Opening Balance', format(acct.closingbalance / 100,2) as 'Closing Balance', format(acct.amount / 100 , 2) as 'Amount',accts.name as 'Account Type'  from transactions txn join accounttransactions acct on txn.transactionid = acct.transactionid join transactiontypes txnt on txnt.transactiontypeid = txn.transactiontypeid join accounttypes accts on accts.accounttypeid = acct.accounttypeid   join accountholders ah on ah.username = txn.userresourceid  order by ah.username, txn.lastupdate");

							StringBuilder sb = new StringBuilder();
							sb.append("select trx1.transactionid as txid,trxtyp.name as 'Transaction Type','MATS_TOTAL_FEE&COMMISSION' as 'Commission Account',acts1.amount as commission,");

							sb.append("acth.username as 'Fees Account',acts2.amount as 'Adjusted Fees',acts2.amount+acts1.amount as 'Original Fees',acts3.amount as amount,acts1.datecreated as 'DoC' from accounttransactions acts1,  transactions trx1, transactiontypes trxtyp, accounttransactions acts2, accounttransactions acts3, accountholders");

							sb.append(" acth, accountholders acth2, accounts act1, accounts act2, profiles pf1, profiles pf2 where acts1.transactionid = trx1.transactionid and act1.profileid = pf1.profileid and acts2.transactionid = trx1.transactionid and acts2.userresourceid = acth.accountholderid and acts1.accountresourceid = act1.accountid and act2.profileid = pf2.profileid and acts2.accountresourceid = act2.accountid and acts3.userresourceid = acth2.accountholderid and acts3.transactionid = trx1.transactionid and trx1.transactiontypeid = trxtyp.transactiontypeid and acts1.accountresourceid = 12 and acts2.accountresourceid in");

							sb.append(" (select distinct(accountresourceid) from accounttransactions  where userresourceid in (select accountholderid from accountholders where profileid = 15 and accounttypeid = 2)) and acts3.accountresourceid in (select distinct(accountresourceid) from accounttransactions  where userresourceid in (select accountholderid from accountholders where (profileid = 11 or profileid = 6) and accounttypeid = 1))");

							rs = stmt.executeQuery(sb.toString());

							while (rs.next()) {
								x = x + 1;
								String fidTxtype = "Transaction Type";
								String fidCommAcc = "Commission Account";
								String fidFAcc = "Fees Account";

								String transactiontype = rs
										.getString(fidTxtype);
								String feesAccount = rs.getString(fidFAcc);
								String commissionAccount = rs
										.getString(fidCommAcc);

								String transID = rs.getString("txid");

								String commission = rs.getString("commission");

								String afees = rs.getString("Adjusted Fees");

								String ofees = rs.getString("Original Fees");

								String amount = rs.getString("amount");

								String fida = "Commission Account";
								String fido = "Fees Account";

								if (!ht.containsKey(fidTxtype)) {
									HashSet<String> arrL = new HashSet<>();
									arrL.add(transactiontype);
									ht.put(fidTxtype, arrL);
								} else {
									ht.get(fidTxtype).add(transactiontype);
								}

								if (!ht.containsKey(fido)) {
									HashSet<String> arrL = new HashSet<>();
									arrL.add(feesAccount);
									ht.put(fido, arrL);
								} else {
									ht.get(fido).add(feesAccount);
								}

								if (!ht.containsKey(fida)) {
									HashSet<String> arrL = new HashSet<>();
									arrL.add(commissionAccount);
									ht.put(fida, arrL);
								} else {
									ht.get(fida).add(commissionAccount);
								}

								itemId = feesCommissionContainer.addItem();

								trItem = feesCommissionContainer
										.getItem(itemId);

								Property<String> tdPropertyserial = trItem
										.getItemProperty("S/N");
								Property<String> tdPropertyCommAcc = trItem
										.getItemProperty("Commission Account");

								Property<String> tdPropertyFeesAcc = trItem
										.getItemProperty("Fees Account");

								Property<String> tdPropertytransactiontype = trItem
										.getItemProperty("Transaction Type");

								Property<String> tdPropertyAFees = trItem
										.getItemProperty("Adjusted Fees (\u20A6)");
								Property<String> tdPropertyOFees = trItem
										.getItemProperty("Original Fees (\u20A6)");

								Property<String> tdPropertyCommission = trItem
										.getItemProperty("Commission (\u20A6)");

								Property<String> tdPropertyamount = trItem
										.getItemProperty("Amount (\u20A6)");

								Property<String> tdPropertytxid = trItem
										.getItemProperty("Trans. ID");

								tdPropertyserial.setValue(String.valueOf(x));

								tdPropertyCommAcc.setValue(commissionAccount);
								tdPropertyFeesAcc.setValue(feesAccount);
								tdPropertyAFees.setValue(afees);
								tdPropertyOFees.setValue(ofees);
								tdPropertyCommission.setValue(commission);
								tdPropertytxid.setValue(transID);

								tdPropertytransactiontype
										.setValue(transactiontype);

								tdPropertyamount.setValue(amount);

							}

							conn.close();
							Notification.show(x + " result(s) found");

							table.setContainerDataSource(feesCommissionContainer);
							if (x < 10)
								table.setPageLength(x);
							else
								table.setPageLength(10);

							table.setSelectable(true);
							// table.setEditable(true);

						} catch (SQLException | ClassNotFoundException
								| InstantiationException
								| IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Notification
									.show("Error Establishing DBConnection:  "
											+ e);
						}

						// searchform.removeAllComponents();
						// searchform.addComponent(SettlementForm());

					}
					comboF.removeAllItems();
					for (String s : ht.keySet())
						comboF.addItem(s);
					comboF.select(null);
					addFilter(ds);
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

		table.setWidth("100%");
		table.setHeightUndefined();
		table.setPageLength(0);

		// table.setEditable(true);
		// table.setSelectable(true);

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

	public VerticalLayout Transactions() {
		// DateField from = new DateField("Transaction Report From");
		// DateField to = new DateField("Transaction Report To");
		VerticalLayout transactions = new VerticalLayout();
		Button search = new Button("Search");
		agent = new ComboBox("Agent/Dealer");

		agent.addItem("dolapo");
		Label lab3 = new Label("Search for Transaction Report");

		// transactions.addComponent(lab3);
		// transactions.addComponent(from);
		// transactions.addComponent(to);
		// transactions.addComponent(agent);
		// transactions.addComponent(partner);
		// transactions.addComponent(search);

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

	public VerticalLayout Commission() {
		DateField from = new DateField("Commission Report From");
		DateField to = new DateField("Commission Report To");
		VerticalLayout commission = new VerticalLayout();
		Button search = new Button("Search");
		agent = new ComboBox("Agent ID");
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

	private void addFilter(final IndexedContainer ds) {
		comboVal.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -2214024761998185485L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				ds.removeAllContainerFilters();
				if (comboVal.getValue() != null) {
					Filter filter = new And(new Compare.Equal(comboF.getValue()
							.toString(), comboVal.getValue()));
					ds.addContainerFilter(filter);
				}

				table.setPageLength(15);
				table.setContainerDataSource(ds);
				int t = table.getVisibleItemIds().size();
				if (t > 30) {
					t = 30;
				}
				table.setPageLength(t);

			}

		});

	}

}
