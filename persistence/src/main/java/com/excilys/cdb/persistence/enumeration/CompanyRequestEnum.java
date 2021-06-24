package com.excilys.cdb.persistence.enumeration;

public enum CompanyRequestEnum {
	
	GET_ALL_COMPANIES(
			"FROM CompanyPDto"),

	GET_COMPANY_BY_ID(
			"FROM CompanyPDto as companyPDto "
			+ "WHERE companyPDto.id = :id"),
	
	GET_COMPANIES_COUNT(
			"SELECT count(companyPDto.id) "
			+ "FROM CompanyPDto as companyPDto"),
	
	DELETE_COMPANY_BY_ID(
			"DELETE FROM CompanyPDto as companyPDto "
			+ "WHERE companyPDto.id = :id");
	
	private final String request;
	
	CompanyRequestEnum(String request) {
		this.request = request;
	}
	
	public String get() {
		return request;
	}
}
