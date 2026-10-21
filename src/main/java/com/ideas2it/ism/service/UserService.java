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

	/**
	 * Fetches the User matching the given username.
	 *
	 * @param username - the username to look up.
	 * @return the matching User.
	 * @throws IsmException - if no User matches the given username.
	 */
	User findByUsername(String username) throws IsmException;

	/**
	 * Updates the email of the User matching the given username, after
	 * verifying the submitted email is non-blank and well-formed.
	 *
	 * @param username - the username of the User being updated.
	 * @param email - the new email to persist.
	 * @return the persisted User.
	 * @throws IsmException - if the email is blank, malformed, or the User
	 *     is not found.
	 */
	User updateEmail(String username, String email) throws IsmException;

	/**
	 * Changes the password of the User matching the given username, after
	 * verifying the submitted current password against the stored hash and
	 * the new password against the existing strength rule.
	 *
	 * @param username - the username of the User being updated.
	 * @param currentPassword - the submitted current password, in plaintext.
	 * @param newPassword - the submitted new password, in plaintext.
	 * @throws IsmException - if either password is blank, the new password
	 *     is too weak, the current password does not match, or the User is
	 *     not found.
	 */
	void changePassword(String username, String currentPassword, String newPassword) throws IsmException;

}
