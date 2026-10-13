package com.ideas2it.ism.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ideas2it.ism.dao.UserRepository;

/**
 * Loads a registered User's stored credentials for Spring Security to
 * authenticate against.
 */
@Service
public class UserAccountDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.ideas2it.ism.entity.User account = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return User.withUsername(account.getUsername())
				.password(account.getPassword())
				.roles("USER")
				.build();
	}

}
