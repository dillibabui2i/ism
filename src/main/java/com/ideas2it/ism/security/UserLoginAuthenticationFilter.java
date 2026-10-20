package com.ideas2it.ism.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.ideas2it.ism.common.Constant;

/**
 * Rejects a login submission with a missing username or password before
 * {@link #getAuthenticationManager()} is asked to authenticate it, so no
 * credential lookup happens for incomplete input.
 */
public class UserLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
			throw new BadCredentialsException(Constant.LOGIN_FIELDS_REQUIRED);
		}
		return super.attemptAuthentication(request, response);
	}

}
