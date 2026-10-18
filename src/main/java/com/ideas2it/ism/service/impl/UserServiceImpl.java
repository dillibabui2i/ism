package com.ideas2it.ism.service.impl;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

	private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9._-]{2,29}$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).{8,}$");

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerUser(User user) throws IsmException {
		validateUser(user);
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

	private void validateUser(User user) throws IsmException {
		if (!StringUtils.hasText(user.getUsername())) {
			throw new IsmException(Constant.USERNAME_REQUIRED);
		}
		if (!StringUtils.hasText(user.getPassword())) {
			throw new IsmException(Constant.PASSWORD_REQUIRED);
		}
		if (!USERNAME_PATTERN.matcher(user.getUsername()).matches()
				&& !EMAIL_PATTERN.matcher(user.getUsername()).matches()) {
			throw new IsmException(Constant.INVALID_USERNAME_FORMAT);
		}
		if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
			throw new IsmException(Constant.PASSWORD_TOO_WEAK);
		}
	}

}
