package com.excilys.cdb.core.enumeration;

public enum EnumUsersRoles {
	ADMINISTRATOR("admin", "Administrator"),
	
	USER("user", "User");
	
	

	private final String databaseValue;
	private final String representation;
	
	EnumUsersRoles(String databaseValue, String representation) {
		this.databaseValue = databaseValue;
		this.representation = representation;
	}
	
	public String toString() {
		return String.format("Role %s", this.representation);
	}
	
	public String getDatabaseValue() {
		return databaseValue;
	}
	
	public String getRepresentation() {
		return representation;
	}
	
	/**
	 * Return if the enumeration contains a role value.
	 * @param roleValue the role value
	 * @return if the value is contained in the enumeration
	 */
	public static boolean contains(String roleValue) {
		for (EnumUsersRoles myEnum : EnumUsersRoles.values()) {
			if (myEnum.databaseValue.equals(roleValue)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return the correspondent enumeration of the value or the default value.
	 * @param roleValue the role value
	 * @return the correspondent role enumeration
	 */
	public static EnumUsersRoles getRole(String roleValue) {
		for (EnumUsersRoles myEnum : EnumUsersRoles.values()) {
			if (myEnum.databaseValue.equals(roleValue)) {
				return myEnum;
			}
		}
		return EnumUsersRoles.USER;
	}
	
	
	
}
