package com.rocket.crm.service;

import java.util.List;
import java.util.Map;

public interface MasterService {

	Map<String, Object> countryCreate(Map<String, Object> map);

	Map<String, Object> cityCreate(Map<String, Object> map);

	Map<String, Object> stateCreate(Map<String, Object> map);

	List<Map<String, Object>> getAllCountry();

	List<Map<String, Object>> getAllState(Long id);

	List<Map<String, Object>> getAllCity(Long id);

}
