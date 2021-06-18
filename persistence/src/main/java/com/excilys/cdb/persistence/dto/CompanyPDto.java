package com.excilys.cdb.persistence.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "company")
public class CompanyPDto {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;
	
	private String name;
	
	@OneToMany(targetEntity = ComputerPDto.class, mappedBy = "companyPDto")
	private List<ComputerPDto> computersPDtoList = new ArrayList<>();;
	
	private CompanyPDto() { }
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
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
		
		private Integer id;
		private String name;
				
		public CompanyPDtoBuilder withId(Integer id) {
			this.id = id;
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
