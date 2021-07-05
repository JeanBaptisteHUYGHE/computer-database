package com.excilys.cdb.restapi.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonRootName(value = "computer")
public class ComputerRDto {

	private String id;
	private String name;
	private String introductionDate;
	private String discontinueDate;
	private String companyName;
	private String companyId;
	
	private ComputerRDto() { }

	@JsonGetter("id")
	public String getId() {
		return id;
	}

	@JsonSetter("id")
	public void setId(String id) {
		this.id = formatAttribut(id);
	}

	@JsonGetter("name")
	public String getName() {
		return name;
	}

	@JsonSetter("name")
	public void setName(String name) {
		this.name = formatAttribut(name);
	}

	@JsonGetter("introductionDate")
	public String getIntroductionDate() {
		return introductionDate;
	}

	@JsonSetter("introductionDate")
	public void setIntroductionDate(String introductionDate) {
		this.introductionDate = formatAttribut(introductionDate);
	}

	@JsonSetter("discontinueDate")
	public String getDiscontinueDate() {
		return discontinueDate;
	}

	@JsonSetter("discontinueDate")
	public void setDiscontinueDate(String discontinueDate) {
		this.discontinueDate = formatAttribut(discontinueDate);
		
	}
	
	@JsonGetter("companyName")
	public String getCompanyName() {
		return companyName;
	}

	@JsonSetter("companyName")
	public void setCompanyName(String companyName) {
		this.companyName = formatAttribut(companyName);
	}

	@JsonGetter("companyId")
	public String getCompanyId() {
		return companyId;
	}

	@JsonSetter("companyId")
	public void setCompanyId(String companyId) {
		this.companyId = formatAttribut(companyId);
	}
	
	public String toString() {
		return String.format("<ComputerRDto; id: %s, name:%s, introductionDate: %s, discontinueDate: %s, companyName: %s, companyId: %s>",
				id, name, introductionDate, discontinueDate, companyName, companyId);
	}
	
	private static String formatAttribut(String attribut) {
		if (attribut != null && attribut.isBlank()) {
			return null;
		}
		return attribut;
	}


	public static class ComputerRDtoBuilder {
		
		private String id;
		private String name;
		private String introductionDate;
		private String discontinueDate;
		private String companyName;
		private String companyId;
				
		public ComputerRDtoBuilder withId(String id) {
			this.id = formatAttribut(id);
			return this;
		}
		
		public ComputerRDtoBuilder withName(String name) {
			this.name = formatAttribut(name);
			return this;
		}
		
		public ComputerRDtoBuilder withIntroductionDate(String introductionDate) {
			this.introductionDate = formatAttribut(introductionDate);
			return this;
		}
		
		public ComputerRDtoBuilder withDiscontinueDate(String discontinueDate) {
			this.discontinueDate = formatAttribut(discontinueDate);
			return this;
		}
		
		public ComputerRDtoBuilder withCompanyName(String companyName) {
			this.companyName = formatAttribut(companyName);
			return this;
		}
		
		public ComputerRDtoBuilder withCompanyId(String companyId) {
			this.companyId = formatAttribut(companyId);
			return this;
		}
		
		public ComputerRDto build() {
			ComputerRDto computerRDto = new ComputerRDto();
			computerRDto.setId(id);
			computerRDto.setName(name);
			computerRDto.setIntroductionDate(introductionDate);
			computerRDto.setDiscontinueDate(discontinueDate);
			computerRDto.setCompanyName(companyName);
			computerRDto.setCompanyId(companyId);
			return computerRDto;
		}
	}
}
