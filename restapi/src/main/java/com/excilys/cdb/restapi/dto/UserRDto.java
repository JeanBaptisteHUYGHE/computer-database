package com.excilys.cdb.restapi.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

public class UserRDto {

	private Integer id;
	private String name;
	private String login;
	private String password;
	private String role;
	private boolean isActive;
	
	
	private UserRDto() { }
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonGetter("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonGetter("login")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@JsonGetter("password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonGetter("role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String toString() {
		return String.format("<UserRDto; id: %s, name: %s, login: %s, password: %s, role: %s, active: %s>",
				id, name, login, password, role, isActive);
	}
	
	public static class UserRDtoBuilder {
		
		private Integer id;
		private String name;
		private String login;
		private String password;
		private String role;
		private boolean isActive;

		public UserRDtoBuilder() { }
		
		public UserRDtoBuilder withId(Integer id) {
			this.id = id;
			return this;
		}
		
		public UserRDtoBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public UserRDtoBuilder withLogin(String login) {
			this.login = login;
			return this;
		}
		
		public UserRDtoBuilder withPassword(String password) {
			this.password = password;
			return this;
		}
		
		public UserRDtoBuilder withRole(String role) {
			this.role = role;
			return this;
		}
		
		public UserRDtoBuilder withActive(boolean isActive) {
			this.isActive = isActive;
			return this;
		}
		
		public UserRDto build() {
			UserRDto userRDto = new UserRDto();
			userRDto.setId(id);
			userRDto.setName(name);
			userRDto.setLogin(login);
			userRDto.setPassword(password);
			userRDto.setRole(role);
			userRDto.setActive(isActive);
			return userRDto;
		}
	}
}
