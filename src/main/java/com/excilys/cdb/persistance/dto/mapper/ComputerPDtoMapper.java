package com.excilys.cdb.persistance.dto.mapper;

import com.excilys.cdb.model.Company.CompanyBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistance.dto.ComputerPDto;
import com.excilys.cdb.persistance.dto.ComputerPDto.ComputerPDtoBuilder;

@Component
public class ComputerPDtoMapper {
		
	public Computer fromComputerPDtoToComputer(ComputerPDto computerPDto) {
	
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		if (computerPDto.getId() != null) {
			computerBuilder.withId(Integer.valueOf(computerPDto.getId()));
		}
		computerBuilder.withName(computerPDto.getName());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
		LocalDate introductionDate = null;
		if (computerPDto.getIntroductionDate() != null && computerPDto.getIntroductionDate().length() > 0) {
			introductionDate = LocalDate.parse(computerPDto.getIntroductionDate(), formatter);
		}
		computerBuilder.withIntroductionDate(introductionDate);
		
		LocalDate discontinueDate = null;
		if (computerPDto.getDiscontinueDate() != null && computerPDto.getDiscontinueDate().length() > 0) {
			discontinueDate = LocalDate.parse(computerPDto.getDiscontinueDate(), formatter);
		}
		computerBuilder.withDiscontinueDate(discontinueDate);
		
		
		if (computerPDto.getCompanyId() != null || computerPDto.getCompanyName() != null) {
			CompanyBuilder companyBuilder = new CompanyBuilder();
			if (computerPDto.getCompanyId() != null) {
				companyBuilder.withId(Integer.valueOf(computerPDto.getCompanyId()));
			}
			companyBuilder.withName(computerPDto.getCompanyName());
			computerBuilder.withCompany(companyBuilder.build());
		}
		return computerBuilder.build();		
	}
	
	public ComputerPDto fromComputerToComputerPDto(Computer computer) {
		ComputerPDtoBuilder computerPDtoBuilder = new ComputerPDtoBuilder();
		
		if (computer.getId().isPresent()) {
			computerPDtoBuilder.withId(computer.getId().get().toString());
		}
		computerPDtoBuilder.withName(computer.getName());
		if (computer.getIntroductionDate().isPresent()) {
			computerPDtoBuilder.withIntroductionDate(computer.getIntroductionDate().get().toString());
		}
		
		if (computer.getDiscontinueDate().isPresent()) {
			computerPDtoBuilder.withDiscontinueDate(computer.getDiscontinueDate().get().toString());
		}
		
		if (computer.getCompany().isPresent()) {
			computerPDtoBuilder.withCompanyId(computer.getCompany().get().getId().toString());
			computerPDtoBuilder.withCompanyName(computer.getCompany().get().getName());
		}
		
		return computerPDtoBuilder.build();
	}
}
