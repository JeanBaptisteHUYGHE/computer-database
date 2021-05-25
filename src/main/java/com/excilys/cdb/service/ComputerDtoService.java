package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.ComputerDto.ComputerDtoBuilder;
import com.excilys.cdb.model.Computer;

public class ComputerDtoService {
	
	private static ComputerDtoService computerDtoService = null;
	private Logger logger;
	
	public static ComputerDtoService getInstance() {
		if (computerDtoService == null) {
			computerDtoService = new ComputerDtoService();
		}
		return computerDtoService;
	}
	
	private ComputerDtoService() {
		logger = LoggerFactory.getLogger(ComputerDtoService.class);
	}

	/**
	 * Return a list of computer dto by page.
	 * @param pageIndex page index
	 * @param pageSize page size
	 * @return the computer dto list
	 * @throws SQLException
	 */
	public List<ComputerDto> getComputersListPage(int pageIndex, int pageSize) throws SQLException {
		logger.debug("getComputersListPage({}, {})", pageIndex, pageSize);
		List<Computer> computersList = ComputerService.getInstance().getComputersListPage(pageIndex, pageSize);
		List<ComputerDto> computersDtoList = new ArrayList<ComputerDto>(computersList.size());
		ComputerDtoBuilder computerDtoBuilder = new ComputerDtoBuilder();
		for (Computer computer : computersList) {
			computersDtoList.add(computerDtoBuilder.withComputer(computer).build());
		}
		return computersDtoList;
	}
}
