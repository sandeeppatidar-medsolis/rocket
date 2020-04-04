package com.rocket.crm.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rocket.crm.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

	Role findByName(String name);

	@Query("SELECT r.name as name, r.displayName as displayName FROM Role r WHERE r.name=:name")
	Map<String, Object> findRoleByName(@Param("name") String name);

}
