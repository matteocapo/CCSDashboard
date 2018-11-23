package com.ccs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ccs.provider.MindsphereServiceClient;
import com.ccs.util.Date;

@Controller
public class CcsController {

	@RequestMapping("/indexprova")
	public ModelAndView homepageview(@RequestParam(value = "datetimes", required = false, defaultValue = "World") String date) {
		
		int oee;
		int prodottiescarti[] = new int[2];
		
		System.out.println("in controller");

		if (!date.equals("World")) {
			System.out.println(date);
			Date.toMindSphereFormat(date);
		}
		
		oee = MindsphereServiceClient.oeeMedia("prova", "prova");
		prodottiescarti = MindsphereServiceClient.prodottiEScarti("prova", "prova");

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
