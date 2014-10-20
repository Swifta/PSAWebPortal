package com.swifta.mats.web.usermanagement;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.ui.UI;

public class BackEndEmulator {
	
	public BackEndEmulator( ){
		
	}
	
	public Map<String, String[]>getUserPersonalInfo(String strTbName, String strUID){
		
		Map<String, String[]> mappedAllData = new HashMap<String, String[]>();
		
		String strTbNamePersonal = "personal";//, "Account", "Authentication"};
		String strTbNameAccount = "account";
		String strTbNameAuth = "auth";
		String strTbNameActLog = "activity_log";
		String strTbNameAccChangeLog = "account_change_log";
		String strUserType = (String) UI.getCurrent().getSession().getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE);
		
		
		String strUser001 = "001";
		
		if(strTbName.equals(strTbNamePersonal) && strUID.equals(strUser001)){
			
			String arrTbName[] = {"personal"};
			String arrUID[] = {"001"};
			
			String[] arrTfCaptions =  new String[]{"UID", "First Name", "Last Name", "Full Name", "Email", "Address", "Phone no."};
			String[] arrTfVals =  new String[]{"001", "Amama", "Yoweri", "Amama Yoweri", "ouzodinma@swifta.com", "6b Crown Court Estate Oniru V1 Lagos", "08089070095"};
			
			String[] arrOptCaptions = new String[]{"Sex"};
			String[] arrOptSelVals = new String[]{"Male"};
			
			String[] arrComboCaptions =  new String[]{"Type"};
			String[] arrComboSelVals =  new String[]{"Transaction"};
			
			String[] arrDfCaptions =  new String[]{"DOC"};
			String[] arrDfVals =  new String[]{"5/10/2014"};
			
			String[][] allData = new String[][] {arrTbName,arrUID, arrTfCaptions, arrTfVals, 
					arrOptCaptions, arrOptSelVals, arrComboCaptions, arrComboSelVals, arrDfCaptions,  arrDfVals } ;
			
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
			
		}else if(strTbName.equals(strTbNameAccount) && strUID.equals(strUser001)){
			
			
			String arrTbName[] = {"account"};
			String arrUID[] = {"001"};
			
			String[] arrTfCaptions =  new String[]{"Account ID", "Account Name"};
			String[] arrTfVals =  new String[]{"001", "Amama"};
			
			String[] arrOptCaptions = new String[]{"Status"};
			String[] arrOptSelVals = new String[]{"Active"};
			
			String[] arrComboCaptions =  new String[]{"Type"};
			String[] arrComboSelVals =  new String[]{"Transaction"};
			
			String[] arrDfCaptions =  new String[]{"DOC"};
			String[] arrDfVals =  new String[]{"5/10/2014"};
			
			String[][] allData = new String[][] {arrTbName,arrUID, arrTfCaptions, arrTfVals, 
					arrOptCaptions, arrOptSelVals, arrComboCaptions, arrComboSelVals, arrDfCaptions,  arrDfVals } ;
			
				
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
			
		}else if(strTbName.equals(strTbNameAuth) && strUID.equals(strUser001)){
			
			if(strUserType.equals("agent")){
				String arrTbName[] = {"auth"};
				String arrUID[] = {"001"};
				
				String[] arrTfCaptions =  new String[]{"Agent ID","Agent Name", "MSISDN", "PIN Status"};
				String[] arrTfVals =  new String[]{arrUID[0], "Seno and Co.", "+256707181923", "Set."};
				
				String[] arrOptCaptions = null;
				String[] arrOptSelVals = null;
				
				String[] arrComboCaptions =  null;
				String[] arrComboSelVals =  null;
				
				String[] arrDfCaptions =  null;
				String[] arrDfVals =  null;
				
				String[][] allData = new String[][] {arrTbName,arrUID, arrTfCaptions, arrTfVals, 
						arrOptCaptions, arrOptSelVals, arrComboCaptions, arrComboSelVals, arrDfCaptions,  arrDfVals } ;
				
					
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
			}else{
				String arrTbName[] = {"auth"};
				String arrUID[] = {"001"};
				
				String[] arrTfCaptions =  new String[]{"Username","Password", "Acccount Status", "Login status", "Last login"};
				String[] arrTfVals =  new String[]{"Sevo", "Default", "Normal", "Logged in.", "2 min. ago." };
				
				String[] arrOptCaptions = null;
				String[] arrOptSelVals = null;
				
				String[] arrComboCaptions =  null;
				String[] arrComboSelVals =  null;
				
				String[] arrDfCaptions =  null;
				String[] arrDfVals =  null;
				
				String[][] allData = new String[][] {arrTbName,arrUID, arrTfCaptions, arrTfVals, 
						arrOptCaptions, arrOptSelVals, arrComboCaptions, arrComboSelVals, arrDfCaptions,  arrDfVals } ;
				
					
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
			
		}else if(strTbName.equals(strTbNameActLog) && strUID.equals(strUser001)){
			
		}else if(strTbName.equals(strTbNameAccChangeLog) && strUID.equals(strUser001)){
			
		}
		
		
		
		
			
			
		
		return mappedAllData;
	}

}
