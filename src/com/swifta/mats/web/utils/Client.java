package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.Date;

import org.apache.axis2.AxisFault;

public class Client {

	public static void main(String args[]) throws Exception {

		// String result = Client.passreset();

		// Client.passreset();

		// Client.unlockAccount();

		// Client.authenticate();
		// Client.authenticate();
		// Client.activate();
		Client.linkuser();

		// System.out.println(result);

		// Client.sql();

	}

	public static void register() {

		UserManagementService register = new UserManagementService();

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
			String ret = register.registerUser(bankAccount, bankCodeid,
					bankdomainNameid, clearingNumber, currencyid, email,
					msisdn, profileid, securityQuest, securityAns,
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

		UserManagementService activate = new UserManagementService();
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
			ret = activate.activateUser(bankdomainid, currency, IDnumber,
					userresourceid, SecurityAns, firstPin, confirmPin);

			System.out.println("Username: " + userresourceid + "  id: "
					+ IDnumber);

			System.out.println(ret);
			;
		}

	}

	public static void authenticate() throws AxisFault {
		LoginService loginService = new LoginService();

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
			String response = loginService.authenticateUser(userresourceid,
					"zzzzz");
			System.out.println("Loggin: " + userresourceid + " Response: "
					+ response);
			response = loginService.authenticateUser(userresourceid, "zzzzz");

			System.out.println("Loggin: " + userresourceid + " Response: "
					+ response);
			response = loginService.authenticateUser(userresourceid, "zzzzz");

			System.out.println("Loggin: " + userresourceid + " Response: "
					+ response);
			response = loginService.authenticateUser(userresourceid, "zzzzz");

			System.out.println("Loggin: " + userresourceid + " Response: "
					+ response);

			System.out.println(ret);

		}

	}

	public static void linkuser() throws RemoteException {
		UserManagementService linkuser = new UserManagementService();

		// 166735
		// 0000000000001
		// 0909
		// matsdealerone//100100
		String parent = "100100";
		String profileid = "8";
		String initiatinguserresourceid = "admin";
		String childuserresourceid = "matsagentone";

		String ret = linkuser.linkUser(parent, profileid,
				initiatinguserresourceid, childuserresourceid);

		System.out.println(ret);
	}

	public static void setdefault() throws RemoteException {
		UserManagementService setdefault = new UserManagementService();

		String parent = "166735";
		// String profileid = "8";
		String initiatinguserresourceid = "backoffice";
		String childuserresourceid = "oniru";

		String ret = setdefault.setDefaultAccount(initiatinguserresourceid,
				childuserresourceid, parent);

		System.out.println(ret);
	}

	public static void sql() {
		StringBuilder agentsql = new StringBuilder();

		agentsql.append("select ach.username, tbl1.cashbalance, tbl2.evaluebalance from accountholders ach,(select actxns.userresourceid as cashacctid,sum(actxns.closingbalance) - sum(actxns.openingbalance) as cashbalance");
		agentsql.append(" from accounttransactions actxns, transactions trx,accounts acts where actxns.transactionid = trx.transactionid and trx.transactionstatusid = 1");
		agentsql.append(" and actxns.accountresourceid = acts.accountid and acts.profileid = 12 group by actxns.userresourceid) tbl1,");
		agentsql.append("(select actxns.userresourceid as evalueacctid,sum(actxns.closingbalance) - sum(actxns.openingbalance) as evaluebalance");
		agentsql.append(" from accounttransactions actxns, transactions trx,accounts acts where actxns.transactionid = trx.transactionid and trx.transactionstatusid = 1");
		agentsql.append(" and actxns.accountresourceid = acts.accountid and acts.profileid = 5 group by actxns.userresourceid) tbl2 where ach.accountholderid = tbl1.cashacctid and tbl1.cashacctid = tbl2.evalueacctid");

		System.out.println(agentsql.toString());
	}

	public static void passreset() throws AxisFault {
		System.out.println(UserManagementService.passwordResetByAdmin("admin",
				"olumide", "1345"));
	}

	public static void unlockAccount() throws AxisFault {
		System.out.println(UserManagementService.unlockUserAccount("admin",
				"matsng"));
	}

	public static void lockUserAccountByAdmin() throws AxisFault {
		System.out.println(UserManagementService.lockUserAccountByAdmin(
				"admin", "matsng"));
	}
}
