package com.excilys.cdb.dto;

public class CompanyDto {

	private String id;
	private String name;
	
	private CompanyDto() { }
	
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
		return String.format("<CompanyDto; id: %s, name:%s>", id, name);
	}
	
	public static class CompanyDtoBuilder {
		
		private String id;
		private String name;
				
		public CompanyDtoBuilder withId(String id) {
			if (id != null && id.isBlank()) {
				this.id = null;
			} else {
				this.id = id;
			}
			return this;
		}
		
		public CompanyDtoBuilder withName(String name) {
			if (name != null && name.isBlank()) {
				this.name = null;
			} else {
				this.name = name;
			}
			return this;
		}
		
		public CompanyDto build() {
			CompanyDto companyDto = new CompanyDto();
			companyDto.setId(id);
			companyDto.setName(name);
			return companyDto;
		}
	}
}
