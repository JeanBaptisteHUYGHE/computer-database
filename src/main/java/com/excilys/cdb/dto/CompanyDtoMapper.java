package com.excilys.cdb.dto;

import java.time.format.DateTimeParseException;

import com.excilys.cdb.dto.CompanyDto.CompanyDtoBuilder;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Company.CompanyBuilder;


public class CompanyDtoMapper {
	
private static CompanyDtoMapper companyDtoMapper = null;
	
	public static CompanyDtoMapper getInstance() {
		if (companyDtoMapper == null) {
			companyDtoMapper = new CompanyDtoMapper();
		}
		return companyDtoMapper;
	}
	
	private CompanyDtoMapper() { }
	
	public CompanyDto getCompanyDto(ComputerDto computerDto) {
		CompanyDtoBuilder companyDtoBuilder = new CompanyDtoBuilder();
		companyDtoBuilder.withId(computerDto.getCompanyId());
		companyDtoBuilder.withName(computerDto.getCompanyName());
		return companyDtoBuilder.build();
	}
	
	public Company getCompany(CompanyDto companyDto) throws IllegalArgumentException, NumberFormatException, DateTimeParseException {
		CompanyBuilder companyBuilder = new CompanyBuilder();
		companyBuilder.withId(companyDto.getId() == null ? null : Integer.valueOf(companyDto.getId()));
		companyBuilder.withName(companyDto.getName());
		return companyBuilder.build();
		
	}
}
