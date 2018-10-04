package com.orasoft.data.link.controllers;

import java.security.Principal;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardController {

	@RequestMapping(value = {"/","/dashboard"}, method = RequestMethod.GET)
	public String register(Map<String, Object> model,Principal principal,RedirectAttributes flash ) {
		
		if (principal == null) {
			return "redirect:/register";
		}
		model.put("activeIndexClassDash", "active");
		model.put("titulo", "Dashboard");
		return "dashboard";
	}
	
}
