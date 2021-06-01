package com.excilys.cdb.cli;

import com.excilys.cdb.cli.controller.PrincipalMenuController;

public class CommandLineInterface {
	
	public CommandLineInterface() {
		new PrincipalMenuController();
	}
	
	public static void main(String[] args) {
		new CommandLineInterface();
	}
}
