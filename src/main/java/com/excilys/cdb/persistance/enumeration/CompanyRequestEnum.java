package com.excilys.cdb.persistance.enumeration;

public enum CompanyRequestEnum {
	
	GET_ALL_COMPANIES(
			"SELECT id, name "
			+ "FROM company "
			+ "ORDER BY name"),
	
	GET_ALL_COMPANIES_FOR_PAGE(
			"SELECT id, name "
			+ "FROM company "
			+ "ORDER BY name "
			+ "LIMIT ? OFFSET ?"),
	
	GET_COMPANY_BY_ID(
			"SELECT id, name "
			+ "FROM company "
			+ "WHERE id = ?"),
	
	GET_COMPANIES_COUNT(
			"SELECT count(id) "
			+ "FROM company");
	
	private final String request;
	
	CompanyRequestEnum(String request) {
		this.request = request;
	}
	
	public String get() {
		return request;
	}
}
