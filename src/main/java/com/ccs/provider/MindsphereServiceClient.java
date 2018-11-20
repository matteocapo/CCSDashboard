package com.ccs.provider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ccs.util.Provider;

public class MindsphereServiceClient {
	
	//variabili standard da impostare
	String entity = "";
	String propertysetname = "";
	String limit = "";
	String select = "";
	
	public int oeeMedia(String from, String to) {
		
		int oeeMedia;
		
		try {
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
				/*JSONArray forecastDays = response.getJSONObject("forecast").getJSONObject("simpleforecast").getJSONArray("forecastday");

				for (int i=0; i<forecastDays.length(); i++) {
					JSONObject forecastDay = forecastDays.getJSONObject(i);
					Date day = new Date(forecastDay.getJSONObject("date").getLong("epoch") * 1000);
					String dayForecast = forecastDay.getString("conditions");
					forecast.setDayForecast(day, dayForecast);
				}

				LOGGER.info("Returning weather service response");
				return CompletableFuture.completedFuture(forecast);
				*/
			}
			

			
		} catch (Exception e) {
			
		}
			
		
		return 1;
	}
	
	public int[] prodottiEScarti(String from, String to) {
		int[] dati = new int[2];
		
		return dati;
	}

}
