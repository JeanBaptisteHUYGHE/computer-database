package com.excilys.cdb.ui.page;

import static java.lang.Math.max;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

public class ComputersPage extends AbstPage {
		
	/**
	 * Draw the current page of computer list
	 * @throws SQLException
	 */
	@Override
	public void drawPage() throws SQLException {
		List<Computer> computersList = ComputerService.getInstance().getComputersListPage(this.pageIndex, PAGE_SIZE);
		System.out.println("ID\tNAME                                                      \t"
				+ "INTRODUCTION DATE\tDISCONTINUE DATE\tMANUFACTURER ID\tMANUFACTURER NAME");
		for (Computer computer : computersList) {
			Integer companyId = null;
			String companyName = null;
			if (computer.getManufacturer().isPresent()) {
				companyId = computer.getManufacturer().get().getId();
				companyName = computer.getManufacturer().get().getName();
			}
			System.out.println(String.format("%-6s\t%-60s\t%-20s\t%-20s\t%-15s\t%s",
					computer.getId().orElse(null),
					computer.getName(),
					computer.getIntroductionDate().orElse(null),
					computer.getDiscontinueDate().orElse(null),
					companyId,
					companyName
					)
				);
		}
	}
}
