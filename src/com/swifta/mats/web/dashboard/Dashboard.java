package com.swifta.mats.web.dashboard;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Flash;
import com.vaadin.ui.VerticalLayout;

public class Dashboard {

	public static HashMap<String, Double> hm = null;
	public static IndexedContainer otb = null;

	// public static HashMap<String, Double> hm

	public VerticalLayout Addlabel() {

		Flash player = new Flash("Charts", new ThemeResource(
				"Charts/FCF_Column3D.swf"));

		player.setCodebase("http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0");
		player.setId("clsid:D27CDB6E-AE6D-11cf-96B8-444553540000");
		player.setWidth(900 + "px");
		player.setHeight(400 + "px");
		// player.setCodetype(Flash.TYPE_OBJECT);
		// player.setMimeType("application/x-shockwave-flash");
		// String url = "http://www.youtube.com/v/"+videoId+"&autoplay=1";
		// player.setSource(new ExternalResource(url));

		player.setParameter("id", "Column3D");
		// player.setSource(new ThemeResource("Charts/FCF_Column3D.swf"));
		player.setParameter("movie", "Charts/FCF_Column3D.swf");
		player.setParameter("FlashVars", "&dataXML=Data.xml");
		player.setParameter("quality", "high");
		player.setImmediate(true);
		VerticalLayout d = new VerticalLayout();
		d.addComponent(player);
		hm = getChartData();

		return d;

	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, Double> getChartData() {
		IndexedContainer originalTb = new IndexedContainer();
		originalTb.addContainerProperty("Transaction Type", String.class, null);
		originalTb.addContainerProperty("Fees Account", String.class, null);
		HashMap<String, Double> hm = new HashMap<>();
		String Uname = "gomint";
		String Pword = "gomint";
		int x = 0;

		Connection conn;
		try {
			String drivers = "com.mysql.jdbc.Driver";

			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasourcetest",
							Uname, Pword);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("select trx1.transactionid as txid,trxtyp.name as 'Transaction Type',acth2.username as 'Agent/Dealer',acts1.amount as commission ,acth.username as 'MM Operator',acts2.amount as Fees,acts3.amount as amount from accounttransactions acts1,  transactions trx1,transactiontypes trxtyp, accounttransactions acts2, accounttransactions acts3, accountholders acth, accountholders acth2 where acts1.transactionid = trx1.transactionid and acts2.transactionid = trx1.transactionid and acts2.userresourceid = acth.accountholderid and acts3.userresourceid = acth2.accountholderid and acts3.transactionid = trx1.transactionid and trx1.transactiontypeid = trxtyp.transactiontypeid and acts1.accountresourceid = 12 and acts2.accountresourceid in (select distinct(accountresourceid) from accounttransactions  where userresourceid in (select accountholderid from accountholders where profileid = 15 and accounttypeid = 2)) and acts3.accountresourceid in (select distinct(accountresourceid) from accounttransactions  where userresourceid in (select accountholderid from accountholders where (profileid = 11 or profileid = 6) and accounttypeid = 1))");
			// Notification.show(rs.);
			// rs = stmt
			// .executeQuery("select txn.userresourceid as 'Username', txnt.name as 'Transaction Type', CAST(txn.lastupdate AS DATE) as 'Timestamp', format(acct.openingbalance /100,2) as 'Opening Balance', format(acct.closingbalance / 100,2) as 'Closing Balance', format(acct.amount / 100 , 2) as 'Amount',accts.name as 'Account Type'  from transactions txn join accounttransactions acct on txn.transactionid = acct.transactionid join transactiontypes txnt on txnt.transactiontypeid = txn.transactiontypeid join accounttypes accts on accts.accounttypeid = acct.accounttypeid   join accountholders ah on ah.username = txn.userresourceid  order by ah.username, txn.lastupdate");
			while (rs.next()) {
				x = x + 1;
				Object objr = originalTb.addItem();
				Item r = originalTb.getItem(objr);
				Property<String> ptType = r.getItemProperty("Transaction Type");
				Property<String> pfAcc = r.getItemProperty("Fees Account");

				String tType = rs.getString("Transaction Type");
				String fAcc = rs.getString("MM Operator");

				ptType.setValue(tType);
				pfAcc.setValue(fAcc);

				String fidTxtype = "Transaction Type";

				String transactiontype = rs.getString(fidTxtype);
				if (!hm.containsKey(fidTxtype)) {
					hm.put(transactiontype, 1.0);
				} else {
					hm.put(transactiontype, hm.get(fidTxtype) + 1);
				}
			}

			otb = originalTb;

			List<?> rows = originalTb.getItemIds();
			Iterator<?> rItr = rows.iterator();

			while (rItr.hasNext()) {
				Item r = originalTb.getItem(rItr.next());
				Property<?> p = r.getItemProperty("Transaction Type");

			}

			Double total = 0.0;
			Iterator<Entry<String, Double>> itr = hm.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<String, Double> e = itr.next();
				total = total + e.getValue();
			}

			// HashMap<String, Double> nhm = new HashMap<>(hm.size(), 0.75F);

			/*
			 * while (itr.hasNext()) { Entry<String, Double> e = itr.next();
			 * nhm.put(e.getKey(), ((e.getValue() / total) * 100)); }
			 */

			itr = hm.entrySet().iterator();

			while (itr.hasNext()) {
				Entry<String, Double> e = itr.next();
				hm.put(e.getKey(), (e.getValue() / total) * 100);
			}

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Notification.show("Error Establishing DBConnection = " + e);
		}
		return hm;

	}

}
