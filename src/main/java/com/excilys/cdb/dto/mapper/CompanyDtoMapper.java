package com.excilys.cdb.dto.mapper;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.CompanyDto.CompanyDtoBuilder;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Company.CompanyBuilder;


public class CompanyDtoMapper {
	
private static CompanyDtoMapper companyDtoMapper;
	
	public static CompanyDtoMapper getInstance() {
		if (companyDtoMapper == null) {
			companyDtoMapper = new CompanyDtoMapper();
		}
		return companyDtoMapper;
	}
	
	private CompanyDtoMapper() { }
	
	public CompanyDto fromComputerDtoToCompanyDto(ComputerDto computerDto) {
		CompanyDtoBuilder companyDtoBuilder = new CompanyDtoBuilder();
		companyDtoBuilder.withId(computerDto.getCompanyId());
		companyDtoBuilder.withName(computerDto.getCompanyName());
		return companyDtoBuilder.build();
	}
	
	public Company fromCompanyDtoToCompany(CompanyDto companyDto) throws IllegalArgumentException, NumberFormatException, DateTimeParseException {
		CompanyBuilder companyBuilder = new CompanyBuilder();
		companyBuilder.withId(companyDto.getId() == null ? null : Integer.valueOf(companyDto.getId()));
		companyBuilder.withName(companyDto.getName());
		return companyBuilder.build();
	}
	
	public CompanyDto fromCompanyToCompanyDto(Company company) {
		CompanyDtoBuilder companyDtoBuilder = new CompanyDtoBuilder();
		companyDtoBuilder.withId(company.getId().toString());
		companyDtoBuilder.withName(company.getName());
		return companyDtoBuilder.build();
	}
	
	public List<CompanyDto> fromCompaniesListToCompaniesDtoList(List<Company> companiesList) {
		List<CompanyDto> companiesDtoList = new ArrayList<CompanyDto>(companiesList.size());
		for (Company company : companiesList) {
			companiesDtoList.add(fromCompanyToCompanyDto(company));
		}
		return companiesDtoList;
	}
}
