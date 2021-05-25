package com.excilys.cdb.dto;

import com.excilys.cdb.model.Computer;

public class ComputerDto {

	private String id = null;
	private String name = null;
	private String introductionDate = null;
	private String discontinueDate = null;
	
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
	
	public static class ComputerDtoBuilder {
		
		private String id = null;
		private String name = null;
		private String introductionDate = null;
		private String discontinueDate = null;
				
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
		
		public ComputerDtoBuilder withComputer(Computer computer) {
			id = computer.getId().orElse(null).toString();
			name = computer.getName();
			introductionDate = computer.getIntroductionDate().orElse(null).toString();
			discontinueDate = computer.getDiscontinueDate().orElse(null).toString();
			return this;
		}
		
		public ComputerDto build() {
			ComputerDto computerDto = new ComputerDto();
			computerDto.setId(id);
			computerDto.setName(name);
			computerDto.setIntroductionDate(introductionDate);
			computerDto.setDiscontinueDate(discontinueDate);
			return computerDto;
		}
	}
}
