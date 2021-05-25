package com.excilys.cdb.dto;

import com.excilys.cdb.model.Company;

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
	
	public class CompanyDtoBuilder {
		
		private String id;
		private String name;
				
		public CompanyDtoBuilder withId(String id) {
			this.id = id;
			return this;
		}
		
		public CompanyDtoBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public CompanyDtoBuilder withCompany(Company company) {
			id = company.getId().toString();
			name = company.getName();
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
