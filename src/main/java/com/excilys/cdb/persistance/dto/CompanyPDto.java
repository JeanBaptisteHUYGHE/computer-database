package com.excilys.cdb.persistance.dto;

public class CompanyPDto {

	private String id;
	private String name;
	
	private CompanyPDto() { }
	
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
		return String.format("<CompanyPDto; id: %s, name:%s>", id, name);
	}
	
	public static class CompanyPDtoBuilder {
		
		private String id;
		private String name;
				
		public CompanyPDtoBuilder withId(String id) {
			if (id != null && id.isBlank()) {
				this.id = null;
			} else {
				this.id = id;
			}
			return this;
		}
		
		public CompanyPDtoBuilder withName(String name) {
			if (name != null && name.isBlank()) {
				this.name = null;
			} else {
				this.name = name;
			}
			return this;
		}
		
		public CompanyPDto build() {
			CompanyPDto companyPDto = new CompanyPDto();
			companyPDto.setId(id);
			companyPDto.setName(name);
			return companyPDto;
		}
	}
}
