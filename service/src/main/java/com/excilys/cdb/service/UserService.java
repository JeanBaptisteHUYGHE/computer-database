package com.excilys.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.core.User;
import com.excilys.cdb.persistence.UserDao;
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;
import com.excilys.cdb.persistence.exception.UserNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	public UserService() { }

	/**
	 * Return the user with this id.
	 * @param id the user id
	 * @return the user
	 * @throws UserNotFoundException 
	 * @throws DatabaseConnectionException 
	 */
	public User getUserById(Integer id) throws UserNotFoundException, DatabaseConnectionException {
		return userDao.getUserById(id);
	}
	
	/**
	 * Return the user with this login.
	 * @param login the user login
	 * @return the user
	 * @throws UserNotFoundException 
	 * @throws DatabaseConnectionException 
	 */
	public User getUserByLogin(String login) throws UserNotFoundException, DatabaseConnectionException {
		return userDao.getUserByLogin(login);
	}

	/**
	 * Return a list of users.
	 * @return the users list
	 * @throws DatabaseConnectionException 
	 */
	public List<User> getUsersList() throws DatabaseConnectionException {
		return userDao.getUsersList();
	}
}
