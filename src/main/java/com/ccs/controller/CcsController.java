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
		

		//oee = MindsphereServiceClient.oeeMedia("prova", "prova");
		oee = MindsphereServiceClient.testUrlDataOee(date);
		
		//prodottiescarti = MindsphereServiceClient.prodottiEScarti("prova", "prova");
		prodottiescarti = MindsphereServiceClient.testUrlDataProdottiEScarti(date);	
		
		//test messaggi di errore
		MindsphereServiceClient.testGetStopCode(date);
		
		//test utilizzo chiamata developer account
		//MindsphereServiceClient.getTimeseriesAsObject("7cb21d4c9b724be5b38c2c9695d9b3c8", "demobox", "eyJhbGciOiJSUzI1NiIsImtpZCI6ImtleS1pZC0xIiwidHlwIjoiSldUIn0.eyJqdGkiOiI0MzViMGVkZWI5Yzc0M2Y0YmZhODQ2MjE1MzcwODJiMSIsInN1YiI6Iml0YWRldi1zZXJ2aWNlLWNyZWRlbnRpYWxzIiwic2NvcGUiOlsiaWFtLWFjdGlvbi5jbGllbnRfY3JlZGVudGlhbHMuc3VidGVuYW50LWltcGVyc29uYXRpb24iLCJtZHNwOmNvcmU6QWRtaW4zcmRQYXJ0eVRlY2hVc2VyIl0sImNsaWVudF9pZCI6Iml0YWRldi1zZXJ2aWNlLWNyZWRlbnRpYWxzIiwiY2lkIjoiaXRhZGV2LXNlcnZpY2UtY3JlZGVudGlhbHMiLCJhenAiOiJpdGFkZXYtc2VydmljZS1jcmVkZW50aWFscyIsImdyYW50X3R5cGUiOiJjbGllbnRfY3JlZGVudGlhbHMiLCJyZXZfc2lnIjoiMjBjZjdhMDQiLCJpYXQiOjE1NDMyNDg3MjEsImV4cCI6MTU0MzI1MDUyMSwiaXNzIjoiaHR0cHM6Ly9pdGFkZXYucGlhbS5ldTEubWluZHNwaGVyZS5pby9vYXV0aC90b2tlbiIsInppZCI6Iml0YWRldiIsImF1ZCI6WyJpdGFkZXYtc2VydmljZS1jcmVkZW50aWFscyIsImlhbS1hY3Rpb24uY2xpZW50X2NyZWRlbnRpYWxzIl0sInRlbiI6Iml0YWRldiIsInNjaGVtYXMiOlsidXJuOnNpZW1lbnM6bWluZHNwaGVyZTppYW06djEiXSwiY2F0IjoiY2xpZW50LXRva2VuOnYxIn0.MypcL3Qi78pu7732bZ_q0AhkvPlwgukptzTJ5cFZoThap78aExjnOFByaDhPaBQ6Ul5EbzK5Ckv6Clz0zoRUODQtuRSElfaIPZcuvSo3jDNDrDL0_Ln6wjEdU3Zm6PWLP_nSYKliKTdDlYM4SLx1ZbSzM69q9A6n5JqjxlM2OSVMRQNFX6wy372oEA0ax03R2j8StiqawTjlSBqKowgjeE1jGOXfUjedSU5LqroKSzXCWXHSCO0xtBjqDoi5xhXye_nip4--qK_tLaYV6wC9kDtYzkARZhs32QnLXX51BVapTzogYpP5GweqW5bK8Yunr_owmoB_lkuB2qYYmTM_Ug");
		
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
