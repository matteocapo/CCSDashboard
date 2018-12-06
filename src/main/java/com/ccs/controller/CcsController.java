package com.ccs.controller;

import java.io.IOException;

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
	
	@RequestMapping("/indexprova")
	public ModelAndView homepageview(@RequestParam(value = "datetimes", required = false, defaultValue = "World") String date) throws IOException, MindsphereException {
		
		int oee = 0;
		int prodottiescarti[] = new int[2];
		
		System.out.println("in controller");

		if (!date.equals("World")) {
			//System.out.println(date);
			Date.toMindSphereFormat(date);
		}
		
		//vecchia funzione di prova con json statico
		//oee = MindsphereServiceClient.oeeMedia("prova", "prova");
		
		//funzione di prova con json generato tramite richiesta di sessione 
		oee = MindsphereServiceClient.testUrlDataOee(date);
		
		//vecchia funzione di prova con json statico
		//prodottiescarti = MindsphereServiceClient.prodottiEScarti("prova", "prova");
		
		//funzione di prova con json generato tramite richiesta di sessione 
		prodottiescarti = MindsphereServiceClient.testUrlDataProdottiEScarti(date);	
		
		//test messaggi di errore generato tramite richiesta di sessione
		ErrorDataModel[] error_code = MindsphereServiceClient.testGetStopCode(date);
		
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

		// scarti e produzioni
		mv.addObject("produzioni", prodottiescarti[1]);
		mv.addObject("scarti", prodottiescarti[0]);

		// velocit� ultima ora

		mv.addObject("min1", "255");
		mv.addObject("min2", "230");
		mv.addObject("min3", "200");
		mv.addObject("min4", "201");
		mv.addObject("min5", "320");
		mv.addObject("min6", "350");
		
		mv.addObject("date", date);
		
		mv.addObject("error_codice", error_code);


		return mv;
	}

}
