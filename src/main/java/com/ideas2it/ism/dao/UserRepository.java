package com.ideas2it.ism.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.ism.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Fetches the User matching the given username, used to detect
	 * duplicate registrations.
	 *
	 * @param username - the username to look up.
	 * @return the matching User, if any.
	 */
	Optional<User> findByUsername(String username);

}
