package com.excilys.cdb.console.dto.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.cdb.console.dto.ComputerCDto;
import com.excilys.cdb.console.dto.ComputerCDto.ComputerCDtoBuilder;
import com.excilys.cdb.core.Company.CompanyBuilder;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Computer.ComputerBuilder;

@Component
public class ComputerCDtoMapper {
	
	private ComputerCDtoMapper() { }
	
	public Computer fromComputerCDtoToComputer(ComputerCDto computerCDto) {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		if (computerCDto.getId() != null) {
			computerBuilder.withId(Integer.valueOf(computerCDto.getId()));
		}
		computerBuilder.withName(computerCDto.getName());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		LocalDate introductionDate = null;
		if (computerCDto.getIntroductionDate() != null && computerCDto.getIntroductionDate().length() > 0) {
			introductionDate = LocalDate.parse(computerCDto.getIntroductionDate(), formatter);
		}
		computerBuilder.withIntroductionDate(introductionDate);
		
		LocalDate discontinueDate = null;
		if (computerCDto.getDiscontinueDate() != null && computerCDto.getDiscontinueDate().length() > 0) {
			discontinueDate = LocalDate.parse(computerCDto.getDiscontinueDate(), formatter);
		}
		computerBuilder.withDiscontinueDate(discontinueDate);
		
		
		if (computerCDto.getCompanyId() != null || computerCDto.getCompanyName() != null) {
			CompanyBuilder companyBuilder = new CompanyBuilder();
			if (computerCDto.getCompanyId() != null) {
				companyBuilder.withId(Integer.valueOf(computerCDto.getCompanyId()));
			}
			companyBuilder.withName(computerCDto.getCompanyName());
			computerBuilder.withCompany(companyBuilder.build());
		}
		return computerBuilder.build();
		
	}
	
	public ComputerCDto fromComputerToComputerCDto(Computer computer) {
		ComputerCDtoBuilder computerCDtoBuilder = new ComputerCDtoBuilder();
		
		if (computer.getId().isPresent()) {
			computerCDtoBuilder.withId(computer.getId().get().toString());
		}
		computerCDtoBuilder.withName(computer.getName());
		if (computer.getIntroductionDate().isPresent()) {
			computerCDtoBuilder.withIntroductionDate(computer.getIntroductionDate().get().toString());
		}
		
		if (computer.getDiscontinueDate().isPresent()) {
			computerCDtoBuilder.withDiscontinueDate(computer.getDiscontinueDate().get().toString());
		}
		
		if (computer.getCompany().isPresent()) {
			computerCDtoBuilder.withCompanyId(computer.getCompany().get().getId().toString());
			computerCDtoBuilder.withCompanyName(computer.getCompany().get().getName());
		}
		
		return computerCDtoBuilder.build();
	}
	
	public List<ComputerCDto> fromComputersListToComputersCDtoList(List<Computer> computersList) {
		List<ComputerCDto> computersCDtoList = new ArrayList<ComputerCDto>(computersList.size());
		for (Computer computer : computersList) {
			computersCDtoList.add(fromComputerToComputerCDto(computer));
		}
		return computersCDtoList;
	}
}
