package com.excilys.cdb.webapp.dto;

public class CompanyWDto {

	private String id;
	private String name;
	
	private CompanyWDto() { }
	
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
		return String.format("<CompanyWDto; id: %s, name:%s>", id, name);
	}
	
	public static class CompanyWDtoBuilder {
		
		private String id;
		private String name;
				
		public CompanyWDtoBuilder withId(String id) {
			if (id != null && id.isBlank()) {
				this.id = null;
			} else {
				this.id = id;
			}
			return this;
		}
		
		public CompanyWDtoBuilder withName(String name) {
			if (name != null && name.isBlank()) {
				this.name = null;
			} else {
				this.name = name;
			}
			return this;
		}
		
		public CompanyWDto build() {
			CompanyWDto companyWDto = new CompanyWDto();
			companyWDto.setId(id);
			companyWDto.setName(name);
			return companyWDto;
		}
	}
}
