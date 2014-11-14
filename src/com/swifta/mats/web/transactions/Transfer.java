package com.swifta.mats.web.transactions;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.utils.LoginService;
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
import com.vaadin.ui.Window;

public class Transfer {

	Window subwindow;

	public FormLayout AddTransferPanel() {
		final FormLayout adjustPan = new FormLayout();
		Label lab = new Label("Float Transfer from Dealer to Bank Account");
		Button adjust = new Button("Transfer Float to Bank Account");
		Button adjust2 = new Button("Link");
		Button adjust3 = new Button("SetParent");
		Button adjust4 = new Button("Activate");
		Button adjust5 = new Button("setDefaultAccount");
		TextField text1 = new TextField("Originating Resource ID");
		TextArea mess = new TextArea("Message");
		TextField text2 = new TextField("Resource Account");
		TextField text3 = new TextField("Amount");
		TextField text4 = new TextField("Bank Code");
		TextField text5 = new TextField("Bank Account Number");
		// ComboBox type = new ComboBox("Type");

		adjustPan.setMargin(true);
		adjustPan.addComponent(lab);
		adjustPan.addComponent(text1);
		adjustPan.addComponent(text2);
		// adjustPan.addComponent(type);
		adjustPan.addComponent(text3);
		adjustPan.addComponent(text4);
		adjustPan.addComponent(text5);
		adjustPan.addComponent(mess);
		adjustPan.addComponent(adjust);
		adjustPan.addComponent(adjust2);
		adjustPan.addComponent(adjust3);
		adjustPan.addComponent(adjust4);
		adjustPan.addComponent(adjust5);

		adjust5.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserManagementService setDefault = new UserManagementService();

				try {
					String parentresourceid = "Ore";
					String reason = "no reason for this";
					String userresourceid = "Adetola";
					String ret = setDefault.setDefaultAccount(parentresourceid,
							reason, userresourceid);

					Label rep;
					rep = new Label(ret);
					adjustPan.addComponent(rep, 0);
					Notification.show(ret);

				} catch (RemoteException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}

				// TODO Auto-generated method stub

			}
		});

		adjust4.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserManagementService activate = new UserManagementService();
				LoginService login = new LoginService();

				String give = login.authenticateUser("Adetolami", "modupe");

				Notification.show(give);

				String userresourceid = "Opeyemierg";
				String bankdomainid = "0";
				String IDnumber = "9010";
				String SecurityAns = "Mrs.fd X";
				String firstPin = "5264";
				String confirmPin = "5264";
				String currency = "0";
				String ret = "Hello";

				Label rep;
				rep = new Label(ret);
				adjustPan.addComponent(rep, 0);
				// Notification.show(ret);

				// TODO Auto-generated method stub

			}
		});

		adjust3.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserManagementService setParent = new UserManagementService();

				try {
					String parentresourceid = "53282323";
					String reason = "fhdgasu";
					String userresourceid = "hvdfyas";
					String ret = setParent.setParent(parentresourceid, reason,
							userresourceid);

					Label rep;
					rep = new Label(ret);
					adjustPan.addComponent(rep, 0);
					Notification.show(ret);

				} catch (RemoteException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub

			}
		});

		adjust2.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserManagementService linker = new UserManagementService();

				try {
					String parentresourceid = "Ore";
					String profileid = "419";
					String reason = "no reason";
					String userresourceid = "Adetola";
					String ret = linker.linkUser(parentresourceid, profileid,
							reason, userresourceid);

					Label rep;
					rep = new Label(ret);
					adjustPan.addComponent(rep, 0);
					Notification.show(ret);

				} catch (RemoteException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub

			}
		});

		adjust.addClickListener(new Button.ClickListener() {

			/**
			 * This cancel button is bulk upload
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				TransactionsService flow = new TransactionsService();

				try {
					String resp = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getOrginatingpartnerbalanceafter();
					String resp2 = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getOrginatingpartnerfee();
					String resp3 = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getTransactionid();

					Label rep = new Label(resp + resp2 + resp3);
					adjustPan.addComponent(rep);
				} catch (AxisFault e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show("Can't connect to Server ");
					adjustPan.addComponent(
							new Label("Can't connect to Server"), 0);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show("Server is currently not available");
					adjustPan.addComponent(new Label(
							"Server is currently not available"), 0);
				}

				// TODO Auto-generated method stub

			}

		});

		return adjustPan;

	}

	public FormLayout AddTransferPanel2() {
		final FormLayout adjustPan = new FormLayout();
		Label lab = new Label("Float Transfer from Agent to Dealer");
		Button adjust = new Button("Transfer Float");
		TextField text1 = new TextField("Resource UserID");
		TextArea mess = new TextArea("Message");
		TextField text2 = new TextField("Resource AccountID");
		TextField text3 = new TextField("Amount");
		TextField text4 = new TextField("Destination AccountID");

		// TextField text5 = new TextField("Bank Account Number");
		ComboBox type = new ComboBox("Type");

		adjustPan.setMargin(true);
		adjustPan.addComponent(lab);
		adjustPan.addComponent(text1);
		adjustPan.addComponent(text2);
		adjustPan.addComponent(type);
		adjustPan.addComponent(text3);
		adjustPan.addComponent(text4);
		// adjustPan.addComponent(text5);
		adjustPan.addComponent(mess);
		adjustPan.addComponent(adjust);
		// adjustPan.addComponent(activate);

		adjust.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * This cancel button is bulk upload
			 * 
			 */

			@Override
			public void buttonClick(ClickEvent event) {
				TransactionsService flow = new TransactionsService();

				try {
					String resp = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getOrginatingpartnerbalanceafter();
					String resp2 = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getOrginatingpartnerfee();
					String resp3 = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getTransactionid();

					Label rep = new Label(resp + resp2 + resp3);
					adjustPan.addComponent(rep);
				} catch (AxisFault e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show("Can't connect to Server1 ");
					adjustPan.addComponent(
							new Label("Can't connect to Server"), 0);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show("Server is currently not available");
					adjustPan.addComponent(new Label(
							"Server is currently not available"), 0);
				}

				// TODO Auto-generated method stub

			}

		});

		return adjustPan;

	}

}
