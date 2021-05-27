package com.excilys.cdb.dto;

import java.time.format.DateTimeParseException;

import com.excilys.cdb.model.Company.CompanyBuilder;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

public class ComputerDtoMapper {
	
	private static ComputerDtoMapper computerDtoMapper = null;
	
	public static ComputerDtoMapper getInstance() {
		if (computerDtoMapper == null) {
			computerDtoMapper = new ComputerDtoMapper();
		}
		return computerDtoMapper;
	}
	
	private ComputerDtoMapper() { }
	
	public Computer getComputer(ComputerDto computerDto) throws IllegalArgumentException, NumberFormatException, DateTimeParseException {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.withId(computerDto.getId() == null ? null : Integer.valueOf(computerDto.getId()));
		computerBuilder.withName(computerDto.getName());
		computerBuilder.withIntroductionDate(computerDto.getIntroductionDate());
		computerBuilder.withDiscontinueDate(computerDto.getDiscontinueDate());
		CompanyBuilder companyBuilder = new CompanyBuilder();
		companyBuilder.withId(computerDto.getCompanyId() == null ? null : Integer.valueOf(computerDto.getCompanyId()));
		companyBuilder.withName(computerDto.getCompanyName());
		computerBuilder.withManufacturer(companyBuilder.build());
		return computerBuilder.build();
		
	}
}
