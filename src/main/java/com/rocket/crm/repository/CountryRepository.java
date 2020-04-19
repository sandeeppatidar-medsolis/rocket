package com.rocket.crm.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rocket.crm.entity.CountryMaster;

@Repository
public interface CountryRepository extends JpaRepository<CountryMaster, Long> {

	@Query("SELECT cm.name as name , cm.id as id FROM CountryMaster cm")
	List<Map<String, Object>> findAllCountry();

}
