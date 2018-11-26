package com.ccs.provider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import com.ccs.model.ErrorDataModel;
import com.ccs.util.Date;
import com.ccs.util.Provider;
import com.siemens.mindsphere.sdk.auth.model.MindsphereCredentials;
import com.siemens.mindsphere.sdk.core.RestClientConfig;
import com.siemens.mindsphere.sdk.core.exception.MindsphereException;
import com.siemens.mindsphere.sdk.iot.timeseries.apiclient.TimeseriesClient;
import com.siemens.mindsphere.sdk.iot.timeseries.model.TimeseriesData;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class MindsphereServiceClient {
	
	//variabili standard da impostare
	String entity = "";
	String propertysetname = "";
	String limit = "";
	String select = "";
	private static String URL = "b75b1930-bb8e-4d00-9b25-50bdde5e1b10";
	
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
		final int PezziScartati = 0;
		final int PezziProdotti = 1;
		
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
				
				if(responseArray.getJSONObject(i).getInt("StopTime")>0) {
					contMedia++;
					dati[PezziScartati] += responseArray.getJSONObject(i).getInt("PezziScartati");
					dati[PezziProdotti] += responseArray.getJSONObject(i).getInt("PezziProdotti");
				}
			    //System.out.println(responseArray.getJSONObject(i).getInt("OEE"));			    
			}
			dati[PezziScartati] = dati[PezziScartati]/contMedia;
			dati[PezziProdotti] = dati[PezziProdotti]/contMedia;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println(dati[PezziScartati]);
		System.out.println(dati[PezziProdotti]);
		
		return dati;
	}

	public static TimeseriesData getTimeseriesAsObject(String entity, String propertySetName, String token) throws MindsphereException{
		
		MindsphereCredentials credentials = MindsphereCredentials.builder().authorization(token).build();
	
		RestClientConfig config = RestClientConfig.builder().connectionTimeoutInSeconds(100).build();
		
		TimeseriesClient timeseriesClient = TimeseriesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();
		
		TimeseriesData timeseriesData = null;
		try {
			timeseriesData = timeseriesClient.getLatestTimeseries(entity, propertySetName);
		} catch (MindsphereException e) {
			
			System.out.println(e);
		}
		return timeseriesData;
	}
	
	public static int testUrlDataOee(String date) throws IOException {
		
		int oeeMedia = 0;
		
		String dateFormat[] = Date.toMindSphereFormat(date);
		
		String stringaRisposta;
		
		System.out.println(dateFormat[0]);
		
		System.out.println(dateFormat[1]);

				
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("https://ccs-fleetmanager.eu1.mindsphere.io/api/iottimeseries/v3/timeseries/8037b181ae634a0aa00e01e4afa61005/FromRunToRun?from="+dateFormat[0]+"&to="+dateFormat[1])
		  .get()
		  .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		  .addHeader("Accept-Encoding", "gzip, deflate, br")
		  .addHeader("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3")
		  .addHeader("Connection", "keep-alive")
		  .addHeader("Cache-Control", "max-age=0")
		  .addHeader("Cookie", "SESSION=" + URL)
		  .addHeader("Host", "ccs-fleetmanager.eu1.mindsphere.io")
		  .addHeader("Referer", "https://ccs.uiam.eu1.mindsphere.io/saml/idp/SSO/alias/ccs.uiam.eu1.mindsphere.io")
		  .addHeader("TE", "Trailers")
		  .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0")
		  .addHeader("Upgrade-Insecure-Requests", "0")
		  .addHeader("cache-control", "no-cache")
		  .build();

		Response response1 = client.newCall(request).execute();
		
		stringaRisposta = response1.body().string();
		
		System.out.println(stringaRisposta);
	
		
		JSONArray responseArray = new JSONArray(stringaRisposta);
	    
		int contMedia = 0;
		
		
		for (int i = 0; i < responseArray.length(); i++){
			
			if(responseArray.getJSONObject(i).getInt("OEE")>0) {
				contMedia++;
				oeeMedia += responseArray.getJSONObject(i).getInt("OEE");
			}
		    //System.out.println(responseArray.getJSONObject(i).getInt("OEE"));			    
		}
		oeeMedia = oeeMedia/contMedia;
		
		return oeeMedia;		
	}

	public static int[] testUrlDataProdottiEScarti(String date) throws IOException {
		
		int[] dati = new int[2];
		final int PezziScartati = 0;
		final int PezziProdotti = 1;
		
		String dateFormat[] = Date.toMindSphereFormat(date);
		String stringaRisposta;
		
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("https://ccs-fleetmanager.eu1.mindsphere.io/api/iottimeseries/v3/timeseries/8037b181ae634a0aa00e01e4afa61005/FromRunToRun?from="+dateFormat[0]+"&to="+dateFormat[1])
		  .get()
		  .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		  .addHeader("Accept-Encoding", "gzip, deflate, br")
		  .addHeader("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3")
		  .addHeader("Connection", "keep-alive")
		  .addHeader("Cache-Control", "max-age=0")
		  .addHeader("Cookie", "SESSION=" + URL)
		  .addHeader("Host", "ccs-fleetmanager.eu1.mindsphere.io")
		  .addHeader("Referer", "https://ccs.uiam.eu1.mindsphere.io/saml/idp/SSO/alias/ccs.uiam.eu1.mindsphere.io")
		  .addHeader("TE", "Trailers")
		  .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0")
		  .addHeader("Upgrade-Insecure-Requests", "0")
		  .addHeader("cache-control", "no-cache")
		  .build();

		Response response1 = client.newCall(request).execute();
		
		stringaRisposta = response1.body().string();
		
		JSONArray responseArray = new JSONArray(stringaRisposta);		
		
		System.out.println(stringaRisposta);
		
		for (int i = 0; i < responseArray.length(); i++){
			
			if(responseArray.getJSONObject(i).getInt("StopTime")>0) {
				
				dati[PezziScartati] += responseArray.getJSONObject(i).getInt("PezziScartati");
				dati[PezziProdotti] += responseArray.getJSONObject(i).getInt("PezziProdotti");
			}
		    //System.out.println(responseArray.getJSONObject(i).getInt("OEE"));			    
		}
		dati[PezziScartati] = dati[PezziScartati];
		dati[PezziProdotti] = dati[PezziProdotti];
		
		System.out.println(dati[PezziScartati]);
		System.out.println(dati[PezziProdotti]);
		
		return dati;
	}
	
	public static ErrorDataModel[] testGetStopCode(String date) throws IOException {
		
		String dateFormat[] = Date.toMindSphereFormat(date);
		String stringaRisposta;
		int grandezza_array = 0;
		
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("https://ccs-fleetmanager.eu1.mindsphere.io/api/iottimeseries/v3/timeseries/8037b181ae634a0aa00e01e4afa61005/FromRunToRun?from="+dateFormat[0]+"&to="+dateFormat[1])
		  .get()
		  .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		  .addHeader("Accept-Encoding", "gzip, deflate, br")
		  .addHeader("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3")
		  .addHeader("Connection", "keep-alive")
		  .addHeader("Cache-Control", "max-age=0")
		  .addHeader("Cookie", "SESSION=" + URL)
		  .addHeader("Host", "ccs-fleetmanager.eu1.mindsphere.io")
		  .addHeader("Referer", "https://ccs.uiam.eu1.mindsphere.io/saml/idp/SSO/alias/ccs.uiam.eu1.mindsphere.io")
		  .addHeader("TE", "Trailers")
		  .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0")
		  .addHeader("Upgrade-Insecure-Requests", "0")
		  .addHeader("cache-control", "no-cache")
		  .build();

		Response response1 = client.newCall(request).execute();
		
		stringaRisposta = response1.body().string();
		
		JSONArray responseArray = new JSONArray(stringaRisposta);		
		
		
		for (int i = 0; i < responseArray.length(); i++){
			
			if(responseArray.getJSONObject(i).getInt("OEE")>0) {
				grandezza_array++;				
			}
		}
		
		//deve essere inizializzato dopo che si è contato il numero di errori contenuti nel json di ritorno
		ErrorDataModel[] error_code  = new ErrorDataModel[grandezza_array]; 
		

		for (int i = 0; i < responseArray.length(); i++){
			
			if(responseArray.getJSONObject(i).getInt("OEE")>0) {
				ErrorDataModel temp = new ErrorDataModel();
				temp.setErrorCode(responseArray.getJSONObject(i).getInt("CodeStop"));
				temp.setTimestamp(responseArray.getJSONObject(i).getString("_time"));
				error_code[i] = temp;
			}
		}
		
		//prova stampa degli elementi
		for (int i = 0; i < grandezza_array; i++) {
			System.out.println(error_code[i].getErrorCode());
			System.out.println(error_code[i].getTimestamp());
		}
		
		return error_code;
	}
}
