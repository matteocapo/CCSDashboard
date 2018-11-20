package com.ccs.util;

public class Provider {
	
	private static final String MINDSPHERE_BASE_URL = "/api/iottimeseries/v3";
	
	//da definire in seguito
	private static final String MINDSPHERE_URL = "https://eu1.mindsphere.com";
	
	public static String buidMindsphereRequestURL(String entity, String propertysetname, String from, String to, String limit, String select){
		//da completare la struttura della chiamata
		String url = MINDSPHERE_URL + MINDSPHERE_BASE_URL +"/"+ entity +"/"+ propertysetname +"/"+ from +"/"+ to +"/"+ limit +"/"+ select; 
		return url;
	}

}
