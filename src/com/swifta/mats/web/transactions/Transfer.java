package com.swifta.mats.web.transactions;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class Transfer {
	public FormLayout AddTransferPanel() {
		FormLayout adjustPan = new FormLayout();
		Label lab = new Label("Float Transfer from Dealer to Bank Account");
		Button adjust = new Button("Transfer Float to Bank Account");
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

		return adjustPan;

	}

	public FormLayout AddTransferPanel2() {
		FormLayout adjustPan = new FormLayout();
		Label lab = new Label("Float Transfer from Super Agent to Dealer");
		Button adjust = new Button("Transfer Float");
		TextField text1 = new TextField("Resource UserID");
		TextArea mess = new TextArea("Message");
		TextField text2 = new TextField("Resource AccountID");
		TextField text3 = new TextField("Amount");
		TextField text4 = new TextField("Destination AccountID");
		// TextField text5 = new TextField("Bank Account Number");
		// ComboBox type = new ComboBox("Type");

		adjustPan.setMargin(true);
		adjustPan.addComponent(lab);
		adjustPan.addComponent(text1);
		adjustPan.addComponent(text2);
		// adjustPan.addComponent(type);
		adjustPan.addComponent(text3);
		adjustPan.addComponent(text4);
		// adjustPan.addComponent(text5);
		adjustPan.addComponent(mess);
		adjustPan.addComponent(adjust);

		return adjustPan;

	}

}
