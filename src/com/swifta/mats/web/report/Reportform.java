package com.swifta.mats.web.report;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
import com.vaadin.data.util.filter.Compare.GreaterOrEqual;
import com.vaadin.data.util.filter.Compare.LessOrEqual;
import com.vaadin.data.util.filter.SimpleStringFilter;
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
import com.vaadin.ui.themes.ValoTheme;

public class Reportform extends VerticalLayout {

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
	LinkedHashMap<String, TreeSet<String>> ht = new LinkedHashMap<>();

	IndexedContainer ds;
	ComboBox reportType;
	// private boolean isCriteriaChanged = false;
	private PopupDateField dat = new PopupDateField("From: ");
	private PopupDateField dat2 = new PopupDateField("To: ");
	HorizontalLayout cD;
	Filter cFilter;
	Filter dFilter;
	private HorizontalLayout cByAndVal;
	private Button btnApply;
	private TreeMap<String, String> cMap = new TreeMap<>();
	private boolean isFirstCriteriaChanged = true;
	private Label lb;
	private Button btnReload;

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

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (s == null || e == null)
				return;

			if (s.compareTo(e) > 0) {
				throw new InvalidValueException("Invalid date range.");
			}
		}
	}

	public void reportformat() {

		// table.setRowHeaderMode(RowHeaderMode.INDEX);

		setMargin(true);
		reportType = new ComboBox("Search by Report Type");
		Button export = new Button("Export result");
		// reportType.addItem("Transaction Report(Only Today)");
		reportType.addItem("Transaction Report");
		reportType.addItem("Transaction Summary Report");
		reportType.addItem("Fees / Commission Report");
		reportType.addItem("Fees / Commission Summary Report");
		reportType.addItem("Float Management Report");

		// reportType.setNullSelectionAllowed(false);
		// reportType.setTextInputAllowed(false);
		reportType.setInputPrompt("Select Report Type");

		VerticalLayout cF = new VerticalLayout();
		cD = new HorizontalLayout();
		cD.setSpacing(true);
		cD.setVisible(false);
		lb = new Label("Filter by: ");
		lb.setSizeUndefined();
		lb.setVisible(false);
		lb.setStyleName("label_search_user");
		cF.addComponent(lb);

		// dat.setDateFormat("mm-dd-yy");

		cD.addComponent(dat);
		cD.addComponent(dat2);

		cByAndVal = new HorizontalLayout();
		btnApply = new Button("Apply");
		btnApply.setStyleName(ValoTheme.LINK_LARGE + " btn_s_rx");

		btnApply.setVisible(false);
		btnApply.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 6233032315069180601L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (!isFirstCriteriaChanged)
					return;
				refine();
				isFirstCriteriaChanged = false;
			}
		});

		cF.addComponent(cByAndVal);

		cF.addComponent(cD);

		btnReload = new Button("Reload");
		cF.addComponent(btnReload);

		cF.setSpacing(true);
		cByAndVal.setSpacing(true);

		addComponent(reportType);
		addComponent(cF);

		dat.setImmediate(true);
		dat2.setImmediate(true);

		SimpleDateFormat sdfd = new SimpleDateFormat("dd-MM-yyyy");
		dat.setDateFormat("M/d/yyyy");
		dat2.setDateFormat("M/d/yyyy");

		dat.setValue(Calendar.getInstance().getTime());
		dat2.setValue(Calendar.getInstance().getTime());

		dat.addValidator(new ValidateRange(dat, dat2));
		dat2.addValidator(new ValidateRange(dat, dat2));

		btnReload.setVisible(false);

		dat.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -1020854682753361028L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				dat.setComponentError(null);
				dat2.setComponentError(null);
				Date d = null;
				dat2.setValue(d);

			}

		});

		dat2.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -1020854682753361028L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				dat.setComponentError(null);
				dat2.setComponentError(null);

				if (dat2.getValue() == null || dat.getValue() == null)
					return;
				if (!dat2.isValid())
					return;

				loadData(reportType.getValue(), dat.getValue(), dat2.getValue());

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
				loadData(reportType.getValue(), dat.getValue(), dat2.getValue());
			}

		});

		btnReload.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -8778498673269689279L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (reportType.getValue() == null)
					return;
				loadData(reportType.getValue(), dat.getValue(), dat2.getValue());
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
						"dd-MM-yyyy_HH_mm_ss");

				StringBuilder builder = new StringBuilder();

				builder.append(table.getCaption().replace(' ', '_'));
				builder.append("_");
				builder.append(sdf.format(cal.getTime()));

				ExcelExport excelExport = new ExcelExport(table);
				excelExport.setReportTitle(table.getCaption());
				excelExport.setExportFileName(builder.toString() + ".xls");

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
		// reportType.setValue("Transaction Report(Only Today)");

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
		VerticalLayout transactions = new VerticalLayout();
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

	private void addFilter() {
		if (ds == null)
			return;

		cMap.clear();

		Set<String> arrC = ht.keySet();
		if (arrC == null)
			return;
		Iterator<String> itrc = arrC.iterator();
		ComboBox combo;
		cByAndVal.removeAllComponents();
		while (itrc.hasNext()) {
			String c = itrc.next();
			combo = new ComboBox(c);
			addVCL(combo);
			TreeSet<String> scv = ht.get(c);
			for (String cv : scv)
				combo.addItem(cv);
			cByAndVal.addComponent(combo);
		}

		cByAndVal.addComponent(btnApply);
		if (ds.size() == 0) {
			btnApply.setVisible(false);
			cD.setVisible(true);
			lb.setVisible(true);
		} else {
			btnApply.setVisible(true);
			lb.setVisible(true);
			cD.setVisible(true);
		}

	}

	private void addVCL(final ComboBox combo) {

		combo.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -5075193702278484474L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				isFirstCriteriaChanged = true;
				if (combo.getValue() == null) {
					if (cMap.containsKey(combo.getCaption())) {
						cMap.remove(combo.getCaption());
						return;
					}
				}
				cMap.put(combo.getCaption(), combo.getValue().toString());

			}

		});

	}

	@SuppressWarnings("unchecked")
	private void refine() {

		if (ds == null)
			return;
		if (cMap.size() == 0) {
			ds.removeAllContainerFilters();

			table.setContainerDataSource(ds);
			int x = ds.size();
			if (x > 30)
				x = 30;
			table.setPageLength(x);

			return;
		}

		ds.removeAllContainerFilters();
		Iterator<Entry<String, String>> itrc = cMap.entrySet().iterator();
		while (itrc.hasNext()) {
			Entry<String, String> e = itrc.next();
			cFilter = new SimpleStringFilter(e.getKey(), e.getValue(), true,
					false);
			ds.addContainerFilter(cFilter);
		}

		dat.setValue(null);
		dat2.setValue(null);

		table.setContainerDataSource(ds);
		int t = ds.size();
		if (t > 30) {
			t = 30;
		}

		table.setPageLength(t);
		Iterator<Collection<?>> itr = (Iterator<Collection<?>>) table
				.getItemIds().iterator();
		int i = 0;
		while (itr.hasNext()) {
			i++;
			Object itemid = itr.next();
			Item item = table.getItem(itemid);
			Property<String> p = item.getItemProperty("S/N");
			p.setValue(i + "");

		}

	}

	@SuppressWarnings("unchecked")
	private void loadData(Object ft, Object dat, Object dat2) {
		if (searchform != null)
			searchform.removeAllComponents();
		if (ft == null)
			return;

		String selectedId = ft.toString();
		if (selectedId == null)
			return;
		if (ht != null) {
			ht.clear();
		}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curd = sdf.format(cal.getTime());

		String d1 = ((dat == null) ? curd : sdf.format(dat));
		String d2 = ((dat == null) ? curd : sdf.format(dat2));

		table.setCaption(selectedId);
		if (selectedId.equalsIgnoreCase("Float Management Report")) {

			IndexedContainer container = new IndexedContainer();

			container.addContainerProperty("S/N", String.class, "");
			container.addContainerProperty("Date", Date.class, "");
			container.addContainerProperty("Agent ID", String.class, "");
			container.addContainerProperty("Dealer ID", String.class, "");
			container.addContainerProperty("Full Name", String.class, "");
			container.addContainerProperty("Amount (\u20A6)", String.class, "");

			ds = container;
			String drivers = "com.mysql.jdbc.Driver";
			try {

				Class<?> driver_class = Class.forName(drivers);
				Driver driver = (Driver) driver_class.newInstance();
				DriverManager.registerDriver(driver);

				Connection conn = DriverManager.getConnection(
						MatsWebPortalUI.conf.DB, MatsWebPortalUI.conf.UN,
						MatsWebPortalUI.conf.PW);

				Statement stmt = conn.createStatement();
				ResultSet rs;
				int x = 0;
				Object itemId;
				Item trItem;

				if (!container.removeAllItems()) {
					return;
				}

				StringBuilder agentsql = new StringBuilder();

				// Calendar cal = Calendar.getInstance();
				// Date dx = cal.getTime();
				// sdf = new SimpleDateFormat("yyyy-MM-dd");
				// String dm = sdf.format(dx);

				agentsql.append(" select cast(tbl1.datecreated as DATE) as datecreated, ctrs.operatorid as aid, ach.username as did, concat(achd.firstname,' ',achd.lastname) as fullname, ");
				agentsql.append(" tbl1.cashbalance as amount from accountholders ach, accountholderdetails achd, ( select actxns.userresourceid as cashacctid, sum(actxns.closingbalance) -  ");
				agentsql.append(" sum(actxns.openingbalance) as cashbalance, actxns.datecreated as datecreated, acts.profileid as profileid, actxns.transactionid as transactionid from  ");
				agentsql.append(" accounttransactions actxns, transactions trx,accounts acts where actxns.transactionid = trx.transactionid and trx.transactionstatusid = 1 and  ");
				agentsql.append(" trx.transactiontypeid=1 and  actxns.accountresourceid = acts.accountid and acts.profileid = 12 group by CAST(actxns.datecreated as DATE),  ");
				agentsql.append(" actxns.userresourceid) tbl1 join cashtransactions ctrs on ctrs.transactionid = tbl1.transactionid where ach.profileid = 11 and tbl1.cashacctid =  ");
				agentsql.append(" ach.accountholderid and achd.accountdetailsid = ach.accountholderdetailid and datecreated >= '"
						+ d1
						+ "' and datecreated <= DATE_ADD('"
						+ d2
						+ "', INTERVAL 1 DAY)  ");
				agentsql.append(" order by datecreated desc;  ");

				rs = stmt.executeQuery(agentsql.toString());

				cD.setVisible(true);

				while (rs.next()) {
					x = x + 1;

					itemId = container.addItem();

					trItem = container.getItem(itemId);

					Property<String> tdPropertyserial = trItem
							.getItemProperty("S/N");
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
					String aid = rs.getString("aid");
					String did = rs.getString("did");
					String amt = rs.getString("amount");
					String fullname = rs.getString("fullname");

					Date date = rs.getDate("datecreated");

					if (!ht.containsKey("Dealer ID")) {
						TreeSet<String> arrL = new TreeSet<>();
						arrL.add(did);
						ht.put("Dealer ID", arrL);
					} else {
						ht.get("Dealer ID").add(did);
					}

					if (!ht.containsKey("Agent ID")) {
						TreeSet<String> arrL = new TreeSet<>();
						arrL.add(aid);
						ht.put("Agent ID", arrL);
					} else {
						ht.get("Agent ID").add(aid);
					}

					tdPropertyserial.setValue(x + "");
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
				// table.getd

				table.setContainerDataSource(container);
				table.setSelectable(true);

			} catch (SQLException | InstantiationException
					| IllegalAccessException | ClassNotFoundException
					| NullPointerException e) {
				errorHandler(e);

			}

		} else if (selectedId.equalsIgnoreCase("Transaction Report")) {

			IndexedContainer container2 = new IndexedContainer();
			container2.addContainerProperty("S/N", String.class, "");
			container2.addContainerProperty("Transaction ID", String.class, "");
			container2.addContainerProperty("Date", Date.class, "");

			container2
					.addContainerProperty("Amount (\u20A6)", String.class, "");
			container2.addContainerProperty("Agent/Sender", String.class, "");

			container2.addContainerProperty("Receiver", String.class, "");

			container2.addContainerProperty("Partner", String.class, "");

			container2.addContainerProperty("Transaction Type", String.class,
					"");
			container2.addContainerProperty("Status", String.class, "");

			searchform.removeAllComponents();
			searchform.addComponent(Transactions());
			ds = container2;
			if (!container2.removeAllItems()) {
				return;
			}

			String drivers = "com.mysql.jdbc.Driver";
			try {

				Class<?> driver_class = Class.forName(drivers);
				Driver driver = (Driver) driver_class.newInstance();
				DriverManager.registerDriver(driver);

				Connection conn = DriverManager.getConnection(
						MatsWebPortalUI.conf.DB, MatsWebPortalUI.conf.UN,
						MatsWebPortalUI.conf.PW);

				Statement stmt = conn.createStatement();

				ResultSet rs;
				int x = 0;
				Object itemId;
				Item trItem;
				StringBuilder trxnsqld = new StringBuilder();
				trxnsqld.append(" SELECT txn.transactionid AS 'TransactionID', txn.lastupdate AS 'Timestamps','N/A' as 'Amount',txn.userresourceid as ");
				trxnsqld.append(" 'Sender','N/A' as 'Reciever','N/A' as 'Partner', txnt.name AS 'Transaction Type', txnst.transactionstatusname AS 'Status' ");
				trxnsqld.append(" FROM transactions txn, transactionstatus txnst, transactiontypes txnt WHERE txn.lastupdate >= '"
						+ d1 + "' and txn.lastupdate <=  ");
				trxnsqld.append("  DATE_ADD('"
						+ d2
						+ "', INTERVAL 1 DAY) and txnst.transactionstatusid = txn.transactionstatusid ");
				trxnsqld.append(" AND txnt.transactiontypeid = txn.transactiontypeid and txn.transactionid not in (select txn1.transactionid from  ");
				trxnsqld.append(" accounttransactions txn1 group by txn1.transactionid) ");

				trxnsqld.append(" UNION ");

				trxnsqld.append("  SELECT txn.transactionid AS 'Transaction ID', txn.lastupdate AS 'Timestamps',(actxn.amount * -1) as 'Amount', ");
				trxnsqld.append(" txn.userresourceid as 'Sender',acth.username as 'Reciever', 'N/A' as 'Partner', txnt.name AS 'Transaction Type',  ");
				trxnsqld.append(" txnst.transactionstatusname AS 'Status' FROM transactions txn,  ");
				trxnsqld.append("  transactionstatus txnst, transactiontypes txnt,accounttransactions actxn join accountholders acth on acth.accountholderid = actxn.userresourceid  ");
				trxnsqld.append("  WHERE txn.lastupdate >= '" + d1
						+ "' and txn.lastupdate <=  DATE_ADD('" + d2
						+ "', INTERVAL 1 DAY) and txnst.transactionstatusid  ");
				trxnsqld.append("  = txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid and  ");
				trxnsqld.append("  actxn.transactionid = txn.transactionid and txnt.name = 'DEPOSIT' AND txn.transactionid not in (SELECT txn.transactionid FROM transactions txn,  ");
				trxnsqld.append("  transactionstatus txnst, transactiontypes txnt,accounttransactions actxn join accountholders acth on acth.accountholderid = actxn.userresourceid  ");
				trxnsqld.append("  WHERE txnst.transactionstatusid = txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid and  ");
				trxnsqld.append("   acth.profileid <> 14 and actxn.transactionid = txn.transactionid and txnt.name = 'DEPOSIT' group by actxn.transactionid) group by actxn.transactionid ");

				trxnsqld.append("  UNION ");

				trxnsqld.append("  SELECT txn.transactionid AS 'Transaction ID', txn.lastupdate AS 'Timestamps',actxn.amount as 'Amount', ");
				trxnsqld.append("  txn.userresourceid as 'Sender',acth.username as 'Reciever','N/A' as 'Partner', txnt.name AS 'Transaction Type',  ");
				trxnsqld.append(" txnst.transactionstatusname AS 'Status' FROM transactions txn,  ");
				trxnsqld.append("  transactionstatus txnst, transactiontypes txnt,accounttransactions actxn join accountholders acth on acth.accountholderid = actxn.userresourceid  ");
				trxnsqld.append("  WHERE txn.lastupdate >= '" + d1
						+ "' and txn.lastupdate <= DATE_ADD('" + d2
						+ "', INTERVAL 1 DAY) and txnst.transactionstatusid  ");
				trxnsqld.append("  = txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid and  ");
				trxnsqld.append("   acth.profileid <> 14 and actxn.transactionid = txn.transactionid and txnt.name = 'DEPOSIT' group by actxn.transactionid  ");

				trxnsqld.append(" UNION  ");

				trxnsqld.append(" SELECT txn.transactionid AS 'TransactionID', txn.lastupdate AS 'Timestamps',(actxn.amount * -1) as 'Amount',  ");
				trxnsqld.append(" txnvo.toreceivinguserresource as 'Sender', extpay.refrence1 as 'Reciever',extpay.resourceid as 'Partner', txnt.name AS 'Transaction Type',  ");
				trxnsqld.append(" txnst.transactionstatusname AS 'Status' FROM transactions txn,transactionvalueoperations txnvo, ");
				trxnsqld.append("  transactionstatus txnst, transactiontypes txnt,accounttransactions actxn,externalpaymentreference extpay WHERE  ");
				trxnsqld.append("  txn.lastupdate >= '" + d1
						+ "' and txn.lastupdate <= DATE_ADD('" + d2
						+ "', INTERVAL 1 DAY) and txnst.transactionstatusid = ");
				trxnsqld.append("  txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid and  ");
				trxnsqld.append("  actxn.amount < 0 and actxn.transactionid = txn.transactionid and extpay.transactionid = txn.transactionid and txnvo.transactionid = txn.transactionid group by  ");
				trxnsqld.append("  actxn.transactionid ");

				trxnsqld.append("  UNION ");

				trxnsqld.append(" 	(SELECT txn.transactionid AS 'TransactionID', txn.lastupdate AS 'Timestamps',(actxn.amount * -1) as 'Amount', ");
				trxnsqld.append(" 'N/A' as 'Sender','N/A' as 'Reciever',txn.userresourceid as 'Partner', txnt.name AS 'Transaction Type',  ");
				trxnsqld.append(" txnst.transactionstatusname AS 'Status' FROM transactions txn,  ");
				trxnsqld.append("  transactionstatus txnst, transactiontypes txnt,accounttransactions actxn WHERE txn.lastupdate >= '"
						+ d1 + "' and txn.lastupdate <=  ");
				trxnsqld.append("  DATE_ADD('" + d2 + "', INTERVAL 1 DAY) and ");
				trxnsqld.append("  txnst.transactionstatusid = txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid and  ");
				trxnsqld.append("  actxn.amount < 0 and actxn.transactionid = txn.transactionid and txnt.name <> 'DEPOSIT' and txn.transactionid  ");
				trxnsqld.append("  not in (select extpay.transactionid from  ");
				trxnsqld.append(" externalpaymentreference extpay group by extpay.transactionid) group by txn.transactionid) ");
				trxnsqld.append("  ORDER BY TransactionID,Timestamps; ");

				rs = stmt.executeQuery(trxnsqld.toString());
				cD.setVisible(true);

				while (rs.next()) {
					x = x + 1;

					String transactiontype = rs.getString("Transaction Type");
					// String amount = rs.getString("Amount");
					Date date = rs.getDate("Timestamps");
					String transactionID = rs.getString("TransactionID");
					String sender = rs.getString("Sender");
					String status = rs.getString("Status");
					String amount = rs.getString("Amount");
					String receiver = rs.getString("Reciever");
					String partner = rs.getString("Partner");

					if (!ht.containsKey("Agent/Sender")) {
						TreeSet<String> arrL = new TreeSet<>();
						arrL.add(sender);
						ht.put("Agent/Sender", arrL);
					} else {
						ht.get("Agent/Sender").add(sender);
					}

					if (!ht.containsKey("Partner")) {
						TreeSet<String> arrL = new TreeSet<>();
						arrL.add(partner);
						ht.put("Partner", arrL);
					} else {
						ht.get("Partner").add(partner);
					}

					if (!ht.containsKey("Transaction Type")) {
						TreeSet<String> arrL = new TreeSet<>();
						arrL.add(transactiontype);
						ht.put("Transaction Type", arrL);
					} else {
						ht.get("Transaction Type").add(transactiontype);
					}

					if (!ht.containsKey("Status")) {
						TreeSet<String> arrL = new TreeSet<>();
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
							.getItemProperty("Agent/Sender");
					// Property<String> tdPropertysenderonbehalfof = trItem
					// .getItemProperty("Sent_on_behalf_of");

					// Property<String> tdPropertyreceiver = trItem
					// .getItemProperty("Receiver");
					// Property<String> tdPropertyreceiveronbehalfof = trItem
					// .getItemProperty("Received_on_behalf_of");
					Property<String> tdPropertystatus = trItem
							.getItemProperty("Status");
					Property<String> tdPropertypartner = trItem
							.getItemProperty("Partner");

					tdPropertyserial.setValue(String.valueOf(x));
					tdPropertytransactionid.setValue(transactionID);
					tdPropertytransactiondate.setValue(date);
					tdPropertytransactiontype.setValue(transactiontype);
					// tdPropertyamount.setValue(amount);
					tdPropertysender.setValue(sender);
					tdPropertyamount.setValue(amount);
					tdPropertyreceiver.setValue(receiver);

					tdPropertystatus.setValue(status);
					tdPropertypartner.setValue(partner);

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
					| InstantiationException | IllegalAccessException
					| NullPointerException e) {
				errorHandler(e);
			}

			// searchform.removeAllComponents();
			// searchform.addComponent(SettlementForm());

		} else if (selectedId.equalsIgnoreCase("Transaction Summary Report")) {
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

			String drivers = "com.mysql.jdbc.Driver";
			try {

				Class<?> driver_class = Class.forName(drivers);
				Driver driver = (Driver) driver_class.newInstance();
				DriverManager.registerDriver(driver);

				Connection conn = DriverManager.getConnection(
						MatsWebPortalUI.conf.DB, MatsWebPortalUI.conf.UN,
						MatsWebPortalUI.conf.PW);

				Statement stmt = conn.createStatement();
				ResultSet rs;
				int x = 0;
				Object itemId;
				Item trItem;

				StringBuilder summarysql = new StringBuilder();

				/*
				 * summarysql .append(
				 * "SELECT CAST(txn.lastupdate as DATE) AS 'TransactionDate'" +
				 * ",txnt.name AS 'Transaction Type'" +
				 * ",sum(actxn.amount) as 'Amount'" +
				 * " FROM transactions txn, transactionstatus txnst, transactiontypes txnt,accounttransactions"
				 * +
				 * " actxn,externalpaymentreference extpay, accountholders acth WHERE  txnst.transactionstatusid = txn.transactionstatusid"
				 * + " AND txnt.transactiontypeid = txn.transactiontypeid and "
				 * +
				 * "actxn.amount > 0 and actxn.transactionid = txn.transactionid and acth.accountholderid = actxn.userresourceid "
				 * +
				 * "and acth.profileid <> 14 and extpay.transactionid = txn.transactionid group by txnt.name, CAST(txn.lastupdate as DATE) order by TransactionDate desc"
				 * );
				 */
				summarysql
						.append(" SELECT CAST(txn.lastupdate as DATE) AS 'TransactionDate',txnt.name AS 'Transaction Type',sum(actxn.amount) as 'Amount' ");
				summarysql
						.append("FROM transactions txn, transactionstatus txnst, transactiontypes txnt,accounttransactions actxn,externalpaymentreference extpay,  ");
				summarysql
						.append("accountholders acth WHERE  txnst.transactionstatusid = txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid  ");
				summarysql
						.append("and actxn.amount > 0 and actxn.transactionid = txn.transactionid and acth.accountholderid = actxn.userresourceid  ");
				summarysql
						.append("and acth.profileid <> 14 and txn.lastupdate >= '"
								+ d1 + "' and txn.lastupdate <=  ");
				summarysql
						.append(" DATE_ADD('"
								+ d2
								+ "', INTERVAL 1 DAY) and extpay.transactionid = txn.transactionid group by txnt.name, CAST(txn.lastupdate as DATE)  ");
				summarysql.append("order by TransactionDate desc ");

				rs = stmt.executeQuery(summarysql.toString());

				cD.setVisible(true);
				while (rs.next()) {
					x = x + 1;

					String transactiontype = rs.getString("Transaction Type");
					String amount = rs.getString("Amount");
					// String nooftransactions = rs
					// .getString("No of transactions");
					Date date = rs.getDate("TransactionDate");

					if (!ht.containsKey("Transaction Type")) {
						TreeSet<String> arrL = new TreeSet<>();
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
				errorHandler(e);
			}

			// searchform.removeAllComponents();
			// searchform.addComponent(SettlementForm());

		} else if (selectedId.equalsIgnoreCase("Fees / Commission Report")) {
			cD.setVisible(true);

			IndexedContainer feesCommissionContainer = new IndexedContainer();
			feesCommissionContainer.addContainerProperty("S/N", String.class,
					"");
			feesCommissionContainer.addContainerProperty("Trans. ID",
					String.class, "");
			feesCommissionContainer.addContainerProperty("Transaction Type",
					String.class, "");

			feesCommissionContainer
					.addContainerProperty("Date", Date.class, "");

			feesCommissionContainer.addContainerProperty("Amount (\u20A6)",
					String.class, "");

			feesCommissionContainer.addContainerProperty("Agent ID",
					String.class, "");

			feesCommissionContainer.addContainerProperty(
					"Adjusted Fees (\u20A6)", String.class, "");
			feesCommissionContainer.addContainerProperty(
					"Original Fees (\u20A6)", String.class, "");

			feesCommissionContainer.addContainerProperty("Commission (\u20A6)",
					String.class, "");

			feesCommissionContainer.addContainerProperty(
					"MATS Commission (\u20A6)", String.class, "");

			feesCommissionContainer.addContainerProperty(
					"Total Commission (\u20A6)", String.class, "");

			feesCommissionContainer.addContainerProperty("Partner",
					String.class, "");

			searchform.removeAllComponents();
			searchform.addComponent(Transactions());
			ds = feesCommissionContainer;

			if (!feesCommissionContainer.removeAllItems()) {
				return;
			}

			String drivers = "com.mysql.jdbc.Driver";
			try {

				Class<?> driver_class = Class.forName(drivers);
				Driver driver = (Driver) driver_class.newInstance();
				DriverManager.registerDriver(driver);

				Connection conn = DriverManager.getConnection(
						MatsWebPortalUI.conf.DB, MatsWebPortalUI.conf.UN,
						MatsWebPortalUI.conf.PW);

				Statement stmt = conn.createStatement();
				ResultSet rs;
				int x = 0;
				Object itemId;
				Item trItem;

				StringBuilder sb = new StringBuilder();

				sb.append(" select trx1.transactionid as txid,trxtyp.name as 'Transaction Type',acts1.datecreated as TransactionDate,  ");
				sb.append(" acts3.amount as Amount,txnvo.toreceivinguserresource as 'Agent ID',acts2.amount as 'Adjusted Fees',acts2.amount+acts1.amount as  ");
				sb.append(" 'Original Fees',acts1.amount as commission, (acts1.amount  * 0.25) as 'MATS Comm.', (acts1.amount  * 0.75) as 'Total Commission', acth.username as  ");
				sb.append(" 'Partner' from accounttransactions acts1, transactionvalueoperations txnvo, transactions trx1, transactiontypes trxtyp, accounttransactions acts2,  ");
				sb.append(" accounttransactions acts3, accountholders acth, accountholders acth2, accounts act1, accounts act2, profiles pf1,  ");
				sb.append(" profiles pf2 WHERE acts1.datecreated >= '" + d1
						+ "' and acts1.datecreated <=  DATE_ADD('" + d2
						+ "', INTERVAL 1 DAY)  ");
				sb.append(" and acts1.transactionid = trx1.transactionid and act1.profileid = pf1.profileid and acts2.transactionid =  ");
				sb.append(" trx1.transactionid and acts2.userresourceid = acth.accountholderid and acts1.accountresourceid = act1.accountid and  ");
				sb.append(" act2.profileid = pf2.profileid and acts2.accountresourceid = act2.accountid and acts3.userresourceid =  ");
				sb.append(" acth2.accountholderid and acts3.transactionid = trx1.transactionid and txnvo.transactionid = trx1.transactionid and trx1.transactiontypeid =  ");
				sb.append(" trxtyp.transactiontypeid and acts1.accountresourceid = 12 and acts2.accountresourceid in  ");
				sb.append(" (select distinct(accountresourceid) from accounttransactions  where userresourceid in (select accountholderid from  ");
				sb.append(" accountholders where profileid = 15 and accounttypeid = 2)) and acts3.accountresourceid in  ");
				sb.append(" (select distinct(accountresourceid) from accounttransactions  where userresourceid in (select accountholderid from ");
				sb.append(" accountholders where (profileid = 11 or profileid = 6) and accounttypeid = 1)) order by TransactionDate desc; ");

				rs = stmt.executeQuery(sb.toString());

				while (rs.next()) {
					x = x + 1;

					String transactiontype = rs.getString("Transaction Type");
					String feesAccount = rs.getString("Partner");
					String commissionAccount = rs.getString("Agent ID");

					String transID = rs.getString("txid");

					Date date = rs.getDate("TransactionDate");

					String commission = rs.getString("commission");

					String afees = rs.getString("Adjusted Fees");

					String ofees = rs.getString("Original Fees");

					String amount = rs.getString("amount");

					String mcomm = rs.getString("MATS Comm.");

					String tcomm = rs.getString("Total Commission");

					if (!ht.containsKey("Transaction Type")) {
						TreeSet<String> arrL = new TreeSet<>();
						arrL.add(transactiontype);
						ht.put("Transaction Type", arrL);
					} else {
						ht.get("Transaction Type").add(transactiontype);
					}

					if (!ht.containsKey("Partner")) {
						TreeSet<String> arrL = new TreeSet<>();
						arrL.add(feesAccount);
						ht.put("Partner", arrL);
					} else {
						ht.get("Partner").add(feesAccount);
					}

					if (!ht.containsKey("Agent ID")) {
						TreeSet<String> arrL = new TreeSet<>();
						arrL.add(commissionAccount);
						ht.put("Agent ID", arrL);
					} else {
						ht.get("Agent ID").add(commissionAccount);
					}

					itemId = feesCommissionContainer.addItem();

					trItem = feesCommissionContainer.getItem(itemId);

					Property<String> tdPropertyserial = trItem
							.getItemProperty("S/N");
					Property<String> tdPropertyCommAcc = trItem
							.getItemProperty("Agent ID");

					Property<String> tdPropertyFeesAcc = trItem
							.getItemProperty("Partner");

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

					Property<String> tdPropertymcomm = trItem
							.getItemProperty("MATS Commission (\u20A6)");

					Property<String> tdPropertytcomm = trItem
							.getItemProperty("Total Commission (\u20A6)");

					Property<Date> tdPropertydate = trItem
							.getItemProperty("Date");

					tdPropertyserial.setValue(String.valueOf(x));
					tdPropertyCommAcc.setValue(commissionAccount);
					tdPropertyFeesAcc.setValue(feesAccount);
					tdPropertyAFees.setValue(afees);
					tdPropertyOFees.setValue(ofees);
					tdPropertyCommission.setValue(commission);
					tdPropertytxid.setValue(transID);
					tdPropertytransactiontype.setValue(transactiontype);
					tdPropertyamount.setValue(amount);
					tdPropertymcomm.setValue(mcomm);
					tdPropertytcomm.setValue(tcomm);
					tdPropertydate.setValue(date);

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
					| InstantiationException | IllegalAccessException
					| NullPointerException e) {
				errorHandler(e);
			}
		} else if (selectedId
				.equalsIgnoreCase("Fees / Commission Summary Report")) {

			cD.setVisible(true);

			IndexedContainer feesCommissionContainer = new IndexedContainer();
			feesCommissionContainer.addContainerProperty("S/N", String.class,
					"");
			feesCommissionContainer
					.addContainerProperty("Date", Date.class, "");

			feesCommissionContainer.addContainerProperty("Partner",
					String.class, "");
			feesCommissionContainer.addContainerProperty("Commission (\u20A6)",
					String.class, "");

			feesCommissionContainer.addContainerProperty(
					"Adjusted Fees (\u20A6)", String.class, "");
			feesCommissionContainer.addContainerProperty(
					"Original Fees (\u20A6)", String.class, "");
			feesCommissionContainer.addContainerProperty("Amount (\u20A6)",
					String.class, "");
			searchform.removeAllComponents();
			searchform.addComponent(Transactions());
			ds = feesCommissionContainer;

			String drivers = "com.mysql.jdbc.Driver";
			try {

				Class<?> driver_class = Class.forName(drivers);
				Driver driver = (Driver) driver_class.newInstance();
				DriverManager.registerDriver(driver);

				Connection conn = DriverManager.getConnection(
						MatsWebPortalUI.conf.DB, MatsWebPortalUI.conf.UN,
						MatsWebPortalUI.conf.PW);

				Statement stmt = conn.createStatement();
				ResultSet rs;
				int x = 0;
				Object itemId;
				Item trItem;

				StringBuilder sb = new StringBuilder();
				sb.append(" select acth.username as 'Partner', acts1.datecreated as 'date', sum(acts1.amount) as commission, sum(acts2.amount) as 'Adjusted Fees', ");
				sb.append(" sum(acts2.amount+acts1.amount) as 'Original Fees',sum(acts3.amount) as amount from  ");
				sb.append(" accounttransactions acts1,  transactions trx1, transactiontypes trxtyp, accounttransactions acts2, accounttransactions  ");
				sb.append(" acts3, accountholders acth, accountholders acth2, accounts act1, accounts act2, profiles pf1, profiles pf2 where  ");
				sb.append(" acts1.datecreated >= '" + d1
						+ "' and acts1.datecreated <= DATE_ADD('" + d2
						+ "', INTERVAL 1 DAY) and  ");
				sb.append(" acts1.transactionid = trx1.transactionid and act1.profileid = pf1.profileid and acts2.transactionid =  ");
				sb.append(" trx1.transactionid and acts2.userresourceid = acth.accountholderid and acts1.accountresourceid = act1.accountid and  ");
				sb.append(" act2.profileid = pf2.profileid and acts2.accountresourceid = act2.accountid and acts3.userresourceid =  ");
				sb.append(" acth2.accountholderid and acts3.transactionid = trx1.transactionid and trx1.transactiontypeid =  ");
				sb.append(" trxtyp.transactiontypeid and acts1.accountresourceid = 12 and acts2.accountresourceid in  ");
				sb.append(" (select distinct(accountresourceid) from accounttransactions  where userresourceid in (select accountholderid from  ");
				sb.append(" accountholders where profileid = 15 and accounttypeid = 2)) and acts3.accountresourceid in ");
				sb.append(" (select distinct(accountresourceid) from accounttransactions  where userresourceid in (select accountholderid from ");
				sb.append(" accountholders where (profileid = 11 or profileid = 6) and accounttypeid = 1)) group by acth.username; ");

				// System.out.println(sb.toString());

				rs = stmt.executeQuery(sb.toString());

				while (rs.next()) {
					x = x + 1;

					String partner = rs.getString("Partner");

					String commission = rs.getString("commission");

					String afees = rs.getString("Adjusted Fees");

					String ofees = rs.getString("Original Fees");

					String amount = rs.getString("amount");

					Date date = rs.getDate("date");

					if (!ht.containsKey("Partner")) {
						TreeSet<String> arrL = new TreeSet<>();
						arrL.add(partner);
						ht.put("Partner", arrL);
					} else {
						ht.get("Partner").add(partner);
					}

					itemId = feesCommissionContainer.addItem();

					trItem = feesCommissionContainer.getItem(itemId);

					Property<String> tdPropertyserial = trItem
							.getItemProperty("S/N");
					Property<String> tdPropertyPartner = trItem
							.getItemProperty("Partner");

					Property<String> tdPropertyAFees = trItem
							.getItemProperty("Adjusted Fees (\u20A6)");
					Property<String> tdPropertyOFees = trItem
							.getItemProperty("Original Fees (\u20A6)");

					Property<String> tdPropertyCommission = trItem
							.getItemProperty("Commission (\u20A6)");

					Property<String> tdPropertyamount = trItem
							.getItemProperty("Amount (\u20A6)");

					Property<Date> tdPropertydate = trItem
							.getItemProperty("Date");

					tdPropertyserial.setValue(String.valueOf(x));
					tdPropertyPartner.setValue(partner);
					tdPropertyAFees.setValue(afees);
					tdPropertyOFees.setValue(ofees);
					tdPropertyCommission.setValue(commission);
					tdPropertyamount.setValue(amount);
					tdPropertydate.setValue(date);

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
					| InstantiationException | IllegalAccessException
					| NullPointerException e) {
				errorHandler(e);
			}
		}

		addFilter();
		btnReload.setVisible(true);

	}

	@SuppressWarnings("unchecked")
	private void showDetailsPop(Item row) {
		if (table.getCaption() == null)
			return;

		if (!table.getCaption().equals("Transaction Report"))
			return;

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

		p = row.getItemProperty("Agent/Sender");
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
		popup.setModal(true);
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

				table.setSelectable(true);
				table.setEnabled(true);
			}

		});

	}

	@SuppressWarnings("unchecked")
	public void addDFilters() {
		dat.setComponentError(null);
		dat2.setComponentError(null);
		// isCriteriaChanged = true;
		if (ds == null)
			return;
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

				table.setPageLength(t);
				Iterator<Collection<?>> itr = (Iterator<Collection<?>>) table
						.getItemIds().iterator();
				int i = 0;
				while (itr.hasNext()) {
					i++;
					Object itemid = itr.next();
					Item item = table.getItem(itemid);
					Property<String> p = item.getItemProperty("S/N");
					p.setValue(i + "");

				}

			}

		} catch (Exception e) {

			Notification.show("Error occured", Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();

		}
	}

	private void errorHandler(Exception e) {
		if (e instanceof NullPointerException) {
			Notification.show(
					"Oops... something happened while generating reports.",
					Notification.Type.WARNING_MESSAGE);
			e.printStackTrace();
		} else {
			e.printStackTrace();
			Notification.show("Error Establishing DBConnection = " + e,
					Notification.Type.ERROR_MESSAGE);
		}
	}
}
