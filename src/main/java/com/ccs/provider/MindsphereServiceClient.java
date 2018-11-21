package com.ccs.provider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import com.ccs.util.Provider;

public class MindsphereServiceClient {
	
	//variabili standard da impostare
	String entity = "";
	String propertysetname = "";
	String limit = "";
	String select = "";
	
	public static int oeeMedia(String from, String to) {
		
		int oeeMedia = 0;
		
		try {
			/*
			String timeseriesMindsphereStringUrl = Provider.buidMindsphereRequestURL(entity, propertysetname, from, to, limit, select);
			
			URL timeseriesMindsphereUrl = new URL(timeseriesMindsphereStringUrl);
			
			HttpURLConnection c = (HttpURLConnection) timeseriesMindsphereUrl.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.connect();
			int status = c.getResponseCode();
			
			switch(status) {
			
			case 400: //bad request, in case of invalid parameters
			case 401: //unauthorized
			case 404: //entity not found 
				
			case 200:
				//array of time series
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) !=null) {
					sb.append(line + "\n");
				}
				br.close();
				
				if(c != null) {
					c.disconnect();
				}
			
				
				JSONObject response = new JSONObject(sb.toString());

				
				//testare la chiamata di ritorno e il formato json
				JSONArray forecastDays = response.getJSONObject("forecast").getJSONObject("simpleforecast").getJSONArray("forecastday");

				for (int i=0; i<forecastDays.length(); i++) {
					JSONObject forecastDay = forecastDays.getJSONObject(i);
					Date day = new Date(forecastDay.getJSONObject("date").getLong("epoch") * 1000);
					String dayForecast = forecastDay.getString("conditions");
					forecast.setDayForecast(day, dayForecast);
				}

				LOGGER.info("Returning weather service response");
				return CompletableFuture.completedFuture(forecast);
				
			}
			*/
			//System.out.println("inizio lettura json"); 
			File file = ResourceUtils.getFile("classpath:json_example_FromRunToRun.json");				 
			//File is found
			//System.out.println("File Found : " + file.exists()); 
			//Read File Content
			String content = new String(Files.readAllBytes(file.toPath()));
			//System.out.println(content);	 
			//System.out.println("fine lettura json");
			
			//JSONObject response = new JSONObject(content);
			JSONArray responseArray = new JSONArray(content);
		    
			int contMedia = 0;
			
			
			for (int i = 0; i < responseArray.length(); i++){
				
				if(responseArray.getJSONObject(i).getInt("OEE")>0) {
					contMedia++;
					oeeMedia += responseArray.getJSONObject(i).getInt("OEE");
				}
			    //System.out.println(responseArray.getJSONObject(i).getInt("OEE"));			    
			}
			oeeMedia = oeeMedia/contMedia;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//System.out.println(oeeMedia);
		return oeeMedia;
	}
	
	public static int[] prodottiEScarti(String from, String to) {
		int[] dati = new int[2];
		
		return dati;
	}

}
