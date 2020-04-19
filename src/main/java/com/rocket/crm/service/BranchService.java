package com.rocket.crm.service;

import java.util.List;
import java.util.Map;

public interface BranchService {

	Map<String, Object> create(Map<String, Object> map);

	List<Map<String, Object>> getAllBranch();

}
