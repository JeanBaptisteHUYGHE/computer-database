package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Page.PageBuilder;
import com.excilys.cdb.service.ComputerDtoService;
import com.excilys.cdb.service.ComputerService;

public class DashboardController {

	private ComputerDtoService computerDtoService;
	private ComputerService computerService;
	private Logger logger;
	
	private List<ComputerDto> computersDtoList;
	private Integer computersCount;
	private Page page;
	
	public DashboardController() {
		computerDtoService = ComputerDtoService.getInstance();
		computerService = ComputerService.getInstance();
		logger = LoggerFactory.getLogger(DashboardController.class);
	}

	public void setRequestedPage(String strRequestedPage) {
		Integer requestedPage = Page.MINIMUM_PAGE_INDEX;
		try {
			requestedPage = Integer.valueOf(strRequestedPage);
		}
		catch (NumberFormatException e) { }
		computeAttributes(requestedPage);
	}

	public List<ComputerDto> getComputersDtoList() {
		return computersDtoList;
	}

	public Integer getComputersCount() {
		return computersCount;
	}

	public Page getPage() {
		return page;
	}
	
	/**
	 * Do the controller logic and compute attributes require by the view
	 * @param requestedPage
	 */
	private void computeAttributes(Integer requestedPage) {
		try {
			computersCount = computerService.getComputerCount();
			PageBuilder pageBuilder = new PageBuilder();
			pageBuilder.withElementsCount(computersCount);
			pageBuilder.withPageIndex(requestedPage);
			page = pageBuilder.build();
			logger.info(page.toString());
			computersDtoList = computerDtoService.getComputersListPage(page.getPageIndex(), page.getPageSize());
		} catch (SQLException e) {
			computersDtoList = new ArrayList<ComputerDto>();
			computersCount = -1;
			logger.error("{} in {}", e, e.getStackTrace());
		}
	}
}
