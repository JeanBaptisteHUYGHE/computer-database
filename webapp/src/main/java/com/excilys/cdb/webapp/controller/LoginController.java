package com.excilys.cdb.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	private Logger logger;
	
	
	public LoginController() {
		logger = LoggerFactory.getLogger(LoginController.class);
	}
	
	@GetMapping
	public String getMapping(
			@RequestParam(name = "username", required = false, defaultValue = "") String username,
			@RequestParam(name = "error", required = false, defaultValue = "false") Boolean error,
			Model model) {
		logger.debug("getMapping(username={}, error={})", username, error);
		
		model.addAttribute("username", username);
		model.addAttribute("error", error);

		return "login";
	}
}
