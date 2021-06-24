package com.excilys.cdb.service.security;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.core.User;
import com.excilys.cdb.persistence.UserDao;
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;
import com.excilys.cdb.persistence.exception.UserNotFoundException;


@Service
public class AuthenticationService implements UserDetailsService {
	
	private Logger logger;
	private UserDao userDao;
	
	public AuthenticationService(UserDao userDao) {
		logger = LoggerFactory.getLogger(AuthenticationService.class);
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("loadUserByUsername({})", username);
		
		try {
			User user = userDao.getUserByLogin(username);
					
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getDatabaseValue());
	        
			UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(
					user.getLogin(),
					user.getPassword(),
					Arrays.asList(authority));

			return userDetails;
	        
		} catch (UserNotFoundException e) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		} catch (DatabaseConnectionException e) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database due to a database error");
		}
	}
	

}
