package com.swifta.mats.web.transactions;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class Adjustment {

	public FormLayout AddAdjustmentPanel() {
		FormLayout adjustPan = new FormLayout();
		Label lab = new Label("Adjustment");
		Button adjust = new Button("Adjust");
		TextField text1 = new TextField("Originating Resource ID");
		TextArea mess = new TextArea("Message");
		TextField text2 = new TextField("Resource Account");
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

		return adjustPan;

	}

}
