package com.ideas2it.ism.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.ism.common.Constant;
import com.ideas2it.ism.dao.UserRepository;
import com.ideas2it.ism.entity.User;
import com.ideas2it.ism.exception.IsmException;
import com.ideas2it.ism.service.UserService;

/**
 * Registers a new User, rejecting duplicate usernames and storing the
 * password as a BCrypt hash.
 */
@Service
public class UserServiceImpl implements UserService {

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerUser(User user) throws IsmException {
		Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			throw new IsmException(Constant.USERNAME_ALREADY_REGISTERED);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException exception) {
			throw new IsmException(Constant.USERNAME_ALREADY_REGISTERED, exception);
		}
	}

}
