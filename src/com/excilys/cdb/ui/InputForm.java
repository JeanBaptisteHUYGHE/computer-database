package com.excilys.cdb.ui;

import java.time.LocalDate;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistance.ComputerDao;

public class InputForm {

	public static Computer readComputer() throws IllegalArgumentException{
		System.out.println("Computer name ?");
		String computerName = Input.readString();
		
		System.out.println("Introduction date ?");
		LocalDate computerIntroductionDate = Input.readValidLocalDate();
		
		System.out.println("Discontinue date ?");
		LocalDate computerDiscontinueDate = Input.readValidLocalDate();
		
		System.out.println("Manufacturer id?");
		Integer companyId = Input.readValidIntegerOrNull();
		
		Computer newComputer = new Computer(computerName, computerIntroductionDate,
					computerDiscontinueDate, new Company(companyId, ""));
		return newComputer;
	}
}