package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

public class Computer {

	private Integer id = null;
	private String name;
	private LocalDate introductionDate = null;
	private LocalDate discontinueDate = null;
	private Company manufacturer = null;
	
	private Computer(String name) throws IllegalArgumentException {
		this.setName(name);
	}

	public Optional<Integer> getId() {
		return Optional.ofNullable(id);
	}

	public void setId(Integer id) throws IllegalArgumentException {
		if (id != null && id < 0) {
			throw new IllegalArgumentException("Computer id must be positive");
		}
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("Computer name can't be null.");
		}
		this.name = name;
	}
	
	/**
	 * Return if the date is valid (=if it can be set in a SQL "timestamp" type).
	 * @param localDate
	 * @return if the date is valid
	 */
	private static boolean isValidDate(LocalDate localDate) {
		if (localDate == null) {
			return true;
		}
		LocalDate minDate = LocalDate.of(1970, 1, 1);
		LocalDate maxDate = LocalDate.of(2038, 1, 19);
		return (localDate.isAfter(minDate) && localDate.isBefore(maxDate));
	}

	public Optional<LocalDate> getIntroductionDate() {
		return Optional.ofNullable(introductionDate);
	}

	public void setIntroductionDate(LocalDate introductionDate) {
		if (introductionDate != null && discontinueDate != null && introductionDate.isAfter(discontinueDate)) {
			throw new IllegalArgumentException("Invalid introduction date, introduction date must be before discontinueD date.");
		}
		if (!isValidDate(introductionDate)) {
			throw new IllegalArgumentException("Invalid introduction date: date too extrem");
		}
		this.introductionDate = introductionDate;
	}

	public Optional<LocalDate> getDiscontinueDate() {
		return Optional.ofNullable(discontinueDate);
	}

	public void setDiscontinueDate(LocalDate discontinueDate) throws IllegalArgumentException {
		if (discontinueDate != null && introductionDate != null && discontinueDate.isBefore(introductionDate)) {
			throw new IllegalArgumentException("Invalid discontinue date, discontinue date must be after introduction date.");
		}
		if (!isValidDate(discontinueDate)) {
			throw new IllegalArgumentException("Invalid discontinue date: date too extrem");
		}
		this.discontinueDate = discontinueDate;
	}

	public Optional<Company> getManufacturer() {
		return Optional.ofNullable(manufacturer);
	}

	public void setManufacturer(Company manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String toString() {
		return String.format("<Computer n°%s: %s, introduction date: %s, discontinue date: %s, manufacturer: %s>",
				this.id, this.name, this.introductionDate, this.discontinueDate, this.manufacturer);
	}
	
	public static class ComputerBuilder {
		private Integer id = null;
		private String name;
		private LocalDate introductionDate = null;
		private LocalDate discontinueDate = null;
		private Company manufacturer = null;
		
		public ComputerBuilder(String name) {
			this.name = name;
		}
		
		public ComputerBuilder(Integer id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public ComputerBuilder(Integer id, String name, LocalDate introductionDate, LocalDate discontinueDate) {
			this.id = id;
			this.name = name;
			this.introductionDate = introductionDate;
			this.discontinueDate = discontinueDate;
		}
		
		public ComputerBuilder(String name, LocalDate introductionDate, LocalDate discontinueDate, Company manufacturer) {
			this.name = name;
			this.introductionDate = introductionDate;
			this.discontinueDate = discontinueDate;
			this.manufacturer = manufacturer;
		}
		
		public ComputerBuilder(Integer id, String name, LocalDate introductionDate, LocalDate discontinueDate, Company manufacturer) {
			this.id = id;
			this.name = name;
			this.introductionDate = introductionDate;
			this.discontinueDate = discontinueDate;
			this.manufacturer = manufacturer;
		}
		
		/**
		 * Return the builded computer.
		 * @return the computer
		 */
		public Computer build() {
			Computer computer = new Computer(name);
			computer.setId(id);
			computer.setIntroductionDate(introductionDate);
			computer.setDiscontinueDate(discontinueDate);
			computer.setManufacturer(manufacturer);
			return computer;
		}
	}
	
}
