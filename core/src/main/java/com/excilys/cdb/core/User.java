package com.excilys.cdb.core;

import com.excilys.cdb.core.enumeration.EnumUsersRoles;

public class User {

	private Integer id;
	private String name;
	private String login;
	private String password;
	private EnumUsersRoles role;
	private boolean isActive;
	
	private User() { }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public EnumUsersRoles getRole() {
		return role;
	}

	public void setRole(EnumUsersRoles role) {
		this.role = role;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public String toString() {
		return String.format("<User; id: %s, name: %s, login: %s, password: %s, role: %s, active: %s>",
				id, name, login, password, role, isActive);
	}


	public static class UserBuilder {
		
		private Integer id;
		private String name;
		private String login;
		private String password;
		private EnumUsersRoles role;
		private boolean isActive;
		
		public UserBuilder() { }
		
		public UserBuilder withId(Integer id) {
			this.id = id;
			return this;
		}
		
		public UserBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public UserBuilder withLogin(String login) {
			this.login = login;
			return this;
		}
		
		public UserBuilder withPassword(String password) {
			this.password = password;
			return this;
		}
		
		public UserBuilder withRole(EnumUsersRoles role) {
			this.role = role;
			return this;
		}
		
		public UserBuilder withActive(boolean isActive) {
			this.isActive = isActive;
			return this;
		}
		
		public User build() {
			User user = new User();
			user.setId(id);
			user.setName(name);
			user.setLogin(login);
			user.setPassword(password);
			user.setRole(role);
			user.setActive(isActive);
			return user;
		}
	}
}

