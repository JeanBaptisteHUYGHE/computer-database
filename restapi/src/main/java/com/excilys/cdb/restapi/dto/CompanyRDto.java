package com.excilys.cdb.restapi.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonRootName(value = "company")
public class CompanyRDto {

	private String id;
	private String name;
	
	private CompanyRDto() { }
	
	@JsonGetter("id")
	public String getId() {
		return id;
	}
	
	@JsonSetter("id")
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonGetter("name")
	public String getName() {
		return name;
	}
	
	@JsonSetter("name")
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return String.format("<CompanyRDto; id: %s, name:%s>", id, name);
	}
	
	public static class CompanyRDtoBuilder {
		
		private String id;
		private String name;
				
		public CompanyRDtoBuilder withId(String id) {
			if (id != null && id.isBlank()) {
				this.id = null;
			} else {
				this.id = id;
			}
			return this;
		}
		
		public CompanyRDtoBuilder withName(String name) {
			if (name != null && name.isBlank()) {
				this.name = null;
			} else {
				this.name = name;
			}
			return this;
		}
		
		public CompanyRDto build() {
			CompanyRDto companyRDto = new CompanyRDto();
			companyRDto.setId(id);
			companyRDto.setName(name);
			return companyRDto;
		}
	}
}
