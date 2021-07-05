package com.excilys.cdb.restapi.dto.mapper;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.cdb.restapi.dto.CompanyRDto;
import com.excilys.cdb.restapi.dto.ComputerRDto;
import com.excilys.cdb.restapi.dto.CompanyRDto.CompanyRDtoBuilder;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Company.CompanyBuilder;

@Component
public class CompanyRDtoMapper {
		
	private CompanyRDtoMapper() { }
	
	public CompanyRDto fromComputerRDtoToCompanyRDto(ComputerRDto computerRDto) {
		CompanyRDtoBuilder companyRDtoBuilder = new CompanyRDtoBuilder();
		companyRDtoBuilder.withId(computerRDto.getCompanyId());
		companyRDtoBuilder.withName(computerRDto.getCompanyName());
		return companyRDtoBuilder.build();
	}
	
	public Company fromCompanyRDtoToCompany(CompanyRDto companyRDto) throws IllegalArgumentException, NumberFormatException, DateTimeParseException {
		CompanyBuilder companyBuilder = new CompanyBuilder();
		companyBuilder.withId(companyRDto.getId() == null ? null : Integer.valueOf(companyRDto.getId()));
		companyBuilder.withName(companyRDto.getName());
		return companyBuilder.build();
	}
	
	public CompanyRDto fromCompanyToCompanyRDto(Company company) {
		CompanyRDtoBuilder companyRDtoBuilder = new CompanyRDtoBuilder();
		companyRDtoBuilder.withId(company.getId().toString());
		companyRDtoBuilder.withName(company.getName());
		return companyRDtoBuilder.build();
	}
	
	public List<CompanyRDto> fromCompaniesListToCompaniesRDtoList(List<Company> companiesList) {
		List<CompanyRDto> companiesRDtoList = new ArrayList<CompanyRDto>(companiesList.size());
		for (Company company : companiesList) {
			companiesRDtoList.add(fromCompanyToCompanyRDto(company));
		}
		return companiesRDtoList;
	}
}
