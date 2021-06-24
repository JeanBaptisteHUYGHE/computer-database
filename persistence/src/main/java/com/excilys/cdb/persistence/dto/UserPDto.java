package com.excilys.cdb.persistence.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "user")
public class UserPDto {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;
	
	private String name;
	private String login;
	private String password;
	private String role;
	
	@Column(name = "active")
	private boolean isActive;
	
	
	private UserPDto() { }
	
	
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
		return String.format("<UserPDto; id: %s, name: %s, login: %s, password: %s, role: %s, active: %s>",
				id, name, login, password, role, isActive);
	}
	
	public static class UserPDtoBuilder {
		
		private Integer id;
		private String name;
		private String login;
		private String password;
		private String role;
		private boolean isActive;

		public UserPDtoBuilder() { }
		
		public UserPDtoBuilder withId(Integer id) {
			this.id = id;
			return this;
		}
		
		public UserPDtoBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public UserPDtoBuilder withLogin(String login) {
			this.login = login;
			return this;
		}
		
		public UserPDtoBuilder withPassword(String password) {
			this.password = password;
			return this;
		}
		
		public UserPDtoBuilder withRole(String role) {
			this.role = role;
			return this;
		}
		
		public UserPDtoBuilder withActive(boolean isActive) {
			this.isActive = isActive;
			return this;
		}
		
		public UserPDto build() {
			UserPDto user = new UserPDto();
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
