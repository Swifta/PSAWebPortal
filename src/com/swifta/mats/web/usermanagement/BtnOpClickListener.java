package com.swifta.mats.web.usermanagement;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Button.ClickEvent;



public class BtnOpClickListener implements Button.ClickListener{
	
	private static final long serialVersionUID = -8361209098934409391L;
	private Object objSlaveF;
	private Button btnSlave;
	
	
		public BtnOpClickListener(Object objSlaveF, Button btnSlave){
			this.objSlaveF= objSlaveF;
			this.btnSlave = btnSlave;
				
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
					Button btn = event.getButton();
					String strBtnID = btn.getId();
					String[] arrData = strBtnID.split("_");
					String strTbName = arrData[0];
					String strUID = arrData[1];
					String strAction = arrData[2];
					
					
					String strNewStatus = null;
					String strOldStatus;
					String strNewAction;
					if(strAction.equals("Activate")||strAction.equals("Deactivate")){
						OptionGroup opt = ((OptionGroup)objSlaveF);
						if(strAction.equals("Activate")){
							strNewStatus = "Active";
							strOldStatus = "Deactive";
							strNewAction = "Activate";
							
							opt.setEnabled(true);
							opt.setReadOnly(false);
							
							opt.setValue(false);
							opt.removeItem(strOldStatus );
							opt.addItem(strNewStatus);
							opt.select(strNewStatus);
							
							opt.setEnabled(false);
							opt.setReadOnly(true);
							btn.setCaption(strNewAction);
							btn.setId(strTbName+"_"+strUID+"_"+strNewAction);
							
								if(btnSlave != null)
									btnSlave.setEnabled(true);
						}else if(strAction.equals("Deactivate")){
							strNewStatus = "Deactive";
							strOldStatus = "Active";
							strNewAction = "Activate";
							
							opt.setEnabled(true);
							opt.setReadOnly(false);
							
							opt.setValue(false);
							opt.removeItem(strOldStatus);
							opt.addItem(strNewStatus);
							opt.select(strNewStatus);
							
							opt.setEnabled(false);
							opt.setReadOnly(true);
							btn.setCaption(strNewAction);
							btn.setId(strTbName+"_"+strUID+"_"+strNewAction);
							if(btnSlave != null)
								btnSlave.setEnabled(false);
						}
							
							 //TODO Commit changes to the server.
							Notification.show("Account status of user of ID "+strUID+" has been changed to "+strNewStatus.toUpperCase());

					}
								
										
							
					
					
				
				}
	
}
