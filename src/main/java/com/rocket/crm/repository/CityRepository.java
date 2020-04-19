package com.rocket.crm.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rocket.crm.entity.CityMaster;

@Repository
public interface CityRepository extends JpaRepository<CityMaster, Long> {

	@Query("SELECT cm.name as name , cm.id as id FROM CityMaster cm WHERE cm.state.id=:id")
	List<Map<String, Object>> findAllCityById(@Param("id") Long id);

}
