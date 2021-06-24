package com.excilys.cdb.persistence.dto.mapper;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.excilys.cdb.core.Company.CompanyBuilder;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.dto.CompanyPDto.CompanyPDtoBuilder;
import com.excilys.cdb.persistence.dto.ComputerPDto;
import com.excilys.cdb.persistence.dto.ComputerPDto.ComputerPDtoBuilder;

@Component
public class ComputerPDtoMapper {
		
	public Computer fromComputerPDtoToComputer(ComputerPDto computerPDto) {
	
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		if (computerPDto.getId() != null) {
			computerBuilder.withId(Integer.valueOf(computerPDto.getId()));
		}
		computerBuilder.withName(computerPDto.getName());
		

		LocalDate introductionDate = null;
		if (computerPDto.getIntroductionDate() != null) {
			introductionDate = computerPDto.getIntroductionDate().toLocalDate();
		}
		computerBuilder.withIntroductionDate(introductionDate);
		
		LocalDate discontinueDate = null;
		if (computerPDto.getDiscontinueDate() != null) {
			discontinueDate = computerPDto.getDiscontinueDate().toLocalDate();

		}
		computerBuilder.withDiscontinueDate(discontinueDate);
		
		if (computerPDto.getCompanyPDto() != null) {
			CompanyBuilder companyBuilder = new CompanyBuilder();
			if (computerPDto.getCompanyPDto().getId() != null) {
				companyBuilder.withId(Integer.valueOf(computerPDto.getCompanyPDto().getId()));
			}
			companyBuilder.withName(computerPDto.getCompanyPDto().getName());
			
			computerBuilder.withCompany(companyBuilder.build());
		}
		
		return computerBuilder.build();		
	}
	
	public ComputerPDto fromComputerToComputerPDto(Computer computer) {
		ComputerPDtoBuilder computerPDtoBuilder = new ComputerPDtoBuilder();
		
		if (computer.getId().isPresent()) {
			computerPDtoBuilder.withId(computer.getId().get());
		}
		computerPDtoBuilder.withName(computer.getName());
		if (computer.getIntroductionDate().isPresent()) {
			computerPDtoBuilder.withIntroductionDate(Date.valueOf(computer.getIntroductionDate().get().toString()));
		}
		
		if (computer.getDiscontinueDate().isPresent()) {
			computerPDtoBuilder.withDiscontinueDate(Date.valueOf(computer.getDiscontinueDate().get().toString()));
		}
		
		
		if (computer.getCompany().isPresent()) {
			CompanyPDtoBuilder companyPDtoBuilder = new CompanyPDtoBuilder();
			companyPDtoBuilder.withId(computer.getCompany().get().getId());
			companyPDtoBuilder.withName(computer.getCompany().get().getName());
			computerPDtoBuilder.withCompany(companyPDtoBuilder.build());
		}
		
		return computerPDtoBuilder.build();
	}
}
