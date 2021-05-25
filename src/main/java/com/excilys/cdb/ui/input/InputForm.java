package com.excilys.cdb.ui.input;

import java.time.LocalDate;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class InputForm {

	/**
	 * Return the computer write in console by user.
	 * @return the correspondent computer
	 * @throws IllegalArgumentException
	 */
	public static Computer readComputer() throws IllegalArgumentException {
		System.out.println("Computer name ?");
		String computerName = Input.readString();
		
		System.out.println("Introduction date ?");
		LocalDate computerIntroductionDate = Input.readValidLocalDate();
		
		System.out.println("Discontinue date ?");
		LocalDate computerDiscontinueDate = Input.readValidLocalDate();
		
		System.out.println("Manufacturer id ?");
		Integer companyId = Input.readValidIntegerOrNull();
		
		Company company = null;
		if (companyId != null) {
			company = new Company(companyId, null);
		}
		
		Computer newComputer = new Computer.ComputerBuilder().withName(computerName)
				.withIntroductionDate(computerIntroductionDate)
				.withDiscontinueDate(computerDiscontinueDate)
				.withManufacturer(company)
				.build();
		return newComputer;
	}
}
