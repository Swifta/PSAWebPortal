package com.swifta.mats.web;

public class Conf {

	public final String DB;
	public final String UN;
	public final String PW;
	public final String ESB;
	public final String dbin;
	public final String env;

	public Conf(String env) {

		if (env.equals("testing")) {
			this.UN = "psatestuser";
			this.PW = "psatest_2015";
			this.DB = "jdbc:mysql://gomintdb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasourcetest";
			this.ESB = "http://54.173.157.210:8283/services/Provisionservice";
			this.dbin = "psadatasourcetest";
			this.env = env;
		} else {
			this.UN = "psaproduser";
			this.PW = "psaproduser@2015";
			this.ESB = "http://10.0.1.5:8283/services/Provisionservice";
			this.DB = "jdbc:mysql://mfisadb.caabwbnfnavv.us-east-1.rds.amazonaws.com:3306/psadatasource";
			this.dbin = "psadatasource";
			this.env = env;
		}

	}

}
