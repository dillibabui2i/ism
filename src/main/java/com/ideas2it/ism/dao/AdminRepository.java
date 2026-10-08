package com.ideas2it.ism.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.ism.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	/**
	 * Fetches the Admin matching the given username, used to authenticate
	 * a login attempt.
	 *
	 * @param username - the username to look up.
	 * @return the matching Admin, if any.
	 */
	Optional<Admin> findByUsername(String username);

}
