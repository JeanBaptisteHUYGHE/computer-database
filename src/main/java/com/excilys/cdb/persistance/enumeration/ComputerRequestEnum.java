package com.excilys.cdb.persistance.enumeration;

public enum ComputerRequestEnum {

	GET_COMPUTERS_LIST_FOR_PAGE(
			"SELECT computer.id as id, computer.name as name, introduced, discontinued, company_id, company.name as company_name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
			+ "ORDER BY computer.id "
			+ "LIMIT :pageSize OFFSET :offset"),

	GET_COMPUTERS_LIST_BY_PAGE_FOR_SEARCH(
			"SELECT computer.id as id, computer.name as name, introduced, discontinued, company_id, company.name as company_name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE computer.name LIKE :computerNameSearch or company.name LIKE :companyNameSearch "
			+ "ORDER BY computer.id "
			+ "LIMIT :pageSize OFFSET :offset"),
	
	GET_COMPUTER_BY_ID(
			"SELECT computer.id as id, computer.name as name, introduced, discontinued, company_id, company.name as company_name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE computer.id = :id"),
	
	UPDATE_COMPUTER_BY_ID(
			"UPDATE computer "
			+ "SET name = :name, introduced = :introductionDate, discontinued = :discontinueDate, company_id = :companyId "
			+ "WHERE id = :id"),
	
	ADD_COMPUTER(
			"INSERT INTO computer (name, introduced, discontinued, company_id) "
			+ "VALUES (:name, :introductionDate, :discontinueDate, :companyId)"),
	
	DELETE_COMPUTER_BY_ID(
			"DELETE FROM computer "
			+ "WHERE id = :id"),
	
	DELETE_COMPUTERS_BY_COMPANY_ID(
			"DELETE FROM computer "
			+ "WHERE company_id = :companyId"),
	
	GET_COMPUTERS_COUNT(
			"SELECT count(id) "
			+ "FROM computer"),
	
	GET_COMPUTERS_COUNT_FOR_SEARCH(
			"SELECT count(computer.id) "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE computer.name LIKE :computerNameSearch or company.name LIKE :companyNameSearch "
			+ "ORDER BY computer.id");
	
	private final String request;
	
	ComputerRequestEnum(String request) {
		this.request = request;
	}
	
	public String get() {
		return request;
	}
}
