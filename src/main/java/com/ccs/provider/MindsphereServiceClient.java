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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ccs.model.ErrorDataModel;
import com.ccs.util.Date;
import com.ccs.util.Provider;
import com.siemens.mindsphere.sdk.auth.model.MindsphereCredentials;
import com.siemens.mindsphere.sdk.core.RestClientConfig;
import com.siemens.mindsphere.sdk.core.exception.MindsphereException;
import com.siemens.mindsphere.sdk.iot.timeseries.apiclient.TimeseriesClient;
import com.siemens.mindsphere.sdk.iot.timeseries.model.TimeseriesData;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class MindsphereServiceClient {
	
	//variabili standard da impostare
	String entity = "";
	String propertysetname = "";
	String limit = "";
	String select = "";
	private static String URL_TOKEN = "a930a23f-7838-4c00-b67f-eb21d3531d00";
	
	public static int oeeMedia(String from, String to) {
		
		int oeeMedia = 0;
		
		try {
			
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

	public static String getTimeSeriesAsObject(String entity, String propertySetName) throws MindsphereException, IOException{
		
	    MindsphereCredentials credentials = MindsphereCredentials.builder().clientId("itadev-service-credentials").clientSecret("012615b6-a16c-4aaf-86af-6da9060df9fa").tenant("itadev").build();

	    RestClientConfig config = RestClientConfig.builder().build();
	    
	    TimeseriesClient timeseriesClient = TimeseriesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();

	    TimeseriesData timeseriesData = null;
	    try {
	      timeseriesData = timeseriesClient.getLatestTimeseries(entity, propertySetName);

	    } catch (MindsphereException e) {
	    	System.out.println(e.getErrorMessage());
	    	System.out.println(e.getHttpStatus());
	    	return "Eccezione sollevata"+ e.getErrorMessage() + e.getHttpStatus();
	    }

	    return "Chiamata effettuata e dati di ritorno corretti";
	}

	public static void testApiSelfMade () throws IOException {
		
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");
		Request request = new Request.Builder()
		  .url("https://itadev.piam.eu1.mindsphere.io/oauth/token")
		  .post(body)
		  .addHeader("Host", "itadev.piam.eu1.mindsphere.io")
		  .addHeader("Content-Type", "application/x-www-form-urlencoded")
		  .addHeader("Authorization", "Basic aXRhZGV2LXNlcnZpY2UtY3JlZGVudGlhbHM6MDEyNjE1YjYtYTE2Yy00YWFmLTg2YWYtNmRhOTA2MGRmOWZh")
		  .build();
		//la parte di authorizazzione deve essere codificata in base64 e inizialmente ha la forma: {ServiceCredentialID: ServiceCredentialSecret}
		Response response = client.newCall(request).execute();
		
		//da modificare con il json
		String token_chiamata = response.body().string().substring(17, 1223);

		Request request2 = new Request.Builder()
		  .url("https://gateway.eu1.mindsphere.io/api/iottimeseries/v3/timeseries/7cb21d4c9b724be5b38c2c9695d9b3c8/demobox?from=&to=&limit=&select=&access_token=" + token_chiamata)
		  .get()
		  .addHeader("cache-control", "no-cache")
		  .build();

		Response response2 = client.newCall(request2).execute();
				
		System.out.println(response2.body().string());
	
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
		  .addHeader("Cookie", "SESSION=" + URL_TOKEN)
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
		if(contMedia == 0) {
			oeeMedia = 0;
		}else {
			oeeMedia = oeeMedia/contMedia;
		}
		
		
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
		  .addHeader("Cookie", "SESSION=" + URL_TOKEN)
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
		
		//System.out.println(stringaRisposta);
		
		for (int i = 0; i < responseArray.length(); i++){
			
			if(responseArray.getJSONObject(i).getInt("StopTime")>0) {
				
				dati[PezziScartati] += responseArray.getJSONObject(i).getInt("PezziScartati");
				dati[PezziProdotti] += responseArray.getJSONObject(i).getInt("PezziProdotti");
			}
		    //System.out.println(responseArray.getJSONObject(i).getInt("OEE"));			    
		}
		dati[PezziScartati] = dati[PezziScartati];
		dati[PezziProdotti] = dati[PezziProdotti];
		
		//System.out.println(dati[PezziScartati]);
		//System.out.println(dati[PezziProdotti]);
		
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
		  .addHeader("Cookie", "SESSION=" + URL_TOKEN)
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
			
			if(responseArray.getJSONObject(i).getInt("OEE") == 0) {
				grandezza_array++;				
			}
		}
		
		//deve essere inizializzato dopo che si è contato il numero di errori contenuti nel json di ritorno
		ErrorDataModel[] error_code  = new ErrorDataModel[grandezza_array]; 
		

		for (int i = 0, j = 0; i < responseArray.length(); i++){
			
			if(responseArray.getJSONObject(i).getInt("OEE") == 0) {
				ErrorDataModel temp = new ErrorDataModel();
				temp.setErrorCode(responseArray.getJSONObject(i).getInt("CodeStop"));
				temp.setTimestamp(responseArray.getJSONObject(i).getString("_time"));
				error_code[j] = temp;
				j++;
			}
			
		}
		
		//prova stampa degli elementi
		/*
		for (int i = 0; i < grandezza_array; i++) {
			System.out.println(error_code[i].getErrorCode());
			System.out.println(error_code[i].getTimestamp());
		}
		*/
		
		
		return error_code;
	}

	public static ErrorDataModel[] testGetStopCodeStaticJson(String date) throws IOException {

		int grandezza_array = 0;
		
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
		
		
		for (int i = 0; i < responseArray.length(); i++){
			
			if(responseArray.getJSONObject(i).getInt("OEE") == 0) {
				grandezza_array++;				
			}
		}
		
		//deve essere inizializzato dopo che si è contato il numero di errori contenuti nel json di ritorno
		ErrorDataModel[] error_code  = new ErrorDataModel[grandezza_array]; 
		

		for (int i = 0, j = 0; i < responseArray.length(); i++){
			
			if(responseArray.getJSONObject(i).getInt("OEE") == 0) {
				ErrorDataModel temp = new ErrorDataModel();
				temp.setErrorCode(responseArray.getJSONObject(i).getInt("CodeStop"));
				temp.setTimestamp(responseArray.getJSONObject(i).getString("_time"));
				error_code[j] = temp;
				j++;
			}
			
		}
		
		//prova stampa degli elementi
		/*
		for (int i = 0; i < grandezza_array; i++) {
			System.out.println(error_code[i].getErrorCode());
			System.out.println(error_code[i].getTimestamp());
		}
		*/
		return error_code;
	}
	
	public static void dataInfoMs() throws IOException {
		
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");
		Request request = new Request.Builder()
		  .url("https://itadev.piam.eu1.mindsphere.io/oauth/token")
		  .post(body)
		  .addHeader("Host", "itadev.piam.eu1.mindsphere.io")
		  .addHeader("Content-Type", "application/x-www-form-urlencoded")
		  .addHeader("Authorization", "Basic aXRhZGV2LXNlcnZpY2UtY3JlZGVudGlhbHM6MDEyNjE1YjYtYTE2Yy00YWFmLTg2YWYtNmRhOTA2MGRmOWZh")
		  .build();
		//la parte di authorizazzione deve essere codificata in base64 e inizialmente ha la forma: {ServiceCredentialID: ServiceCredentialSecret}
		Response response = client.newCall(request).execute();
		
		//da modificare con il json
		String token_chiamata = response.body().string().substring(17, 1223);

		Request request2 = new Request.Builder()
		  .url("https://gateway.eu1.mindsphere.io/api/identitymanagement/v3/Grups?access_token=" + token_chiamata)
		  .get()
		  .addHeader("cache-control", "no-cache")
		  .build();

		Response response2 = client.newCall(request2).execute();
				
		System.out.println(response2.body().string());
		
		
		
	}
}