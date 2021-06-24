package com.excilys.cdb.webapp.dto.mapper;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.cdb.webapp.dto.CompanyWDto;
import com.excilys.cdb.webapp.dto.ComputerWDto;
import com.excilys.cdb.webapp.dto.CompanyWDto.CompanyWDtoBuilder;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Company.CompanyBuilder;

@Component
public class CompanyWDtoMapper {
		
	private CompanyWDtoMapper() { }
	
	public CompanyWDto fromComputerWDtoToCompanyWDto(ComputerWDto computerWDto) {
		CompanyWDtoBuilder companyWDtoBuilder = new CompanyWDtoBuilder();
		companyWDtoBuilder.withId(computerWDto.getCompanyId());
		companyWDtoBuilder.withName(computerWDto.getCompanyName());
		return companyWDtoBuilder.build();
	}
	
	public Company fromCompanyWDtoToCompany(CompanyWDto companyWDto) throws IllegalArgumentException, NumberFormatException, DateTimeParseException {
		CompanyBuilder companyBuilder = new CompanyBuilder();
		companyBuilder.withId(companyWDto.getId() == null ? null : Integer.valueOf(companyWDto.getId()));
		companyBuilder.withName(companyWDto.getName());
		return companyBuilder.build();
	}
	
	public CompanyWDto fromCompanyToCompanyWDto(Company company) {
		CompanyWDtoBuilder companyWDtoBuilder = new CompanyWDtoBuilder();
		companyWDtoBuilder.withId(company.getId().toString());
		companyWDtoBuilder.withName(company.getName());
		return companyWDtoBuilder.build();
	}
	
	public List<CompanyWDto> fromCompaniesListToCompaniesWDtoList(List<Company> companiesList) {
		List<CompanyWDto> companiesWDtoList = new ArrayList<CompanyWDto>(companiesList.size());
		for (Company company : companiesList) {
			companiesWDtoList.add(fromCompanyToCompanyWDto(company));
		}
		return companiesWDtoList;
	}
}
