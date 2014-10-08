package com.swifta.mats.web.usermanagement;

import java.util.HashMap;
import java.util.Map;

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
		
		
		String strUser001 = "001";
		
		if(strTbName.equals(strTbNamePersonal) && strUID.equals(strUser001)){
			String arrUID[] = {"001"};
			String arrTbName[] = {"Personal"};
			
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
			
				mappedAllData.put("arrUID", allData[0]);
				mappedAllData.put("arrTbName", allData[1]);
				mappedAllData.put("arrTfCaptions", allData[2]);
				mappedAllData.put("arrTfVals", allData[3]);
				mappedAllData.put("arrOptCaptions", allData[4]);
				mappedAllData.put("arrOptVals", allData[5]);
				mappedAllData.put("arrComboCaptions", allData[6]);
				mappedAllData.put("arrComboVals", allData[7]);
				mappedAllData.put("arrDfCaptions", allData[8]);
				mappedAllData.put("arrDfVals", allData[9]);
			
		}else if(strTbName.equals(strTbNameAccount) && strUID.equals(strUser001)){
			
		}else if(strTbName.equals(strTbNameAuth) && strUID.equals(strUser001)){
			
		}else if(strTbName.equals(strTbNameActLog) && strUID.equals(strUser001)){
			
		}else if(strTbName.equals(strTbNameAccChangeLog) && strUID.equals(strUser001)){
			
		}
		
		
		
		
			
			
		
		return mappedAllData;
	}

}
