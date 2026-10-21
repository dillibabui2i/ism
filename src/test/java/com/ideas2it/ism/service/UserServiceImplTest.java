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
	private static final String PASSWORD = "plaintextPassword1";

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

	// ac-004: a blank username is rejected before any lookup or save.
	@Test
	public void registerUser_throwsAndNeverSaves_whenUsernameIsBlank() {
		User user = newUser();
		user.setUsername("");

		try {
			userService.registerUser(user);
			fail("Expected IsmException for blank username");
		} catch (IsmException exception) {
			assertEquals(Constant.USERNAME_REQUIRED, exception.getMessage());
		}
		verify(userRepository, never()).save(any());
	}

	// ac-004: a blank password is rejected before any lookup or save.
	@Test
	public void registerUser_throwsAndNeverSaves_whenPasswordIsBlank() {
		User user = newUser();
		user.setPassword("");

		try {
			userService.registerUser(user);
			fail("Expected IsmException for blank password");
		} catch (IsmException exception) {
			assertEquals(Constant.PASSWORD_REQUIRED, exception.getMessage());
		}
		verify(userRepository, never()).save(any());
	}

	// ac-004: an unusable username/email format is rejected and no save is attempted.
	@Test
	public void registerUser_throwsAndNeverSaves_whenUsernameFormatIsInvalid() {
		User user = newUser();
		user.setUsername("in valid!");

		try {
			userService.registerUser(user);
			fail("Expected IsmException for invalid username format");
		} catch (IsmException exception) {
			assertEquals(Constant.INVALID_USERNAME_FORMAT, exception.getMessage());
		}
		verify(userRepository, never()).save(any());
	}

	// ac-005: a password below the minimum length/complexity is rejected and no save is attempted.
	@Test
	public void registerUser_throwsAndNeverSaves_whenPasswordIsTooWeak() {
		User user = newUser();
		user.setPassword("short1");

		try {
			userService.registerUser(user);
			fail("Expected IsmException for weak password");
		} catch (IsmException exception) {
			assertEquals(Constant.PASSWORD_TOO_WEAK, exception.getMessage());
		}
		verify(userRepository, never()).save(any());
	}

	// ac-001: findByUsername returns the persisted User matching the authenticated principal.
	@Test
	public void findByUsername_returnsPersistedUser() throws IsmException {
		User existingUser = newUser();
		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(existingUser));

		User result = userService.findByUsername(USERNAME);

		assertEquals(existingUser, result);
	}

	// ac-001: a username with no matching User throws rather than returning null.
	@Test
	public void findByUsername_throws_whenUserNotFound() {
		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

		try {
			userService.findByUsername(USERNAME);
			fail("Expected IsmException for missing user");
		} catch (IsmException exception) {
			assertEquals(Constant.USER_NOT_FOUND, exception.getMessage());
		}
	}

	// ac-002: a valid new email is saved against the existing User.
	@Test
	public void updateEmail_savesNewEmail_whenFormatIsValid() throws IsmException {
		User existingUser = newUser();
		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

		userService.updateEmail(USERNAME, "user@example.com");

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(captor.capture());
		assertEquals("user@example.com", captor.getValue().getEmail());
	}

	// ac-005: a blank email is rejected before any lookup or save.
	@Test
	public void updateEmail_throwsAndNeverSaves_whenEmailIsBlank() {
		try {
			userService.updateEmail(USERNAME, "");
			fail("Expected IsmException for blank email");
		} catch (IsmException exception) {
			assertEquals(Constant.EMAIL_REQUIRED, exception.getMessage());
		}
		verify(userRepository, never()).save(any());
	}

	// ac-005: a malformed email is rejected and no save is attempted.
	@Test
	public void updateEmail_throwsAndNeverSaves_whenEmailFormatIsInvalid() {
		try {
			userService.updateEmail(USERNAME, "not-an-email");
			fail("Expected IsmException for invalid email format");
		} catch (IsmException exception) {
			assertEquals(Constant.INVALID_EMAIL_FORMAT, exception.getMessage());
		}
		verify(userRepository, never()).save(any());
	}

	// ac-003: the correct current password allows the new password to be hashed and saved.
	@Test
	public void changePassword_updatesHashedPassword_whenCurrentPasswordMatches() throws IsmException {
		User existingUser = newUser();
		existingUser.setPassword(new BCryptPasswordEncoder().encode(PASSWORD));
		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

		userService.changePassword(USERNAME, PASSWORD, "newPassword1");

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(captor.capture());
		String storedPassword = captor.getValue().getPassword();
		assertNotEquals("newPassword1", storedPassword);
		assertTrue(new BCryptPasswordEncoder().matches("newPassword1", storedPassword));
	}

	// ac-004: an incorrect current password is rejected and no save is attempted.
	@Test
	public void changePassword_throwsAndNeverSaves_whenCurrentPasswordIsIncorrect() {
		User existingUser = newUser();
		existingUser.setPassword(new BCryptPasswordEncoder().encode(PASSWORD));
		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(existingUser));

		try {
			userService.changePassword(USERNAME, "wrongPassword1", "newPassword1");
			fail("Expected IsmException for incorrect current password");
		} catch (IsmException exception) {
			assertEquals(Constant.CURRENT_PASSWORD_INCORRECT, exception.getMessage());
		}
		verify(userRepository, never()).save(any());
	}

	// ac-006: a new password that fails the strength rule is rejected before the current-password check consumes it.
	@Test
	public void changePassword_throwsAndNeverSaves_whenNewPasswordIsTooWeak() {
		try {
			userService.changePassword(USERNAME, PASSWORD, "short1");
			fail("Expected IsmException for weak new password");
		} catch (IsmException exception) {
			assertEquals(Constant.PASSWORD_TOO_WEAK, exception.getMessage());
		}
		verify(userRepository, never()).save(any());
	}

}
