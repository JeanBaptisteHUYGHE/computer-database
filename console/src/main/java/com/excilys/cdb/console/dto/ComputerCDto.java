package com.excilys.cdb.console.dto;

public class ComputerCDto {

	private String id;
	private String name;
	private String introductionDate;
	private String discontinueDate;
	private String companyName;
	private String companyId;
	
	private ComputerCDto() { }

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
		return String.format("<ComputerCDto; id: %s, name:%s, introductionDate: %s, discontinueDate: %s, companyName: %s, companyId: %s>",
				id, name, introductionDate, discontinueDate, companyName, companyId);
	}
	
	private static String formatAttribut(String attribut) {
		if (attribut != null && attribut.isBlank()) {
			return null;
		}
		return attribut;
	}


	public static class ComputerCDtoBuilder {
		
		private String id;
		private String name;
		private String introductionDate;
		private String discontinueDate;
		private String companyName;
		private String companyId;
				
		public ComputerCDtoBuilder withId(String id) {
			this.id = formatAttribut(id);
			return this;
		}
		
		public ComputerCDtoBuilder withName(String name) {
			this.name = formatAttribut(name);
			return this;
		}
		
		public ComputerCDtoBuilder withIntroductionDate(String introductionDate) {
			this.introductionDate = formatAttribut(introductionDate);
			return this;
		}
		
		public ComputerCDtoBuilder withDiscontinueDate(String discontinueDate) {
			this.discontinueDate = formatAttribut(discontinueDate);
			return this;
		}
		
		public ComputerCDtoBuilder withCompanyName(String companyName) {
			this.companyName = formatAttribut(companyName);
			return this;
		}
		
		public ComputerCDtoBuilder withCompanyId(String companyId) {
			this.companyId = formatAttribut(companyId);
			return this;
		}
		
		public ComputerCDto build() {
			ComputerCDto computerCDto = new ComputerCDto();
			computerCDto.setId(id);
			computerCDto.setName(name);
			computerCDto.setIntroductionDate(introductionDate);
			computerCDto.setDiscontinueDate(discontinueDate);
			computerCDto.setCompanyName(companyName);
			computerCDto.setCompanyId(companyId);
			return computerCDto;
		}
	}
}
