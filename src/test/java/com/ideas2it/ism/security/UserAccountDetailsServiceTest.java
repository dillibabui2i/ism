package com.ideas2it.ism.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ideas2it.ism.dao.UserRepository;
import com.ideas2it.ism.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountDetailsServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserAccountDetailsService userAccountDetailsService;

	private static final String USERNAME = "newuser";
	private static final String STORED_HASH = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";

	// ac-001: a registered User's stored hash is loaded and granted ROLE_USER.
	@Test
	public void loadUserByUsername_returnsStoredHashWithUserRole_whenUserIsRegistered() {
		User user = new User();
		user.setUsername(USERNAME);
		user.setPassword(STORED_HASH);
		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

		UserDetails details = userAccountDetailsService.loadUserByUsername(USERNAME);

		assertEquals(USERNAME, details.getUsername());
		assertEquals(STORED_HASH, details.getPassword());
		assertEquals(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
				new ArrayList<GrantedAuthority>(details.getAuthorities()));
	}

	// ac-002: an unknown username is rejected, so no User is authenticated.
	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsername_throws_whenUsernameIsNotRegistered() {
		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

		userAccountDetailsService.loadUserByUsername(USERNAME);
	}

}
