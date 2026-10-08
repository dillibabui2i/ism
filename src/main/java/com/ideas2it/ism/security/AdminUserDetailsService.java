package com.ideas2it.ism.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ideas2it.ism.dao.AdminRepository;
import com.ideas2it.ism.entity.Admin;

/**
 * Loads an Admin's stored credentials for Spring Security to authenticate
 * against.
 */
@Service
public class AdminUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return User.withUsername(admin.getUsername())
				.password(admin.getPassword())
				.roles("ADMIN")
				.build();
	}

}
