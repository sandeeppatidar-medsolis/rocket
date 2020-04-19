package com.rocket.crm.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rocket.crm.entity.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

	@Query("SELECT b.name as name , b.id as id , b.address as address , b.country.name as country , b.state.name as state , b.city.name as city FROM Branch b")
	List<Map<String, Object>> getAllBranch();

}
