package com.ccs.util;
import com.squareup.okhttp.Response;
import java.util.logging.*;

public class Utils {
	private static Logger LOGGER = Logger.getLogger( Utils.class.getName() );
	
	public Utils() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean isAGoodResponse(Response response) {
		
		switch(response.code()) {
			case 200:
				LOGGER.log(Level.FINE, "Good Responce : Timeseries Retrieved (200)");
				return true;
			case 400:
				LOGGER.log(Level.SEVERE, "Bad Responce : Invalid Parameter (400)");
				return false;
			case 401:
				LOGGER.log(Level.SEVERE, "Bad Responce : Unauthorized (401)");
				return false;
			case 403:
				LOGGER.log(Level.SEVERE, "Bad Responce : Forbidden (403)");
				return false;
			case 404:
				LOGGER.log(Level.SEVERE, "Bad Responce : Entity not Found (404)");
				return false;
			default:
				LOGGER.log(Level.SEVERE, "unexpected error (" + response.code() + ")");
				return false;
		}
	}

}
