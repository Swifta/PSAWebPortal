package com.swifta.mats.web.dashboard;

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
import java.util.Iterator;
import java.util.Map.Entry;

import com.swifta.mats.web.MatsWebPortalUI;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;

public class Dashboard {

	public static HashMap<String, Float> hm = null;
	public static IndexedContainer otb = null;
	private static HashMap<String, HashMap<String, Float>> hmStatusStats;

	// public static HashMap<String, Double> hm

	public static HashMap<String, HashMap<String, Float>> getStatusStats() {
		return hmStatusStats;
	}

	public static HashMap<String, Float> getChartData() {
		return loadData();

	}

	@SuppressWarnings("unchecked")
	public static void updateOtb() {

		IndexedContainer originalTb = new IndexedContainer();
		originalTb.addContainerProperty("Transaction Type", String.class, null);
		originalTb.addContainerProperty("Fees Account", String.class, null);
		originalTb.addContainerProperty("DoT", Date.class, null);
		originalTb.addContainerProperty("Status", String.class, null);
		originalTb.addContainerProperty("Count", Double.class, null);

		int x = 0;

		Connection conn;
		try {
			String drivers = "com.mysql.jdbc.Driver";

			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(MatsWebPortalUI.conf.DB,
					MatsWebPortalUI.conf.UN, MatsWebPortalUI.conf.PW);
			Statement stmt = conn.createStatement();
			StringBuilder trxnsql = new StringBuilder();
			trxnsql.append("SELECT txn.transactionid AS 'Transaction ID', txnt.name AS 'Transaction Type',");
			trxnsql.append("txn.lastupdate AS 'DoT', txn.userresourceid as 'MM Operator', txnst.transactionstatusname AS 'Status' ");
			trxnsql.append("FROM transactions txn, transactionstatus txnst, transactiontypes txnt WHERE txnst.transactionstatusid = txn.transactionstatusid ");
			trxnsql.append("AND txnt.transactiontypeid = txn.transactiontypeid ");
			trxnsql.append("ORDER BY txn.transactionid , txn.lastupdate;");

			String sql = trxnsql.toString();
			ResultSet rs = stmt.executeQuery(sql);
			Object objr = new Object();
			Item r = null;
			Property<String> ptType = null;
			Property<String> pfAcc = null;
			Property<String> pS = null;
			Property<Date> pDoT = null;
			Property<Double> pC = null;
			String tType = "";
			String fAcc = "";
			String s = "";
			Date fDoT = null;
			Calendar cal = Calendar.getInstance();
			Double sc = 0D;

			while (rs.next()) {
				x = x + 1;
				objr = originalTb.addItem();
				r = originalTb.getItem(objr);
				ptType = r.getItemProperty("Transaction Type");
				pfAcc = r.getItemProperty("Fees Account");
				pDoT = r.getItemProperty("DoT");
				pS = r.getItemProperty("Status");
				pC = r.getItemProperty("Count");

				tType = rs.getString("Transaction Type");
				fAcc = rs.getString("MM Operator");
				fDoT = rs.getDate("DoT");
				cal.setTime(fDoT);
				s = rs.getString("Status");

				if (s.equals("SUCCESSFUL"))
					sc++;

				ptType.setValue(tType);
				pfAcc.setValue(fAcc);
				pDoT.setValue(cal.getTime());
				pS.setValue(s);
				pC.setValue(sc);

			}

			otb = originalTb;

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | NullPointerException e) {

			e.printStackTrace();

		}

	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, Float> loadData() {
		IndexedContainer originalTb = new IndexedContainer();
		originalTb.addContainerProperty("Transaction Type", String.class, null);
		originalTb.addContainerProperty("Fees Account", String.class, null);
		originalTb.addContainerProperty("DoT", Date.class, null);
		originalTb.addContainerProperty("Status", String.class, null);
		originalTb.addContainerProperty("Count", Double.class, null);
		HashMap<String, Float> hm = new HashMap<>();
		hmStatusStats = new HashMap<>();

		int x = 0;

		Double sc = 0D;

		Connection conn;
		try {
			String drivers = "com.mysql.jdbc.Driver";

			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(MatsWebPortalUI.conf.DB,
					MatsWebPortalUI.conf.UN, MatsWebPortalUI.conf.PW);
			Statement stmt = conn.createStatement();
			StringBuilder trxnsql = new StringBuilder();
			trxnsql.append("SELECT txn.transactionid AS 'Transaction ID', txnt.name AS 'Transaction Type',");
			trxnsql.append("txn.lastupdate AS 'DoT', txn.userresourceid as 'MM Operator', txnst.transactionstatusname AS 'Status' ");
			trxnsql.append("FROM transactions txn, transactionstatus txnst, transactiontypes txnt WHERE txnst.transactionstatusid = txn.transactionstatusid ");
			trxnsql.append("AND txnt.transactiontypeid = txn.transactiontypeid ");
			trxnsql.append("ORDER BY txn.transactionid , txn.lastupdate;");

			String sql = trxnsql.toString();
			ResultSet rs = stmt.executeQuery(sql);

			String fidTxtype = "Transaction Type";
			Object objr = new Object();
			Item r = null;
			Property<String> ptType = null;
			Property<String> pfAcc = null;
			Property<Date> pDoT = null;
			Property<String> pS = null;
			Property<Double> pC = null;
			String tType = "";
			String fAcc = "";
			Date fDoT = null;
			String transactiontype = "";
			String status = null;
			Calendar cal = Calendar.getInstance();
			while (rs.next()) {
				x = x + 1;
				objr = originalTb.addItem();
				r = originalTb.getItem(objr);
				ptType = r.getItemProperty("Transaction Type");
				pfAcc = r.getItemProperty("Fees Account");
				pDoT = r.getItemProperty("DoT");
				pS = r.getItemProperty("Status");
				pC = r.getItemProperty("Count");

				tType = rs.getString("Transaction Type");
				fAcc = rs.getString("MM Operator");
				String sd = rs.getString("DoT");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					fDoT = sdf.parse(sd);

					System.out
							.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: "
									+ sdf.format(fDoT)
									+ " :xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				} catch (ParseException e) {
					System.out
							.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: "
									+ sdf.format(fDoT)
									+ " :xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
					fDoT = Calendar.getInstance().getTime();
					e.printStackTrace();
				}
				// fDoT = rs.getDate("DoT");
				status = rs.getString("Status");
				if (status.equals("SUCCESSFUL"))
					sc++;

				cal.setTime(fDoT);
				fDoT = cal.getTime();

				ptType.setValue(tType);
				pfAcc.setValue(fAcc);
				pDoT.setValue(fDoT);
				pS.setValue(status);
				pC.setValue(sc);

				transactiontype = rs.getString(fidTxtype);
				if (!hm.containsKey(transactiontype)) {
					HashMap<String, Float> hmStat = new HashMap<>();
					hm.put(transactiontype, 1F);
					hmStat.put(status, 1F);
					hmStatusStats.put(transactiontype, hmStat);
				} else {
					hm.put(transactiontype, hm.get(transactiontype) + 1);
					HashMap<String, Float> hmStat = hmStatusStats
							.get(transactiontype);
					if (!hmStat.containsKey(status)) {
						hmStat.put(status, 1F);
					} else {
						hmStat.put(status, hmStat.get(status) + 1);

					}

				}
			}

			otb = originalTb;

			Float total = 0F;
			Iterator<Entry<String, Float>> itr = hm.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<String, Float> e = itr.next();
				total = total + e.getValue();
			}

			itr = hm.entrySet().iterator();
			int t = otb.size();

			while (itr.hasNext()) {
				Entry<String, Float> e = itr.next();
				hm.put(e.getKey(), ((e.getValue() / t) * 100));

			}

			// itr = hm.entrySet().iterator();

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {

			e.printStackTrace();
			// Notification.show("Error Establishing DBConnection = " + e);
		}

		return hm;
	}
}
