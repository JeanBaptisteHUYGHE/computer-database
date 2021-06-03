package com.excilys.cdb.cli.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
	
	private static Scanner scanner;
	
	/**
	 * Return the scanner / the user input.
	 * @return the scanner
	 */
	private static Scanner getScanner() {
		if (scanner == null) {
			scanner = new Scanner(System.in);
		}
		return scanner;
	}
	
	/**
	 * Close the input.
	 * @warning Only use if you will not use it again on the program
	 */
	public static void close() {
		if (scanner != null) {
			scanner.close();
			scanner = null;
		}
	}

	/**
	 * Display a prompt for the user to indicate that his input is attempted.
	 */
	private static void displayPrompt() {
		System.out.print("> ");
	}
	
	/**
	 * Read a string write by user.
	 * @return the string
	 */
	public static String readString() {
		displayPrompt();
		Scanner scanner = getScanner();
		String input = scanner.nextLine();
		return input;
	}
	
	public static String readStringOrNull() {
		String string = readString();
		if (string.isBlank()) {
			return null;
		}
		return string;
	}
	
	/**
	 * Read a Integer write by user.
	 * @return the integer
	 * @throws InputMismatchException
	 */
	public static Integer readInteger() throws InputMismatchException {
		displayPrompt();
		Scanner scanner = getScanner();
		String input = scanner.nextLine();
		Integer output;
		try {
			output = Integer.valueOf(input);
		} catch (NumberFormatException e) {
			throw new InputMismatchException();
		}
		return output;
	}
	
	/**
	 * Return a valid integer write by user.
	 * @return the integer
	 */
	public static Integer readValidInteger() {
		boolean isValid = false;
		Integer integer = null;
		while (!isValid) {
			try {
				integer = readInteger();
				isValid = true;
			} catch (InputMismatchException e) {
				System.out.println("Invalid number, please retry");
			}
		}
		return integer;
	}
	
	/**
	 * Read a Integer write by user.
	 * @return the integer or null
	 * @throws InputMismatchException
	 */
	public static Integer readIntegerOrNull() throws InputMismatchException {
		System.out.println("(number or \"null\")");
		displayPrompt();
		Scanner scanner = getScanner();
		String input = scanner.nextLine();
		if (input.equals("null")) {
			return null;
		}
		Integer output;
		try {
			output = Integer.valueOf(input);
		} catch (NumberFormatException e) {
			throw new InputMismatchException();
		}
		return output;
	}
	
	/**
	 * Return a valid integer write by user.
	 * @return the integer or null
	 */
	public static Integer readValidIntegerOrNull() {
		boolean isValid = false;
		Integer integer = null;
		while (!isValid) {
			try {
				integer = readIntegerOrNull();
				isValid = true;
			} catch (InputMismatchException e) {
				System.out.println("Invalid number, please retry");
			}
		}
		return integer;
	}
	
	/**
	 * Read a date write by user.
	 * @return the string of the local date or null
	 * @throws InputMismatchException
	 */
	public static String readLocalDateOrNull() throws InputMismatchException {
		System.out.println("(format YYYY/MM/DD or let empty");
		String input = readString();
		if (input.isBlank()) {
			return null;
		}
		return input;
	}
}
