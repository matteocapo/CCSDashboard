package com.ccs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ccs.util.Date;

@Controller
public class CcsController {

	@RequestMapping("/indexprova")
	public ModelAndView homepageview(
			@RequestParam(value = "datetimes", required = false, defaultValue = "World") String date) {

		System.out.println("in controller");

		if (!date.equals("World")) {
			System.out.println(date);
			Date.toMindSphereFormat(date);
		}

		ModelAndView mv = new ModelAndView("indexprova");

		mv.addObject("domenica", "350");
		mv.addObject("lunedì", "340");

		// oee
		mv.addObject("oee", "80%");

		// scarti e produzioni
		mv.addObject("produzioni", "200562");
		mv.addObject("scarti", "8000");

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
