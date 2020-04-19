package com.rocket.crm.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rocket.crm.entity.StateMaster;

@Repository
public interface StateRepository extends JpaRepository<StateMaster, Long> {

	@Query("SELECT sm.name as name , sm.id as id FROM StateMaster sm WHERE sm.country.id=:id")
	List<Map<String, Object>> findAllStateById(@Param("id") Long id);

}
