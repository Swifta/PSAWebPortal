package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.Date;

public class Client {

	public static void main(String args[]) {

		Client.activate();
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
		String idType = "Kay";
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

	public static void activate() {

		UserManagementService activate = new UserManagementService();
		// LoginService login = new LoginService();

		// boolean give = login.authenticateUser("Adetolami", "modupe");

		// Notification.show(String.valueOf(give));

		String userresourceid = "166735";
		String bankdomainid = "0";
		String IDnumber = "166735";
		String SecurityAns = "Mrs.fd X";
		String firstPin = "modupe";
		String confirmPin = "modupe";
		String currency = "0";
		String ret = "No response";

		try {
			ret = activate.activateUser(bankdomainid, currency, IDnumber,
					userresourceid, SecurityAns, firstPin, confirmPin);

			System.out.println(ret);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
