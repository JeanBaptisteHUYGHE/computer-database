package com.excilys.cdb.dto;

import java.time.LocalDate;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDto {

	private String id = null;
	private String name = null;
	private String introductionDate = null;
	private String discontinueDate = null;
	private String companyName = null;
	
	private ComputerDto() { }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroductionDate() {
		return introductionDate;
	}

	public void setIntroductionDate(String introductionDate) {
		this.introductionDate = introductionDate;
	}

	public String getDiscontinueDate() {
		return discontinueDate;
	}

	public void setDiscontinueDate(String discontinueDate) {
		this.discontinueDate = discontinueDate;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public static class ComputerDtoBuilder {
		
		private String id = null;
		private String name = null;
		private String introductionDate = null;
		private String discontinueDate = null;
		private String companyName = null;
				
		public ComputerDtoBuilder withId(String id) {
			this.id = id;
			return this;
		}
		
		public ComputerDtoBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ComputerDtoBuilder withIntroductionDate(String introductionDate) {
			this.introductionDate = introductionDate;
			return this;
		}
		
		public ComputerDtoBuilder withDiscontinueDate(String discontinueDate) {
			this.discontinueDate = discontinueDate;
			return this;
		}
		
		public ComputerDtoBuilder withCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
		public ComputerDtoBuilder withComputer(Computer computer) {
			id = computer.getId().orElse(null).toString();
			name = computer.getName();
			LocalDate tmpIntroductionDate = computer.getIntroductionDate().orElse(null);
			introductionDate = tmpIntroductionDate != null ? tmpIntroductionDate.toString() : "";
			LocalDate tmpDiscontinueDate = computer.getDiscontinueDate().orElse(null);
			discontinueDate = tmpDiscontinueDate != null ? tmpDiscontinueDate.toString() : "";
			Company company = computer.getManufacturer().orElse(null);
			companyName = (company != null && company.getName() != null) ? company.getName() : "";
			return this;
		}
		
		public ComputerDto build() {
			ComputerDto computerDto = new ComputerDto();
			computerDto.setId(id);
			computerDto.setName(name);
			computerDto.setIntroductionDate(introductionDate);
			computerDto.setDiscontinueDate(discontinueDate);
			computerDto.setCompanyName(companyName);
			return computerDto;
		}
	}
}
