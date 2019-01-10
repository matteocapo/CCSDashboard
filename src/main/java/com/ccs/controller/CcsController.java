package com.ccs.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ccs.model.ErrorDataModel;
import com.ccs.provider.MindsphereServiceClient;
import com.ccs.util.Date;
import com.siemens.mindsphere.sdk.core.exception.MindsphereException;

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

	@RequestMapping("/indexprovatime")
	public ModelAndView timepageview() {

		ModelAndView mv = new ModelAndView("indexprovatime");
		
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
	
	
	@RequestMapping("/indexprova")
	public ModelAndView homepageview(@RequestParam(value = "datetimes", required = false, defaultValue = "World") String date) throws IOException, MindsphereException, ParseException {
		
		int oee = 0;
		int prodottiescarti[] = new int[2];
		ArrayList<Integer> oeeArray=new ArrayList<Integer>();
		ArrayList<String> oeeNamesArr=new ArrayList<String>();
		String[] fromTo = new String[2];
		String testalert;
		String oees_name  = new String();
		String oees_value = new String();
		
		System.out.println("in controller");
		
		System.out.println(date);

		if (!date.equals("World")) {
			//System.out.println(date);
			//Date.toMindSphereFormat(date);
			if(date.substring(4, 5).equals("-")) {
				fromTo[0] = date.substring(0, 24);
				fromTo[1] = date.substring(25, 49);
			}else {
				fromTo = Date.toMindSphereFormat(date);
			}
		}

		System.out.println(fromTo[0]);
		System.out.println(fromTo[1]);
		
		
		//vecchia funzione di prova con json statico
		oee = MindsphereServiceClient.oeeMediaJson("prova", "prova", oeeArray, oeeNamesArr);
		
		//funzione di prova con json generato tramite richiesta di sessione 
		//oee = MindsphereServiceClient.testUrlDataOee(date);
		
		//vecchia funzione di prova con json statico
		prodottiescarti = MindsphereServiceClient.prodottiEScartiJson("prova", "prova");
		
		//funzione di prova con json generato tramite richiesta di sessione 
		//prodottiescarti = MindsphereServiceClient.testUrlDataProdottiEScarti(date);	
		
		//test messaggi di errore generato tramite richiesta di sessione
		ErrorDataModel[] error_code = MindsphereServiceClient.testJsonGetStopCode(date);
		
		//test popup
		testalert = MindsphereServiceClient.checkNewDataAlert(date);
		
		//test utilizzo chiamata developer account
		//String stringa_di_ritorno_chiamata_MS = MindsphereServiceClient.getTimeSeriesAsObject("7cb21d4c9b724be5b38c2c9695d9b3c8", "demobox");
	    //String stringa_di_ritorno_chiamata_MS = MindsphereServiceClient.getTimeSeriesAsObjectTestCloudfoundry();
		
		//MindsphereServiceClient.testApiSelfMade();
		
		//MindsphereServiceClient.dataInfoMs();
		
		//ErrorDataModel[] error_code = MindsphereServiceClient.testGetStopCodeStaticJson(date);
				
		
		ModelAndView mv = new ModelAndView("indexprova");
		
		//mv.addObject("stringa", stringa_di_ritorno_chiamata_MS);

		// oee
		mv.addObject("oee", oee);
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
		System.out.println(oees_value);
		mv.addObject("oees_value", oees_value);
		mv.addObject("oees_name" , oees_name);

		// scarti e produzioni
		mv.addObject("produzioni", prodottiescarti[1]);
		mv.addObject("scarti", prodottiescarti[0]);

		// velocità ultima ora

		mv.addObject("min1", "255");
		mv.addObject("min2", "230");
		mv.addObject("min3", "200");
		mv.addObject("min4", "201");
		mv.addObject("min5", "320");
		mv.addObject("min6", "350");
		
		mv.addObject("date", date);
		
		mv.addObject("error_codice", error_code);

		mv.addObject("testalert", testalert);

		return mv;
	}

}
