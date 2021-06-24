package com.excilys.cdb.console.dto;

public class CompanyCDto {

	private String id;
	private String name;
	
	private CompanyCDto() { }
	
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
	
	public String toString() {
		return String.format("<CompanyCDto; id: %s, name:%s>", id, name);
	}
	
	public static class CompanyCDtoBuilder {
		
		private String id;
		private String name;
				
		public CompanyCDtoBuilder withId(String id) {
			if (id != null && id.isBlank()) {
				this.id = null;
			} else {
				this.id = id;
			}
			return this;
		}
		
		public CompanyCDtoBuilder withName(String name) {
			if (name != null && name.isBlank()) {
				this.name = null;
			} else {
				this.name = name;
			}
			return this;
		}
		
		public CompanyCDto build() {
			CompanyCDto companyCDto = new CompanyCDto();
			companyCDto.setId(id);
			companyCDto.setName(name);
			return companyCDto;
		}
	}
}
