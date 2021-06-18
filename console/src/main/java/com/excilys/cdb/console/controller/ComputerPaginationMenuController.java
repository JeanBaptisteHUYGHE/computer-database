package com.excilys.cdb.console.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.console.dto.ComputerCDto;
import com.excilys.cdb.console.dto.mapper.ComputerCDtoMapper;
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.service.ComputerService;

@Controller
public class ComputerPaginationMenuController extends PaginationMenuController {
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerCDtoMapper computerCDtoMapper;


	@Override
	protected void updateDisplay() {
		try {
			List<Computer> computersList = computerService.getComputersListPage(page);
			List<ComputerCDto> computersCDtoList = computerCDtoMapper.fromComputersListToComputersCDtoList(computersList);
			paginationMenuView.drawComputersCDtoPage(computersCDtoList);
		} catch (DatabaseConnectionException e) {
			paginationMenuView.drawError("Cannot get computers list: " + e.getMessage());
		}
	}

}
