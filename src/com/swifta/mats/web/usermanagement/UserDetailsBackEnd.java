package com.swifta.mats.web.usermanagement;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class UserDetailsBackEnd {

	public UserDetailsBackEnd() {

	}

	public Map<String, String> getUD(String strUID) {
		if (strUID == null || strUID.trim().isEmpty())
			return Collections.emptyMap();

		HashMap<String, String> hm = new HashMap<>();

		String Uname = "psatestuser";
		String Pword = "psatest_2015";
		String drivers = "com.mysql.jdbc.Driver";
		try {
			Class<?> driver_class = Class.forName(drivers);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			Connection conn = DriverManager
					.getConnection(
							"jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasourcetest",
							Uname, Pword);

			// String qx =
			// "SELECT acth.username as un, acth.msisdn as msisdn, acth.email as email,pf.profilename as prof, acths.accountholderstatusname as status,acthd.firstname as fn ,acthd.lastname as ln,id.identificationnumber as id,ad.streetaddress as street from accountholders acth, accountholderdetails acthd, accountholderstatus acths, identificationattribute id, address ad, profiles pf where acth.accountholderdetailid = acthd.accountdetailsid and acth.accountholderstatusid = acths.accountholderstatusid and acthd.identificationid = id.identificationattrid and acthd.addressid = ad.addressid and pf.profileid = acth.profileid and pf.profiletypeid = 1;";

			String sql = "SELECT  acth.username AS un, acth.msisdn AS msisdn, acth.email AS email, acthd.gender AS gender, acthd.occupation AS occupation, acthd.employer AS employer, acthd.dateofbirth AS dateofbirth, pf.profilename AS prof, acths.accountholderstatusname AS status, acthd.firstname AS fn, acthd.lastname AS ln, c.countryname AS country, cs.state AS state, csl.lganame AS lg, idtype.identificationdescr AS idtype, id.identificationnumber AS id, ad.streetaddress AS street, id.isssuer as issuer, id.issuedate as issuedate, id.expirydate as expirydate,  pc.name as primarycontactname, pc.mobilenumber as primarymobilenumber,  pc.phonenumber as primaryphonenumber, sc.name as secondarycontactname, sc.mobilenumber as secondarymobilenumber,  sc.phonenumber as secondaryphonenumber FROM accountholders acth, accountholderdetails acthd, accountholderstatus acths, identificationattribute id, address ad,  profiles pf, identificationtypes idtype, country c, countrystate cs, countrystatelga csl,  primarycontactinfo pc, secondarycontactinfo sc  WHERE acth.accountholderdetailid = acthd.accountdetailsid AND acth.accountholderstatusid = acths.accountholderstatusid AND acthd.identificationid = id.identificationattrid AND acthd.addressid = ad.addressid AND pf.profileid = acth.profileid AND id.identificationtypeid = idtype.identificationtypeid AND acthd.countryid = c.countryid AND acthd.countrystateid = cs.countrystateid AND acthd.countrystatelgaid = csl.countrystatelgaid AND acthd.primarycontactinfoid = pc.primarycontactinfoid  AND acthd.secondarycontactinfoid = sc.secondarycontactinfoid AND pf.profiletypeid = 1  AND acth.username = '"
					+ strUID + "';";
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			int x = 0;
			if (rs.next()) {
				hm.put("Username", rs.getString("un"));
				hm.put("Gender", rs.getString("gender"));
				hm.put("Occupation", rs.getString("occupation"));
				hm.put("Employer", rs.getString("employer"));
				hm.put("Date of Birth", rs.getString("dateofbirth"));
				hm.put("Status", rs.getString("status"));
				hm.put("First Name", rs.getString("fn"));
				hm.put("Profile Type", rs.getString("prof"));
				hm.put("Last Name", rs.getString("ln"));
				hm.put("ID Type", rs.getString("idtype"));
				hm.put("ID No.", rs.getString("id"));
				hm.put("Street", rs.getString("street"));
				hm.put("Issuer", rs.getString("issuer"));
				hm.put("Issue Date", rs.getString("issuedate"));
				hm.put("Expiry Date", rs.getString("expirydate"));
				hm.put("P-Mobile Phone No.",
						rs.getString("primarymobilenumber"));
				hm.put("P-Alt. Phone No.", rs.getString("primaryphonenumber"));
				hm.put("S-Mobile Phone No.",
						rs.getString("secondarymobilenumber"));
				hm.put("S-Alt. Phone No.", rs.getString("secondaryphonenumber"));
				hm.put("Country", rs.getString("country"));
				hm.put("State", rs.getString("state"));
				hm.put("Local Government", rs.getString("lg"));
				hm.put("MSISDN", rs.getString("msisdn"));
				hm.put("Email", rs.getString("email"));
				hm.put("Full Name",
						hm.get("First Name") + " " + hm.get("Last Name"));

			}

			while (rs.next()) {
				x++;
				// String id = rs.getString("id");
				// String sn = String.valueOf(x);
				// String un = rs.getString("un");
				// String prof = rs.getString("prof");
				// String msisdn = rs.getString("msisdn");
				// String email = rs.getString("email");
				// String fn = rs.getString("fn");

				// String ln = rs.getString("ln");

				// String status = rs.getString("status");

			}

			// Notification.show(x + "", Notification.Type.ERROR_MESSAGE);

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show("DB Connection",
					"Error Establishing DBConnection:  " + e.getMessage(),
					Notification.Type.ERROR_MESSAGE);
		}

		return hm;
	}

	public Map<String, String[]> getUserPersonalInfo(String strTbName,
			String strUID) {

		// getUD("Livepwndz");

		Map<String, String[]> mappedAllData = new HashMap<String, String[]>();

		String strTbNamePersonal = "personal";// , "Account", "Authentication"};
		String strTbNameAccount = "account";
		String strTbNameAuth = "auth";
		String strTbNameActLog = "activity_log";
		String strTbNameAccChangeLog = "account_change_log";

		String strTbNameCurUserAuth = "cur_user_auth";
		String strUserType = (String) UI.getCurrent().getSession()
				.getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE);

		String strUser001 = "001";
		strUID = "001";

		if (strTbName.equals(strTbNamePersonal) && strUID.equals(strUser001)) {

			String arrTbName[] = { "personal" };
			String arrUID[] = { "001" };

			String[] arrTfCaptions = new String[] { "UID", "First Name",
					"Last Name", "Full Name", "Email", "Address", "Phone no." };
			String[] arrTfVals = new String[] { "001", "Amama", "Yoweri",
					"Amama Yoweri", "ouzodinma@swifta.com",
					"6b Crown Court Estate Oniru V1 Lagos", "08089070095" };

			String[] arrOptCaptions = new String[] { "Sex" };
			String[] arrOptSelVals = new String[] { "Male" };

			String[] arrComboCaptions = new String[] { "Type" };
			String[] arrComboSelVals = new String[] { "Transaction" };

			String[] arrDfCaptions = new String[] { "DOC" };
			String[] arrDfVals = new String[] { "5/10/2014" };

			String[][] allData = new String[][] { arrTbName, arrUID,
					arrTfCaptions, arrTfVals, arrOptCaptions, arrOptSelVals,
					arrComboCaptions, arrComboSelVals, arrDfCaptions, arrDfVals };

			;
			mappedAllData.put("arrTbName", allData[0]);
			mappedAllData.put("arrUID", allData[1]);
			mappedAllData.put("arrTfCaptions", allData[2]);
			mappedAllData.put("arrTfVals", allData[3]);
			mappedAllData.put("arrOptCaptions", allData[4]);
			mappedAllData.put("arrOptVals", allData[5]);
			mappedAllData.put("arrComboCaptions", allData[6]);
			mappedAllData.put("arrComboVals", allData[7]);
			mappedAllData.put("arrDfCaptions", allData[8]);
			mappedAllData.put("arrDfVals", allData[9]);

		} else if (strTbName.equals(strTbNameAccount)
				&& strUID.equals(strUser001)) {

			String arrTbName[] = { "account" };
			String arrUID[] = { "001" };

			String[] arrTfCaptions = new String[] { "Account ID",
					"Account Name" };
			String[] arrTfVals = new String[] { "001", "Amama" };

			String[] arrOptCaptions = new String[] { "Status" };
			String[] arrOptSelVals = new String[] { "Active" };

			String[] arrComboCaptions = new String[] { "Type" };
			String[] arrComboSelVals = new String[] { "Transaction" };

			String[] arrDfCaptions = new String[] { "DOC" };
			String[] arrDfVals = new String[] { "5/10/2014" };

			String[][] allData = new String[][] { arrTbName, arrUID,
					arrTfCaptions, arrTfVals, arrOptCaptions, arrOptSelVals,
					arrComboCaptions, arrComboSelVals, arrDfCaptions, arrDfVals };

			mappedAllData.put("arrTbName", allData[0]);
			mappedAllData.put("arrUID", allData[1]);
			mappedAllData.put("arrTfCaptions", allData[2]);
			mappedAllData.put("arrTfVals", allData[3]);
			mappedAllData.put("arrOptCaptions", allData[4]);
			mappedAllData.put("arrOptVals", allData[5]);
			mappedAllData.put("arrComboCaptions", allData[6]);
			mappedAllData.put("arrComboVals", allData[7]);
			mappedAllData.put("arrDfCaptions", allData[8]);
			mappedAllData.put("arrDfVals", allData[9]);

		} else if (strTbName.equals(strTbNameAuth) && strUID.equals(strUser001)) {

			if (strUserType
					.equals(WorkSpaceManageUser.SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE)) {
				String arrTbName[] = { "auth" };
				String arrUID[] = { "001" };

				String[] arrTfCaptions = new String[] { "Agent ID",
						"Agent Name", "MSISDN", "PIN Status" };
				String[] arrTfVals = new String[] { arrUID[0], "Seno and Co.",
						"+256707181923", "Set" };

				String[] arrOptCaptions = null;
				String[] arrOptSelVals = null;

				String[] arrComboCaptions = null;
				String[] arrComboSelVals = null;

				String[] arrDfCaptions = null;
				String[] arrDfVals = null;

				String[][] allData = new String[][] { arrTbName, arrUID,
						arrTfCaptions, arrTfVals, arrOptCaptions,
						arrOptSelVals, arrComboCaptions, arrComboSelVals,
						arrDfCaptions, arrDfVals };

				mappedAllData.put("arrTbName", allData[0]);
				mappedAllData.put("arrUID", allData[1]);
				mappedAllData.put("arrTfCaptions", allData[2]);
				mappedAllData.put("arrTfVals", allData[3]);
				mappedAllData.put("arrOptCaptions", allData[4]);
				mappedAllData.put("arrOptVals", allData[5]);
				mappedAllData.put("arrComboCaptions", allData[6]);
				mappedAllData.put("arrComboVals", allData[7]);
				mappedAllData.put("arrDfCaptions", allData[8]);
				mappedAllData.put("arrDfVals", allData[9]);
			} else {
				String arrTbName[] = { "auth" };
				String arrUID[] = { "001" };

				String[] arrTfCaptions = new String[] { "Username", "Password",
						"Acccount Status", "Login status", "Last login" };
				String[] arrTfVals = new String[] { "Sevo", "Default",
						"Normal", "Logged in.", "2 min. ago." };

				String[] arrOptCaptions = null;
				String[] arrOptSelVals = null;

				String[] arrComboCaptions = null;
				String[] arrComboSelVals = null;

				String[] arrDfCaptions = null;
				String[] arrDfVals = null;

				String[][] allData = new String[][] { arrTbName, arrUID,
						arrTfCaptions, arrTfVals, arrOptCaptions,
						arrOptSelVals, arrComboCaptions, arrComboSelVals,
						arrDfCaptions, arrDfVals };

				mappedAllData.put("arrTbName", allData[0]);
				mappedAllData.put("arrUID", allData[1]);
				mappedAllData.put("arrTfCaptions", allData[2]);
				mappedAllData.put("arrTfVals", allData[3]);
				mappedAllData.put("arrOptCaptions", allData[4]);
				mappedAllData.put("arrOptVals", allData[5]);
				mappedAllData.put("arrComboCaptions", allData[6]);
				mappedAllData.put("arrComboVals", allData[7]);
				mappedAllData.put("arrDfCaptions", allData[8]);
				mappedAllData.put("arrDfVals", allData[9]);

			}

		} else if (strTbName.equals(strTbNameActLog)
				&& strUID.equals(strUser001)) {

		} else if (strTbName.equals(strTbNameAccChangeLog)
				&& strUID.equals(strUser001)) {

		} else if (strTbName.equals(strTbNameCurUserAuth)
				&& strUID.equals(strUser001)) {

			String arrTbName[] = { "cur_user_auth" };
			String arrUID[] = { "001" };

			String[] arrTfCaptions = new String[] { "Account ID", "Username",
					"Password" };
			String[] arrTfVals = new String[] { "001", "Amama", "************" };

			String[] arrOptCaptions = null;
			String[] arrOptSelVals = null;

			String[] arrComboCaptions = null;
			String[] arrComboSelVals = null;

			String[] arrDfCaptions = null;
			String[] arrDfVals = null;

			String[][] allData = new String[][] { arrTbName, arrUID,
					arrTfCaptions, arrTfVals, arrOptCaptions, arrOptSelVals,
					arrComboCaptions, arrComboSelVals, arrDfCaptions, arrDfVals };

			mappedAllData.put("arrTbName", allData[0]);
			mappedAllData.put("arrUID", allData[1]);
			mappedAllData.put("arrTfCaptions", allData[2]);
			mappedAllData.put("arrTfVals", allData[3]);
			mappedAllData.put("arrOptCaptions", allData[4]);
			mappedAllData.put("arrOptVals", allData[5]);
			mappedAllData.put("arrComboCaptions", allData[6]);
			mappedAllData.put("arrComboVals", allData[7]);
			mappedAllData.put("arrDfCaptions", allData[8]);
			mappedAllData.put("arrDfVals", allData[9]);

		}

		return mappedAllData;
	}
}
