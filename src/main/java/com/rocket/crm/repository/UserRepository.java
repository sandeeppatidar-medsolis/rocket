package com.rocket.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rocket.crm.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByUsername(String username);

	List<User> findByEmail(String email);

	List<User> findByPhone(String phone);

}
