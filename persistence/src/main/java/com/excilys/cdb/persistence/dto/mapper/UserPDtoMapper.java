package com.excilys.cdb.persistence.dto.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.core.User;
import com.excilys.cdb.core.User.UserBuilder;
import com.excilys.cdb.core.enumeration.EnumUsersRoles;
import com.excilys.cdb.persistence.dto.UserPDto;
import com.excilys.cdb.persistence.dto.UserPDto.UserPDtoBuilder;

@Component
public class UserPDtoMapper {
			
	public User fromUserPDtoToUser(UserPDto userPDto) {
		UserBuilder userBuilder = new UserBuilder()
			.withId(userPDto.getId())
			.withName(userPDto.getName())
			.withLogin(userPDto.getLogin())
			.withPassword(userPDto.getPassword())
			.withRole(EnumUsersRoles.getRole(userPDto.getRole()))
			.withActive(userPDto.isActive());
		return userBuilder.build();
	}
	
	public UserPDto fromUserToUserPDto(User user) {
		UserPDtoBuilder userPDtoBuilder = new UserPDtoBuilder()
			.withId(user.getId())
			.withName(user.getName())
			.withLogin(user.getLogin())
			.withPassword(user.getPassword())
			.withRole(user.getRole().getDatabaseValue())
			.withActive(user.isActive());
		return userPDtoBuilder.build();
	}
}
