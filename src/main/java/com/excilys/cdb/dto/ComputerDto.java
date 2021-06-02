package com.excilys.cdb.dto;

public class ComputerDto {

	private String id = null;
	private String name = null;
	private String introductionDate = null;
	private String discontinueDate = null;
	private String companyName = null;
	private String companyId = null;
	
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String toString() {
		return String.format("<ComputerDto; id: %s, name:%s, introductionDate: %s, discontinueDate: %s, companyName: %s, companyId: %s>",
				id, name, introductionDate, discontinueDate, companyName, companyId);
	}


	public static class ComputerDtoBuilder {
		
		private String id;
		private String name;
		private String introductionDate;
		private String discontinueDate;
		private String companyName;
		private String companyId;
				
		public ComputerDtoBuilder withId(String id) {
			if (id != null && id.isBlank()) {
				this.id = null;
			} else {
				this.id = id;
			}
			return this;
		}
		
		public ComputerDtoBuilder withName(String name) {
			if (name != null && name.isBlank()) {
				this.name = null;
			} else {
				this.name = name;
			}
			return this;
		}
		
		public ComputerDtoBuilder withIntroductionDate(String introductionDate) {
			if (introductionDate != null && introductionDate.isBlank()) {
				this.introductionDate = null;
			} else {
				this.introductionDate = introductionDate;
			}
			return this;
		}
		
		public ComputerDtoBuilder withDiscontinueDate(String discontinueDate) {
			if (discontinueDate != null && discontinueDate.isBlank()) {
				this.discontinueDate = null;
			} else {
				this.discontinueDate = discontinueDate;
			}
			return this;
		}
		
		public ComputerDtoBuilder withCompanyName(String companyName) {
			if (companyName != null && companyName.isBlank()) {
				this.companyName = null;
			} else {
				this.companyName = companyName;
			}
			return this;
		}
		
		public ComputerDtoBuilder withCompanyId(String companyId) {
			if (companyId != null && companyId.isBlank()) {
				this.companyId = null;
			} else {
				this.companyId = companyId;
			}
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
