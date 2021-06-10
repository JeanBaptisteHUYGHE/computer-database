package com.excilys.cdb.dto;

public class ComputerDto {

	private String id;
	private String name;
	private String introductionDate;
	private String discontinueDate;
	private String companyName;
	private String companyId;
	
	private ComputerDto() { }

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
		return String.format("<ComputerDto; id: %s, name:%s, introductionDate: %s, discontinueDate: %s, companyName: %s, companyId: %s>",
				id, name, introductionDate, discontinueDate, companyName, companyId);
	}
	
	private static String formatAttribut(String attribut) {
		if (attribut != null && attribut.isBlank()) {
			return null;
		}
		return attribut;
	}


	public static class ComputerDtoBuilder {
		
		private String id;
		private String name;
		private String introductionDate;
		private String discontinueDate;
		private String companyName;
		private String companyId;
				
		public ComputerDtoBuilder withId(String id) {
			this.id = formatAttribut(id);
			return this;
		}
		
		public ComputerDtoBuilder withName(String name) {
			this.name = formatAttribut(name);
			return this;
		}
		
		public ComputerDtoBuilder withIntroductionDate(String introductionDate) {
			this.introductionDate = formatAttribut(introductionDate);
			return this;
		}
		
		public ComputerDtoBuilder withDiscontinueDate(String discontinueDate) {
			this.discontinueDate = formatAttribut(discontinueDate);
			return this;
		}
		
		public ComputerDtoBuilder withCompanyName(String companyName) {
			this.companyName = formatAttribut(companyName);
			return this;
		}
		
		public ComputerDtoBuilder withCompanyId(String companyId) {
			this.companyId = formatAttribut(companyId);
			return this;
		}
		
		public ComputerDto build() {
			ComputerDto computerDto = new ComputerDto();
			computerDto.setId(id);
			computerDto.setName(name);
			computerDto.setIntroductionDate(introductionDate);
			computerDto.setDiscontinueDate(discontinueDate);
			computerDto.setCompanyName(companyName);
			computerDto.setCompanyId(companyId);
			return computerDto;
		}
	}
}
