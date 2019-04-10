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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.ccs.model.IntermediateOeesModel;
import com.ccs.model.ListAndInfo;
import com.ccs.util.DateProp;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.ccs.util.Provider;
import com.siemens.mindsphere.sdk.auth.model.MindsphereCredentials;
import com.siemens.mindsphere.sdk.core.RestClientConfig;
import com.siemens.mindsphere.sdk.core.exception.MindsphereException;
import com.siemens.mindsphere.sdk.iot.asset.apiclient.AssetClient;
import com.siemens.mindsphere.sdk.iot.asset.model.AssetResource;
import com.siemens.mindsphere.sdk.iot.asset.model.Assets;
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
	
	public static List<TimeseriesData> getLargeRangeV1(String[] fromTo) throws java.text.ParseException {
	    
		List<TimeseriesData> timeseriesData = null;
	    String fromUteData = fromTo[0];
	    String toUteData = fromTo[1];
	    
	    String fromNew = DateProp.previousDay(fromTo[0]);
	    String toNew = DateProp.nextDay(fromTo[1]);
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	    Date fromUteDataFormatted = formatter.parse(fromUteData);
	    Date toUteDataFormatted = formatter.parse(fromUteData);
	    Date fromNewFormatted = formatter.parse(fromUteData);
	    Date toNewFormatted = formatter.parse(fromUteData);
	    
	    List<TimeseriesData> lista = null;
	   
	    for(int i = 0; i < lista.size(); i++) {
	    	//lista.get(i).getData();
	    	//1) controllo gli indici della data immessa dall'utente
	    	//2) controllo se devo prendermi gli indici per effettuare la correzione
	    	//3) se devo prendermi gli indici della correzione li prendo e copio in un'altra lista che ritorna la funzione
	    }
	    
	    
	    return timeseriesData;
	}
	
	public static int oeeMediaJson(String from, String to, ArrayList<Integer> oeeArr, ArrayList<String> oeeNamesArr) {
		
		int oeeMedia = 0;
		
		try {
			
			//System.out.println("inizio lettura json"); 
			File file = ResourceUtils.getFile("classpath:from_run_to_run2.json");				 
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
					oeeArr.add(contMedia, responseArray.getJSONObject(i).getInt("OEE"));
					if(i == responseArray.length() || (i == responseArray.length() - 1 ) || (i == responseArray.length() - 2) ) {
						oeeNamesArr.add(contMedia, responseArray.getJSONObject(i).getString("_time") +" - "+ responseArray.getJSONObject(i+1).getString("_time"));
					}else {
						oeeNamesArr.add(contMedia, responseArray.getJSONObject(i).getString("_time") +" - "+ responseArray.getJSONObject(i+2).getString("_time"));
					}
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
	
	public static int[] prodottiEScartiJson(String from, String to) {
		int[] dati = new int[2];
		final int PezziScartati = 0;
		final int PezziProdotti = 1;
		
		try {
			File file = ResourceUtils.getFile("classpath:from_run_to_run.json");				 
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
		
	public static int testTokenUrlDataOee(String date) throws IOException {
		
		int oeeMedia = 0;
		
		String dateFormat[] = DateProp.toMindSphereFormat(date);
		
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

	public static int[] testTokenUrlDataProdottiEScarti(String date) throws IOException {
		
		int[] dati = new int[2];
		final int PezziScartati = 0;
		final int PezziProdotti = 1;
		
		String dateFormat[] = DateProp.toMindSphereFormat(date);
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
	
	public static ErrorDataModel[] testTokenUrlGetStopCode(String date) throws IOException {
		
		String dateFormat[] = DateProp.toMindSphereFormat(date);
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
	
	public static ErrorDataModel[] testJsonGetStopCode(String date) throws IOException {
		
		
		String dateFormat[] = new String[2];
		
		if(date.substring(4, 5).equals("-")) {
			dateFormat[0] = date.substring(0, 24);
			dateFormat[1] = date.substring(25, 49);
		}else {
			dateFormat = DateProp.toMindSphereFormat(date);
		}
		
		String stringaRisposta;
		int grandezza_array = 0;
		
		//System.out.println("inizio lettura json"); 
		File file = ResourceUtils.getFile("classpath:from_run_to_run.json");				 
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
	
	public static String checkNewDataAlert(String date) throws MindsphereException, IOException, java.text.ParseException{
				
		//trasformo la data in un formato mindsphere like
		String[] dates = new String[2];
		
		if(date.substring(4, 5).equals("-")) {
			dates[0] = date.substring(0, 24);
			dates[1] = date.substring(25, 49);
		}else {
			dates = DateProp.toMindSphereFormat(date);
		}
		
		//flag inizio
		int initflag = 0;
		String newInit, goodInit;
		
		
		//flag fine
		int endflag = 0;
		String newEnd, goodEnd;
		
	    //MindsphereCredentials credentials = MindsphereCredentials.builder().clientId("itadev-service-credentials").clientSecret("012615b6-a16c-4aaf-86af-6da9060df9fa").tenant("itadev").build();

	    //RestClientConfig config = RestClientConfig.builder().build();
	    
	    //TimeseriesClient timeseriesClient = TimeseriesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();

	    //List<TimeseriesData> timeseriesDataOEE = null;
	  
		//timeseriesDataOEE = timeseriesClient.getTimeseries("codice id della tabella di mindsphere", "nome della tabella di mindsphere", dates[0], dates[1], 100, "oee");
		
		
		//if controlla se il primo elemento è uno stop. (è uno stop se ha oee = 0)
			//Se si, chiedi di partire dal run precedente a calcolare il tutto e imposta l'initflag a 1
		
		//if controlla se l'ultimo elemento è un run. (è un run se ha oee > 0)
			//se si, imposta l' endflag di fine a 1 e viene richiesto se inglobare anche lui
	
		//if initiflag == 1 
			// calcolo il range del giorno precedente e vado a prendere l'ultimo elemento il quale siamo sicuri che sia un run e prendo il tempo di questo run
			// il tempo del nuovo inizio lo salvo in una variabile d'appoggio
			newInit = DateProp.previousDay(dates[0]);
			//timeseriesDataOEE = timeseriesClient.getTimeseries("codice id della tabella di mindsphere", "nome della tabella di mindsphere", dates[0], newInit, 100, "oee");
			//mi prendo il tempo dell'ultimo valore (che siamo sicuri che sia un run)
			goodInit = "valore1";
	
		
		//if endflag == 1
			//calcolo il range del giorno successivo e prendo il primo valore che possiedo, e sono sicuro che sia un valore di run
			// il tempo della nuova fine lo salvo in una variabile d'appoggio
			newEnd = DateProp.nextDay(dates[1]);
			//timeseriesDataOEE = timeseriesClient.getTimeseries("codice id della tabella di mindsphere", "nome della tabella di mindsphere", dates[1], newEnd, 100, "oee");
			//mi prendo il tempo del primo valore (che siamo sicuri che sia un run)
			goodEnd = "valore2";
	    
	    
	    if((initflag+endflag) == 0) {
		    return "2019-01-05T01:00:00.000Z+2019-01-05T01:00:00.000Z";
	    } else {
	    	return "no";
	    }
	}
	
	//Da qui iniziano le chiamate che vengono effettuate a mindsphere
	
	//Questa funzione al suo interno effettua 3 chiamate rest e serve per prendere gli OEE, pezzi prodotti e scartati
	public static int[] oeeTotalScrapMSApi(String date, String credentialId, String tableName, int max_visual) throws MindsphereException, IOException{
		
		//array di interi che conterrà in prima posizione i pezzi scartati [0], in seconda i pezzi prodotti [1] e in terza l'oee medio [2]
		    	
		int contMedia = 0;
		int[] oeeTotScrap = new int[3];
		oeeTotScrap[0] = 0;
		oeeTotScrap[1] = 0;
		oeeTotScrap[2] = 0;
				
		//trasformo la data in un formato mindsphere like
			
		String dates[] = new String[2];
		
		System.out.println("ora che arriva dalla form:"+ date);
		
		if(date.substring(4, 5).equals("-")) {
			dates[0] = date.substring(0, 24);
			dates[1] = date.substring(25, 49);
		}else {
			dates = DateProp.toMindSphereFormat(date);
		}
		
		dates = DateProp.toMindSphereFormat(date);
		
		System.out.println("ora inizio:"+ dates[0]);
		System.out.println("ora fine: "+ dates[1]);

		
	    MindsphereCredentials credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();

	    RestClientConfig config = RestClientConfig.builder().build();
	    
	    
	    TimeseriesClient timeseriesClient = TimeseriesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();

	    List<TimeseriesData> timeseriesDataOEEScrapTotal = null;
	    
	    try {
	    	
	    	//chiamata dall'SDK per ricevere tutte le info dal db
	    	timeseriesDataOEEScrapTotal = timeseriesClient.getTimeseries(credentialId , tableName, dates[0], dates[1], max_visual, null);
	    	
	    	//set scarti totali e oee
	    	if(!(timeseriesDataOEEScrapTotal == null)) {
	    		for (int i = 0; i < timeseriesDataOEEScrapTotal.size(); i++){
					oeeTotScrap[0] =  oeeTotScrap[0] + timeseriesDataOEEScrapTotal.get(i).getData().get("PezziScartati").hashCode();
					oeeTotScrap[1] = oeeTotScrap[1] + timeseriesDataOEEScrapTotal.get(i).getData().get("PezziProdotti").hashCode();	
					if(timeseriesDataOEEScrapTotal.get(i).getData().get("OEE").hashCode() > 0) {
						contMedia++;
						oeeTotScrap[2] += timeseriesDataOEEScrapTotal.get(i).getData().get("OEE").hashCode();	
					}
				}
	    		oeeTotScrap[2] = oeeTotScrap[2]/contMedia;
	    	}
	    	
	    } catch (MindsphereException e) {
	    	System.out.println(e);
	    	System.out.println(e.getErrorMessage());
	    	System.out.println(e.getHttpStatus());
	    	System.out.println("errore nel collegamento");
	    	return oeeTotScrap;
	    }
	    
	    System.out.println("OEE: " + oeeTotScrap[2]);
	    System.out.println("collegamento stabilito e dati ricevuti");
	    
	    return oeeTotScrap;
	}

	
	public static ErrorDataModel[] stopcodeMSApi(String date, String credentialId, String tableName, int max_visual) throws MindsphereException, IOException {
		
		
		String dates[] = new String[2];
		
		if(date.substring(4, 5).equals("-")) {
			dates[0] = date.substring(0, 24);
			dates[1] = date.substring(25, 49);
		}else {
			dates = DateProp.toMindSphereFormat(date);
		}
		
		
		MindsphereCredentials credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();

	    RestClientConfig config = RestClientConfig.builder().build();
	    
	    TimeseriesClient timeseriesClient = TimeseriesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();

	    List<TimeseriesData> timeseriesStopCode = null;
	    
	    try {
	    	
	    	//lista degli stop code
	    	timeseriesStopCode = timeseriesClient.getTimeseries(credentialId , tableName, dates[0], dates[1], max_visual, null);
	    	
	    	if(!(timeseriesStopCode == null)) {
	    		for (int i = 0; i < timeseriesStopCode.size(); i++){
	    			
				}
	       	}
	    
	    } catch (MindsphereException e) {
	    	System.out.println(e);
	    	System.out.println(e.getErrorMessage());
	    	System.out.println(e.getHttpStatus());
	    	System.out.println("errore nel collegamento");
	    }
	    
	    System.out.println("collegamento stabilito e dati ricevuti");
	    
		String stringaRisposta;
		int grandezza_array = 0;
		
		//System.out.println("inizio lettura json"); 
		File file = ResourceUtils.getFile("classpath:from_run_to_run.json");				 
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
	
	/* NUOVA GESTIONE DELLA LOGICA 
	 * 
	 * Da qui inizia la gestione della logica tramite l'utilizzo delle chiamate API a MindSphere che vengono effettuate nella servlet principale /indexprova
	 * 
	 *  
	 */
	
	
	public static List<TimeseriesData> listMindsphere (String date, String credentialId, String tableName, int max_visual, String auth) throws MindsphereException, IOException{
						
		//trasformo la data in un formato mindsphere like
			
		String dates[] = new String[2];
		
		System.out.println("ora che arriva dalla form:"+ date);
		
		if(date.substring(4, 5).equals("-")) {
			dates[0] = date.substring(0, 24);
			dates[1] = date.substring(25, 49);
		}else {
			dates = DateProp.toMindSphereFormat(date);
		}
		
		dates = DateProp.toMindSphereFormat(date);
		
		System.out.println("ora inizio:"+ dates[0]);
		System.out.println("ora fine: "+ dates[1]);

		
	    //MindsphereCredentials credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();

	    MindsphereCredentials credentials = MindsphereCredentials.builder().authorization(auth).build();
	    RestClientConfig config = RestClientConfig.builder().build();
	    
	    TimeseriesClient timeseriesClient = TimeseriesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();

	    List<TimeseriesData> timeseriesList = null;
	    
	    try {
	    	
	    	//chiamata dall'SDK per ricevere tutte le info dal db
	    	timeseriesList = timeseriesClient.getTimeseries(credentialId , tableName, dates[0], dates[1], max_visual, null);
	    	
	    	//set scarti totali e oee
	    	if(!(timeseriesList == null)) {
	    		System.out.println("dati ricevuti con successo (lista non vuota)");
	    	} else {
	    		System.out.println("dati ricevuti (lista vuota)");
	    	}
	    	
	    } catch (MindsphereException e) {
	    	System.out.println(e);
	    	System.out.println(e.getErrorMessage());
	    	System.out.println(e.getHttpStatus());
	    	System.out.println("errore nel collegamento");
	    	return timeseriesList;
	    }
	    
	    System.out.println("collegamento stabilito");    
	    return timeseriesList;
	}
	
	public static ListAndInfo listAndInfoMindsphere(List<TimeseriesData> timeseriesList) {
		
		ListAndInfo timeseries_list_info = new ListAndInfo();

		if(!(timeseriesList == null)) {
			timeseries_list_info.setTimeseriesList(timeseriesList);
			timeseries_list_info.setLunghezza_lista(timeseriesList.size());
			timeseries_list_info.setData_iniziale(timeseriesList.get(0).getTimeString());
			timeseries_list_info.setData_finale(timeseriesList.get(timeseries_list_info.getLunghezza_lista()-1).getTimeString());
	
			
			if(timeseriesList.get(0).getData().get("OEE").hashCode() == 0) {
				timeseries_list_info.setTipo_iniziale("stop");
			} else {
				timeseries_list_info.setTipo_iniziale("run");
			}
			
			if(timeseriesList.get(timeseriesList.size()-1).getData().get("OEE").hashCode() == 0) {
				timeseries_list_info.setTipo_finale("stop");
			} else {
				timeseries_list_info.setTipo_finale("run");
			}
			
			System.out.println("");
			System.out.println("Data iniziale: "+timeseries_list_info.getData_iniziale());
			System.out.println("Data finale: "+timeseries_list_info.getData_finale());
			System.out.println("Tipo iniziale: "+timeseries_list_info.getTipo_iniziale());
			System.out.println("Tipo finale: "+timeseries_list_info.getTipo_finale());
			System.out.println("Lunghezza lista: "+timeseries_list_info.getLunghezza_lista());
			System.out.println("");

		}
		return timeseries_list_info;
		
	}
	
	public static int[] oeeTotalScrapMSApi(ListAndInfo timeseries_list_info) throws MindsphereException, IOException{
		
		//array di interi che conterrà in prima posizione i pezzi scartati [0], in seconda i pezzi prodotti [1] e in terza l'oee medio [2]
		    	
		int contMedia = 0;
		int[] oeeTotScrap = new int[3];
		oeeTotScrap[0] = 0;
		oeeTotScrap[1] = 0;
		oeeTotScrap[2] = 0;
		
		List<TimeseriesData> timeseriesDataOEEScrapTotal = timeseries_list_info.getTimeseriesList();
		
    	//set scarti totali e oee
		
    	if(!(timeseriesDataOEEScrapTotal == null)) {
    		if(timeseriesDataOEEScrapTotal.size()>3) {

	    		for (int i = 0; i < timeseriesDataOEEScrapTotal.size(); i++){
					if(i == 0) {
						if(timeseriesDataOEEScrapTotal.get(0).getData().get("OEE").hashCode() == 0) {
							//vado avanti di 3 perchè è il primo run utile		
							i = i + 3;
						}else {
							
							//vado avanti di 2 perchè è il primo run utile
							i = i + 2;
						}
					}
					oeeTotScrap[0] =  oeeTotScrap[0] + timeseriesDataOEEScrapTotal.get(i).getData().get("PezziScartati").hashCode();
					oeeTotScrap[1] = oeeTotScrap[1] + timeseriesDataOEEScrapTotal.get(i).getData().get("PezziProdotti").hashCode();	
					if(timeseriesDataOEEScrapTotal.get(i).getData().get("OEE").hashCode() > 0) {
						contMedia++;
						oeeTotScrap[2] += timeseriesDataOEEScrapTotal.get(i).getData().get("OEE").hashCode();	
					}
				}
	    		oeeTotScrap[2] = oeeTotScrap[2]/contMedia;
    		}
    	}
    	
    	return oeeTotScrap;
	}
	
	public static void intermediateOees(List<TimeseriesData> list, IntermediateOeesModel intermediateOees) {
		
		/* se impostato a 1 = il primo era uno stop e posso prendere il primo tempo di run che è su i-2
		 * se impostato a 2 = il primo era un run e posso prendere il primo tempo di run che è su i-2
		 */
		
		int flag = 0;
		
		ArrayList<Integer> oeeArr = new ArrayList<Integer>();
		ArrayList<String> oeeNamesArr = new ArrayList<String>();
		
		int contMedia = 0;
		
		if(!(list == null)) {
			
			for (int i = 0; i < list.size() - 1; i++){
				/*
				if(list.get(0).getData().get("OEE").hashCode() == 0) {
					
					//vado avanti di 3 perchè è il primo run utile		
					i = i + 3;
					flag = 1;
				}else {
					
					//vado avanti di 2 perchè è il primo run utile
					i = i + 2;
					flag = 2;
				}
				*/
				if(list.get(i).getData().get("OEE").hashCode()>0) {
						oeeArr.add(contMedia, list.get(i).getData().get("OEE").hashCode());
						if(i == list.size()|| (i == list.size() - 1 ) || (i == list.size() - 2) ) {
							oeeNamesArr.add(contMedia, list.get(i).getTimeString() +" - "+list.get(i+1).getTimeString());
						}else {
							oeeNamesArr.add(contMedia, list.get(i).getTimeString() +" - "+list.get(i+2).getTimeString());
						}
						contMedia++;
				}
			}
			
			
		} else {
			oeeArr.add(0, 0);
			oeeNamesArr.add(0, "null");
		}
		intermediateOees.setOeeArray(oeeArr);
		intermediateOees.setOeeNamesArr(oeeNamesArr);
	}
	
	public static void intermediateOeesModifica(ListAndInfo timeseries_list_info, IntermediateOeesModel intermediateOees) {
		
		/* se impostato a 1 = il primo era uno stop e posso prendere il primo tempo di run che è su i-2
		 * se impostato a 2 = il primo era un run e posso prendere il primo tempo di run che è su i-2
		 */
		
		int flag = 0;
		
		List <TimeseriesData> list = timeseries_list_info.getTimeseriesList();
		
		ArrayList<Integer> oeeArr = new ArrayList<Integer>();
		ArrayList<String> oeeNamesArr = new ArrayList<String>();
		
		int contMedia = 0;
		
		if(!(list == null)) {
			if(list.size()<4) {
				
				oeeArr.add(0, 0);
				oeeNamesArr.add(0, "null");
				
			} else {
				
				for (int i = 0; i < list.size(); i++){
					
					if(i == 0) {
						if(list.get(0).getData().get("OEE").hashCode() == 0) {
							
							//vado avanti di 3 perchè è il primo run utile		
							i = i + 3;
							flag = 1;
						}else {
							
							//vado avanti di 2 perchè è il primo run utile
							i = i + 2;
							flag = 2;
						}
					}	
					if(list.get(i).getData().get("OEE").hashCode()>0) {
							oeeArr.add(contMedia, list.get(i).getData().get("OEE").hashCode());
							oeeNamesArr.add(contMedia, list.get(i-2).getTimeString() +" - "+list.get(i-1).getTimeString());
							contMedia++;
					}
				}
			}	
		} else {
			oeeArr.add(0, 0);
			oeeNamesArr.add(0, "null");
		}
		intermediateOees.setOeeArray(oeeArr);
		intermediateOees.setOeeNamesArr(oeeNamesArr);
	}
	
	public static ErrorDataModel[] getStopCodeFromList(ListAndInfo timeseries_list_info) {
		
		int grandezza_array = 0;
		
		List<TimeseriesData> list = timeseries_list_info.getTimeseriesList();
		
		for (int i = 0; i < timeseries_list_info.getLunghezza_lista(); i++){
			if((list.get(i).getData().get("OEE").hashCode() == 0)) {
				grandezza_array++;				
			}
		}
		
		//deve essere inizializzato dopo che si è contato il numero di errori contenuti nel json di ritorno
		ErrorDataModel[] error_code ;
		
		if(grandezza_array == 0) {
			error_code  = new ErrorDataModel[0]; 
		} else {
			error_code  = new ErrorDataModel[grandezza_array-1]; 
		}
		
		if(!(list == null)) {
			if(list.size()>3) {

				for (int i = 0, j = 0; i < timeseries_list_info.getLunghezza_lista(); i++){
					if(i == 0) {
						if(list.get(0).getData().get("OEE").hashCode() == 0) {
							
							//vado avanti di 2 perchè è il primo run utile		
							i = i + 2;
						}else {
							
							//vado avanti di 1 perchè è il primo run utile
							i = i + 1;
						}
					}	
					
					if(list.get(i).getData().get("OEE").hashCode() == 0) {
						ErrorDataModel temp = new ErrorDataModel();
						temp.setErrorCode(list.get(i).getData().get("CodeStop").hashCode());
						temp.setTimestamp(list.get(i).getTimeString());
						error_code[j] = temp;
						j++;
					}
				}
			}
		}
		return error_code;
	}
	
	public static String checkNewDataAlert(ListAndInfo timeseries_list_info, String auth) throws java.text.ParseException, MindsphereException {
			
			
		//flag inizio
		int initflag = 0;
		String newInit, goodInit = "";
		
		
		//flag fine
		int endflag = 0;
		String newEnd, goodEnd = "";
		
		if(timeseries_list_info.getTimeseriesList() == null) {
			return "0";
		} else {
			//MindsphereCredentials credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();
			
			MindsphereCredentials credentials = MindsphereCredentials.builder().authorization(auth).build();

			RestClientConfig config = RestClientConfig.builder().build();
			    
			TimeseriesClient timeseriesClient = TimeseriesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();
			
			List<TimeseriesData> init_date = null;
			List<TimeseriesData> final_date = null;
			
			
			if(timeseries_list_info.getTipo_iniziale().equals("stop")) {
				//if controlla se il primo elemento è uno stop. (è uno stop se ha oee = 0)
				//Se si, chiedi di partire dal run precedente a calcolare il tutto e imposta l'initflag a 1
				
				initflag = 1;
				
			}
			
			
			if(timeseries_list_info.getTipo_finale().equals("run")) {
				//if controlla se l'ultimo elemento è un run. (è un run se ha oee > 0)
				//se si, imposta l' endflag di fine a 1 e viene richiesto se inglobare anche lui
				System.out.println("endflag di run: "+endflag);
				endflag = 1;
				
			}
			
			try {
				
				if(initflag == 1) {
					//if initiflag == 1 
					// calcolo il range del giorno precedente e vado a prendere l'ultimo elemento il quale siamo sicuri che sia un run e prendo il tempo di questo run
					// il tempo del nuovo inizio lo salvo in una variabile d'appoggio
					
					//newInit = DateProp.previousDay(dates[0]);
					
					//timeseriesDataOEE = timeseriesClient.getTimeseries("codice id della tabella di mindsphere", "nome della tabella di mindsphere", dates[0], newInit, 100, "oee");
					//mi prendo il tempo dell'ultimo valore (che siamo sicuri che sia un run)
					//goodInit = "valore1";
					
					newInit = DateProp.previousDayList(timeseries_list_info.getData_iniziale());
					
					//lista degli stop code
					init_date = timeseriesClient.getTimeseries("e8e33cdb64fa4e14b0692d4d66b5fd04" , "FromRunToRun", newInit, timeseries_list_info.getData_iniziale(), 2000, "OEE");
					
					int index;
					
					index = init_date.size();
					System.out.println("grandezza lista di ritorno dalla prima chiamata: "+index);

					if(index>1) {
						index = index -2;
						goodInit =  goodInit + init_date.get(index).getTimeString();
					} else {
					goodInit =  goodInit + timeseries_list_info.getData_iniziale();
					}
					System.out.println("nuovo elemento di partenza: "+goodInit);
				}
				
				if(endflag == 1) {
					//if endflag == 1
					//calcolo il range del giorno successivo e prendo il primo valore che possiedo, e sono sicuro che sia un valore di run
					// il tempo della nuova fine lo salvo in una variabile d'appoggio
					
					//newEnd = DateProp.nextDay(dates[1]);
					
					//timeseriesDataOEE = timeseriesClient.getTimeseries("codice id della tabella di mindsphere", "nome della tabella di mindsphere", dates[1], newEnd, 100, "oee");
					//mi prendo il tempo del primo valore (che siamo sicuri che sia un run)
					//goodEnd = "valore2";
					
					newEnd = DateProp.nextDayList(timeseries_list_info.getData_finale());
					
					//lista degli stop code
					final_date = timeseriesClient.getTimeseries("e8e33cdb64fa4e14b0692d4d66b5fd04" , "FromRunToRun", timeseries_list_info.getData_finale(), newEnd, 2, "OEE");
					
					if((final_date.size() == 0) || (final_date.size() == 1)) {
						goodEnd = goodEnd + timeseries_list_info.getData_finale();
					} else {
						goodEnd = goodEnd +  final_date.get(1).getTimeString();	
					}
					System.out.println("nuovo elemento di arrivo: "+goodEnd);
				}
			} catch (MindsphereException e) {
		    	System.out.println(e);
		    	System.out.println(e.getErrorMessage());
		    	System.out.println(e.getHttpStatus());
		    }

					
			
		    if((initflag == 1) && (endflag == 1)) {
			    return goodInit + "+" + goodEnd;
		    } else if((initflag == 1) && (endflag == 0)) {
		    	 return goodInit + "+" + timeseries_list_info.getData_finale();
		    } else if((initflag == 0) && (endflag == 1)) {
		    	 return timeseries_list_info.getData_iniziale() + "+" + goodEnd;
		    } else {
		    	return "no";
		    }
			
		}
		
	}

	

	/* GESTIONE DELLA LOGICA PER LA GENERAZIONE AUTOMATICA DEGLI ASSET E VISUALIZZAZIONE DEI NOMI E CODICI 
	 * 
	 * Da qui inizia la gestione della logica tramite l'utilizzo delle chiamate API a MindSphere che vengono effettuate nella servlet principale /indexprovatime
	 * 
	 *  
	 */
	
	public static ArrayList<String> reciveAsset(String auth) {
		
		ArrayList<String> list_asset_id = new ArrayList<String>();
		
		MindsphereCredentials credentials = MindsphereCredentials.builder().authorization(auth).build();
	   
		//MindsphereCredentials credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();
		
		RestClientConfig config = RestClientConfig.builder().build();
	    
		AssetClient  assetClient = AssetClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();
	    
	    Assets assets = null;
	    
	    List<AssetResource> asset_resource = null;
	    
	    
	    try {
	    	 assets = assetClient.getAssets();
	    	 asset_resource = assets.getEmbedded().getAssets();
	    	 if(asset_resource.size() == 0) {
	    		 list_asset_id.add("empity list");
	    	 }
	    	 for(int i = 0; i < asset_resource.size(); i++) {
	    		 if(!(asset_resource.get(i).getVariables().isEmpty())) {
	    			 if(asset_resource.get(i).getVariables().get(0).getName().equals("ccs_type")) {
		    			 list_asset_id.add(asset_resource.get(i).getAssetId());
		    		 }
	    		 }
	    	 }
	    	 //asset =  asset_resource.get(1).getVariables().get(0).getName();
	    		  
	    	 
	    } catch (MindsphereException e) {
	    	System.out.println(e);
	    	System.out.println(e.getErrorMessage());
	    	System.out.println(e.getHttpStatus());
	    	System.out.println("errore nel collegamento");
	    	return list_asset_id;
	    }
	    
	    System.out.println("collegamento stabilito");    
	    return list_asset_id;		
	}
}