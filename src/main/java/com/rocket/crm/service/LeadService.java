package com.rocket.crm.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeadService {

	Map<String, Object> create(Map<String, Object> map);

	Page<Map<String, Object>> getAll(String search, boolean advanceSearch, Map<String, Object> context,
			Pageable pageable);
}
