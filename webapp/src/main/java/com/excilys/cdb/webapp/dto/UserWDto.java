package com.excilys.cdb.webapp.dto;


public class UserWDto {

	private Integer id;
	private String name;
	private String login;
	private String password;
	private String role;
	private boolean isActive;
	
	
	private UserWDto() { }
	
	
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
		return String.format("<UserWDto; id: %s, name: %s, login: %s, password: %s, role: %s, active: %s>",
				id, name, login, password, role, isActive);
	}
	
	public static class UserWDtoBuilder {
		
		private Integer id;
		private String name;
		private String login;
		private String password;
		private String role;
		private boolean isActive;

		public UserWDtoBuilder() { }
		
		public UserWDtoBuilder withId(Integer id) {
			this.id = id;
			return this;
		}
		
		public UserWDtoBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public UserWDtoBuilder withLogin(String login) {
			this.login = login;
			return this;
		}
		
		public UserWDtoBuilder withPassword(String password) {
			this.password = password;
			return this;
		}
		
		public UserWDtoBuilder withRole(String role) {
			this.role = role;
			return this;
		}
		
		public UserWDtoBuilder withActive(boolean isActive) {
			this.isActive = isActive;
			return this;
		}
		
		public UserWDto build() {
			UserWDto userWDto = new UserWDto();
			userWDto.setId(id);
			userWDto.setName(name);
			userWDto.setLogin(login);
			userWDto.setPassword(password);
			userWDto.setRole(role);
			userWDto.setActive(isActive);
			return userWDto;
		}
	}
}
