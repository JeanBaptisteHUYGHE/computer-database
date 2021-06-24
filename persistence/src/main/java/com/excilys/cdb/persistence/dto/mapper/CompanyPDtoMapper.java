package com.excilys.cdb.persistence.dto.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Company.CompanyBuilder;
import com.excilys.cdb.persistence.dto.CompanyPDto;
import com.excilys.cdb.persistence.dto.CompanyPDto.CompanyPDtoBuilder;

@Component
public class CompanyPDtoMapper {
			
	public Company fromCompanyPDtoToCompany(CompanyPDto companyPDto) {
		CompanyBuilder companyBuilder = new CompanyBuilder();
		companyBuilder.withId(companyPDto.getId() == null ? null : Integer.valueOf(companyPDto.getId()));
		companyBuilder.withName(companyPDto.getName());
		return companyBuilder.build();
	}
	
	public CompanyPDto fromCompanyToCompanyPDto(Company company) {
		CompanyPDtoBuilder companyPDtoBuilder = new CompanyPDtoBuilder();
		companyPDtoBuilder.withId(company.getId());
		companyPDtoBuilder.withName(company.getName());
		return companyPDtoBuilder.build();
	}
}
