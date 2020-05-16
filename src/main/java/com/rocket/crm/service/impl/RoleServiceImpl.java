package com.rocket.crm.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rocket.crm.constants.MsgConstants;
import com.rocket.crm.dao.RoleDao;
import com.rocket.crm.entity.Role;
import com.rocket.crm.exception.GenricException;
import com.rocket.crm.repository.RoleRepository;
import com.rocket.crm.service.RoleService;
import com.rocket.crm.utility.AppUtility;
import com.rocket.crm.utility.ConversionUtils;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	RoleDao roleDao;

	@Override
	public Map<String, Object> create(Map<String, Object> map) {
		Role role = (Role) ConversionUtils.convertMapToEntity(map, Role.class);
		if (AppUtility.isEmpty(role.getName())) {
			throw new GenricException(MsgConstants.ERROR_ROLE_NAME_NOT_NULL);
		}
		role = roleRepository.save(role);
		return ConversionUtils.convertEntityToMap(role, 1);
	}

	@Override
	public Map<String, Object> getByName(String name) {
		Map<String, Object> role = roleRepository.findRoleByName(name);
		if (AppUtility.isEmpty(role)) {
			throw new GenricException(MsgConstants.ERROR_INVALID_ROLE);
		}
		return role;
	}

	@Override
	public Page<Map<String, Object>> getAll(String search, boolean advanceSearch, Map<String, Object> context,
			Pageable pageable) {
		return roleDao.getAll(search, advanceSearch, context, pageable);
	}

	@Override
	public Map<String, Object> delete(String name) {
		Optional<Role> role = roleRepository.findById(name);
		if (!role.isPresent()) {
			throw new GenricException(MsgConstants.ERROR_INVALID_ROLE);
		}
		if (!AppUtility.isEmpty(role.get().getUsers())) {
			throw new GenricException(MsgConstants.ERROR_ROLE_ALREADY_ASSOCIATED_WITH_USER);
		}
		roleRepository.delete(role.get());
		return null;
	}
}
