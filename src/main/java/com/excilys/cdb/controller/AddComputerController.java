package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.ComputerDtoMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyDtoService;
import com.excilys.cdb.service.ComputerService;

public class AddComputerController {

	private Logger logger;
	
	public AddComputerController() {
		logger = LoggerFactory.getLogger(AddComputerController.class);
	}
	
	public List<CompanyDto> getCompaniesDtoList() throws SQLException {
		return CompanyDtoService.getInstance().getCompaniesDtoList();
	}

	public void addComputer(ComputerDto computerDto) throws IllegalArgumentException, DateTimeParseException, SQLException {
		ComputerDtoMapper computerDtoMapper = ComputerDtoMapper.getInstance();
		Computer computer = computerDtoMapper.getComputer(computerDto);
		ComputerService computerService = ComputerService.getInstance();
		computerService.addComputer(computer);
	}
	
	
}
