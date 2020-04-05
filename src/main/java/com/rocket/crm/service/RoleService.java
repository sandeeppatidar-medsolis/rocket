package com.rocket.crm.service;

import java.util.Map;

public interface RoleService {

	Map<String, Object> create(Map<String, Object> map);

	Map<String, Object> getByName(String name);
}
