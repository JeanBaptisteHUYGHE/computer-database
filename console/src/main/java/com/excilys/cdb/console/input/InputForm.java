package com.excilys.cdb.console.input;

import com.excilys.cdb.console.dto.ComputerCDto;
import com.excilys.cdb.console.dto.ComputerCDto.ComputerCDtoBuilder;

public class InputForm {

	/**
	 * Return the computer cdto write in console by user.
	 * @return the correspondent computer cdto
	 * @throws IllegalArgumentException
	 */
	public static ComputerCDto readComputerCDto() {
		ComputerCDtoBuilder computerCDtoBuilder = new ComputerCDtoBuilder();
		
		System.out.println("Computer name ?");
		computerCDtoBuilder.withName(Input.readStringOrNull());
		
		System.out.println("Introduction date ?");
		computerCDtoBuilder.withIntroductionDate(Input.readLocalDateOrNull());
		
		System.out.println("Discontinue date ?");
		computerCDtoBuilder.withDiscontinueDate(Input.readLocalDateOrNull());
		
		System.out.println("Manufacturer id ?");
		computerCDtoBuilder.withCompanyId(Input.readStringOrNull());
		return computerCDtoBuilder.build();
	}
}
