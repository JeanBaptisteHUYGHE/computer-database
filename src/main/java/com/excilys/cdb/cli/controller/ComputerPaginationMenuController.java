package com.excilys.cdb.cli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mapper.ComputerDtoMapper;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

@Controller
public class ComputerPaginationMenuController extends PaginationMenuController {
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerDtoMapper computerDtoMapper;


	@Override
	protected void updateDisplay() {
		try {
			List<Computer> computersList = computerService.getComputersListPage(page);
			List<ComputerDto> computersDtoList = computerDtoMapper.fromComputersListToComputersDtoList(computersList);
			paginationMenuView.drawComputersDtoPage(computersDtoList);
		} catch (DatabaseConnectionException e) {
			paginationMenuView.drawError("Cannot get computers list: " + e.getMessage());
		}
	}

}
