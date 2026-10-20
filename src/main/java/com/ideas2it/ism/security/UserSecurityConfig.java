package com.ideas2it.ism.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/" + Constant.USER_LOGOUT, "POST"))
				.logoutSuccessUrl("/" + Constant.USER_LOGIN)
				.invalidateHttpSession(true)
				.and()
			.addFilterAt(userLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	private UserLoginAuthenticationFilter userLoginAuthenticationFilter() throws Exception {
		UserLoginAuthenticationFilter filter = new UserLoginAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setFilterProcessesUrl("/" + Constant.USER_LOGIN);

		SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler(
				"/" + Constant.USER_HOME);
		successHandler.setAlwaysUseDefaultTargetUrl(true);
		filter.setAuthenticationSuccessHandler(successHandler);

		filter.setAuthenticationFailureHandler(
				new SimpleUrlAuthenticationFailureHandler("/" + Constant.USER_LOGIN + "?error"));

		return filter;
	}

}
