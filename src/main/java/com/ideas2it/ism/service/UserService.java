package com.ideas2it.ism.service;

import com.ideas2it.ism.entity.User;
import com.ideas2it.ism.exception.IsmException;

public interface UserService {

	/**
	 * Registers a new User with a hashed password, after verifying the
	 * submitted username is not already registered.
	 *
	 * @param user - the submitted username/password to register.
	 * @return the persisted User.
	 * @throws IsmException - if the username is already registered.
	 */
	User registerUser(User user) throws IsmException;

}
