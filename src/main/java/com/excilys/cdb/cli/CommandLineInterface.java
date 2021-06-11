package com.excilys.cdb.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.cli.controller.PrincipalMenuController;

@Component
public class CommandLineInterface {
	
	@Autowired
	PrincipalMenuController principalMenuController;
	
	public CommandLineInterface() { }
	
	public void start() {		
		principalMenuController.start();
	}
}
