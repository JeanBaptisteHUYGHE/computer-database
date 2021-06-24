package com.excilys.cdb.webapp.dto.mapper;

import com.excilys.cdb.core.Company.CompanyBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.cdb.webapp.dto.ComputerWDto;
import com.excilys.cdb.webapp.dto.ComputerWDto.ComputerWDtoBuilder;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Computer.ComputerBuilder;

@Component
public class ComputerWDtoMapper {
	
	private ComputerWDtoMapper() { }
	
	public Computer fromComputerWDtoToComputer(ComputerWDto computerWDto) {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		if (computerWDto.getId() != null) {
			computerBuilder.withId(Integer.valueOf(computerWDto.getId()));
		}
		computerBuilder.withName(computerWDto.getName());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		LocalDate introductionDate = null;
		if (computerWDto.getIntroductionDate() != null && computerWDto.getIntroductionDate().length() > 0) {
			introductionDate = LocalDate.parse(computerWDto.getIntroductionDate(), formatter);
		}
		computerBuilder.withIntroductionDate(introductionDate);
		
		LocalDate discontinueDate = null;
		if (computerWDto.getDiscontinueDate() != null && computerWDto.getDiscontinueDate().length() > 0) {
			discontinueDate = LocalDate.parse(computerWDto.getDiscontinueDate(), formatter);
		}
		computerBuilder.withDiscontinueDate(discontinueDate);
		
		
		if (computerWDto.getCompanyId() != null || computerWDto.getCompanyName() != null) {
			CompanyBuilder companyBuilder = new CompanyBuilder();
			if (computerWDto.getCompanyId() != null) {
				companyBuilder.withId(Integer.valueOf(computerWDto.getCompanyId()));
			}
			companyBuilder.withName(computerWDto.getCompanyName());
			computerBuilder.withCompany(companyBuilder.build());
		}
		return computerBuilder.build();
		
	}
	
	public ComputerWDto fromComputerToComputerWDto(Computer computer) {
		ComputerWDtoBuilder computerWDtoBuilder = new ComputerWDtoBuilder();
		
		if (computer.getId().isPresent()) {
			computerWDtoBuilder.withId(computer.getId().get().toString());
		}
		computerWDtoBuilder.withName(computer.getName());
		if (computer.getIntroductionDate().isPresent()) {
			computerWDtoBuilder.withIntroductionDate(computer.getIntroductionDate().get().toString());
		}
		
		if (computer.getDiscontinueDate().isPresent()) {
			computerWDtoBuilder.withDiscontinueDate(computer.getDiscontinueDate().get().toString());
		}
		
		if (computer.getCompany().isPresent()) {
			computerWDtoBuilder.withCompanyId(computer.getCompany().get().getId().toString());
			computerWDtoBuilder.withCompanyName(computer.getCompany().get().getName());
		}
		
		return computerWDtoBuilder.build();
	}
	
	public List<ComputerWDto> fromComputersListToComputersWDtoList(List<Computer> computersList) {
		List<ComputerWDto> computersWDtoList = new ArrayList<ComputerWDto>(computersList.size());
		for (Computer computer : computersList) {
			computersWDtoList.add(fromComputerToComputerWDto(computer));
		}
		return computersWDtoList;
	}
}
