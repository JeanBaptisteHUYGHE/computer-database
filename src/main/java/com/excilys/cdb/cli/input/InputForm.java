package com.excilys.cdb.cli.input;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.ComputerDto.ComputerDtoBuilder;

public class InputForm {

	/**
	 * Return the computer dto write in console by user.
	 * @return the correspondent computer dto
	 * @throws IllegalArgumentException
	 */
	public static ComputerDto readComputerDto() {
		ComputerDtoBuilder computerDtoBuilder = new ComputerDtoBuilder();
		
		System.out.println("Computer name ?");
		computerDtoBuilder.withName(Input.readStringOrNull());
		
		System.out.println("Introduction date ?");
		computerDtoBuilder.withIntroductionDate(Input.readLocalDateOrNull());
		
		System.out.println("Discontinue date ?");
		computerDtoBuilder.withDiscontinueDate(Input.readLocalDateOrNull());
		
		System.out.println("Manufacturer id ?");
		computerDtoBuilder.withCompanyId(Input.readStringOrNull());
		return computerDtoBuilder.build();
	}
}
