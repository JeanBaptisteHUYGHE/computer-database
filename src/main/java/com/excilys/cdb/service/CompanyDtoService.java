package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.CompanyDto.CompanyDtoBuilder;
import com.excilys.cdb.model.Company;

public class CompanyDtoService {
	
	private static CompanyDtoService companyDtoService = null;
	private Logger logger;
	
	public static CompanyDtoService getInstance() {
		if (companyDtoService == null) {
			companyDtoService = new CompanyDtoService();
		}
		return companyDtoService;
	}
	
	private CompanyDtoService() {
		logger = LoggerFactory.getLogger(CompanyDtoService.class);
	}

	/**
	 * Return a list of company dto by page.
	 * @return the company dto list
	 * @throws SQLException
	 */
	public List<CompanyDto> getCompaniesDtoList() throws SQLException {
		logger.debug("getCompaniesDtoList()");
		List<Company> companiesList = CompanyService.getInstance().getCompaniesList();
		List<CompanyDto> companiesDtoList = new ArrayList<CompanyDto>(companiesList.size());
		CompanyDtoBuilder companyDtoBuilder = new CompanyDtoBuilder();
		for (Company company : companiesList) {
			companiesDtoList.add(companyDtoBuilder.withCompany(company).build());
		}
		return companiesDtoList;
	}
}
