package com.excilys.cdb.restapi.dto.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import com.excilys.cdb.restapi.dto.ComputerRDto;
import com.excilys.cdb.restapi.dto.exception.InvalidComputerDatesPrecedenceException;
import com.excilys.cdb.restapi.dto.exception.InvalidComputerDiscontinueDateException;
import com.excilys.cdb.restapi.dto.exception.InvalidComputerException;
import com.excilys.cdb.restapi.dto.exception.InvalidComputerIdException;
import com.excilys.cdb.restapi.dto.exception.InvalidComputerIntroductionDateException;
import com.excilys.cdb.restapi.dto.exception.InvalidComputerNameException;

@Component
public class ComputerRDtoValidator {
	
	private ComputerRDtoValidator() { }
	
	public void validate(ComputerRDto computerRDto) throws InvalidComputerException {
		validateId(computerRDto.getId());
		validateName(computerRDto.getName());
		validateIntroductionDate(computerRDto.getIntroductionDate());
		validateDiscontinueDate(computerRDto.getDiscontinueDate());
		validateDatesPrecedence(computerRDto.getIntroductionDate(), computerRDto.getDiscontinueDate());
	}

	private void validateId(String id) throws InvalidComputerIdException {
		if (id != null) {
			Integer integerId = Integer.valueOf(id);
			try {
				if (integerId < 0) {
					throw new InvalidComputerIdException("Invalid computer id, it must be positive or null");
				}
				
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid computer id, it must be a number");
			}
		}
	}
	
	private void validateName(String name) throws InvalidComputerNameException {
		if (name == null) {
			throw new InvalidComputerNameException("Invalid computer name, it can't be null");
			
		} else {
			if (name.length() == 0) {
				throw new InvalidComputerNameException("Invalid computer name, it can't be empty");
			}
		}
	}
	
	private void validateIntroductionDate(String introductionDate) throws InvalidComputerIntroductionDateException {
		if (introductionDate != null) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
				LocalDate localDateIntroductionDate = LocalDate.parse(introductionDate, formatter);
				if (!isValidDate(localDateIntroductionDate)) {
					throw new InvalidComputerIntroductionDateException("Invalid computer introduction date, date too extrem");
				}
				
			} catch (DateTimeParseException e) {
				throw new InvalidComputerIntroductionDateException("Invalid computer introduction date, it have an invalid format");
			}
		}
	}
	
	private void validateDiscontinueDate(String discontinueDate) throws InvalidComputerDiscontinueDateException {
		if (discontinueDate != null) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
				LocalDate localDateDiscontinueDate = LocalDate.parse(discontinueDate, formatter);
				if (!isValidDate(localDateDiscontinueDate)) {
					throw new InvalidComputerDiscontinueDateException("Invalid computer discontinue date, date too extrem");
				}
				
			} catch (DateTimeParseException e) {
				throw new InvalidComputerDiscontinueDateException("Invalid computer discontinue date, it have an invalid format");
			}
		}
	}
	
	private void validateDatesPrecedence(String introductionDate, String discontinueDate) throws InvalidComputerDatesPrecedenceException {
		if (introductionDate != null && discontinueDate != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
			LocalDate localDateIntroductionDate = LocalDate.parse(introductionDate, formatter);
			LocalDate localDateDiscontinueDate = LocalDate.parse(discontinueDate, formatter);
			if (localDateDiscontinueDate.isBefore(localDateIntroductionDate)) {
				throw new InvalidComputerDatesPrecedenceException("Computer introduction date must be smaller than discontinue date");
			}
		}
	}

	/**
	 * Return if the date is valid (=if it can be set in a SQL "timestamp" type).
	 * @param localDate
	 * @return if the date is valid
	 */
	private boolean isValidDate(LocalDate localDate) {
		if (localDate == null) {
			return true;
		}
		LocalDate minDate = LocalDate.of(1970, 1, 1);
		LocalDate maxDate = LocalDate.of(2038, 1, 19);
		return (localDate.isAfter(minDate) && localDate.isBefore(maxDate));
	}
	
}
