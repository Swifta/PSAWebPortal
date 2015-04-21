package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.Date;

import org.apache.axis2.AxisFault;

public class Client {

	public static void main(String args[]) throws Exception {
		try {

			// String result = Client.passreset();

			// Client.passreset();

			// Client.unlockAccount();

			// Client.changepassword();

			// Client.getnonactive();

			// Client.getactive();

			// Client.webauthenticate();

			Client.setProfilepermission();

			// Client.removeProfilePermission();

			// Client.addprofile();

			// Client.deleteprofile();

			// Client.renameprofile();

			// Client.activate();
			// Client.linkuser();
			// Client.unlinkuser();

			// System.out.println(result);

			// Client.sql();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static void register() {

		// UserManagementService register = new UserManagementService();

		String bankAccount = "4326829803";
		int bankCodeid = 1232232;
		String bankdomainNameid = "145";
		String clearingNumber = "65733";
		String currencyid = "6357";
		String email = "ooghenekaro@total.com.ng";
		String msisdn = "08169773939";
		int profileid = 2;
		String securityQuest = "Hello Africa";
		String securityAns = "How r u";
		String termscondition = "gdeyu";
		String username = "166735";
		int countryid = 162;
		// int countrystate = 10;
		// int statelga = 20;
		Date dateofBirth = new Date();
		String employer = "Swifta";
		String firstname = "ALAPERE 2";
		String province = "Kay";
		Date Expirydate = new Date();
		String idNumber = "166735";
		String idType = "PASSP";
		Date Issuedate = new Date();
		String Issue = "Kay";
		String gender = "MALE";
		int languageid = 2;
		String lastname = "STATION";
		int Lgaid = 42;
		String middlename = "OSAYI";
		String PrimaryMobilenumber = "08062944496";
		String PrimaryEmail = "Kay@gesg.com";
		String PrimaryPhonenumber = "08062944496";
		String occupation = "Kay";
		String prefix = "K";
		String SecondaryMobilenumber = "08062944496";
		String SecondaryEmail = "Kayo@gfgy.com";
		String suffix = "Kay";
		int stateid = 20;
		String city = "Kaytyu";
		String postalcode = "234";
		String SecondaryPhonenumber = "08087280449";
		String streetAddress = "Kaygafg7u";

		try {
			String ret = UserManagementService.registerUser(bankAccount,
					bankCodeid, bankdomainNameid, clearingNumber, currencyid,
					email, msisdn, profileid, securityQuest, securityAns,
					termscondition, username, countryid, dateofBirth, employer,
					firstname, gender, languageid, lastname, Lgaid, middlename,
					occupation, prefix, stateid, suffix, city, postalcode,
					streetAddress, province, Expirydate, idNumber, idType,
					Issuedate, Issue, PrimaryEmail, PrimaryMobilenumber,
					PrimaryPhonenumber, SecondaryEmail, SecondaryMobilenumber,
					SecondaryPhonenumber);
			// Label rep;
			// rep = new Label(ret);
			// adjustPan.addComponent(rep, 0);
			// Notification.show(ret);

			System.out.println(ret);

		} catch (RemoteException e) { // TODO
										// Auto-generated
										// catch //
										// // //
										// block
			e.printStackTrace();
		}

	}

	public static void activate() throws Exception {

		// UserManagementService activate = new UserManagementService();
		// LoginService login = new LoginService();

		// boolean give = login.authenticateUser("Adetolami", "modupe");

		// Notification.show(String.valueOf(give));

		/*
		 * 'readycash','0000000000001' 'fets','00000012' 'fortis','000001234'
		 * 'Livepwndz','001' 'Livepwissndz','00173737' 'Livepwndzzz','001878787'
		 */
		String[] cred = { "readycash", "0000000000001", "fets", "00000012",
				"fortis", "000001234", "Livepwndz", "001", "Livepwissndz",
				"00173737", "Livepwndzzz", "001878787", "paulmugo", "0093939",
				"teasymobile", "009878", "90oooio", "0909", "olumide",
				"092302934", "onir", "0982273", "oniru", "09827273", "dare",
				"09827273/09923823", "matsdealerone", "100100", "matsagentone",
				"100101", "matsadminone", "100102", "systems", "123453",
				"pocketmoni", "123456", "166735", "166735", "Livep", "2111",
				"deejah", "6890", "kenza", "89989889", "comfy", "98989889",
				"username", "98989898989", "kdkkdkd", "9d989d8989", "dealer2",
				"dealer01", "jdjjd", "kdkjkdk", "ldkkdkldkld", "lkkl" };

		for (int i = 0; i < cred.length; i += 2) {

			String userresourceid = cred[i];
			String bankdomainid = "0";
			String IDnumber = cred[i];
			String SecurityAns = "Mrs.fd X";
			String firstPin = "mo123";
			String confirmPin = "mo123";
			String currency = "0";
			String ret = "No response";
			ret = UserManagementService
					.activateUser(bankdomainid, currency, IDnumber,
							userresourceid, SecurityAns, firstPin, confirmPin);

			System.out.println("Username: " + userresourceid + "  id: "
					+ IDnumber);

			System.out.println(ret);
			;
		}

	}

	public static void webauthenticate() throws Exception {
		// LoginService loginService = new LoginService();

		// String[] cred = { "readycash", "0000000000001", "fets", "00000012",
		// "fortis", "000001234", "Livepwndz", "001", "Livepwissndz",
		// "00173737", "Livepwndzzz", "001878787", "paulmugo", "0093939",
		// "teasymobile", "009878", "90oooio", "0909", "olumide",
		// "092302934", "onir", "0982273", "oniru", "09827273", "dare",
		// "09827273/09923823", "matsdealerone", "100100", "matsagentone",
		// "100101", "matsadminone", "100102", "systems", "123453",
		// "pocketmoni", "123456", "166735", "166735", "Livep", "2111",
		// "deejah", "6890", "kenza", "89989889", "comfy", "98989889",
		// "username", "98989898989", "kdkkdkd", "9d989d8989", "dealer2",
		// "dealer01", "jdjjd", "kdkjkdk", "ldkkdkldkld", "lkkl" };

		// for (int i = 0; i < cred.length; i += 2) {
		//
		// String userresourceid = cred[i];
		// String bankdomainid = "0";
		// String IDnumber = cred[i];
		// String SecurityAns = "Mrs.fd X";
		// String firstPin = "mo123";
		// String confirmPin = "mo123";
		// String currency = "0";
		// String ret = "No response";
		// String response = loginService.authenticateUser(userresourceid,
		// "zzzzz");
		// System.out.println("Loggin: " + userresourceid + " Response: "
		// + response);
		// response = loginService.authenticateUser(userresourceid, "zzzzz");
		//
		// System.out.println("Loggin: " + userresourceid + " Response: "
		// + response);
		// response = loginService.authenticateUser(userresourceid, "zzzzz");
		//
		// System.out.println("Loggin: " + userresourceid + " Response: "
		// + response);
		// response = loginService.authenticateUser(userresourceid, "zzzzz");
		//
		// System.out.println("Loggin: " + userresourceid + " Response: "
		// + response);
		//
		// System.out.println(ret);
		//
		String response = LoginService.webauthenticate("username", "12345");
		System.out.println(response);

		// }

	}

	public static void linkuser() throws RemoteException {
		// UserManagementService linkuser = new UserManagementService();

		// 166735
		// 0000000000001
		// 0909
		// matsdealerone//100100
		String parent = "100100";
		String profileid = "8";
		String initiatinguserresourceid = "admin";
		String childuserresourceid = "oniru";
		String[] cred = { "readycash", "0000000000001", "fets", "00000012",
				"fortis", "000001234", "Livepwndz", "001", "Livepwissndz",
				"00173737", "Livepwndzzz", "001878787", "paulmugo", "0093939",
				"teasymobile", "009878", "90oooio", "0909", "olumide",
				"092302934", "onir", "0982273", "oniru", "09827273", "dare",
				"09827273/09923823", "matsdealerone", "100100", "matsagentone",
				"100101", "matsadminone", "100102", "systems", "123453",
				"pocketmoni", "123456", "166735", "166735", "Livep", "2111",
				"deejah", "6890", "kenza", "89989889", "comfy", "98989889",
				"username", "98989898989", "kdkkdkd", "9d989d8989", "dealer2",
				"dealer01", "jdjjd", "kdkjkdk", "ldkkdkldkld", "lkkl" };

		/*
		 * for (int i = 0; i < cred.length; i++) {
		 * 
		 * for (int j = 0; j < cred.length; j++) {
		 * 
		 * childuserresourceid = cred[i]; String parentresourceid = cred[j];
		 * 
		 * String ret = "No response"; ret = linkuser.linkUser(parentresourceid,
		 * profileid, initiatinguserresourceid, childuserresourceid);
		 * System.out.println("Linking Child: " + childuserresourceid +
		 * " to Parent: " + parentresourceid); System.out.println(ret); }
		 * 
		 * }
		 */

		String ret = "No response";
		ret = UserManagementService.linkUser(parent, new Integer(profileid),
				initiatinguserresourceid, childuserresourceid);
		System.out.println("Linking Child: " + childuserresourceid
				+ " to Parent: " + parent);
		System.out.println(ret);
	}

	public static void unlinkuser() throws RemoteException {
		// UserManagementService linkuser = new UserManagementService();

		// 166735
		// 0000000000001
		// 0909
		// matsdealerone//100100
		String parent = "teasymobile";
		int profileid = 8;
		String initiatinguserresourceid = "admin";
		String childuserresourceid = "oniru";

		parent = "166735";
		profileid = 8;
		initiatinguserresourceid = "backoffice";
		childuserresourceid = "oniru";

		String[] cred = { "readycash", "0000000000001", "fets", "00000012",
				"fortis", "000001234", "Livepwndz", "001", "Livepwissndz",
				"00173737", "Livepwndzzz", "001878787", "paulmugo", "0093939",
				"teasymobile", "009878", "90oooio", "0909", "olumide",
				"092302934", "onir", "0982273", "oniru", "09827273", "dare",
				"09827273/09923823", "matsdealerone", "100100", "matsagentone",
				"100101", "matsadminone", "100102", "systems", "123453",
				"pocketmoni", "123456", "166735", "166735", "Livep", "2111",
				"deejah", "6890", "kenza", "89989889", "comfy", "98989889",
				"username", "98989898989", "kdkkdkd", "9d989d8989", "dealer2",
				"dealer01", "jdjjd", "kdkjkdk", "ldkkdkldkld", "lkkl" };

		/*
		 * for (int i = 0; i < cred.length; i++) {
		 * 
		 * for (int j = 0; j < cred.length; j++) {
		 * 
		 * childuserresourceid = cred[i]; String parentresourceid = cred[j];
		 * 
		 * String ret = "No response"; ret =
		 * linkuser.unlinkUser(parentresourceid, profileid,
		 * initiatinguserresourceid, childuserresourceid);
		 * System.out.println("Linking Child: " + childuserresourceid +
		 * " to Parent: " + parentresourceid); System.out.println(ret); }
		 * 
		 * }
		 */

		String ret = "No response";
		ret = UserManagementService.unlinkUser(parent, new Integer(profileid),
				initiatinguserresourceid, childuserresourceid);
		System.out.println("UNLinking Child: " + childuserresourceid
				+ " from Parent: " + parent);
		System.out.println(ret);

	}

	public static void setdefault() throws RemoteException {
		// UserManagementService setdefault = new UserManagementService();

		String parent = "166735";
		// String profileid = "8";
		String initiatinguserresourceid = "backoffice";
		String childuserresourceid = "oniru";

		String ret = UserManagementService.setDefaultAccount(
				initiatinguserresourceid, childuserresourceid, parent);

		System.out.println(ret);
	}

	public static void sql() {

		StringBuilder trxnsqld = new StringBuilder();

		trxnsqld.append(" SELECT txn.transactionid AS 'TransactionID', txn.lastupdate AS 'Timestamps','N/A' as 'Amount',txn.userresourceid as  ");
		trxnsqld.append(" 'Sender','N/A' as 'Reciever','N/A' as 'Partner', txnt.name AS 'Transaction Type', txnst.transactionstatusname AS 'Status' ");
		trxnsqld.append(" FROM transactions txn, transactionstatus txnst, transactiontypes txnt WHERE txn.transactionid = '"
				+ "100" + "' ");
		trxnsqld.append("  and txnst.transactionstatusid = txn.transactionstatusid ");
		trxnsqld.append(" AND txnt.transactiontypeid = txn.transactiontypeid and txn.transactionid not in (select txn1.transactionid from  ");
		trxnsqld.append(" accounttransactions txn1 group by txn1.transactionid) ");

		trxnsqld.append(" UNION ");

		trxnsqld.append("  SELECT txn.transactionid AS 'Transaction ID', txn.lastupdate AS 'Timestamps',(actxn.amount * -1) as 'Amount', ");
		trxnsqld.append(" txn.userresourceid as 'Sender',acth.username as 'Reciever', 'N/A' as 'Partner', txnt.name AS 'Transaction Type',  ");
		trxnsqld.append(" txnst.transactionstatusname AS 'Status' FROM transactions txn,  ");
		trxnsqld.append("  transactionstatus txnst, transactiontypes txnt,accounttransactions actxn join accountholders acth on acth.accountholderid = actxn.userresourceid  ");
		trxnsqld.append("  WHERE txn.transactionid = '" + "100"
				+ "' and txnst.transactionstatusid  ");
		trxnsqld.append("  = txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid and  ");
		trxnsqld.append("  actxn.transactionid = txn.transactionid and txnt.name = 'DEPOSIT' AND txn.transactionid not in (SELECT txn.transactionid FROM transactions txn,  ");
		trxnsqld.append("  transactionstatus txnst, transactiontypes txnt,accounttransactions actxn join accountholders acth on acth.accountholderid = actxn.userresourceid  ");
		trxnsqld.append("  WHERE txnst.transactionstatusid = txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid and  ");
		trxnsqld.append("  acth.profileid <> 14 and actxn.transactionid = txn.transactionid and txnt.name = 'DEPOSIT' group by actxn.transactionid) group by actxn.transactionid ");

		trxnsqld.append("  UNION ");

		trxnsqld.append("  SELECT txn.transactionid AS 'Transaction ID', txn.lastupdate AS 'Timestamps',actxn.amount as 'Amount', ");
		trxnsqld.append("  txn.userresourceid as 'Sender',acth.username as 'Reciever','N/A' as 'Partner', txnt.name AS 'Transaction Type',  ");
		trxnsqld.append(" txnst.transactionstatusname AS 'Status' FROM transactions txn,  ");
		trxnsqld.append(" transactionstatus txnst, transactiontypes txnt,accounttransactions actxn join accountholders acth on acth.accountholderid = actxn.userresourceid  ");
		trxnsqld.append("  WHERE txn.transactionid = '" + "100"
				+ "' and txnst.transactionstatusid  ");
		trxnsqld.append("  = txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid and  ");
		trxnsqld.append("   acth.profileid <> 14 and actxn.transactionid = txn.transactionid and txnt.name = 'DEPOSIT' group by actxn.transactionid ");

		trxnsqld.append(" UNION ");

		trxnsqld.append(" SELECT txn.transactionid AS 'TransactionID', txn.lastupdate AS 'Timestamps',(actxn.amount * -1) as 'Amount', ");
		trxnsqld.append(" txnvo.toreceivinguserresource as 'Sender', extpay.refrence1 as 'Reciever',extpay.resourceid as 'Partner', txnt.name AS 'Transaction Type',  ");
		trxnsqld.append(" txnst.transactionstatusname AS 'Status' FROM transactions txn,transactionvalueoperations txnvo, ");
		trxnsqld.append("  transactionstatus txnst, transactiontypes txnt,accounttransactions actxn,externalpaymentreference extpay WHERE  ");
		trxnsqld.append("  txn.transactionid = '" + "100"
				+ "' and txnst.transactionstatusid = ");
		trxnsqld.append(" txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid and  ");
		trxnsqld.append(" actxn.amount < 0 and actxn.transactionid = txn.transactionid and extpay.transactionid = txn.transactionid and txnvo.transactionid = txn.transactionid group by  ");
		trxnsqld.append("  actxn.transactionid ");

		trxnsqld.append("  UNION ");

		trxnsqld.append(" (SELECT txn.transactionid AS 'TransactionID', txn.lastupdate AS 'Timestamps',(actxn.amount * -1) as 'Amount', ");
		trxnsqld.append(" 'N/A' as 'Sender','N/A' as 'Reciever',txn.userresourceid as 'Partner', txnt.name AS 'Transaction Type',  ");
		trxnsqld.append(" txnst.transactionstatusname AS 'Status' FROM transactions txn, ");
		trxnsqld.append(" transactionstatus txnst, transactiontypes txnt,accounttransactions actxn WHERE txn.transactionid = '"
				+ "100" + "' and ");
		trxnsqld.append("  txnst.transactionstatusid = txn.transactionstatusid AND txnt.transactiontypeid = txn.transactiontypeid and ");
		trxnsqld.append("  actxn.amount < 0 and actxn.transactionid = txn.transactionid and txnt.name <> 'DEPOSIT' and txn.transactionid ");
		trxnsqld.append(" not in (select extpay.transactionid from ");
		trxnsqld.append(" externalpaymentreference extpay group by extpay.transactionid) group by txn.transactionid) ");
		trxnsqld.append("  ORDER BY TransactionID,Timestamps;");
		System.out.println(trxnsqld.toString());
	}

	public static void passreset() throws Exception {
		System.out.println(UserManagementService.passwordReset("username",
				"12345"));
	}

	public static void unlockAccount() throws AxisFault {
		System.out.println(UserManagementService.unlockUserAccount("admin",
				"matsng"));
	}

	public static void lockUserAccountByAdmin() throws AxisFault {
		System.out.println(UserManagementService.lockUserAccountByAdmin(
				"admin", "matsng"));
	}

	public static void setProfilepermission() throws Exception {
		String[] action = { "/deposit", "/activationrequest", "/passwordReset" };
		System.out.println(UserManagementService.setProfilePermission(
				"MATS_ADMIN_USER_PROFILE", 1, action));
	}

	public static void removeProfilePermission() throws Exception {
		String[] action = { "/deposit", "/activationrequest", "/passwordReset" };
		System.out.println(UserManagementService.removeProfilePermission(
				"MATS_ADMIN_USER_PROFILE", 1, action));
	}

	public static void addprofile() throws Exception {

		System.out.println(UserManagementService.addProfile(
				"MATS_CUSTOMER_CARE_USER_PROFILE", 1));
	}

	public static void deleteprofile() throws Exception {

		System.out
				.println(UserManagementService
						.removeProfile(
								"TOTALEntered role name is not conforming to policy.v_USER",
								41));
	}

	public static void renameprofile() throws Exception {

		System.out.println(UserManagementService.editProfile("TOTAL_USER",
				"TOTAL_USERS", 42));
	}

	public static void changepassword() throws Exception {

		System.out.println(UserManagementService.changepassword("username",
				"12345", "modupe123"));
	}

	public static void getnonactive() throws Exception {

		UserManagementService.getnonactiveprofilepermission(1);
	}

	public static void getactive() throws Exception {

		UserManagementService.getactiveprofilepermission(2);
	}
}
