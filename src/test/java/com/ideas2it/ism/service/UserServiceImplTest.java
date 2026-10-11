package com.ideas2it.ism.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ideas2it.ism.common.Constant;
import com.ideas2it.ism.dao.UserRepository;
import com.ideas2it.ism.entity.User;
import com.ideas2it.ism.exception.IsmException;
import com.ideas2it.ism.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	private static final String USERNAME = "newuser";
	private static final String PASSWORD = "plaintextPassword";

	private User newUser() {
		User user = new User();
		user.setUsername(USERNAME);
		user.setPassword(PASSWORD);
		return user;
	}

	// ac-001: unique username/password results in a new User being saved.
	@Test
	public void registerUser_savesNewUser_whenUsernameIsUnique() throws IsmException {
		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

		userService.registerUser(newUser());

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(captor.capture());
		assertEquals(USERNAME, captor.getValue().getUsername());
	}

	// ac-002: duplicate username is rejected and no save is attempted.
	@Test
	public void registerUser_throwsAndNeverSaves_whenUsernameAlreadyRegistered() {
		User existingUser = newUser();
		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(existingUser));

		try {
			userService.registerUser(newUser());
			fail("Expected IsmException for duplicate username");
		} catch (IsmException exception) {
			assertEquals(Constant.USERNAME_ALREADY_REGISTERED, exception.getMessage());
		}
		verify(userRepository, never()).save(any());
	}

	// ac-003: the persisted password is BCrypt-hashed, not the submitted plaintext.
	@Test
	public void registerUser_hashesPassword_beforeSaving() throws IsmException {
		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

		userService.registerUser(newUser());

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(captor.capture());
		String storedPassword = captor.getValue().getPassword();

		assertNotEquals(PASSWORD, storedPassword);
		assertTrue(new BCryptPasswordEncoder().matches(PASSWORD, storedPassword));
	}

}
