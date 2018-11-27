package com.ccs.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ccs.provider.MindsphereServiceClient;
import com.ccs.util.Date;
import com.siemens.mindsphere.sdk.core.exception.MindsphereException;

@Controller
public class CcsController {

	@RequestMapping("/indexprova")
	public ModelAndView homepageview(@RequestParam(value = "datetimes", required = false, defaultValue = "World") String date) throws IOException, MindsphereException {
		
		int oee = 0;
		int prodottiescarti[] = new int[2];
		
		System.out.println("in controller");

		if (!date.equals("World")) {
			System.out.println(date);
			Date.toMindSphereFormat(date);
		}
		
		//vecchia funzione di prova con json statico
		//oee = MindsphereServiceClient.oeeMedia("prova", "prova");
		
		//funzione di prova con json generato tramite richiesta di sessione 
		//oee = MindsphereServiceClient.testUrlDataOee(date);
		
		//vecchia funzione di prova con json statico
		//prodottiescarti = MindsphereServiceClient.prodottiEScarti("prova", "prova");
		
		//funzione di prova con json generato tramite richiesta di sessione 
		//prodottiescarti = MindsphereServiceClient.testUrlDataProdottiEScarti(date);	
		
		//test messaggi di errore
		//MindsphereServiceClient.testGetStopCode(date);
		
		//test utilizzo chiamata developer account
		//MindsphereServiceClient.getTimeSeriesAsObject("7cb21d4c9b724be5b38c2c9695d9b3c8", "demobox", "eyJhbGciOiJSUzI1NiIsImtpZCI6ImtleS1pZC0xIiwidHlwIjoiSldUIn0.eyJqdGkiOiJkYTAyOGZiNmRkODU0OGY3ODllODcwMGIxMzIzYzI2NiIsInN1YiI6Iml0YWRldi1zZXJ2aWNlLWNyZWRlbnRpYWxzIiwic2NvcGUiOlsiaWFtLWFjdGlvbi5jbGllbnRfY3JlZGVudGlhbHMuc3VidGVuYW50LWltcGVyc29uYXRpb24iLCJtZHNwOmNvcmU6QWRtaW4zcmRQYXJ0eVRlY2hVc2VyIl0sImNsaWVudF9pZCI6Iml0YWRldi1zZXJ2aWNlLWNyZWRlbnRpYWxzIiwiY2lkIjoiaXRhZGV2LXNlcnZpY2UtY3JlZGVudGlhbHMiLCJhenAiOiJpdGFkZXYtc2VydmljZS1jcmVkZW50aWFscyIsImdyYW50X3R5cGUiOiJjbGllbnRfY3JlZGVudGlhbHMiLCJyZXZfc2lnIjoiMjBjZjdhMDQiLCJpYXQiOjE1NDMzMjI5MzUsImV4cCI6MTU0MzMyNDczNSwiaXNzIjoiaHR0cHM6Ly9pdGFkZXYucGlhbS5ldTEubWluZHNwaGVyZS5pby9vYXV0aC90b2tlbiIsInppZCI6Iml0YWRldiIsImF1ZCI6WyJpdGFkZXYtc2VydmljZS1jcmVkZW50aWFscyIsImlhbS1hY3Rpb24uY2xpZW50X2NyZWRlbnRpYWxzIl0sInRlbiI6Iml0YWRldiIsInNjaGVtYXMiOlsidXJuOnNpZW1lbnM6bWluZHNwaGVyZTppYW06djEiXSwiY2F0IjoiY2xpZW50LXRva2VuOnYxIn0.hFGO75Wkav-Bl8YwxjZATUv2c0YnoTh-SOLRX1XHzcwTYe1dPRThrJu4IoAq7aLelKUTwwdU57obk-3fejDjqZ3KmMcJ8yFqK36fM-Bvisp-_GgzVBurZQgV825axgEDtrhZCGoiFM5UrlpR-3v6BZjWG-FOR86DUc2pO-d3hGkP8HHZDpD1Sy8xBPctZk18mCnNmwNUP9Eq6YgGWv6I7ZtI2kPG_GkgMpGyF04yV7EKHBn0eUhOYatpu32NeppBTm_FdiLqJtLQV7DH5XmbN3FEcRgwqJrlbr3POrTSL8i8ggRCpVjvK3SyMIxl3N4pl0eFFG3UURQH6Z797C8uWw");
		
		MindsphereServiceClient.testApiSelfMade();
		
		
		ModelAndView mv = new ModelAndView("indexprova");

		// oee
		mv.addObject("oee", oee + "%");

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

		return mv;
	}

}
