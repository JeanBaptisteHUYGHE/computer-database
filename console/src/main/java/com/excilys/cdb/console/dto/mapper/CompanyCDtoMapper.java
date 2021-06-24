package com.excilys.cdb.console.dto.mapper;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.cdb.console.dto.CompanyCDto;
import com.excilys.cdb.console.dto.ComputerCDto;
import com.excilys.cdb.console.dto.CompanyCDto.CompanyCDtoBuilder;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Company.CompanyBuilder;

@Component
public class CompanyCDtoMapper {
		
	private CompanyCDtoMapper() { }
	
	public CompanyCDto fromComputerDtoToCompanyDto(ComputerCDto computerCDto) {
		CompanyCDtoBuilder companyCDtoBuilder = new CompanyCDtoBuilder();
		companyCDtoBuilder.withId(computerCDto.getCompanyId());
		companyCDtoBuilder.withName(computerCDto.getCompanyName());
		return companyCDtoBuilder.build();
	}
	
	public Company fromCompanyCDtoToCompany(CompanyCDto companyCDto) throws IllegalArgumentException, NumberFormatException, DateTimeParseException {
		CompanyBuilder companyBuilder = new CompanyBuilder();
		companyBuilder.withId(companyCDto.getId() == null ? null : Integer.valueOf(companyCDto.getId()));
		companyBuilder.withName(companyCDto.getName());
		return companyBuilder.build();
	}
	
	public CompanyCDto fromCompanyToCompanyCDto(Company company) {
		CompanyCDtoBuilder companyCDtoBuilder = new CompanyCDtoBuilder();
		companyCDtoBuilder.withId(company.getId().toString());
		companyCDtoBuilder.withName(company.getName());
		return companyCDtoBuilder.build();
	}
	
	public List<CompanyCDto> fromCompaniesListToCompaniesCDtoList(List<Company> companiesList) {
		List<CompanyCDto> companiesCDtoList = new ArrayList<CompanyCDto>(companiesList.size());
		for (Company company : companiesList) {
			companiesCDtoList.add(fromCompanyToCompanyCDto(company));
		}
		return companiesCDtoList;
	}
}
