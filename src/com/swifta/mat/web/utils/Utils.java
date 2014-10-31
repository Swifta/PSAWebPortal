package com.swifta.mat.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class Utils {
	
	String u;

 public String queryServer() throws HttpException, IOException{
	 
	 String url = "http://54.164.96.105:8283/perform/authentication?username=AG000001&password=password";
     InputStream in = null;

     try {
         HttpClient client = new HttpClient();
         PostMethod method = new PostMethod(url);
         
       
			//Add any parameter if u want to send it with Post req.
        
         //method.addParameter("username", "emeka@swifta.com");
        // method.addParameter("password", "emeka");
         int statusCode = client.executeMethod(method);

         if (statusCode != -1) {
             try {
				in = method.getResponseBodyAsStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

         }
         BufferedReader reader = new BufferedReader(new InputStreamReader(in));
         StringBuilder out = new StringBuilder();
         String line;
         while ((line = reader.readLine()) != null) {
             out.append(line);
            
         }
         u = line;
         
         System.out.println(out.toString());			  
         
 }finally{}
	return u;
}
}
