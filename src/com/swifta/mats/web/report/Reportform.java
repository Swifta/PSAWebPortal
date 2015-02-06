package com.swifta.mats.web.report;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.mats.web.usermanagement.PagedTableCustom;
import com.vaadin.addon.tableexport.ExcelExport;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Compare.GreaterOrEqual;
import com.vaadin.data.util.filter.Compare.LessOrEqual;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
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
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class Reportform extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 252829471857525213L;
	VerticalLayout searchform = new VerticalLayout();

	PagedTableCustom table = new PagedTableCustom() {

		private static final long serialVersionUID = 1257822354631333070L;

		@Override
		protected String formatPropertyValue(Object rowId, Object colId,
				Property<?> property) {
			Object v = property.getValue();
			if (v instanceof Date) {
				Date dateValue = (Date) v;
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateValue);
				return (cal.get(Calendar.MONTH) + 1 + "/"
						+ cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
			}
			return super.formatPropertyValue(rowId, colId, property);
		}

	};

	HorizontalLayout pnUserSearchResults;
	HorizontalLayout pnUserSearchResults2;

	ComboBox agent;
	HashMap<String, HashSet<String>> ht = new HashMap<>();
	ComboBox comboF;
	ComboBox comboVal;
	IndexedContainer ds;
	private boolean isPopupShowing = false;
	private boolean isTxnReport = false;
	ComboBox reportType;
	boolean isCriteriaChanged = false;
	private PopupDateField dat = new PopupDateField("From: ");
	private PopupDateField dat2 = new PopupDateField("To: ");
	HorizontalLayout cD;
	Filter cFilter;
	Filter dFilter;

	// PagedTableContainerCustom container = new
	// PagedTableContainerCustom(contain);
	// PagedTableContainerCustom container2 = new PagedTableContainerCustom(
	// contain2);
	// PagedTableContainerCustom container3 = new PagedTableContainerCustom(
	// contain3);

	private class ValidateRange implements Validator {
		private static final long serialVersionUID = -5454180295673067279L;
		DateField start;
		DateField end;

		ValidateRange(DateField dat, DateField dat2) {
			this.start = dat;
			this.end = dat2;
		}

		@Override
		public void validate(Object value) throws InvalidValueException {
			Date s = start.getValue();
			Date e = end.getValue();
			if (s == null || e == null)
				return;

			if (s.compareTo(e) > 0) {
				throw new InvalidValueException("Invalid date range");
			}
		}

	}

	public void reportformat() {
		// Float Management

		// Transaction

		// Summary

		// Commission

		// container2.addContainerProperty("Account Type", String.class, "");
		setMargin(true);
		reportType = new ComboBox("Search by Report Type");
		Button export = new Button("Export result");

		Button Add = new Button("Add");

		reportType.addItem("Float Management Report");
		reportType.addItem("Transaction Report");
		reportType.addItem("Summary Report");
		reportType.addItem("Fees / Commission Report");

		// reportType.setNullSelectionAllowed(false);
		// reportType.setTextInputAllowed(false);
		reportType.setInputPrompt("Select Report Type");

		VerticalLayout cF = new VerticalLayout();
		cD = new HorizontalLayout();
		cD.setSpacing(true);
		cD.setVisible(false);

		// dat.setDateFormat("mm-dd-yy");

		cD.addComponent(dat);
		cD.addComponent(dat2);

		final HorizontalLayout cByAndVal = new HorizontalLayout();
		cF.addComponent(cByAndVal);

		cF.addComponent(cD);

		comboF = new ComboBox("Filter by: ");

		Button btnReload = new Button("Reload");
		cF.addComponent(btnReload);
		cF.setSpacing(true);

		cByAndVal.addComponent(comboF);
		cByAndVal.setSpacing(true);

		addComponent(reportType);
		addComponent(cF);

		comboVal = new ComboBox("Select " + comboF.getValue());
		cByAndVal.addComponent(comboVal);
		comboVal.setVisible(false);

		dat.addValidator(new ValidateRange(dat, dat2));
		dat2.addValidator(new ValidateRange(dat, dat2));
		dat.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -1020854682753361028L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				dat.setComponentError(null);
				dat2.setComponentError(null);
				isCriteriaChanged = true;

			}

		});

		dat2.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -1020854682753361028L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// addDFilters();
			}

		});

		dat2.addFocusListener(new FocusListener() {

			@Override
			public void focus(FocusEvent event) {
				if (dat2.getValue() == null || dat.getValue() == null)
					return;
				addDFilters();

			}

		});

		dat.addFocusListener(new FocusListener() {

			@Override
			public void focus(FocusEvent event) {
				if (dat2.getValue() == null || dat.getValue() == null)
					return;
				addDFilters();

			}

		});

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

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (reportType.getValue() == null)
					return;
				loadData(reportType.getValue());
			}

		});

		btnReload.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -8778498673269689279L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (reportType.getValue() == null)
					return;
				loadData(reportType.getValue());
			}
		});

		export.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (table == null || table.size() == 0)
					return;

				Calendar cal = Calendar.getInstance();

				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd-MM-yyyy_HH.mm.ss");

				StringBuilder builder = new StringBuilder();
				builder.append(table.getCaption());
				builder.append("_");
				builder.append(sdf.format(cal.getTime()));
				/*
				 * .append(cal.get(Calendar.DATE)); builder.append("-");
				 * builder.append(cal.get(Calendar.MONTH)); builder.append("-");
				 * builder.append(cal.get(Calendar.YEAR)); builder.append("_");
				 * builder.append(cal.get(Calendar.HOUR)); builder.append(".");
				 * builder.append(cal.get(Calendar.MINUTE));
				 */

				ExcelExport excelExport = new ExcelExport(table);
				excelExport.setReportTitle(table.getCaption());
				excelExport.setExportFileName(builder.toString());
				excelExport.setDisplayTotals(false);
				excelExport.export();

			}
		});

		setSpacing(true);

		table.setWidth("100%");
		table.setHeightUndefined();
		table.setPageLength(0);
		table.setSelectable(true);
		table.setImmediate(true);

		table.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = 5064674940597899639L;

			@Override
			public void itemClick(ItemClickEvent event) {

				showDetailsPop(event.getItem());
			}

		});

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

		/*
		 * m* search.addClickListener(new Button.ClickListener() {
		 * 
		 * 
		 * 
		 * private static final long serialVersionUID = 1L;
		 * 
		 * @Override public void buttonClick(ClickEvent event) {
		 * container2.removeAllContainerFilters(); Filter filter = new
		 * SimpleStringFilter("Name", agent.getValue() .toString(), true, true);
		 * 
		 * container2.addContainerFilter(filter);
		 * table.setContainerDataSource(container2); // TODO Auto-generated
		 * method stub agent.setInputPrompt("Select");
		 * 
		 * } }); *
		 */

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

		/**
		 * search.addClickListener(new Button.ClickListener() {
		 * 
		 * 
		 * 
		 * private static final long serialVersionUID = 1L;
		 * 
		 * @Override public void buttonClick(ClickEvent event) {
		 *           container2.removeAllContainerFilters(); Filter filter = new
		 *           SimpleStringFilter("Name", agent.getValue() .toString(),
		 *           true, true);
		 * 
		 *           container2.addContainerFilter(filter);
		 *           table.setContainerDataSource(container2); // TODO
		 *           Auto-generated method stub agent.setInputPrompt("Select");
		 * 
		 *           } }); *
		 */

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

	@SuppressWarnings("unchecked")
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
				if (ds == null)
					return;
				ds.removeAllContainerFilters();
				if (comboVal.getValue() != null && comboF.getValue() != null) {
					cFilter = new And(new Compare.Equal(comboF.getValue()
							.toString(), comboVal.getValue()));
					ds.addContainerFilter(cFilter);
				}

				dat.setValue(null);
				dat2.setValue(null);

				table.setContainerDataSource(ds);
				int t = table.size();
				if (t > 30) {
					t = 30;
				}
				table.setPageLength(t);

			}

		});

	}

	@SuppressWarnings("unchecked")
	private void loadData(Object ft) {
		if (searchform != null)
			searchform.removeAllComponents();

		String selectedId = ft.toString();
		if (selectedId == null)
			return;
		if (ht != null) {
			ht.clear();
		}

		table.setCaption(selectedId);
		if (selectedId.equalsIgnoreCase("Float Management Report")) {

			IndexedContainer container = new IndexedContainer();

			container.addContainerProperty("S/N", String.class, "");
			container.addContainerProperty("Date", Date.class, "");
			// container.addContainerProperty("Transaction ID",
			// String.class, "");

			// container.addContainerProperty("Transaction Date",
			// String.class, "");
			// container
			// .addContainerProperty("Agent ID", String.class, "");
			container.addContainerProperty("Agent ID", String.class, "");
			container.addContainerProperty("Dealer ID", String.class, "");
			container.addContainerProperty("Full Name", String.class, "");
			container.addContainerProperty("Amount (\u20A6)", String.class, "");

			ds = container;

			String Uname = "psaproduser";
			String Pword = "psaproduser@2015";
			String drivers = "com.mysql.jdbc.Driver";
			try {

				Class driver_class = Class.forName(drivers);
				Driver driver = (Driver) driver_class.newInstance();
				DriverManager.registerDriver(driver);

				Connection conn = DriverManager.getConnection(
						MatsWebPortalUI.dbconn, Uname, Pword);

				Statement stmt = conn.createStatement();
				ResultSet rs;
				int x = 0;
				Object itemId;
				Item trItem;
				ds = container;
				if (!container.removeAllItems()) {
					return;
				}

				StringBuilder agentsql = new StringBuilder();

				agentsql.append("select cast(tbl1.datecreated as DATE) as 'date', ctrs.operatorid as aid, ach.username as did, concat(achd.firstname,' ',achd.lastname) as fullname, tbl1.cashbalance as ");
				agentsql.append(" amount from accountholders ach, accountholderdetails achd, ( select actxns.userresourceid as cashacctid, ");
				agentsql.append(" sum(actxns.closingbalance) - sum(actxns.openingbalance) as cashbalance, actxns.datecreated ");
				agentsql.append("as datecreated, acts.profileid as profileid, actxns.transactionid as transactionid from");
				agentsql.append(" accounttransactions actxns, transactions trx,accounts acts where actxns.transactionid = trx.transactionid and trx.transactionstatusid = 1 and trx.transactiontypeid=1 and  actxns.accountresourceid = acts.accountid and ");
				agentsql.append(" acts.profileid = 12 group by CAST(actxns.datecreated as DATE),actxns.userresourceid) tbl1 join cashtransactions ctrs on ctrs.transactionid = tbl1.transactionid where ach.profileid = 11 and tbl1.cashacctid = ach.accountholderid and achd.accountdetailsid = ach.accountholderdetailid");

				// rs = stmt
				// .executeQuery("SELECT count(amount) as 'transactioncount',operatorid,format(sum(amount / 100),2) as 'amount',CAST(createdon as DATE) as 'created',dealerid FROM cashtransactions group by operatorid,CAST(createdon as DATE),dealerid order by created,operatorid");
				rs = stmt.executeQuery(agentsql.toString());

				cD.setVisible(true);

				while (rs.next()) {
					x = x + 1;

					itemId = container.addItem();

					trItem = container.getItem(itemId);

					Property<String> tdPropertyserial = trItem
							.getItemProperty("S/N");

					// Property<String> tdPropertytransactiondate =
					// trItem
					// .getItemProperty("Transaction Date");
					// Property<String> tdPropertyagentid = trItem
					// .getItemProperty("Agent ID");
					Property<String> tdPropertydealerid = trItem
							.getItemProperty("Dealer ID");
					Property<String> tdPropertyagentid = trItem
							.getItemProperty("Agent ID");
					Property<String> tdPropertyevalueamount = trItem
							.getItemProperty("Amount (\u20A6)");

					Property<Date> tdPropertydate = trItem
							.getItemProperty("Date");

					Property<String> tdPropertyfullname = trItem
							.getItemProperty("Full Name");

					// String agentid = rs.getString("operatorid");
					String aid = rs.getString("aid");
					String did = rs.getString("did");
					// String createdon = rs.getString("created");
					// String transactionstatusid = rs
					// .getString("transactionstatusid");
					String amt = rs.getString("amount");
					String fullname = rs.getString("fullname");

					// Calendar cal = Calendar.getIns

					String d = rs.getString("date");

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = sdf.parse(d);
					} catch (ParseException e) {

						e.printStackTrace();
					}

					if (!ht.containsKey("Dealer ID")) {
						HashSet<String> arrL = new HashSet<>();
						arrL.add(did);
						ht.put("Dealer ID", arrL);
					} else {
						ht.get("Dealer ID").add(did);
					}

					if (!ht.containsKey("Agent ID")) {
						HashSet<String> arrL = new HashSet<>();
						arrL.add(aid);
						ht.put("Agent ID", arrL);
					} else {
						ht.get("Agent ID").add(aid);
					}

					// DateFormat.

					tdPropertyserial.setValue(String.valueOf(x));
					// tdPropertytransactiondate.setValue(createdon);
					// tdPropertyagentid.setValue(agentid);
					tdPropertydealerid.setValue(did);
					tdPropertyagentid.setValue(aid);
					tdPropertyevalueamount.setValue(amt);
					tdPropertydate.setValue(date);
					tdPropertyfullname.setValue(fullname);

				}
				conn.close();

				Notification.show(x + " result(s) found",
						Notification.Type.WARNING_MESSAGE);

				if (x > 30) {
					x = 30;
				}

				table.setPageLength(x);

				table.setContainerDataSource(container);
				table.setSelectable(true);

			} catch (SQLException | InstantiationException
					| IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				Notification.show("Error Establishing DBConnection = " + e,
						Notification.Type.ERROR_MESSAGE);
			}

			// searchform.removeAllComponents();
			// searchform.addComponent(FloatManagementForm());
		} else if (selectedId.equalsIgnoreCase("Transaction Report")) {

			IndexedContainer container2 = new IndexedContainer();
			container2.addContainerProperty("S/N", String.class, "");
			container2.addContainerProperty("Transaction ID", String.class, "");
			container2.addContainerProperty("Transaction Type", String.class,
					"");

			// container2.addContainerProperty("Amount", String.class, "");

			container2
					.addContainerProperty("Amount (\u20A6)", String.class, "");
			container2.addContainerProperty("Sender", String.class, "");
			container2.addContainerProperty("Receiver", String.class, "");

			// container2.addContainerProperty("Sent_on_behalf_of",
			// String.class,
			// "");
			// container2.addContainerProperty("Receiver", String.class, "");
			// container2.addContainerProperty("Received_on_behalf_of",
			// String.class, "");
			container2.addContainerProperty("Status", String.class, "");
			container2.addContainerProperty("Date", Date.class, "");

			searchform.removeAllComponents();
			searchform.addComponent(Transactions());
			ds = container2;
			if (!container2.removeAllItems()) {
				return;
			}

			String Uname = "psaproduser";
			String Pword = "psaproduser@2015";
			String drivers = "com.mysql.jdbc.Driver";
			try {

				Class driver_class = Class.forName(drivers);
				Driver driver = (Driver) driver_class.newInstance();
				DriverManager.registerDriver(driver);

				Connection conn = DriverManager.getConnection(
						MatsWebPortalUI.dbconn, Uname, Pword);

				Statement stmt = conn.createStatement();

				ResultSet rs;
				int x = 0;
				Object itemId;
				Item trItem;

				// String sql =
				// "select txn.transactionid as 'Transaction ID', tvo.fromamount as 'Amount', fromah.username as 'From', toah.username as 'To',txnt.name as 'Transaction Type', CAST(txn.lastupdate AS DATE) as 'Timestamp', txnst.transactionstatusname as 'Status' from transactions txn join transactionvalueoperations tvo on tvo.transactionid = txn.transactionid join transactionstatus txnst on txnst.transactionstatusid = txn.transactionstatusid join transactiontypes txnt on txnt.transactiontypeid = txn.transactiontypeid join accountholders ah on ah.username = txn.userresourceid join accountholders fromah on fromah.accountholderid = tvo.fromaccountholderuserid join accountholders toah on toah.accountholderid = tvo.toaccountholderresourceid   order by tvo.transactionid, ah.username, txn.lastupdate";
				StringBuilder trxnsql = new StringBuilder();
				// trxnsql.append("select  txn.transactionid as 'Transaction ID', rolejoin.amount as 'Amount', rolejoin.sender as 'Sender', rolejoin.send_onbehalf_of as 'send_onbehalf_of',");
				// trxnsql.append("rolejoin.receiver as 'Receiver', rolejoin.received_onbehalf_of as 'received_onbehalf_of',txnt.name as 'Transaction Type', txn.lastupdate as 'Timestamp', txnst.transactionstatusname as 'Status'");
				// trxnsql.append(" from transactions txn, (select transactionid as transactionid, operatorid as sender, operatorid as send_onbehalf_of, dealerid as receiver, dealerid as received_onbehalf_of,");
				// trxnsql.append("amount as amount from cashtransactions union select transactionid as transactionid,initiatinguserresourceid as sender, realuserresourceid as onbehalf_of_sender,");
				// trxnsql.append("toreceivinguserresource as receiver, (select username from accountholders where accountholderid = torealaccountholderresourceid) as onbehalf_of_receiver, toamount as amount from transactionvalueoperations) rolejoin,");
				// trxnsql.append("transactionstatus txnst, transactiontypes txnt where rolejoin.transactionid = txn.transactionid and txnst.transactionstatusid = txn.transactionstatusid");
				// trxnsql.append(" and txnt.transactiontypeid = txn.transactiontypeid order by rolejoin.transactionid, txn.lastupdate;");
				trxnsql.append("SELECT txn.transactionid AS 'Transaction ID',"
						+ " txn.lastupdate AS 'Timestamps'"
						+ ",'N/A' as 'Amount',txn.userresourceid as 'Sender',"
						+ "'N/A' as 'Reciever',txnt.name AS 'Transaction Type',"
						+ " txnst.transactionstatusname AS 'Status' FROM transactions txn,"
						+ " transactionstatus txnst, transactiontypes txnt"
						+ " WHERE txnst.transactionstatusid = txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid and txn.transactionid not in (select txn1.transactionid from accounttransactions txn1 group by txn1.transactionid) "
						+ "UNION"
						+ " SELECT txn.transactionid AS 'Transaction ID',"
						+ " txn.lastupdate AS 'Timestamps',(actxn.amount * -1) as 'Amount',"
						+ " acth.username as 'Sender',txn.userresourceid as 'Reciever',"
						+ "txnt.name AS 'Transaction Type',"
						+ " txnst.transactionstatusname AS 'Status' "
						+ "FROM transactions txn,  transactionstatus txnst,"
						+ " transactiontypes txnt,accounttransactions actxn"
						+ " join accountholders acth on acth.accountholderid = actxn.userresourceid"
						+ " WHERE txnst.transactionstatusid "
						+ "= txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid"
						+ " and actxn.amount < 0 and actxn.transactionid = txn.transactionid"
						+ " and txnt.name = 'DEPOSIT' group by actxn.transactionid"
						+ " UNION SELECT txn.transactionid AS 'Transaction ID',"
						+ " txn.lastupdate AS 'Timestamps',(actxn.amount * -1) as 'Amount',"
						+ " txn.userresourceid as 'Sender','N/A' as 'Reciever',"
						+ "txnt.name AS 'Transaction Type', "
						+ "txnst.transactionstatusname AS 'Status'"
						+ " FROM transactions txn, transactionstatus txnst,"
						+ " transactiontypes txnt,accounttransactions actxn"
						+ " WHERE txnst.transactionstatusid = txn.transactionstatusid"
						+ " AND txnt.transactiontypeid = txn.transactiontypeid "
						+ "and actxn.amount < 0 and actxn.transactionid = txn.transactionid "
						+ "and txnt.name <> 'DEPOSIT' and txn.transactionid not in (select extpay.transactionid from externalpaymentreference extpay group by extpay.transactionid) ORDER BY 'Transaction ID',Timestamps;");

				// trxnsql.append("SELECT txn.transactionid AS 'Transaction ID', txnt.name AS 'Transaction Type',");
				// trxnsql.append("txn.lastupdate AS 'Timestamp', txn.userresourceid as 'Sender', txnst.transactionstatusname AS 'Status' ");
				// trxnsql.append("FROM transactions txn, transactionstatus txnst, transactiontypes txnt WHERE txnst.transactionstatusid = txn.transactionstatusid ");
				// trxnsql.append("AND txnt.transactiontypeid = txn.transactiontypeid ");
				// trxnsql.append("ORDER BY txn.transactionid , txn.lastupdate;");

				rs = stmt.executeQuery(trxnsql.toString());
				// .executeQuery("select txn.userresourceid as 'Username', txnt.name as 'Transaction Type', CAST(txn.lastupdate AS DATE) as 'Timestamp', format(acct.openingbalance /100,2) as 'Opening Balance', format(acct.closingbalance / 100,2) as 'Closing Balance', format(acct.amount / 100 , 2) as 'Amount',accts.name as 'Account Type'  from transactions txn join accounttransactions acct on txn.transactionid = acct.transactionid join transactiontypes txnt on txnt.transactiontypeid = txn.transactiontypeid join accounttypes accts on accts.accounttypeid = acct.accounttypeid   join accountholders ah on ah.username = txn.userresourceid  order by ah.username, txn.lastupdate");
				cD.setVisible(true);

				while (rs.next()) {
					x = x + 1;

					String transactiontype = rs.getString("Transaction Type");
					// String amount = rs.getString("Amount");
					String createdon = rs.getString("Timestamp");
					String transactionID = rs.getString("Transaction ID");
					String sender = rs.getString("Sender");
					String status = rs.getString("Status");
					String amount = rs.getString("Amount");
					String receiver = rs.getString("Receiver");
					String d = createdon;

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = sdf.parse(d);
					} catch (ParseException e) {

						e.printStackTrace();
					}

					if (!ht.containsKey("Transaction Type")) {
						HashSet<String> arrL = new HashSet<>();
						arrL.add(transactiontype);
						ht.put("Transaction Type", arrL);
					} else {
						ht.get("Transaction Type").add(transactiontype);
					}

					if (!ht.containsKey("Status")) {
						HashSet<String> arrL = new HashSet<>();
						arrL.add(status);
						ht.put("Status", arrL);
					} else {
						ht.get("Status").add(status);
					}

					itemId = container2.addItem();

					trItem = container2.getItem(itemId);

					Property<String> tdPropertyserial = trItem
							.getItemProperty("S/N");

					Property<Date> tdPropertytransactiondate = trItem
							.getItemProperty("Date");
					Property<String> tdPropertytransactionid = trItem
							.getItemProperty("Transaction ID");
					Property<String> tdPropertyamount = trItem
							.getItemProperty("Amount (\u20A6)");

					Property<String> tdPropertyreceiver = trItem
							.getItemProperty("Receiver");
					Property<String> tdPropertytransactiontype = trItem
							.getItemProperty("Transaction Type");
					// Property<String> tdPropertyamount = trItem
					// .getItemProperty("Amount (\u20A6)");
					Property<String> tdPropertysender = trItem
							.getItemProperty("Sender");
					// Property<String> tdPropertysenderonbehalfof = trItem
					// .getItemProperty("Sent_on_behalf_of");

					// Property<String> tdPropertyreceiver = trItem
					// .getItemProperty("Receiver");
					// Property<String> tdPropertyreceiveronbehalfof = trItem
					// .getItemProperty("Received_on_behalf_of");
					Property<String> tdPropertystatus = trItem
							.getItemProperty("Status");

					tdPropertyserial.setValue(String.valueOf(x));
					tdPropertytransactionid.setValue(transactionID);
					tdPropertytransactiondate.setValue(date);
					tdPropertytransactiontype.setValue(transactiontype);
					// tdPropertyamount.setValue(amount);
					tdPropertysender.setValue(sender);
					tdPropertyamount.setValue(amount);
					tdPropertyreceiver.setValue(receiver);

					tdPropertystatus.setValue(status);

				}

				conn.close();
				Notification.show(x + " result(s) found",
						Notification.Type.WARNING_MESSAGE);

				if (x > 30) {
					x = 30;
				}

				table.setPageLength(x);
				table.setSelectable(true);
				table.setContainerDataSource(container2);

			} catch (SQLException | ClassNotFoundException
					| InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Notification.show("Error Establishing DBConnection = " + e,
						Notification.Type.ERROR_MESSAGE);
			}

			// searchform.removeAllComponents();
			// searchform.addComponent(SettlementForm());

		} else if (selectedId.equalsIgnoreCase("Summary Report")) {
			IndexedContainer container3 = new IndexedContainer();

			container3.addContainerProperty("S/N", String.class, "");
			// container3.addContainerProperty("Name", String.class,
			// "");
			container3.addContainerProperty("Date", Date.class, "");
			container3.addContainerProperty("Transaction Type", String.class,
					"");
			// container3.addContainerProperty("Transaction Count",
			// String.class,
			// "");
			container3
					.addContainerProperty("Amount (\u20A6)", String.class, "");
			// container3.addContainerProperty("Status", String.class, "");

			ds = container3;

			if (!container3.removeAllItems()) {
				return;
			}

			String Uname = "psaproduser";
			String Pword = "psaproduser@2015";
			String drivers = "com.mysql.jdbc.Driver";
			try {

				Class driver_class = Class.forName(drivers);
				Driver driver = (Driver) driver_class.newInstance();
				DriverManager.registerDriver(driver);

				Connection conn = DriverManager.getConnection(
						MatsWebPortalUI.dbconn, Uname, Pword);

				Statement stmt = conn.createStatement();
				ResultSet rs;
				int x = 0;
				Object itemId;
				Item trItem;

				StringBuilder summarysql = new StringBuilder();

				summarysql
						.append("SELECT CAST(txn.lastupdate as DATE) AS 'Transaction Date'"
								+ ",txnt.name AS 'Transaction Type'"
								+ ",sum(actxn.amount) as 'Amount'"
								+ " FROM transactions txn, transactionstatus txnst, transactiontypes txnt,accounttransactions"
								+ " actxn,externalpaymentreference extpay, accountholders acth WHERE  txnst.transactionstatusid = txn.transactionstatusid"
								+ " AND txnt.transactiontypeid = txn.transactiontypeid and "
								+ "actxn.amount > 0 and actxn.transactionid = txn.transactionid and acth.accountholderid = actxn.userresourceid "
								+ "and acth.profileid <> 14 and extpay.transactionid = txn.transactionid group by txnt.name, CAST(txn.lastupdate as DATE)");
				// summarysql
				// .append("select txnt.name as 'Transaction Type', count(txnt.name) as 'No of transactions', sum(rolejoin.amount) as 'Total Amount', CAST(txn.lastupdate AS DATE) as 'Date',");
				// summarysql
				// .append("txnst.transactionstatusname as 'Status' from transactions txn, (select transactionid as transactionid, operatorid as originatinguser,dealerid as receivinguser,");
				// summarysql
				// .append("amount as amount from cashtransactions union select transactionid as transactionid, realuserresourceid as originatinguser, toreceivinguserresource as receivinguser, toamount as amount");
				// summarysql
				// .append(" from transactionvalueoperations) rolejoin, transactionstatus txnst,transactiontypes txnt where rolejoin.transactionid = txn.transactionid");
				// summarysql
				// .append(" and txnst.transactionstatusid = txn.transactionstatusid and txnt.transactiontypeid = txn.transactiontypeid group by txnt.name,txnst.transactionstatusname,CAST(txn.lastupdate AS DATE)");

				rs = stmt.executeQuery(summarysql.toString());
				cD.setVisible(true);
				while (rs.next()) {
					x = x + 1;

					String transactiontype = rs.getString("Transaction Type");
					String amount = rs.getString("Amount");
					// String nooftransactions = rs
					// .getString("No of transactions");
					String createdon = rs.getString("Transaction Date");
					// String status = rs.getString("Status");
					// System.out.println(nooftransactions);
					// System.out.println(amount);
					// System.out.println(createdon);
					// String name = rs.getString("Username");

					String d = createdon;

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = sdf.parse(d);
					} catch (ParseException e) {

						e.printStackTrace();
					}

					if (!ht.containsKey("Transaction Type")) {
						HashSet<String> arrL = new HashSet<>();
						arrL.add(transactiontype);
						ht.put("Transaction Type", arrL);
					} else {
						ht.get("Transaction Type").add(transactiontype);
					}

					itemId = container3.addItem();

					trItem = container3.getItem(itemId);

					Property<String> tdPropertyserial = trItem
							.getItemProperty("S/N");
					// Property<String> tdPropertyname = trItem
					// .getItemProperty("Name");
					Property<Date> tdPropertytransactiondate = trItem
							.getItemProperty("Date");
					Property<String> tdPropertytransactiontype = trItem
							.getItemProperty("Transaction Type");
					// Property<String> tdPropertytransactioncount = trItem
					// .getItemProperty("Transaction Count");
					Property<String> tdPropertyamount = trItem
							.getItemProperty("Amount (\u20A6)");
					// Property<String> tdPropertytransactionstatus = trItem
					// .getItemProperty("Status");

					// tdPropertyname.setValue(name);
					tdPropertyserial.setValue(String.valueOf(x));
					tdPropertytransactiondate.setValue(date);
					tdPropertytransactiontype.setValue(transactiontype);
					tdPropertyamount.setValue(amount);
					// tdPropertytransactioncount.setValue(nooftransactions);
					// tdPropertytransactionstatus.setValue(status);
				}

				conn.close();
				Notification.show(x + " result(s) found",
						Notification.Type.WARNING_MESSAGE);

				if (x > 30) {
					x = 30;
				}

				table.setPageLength(x);

				table.setContainerDataSource(container3);
				table.setSelectable(true);

			} catch (SQLException | ClassNotFoundException
					| InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Notification.show("Error Establishing DBConnection = " + e,
						Notification.Type.ERROR_MESSAGE);
			}

			// searchform.removeAllComponents();
			// searchform.addComponent(SettlementForm());

		} else if (selectedId.equalsIgnoreCase("Fees / Commission Report")) {

			IndexedContainer feesCommissionContainer = new IndexedContainer();
			feesCommissionContainer.addContainerProperty("S/N", String.class,
					"");
			feesCommissionContainer.addContainerProperty("Trans. ID",
					String.class, "");
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

			feesCommissionContainer.addContainerProperty(
					"Adjusted Fees (\u20A6)", String.class, "");
			feesCommissionContainer.addContainerProperty(
					"Original Fees (\u20A6)", String.class, "");

			feesCommissionContainer.addContainerProperty("Commission (\u20A6)",
					String.class, "");

			feesCommissionContainer.addContainerProperty("Amount (\u20A6)",
					String.class, "");
			searchform.removeAllComponents();
			searchform.addComponent(Transactions());
			ds = feesCommissionContainer;
			// feesCommissionContainer.removeAllItems();
			if (!feesCommissionContainer.removeAllItems()) {
				return;
			}

			ds = feesCommissionContainer;
			String Uname = "psaproduser";
			String Pword = "psaproduser@2015";
			String drivers = "com.mysql.jdbc.Driver";
			try {

				Class driver_class = Class.forName(drivers);
				Driver driver = (Driver) driver_class.newInstance();
				DriverManager.registerDriver(driver);

				Connection conn = DriverManager.getConnection(
						MatsWebPortalUI.dbconn, Uname, Pword);

				Statement stmt = conn.createStatement();
				Statement stmt2 = conn.createStatement();
				ResultSet rs, rs2;
				int x = 0;
				Object itemId;
				Item trItem;
				rs2 = stmt2
						.executeQuery("select txn.userresourceid as 'Username', txnt.name as 'Transaction Type', txn.lastupdate as 'Timestamp', acct.openingbalance as 'Opening Balance', acct.closingbalance as 'Closing Balance', acct.amount as 'Amount',accts.name as 'Account Type'  from transactions txn join accounttransactions acct on txn.transactionid = acct.transactionid join transactiontypes txnt on txnt.transactiontypeid = txn.transactiontypeid join accounttypes accts on accts.accounttypeid = acct.accounttypeid   join accountholders ah on ah.username = txn.userresourceid group by txn.userresourceid order by txn.lastupdate");
				cD.setVisible(false);

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

					String transactiontype = rs.getString(fidTxtype);
					String feesAccount = rs.getString(fidFAcc);
					String commissionAccount = rs.getString(fidCommAcc);

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

					trItem = feesCommissionContainer.getItem(itemId);

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

					tdPropertytransactiontype.setValue(transactiontype);

					tdPropertyamount.setValue(amount);

				}

				conn.close();
				Notification.show(x + " result(s) found",
						Notification.Type.WARNING_MESSAGE);

				table.setContainerDataSource(feesCommissionContainer);

				if (x > 30)
					x = 30;
				table.setPageLength(x);
				table.setSelectable(true);
				// table.setEditable(true);
			} catch (SQLException | ClassNotFoundException
					| InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Notification.show("Error Establishing DBConnection = " + e,
						Notification.Type.ERROR_MESSAGE);
			}
		}
		comboF.removeAllItems();
		for (String s : ht.keySet())
			comboF.addItem(s);
		comboF.select(null);
		addFilter(ds);

	}

	@SuppressWarnings("unchecked")
	private void showDetailsPop(Item row) {
		if (!reportType.getValue().toString().equals("Transaction Report"))
			return;

		isPopupShowing = true;

		table.setSelectable(false);
		table.setEnabled(false);

		final Window popup = new Window("Transaction Details.");
		popup.setStyleName("w_delete_user");
		popup.setIcon(FontAwesome.KEY);

		popup.center();

		Property<Object> p = row.getItemProperty("Transaction ID");
		String txnID = p.getValue().toString();

		VerticalLayout cDeletePrompt = new VerticalLayout();
		cDeletePrompt.setSpacing(true);
		// cDeletePrompt.setMargin(true);

		Label lbActivationPrompt = new Label();
		lbActivationPrompt.setStyleName("label_txn_d");
		lbActivationPrompt.setWidth("250px");
		lbActivationPrompt.setValue("Showing Details of Transaction of ID: "
				+ txnID);

		HorizontalLayout cLbA = new HorizontalLayout();
		cLbA.setWidth("100%");
		cLbA.addComponent(lbActivationPrompt);
		cLbA.setComponentAlignment(lbActivationPrompt, Alignment.TOP_CENTER);

		FormLayout frmDeleteReason = new FormLayout();
		frmDeleteReason.setSizeUndefined();
		frmDeleteReason.setSpacing(true);
		frmDeleteReason.setMargin(true);
		cDeletePrompt.addComponent(cLbA);
		cDeletePrompt.addComponent(frmDeleteReason);

		p = row.getItemProperty("Transaction Type");
		String txnT = p.getValue().toString();

		p = row.getItemProperty("Sender");
		String sender = p.getValue().toString();

		p = row.getItemProperty("Status");
		String status = p.getValue().toString();

		p = row.getItemProperty("Date");
		String date = p.getValue().toString();

		final Label lb1 = new Label();
		lb1.setCaption("Transaction ID: ");
		lb1.setValue(txnID);

		final Label lb2 = new Label();
		lb2.setCaption("Transation Type: ");
		lb2.setValue(txnT);

		final Label lb3 = new Label();
		lb3.setCaption("Sender: ");
		lb3.setValue(sender);

		final Label lb4 = new Label();
		lb4.setCaption("Status: ");
		lb4.setValue(status);

		final Label lb5 = new Label();
		lb5.setCaption("Date: ");
		lb5.setValue(date);

		// frmDeleteReason.addComponent(cLbA);
		cLbA.setStyleName("x_y_z_me");

		frmDeleteReason.addComponent(lb1);

		frmDeleteReason.addComponent(lb2);
		frmDeleteReason.addComponent(lb3);

		frmDeleteReason.addComponent(lb4);
		frmDeleteReason.addComponent(lb5);

		VerticalLayout cPopupBtns = new VerticalLayout();
		cPopupBtns.setSizeUndefined();
		cPopupBtns.setSpacing(true);

		Button btnCancel = new Button("Close");

		cPopupBtns.addComponent(btnCancel);
		frmDeleteReason.addComponent(cPopupBtns);
		cDeletePrompt.setComponentAlignment(frmDeleteReason,
				Alignment.MIDDLE_CENTER);
		/*
		 * cDeletePrompt .setComponentAlignment(cPopupBtns,
		 * Alignment.BOTTOM_CENTER);
		 */
		popup.setContent(cDeletePrompt);

		UI.getCurrent().addWindow(popup);
		btnCancel.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -9071850366625898895L;

			@Override
			public void buttonClick(ClickEvent event) {
				popup.close();

			}
		});

		popup.addCloseListener(new CloseListener() {
			private static final long serialVersionUID = 3911244162638119820L;

			@Override
			public void windowClose(CloseEvent e) {
				isPopupShowing = false;
				table.setSelectable(true);
				table.setEnabled(true);
			}

		});

	}

	public void addDFilters() {
		dat.setComponentError(null);
		dat2.setComponentError(null);
		isCriteriaChanged = true;
		try {
			dat2.validate();

			Date start = dat.getValue();
			Date end = dat2.getValue();

			// Notification.show(start.get);

			if (start != null && end != null) {

				ds.removeContainerFilter(dFilter);

				dFilter = new And(new GreaterOrEqual("Date", start),
						new LessOrEqual("Date", end));
				ds.addContainerFilter(dFilter);
				table.setContainerDataSource(ds);
				int t = ds.size();
				if (t > 30)
					t = 30;
				table.setPageLength(t);

			}

		} catch (Exception e) {

			Notification.show("Error occured", Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();

		}
	}
}
