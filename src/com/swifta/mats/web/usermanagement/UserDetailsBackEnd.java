package com.swifta.mats.web.usermanagement;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.swifta.mats.web.MatsWebPortalUI;
import com.vaadin.ui.Notification;

public class UserDetailsBackEnd {

	public UserDetailsBackEnd() {

	}

	public Map<String, String> getUD(String strUID) {
		if (strUID == null || strUID.trim().isEmpty())
			return Collections.emptyMap();

		HashMap<String, String> hm = new HashMap<>();

		String drivers = "com.mysql.jdbc.Driver";
		try {
			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			Connection conn = DriverManager.getConnection(
					MatsWebPortalUI.conf.DB, MatsWebPortalUI.conf.UN,
					MatsWebPortalUI.conf.PW);

			StringBuilder sb = new StringBuilder();

			sb.append(" SELECT  acth.username AS un, acth.msisdn AS msisdn, acth.email AS email, acthd.gender AS gender, acthd.occupation AS occupation, ");
			sb.append(" acthd.employer AS employer, CAST(acthd.dateofbirth as DATE) AS dateofbirth, pf.profilename AS prof, acths.accountholderstatusname  ");
			sb.append(" AS status, acthd.firstname AS fn, acthd.lastname AS ln, c.countryname AS country, cs.state AS state, csl.lganame AS lg,  ");
			sb.append(" idtype.identificationdescr AS idtype, id.identificationnumber AS id, ad.streetaddress AS street, id.isssuer as issuer, CAST(id.issuedate as  ");
			sb.append(" DATE) as issuedate, CAST(id.expirydate as DATE) as expirydate,  pc.name as primarycontactname, pc.mobilenumber as primarymobilenumber,   ");
			sb.append(" pc.phonenumber as primaryphonenumber, sc.name as secondarycontactname, sc.mobilenumber as secondarymobilenumber,  sc.phonenumber as  ");
			sb.append(" secondaryphonenumber, addy.province as province, addy.postalCode as postalcode, addy.city as city FROM accountholders acth, accountholderdetails acthd, accountholderstatus acths, identificationattribute id, address ad, ");
			sb.append("   profiles pf, identificationtypes idtype, country c, countrystate cs, countrystatelga csl,  primarycontactinfo pc, secondarycontactinfo sc, ");
			sb.append("  address addy WHERE acth.accountholderdetailid = acthd.accountdetailsid AND acth.accountholderstatusid = acths.accountholderstatusid AND  ");
			sb.append("  acthd.identificationid = id.identificationattrid AND acthd.addressid = ad.addressid AND pf.profileid = acth.profileid AND  ");
			sb.append("  id.identificationtypeid = idtype.identificationtypeid AND acthd.countryid = c.countryid AND acthd.countrystateid = cs.countrystateid AND  ");
			sb.append("   acthd.countrystatelgaid = csl.countrystatelgaid AND acthd.primarycontactinfoid = pc.primarycontactinfoid  AND acthd.secondarycontactinfoid ");
			sb.append("   = sc.secondarycontactinfoid AND pf.profiletypeid = 1 and addy.addressid = acthd.addressid AND acth.username = '"
					+ strUID + "'; ");

			// System.out.println("User details: " + sb.toString());

			// String sql =
			// "SELECT  acth.username AS un, acth.msisdn AS msisdn, acth.email AS email, acthd.gender AS gender, acthd.occupation AS occupation, acthd.employer AS employer, CAST(acthd.dateofbirth as DATE) AS dateofbirth, pf.profilename AS prof, acths.accountholderstatusname AS status, acthd.firstname AS fn, acthd.lastname AS ln, c.countryname AS country, cs.state AS state, csl.lganame AS lg, idtype.identificationdescr AS idtype, id.identificationnumber AS id, ad.streetaddress AS street, id.isssuer as issuer, CAST(id.issuedate as DATE) as issuedate, CAST(id.expirydate as DATE) as expirydate,  pc.name as primarycontactname, pc.mobilenumber as primarymobilenumber,  pc.phonenumber as primaryphonenumber, sc.name as secondarycontactname, sc.mobilenumber as secondarymobilenumber,  sc.phonenumber as secondaryphonenumber FROM accountholders acth, accountholderdetails acthd, accountholderstatus acths, identificationattribute id, address ad,  profiles pf, identificationtypes idtype, country c, countrystate cs, countrystatelga csl,  primarycontactinfo pc, secondarycontactinfo sc  WHERE acth.accountholderdetailid = acthd.accountdetailsid AND acth.accountholderstatusid = acths.accountholderstatusid AND acthd.identificationid = id.identificationattrid AND acthd.addressid = ad.addressid AND pf.profileid = acth.profileid AND id.identificationtypeid = idtype.identificationtypeid AND acthd.countryid = c.countryid AND acthd.countrystateid = cs.countrystateid AND acthd.countrystatelgaid = csl.countrystatelgaid AND acthd.primarycontactinfoid = pc.primarycontactinfoid  AND acthd.secondarycontactinfoid = sc.secondarycontactinfoid AND pf.profiletypeid = 1  AND acth.username = '"
			// + strUID + "';";
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sb.toString());
			Calendar cal = Calendar.getInstance();
			Date dob = null;
			String sdob = null;
			String sdoi = null;
			String sdoe = null;

			if (rs.next()) {

				dob = rs.getDate("dateofbirth");
				if (dob != null) {
					cal.setTime(dob);
					sdob = cal.get(Calendar.DATE) + "/"
							+ cal.get(Calendar.MONTH) + "/"
							+ cal.get(Calendar.YEAR);
				}

				cal.clear();
				dob = rs.getDate("issuedate");
				Date d = null;

				if (dob != null) {

					cal.setTime((dob != null) ? dob : d);
					sdoi = cal.get(Calendar.DATE) + "/"
							+ cal.get(Calendar.MONTH) + "/"
							+ cal.get(Calendar.YEAR);
				}
				cal.clear();
				dob = rs.getDate("expirydate");
				if (dob != null) {
					cal.setTime((dob != null) ? dob : new Date());
					sdoe = cal.get(Calendar.DATE) + "/"
							+ cal.get(Calendar.MONTH) + "/"
							+ cal.get(Calendar.YEAR);
				}

				hm.put("Username", rs.getString("un"));
				hm.put("Gender", rs.getString("gender"));
				hm.put("Occupation", rs.getString("occupation"));
				hm.put("Employer", rs.getString("employer"));
				hm.put("Date of Birth", sdob);
				hm.put("Status", rs.getString("status"));
				hm.put("First Name", rs.getString("fn"));
				hm.put("Profile Type", rs.getString("prof"));
				hm.put("Last Name", rs.getString("ln"));
				hm.put("ID Type", rs.getString("idtype"));
				hm.put("ID No.", rs.getString("id"));
				hm.put("Street", rs.getString("street"));
				hm.put("Issuer", rs.getString("issuer"));
				hm.put("Issue Date", sdoi);
				hm.put("Expiry Date", sdoe);
				hm.put("P-Mobile Phone No.",
						rs.getString("primarymobilenumber"));
				hm.put("P-Alt. Phone No.", rs.getString("primaryphonenumber"));
				hm.put("S-Mobile Phone No.",
						rs.getString("secondarymobilenumber"));
				hm.put("S-Alt. Phone No.", rs.getString("secondaryphonenumber"));
				hm.put("Country", rs.getString("country"));
				hm.put("State", rs.getString("state"));
				hm.put("Local Government", rs.getString("lg"));
				hm.put("City", rs.getString("city"));
				hm.put("Province", rs.getString("province"));
				hm.put("Postal Code", rs.getString("postalcode"));
				hm.put("MSISDN", rs.getString("msisdn"));
				hm.put("Email", rs.getString("email"));
				hm.put("Full Name",
						hm.get("First Name") + " " + hm.get("Last Name"));

			}

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
			Notification.show("DB Connection",
					"Error Establishing DBConnection:  " + e.getMessage(),
					Notification.Type.ERROR_MESSAGE);
		}

		return hm;
	}
}
