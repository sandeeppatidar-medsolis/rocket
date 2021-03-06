package com.rocket.crm.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rocket.crm.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByUsername(String username);

	@Query("SELECT u.username as username, u.enabled as enabled FROM User u WHERE u.username=:username")
	Map<String, Object> findUserByUsername(@Param("username") String username);

}
