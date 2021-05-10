package com.excilys.cdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistance.CompanyDao;
import com.excilys.cdb.persistance.ComputerDao;
import com.excilys.cdb.persistance.Database;
import com.excilys.cdb.ui.CommandLineInterface;

public class Main {

	public static void main(String[] args) {
		
		
		/*
		System.out.println(new Computer(1, "ROG Strix GL703VM"));		
		System.out.println(new Computer("ROG Strix GL703VM", new LocalDate(), new LocalDate(), new Company("ASUS")));
		*/
		
		//try {
			/*
			System.out.println(CompanyDao.getCompaniesList());
			System.out.println(ComputerDao.getComputersList());
			
			Computer newComputer = new Computer("My beautifull PC", null, null, 
					new Company(1, "My beautifull company"));
			System.out.println(newComputer);
			ComputerDao.addComputer(newComputer);
			System.out.println(newComputer);
			*/
			/*Computer myComputer = new Computer(577, "My beautifull PC", null, null, 
					new Company(1, "My beautifull company"));
			ComputerDao.deleteComputer(myComputer);
			*/
			
			/*
			// get
			Computer myComputer = new Computer(1, "My beautifull PC", null, null, 
			new Company(1, "My beautifull company"));
			System.out.println(ComputerDao.getComputer(myComputer));
			*/
			
			/*
			// update
			LocalDate ld1 = LocalDate.of(2018, 04, 15);
			LocalDate ld2 = LocalDate.now();
			Computer myComputer = new Computer(579, "ThinkPad", ld1, ld2, 
				new Company(2, "My beautifull company"));
			ComputerDao.updateComputer(myComputer);
			*/
			
			


		/*} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}*/
		
		CommandLineInterface cli = new CommandLineInterface();
	}

}
