package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {

	private Integer id = null;
	private String name;
	private LocalDate introductionDate = null;
	private LocalDate discontinueDate = null;
	private Company manufacturer = null;
	
	public Computer(String name) throws IllegalArgumentException {
		this.setName(name);
	}
	
	public Computer(Integer id, String name) throws IllegalArgumentException {
		this.id = id;
		this.setName(name);
	}
	
	public Computer(String name, LocalDate introductionDate, LocalDate discontinueDate, Company manufacturer) throws IllegalArgumentException {
		this.setName(name);
		this.setIntroductionDate(introductionDate);
		this.setDiscontinueDate(discontinueDate);
		this.setManufacturer(manufacturer);
	}
	
	public Computer(Integer id, String name, LocalDate introductionDate, LocalDate discontinueDate, Company manufacturer) throws IllegalArgumentException {
		this.setId(id);
		this.setName(name);
		this.setIntroductionDate(introductionDate);
		this.setDiscontinueDate(discontinueDate);
		this.setManufacturer(manufacturer);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) throws IllegalArgumentException {
		if (id != null && id < 0) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("Computer can't be null.");
		}
		this.name = name;
	}
	
	/**
	 * Return if the date is valid (=if it can be set in a SQL "timestamp" type)
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

	public LocalDate getIntroductionDate() {
		return introductionDate;
	}

	public void setIntroductionDate(LocalDate introductionDate) {
		if (introductionDate != null && this.discontinueDate != null && introductionDate.isAfter(discontinueDate)) {
			throw new IllegalArgumentException("Invalid introduction date, introduction date must be before discontinueD date.");
		}
		if (!isValidDate(introductionDate)) {
			throw new IllegalArgumentException("Invalid introduction date: date too extrem");
		}
		this.introductionDate = introductionDate;
	}

	public LocalDate getDiscontinueDate() {
		return discontinueDate;
	}

	public void setDiscontinueDate(LocalDate discontinueDate) throws IllegalArgumentException {
		if (discontinueDate != null && this.introductionDate != null && discontinueDate.isBefore(introductionDate)) {
			throw new IllegalArgumentException("Invalid discontinue date, discontinue date must be after introduction date.");
		}
		if (!isValidDate(discontinueDate)) {
			throw new IllegalArgumentException("Invalid discontinue date: date too extrem");
		}
		this.discontinueDate = discontinueDate;
	}

	public Company getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Company manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String toString() {
		return String.format("<Computer n°%s: %s, introduction date: %s, discontinue date: %s, manufacturer: %s>",
				this.id, this.name, this.introductionDate, this.discontinueDate, this.manufacturer);
	}
	
	
}
