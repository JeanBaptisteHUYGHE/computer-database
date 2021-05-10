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
	
	public Computer(String name, LocalDate introductionDate, LocalDate discontinueDate, Company manufacturer) {
		this.setName(name);
		this.setIntroductionDate(introductionDate);
		this.setDiscontinueDate(discontinueDate);
		this.setManufacturer(manufacturer);
	}
	
	public Computer(Integer id, String name, LocalDate introductionDate, LocalDate discontinueDate, Company manufacturer) {
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
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	public LocalDate getIntroductionDate() {
		return introductionDate;
	}

	public void setIntroductionDate(LocalDate introductionDate) {
		if (introductionDate != null && this.discontinueDate != null && introductionDate.compareTo(this.discontinueDate) > 0) {
			throw new IllegalArgumentException();
		}
		this.introductionDate = introductionDate;
	}

	public LocalDate getDiscontinueDate() {
		return discontinueDate;
	}

	public void setDiscontinueDate(LocalDate discontinueDate) throws IllegalArgumentException {
		if (discontinueDate != null && this.introductionDate != null && discontinueDate.compareTo(this.introductionDate) < 0) {
			throw new IllegalArgumentException();
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
