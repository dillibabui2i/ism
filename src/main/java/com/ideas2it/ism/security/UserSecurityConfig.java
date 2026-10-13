package com.ideas2it.ism.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ideas2it.ism.common.Constant;

/**
 * Authenticates registered Users against their own accounts for the
 * /user/** pages. Ordered ahead of the Admin chain, which handles every
 * other request.
 */
@EnableWebSecurity
@Order(1)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserAccountDetailsService userAccountDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userAccountDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/" + Constant.USER + "/**")
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/" + Constant.USER_LOGIN).permitAll()
				.anyRequest().hasRole("USER")
				.and()
			.formLogin()
				.loginPage("/" + Constant.USER_LOGIN)
				.loginProcessingUrl("/" + Constant.USER_LOGIN)
				.defaultSuccessUrl("/" + Constant.USER_HOME, true)
				.failureUrl("/" + Constant.USER_LOGIN + "?error")
				.permitAll();
	}

}
