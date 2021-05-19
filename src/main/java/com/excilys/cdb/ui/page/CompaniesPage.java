package com.excilys.cdb.ui.page;

import static java.lang.Math.max;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class CompaniesPage extends AbstPage {
		
	/**
	 * Draw the current page of company list
	 * @throws SQLException
	 */
	@Override
	public void drawPage() throws SQLException {
		List<Company> companiesList = CompanyService.getInstance().getCompaniesListPage(this.pageIndex, PAGE_SIZE);
		System.out.println("ID\tNAME");
		for (Company company : companiesList) {
			System.out.println(String.format("%s\t%s",company.getId(), company.getName()));
		}
	}
}
