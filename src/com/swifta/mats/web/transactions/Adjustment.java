package com.swifta.mats.web.transactions;

import java.rmi.RemoteException;
import java.util.Date;

import com.swifta.mats.web.utils.TransactionsService;
import com.swifta.mats.web.utils.UserManagementService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class Adjustment {

	public FormLayout AddAdjustmentPanel() {
		final FormLayout adjustPan = new FormLayout();
		Label lab = new Label("Adjustment");
		Button adjust = new Button("Adjust");
		Button Test = new Button("Register");
		TextField text1 = new TextField("Originating Resource ID");
		TextArea mess = new TextArea("Originating Resource Description");
		TextField text2 = new TextField("Destination Resource ID");
		TextField text3 = new TextField("Amount");
		TextField text4 = new TextField("Destination Resource ID");
		TextField text5 = new TextField("Account");
		ComboBox type = new ComboBox("Type");
		type.addItem("ADJUST");
		type.addItem("ADJUST_FEE");
		type.addItem("ADJUST_COMMISSION");
		type.setNullSelectionAllowed(false);
		type.setTextInputAllowed(false);
		type.setInputPrompt("Select");

		adjustPan.setMargin(true);
		adjustPan.addComponent(lab);
		adjustPan.addComponent(text1);
		adjustPan.addComponent(text2);
		adjustPan.addComponent(type);
		adjustPan.addComponent(text3);
		adjustPan.addComponent(text4);
		adjustPan.addComponent(text5);
		adjustPan.addComponent(mess);
		adjustPan.addComponent(adjust);
		adjustPan.addComponent(Test);

		adjust.addClickListener(new Button.ClickListener() {

			/*
			 * This cancel button is bulk upload
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				new TransactionsService();

				// TODO Auto-generated method stub

			}

		});

		Test.addClickListener(new Button.ClickListener() {

			/*
			 * This cancel button is bulk upload
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
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
				String idType = "EMID";
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
							bankdomainNameid, clearingNumber, currencyid,
							email, msisdn, profileid, securityQuest,
							securityAns, termscondition, username, countryid,
							dateofBirth, employer, firstname, gender,
							languageid, lastname, Lgaid, middlename,
							occupation, prefix, stateid, suffix, city,
							postalcode, streetAddress, province, Expirydate,
							idNumber, idType, Issuedate, Issue, PrimaryEmail,
							PrimaryMobilenumber, PrimaryPhonenumber,
							SecondaryEmail, SecondaryMobilenumber,
							SecondaryPhonenumber);
					Label rep;
					rep = new Label(ret);
					adjustPan.addComponent(rep, 0);
					Notification.show(ret);

				} catch (RemoteException e) { // TODO
												// Auto-generated
												// catch //
												// // //
												// block
					e.printStackTrace();
				}
			}

			// TODO Auto-generated method stub

		});

		return adjustPan;

	}
}
