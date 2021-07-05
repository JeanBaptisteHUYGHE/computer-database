package com.excilys.cdb.restapi.dto.mapper;

import com.excilys.cdb.core.Company.CompanyBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.cdb.restapi.dto.ComputerRDto;
import com.excilys.cdb.restapi.dto.ComputerRDto.ComputerRDtoBuilder;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Computer.ComputerBuilder;

@Component
public class ComputerRDtoMapper {
	
	private ComputerRDtoMapper() { }
	
	public Computer fromComputerWRtoToComputer(ComputerRDto computerRDto) {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		if (computerRDto.getId() != null) {
			computerBuilder.withId(Integer.valueOf(computerRDto.getId()));
		}
		computerBuilder.withName(computerRDto.getName());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		LocalDate introductionDate = null;
		if (computerRDto.getIntroductionDate() != null && computerRDto.getIntroductionDate().length() > 0) {
			introductionDate = LocalDate.parse(computerRDto.getIntroductionDate(), formatter);
		}
		computerBuilder.withIntroductionDate(introductionDate);
		
		LocalDate discontinueDate = null;
		if (computerRDto.getDiscontinueDate() != null && computerRDto.getDiscontinueDate().length() > 0) {
			discontinueDate = LocalDate.parse(computerRDto.getDiscontinueDate(), formatter);
		}
		computerBuilder.withDiscontinueDate(discontinueDate);
		
		
		if (computerRDto.getCompanyId() != null || computerRDto.getCompanyName() != null) {
			CompanyBuilder companyBuilder = new CompanyBuilder();
			if (computerRDto.getCompanyId() != null) {
				companyBuilder.withId(Integer.valueOf(computerRDto.getCompanyId()));
			}
			companyBuilder.withName(computerRDto.getCompanyName());
			computerBuilder.withCompany(companyBuilder.build());
		}
		return computerBuilder.build();
		
	}
	
	public ComputerRDto fromComputerToComputerRDto(Computer computer) {
		ComputerRDtoBuilder computerRDtoBuilder = new ComputerRDtoBuilder();
		
		if (computer.getId().isPresent()) {
			computerRDtoBuilder.withId(computer.getId().get().toString());
		}
		computerRDtoBuilder.withName(computer.getName());
		if (computer.getIntroductionDate().isPresent()) {
			computerRDtoBuilder.withIntroductionDate(computer.getIntroductionDate().get().toString());
		}
		
		if (computer.getDiscontinueDate().isPresent()) {
			computerRDtoBuilder.withDiscontinueDate(computer.getDiscontinueDate().get().toString());
		}
		
		if (computer.getCompany().isPresent()) {
			computerRDtoBuilder.withCompanyId(computer.getCompany().get().getId().toString());
			computerRDtoBuilder.withCompanyName(computer.getCompany().get().getName());
		}
		
		return computerRDtoBuilder.build();
	}
	
	public List<ComputerRDto> fromComputersListToComputersRDtoList(List<Computer> computersList) {
		List<ComputerRDto> computersRDtoList = new ArrayList<ComputerRDto>(computersList.size());
		for (Computer computer : computersList) {
			computersRDtoList.add(fromComputerToComputerRDto(computer));
		}
		return computersRDtoList;
	}
}
