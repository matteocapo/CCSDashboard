package com.ccs.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccs.model.ErrorDataModel;
import com.ccs.model.IntermediateOeesModel;
import com.ccs.model.ListAndInfo;
import com.ccs.provider.MindsphereServiceClient;
import com.ccs.util.DateProp;
import com.siemens.mindsphere.sdk.core.exception.MindsphereException;
import com.siemens.mindsphere.sdk.iot.timeseries.model.TimeseriesData;

@Controller
public class CcsController {
	
	@RequestMapping("/timetest")
	public ModelAndView timetest() {

		ModelAndView mv = new ModelAndView("timetest");
		
		return mv;
		
	}
	
	@RequestMapping("/eonasdan_date")
	public ModelAndView eonasdan_date() {

		ModelAndView mv = new ModelAndView("eonasdan_date");
		
		return mv;
		
	}
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public @ResponseBody ModelAndView homepagetimepageview(HttpServletRequest request) {
		
		//mappa da inviare alla pagina jsp
		Map<String, String> assets = new HashMap<String, String>();

		//array che contiene gli asset id e i nomi degli asset che l'utente può visualizzare in [1] ci sono i nomi, in [0] ci sono gli id
		ArrayList<String> array_asset [] = new ArrayList[2];
		
		String authorization = "";
		//String asset = "";
	
	    authorization = authorization + request.getHeader("authorization");

		ModelAndView mv = new ModelAndView("indexprovatime");
		
		//mv.addObject("auth", authorization);
		
		//chiamata alla funzione per il reperimento degli asset recieveAsset(String authorization){List<String>}
		array_asset =  MindsphereServiceClient.reciveAsset(authorization);
		
		for (int i = 0; i < array_asset[0].size(); i++) {
			assets.put(array_asset[1].get(i), array_asset[0].get(i));
		}
		//mv.addObject("asset_size", list_asset_id.size());
		
		//mv.addObject("asset_id", array_asset[0]);
		//mv.addObject("asset_name", array_asset[1]);
		
		mv.addObject("assets", assets);
		
		return mv;
		
	}

	@RequestMapping(path = "/indexprovatime", method = RequestMethod.GET)
	public @ResponseBody ModelAndView timepageview(HttpServletRequest request) {
		
		Enumeration<String> headerNames = request.getHeaderNames();
		String authorization = "";
		String asset = "";
		
	    while (headerNames.hasMoreElements()) {
	      String header = headerNames.nextElement();
	      //System.out.println("header " + header +  " " + request.getHeader(header));
	    }
	    authorization = authorization + request.getHeader("authorization");

		ModelAndView mv = new ModelAndView("indexprovatime");
		
		//mv.addObject("auth", authorization);
		
		//chiamata alla funzione per il reperimento degli asset recieveAsset(String authorization){List<String>}
		asset = asset + MindsphereServiceClient.reciveAsset(authorization);
		
		mv.addObject("asset", asset);
		
		return mv;
		
	}
	
