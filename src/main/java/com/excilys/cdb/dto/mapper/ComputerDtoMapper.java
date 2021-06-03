package com.excilys.cdb.dto.mapper;

import com.excilys.cdb.model.Company.CompanyBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.ComputerDto.ComputerDtoBuilder;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

public class ComputerDtoMapper {
	
	private static ComputerDtoMapper computerDtoMapper;
	
	public static ComputerDtoMapper getInstance() {
		if (computerDtoMapper == null) {
			computerDtoMapper = new ComputerDtoMapper();
		}
		return computerDtoMapper;
	}
	
	private ComputerDtoMapper() { }
	
	public Computer fromComputerDtoToComputer(ComputerDto computerDto) {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		if (computerDto.getId() != null) {
			computerBuilder.withId(Integer.valueOf(computerDto.getId()));
		}
		computerBuilder.withName(computerDto.getName());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
		LocalDate introductionDate = null;
		if (computerDto.getIntroductionDate() != null && computerDto.getIntroductionDate().length() > 0) {
			introductionDate = LocalDate.parse(computerDto.getIntroductionDate(), formatter);
		}
		computerBuilder.withIntroductionDate(introductionDate);
		
		LocalDate discontinueDate = null;
		if (computerDto.getDiscontinueDate() != null && computerDto.getDiscontinueDate().length() > 0) {
			discontinueDate = LocalDate.parse(computerDto.getDiscontinueDate(), formatter);
		}
		computerBuilder.withDiscontinueDate(discontinueDate);
		
		
		if (computerDto.getCompanyId() != null || computerDto.getCompanyName() != null) {
			CompanyBuilder companyBuilder = new CompanyBuilder();
			if (computerDto.getCompanyId() != null) {
				companyBuilder.withId(Integer.valueOf(computerDto.getCompanyId()));
			}
			companyBuilder.withName(computerDto.getCompanyName());
			computerBuilder.withCompany(companyBuilder.build());
		}
		return computerBuilder.build();
		
	}
	
	public ComputerDto fromComputerToComputerDto(Computer computer) {
		ComputerDtoBuilder computerDtoBuilder = new ComputerDtoBuilder();
		
		if (computer.getId().isPresent()) {
			computerDtoBuilder.withId(computer.getId().get().toString());
		}
		computerDtoBuilder.withName(computer.getName());
		if (computer.getIntroductionDate().isPresent()) {
			computerDtoBuilder.withIntroductionDate(computer.getIntroductionDate().get().toString());
		}
		
		if (computer.getDiscontinueDate().isPresent()) {
			computerDtoBuilder.withDiscontinueDate(computer.getDiscontinueDate().get().toString());
		}
		
		if (computer.getCompany().isPresent()) {
			computerDtoBuilder.withCompanyId(computer.getCompany().get().getId().toString());
			computerDtoBuilder.withCompanyName(computer.getCompany().get().getName());
		}
		
		return computerDtoBuilder.build();
	}
	
	public List<ComputerDto> fromComputersListToComputersDtoList(List<Computer> computersList) {
		List<ComputerDto> computersDtoList = new ArrayList<ComputerDto>(computersList.size());
		for (Computer computer : computersList) {
			computersDtoList.add(fromComputerToComputerDto(computer));
		}
		return computersDtoList;
	}
}
