package com.excilys.cdb.persistance.dto.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Company.CompanyBuilder;
import com.excilys.cdb.persistance.dto.CompanyPDto;
import com.excilys.cdb.persistance.dto.CompanyPDto.CompanyPDtoBuilder;

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
