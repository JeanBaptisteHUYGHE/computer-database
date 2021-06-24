package com.excilys.cdb.console.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.console.dto.CompanyCDto;
import com.excilys.cdb.console.dto.mapper.CompanyCDtoMapper;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;
import com.excilys.cdb.service.CompanyService;

@Controller
public class CompanyPaginationMenuController extends PaginationMenuController {
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyCDtoMapper companyCDtoMapper;

	@Override
	protected void updateDisplay() {
		try {
			List<Company> companiesList = companyService.getCompaniesListPage(page);
			List<CompanyCDto> companiesCDtoList = companyCDtoMapper.fromCompaniesListToCompaniesCDtoList(companiesList);
			paginationMenuView.drawCompaniesCDtoPage(companiesCDtoList);
		} catch (DatabaseConnectionException e) {
			paginationMenuView.drawError("Cannot get companies list: " + e.getMessage());
		}
	}
}
