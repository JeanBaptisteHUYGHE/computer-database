package com.excilys.cdb.console.view;

public abstract class MenuView {

	public void drawMessage(String message) {
		System.out.println(message);
	}
	
	public void drawError(String errorMessage) {
		System.err.println(errorMessage);
	}
	
}
