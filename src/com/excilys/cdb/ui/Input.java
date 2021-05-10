package com.excilys.cdb.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
	
	private static Scanner scanner = null;
	
	/**
	 * Return the scanner / the user input
	 * @return the scanner
	 */
	private static Scanner getScanner() {
		if (scanner == null) {
			scanner = new Scanner(System.in);
		}
		return scanner;
	}

	/**
	 * Display a prompt for the user to indicate that his input is attempted
	 */
	private static void displayPrompt() {
		System.out.print("> ");
	}
	
	/**
	 * Read a string write by user
	 * @return the string
	 */
	public static String readString() {
		displayPrompt();
		Scanner scanner = getScanner();
		String input = scanner.nextLine();
		return input;
	}
	
	/**
	 * Read a Integer write by user
	 * @return the integer
	 * @throws InputMismatchException
	 */
	public static Integer readInteger() throws InputMismatchException {
		displayPrompt();
		Scanner scanner = getScanner();
		Integer input = scanner.nextInt();
		return input;
	}
	
	/**
	 * Read a date write by user
	 * @return the localDate or null
	 * @throws InputMismatchException
	 */
	public static LocalDate readLocalDate() throws InputMismatchException {
		System.out.println("(format YYYY/MM/DD or \"null\"");
		String input = readString();
		if (input.equals("null")) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
		LocalDate localDate;
		try {
			localDate = LocalDate.parse(input, formatter);
		}
		catch(DateTimeParseException e) {
			throw new InputMismatchException();
		}
		return localDate;
	}
	
	/**
	 * Close the input
	 */
	public static void close() {
		scanner.close();
	}
}
