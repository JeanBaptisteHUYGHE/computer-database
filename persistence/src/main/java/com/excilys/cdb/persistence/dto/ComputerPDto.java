package com.excilys.cdb.persistence.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "computer")
public class ComputerPDto {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;
	
	private String name;
	
	@Column(name = "introduced")
	private Date introductionDate;
	
	@Column(name = "discontinued")
	private Date discontinueDate;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private CompanyPDto companyPDto;	
	
	
	private ComputerPDto() { }

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

	public Date getIntroductionDate() {
		return introductionDate;
	}

	public void setIntroductionDate(Date introductionDate) {
		this.introductionDate = introductionDate;
	}

	public Date getDiscontinueDate() {
		return discontinueDate;
	}

	public void setDiscontinueDate(Date discontinueDate) {
		this.discontinueDate = discontinueDate;
	}
	
	public CompanyPDto getCompanyPDto() {
		return companyPDto;
	}

	public void setCompanyPDto(CompanyPDto companyPDto) {
		this.companyPDto = companyPDto;
	}

	public String toString() {
		return String.format("<ComputerPDto; id: %s, name:%s, introductionDate: %s, discontinueDate: %s, company: %s>",
				id, name, introductionDate, discontinueDate, companyPDto);
	}


	public static class ComputerPDtoBuilder {
		
		private Integer id;
		private String name;
		private Date introductionDate;
		private Date discontinueDate;
		private CompanyPDto companyPDto;
				
		public ComputerPDtoBuilder withId(Integer id) {
			this.id = id;
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
		
		public ComputerPDtoBuilder withIntroductionDate(Date introductionDate) {
			this.introductionDate = introductionDate;
			return this;
		}
		
		public ComputerPDtoBuilder withDiscontinueDate(Date discontinueDate) {
			this.discontinueDate = discontinueDate;
			return this;
		}
		
		public ComputerPDtoBuilder withCompany(CompanyPDto companyPDto) {
			this.companyPDto = companyPDto;
			return this;
		}
		
		public ComputerPDto build() {
			ComputerPDto computerPDto = new ComputerPDto();
			computerPDto.setId(id);
			computerPDto.setName(name);
			computerPDto.setIntroductionDate(introductionDate);
			computerPDto.setDiscontinueDate(discontinueDate);
			computerPDto.setCompanyPDto(companyPDto);
			return computerPDto;
		}
	}
}
