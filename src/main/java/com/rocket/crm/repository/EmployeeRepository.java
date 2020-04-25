package com.rocket.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rocket.crm.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByEmail(String email);

	List<Employee> findByMobile(String mobile);
}
