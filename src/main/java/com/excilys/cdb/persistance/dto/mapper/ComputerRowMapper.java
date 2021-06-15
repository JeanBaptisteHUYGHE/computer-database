package com.excilys.cdb.persistance.dto.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistance.dto.ComputerPDto;
import com.excilys.cdb.persistance.dto.ComputerPDto.ComputerPDtoBuilder;

@Component
public class ComputerRowMapper implements RowMapper<Computer> {
	
	private ComputerPDtoMapper computerDtoMapper;
	
	public ComputerRowMapper(ComputerPDtoMapper computerDtoMapper) {
		this.computerDtoMapper = computerDtoMapper;
	}

	@Override
	public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		ComputerPDtoBuilder computerPDtoBuilder = new ComputerPDtoBuilder()
				.withId(resultSet.getString("id"))
				.withName(resultSet.getString("name"))
				.withIntroductionDate(mapDateAndConvertFormat(resultSet, "introduced"))
				.withDiscontinueDate(mapDateAndConvertFormat(resultSet, "discontinued"))
				.withCompanyId(resultSet.getString("company_id"))
				.withCompanyName(resultSet.getString("company_name"));
				
		ComputerPDto computerPDto = computerPDtoBuilder.build();
		
		return computerDtoMapper.fromComputerPDtoToComputer(computerPDto);
	}
	
	private String mapDateAndConvertFormat(ResultSet resultSet, String attribut) throws SQLException {
		LocalDate localDate = null;
        try {
        	localDate = resultSet.getDate(attribut).toLocalDate();
        	return localDate.toString().replace('-', '/');
        } catch (NullPointerException e) {
        	return null;
        }
	}

}
