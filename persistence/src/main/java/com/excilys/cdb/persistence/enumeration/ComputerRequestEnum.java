package com.excilys.cdb.persistence.enumeration;

public enum ComputerRequestEnum {

	GET_COMPUTERS_LIST_FOR_PAGE(
			"FROM ComputerPDto"),

	GET_COMPUTERS_LIST_BY_PAGE_FOR_SEARCH(
			"SELECT computerPDto "
			+ "FROM ComputerPDto as computerPDto LEFT OUTER JOIN computerPDto.companyPDto as companyPDto "
			+ "WHERE computerPDto.name LIKE :computerNameSearch or companyPDto.name LIKE :companyNameSearch "
			+ "ORDER BY computerPDto.id"),
	
	GET_COMPUTER_BY_ID(
			"FROM ComputerPDto as computerPDto "
			+ "WHERE computerPDto.id = :id"),
	
	UPDATE_COMPUTER_BY_ID(
			"UPDATE ComputerPDto as computerPDto "
			+ "SET computerPDto.name = :name, "
				+ "computerPDto.introductionDate = :introductionDate, "
				+ "computerPDto.discontinueDate = :discontinueDate, "
				+ "computerPDto.companyPDto = :companyPDto "
			+ "WHERE computerPDto.id = :id"),
	
	DELETE_COMPUTER_BY_ID(
			"DELETE FROM ComputerPDto as computerPDto "
			+ "WHERE computerPDto.id = :id"),
	
	DELETE_COMPUTERS_BY_COMPANY_ID(
			"DELETE FROM computer "
			+ "WHERE company_id = :companyId"),
	
	GET_COMPUTERS_COUNT(
			"SELECT count(computerPDto.id) "
			+ "FROM ComputerPDto as computerPDto"),
	
	GET_COMPUTERS_COUNT_FOR_SEARCH(
			"SELECT count(computerPDto.id) "
			+ "FROM ComputerPDto as computerPDto LEFT OUTER JOIN computerPDto.companyPDto as companyPDto "
			+ "WHERE computerPDto.name LIKE :computerNameSearch or companyPDto.name LIKE :companyNameSearch "
			+ "ORDER BY computerPDto.id");
	
	private final String request;
	
	ComputerRequestEnum(String request) {
		this.request = request;
	}
	
	public String get() {
		return request;
	}
}
