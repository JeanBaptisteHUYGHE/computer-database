package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.excilys.cdb.controller.session.UserSession;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mapper.ComputerDtoMapper;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Page.PageBuilder;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/dashboard")
@SessionAttributes("userSession")
public class DashboardController {
	
	private static final int DEFAULT_COMPUTERS_COUNT = 0;
	
	private Logger logger;
	private ComputerService computerService;
	private ComputerDtoMapper computerDtoMapper;
	
	public DashboardController(ComputerService computerService, ComputerDtoMapper computerDtoMapper) {
		logger = LoggerFactory.getLogger(DashboardController.class);
		this.computerService = computerService;
		this.computerDtoMapper = computerDtoMapper;
	}
	
	@GetMapping
	public String getMapping(
			@RequestParam(name = "search", required = false, defaultValue = "") String search,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "pageSize", required = false) Integer requestedPageSize,
			@RequestParam(name = "errorsList", required = false, defaultValue = "") List<String> errorsList,
			UserSession userSession,
			Model model) {
		logger.info("getMapping(search={}, pageIndex={}, pageSize={}, userSession={})", search, pageIndex, requestedPageSize, userSession);
		
		Integer requestedPage = max(pageIndex, Page.MINIMUM_PAGE_INDEX);
		Integer pageSize = getPageSize(requestedPageSize, userSession);
		Integer computersCount = getComputersCountForSearch(search, errorsList);

		Page page = getPage(requestedPage, computersCount, pageSize);
		List<ComputerDto> computersDtoList = getComputersDtoListForSearch(search, page, errorsList);
				
		model.addAttribute("errorsList", errorsList);
		model.addAttribute("computersList", computersDtoList);
		model.addAttribute("search", search);
		model.addAttribute("computersCount", computersCount);
		model.addAttribute("page", page);

		return "/WEB-INF/jsp/dashboard.jsp";
	}
	
	@PostMapping
	public String postMapping(
			@RequestParam(name = "selection", required = false, defaultValue = "") String selection,
			Model model) {
		logger.debug("postMapping(selection={})", selection);
		
		List<String> errorsList = new ArrayList<String>(0);
		
		try {
			if (selection != null) {
				String[] stringsIds = selection.split(",");
				for (String stringId : stringsIds) {
					try {
						Integer id = Integer.valueOf(stringId);
						computerService.deleteComputer(id);
					} catch (NumberFormatException e) { }
				}
			}
		} catch (DatabaseConnectionException e) {
			errorsList.add("Computer(s) cannot be delete. " + e.getMessage());
		}
		
		model.addAttribute("errorsList", errorsList);
		return "redirect:/dashboard";
	}
	
	private Integer getPageSize(Integer requestedPageSize, UserSession userSession) {
		Integer pageSize;
		if (requestedPageSize == null && userSession.getPageSize() == null) {
			pageSize = Page.DEFAULT_PAGE_SIZE;
		} else {
			if (requestedPageSize != null) {
				pageSize = requestedPageSize;
			} else {
				pageSize = userSession.getPageSize();
			}
		}
		userSession.setPageSize(pageSize);
		return pageSize;
	}
	
	
	private Integer getComputersCountForSearch(String search, List<String> errorsList) {
		Integer computersCount = DEFAULT_COMPUTERS_COUNT;
		try {
			computersCount = computerService.getComputersCountForSearch(search);
		} catch (DatabaseConnectionException e) {
			errorsList.add("Error while getting the computers count. " + e.getMessage());
		}
		return computersCount;
	}
	
	private Page getPage(Integer requestedPage, Integer computersCount, Integer requestedPageSize) {
		PageBuilder pageBuilder = new PageBuilder()
				.withElementsCount(computersCount)
				.withIndex(requestedPage)
				.withSize(requestedPageSize);
		Page page = pageBuilder.build();
		return page;
	}
	
	private List<ComputerDto> getComputersDtoListForSearch(String search, Page page, List<String> errorsList) {
		List<ComputerDto> computersDtoList;
		try {
			List<Computer> computersList = computerService.getComputersListPageForSearch(search, page);
			computersDtoList = computerDtoMapper.fromComputersListToComputersDtoList(computersList);

		} catch (DatabaseConnectionException e) {
			computersDtoList = new ArrayList<ComputerDto>();
			errorsList.add("Error while getting the computers list. " + e.getMessage());
		}
		return computersDtoList;
	}
}

