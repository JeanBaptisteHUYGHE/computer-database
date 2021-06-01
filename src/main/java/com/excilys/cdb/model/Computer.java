package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

public class Computer {

	private Integer id;
	private String name;
	private LocalDate introductionDate;
	private LocalDate discontinueDate;
	private Company company;
	
	private Computer()  {
	}

	public Optional<Integer> getId() {
		return Optional.ofNullable(id);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Optional<LocalDate> getIntroductionDate() {
		return Optional.ofNullable(introductionDate);
	}

	public void setIntroductionDate(LocalDate introductionDate) {
		this.introductionDate = introductionDate;
	}

	public Optional<LocalDate> getDiscontinueDate() {
		return Optional.ofNullable(discontinueDate);
	}

	public void setDiscontinueDate(LocalDate discontinueDate) {
		this.discontinueDate = discontinueDate;
	}

	public Optional<Company> getCompany() {
		return Optional.ofNullable(company);
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String toString() {
		return String.format("<Computer n°%s: %s, introduction date: %s, discontinue date: %s, company: %s>",
				this.id, this.name, this.introductionDate, this.discontinueDate, this.company);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discontinueDate == null) ? 0 : discontinueDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introductionDate == null) ? 0 : introductionDate.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Computer other = (Computer) obj;
		if (discontinueDate == null) {
			if (other.discontinueDate != null) {
				return false;
			}
		} else {
			if (!discontinueDate.equals(other.discontinueDate)) {
				return false;
			}
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		if (introductionDate == null) {
			if (other.introductionDate != null) {
				return false;
			}
		} else {
			if (!introductionDate.equals(other.introductionDate)) {
				return false;
			}
		}
		if (company == null) {
			if (other.company != null) {
				return false;
			}
		} else {
			if (!company.equals(other.company)) {
				return false;
			}
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else {
			if (!name.equals(other.name)) {
				return false;
			}
		}
		return true;
	}

	public static class ComputerBuilder {
		private Integer id = null;
		private String name = null;
		private LocalDate introductionDate = null;
		private LocalDate discontinueDate = null;
		private Company company = null;
		
		public ComputerBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public ComputerBuilder withIntroductionDate(LocalDate introductionDate) {
			this.introductionDate = introductionDate;
			return this;
		}

		public ComputerBuilder withDiscontinueDate(LocalDate discontinueDate) {
			this.discontinueDate = discontinueDate;
			return this;
		}

		public ComputerBuilder withCompany(Company manufacturer) {
			this.company = manufacturer;
			return this;
		}

		public Computer build() throws IllegalArgumentException {
			Computer computer = new Computer();
			computer.setId(id);
			computer.setName(name);
			computer.setIntroductionDate(introductionDate);
			computer.setDiscontinueDate(discontinueDate);
			computer.setCompany(company);
			return computer;
		}
	}
}
