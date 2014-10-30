package com.swifta.mats.web.dashboard;


import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*; 



public class Dashboard {

	public VerticalLayout Addlabel() {
		
		
		
		
		Flash player = new Flash("Charts", new ThemeResource("Charts/FCF_Column3D.swf"));
		   
		   player.setCodebase("http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0");
		   player.setId("clsid:D27CDB6E-AE6D-11cf-96B8-444553540000");
           player.setWidth(900 + "px");
           player.setHeight(400 + "px");
           //player.setCodetype(Flash.TYPE_OBJECT);
          // player.setMimeType("application/x-shockwave-flash");
           //String url = "http://www.youtube.com/v/"+videoId+"&autoplay=1";
           //player.setSource(new ExternalResource(url));
           
           
           player.setParameter("id", "Column3D");
           //player.setSource(new ThemeResource("Charts/FCF_Column3D.swf"));
           player.setParameter("movie", "Charts/FCF_Column3D.swf");
           player.setParameter("FlashVars", "&dataXML=Data.xml");
           player.setParameter("quality", "high");
           player.setImmediate(true); 
           VerticalLayout d = new VerticalLayout();
           d.addComponent(player);
           return d;
		
		
		
		
	}
	
	

}
