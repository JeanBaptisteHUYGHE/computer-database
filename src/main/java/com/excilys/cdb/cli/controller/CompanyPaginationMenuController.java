package com.excilys.cdb.cli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.mapper.CompanyDtoMapper;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

@Controller
public class CompanyPaginationMenuController extends PaginationMenuController {
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyDtoMapper companyDtoMapper;

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
