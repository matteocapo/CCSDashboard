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
import java.util.Map;
import java.util.stream.Stream;

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

import com.ccs.model.CompareModel;
import com.ccs.model.ErrorDataModel;
import com.ccs.model.IntermediateOeesModel;
import com.ccs.model.ListAndInfo;
import com.ccs.model.RawDataModel;
import com.ccs.util.DateProp;
import java.util.Date;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.ccs.util.Provider;
import com.siemens.mindsphere.sdk.auth.model.MindsphereCredentials;
import com.siemens.mindsphere.sdk.core.RestClientConfig;
import com.siemens.mindsphere.sdk.core.exception.MindsphereException;
import com.siemens.mindsphere.sdk.iot.asset.apiclient.AssetClient;
import com.siemens.mindsphere.sdk.iot.asset.model.AssetResource;
import com.siemens.mindsphere.sdk.iot.asset.model.Assets;
import com.siemens.mindsphere.sdk.iot.fileservice.apiclient.FileservicesClient;
import com.siemens.mindsphere.sdk.iot.fileservice.model.FileReaderResponse;
import com.siemens.mindsphere.sdk.iot.timeseries.apiclient.TimeseriesClient;
import com.siemens.mindsphere.sdk.iot.timeseries.model.TimeseriesData;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class MindsphereServiceClient {
	
	
	//variabili standard da impostare DEPRECATE
	String entity = "";
	String propertysetname = "";
	String limit = "";
	String select = "";
	private static String URL_TOKEN = "a930a23f-7838-4c00-b67f-eb21d3531d00";
	
	public static int debug = 0;
	
	/*
	 * DEPRECATE
	 */
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
	/*
	 * DEPRECATE
	 */
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
	/*
	 * DEPRECATE
	 */
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
	/*
	 * DEPRECATE
	 */
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
	/*
	 * DEPRECATE
	 */
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
	/*
	 * DEPRECATE
	 */
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
	/*
	 * DEPRECATE
	 */
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
	/*
	 * DEPRECATE
	 */
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
				temp.setErrorCode(responseArray.getJSONObject(i).getString("CodeStop"));
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
	/*
	 * DEPRECATE
	 */
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
				temp.setErrorCode(responseArray.getJSONObject(i).getString("CodeStop"));
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
	/*
	 * DEPRECATE
	 */
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
	/*
	 * DEPRECATE
	 */
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
	/*
	 * DEPRECATE
	 */
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
	/*
	 * DEPRECATE
	 */	
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
				temp.setErrorCode(responseArray.getJSONObject(i).getString("CodeStop"));
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
	 * Tutte le funzioni definte di seguito sono state sviluppate e utilizzate a partire dal 27/03/2019.
	 * Esse forniscono tutti gli strumenti per reperire e analizzare le informazioni da MS e renderle disponibili sul controller delle pagine.
	 * 
	 * Nello specifico tutte le funzioni sono usate nella pagina pricipale di visualizzazione.
	 *  
	 */
	
	
	/* Nome:				listMindsphere
	 * 
	 * Parametri attuali:	String date,
	 * 						String credential_id,
	 * 						String table_name,
	 * 						int max_visual, 
	 * 						String auth
	 * 
	 * Tipo di ritorno:		List<TimeseriesData>
	 * 
	 * Descrizione: 		Questa funzione reperisce la lista da MS e la rende utilizzabile all'interno della logica di progetto.
	 * 
	 * N.B					Attenzione alla parte di autenticazione, essa viene definita in due modi differenti:
	 * 						1) autenticazione tramite client secret e service credentials
	 * 						2) autenticazione tramite login in base ai previlegi e al tipo di account utilizzato al momento del login
	 * 						
	 * 						solo uno di questi due metodi deve essere utilizzato per il corretto funzionamento, nello specifico la (1) può essere utilizzata in fase di testing
	 * 						mentre la (2) deve essere usata obbligatoriamente nel normale utilizzo.
	 * 						
	 */
	public static List<TimeseriesData> listMindsphere (String date, String credential_id, String table_name, int max_visual, String auth) throws MindsphereException, IOException{
			
		//dates conterrà due stringhe una per la data iniziale e una per la data finale
		String dates[] = new String[2];
		
		//System.out.println("ora che arriva dalla form:"+ date);
		
		
		//Trasformazione della stringa date in formto MS-Like
		if(date.substring(4, 5).equals("-")) {
			dates[0] = date.substring(0, 24);
			dates[1] = date.substring(25, 49);
		}else {
			dates = DateProp.toMindSphereFormat(date);
		}
		
		//System.out.println("ora inizio:"+ dates[0]);
		//System.out.println("ora fine: "+ dates[1]);

		//Parte dell'autenticazione a MS
		 MindsphereCredentials credentials;
		
		if(debug == 1) {
		   credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();

		} else {
			credentials = MindsphereCredentials.builder().authorization(auth).build();
		}
		
	    RestClientConfig config = RestClientConfig.builder().build();
	    
	    TimeseriesClient timeseriesClient = TimeseriesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();

	    List<TimeseriesData> timeseriesList = null;
	    
	    try {
	    	
	    	//Chiamata dall'SDK per ricevere la lista dal Cloud DB
	    	timeseriesList = timeseriesClient.getTimeseries(credential_id , table_name, dates[0], dates[1], max_visual, null);
	    	
	    	//Test da abilitare nel caso in cui bisogna controllare la lista
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
	
		
	/* Nome:				listAndInfoMindsphere
	 * 
	 * Parametri attuali:	List<TimeseriesData> timeseries_list,
	 * 						String[] from_to,				
	 * 
	 * Tipo di ritorno:		ListAndInfo
	 * 
	 * Descrizione: 		Questa funzione viene utilizzata per riempire l'oggetto della classe ListAndInfo, oggetto che oltre alla lista di
	 * 						ritorno da MS contiene anche altre informazioni utili sulla lista.						
	 */
	public static ListAndInfo listAndInfoMindsphere(List<TimeseriesData> timeseries_list, String[] from_to) {
		
		ListAndInfo timeseries_list_info = new ListAndInfo();

		if(!(timeseries_list == null)) {
			
			timeseries_list_info.setTimeseriesList(timeseries_list);
			timeseries_list_info.setLunghezza_lista(timeseries_list.size());
			timeseries_list_info.set_data_iniziale_da_utente(from_to[0]);
			timeseries_list_info.set_data_finale_da_utente(from_to[1]);
			timeseries_list_info.setData_iniziale(timeseries_list.get(0).getTimeString());
			timeseries_list_info.setData_finale(timeseries_list.get(timeseries_list_info.getLunghezza_lista()-1).getTimeString());
	
			
			if(timeseries_list.get(0).getData().get("OEE").hashCode() == 0) {
				timeseries_list_info.setTipo_iniziale("stop");
			} else {
				timeseries_list_info.setTipo_iniziale("run");
			}
			
			if(timeseries_list.get(timeseries_list.size()-1).getData().get("OEE").hashCode() == 0) {
				timeseries_list_info.setTipo_finale("stop");
			} else {
				timeseries_list_info.setTipo_finale("run");
			}
			//Messaggi di test
			
			//System.out.println("");
			//System.out.println("Data iniziale: "+timeseries_list_info.getData_iniziale());
			//System.out.println("Data finale: "+timeseries_list_info.getData_finale());
			//System.out.println("Tipo iniziale: "+timeseries_list_info.getTipo_iniziale());
			//System.out.println("Tipo finale: "+timeseries_list_info.getTipo_finale());
			//System.out.println("Lunghezza lista: "+timeseries_list_info.getLunghezza_lista());
			//System.out.println("");

		}
		return timeseries_list_info;
		
	}
	
	
	/* Nome:				oeeTotalScrapMSApi
	 * 
	 * Parametri attuali:	ListAndInfo timeseries_list_info
	 * 
	 * Tipo di ritorno:		int[]
	 * 
	 * Descrizione: 		Questa funzione restituisce un array composto da 3 interi che contiene i valori inerenti a:
	 * 						1) totale dei pezzi scartati nella timeseries passata
	 * 						2) totale dei pezzi prodotti nella timeseries passata
	 * 						3) media ponderata in base al tempo di funzionamento degli OEE della timeseries passata				
	 */
	public static int[] oeeTotalScrapMSApi(List<TimeseriesData> timeseries_data) throws MindsphereException, IOException{
		
		//Array di interi che conterrà in prima posizione i pezzi scartati [0], in seconda i pezzi prodotti [1] e in terza l'oee medio [2]
		int[] oee_tot_scrap = new int[3];
		
		//Viene tutto inizializzato a 0 in maniera tale da poter restituire un'array coerente nel caso in vui la lista fosse vuota
		int cont_media = 0;
		oee_tot_scrap[0] = 0;
		oee_tot_scrap[1] = 0;
		oee_tot_scrap[2] = 0;
				
		//Qui vengono effettuati i controlli sulla lista contenuta all'interno del paramentro attuale timeseries_list_info
    	if(!(timeseries_data == null)) {
    		//Controllo che la lista contenga almeno 3 elementi 
    		if(timeseries_data.size()>3) {
	    		for (int i = 0; i < timeseries_data.size(); i++){
					if(i == 0) {
						//Controllo sul primo elemento per capire da dove iniziare a calcolare l'oee
						if(timeseries_data.get(0).getData().get("OEE").hashCode() == 0) {
							//Mi sposto in avanti di 3 elementi dato che sono sicuro che il terzo sia il primo run utile
							i = i + 3;
						}else {
							//Mi sposto in avanti di 2 elementi dato che sono sicuro che il terzo sia il primo run utile
							i = i + 2;
						}
					}
					//Riempimento dell'array
					oee_tot_scrap[0] =  oee_tot_scrap[0] + timeseries_data.get(i).getData().get("PezziScartati").hashCode();
					oee_tot_scrap[1] = oee_tot_scrap[1] + timeseries_data.get(i).getData().get("PezziProdotti").hashCode();	
					
					if(timeseries_data.get(i).getData().get("OEE").hashCode() > 0) {
						cont_media = cont_media + timeseries_data.get(i).getData().get("RunTime").hashCode() + timeseries_data.get(i).getData().get("StopTime").hashCode();
						oee_tot_scrap[2] = oee_tot_scrap[2] + timeseries_data.get(i).getData().get("OEE").hashCode() * (timeseries_data.get(i).getData().get("RunTime").hashCode() + timeseries_data.get(i).getData().get("StopTime").hashCode());	
					}
				}
	    		oee_tot_scrap[2] = oee_tot_scrap[2]/cont_media;
    		}
    	}
    	return oee_tot_scrap;
	}
	
	
	/* Nome:				intermediateOees
	 * 
	 * Parametri attuali:	ListAndInfo timeseries_list_info, 
	 * 						IntermediateOeesModel intermediate_oees
	 * 
	 * Tipo di ritorno:		void
	 * 
	 * Descrizione: 		Questa funzione riceve l'oggetto di tipo IntermediateOeesModel che al suo interno contiene due liste di stringhe
	 * 						una che contiene gli OEE di ogni frame e l'altra che contiene la stringa della durata del frame, non restituisce nulla perchè
	 * 						popola direttamente l'oggetto che riceve
	 */	
	public static void intermediateOees(ListAndInfo timeseries_list_info, IntermediateOeesModel intermediate_oees) {
		
		List <TimeseriesData> list = timeseries_list_info.getTimeseriesList();
		
		ArrayList<Integer> oee_arr = new ArrayList<Integer>();
		ArrayList<String> oee_names_arr = new ArrayList<String>();
		
		int cont_index = 0;
		
		if(!(list == null)) {
			if(list.size()<4) {	
				oee_arr.add(0, 0);
				oee_names_arr.add(0, "null");	
			}else{
				for (int i = 0; i < list.size(); i++){	
					if(i == 0) {
						if(list.get(0).getData().get("OEE").hashCode() == 0) {
							//vado avanti di 3 perchè è il primo run utile		
							i = i + 3;
						}else {	
							//vado avanti di 2 perchè è il primo run utile
							i = i + 2;
						}
					}	
					if(list.get(i).getData().get("OEE").hashCode()>0) {
							oee_arr.add(cont_index, list.get(i).getData().get("OEE").hashCode());
							oee_names_arr.add(cont_index, list.get(i-2).getTimeString() +" - "+list.get(i).getTimeString());
							cont_index++;
					}
				}
			}	
		}else{
			oee_arr.add(0, 0);
			oee_names_arr.add(0, "null");
		}
		
		intermediate_oees.setOeeArray(oee_arr);
		intermediate_oees.setOeeNamesArr(oee_names_arr);
	}
	
	
	/* Nome:				getStopCodeFromList
	 * 
	 * Parametri attuali:	ListAndInfo timeseries_list_info, 
	 * 						Map<String, String> allarmi_da_file
	 * 
	 * Tipo di ritorno:		ErrorDataModel[]
	 * 
	 * Descrizione: 		Riceve come di consueto l'oggetto di tipo ListAndInfo e inoltre riceve una mappa che è
	 * 						popolata con le stringhe degli stop di errore (valore) e con il rispettivo codice (chiave).
	 */	
	public static ErrorDataModel[] getStopCodeFromList(ListAndInfo timeseries_list_info, Map<String, String> allarmi_da_file) {
		
		int grandezza_array_errori = 0;
		
		List<TimeseriesData> list = timeseries_list_info.getTimeseriesList();
		
		for (int i = 0; i < timeseries_list_info.getLunghezza_lista(); i++){
			if((list.get(i).getData().get("OEE").hashCode() == 0)) {
				grandezza_array_errori++;				
			}
		}
		
		//Deve essere inizializzato dopo che si è contato il numero di errori contenuti nel json di ritorno
		ErrorDataModel[] error_code ;
		
		if(grandezza_array_errori == 0) {
			error_code  = new ErrorDataModel[0]; 
		} else {
			if(timeseries_list_info.getTipo_iniziale().equals("stop")) {
				error_code  = new ErrorDataModel[grandezza_array_errori-1]; 
			} else {
				error_code  = new ErrorDataModel[grandezza_array_errori]; 
			}
		}
		
		if(!(list == null)) {
			if(list.size()>3) {

				for (int i = 0, j = 0; i < timeseries_list_info.getLunghezza_lista(); i++){
					if(i == 0) {
						if(list.get(0).getData().get("OEE").hashCode() == 0) {
							//vado avanti di 2 perchè è il primo run utile		
							i = i + 2;
						}else{
							//vado avanti di 1 perchè è il primo run utile
							i = i + 1;
						}
					}	
					
					if(list.get(i).getData().get("OEE").hashCode() == 0) {
						ErrorDataModel temp = new ErrorDataModel();
						if(allarmi_da_file.isEmpty()) {
							temp.setErrorCode(list.get(i).getData().get("CodeStop").toString());
						} else {
							temp.setErrorCode(list.get(i).getData().get("CodeStop").toString()+ ": " + (allarmi_da_file.get(list.get(i).getData().get("CodeStop").toString())));
						}
						temp.setTimestamp(DateProp.fromMSFormatToUser(list.get(i).getTimeString()));
						error_code[j] = temp;
						j++;
					}
				}
			}
		}
		return error_code;
	}
	
	
	/* Nome:				checkNewDataAlert
	 * 
	 * Parametri attuali:	ListAndInfo timeseries_list_info, 
	 * 						String asset, 
	 * 						String auth
	 * 
	 * Tipo di ritorno:		String
	 * 
	 * Descrizione: 		Questa funzione viene utilizzata per conferire una nuova query all'utente, perchè dato che l'utente è libero di scegliere 
	 * 						data e ora di inizio e fine monitoraggio, può capitare che l'utente possa spezzare un frame e quindi non reperire il 
	 * 						giusto periodo di campionamento selezionato, per cui gli viene proposta la possibilità di ridefine i bound della ricerca
	 * 						per poi poter inglobare eventuali frame spezzati precedenti o successivi al periodo selezionato.
	 * 
	 * N.B					Attenzione alla parte di autenticazione, essa viene definita in due modi differenti:
	 * 						1) autenticazione tramite client secret e service credentials
	 * 						2) autenticazione tramite login in base ai previlegi e al tipo di account utilizzato al momento del login
	 * 						
	 * 						solo uno di questi due metodi deve essere utilizzato per il corretto funzionamento, nello specifico la (1) può essere utilizzata in fase di testing
	 * 						mentre la (2) deve essere usata obbligatoriamente nel normale utilizzo.
	 */	
	public static String checkNewDataAlert(ListAndInfo timeseries_list_info, String asset, String auth) throws java.text.ParseException, MindsphereException {
			
			
		//Flag di inizio 
		int initflag = 0;
		String newInit, goodInit = "";
		
		
		//Flag di fine
		int endflag = 0;
		String newEnd, goodEnd = "";
		
		if(timeseries_list_info.getTimeseriesList() == null) {
			return "correct_data";
		} else {
			
			//Parte dell'autenticazione a MS
			 MindsphereCredentials credentials;
			
			if(debug == 1) {
			   credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();

			} else {
				credentials = MindsphereCredentials.builder().authorization(auth).build();
			}
			
			RestClientConfig config = RestClientConfig.builder().build();
			    
			TimeseriesClient timeseriesClient = TimeseriesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();
			
			List<TimeseriesData> init_date = null;
			List<TimeseriesData> final_date = null;
					
			if(timeseries_list_info.getTipo_iniziale().equals("stop")) {
				//Controlla se il primo elemento è uno stop. (è uno stop se ha oee = 0)
				//Se si, chiedi di partire dal run precedente a calcolare il tutto e imposta l'initflag a 1
				
				initflag = 1;				
			}
			
			
			if(timeseries_list_info.getTipo_finale().equals("stop")) {
				//if controlla se l'ultimo elemento è un stop. (è uno stop se ha oee = 0)
				//se si, imposta l' endflag di fine a 1 e viene richiesto se inglobare anche lui
		
				endflag = 1;		
			}
			
			try {
				if(initflag == 1) {
					//Calcolo il range del giorno precedente e vado a prendere l'ultimo elemento il quale siamo sicuri che sia un run e prendo il tempo di questo run
					//il tempo del nuovo inizio lo salvo in una variabile d'appoggio
					
					newInit = DateProp.previousDay(timeseries_list_info.getData_inizialeDaUtente());
					
					init_date = timeseriesClient.getTimeseries(asset , "FromRunToRun", newInit, timeseries_list_info.getData_inizialeDaUtente(), 2000, "OEE");
					
					int index;
					
					index = init_date.size();
					//System.out.println("grandezza lista di ritorno dalla prima chiamata: "+index);

					if(index>1) {
						index = index -2;
						goodInit =  goodInit + init_date.get(index).getTimeString();
					}else{
					goodInit =  goodInit + timeseries_list_info.getData_inizialeDaUtente();
					}
					//System.out.println("nuovo elemento di partenza: "+goodInit);
				}
				
				if(endflag == 1) {
					//calcolo il range del giorno successivo e prendo il primo valore che possiedo, e sono sicuro che sia un valore di run
					// il tempo della nuova fine lo salvo in una variabile d'appoggio

					newEnd = DateProp.nextDay(timeseries_list_info.getData_finaleDaUtente());
					
					final_date = timeseriesClient.getTimeseries(asset , "FromRunToRun", timeseries_list_info.getData_finaleDaUtente(), newEnd, 2, "OEE");
					
					if((final_date.size() == 0) || (final_date.size() == 1)) {
						goodEnd = goodEnd + timeseries_list_info.getData_finaleDaUtente();
						endflag = 0;
					} else {
						goodEnd = goodEnd +  final_date.get(0).getTimeString();	
					}
					//System.out.println("nuovo elemento di arrivo: "+goodEnd);
				}
			} catch (MindsphereException e) {
		    	System.out.println(e);
		    	System.out.println(e.getErrorMessage());
		    	System.out.println(e.getHttpStatus());
		    }			
			
		    if((initflag == 1) && (endflag == 1)) {
		    	/*
		    	if(initflag == 1) {
		    		System.out.println("Il primo valore è uno stop");
		    	} else {
		    		System.out.println("Il primo valore è uno run");
		    	}
		    	
		    	if(endflag == 1) {
		    		System.out.println("L'ultimo valore è un run");
		    	} else {
		    		System.out.println("L'ultimo valore è uno stop");
		    	}
		    	*/
		    	
			    return DateProp.setMindshphereDate(goodInit) + "+" + DateProp.setMindshphereDate(goodEnd);
		    }
		    if((initflag == 1) && (endflag == 0)) {
		    	/*
		    	if(initflag == 1) {
		    		System.out.println("Il primo valore è uno stop");
		    	} else {
		    		System.out.println("Il primo valore è uno run");
		    	}
		    	
		    	if(endflag == 1) {
		    		System.out.println("L'ultimo valore è un run");
		    	} else {
		    		System.out.println("L'ultimo valore è uno stop");
		    	}
		    	*/
		    	
		    	 return DateProp.setMindshphereDate(goodInit) + "+" + timeseries_list_info.getData_finaleDaUtente();
		    }
		    if((initflag == 0) && (endflag == 1)) {
		    	/*
		    	if(initflag == 1) {
		    		System.out.println("Il primo valore è uno stop");
		    	} else {
		    		System.out.println("Il primo valore è uno run");
		    	}
		    	
		    	if(endflag == 1) {
		    		System.out.println("L'ultimo valore è un run");
		    	} else {
		    		System.out.println("L'ultimo valore è uno stop");
		    	}
		    	*/

		    	 return timeseries_list_info.getData_inizialeDaUtente() + "+" + DateProp.setMindshphereDate(goodEnd);
		    } 
		    if ((initflag == 0) && (endflag == 0)){
		    	/*
		    	if(initflag == 1) {
		    		System.out.println("Il primo valore è uno stop");
		    	} else {
		    		System.out.println("Il primo valore è uno run");
		    	}
		    	
		    	if(endflag == 1) {
		    		System.out.println("L'ultimo valore è un run");
		    	} else {
		    		System.out.println("L'ultimo valore è uno stop");
		    	}
		    	*/
		    	return "correct_data";
		    }else{
		    	return "correct_data";
		    }
		}
	}

	
	/* Nome:				reciveAsset
	 * 
	 * Parametri attuali:	String auth
	 * 
	 * Tipo di ritorno:		ArrayList<String>[]
	 * 
	 * Descrizione: 		Questa funziona restituisce un'array di 2 ArrayList che contengono uno i valori degli assets id e l'altro i nomi degli assets id inerenti
	 * 						solamente agli assets che sono visualizzabili dall'utente che si è loggato tramite credenziali Siemens.
	 * 
	 * N.B					Attenzione alla parte di autenticazione, essa viene definita in due modi differenti:
	 * 						1) autenticazione tramite client secret e service credentials
	 * 						2) autenticazione tramite login in base ai previlegi e al tipo di account utilizzato al momento del login
	 * 						
	 * 						solo uno di questi due metodi deve essere utilizzato per il corretto funzionamento, nello specifico la (1) può essere utilizzata in fase di testing
	 * 						mentre la (2) deve essere usata obbligatoriamente nel normale utilizzo.
	 */	
	public static ArrayList<String>[] reciveAsset(String auth) {
		
		ArrayList<String> array_asset [] = new ArrayList[2];
		
		ArrayList<String> list_asset_id = new ArrayList<String>();
		
		ArrayList<String> list_asset_name = new ArrayList<String>();
		
		try {			
					
			//Parte dell'autenticazione a MS
			 MindsphereCredentials credentials;
			
			if(debug == 1) {
			   credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();

			} else {
				credentials = MindsphereCredentials.builder().authorization(auth).build();
			}
			
			RestClientConfig config = RestClientConfig.builder().build();
		    
			AssetClient  assetClient = AssetClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();
		    
		    Assets assets = null;
		    
		    List<AssetResource> asset_resource = null;
	    
			assets = assetClient.getAssets();
			asset_resource = assets.getEmbedded().getAssets();
			
			if(asset_resource.size() == 0) {
				list_asset_id.add("empity list");
				list_asset_name.add("empity list");
			}
			
			/*
			 * ATTENZIONE l'attributo "ccs_type" è fondamentale perchè definisce che il tipo di asset registrato su MS "DEVE" essere visualizzato
			 * dall'utente perchè è un asset creato per immagazzinare dati
			 * Nel caso in cui questo attributo mancasse sulla tabella definida su MS, le informazioni inerenti a quella tabella non vengono visualizzate
			 */
			
			for(int i = 0; i < asset_resource.size(); i++) {
				if(!(asset_resource.get(i).getVariables().isEmpty())) {
					if(asset_resource.get(i).getVariables().get(0).getName().equals("ccs_type")) {
						list_asset_id.add(asset_resource.get(i).getAssetId());
						list_asset_name.add(asset_resource.get(i).getName());
					}
				}
			}
			 
			 array_asset [0] = list_asset_id;
			 array_asset [1] = list_asset_name;
 
	    }catch (MindsphereException e) {
	    	System.out.println(e);
	    	System.out.println(e.getErrorMessage());
	    	System.out.println(e.getHttpStatus());
	    	//System.out.println("errore nel collegamento");
	    	
	    	list_asset_id.add("empity list");
   		 	list_asset_name.add("empity list");
	    	array_asset [0] = list_asset_id;
	    	array_asset [1] = list_asset_name;
	    	
	    	return array_asset;
	    }
	    
	    array_asset [0] = list_asset_id;
   	 	array_asset [1] = list_asset_name;
	    
   	 	//System.out.println("collegamento stabilito");    
	    return array_asset;		
	}
	
	
	/* Nome:				ListaAllarmi
	 * 
	 * Parametri attuali:	String auth, 
	 * 						String asset
	 * 
	 * Tipo di ritorno:		Map<String, String>
	 * 
	 * Descrizione: 		Questa funzione genera una mappa che contiene come chiave il codice di errore e come valore il testo inerente al codice di errore,
	 * 						Questa funzione è legata strettamente ad un file che deve essere contenuto all'interno dell'asset della macchina dal nome AlarmList.txt, che contiene la lista
	 * 						di tutti gli allarmi della macchina legata all'asset.
	 * 
	 * Tipologia del file:  AlarmList.txt
	 * 
	 * 						"#CODICE STOP";"NOME DELLO STOP"
	 * 						"#CODICE STOP";"NOME DELLO STOP"
	 * 						"#CODICE STOP";"NOME DELLO STOP"
	 * 						"#CODICE STOP";"NOME DELLO STOP"
	 * 						"#CODICE STOP";"NOME DELLO STOP"
	 *	
	 * N.B					Attenzione alla parte di autenticazione, essa viene definita in due modi differenti:
	 * 						1) autenticazione tramite client secret e service credentials
	 * 						2) autenticazione tramite login in base ai previlegi e al tipo di account utilizzato al momento del login
	 * 						
	 * 						solo uno di questi due metodi deve essere utilizzato per il corretto funzionamento, nello specifico la (1) può essere utilizzata in fase di testing
	 * 						mentre la (2) deve essere usata obbligatoriamente nel normale utilizzo.
	 */	
	public static Map<String, String> ListaAllarmi(String auth, String asset){
		
		Map<String, String> allarmi = new HashMap<String, String>();
		
		String alarm_list = "";
		//Parte dell'autenticazione a MS
		 MindsphereCredentials credentials;
		
		if(debug == 1) {
		   credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();

		} else {
			credentials = MindsphereCredentials.builder().authorization(auth).build();
		}
		
		RestClientConfig config = RestClientConfig.builder().build();

		
		FileservicesClient fileservicesClient = FileservicesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();

		FileReaderResponse fileReaderResponse;
		
		try {
			//Il nome di questo file deve coincidere strettamente con il file caricato su MS
		    fileReaderResponse = fileservicesClient.readFile(asset, "AlarmList.txt"); 
		    
		    alarm_list = alarm_list + fileReaderResponse.getFileContent();  
		    
		    String[] pairs = alarm_list.split("\n");
		    for (int i=0;i<pairs.length;i++) {
		        String pair = pairs[i];
		        String[] keyValue = pair.split(";");
		        allarmi.put(keyValue[0], keyValue[1]);
		    }
	    
		} catch (MindsphereException e) {
			System.out.println(e);
			System.out.println("File non letto");
			return allarmi;
		}
		return allarmi;
	}
	
	
	/* Nome:				getRawData
	 * 
	 * Parametri attuali:	String date, 
	 * 						String credentialId, 
	 * 						String tableName, 
	 * 						int max_visual, 
	 * 						String auth
	 * 
	 * Tipo di ritorno:		RawDataModel
	 * 
	 * Descrizione: 		DEPRECATA, FUNZIONE DI TEST
	 * 
	 * N.B					Attenzione alla parte di autenticazione, essa viene definita in due modi differenti:
	 * 						1) autenticazione tramite client secret e service credentials
	 * 						2) autenticazione tramite login in base ai previlegi e al tipo di account utilizzato al momento del login
	 * 						
	 * 						solo uno di questi due metodi deve essere utilizzato per il corretto funzionamento, nello specifico la (1) può essere utilizzata in fase di testing
	 * 						mentre la (2) deve essere usata obbligatoriamente nel normale utilizzo.
	 */	
	public static RawDataModel getRawData(String date, String credentialId, String tableName, int max_visual, String auth) {
		
		RawDataModel raw_data = new RawDataModel();
		
		//trasformo la data in un formato mindsphere like
		String dates[] = new String[2];
		
		System.out.println("ora che arriva dalla form:"+ date);
		
		if(date.substring(4, 5).equals("-")) {
			dates[0] = date.substring(0, 24);
			dates[1] = date.substring(25, 49);
		}else {
			dates = DateProp.toMindSphereFormat(date);
		}
		
		//dates = DateProp.toMindSphereFormat(date);
		
		System.out.println("ora inizio:"+ dates[0]);
		System.out.println("ora fine: "+ dates[1]);

		
		//Parte dell'autenticazione a MS
		 MindsphereCredentials credentials;
		
		if(debug == 1) {
		   credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();

		} else {
			credentials = MindsphereCredentials.builder().authorization(auth).build();
		}
		
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
	    }
	    
	    System.out.println("collegamento stabilito");    
	
	    
	    
		return raw_data;
	}
	
	
	/* Nome:				ListaMateriali
	 * 
	 * Parametri attuali:	String date, 
	 * 						String auth, 
	 * 						String asset
	 * 
	 * Tipo di ritorno:		RawDataModel[]
	 * 
	 * Descrizione: 		Questa è la funzione che gestisce la parte del consumo di materie prime, nello specifico è legata ad un file registrato su MS all'interno dell'asset
	 * 						il file deve avere il seguente nome: "raw_materials.txt".
	 * 						
	 * 						Essa restituisce un array di tipo RawDataModel che contiene i valori dei materiali usati e scartati
	 * 						la funzione effettua la prima chiamata a MS che torna la lista delle materie prime consumate per svolgitore/dosatore,
	 * 						successivamente effettua la chiamata per prendere il file raw_materials che contiene tutte le materie prime utilizzate sui rispettivi svolgitori/dosatori
	 * 						infine cerco la relazione tra i dati che ricevo dalla lista e i dati che riprendo dal file.
	 * 
	 * Tipologia del file:  AlarmList.txt
	 * 
	 * 						"Raw Material_1";"unwinding_code_1";"unwinding_code_2";"unwinding_code_3";"unwinding_code_4"
	 * 						"Raw Material_2";"unwinding_code_5";"unwinding_code_6"
	 * 						"Raw Material_3";"unwinding_code_7";"unwinding_code_8";"unwinding_code_9"
	 * 						"Raw Material_4";"unwinding_code_10";"unwinding_code_11"
	 * 						"Raw Material_5";"unwinding_code_12"
	 * 
	 * N.B					Attenzione alla parte di autenticazione, essa viene definita in due modi differenti:
	 * 						1) autenticazione tramite client secret e service credentials
	 * 						2) autenticazione tramite login in base ai previlegi e al tipo di account utilizzato al momento del login 
	 * 						
	 * 						solo uno di questi due metodi deve essere utilizzato per il corretto funzionamento, nello specifico la (1) può essere utilizzata in fase di testing
	 * 						mentre la (2) deve essere usata obbligatoriamente nel normale utilizzo.
	 */	
	public static RawDataModel[] ListaMateriali (String date, String auth, String asset) throws MindsphereException, IOException{
		
		//Modello di ritorno
		RawDataModel[] return_model;
		
		//Mapping tra svolgitore e materia prima
		int[] svol_dis; 
		int len_svol_dis = 0;
		
		//Hash map tra codice svolgitore/dispenser e tipo di materia prima
		HashMap<String, Integer> hash_map = new HashMap<String, Integer>();

		//Chiamata per la ricezione della lista dei raw material dalla tabella RawMaterials
		List<TimeseriesData> timeseriesList_raw_materials = listMindsphere(date, asset, "RawMaterialsData", 2000, auth);
		
		List<String[]> liste_materiali = new ArrayList<String[]>();
		
		String lista_materiali_svolgitori = "";
		
		//Parte dell'autenticazione a MS
		 MindsphereCredentials credentials;
		
		if(debug == 1) {
		   credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();

		} else {
			credentials = MindsphereCredentials.builder().authorization(auth).build();
		}
		
		RestClientConfig config = RestClientConfig.builder().build();
		
		FileservicesClient fileservicesClient = FileservicesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();

		FileReaderResponse fileReaderResponse;
		
		try {
			
		    fileReaderResponse = fileservicesClient.readFile(asset, "raw_materials.txt"); 
		    
		    lista_materiali_svolgitori = lista_materiali_svolgitori + fileReaderResponse.getFileContent();  
		    
		    //Split per riga del file contenente le materie prime e gli svolgitori associati
		    String[] pairs = lista_materiali_svolgitori.split("\n");
		    
		    return_model = new RawDataModel[pairs.length];
		    
		    //Split per colonna ogni riga
		    for (int i=0;i<pairs.length;i++) { 			    	
		    	String[] raw = pairs[i].split(";");    	
		    	liste_materiali.add(raw);
		    }
		    
		    for(int i=0;i<liste_materiali.size();i++) {		    	
		    	len_svol_dis += liste_materiali.get(i).length -1;
		       	RawDataModel info_raw = new RawDataModel(); 	
		    	info_raw.setName(liste_materiali.get(i)[0]);
		    	return_model[i] = info_raw;
		    	//System.out.println("Lista delle tipologie di materiali" + liste_materiali.get(i)[0]);
		    }
		    
		    svol_dis = new int[len_svol_dis];
				    
		    int k=0;
	    	for(int i=0; i<liste_materiali.size(); i++) {
		    	for(int j=1; j<liste_materiali.get(i).length; j++) {
		    		svol_dis[k] = i;
		    		hash_map.put(liste_materiali.get(i)[j], k);
		    		k++;
		    	}
		    }
		    
		    
		//ERRORE!
	    	if(timeseriesList_raw_materials == null) {
	    		System.out.println("non ho letto");
				return return_model;
	    	}else {
	    		if(!(timeseriesList_raw_materials.isEmpty())) {
	    			for(int i=0; i<timeseriesList_raw_materials.size(); i++) {
			    		return_model[svol_dis[hash_map.get(timeseriesList_raw_materials.get(i).getData().get("CodiceSvolgitore").toString())]].addMaterialeConsumato(timeseriesList_raw_materials.get(i).getData().get("MaterialeConsumato").hashCode());
			    		return_model[svol_dis[hash_map.get(timeseriesList_raw_materials.get(i).getData().get("CodiceSvolgitore").toString())]].addMaterialeScartato(timeseriesList_raw_materials.get(i).getData().get("MaterialeSprecato").hashCode());
			    	}	
	    		}		
	    	}	    		    
		}catch (MindsphereException e) {
		    // Exception handling
			System.out.println(e);
			System.out.println(e.getErrorMessage());
			System.out.println("non ho letto");
		    return_model = new RawDataModel[1];
			return return_model;
		}
		System.out.println("ho letto");
		return return_model;
	}
	
	
	//*** LOGICA DI GESTIONE DELLA NUOVA SERVLET PER LA COSTRUZIONE DELLA PAGINA DI COMPARAZIONE ***//
	public static CompareModel[] compareList(String[] assets_name, String[] assets_value, String auth, String date) throws MindsphereException, IOException{
		
		
		CompareModel[] return_model = new CompareModel[assets_name.length];
		
		
		//ates conterrà due stringhe una per la data iniziale e una per la data finale
		String dates[] = new String[2];		
		
		//Trasformazione della stringa date in formto MS-Like
		if(date.substring(4, 5).equals("-")) {
			dates[0] = date.substring(0, 24);
			dates[1] = date.substring(25, 49);
		}else {
			dates = DateProp.toMindSphereFormat(date);
		}
		
		//Parte dell'autenticazione a MS
		 MindsphereCredentials credentials;
		
		if(debug == 1) {
		   credentials = MindsphereCredentials.builder().clientId("ccsdev-service-credentials").clientSecret("62c6be6e-6a6b-5bf2-eece-f9a98652b127").tenant("ccsdev").build();

		} else {
			credentials = MindsphereCredentials.builder().authorization(auth).build();
		}

	    //MindsphereCredentials credentials = MindsphereCredentials.builder().authorization(auth).build();
	   
	    RestClientConfig config = RestClientConfig.builder().build();
	    
	    TimeseriesClient timeseriesClient = TimeseriesClient.builder().mindsphereCredentials(credentials).restClientConfig(config).build();
	    
	    for (int i=0; i<assets_name.length; i++) {
	    	
	    	CompareModel model = new CompareModel();
			
	    	//Array di interi che conterrà in prima posizione i pezzi scartati [0], in seconda i pezzi prodotti [1] e in terza l'oee medio [2]
			int[] oeeTotScrap = new int[3];

		    List<TimeseriesData> timeseriesList = null;
		    
		    try {
		    	
		    	//Chiamata dall'SDK per ricevere la lista dal Cloud DB
		    	timeseriesList = timeseriesClient.getTimeseries(assets_value[i] , "FromRunToRun", dates[0], dates[1], 2000, null);
		    			    	
		    	//Test da abilitare nel caso in cui bisogna controllare la lista
		    	if(!(timeseriesList == null)) {
		    		
		    		oeeTotScrap = MindsphereServiceClient.oeeTotalScrapMSApi(timeseriesList);
		    		
		    		model.setName(assets_name[i]);
		    		model.setAsset(assets_value[i]);
		    		model.setScrapPieces(oeeTotScrap[0]);
		    		model.setTotPieces(oeeTotScrap[1]);
		    		model.setOee(oeeTotScrap[2]);
		    		
		    		return_model[i] = model;
		    		
		    		System.out.println("dati ricevuti con successo (lista non vuota)");
		    	} else {
		    		
		    		model.setName(assets_name[i]);
		    		model.setAsset(assets_value[i]);
		    		model.setScrapPieces(0);
		    		model.setTotPieces(0);
		    		model.setOee(0);
		    		
		    		return_model[i] = model;
		    				
		    		System.out.println("dati ricevuti (lista vuota)");
		    	}
			    System.out.println("collegamento stabilito");    
	
		    } catch (MindsphereException e) {
		    	System.out.println(e);
		    	System.out.println(e.getErrorMessage());
		    	System.out.println(e.getHttpStatus());
		    	System.out.println("errore nel collegamento, zona delle chiamate multiple seconda servlet");
		    	
		    	return_model[i] = model;
		    }
		    
		
	    };
		
	    System.out.println("Lunghezza array di ritorno: " + return_model.length);
		
		return return_model;
	}

}