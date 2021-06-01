package com.excilys.cdb.cli.controller;

import java.util.List;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.mapper.CompanyDtoMapper;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;

public class CompanyPaginationMenuController extends PaginationMenuController {
	
	private CompanyService companyService;
	private CompanyDtoMapper companyDtoMapper;

	public CompanyPaginationMenuController(Page page) {
		super(page);
		companyService = CompanyService.getInstance();
		companyDtoMapper = CompanyDtoMapper.getInstance();
		start();
	}

	@Override
	protected void updateDisplay() {
		try {
			List<Company> companiesList = companyService.getCompaniesListPage(page);
			List<CompanyDto> companiesDtoList = companyDtoMapper.fromCompaniesListToCompaniesDtoList(companiesList);
			paginationMenuView.drawCompaniesDtoPage(companiesDtoList);
		} catch (DatabaseConnectionException e) {
			paginationMenuView.drawError("Cannot get companies list: " + e.getMessage());
		}
	}
}
