package com.excilys.cdb.persistance.dto;

public class ComputerPDto {

	private String id;
	private String name;
	private String introductionDate;
	private String discontinueDate;
	private String companyName;
	private String companyId;
	
	private ComputerPDto() { }

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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String toString() {
		return String.format("<ComputerPDto; id: %s, name:%s, introductionDate: %s, discontinueDate: %s, companyName: %s, companyId: %s>",
				id, name, introductionDate, discontinueDate, companyName, companyId);
	}


	public static class ComputerPDtoBuilder {
		
		private String id;
		private String name;
		private String introductionDate;
		private String discontinueDate;
		private String companyName;
		private String companyId;
				
		public ComputerPDtoBuilder withId(String id) {
			if (id != null && id.isBlank()) {
				this.id = null;
			} else {
				this.id = id;
			}
			return this;
		}
		
		public ComputerPDtoBuilder withName(String name) {
			if (name != null && name.isBlank()) {
				this.name = null;
			} else {
				this.name = name;
			}
			return this;
		}
		
		public ComputerPDtoBuilder withIntroductionDate(String introductionDate) {
			if (introductionDate != null && introductionDate.isBlank()) {
				this.introductionDate = null;
			} else {
				this.introductionDate = introductionDate;
			}
			return this;
		}
		
		public ComputerPDtoBuilder withDiscontinueDate(String discontinueDate) {
			if (discontinueDate != null && discontinueDate.isBlank()) {
				this.discontinueDate = null;
			} else {
				this.discontinueDate = discontinueDate;
			}
			return this;
		}
		
		public ComputerPDtoBuilder withCompanyName(String companyName) {
			if (companyName != null && companyName.isBlank()) {
				this.companyName = null;
			} else {
				this.companyName = companyName;
			}
			return this;
		}
		
		public ComputerPDtoBuilder withCompanyId(String companyId) {
			if (companyId != null && companyId.isBlank()) {
				this.companyId = null;
			} else {
				this.companyId = companyId;
			}
			return this;
		}
		
		public ComputerPDto build() {
			ComputerPDto computerPDto = new ComputerPDto();
			computerPDto.setId(id);
			computerPDto.setName(name);
			computerPDto.setIntroductionDate(introductionDate);
			computerPDto.setDiscontinueDate(discontinueDate);
			computerPDto.setCompanyName(companyName);
			computerPDto.setCompanyId(companyId);
			return computerPDto;
		}
	}
}
