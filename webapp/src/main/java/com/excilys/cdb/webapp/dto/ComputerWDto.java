package com.excilys.cdb.webapp.dto;

public class ComputerWDto {

	private String id;
	private String name;
	private String introductionDate;
	private String discontinueDate;
	private String companyName;
	private String companyId;
	
	private ComputerWDto() { }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = formatAttribut(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = formatAttribut(name);
	}

	public String getIntroductionDate() {
		return introductionDate;
	}

	public void setIntroductionDate(String introductionDate) {
		this.introductionDate = formatAttribut(introductionDate);
	}

	public String getDiscontinueDate() {
		return discontinueDate;
	}

	public void setDiscontinueDate(String discontinueDate) {
		this.discontinueDate = formatAttribut(discontinueDate);
		
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = formatAttribut(companyName);
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = formatAttribut(companyId);
	}
	
	public String toString() {
		return String.format("<ComputerWDto; id: %s, name:%s, introductionDate: %s, discontinueDate: %s, companyName: %s, companyId: %s>",
				id, name, introductionDate, discontinueDate, companyName, companyId);
	}
	
	private static String formatAttribut(String attribut) {
		if (attribut != null && attribut.isBlank()) {
			return null;
		}
		return attribut;
	}


	public static class ComputerWDtoBuilder {
		
		private String id;
		private String name;
		private String introductionDate;
		private String discontinueDate;
		private String companyName;
		private String companyId;
				
		public ComputerWDtoBuilder withId(String id) {
			this.id = formatAttribut(id);
			return this;
		}
		
		public ComputerWDtoBuilder withName(String name) {
			this.name = formatAttribut(name);
			return this;
		}
		
		public ComputerWDtoBuilder withIntroductionDate(String introductionDate) {
			this.introductionDate = formatAttribut(introductionDate);
			return this;
		}
		
		public ComputerWDtoBuilder withDiscontinueDate(String discontinueDate) {
			this.discontinueDate = formatAttribut(discontinueDate);
			return this;
		}
		
		public ComputerWDtoBuilder withCompanyName(String companyName) {
			this.companyName = formatAttribut(companyName);
			return this;
		}
		
		public ComputerWDtoBuilder withCompanyId(String companyId) {
			this.companyId = formatAttribut(companyId);
			return this;
		}
		
		public ComputerWDto build() {
			ComputerWDto computerWDto = new ComputerWDto();
			computerWDto.setId(id);
			computerWDto.setName(name);
			computerWDto.setIntroductionDate(introductionDate);
			computerWDto.setDiscontinueDate(discontinueDate);
			computerWDto.setCompanyName(companyName);
			computerWDto.setCompanyId(companyId);
			return computerWDto;
		}
	}
}
