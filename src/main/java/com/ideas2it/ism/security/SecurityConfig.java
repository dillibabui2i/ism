package com.ideas2it.ism.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ideas2it.ism.common.Constant;

/**
 * Requires an authenticated Admin session for every request except the
 * login page and static assets.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AdminUserDetailsService adminUserDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(adminUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/" + Constant.LOGIN, "/css/**", "/image/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/" + Constant.LOGIN)
				.loginProcessingUrl("/" + Constant.LOGIN)
				.failureUrl("/" + Constant.LOGIN + "?error")
				.permitAll();
	}

}
