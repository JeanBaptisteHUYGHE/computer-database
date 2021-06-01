package com.excilys.cdb.cli.controller;

import java.util.List;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mapper.ComputerDtoMapper;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

public class ComputerPaginationMenuController extends PaginationMenuController {
	
	private ComputerService computerService;
	private ComputerDtoMapper computerDtoMapper;

	public ComputerPaginationMenuController(Page page) {
		super(page);
		computerService = ComputerService.getInstance();
		computerDtoMapper = ComputerDtoMapper.getInstance();
		start();
	}

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