	@RequestMapping("/frameinfo")
	public ModelAndView motorpageview(	@RequestParam(value = "from", 	required = false, defaultValue = "from") String from
									 ,	@RequestParam(value = "to", 	required = false, defaultValue = "to") 	 String to
					) throws IOException, MindsphereException, ParseException {
		
		//Getting frame information about motors and sensors
		
		
		
		ModelAndView mv = new ModelAndView("frameInfo");
		return mv;
	}
	
	
	@RequestMapping(path = "/indexprova", method = RequestMethod.GET)
	public @ResponseBody ModelAndView homepageview(@RequestParam(value = "datetimes", required = false, defaultValue = "World") String date, 
												   @RequestParam(value = "asset", required = false, defaultValue = "null") String asset,
												   @RequestParam(value = "alert", required = false, defaultValue = "no") String alert,
												   HttpServletRequest request) throws IOException, MindsphereException, ParseException {
		
		
		/* Definizione delle variabili */
		
		Enumeration<String> headerNames = request.getHeaderNames();
		String authorization = "";
	
	    authorization = authorization + request.getHeader("authorization");
				
		List<TimeseriesData> timeseriesList = null;	
		
		ListAndInfo timeseries_list_info;
		
		int[] oeeTotScrap = new int[3];
		
		IntermediateOeesModel intermediateOees = new IntermediateOeesModel();
		ArrayList<Integer> oeeArray=new ArrayList<Integer>();
		ArrayList<String> oeeNamesArr=new ArrayList<String>();
		String[] fromTo = new String[2];
		String testalert;
		String oees_name  = new String();
		String oees_value = new String();
		
		/*
		HttpHeaders headerNames = request.getHeaders();
		while (!(headerNames.isEmpty())) {
			String header = headerNames.getFirst("authorization");
		    System.out.println("header " + header +  " ");
		 }
		 */    

		
		System.out.println("in controller");
		
		if (!date.equals("World")) {
				if(date.substring(4, 5).equals("-")) {
				fromTo[0] = date.substring(0, 24);
				fromTo[1] = date.substring(25, 49);
			}else {
				fromTo = DateProp.toMindSphereFormat(date);
			}
		}

			
		/*nuova gestione della chiamata, 
		 * 1) viene effettuata la chiamata a MS e tramite l'SDK viene ricevuta la lista contenete tutti i valori delle TS nel range selezionato, oppure la lista viene ritornata a null
		 * 2) vengono effettuate le chiamate per la gestione e visualizzazione dei dati di produzione sulla dashboard
		 */
		
		/* Prima chiamata che riceve in input la data (formattata come vuole mindsphere), l'ID univoco della tabella definita su mindsphere, il nome della tabella su mindsphere,
		 *  e il numero massimo di valori che possono tornarne dalla query, in questo caso l'API ha un valore massimo di 2000 risultati di ritorno
		 */
		timeseriesList = MindsphereServiceClient.listMindsphere(date, asset, "FromRunToRun", 2000, authorization);
		
		/* 
		 * con questa funzione reperiamo tutte le informazioni sulla lista per utilizzi futuri
		 */
		timeseries_list_info = MindsphereServiceClient.listAndInfoMindsphere(timeseriesList);

		/* chiamata alla funzione che rende disponibili i dati inerenti all'oee medio, pezzi prodotti e pezzi scartati*/
		oeeTotScrap = MindsphereServiceClient.oeeTotalScrapMSApi(timeseries_list_info);
		
		/*chiamata alla funzione che rende disponibili i dati inerenti agli oee intermedi con lista dei range di funzionamento con rispettivo oee*/
		MindsphereServiceClient.intermediateOeesModifica(timeseries_list_info, intermediateOees);
		
		if(intermediateOees.getOeeArray().size() > 0) {
			oees_name = oees_name.concat("'" + intermediateOees.getOeeNamesArr().get(0) + "'");
			oees_value = oees_value.concat(intermediateOees.getOeeArray().get(0).toString());
			if (intermediateOees.getOeeArray().size() > 1) {
				for(int i = 1; i < intermediateOees.getOeeArray().size(); i++) {
					oees_name = oees_name.concat(", '" + intermediateOees.getOeeNamesArr().get(i) + "'");
					oees_value = oees_value.concat(", " + intermediateOees.getOeeArray().get(i).toString());
				}
			}
		}
		//System.out.println(oees_value);		
		//System.out.println(oees_name);

		//System.out.println("inizio chiamata MS");
		//oeeTotScrap = MindsphereServiceClient.oeeTotalScrapMSApi(date, "8dda19eac02e4eec8489535a5cbaa235", "FromRunToRun", 2000);
		//System.out.println("fine chiamata MS");

	
		//vecchia funzione di prova con json statico
		//oee = MindsphereServiceClient.oeeMediaJson("prova", "prova", oeeArray, oeeNamesArr);
		
		//funzione di prova con json generato tramite richiesta di sessione 
		//oee = MindsphereServiceClient.testUrlDataOee(date);
		
		//vecchia funzione di prova con json statico
		//prodottiescarti = MindsphereServiceClient.prodottiEScartiJson("prova", "prova");
		
		//funzione di prova con json generato tramite richiesta di sessione 
		//prodottiescarti = MindsphereServiceClient.testUrlDataProdottiEScarti(date);	
		
		//test messaggi di errore generato tramite richiesta di sessione
		//ErrorDataModel[] error_code = MindsphereServiceClient.testJsonGetStopCode(date);
		ErrorDataModel[] error_code = MindsphereServiceClient.getStopCodeFromList(timeseries_list_info);
		//test popup
		//testalert = MindsphereServiceClient.checkNewDataAlert(date);
		
		
		//System.out.println("Nuovo range di date da selezionare: " + MindsphereServiceClient.checkNewDataAlert(timeseries_list_info, authorization));
		
		//test utilizzo chiamata developer account
		//String stringa_di_ritorno_chiamata_MS = MindsphereServiceClient.getTimeSeriesAsObject("7cb21d4c9b724be5b38c2c9695d9b3c8", "demobox");
	    //String stringa_di_ritorno_chiamata_MS = MindsphereServiceClient.getTimeSeriesAsObjectTestCloudfoundry();
		
		//MindsphereServiceClient.testApiSelfMade();
		
		//MindsphereServiceClient.dataInfoMs();
		
		//ErrorDataModel[] error_code = MindsphereServiceClient.testGetStopCodeStaticJson(date);
		
		//MindsphereServiceClient.getLargeRangeV1(fromTo);
				
		
		ModelAndView mv = new ModelAndView("indexprova");
		
		//mv.addObject("stringa", stringa_di_ritorno_chiamata_MS);

		/*
		if(oeeArray.size() > 0) {
			oees_name = oees_name.concat("'" + oeeNamesArr.get(0) + "'");
			oees_value = oees_value.concat(oeeArray.get(0).toString());
			if (oeeArray.size() > 1) {
				for(int i = 1; i < oeeArray.size(); i++) {
					oees_name = oees_name.concat(", '" + oeeNamesArr.get(i) + "'");
					oees_value = oees_value.concat(", " + oeeArray.get(i).toString());
				}
			}
		}
		
		*/
		//System.out.println(oees_value);		
		//System.out.println(oees_name);

		//System.out.println("inizio chiamata MS");
		//oeeTotScrap = MindsphereServiceClient.oeeTotalScrapMSApi(date, "8dda19eac02e4eec8489535a5cbaa235", "FromRunToRun", 2000);
		//System.out.println("fine chiamata MS");

		mv.addObject("oees_value", oees_value);
		mv.addObject("oees_name" , oees_name);
		
		// oee
		mv.addObject("oee", oeeTotScrap[2]);

		// scarti e produzioni
		mv.addObject("produzioni", oeeTotScrap[1]);
		mv.addObject("scarti", oeeTotScrap[0]);

		// velocità ultima ora

		mv.addObject("min1", "255");
		mv.addObject("min2", "230");
		mv.addObject("min3", "200");
		mv.addObject("min4", "201");
		mv.addObject("min5", "320");
		mv.addObject("min6", "350");
		
		mv.addObject("date", date);
		
		mv.addObject("error_codice", error_code);

		//mv.addObject("testalert", testalert);
		
		//mv.addObject("auth", authorization);


		return mv;
	}

}
