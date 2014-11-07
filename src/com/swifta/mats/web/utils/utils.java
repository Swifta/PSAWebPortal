package com.swifta.mats.web.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class utils {
	String url = "http://54.164.96.105:8283/perform/authentication?username=AG000001&password=password";
	InputStream in = null;
	String u;

	public String queryServer() {
		try {
			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(url);

			// Add any parameter if u want to send it with Post req.

			// method.addParameter("username", "emeka@swifta.com");
			// method.addParameter("password", "emeka");
			// this is just a new test
			int statusCode = client.executeMethod(method);

			if (statusCode != -1) {
				in = method.getResponseBodyAsStream();

			}
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder out = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				out.append(line);

			}
			u = line;

			System.out.println(out.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	public static Calendar DateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
}
