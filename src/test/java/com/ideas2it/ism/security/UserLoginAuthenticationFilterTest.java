package com.ideas2it.ism.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.ideas2it.ism.common.Constant;

public class UserLoginAuthenticationFilterTest {

	private AuthenticationManager authenticationManager;
	private UserLoginAuthenticationFilter filter;

	@Before
	public void setUp() {
		authenticationManager = mock(AuthenticationManager.class);
		filter = new UserLoginAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager);
	}

	private MockHttpServletRequest requestWith(String username, String password) {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/user/login");
		if (username != null) {
			request.addParameter("username", username);
		}
		if (password != null) {
			request.addParameter("password", password);
		}
		return request;
	}

	// ac-006: a missing username is rejected before the AuthenticationManager is invoked.
	@Test
	public void attemptAuthentication_throwsAndNeverAuthenticates_whenUsernameIsMissing() {
		MockHttpServletRequest request = requestWith("", "password1");

		try {
			filter.attemptAuthentication(request, new MockHttpServletResponse());
			fail("Expected BadCredentialsException for missing username");
		} catch (BadCredentialsException exception) {
			assertEquals(Constant.LOGIN_FIELDS_REQUIRED, exception.getMessage());
		}
		verify(authenticationManager, never()).authenticate(any());
	}

	// ac-006: a missing password is rejected before the AuthenticationManager is invoked.
	@Test
	public void attemptAuthentication_throwsAndNeverAuthenticates_whenPasswordIsMissing() {
		MockHttpServletRequest request = requestWith("someuser", "");

		try {
			filter.attemptAuthentication(request, new MockHttpServletResponse());
			fail("Expected BadCredentialsException for missing password");
		} catch (BadCredentialsException exception) {
			assertEquals(Constant.LOGIN_FIELDS_REQUIRED, exception.getMessage());
		}
		verify(authenticationManager, never()).authenticate(any());
	}

	// Control case: both fields present means the AuthenticationManager is invoked as normal.
	@Test
	public void attemptAuthentication_delegatesToAuthenticationManager_whenBothFieldsPresent() {
		MockHttpServletRequest request = requestWith("someuser", "password1");
		Authentication expected = new UsernamePasswordAuthenticationToken("someuser", "password1");
		when(authenticationManager.authenticate(any())).thenReturn(expected);

		Authentication result = filter.attemptAuthentication(request, new MockHttpServletResponse());

		assertEquals(expected, result);
		verify(authenticationManager).authenticate(any());
	}

}
