package com.excilys.cdb.persistence.enumeration;

public enum UserRequestEnum {
	
	GET_ALL_USERS(
			"FROM UserPDto"),

	GET_USER_BY_ID(
			"FROM UserPDto as userPDto "
			+ "WHERE userPDto.id = :id"),
	
	GET_USER_BY_LOGIN(
			"FROM UserPDto as userPDto "
			+ "WHERE userPDto.login = :login");
	
	
	private final String request;
	
	UserRequestEnum(String request) {
		this.request = request;
	}
	
	public String get() {
		return request;
	}
}
