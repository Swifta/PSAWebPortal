package com.swifta.mats.web.settings;

import java.util.ArrayList;

import com.swifta.mats.web.usermanagement.UserDetailsModule;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.event.ListenerMethod.MethodException;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
//import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class FeesAndCommModule {
	UserDetailsModule udm;
	HorizontalLayout udc;
	private ArrayList<VerticalLayout> cArrLItemContent;
	private ArrayList<FieldGroup> arrLRangeFG;
	private ArrayList<FieldGroup> arrLMatFG;
	private boolean isTiered;
	private static String lookedTab = null;
	private ComboBox comboOp;
	private ComboBox comboTxType;
	private int confCount = 0;

	public FeesAndCommModule() {
		udm = new UserDetailsModule();
		comboOp = new ComboBox("Operator");
		comboOp.addItem(1);
		comboOp.setItemCaption(1, "Teasy");
		comboOp.addItem(2);
		comboOp.setItemCaption(2, "Pocket Money");
		comboOp.select(1);
		comboTxType = new ComboBox("Transaction Type");
		comboTxType.addItem(1);
		comboTxType.setItemCaption(1, "Cash-in");
		comboTxType.addItem(2);
		comboTxType.setItemCaption(2, "Cash-out");
		comboTxType.addItem(3);
		comboTxType.setItemCaption(3, "Airtime topup");
		comboTxType.addItem(4);
		comboTxType.setItemCaption(4, "Money Transfer");
		comboTxType.addItem(5);
		comboTxType.setItemCaption(5, "Bill Payment");
		comboTxType.select(1);

	}

	private void addMatrix() {
		VerticalLayout cMat = cArrLItemContent.get(2);
		VerticalLayout cAmt = cArrLItemContent.get(3);
		final ComboBox comboMat = new ComboBox();
		comboMat.addItem(1);
		comboMat.setItemCaption(1, "%ge");
		comboMat.addItem(2);
		comboMat.setItemCaption(2, "Fixed");
		comboMat.select(2);

		final TextField tfAmt = new TextField();
		cMat.addComponent(comboMat);
		cAmt.addComponent(tfAmt);

		Property<Integer> pMat = new ObjectProperty<>(0);
		Property<String> pAmt = new ObjectProperty<>("");

		Item row = new PropertysetItem();
		row.addItemProperty("Mat", pMat);
		row.addItemProperty("Amt", pAmt);
		FieldGroup fg = new FieldGroup(row);
		fg.bind(comboMat, "Mat");
		fg.bind(tfAmt, "Amt");
		arrLMatFG.add(fg);

		comboMat.addValidator(new MatrixHouseKeeper(comboMat, tfAmt));
		tfAmt.addValidator(new MatrixHouseKeeper(comboMat, tfAmt));
		comboMat.setValidationVisible(true);
		tfAmt.setValidationVisible(true);
		comboMat.setImmediate(true);
		tfAmt.setImmediate(true);

		fg.addCommitHandler(new CommitHandler() {
			private static final long serialVersionUID = -4960371331680442909L;

			@Override
			public void preCommit(CommitEvent commitEvent)
					throws CommitException {
				tfAmt.setRequired(false);
				comboMat.setRequired(false);
				if ((int) comboMat.getValue() == 0) {
					comboMat.setRequired(true);
					Notification.show("Fields marked with (*) are required.");
					throw new CommitException("Field required.");
				} else if (tfAmt.getValue().trim().isEmpty()) {
					tfAmt.setRequired(true);
					Notification.show("Fields marked with (*) are required.");
					throw new CommitException("Field required.");
				} else {
					tfAmt.setRequired(false);
					comboMat.setRequired(false);
					return;
				}

			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {

			}

		});

		comboMat.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -47504574989952155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					comboMat.setComponentError(null);
					tfAmt.setComponentError(null);
					comboMat.validate();
				} catch (MethodException e) {

				}
			}

		});

		tfAmt.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -4750457473552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					tfAmt.setComponentError(null);
					comboMat.setComponentError(null);
					tfAmt.validate();

				} catch (MethodException e) {

				}

			}

		});

	}

	private void addRange() {
		VerticalLayout cMin = cArrLItemContent.get(0);
		VerticalLayout cMax = cArrLItemContent.get(1);
		final TextField tfMin = new TextField();
		final TextField tfMax = new TextField();
		cMin.addComponent(tfMin);
		cMax.addComponent(tfMax);
		// tfMin.setRequired(true);
		// tfMax.setRequired(true);

		Property<String> pMin = new ObjectProperty<>("");
		Property<String> pMax = new ObjectProperty<>("");

		Item row = new PropertysetItem();
		row.addItemProperty("Min", pMin);
		row.addItemProperty("Max", pMax);
		FieldGroup fg = new FieldGroup(row);
		fg.bind(tfMin, "Min");
		fg.bind(tfMax, "Max");
		arrLRangeFG.add(fg);
		int index = arrLRangeFG.size() - 1;
		tfMin.addValidator(new RangeHouseKeeper(tfMin, tfMax, index));
		tfMax.addValidator(new RangeHouseKeeper(tfMin, tfMax, index));
		tfMin.setValidationVisible(true);
		tfMax.setValidationVisible(true);
		tfMin.setImmediate(true);
		tfMax.setImmediate(true);

		if (!isTiered) {
			tfMin.setValue("0.00");
			tfMin.setEnabled(false);
			tfMax.setValue("0.00");
			tfMax.setEnabled(false);
		}

		fg.addCommitHandler(new CommitHandler() {
			private static final long serialVersionUID = -4960371331680442909L;

			@Override
			public void preCommit(CommitEvent commitEvent)
					throws CommitException {
				tfMin.setRequired(false);
				tfMax.setRequired(false);
				if (tfMin.getValue().trim().isEmpty()) {
					tfMin.setRequired(true);
					Notification.show("Fields marked with (*) are required.");
					throw new CommitException("Field required.");
				}
				if (tfMax.getValue().trim().isEmpty()) {
					tfMax.setRequired(true);
					Notification.show("Fields marked with (*) are required.");
					throw new CommitException("Field required.");
				} else {
					tfMin.setRequired(false);
					tfMax.setRequired(false);
					return;
				}

			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {

			}

		});

		tfMin.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -47504574989952155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					tfMin.setComponentError(null);
					tfMax.setComponentError(null);
					tfMin.validate();
				} catch (MethodException e) {

				}
			}

		});

		tfMax.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -4750457473552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					tfMax.setComponentError(null);
					tfMin.setComponentError(null);
					tfMax.validate();
				} catch (MethodException e) {

				}

			}

		});

	}

	private class RangeHouseKeeper implements Validator {

		private static final long serialVersionUID = 8429528148551522733L;
		private TextField tfMin;
		private TextField tfMax;
		private Float vMin;
		private Float vMax;
		private int fgIndex;

		RangeHouseKeeper(TextField tfMin, TextField tfMax, int fgIndex) {
			this.tfMin = tfMin;
			this.tfMax = tfMax;
			this.fgIndex = fgIndex;

		}

		@Override
		public void validate(Object v) throws InvalidValueException,
				NumberFormatException {

			if (v.toString().trim().isEmpty())
				return;

			try {
				Float.valueOf(v.toString());
			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			String sMin = tfMin.getValue().trim();
			String sMax = tfMax.getValue().trim();

			if (sMin.isEmpty() || sMax.isEmpty())
				return;

			try {
				vMin = Float.parseFloat(sMin);

			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			try {
				vMax = Float.parseFloat(sMax);

			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			if (vMin > vMax) {
				throw new InvalidValueException("Invalid range.");

			}

			if (arrLRangeFG.size() > fgIndex + 1) {
				FieldGroup nxtFG = arrLRangeFG.get(fgIndex + 1);
				TextField nxtMin = (TextField) nxtFG.getField("Min");
				Double nxt = Double.valueOf(tfMax.getValue()) + 1;
				nxtMin.setValue(nxt.toString());
				nxtMin.setEnabled(false);

				return;

			}
		}
	}

	private class MatrixHouseKeeper implements Validator {

		private static final long serialVersionUID = 8429528148551522733L;
		private ComboBox comboMat;
		private TextField tfAmt;
		private Integer mat;
		private Float vAmt;

		MatrixHouseKeeper(ComboBox comboMat, TextField tfAmt) {
			this.comboMat = comboMat;
			this.tfAmt = tfAmt;
		}

		@Override
		public void validate(Object v) throws InvalidValueException,
				NumberFormatException {

			if (v.toString().trim().isEmpty())
				return;

			if (tfAmt.getValue().trim().isEmpty())
				return;

			try {
				Float.valueOf(tfAmt.getValue());
			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			mat = (Integer) comboMat.getValue();
			String sAmt = tfAmt.getValue().trim();

			try {
				vAmt = Float.parseFloat(sAmt);

			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			if (mat == 1 && vAmt > 100) {
				throw new InvalidValueException("Invalid Percentage.");
			}

		}
	}

	private void add() {
		addRange();
		addMatrix();
	}

	private void remove() {
		removeRange();
		removeMatrix();
	}

	private void removeAll() {
		removeAllRange();
		removeAllMatrix();
	}

	private void removeAllRange() {
		if (removeRange() == -1) {
			return;
		}
		removeAllRange();
	}

	private void removeAllMatrix() {
		if (removeMatrix() == -1) {
			return;
		}
		removeAllMatrix();
	}

	private int removeRange() {
		if (arrLRangeFG.size() < 2)
			return -1;

		arrLRangeFG.remove(arrLRangeFG.size() - 1);
		VerticalLayout cMin = cArrLItemContent.get(0);
		VerticalLayout cMax = cArrLItemContent.get(1);
		cMin.removeComponent(cMin.getComponent(cMin.getComponentCount() - 1));
		cMax.removeComponent(cMax.getComponent(cMax.getComponentCount() - 1));
		return 1;

	}

	private int removeMatrix() {
		if (arrLMatFG.size() < 2)
			return -1;

		arrLMatFG.remove(arrLMatFG.size() - 1);
		VerticalLayout cMat = cArrLItemContent.get(2);
		VerticalLayout cAmt = cArrLItemContent.get(3);
		cMat.removeComponent(cMat.getComponent(cMat.getComponentCount() - 1));
		cAmt.removeComponent(cAmt.getComponent(cAmt.getComponentCount() - 1));
		return 1;

	}

	public HorizontalLayout getFeesContainer(final String type) {

		cArrLItemContent = new ArrayList<>(4);
		arrLRangeFG = new ArrayList<>();
		arrLMatFG = new ArrayList<>();
		isTiered = true;

		HorizontalLayout cManage = new HorizontalLayout();
		cManage.setWidthUndefined();
		cManage.setWidthUndefined();

		VerticalLayout cChoose = new VerticalLayout();
		cChoose.setSizeUndefined();
		cChoose.setStyleName("frm_add_user frm_manage_commission");

		VerticalLayout addUserHeader = new VerticalLayout();
		addUserHeader.setWidthUndefined();
		addUserHeader.setHeightUndefined();
		addUserHeader.setSpacing(true);

		cChoose.addComponent(addUserHeader);

		Label lbAddUser = new Label("Manage " + type);
		lbAddUser.setStyleName("label_add_user");
		lbAddUser.setSizeUndefined();

		cChoose.addComponent(lbAddUser);
		Label lbChoose = new Label("Please choose...");

		final ComboBox comboConditionType = new ComboBox("Condition Type");
		comboConditionType.addItem(1);
		comboConditionType.setItemCaption(1, "Amount");
		comboConditionType.addItem(2);
		comboConditionType.setItemCaption(2, "Fee");
		comboConditionType.select(1);

		final ComboBox comboModelType = new ComboBox("Model Type");
		comboModelType.addItem(1);
		comboModelType.setItemCaption(1, "Tiered");
		comboModelType.addItem(2);
		comboModelType.setItemCaption(2, "None");
		comboModelType.select(1);

		Item row = new PropertysetItem();

		Property<Integer> pconTypeID = new ObjectProperty<Integer>(1);
		Property<Integer> pmodelTypeID = new ObjectProperty<Integer>(1);
		row.addItemProperty("CONID", pconTypeID);
		row.addItemProperty("MODID", pmodelTypeID);

		final FieldGroup fg = new FieldGroup(row);
		fg.bind(comboConditionType, "CONID");
		fg.bind(comboModelType, "MODID");

		fg.addCommitHandler(new CommitHandler() {
			private static final long serialVersionUID = 6144936023943646696L;

			@Override
			public void preCommit(CommitEvent commitEvent)
					throws CommitException {
				comboOp.setRequired(false);
				comboConditionType.setRequired(false);
				comboModelType.setRequired(false);
				comboTxType.setRequired(false);
				if ((int) comboOp.getValue() == 0) {
					comboOp.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");
				} else if ((int) comboConditionType.getValue() == 0) {
					comboConditionType.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");

				} else if ((int) comboModelType.getValue() == 0) {
					comboModelType.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");

				} else if ((int) comboTxType.getValue() == 0) {
					comboTxType.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");
				} else {
					comboOp.setRequired(false);
					comboConditionType.setRequired(false);
					comboModelType.setRequired(false);
					comboTxType.setRequired(false);
					return;
				}
			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {

			}

		});

		cChoose.addComponent(lbChoose);
		cChoose.addComponent(comboOp);

		cChoose.addComponent(comboConditionType);
		cChoose.addComponent(comboModelType);
		cChoose.addComponent(comboTxType);
		cChoose.setStyleName("c_choose");

		VerticalLayout cAttr = new VerticalLayout();
		cAttr.setSpacing(true);
		cAttr.setMargin(new MarginInfo(false, false, true, false));
		cAttr.setStyleName("c_attr");

		final Label tFOp = new Label();
		tFOp.setCaption("Selected Operator: ");
		tFOp.setValue(comboOp.getItemCaption(comboOp.getValue()));
		tFOp.setStyleName("label_add_user");

		FormLayout cOp = new FormLayout();
		cOp.addComponent(tFOp);
		cAttr.addComponent(cOp);

		HorizontalLayout cAttrItem = new HorizontalLayout();
		VerticalLayout cItemContent = new VerticalLayout();
		Label lbAttr = new Label(comboTxType.getCaption());
		lbAttr.setSizeUndefined();
		cItemContent.setSizeFull();

		lbAttr.setStyleName("label_add_user attr");
		final Label lbAttrVal = new Label(
				comboTxType.getItemCaption(comboTxType.getValue()));

		HorizontalLayout cAttrVal = new HorizontalLayout();
		cAttrVal.addComponent(lbAttrVal);
		lbAttrVal.setStyleName("attr_val");
		cAttrVal.setStyleName("c_attr_val");
		cItemContent.addComponent(lbAttr);
		cItemContent.addComponent(cAttrVal);
		cItemContent.setComponentAlignment(cAttrVal, Alignment.MIDDLE_CENTER);
		cItemContent.setComponentAlignment(lbAttr, Alignment.TOP_LEFT);
		cAttrItem.addComponent(cItemContent);
		cAttr.addComponent(cAttrItem);

		// MIN

		VerticalLayout cItemContentMin = new VerticalLayout();
		cArrLItemContent.add(cItemContentMin);
		lbAttr = new Label("Min.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMin.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentMin);

		// MAX

		VerticalLayout cItemContentMax = new VerticalLayout();
		cArrLItemContent.add(cItemContentMax);
		lbAttr = new Label("Max.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMax.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentMax);

		// MATRIX
		VerticalLayout cItemContentMat = new VerticalLayout();
		cArrLItemContent.add(cItemContentMat);
		lbAttr = new Label("Matrix");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMat.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentMat);

		// AMOUNT

		VerticalLayout cItemContentAmt = new VerticalLayout();
		cArrLItemContent.add(cItemContentAmt);
		lbAttr = new Label("Amount (%/N)");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentAmt.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentAmt);
		add();
		add();

		HorizontalLayout cControls = new HorizontalLayout();
		Button btnAdd = new Button("+");

		btnAdd.setStyleName("btn_link");
		Button btnRemove = new Button("-");
		btnRemove.setStyleName("btn_link");

		Button btnSave = new Button(FontAwesome.SAVE);
		btnSave.setStyleName("btn_link");

		Button btnCancel = new Button(FontAwesome.UNDO);
		btnCancel.setStyleName("btn_link");

		cControls.addComponent(btnAdd);
		cControls.addComponent(btnRemove);
		cControls.addComponent(btnSave);
		// cControls.addComponent(btn);

		cAttr.addComponent(cAttrItem);
		cAttr.addComponent(cControls);
		cAttr.setComponentAlignment(cControls, Alignment.BOTTOM_RIGHT);
		cManage.addComponent(cChoose);
		cManage.addComponent(cAttr);

		comboTxType.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -4750457498573552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				lbAttrVal.setValue(comboTxType.getItemCaption(comboTxType
						.getValue()));
			}

		});

		comboOp.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -4750457498573552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				tFOp.setValue(comboOp.getItemCaption(comboOp.getValue()));
			}

		});

		comboModelType.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -6418325605387194047L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (Integer.valueOf(comboModelType.getValue().toString()) == 1) {

					if (arrLRangeFG.size() == 0)
						return;

					removeAll();
					FieldGroup fg = arrLRangeFG.get(0);
					TextField min = (TextField) fg.getField("Min");
					TextField max = (TextField) fg.getField("Max");
					min.setValue("");
					min.setEnabled(true);

					max.setValue("");
					max.setEnabled(true);
					isTiered = true;
					return;

				} else {
					if (arrLRangeFG.size() == 0)
						return;
					removeAll();
					FieldGroup fg = arrLRangeFG.get(0);
					TextField min = (TextField) fg.getField("Min");
					TextField max = (TextField) fg.getField("Max");
					min.setValue("0.00");
					min.setEnabled(false);

					max.setValue("0.00");
					max.setEnabled(false);
					isTiered = false;
					return;
				}

			}

		});

		btnAdd.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 8105347974986024315L;

			@Override
			public void buttonClick(ClickEvent event) {
				add();

			}
		});

		btnRemove.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3921995443687711235L;

			@Override
			public void buttonClick(ClickEvent event) {
				remove();

			}
		});

		btnSave.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -7900935606088856031L;

			@Override
			public void buttonClick(ClickEvent event) {
				/*
				 * Commit to server.
				 */
				if (isReadyToCommit(type, fg)) {
					/*
					 * Ready to commit to server.
					 */

					Notification.show("Goddamn ready to commit.");
					return;
				} else {
					return;
				}

			}
		});

		return cManage;
	}

	private boolean isReadyToCommit(String type, FieldGroup dfg) {
		try {
			dfg.commit();
		} catch (CommitException e1) {
			e1.printStackTrace();
			return false;
		}

		for (FieldGroup fg : arrLRangeFG) {
			try {
				fg.commit();
			} catch (CommitException e) {
				e.printStackTrace();
				return false;
			}
		}
		for (FieldGroup fg : arrLMatFG) {
			try {
				fg.commit();
			} catch (CommitException e) {
				e.printStackTrace();
				return false;
			}
		}

		/*
		 * Ready to commit valid values to the server.
		 */
		lookedTab = type;
		confCount++;
		if (type.equals("fees") && confCount < 2) {
			UI.getCurrent()
					.getSession()
					.setAttribute(
							WorkSpaceManageFeesAndComm.SESSION_WSMP_CUR_ACTION,
							"Commission");
			Settings.wmfac.wsmpModifier();
			ManageFeesAndCommModule.btnFees.setStyleName("btn_tab_like");
			ManageFeesAndCommModule.btnFees.setEnabled(true);
			ManageFeesAndCommModule.btnComm
					.setStyleName("btn_tab_like btn_tab_like_active");
			ManageFeesAndCommModule.btnComm.setEnabled(false);
		} else if (type.equals("Commission") && confCount < 2) {

			UI.getCurrent()
					.getSession()
					.setAttribute(
							WorkSpaceManageFeesAndComm.SESSION_WSMP_CUR_ACTION,
							"fees");
			Settings.wmfac.wsmpModifier();

			ManageFeesAndCommModule.btnFees
					.setStyleName("btn_tab_like btn_tab_like_active");
			ManageFeesAndCommModule.btnFees.setEnabled(false);
			ManageFeesAndCommModule.btnComm.setStyleName("btn_tab_like");
			ManageFeesAndCommModule.btnComm.setEnabled(true);

		} else {
			return true;
		}
		return false;
	}

	public HorizontalLayout getFeesForm(String strTbName, String strID,
			boolean a, boolean b) {
		return null;
	}

	public void apmModifier(String strTbName, HorizontalLayout cContent) {
		if (lookedTab != null) {
			if (lookedTab.equals(strTbName)) {
				comboOp.setEnabled(true);
				comboTxType.setEnabled(true);
			} else {
				comboOp.setEnabled(false);
				comboTxType.setEnabled(false);
			}
		}
		cContent.removeAllComponents();
		cContent.addComponent(getFeesContainer(strTbName));

	}

}
