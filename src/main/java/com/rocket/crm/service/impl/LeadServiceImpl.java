package com.rocket.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rocket.crm.constants.AppConstants;
import com.rocket.crm.constants.MsgConstants;
import com.rocket.crm.dao.EmployeeDao;
import com.rocket.crm.entity.Branch;
import com.rocket.crm.entity.CityMaster;
import com.rocket.crm.entity.CountryMaster;
import com.rocket.crm.entity.Employee;
import com.rocket.crm.entity.StateMaster;
import com.rocket.crm.exception.GenricException;
import com.rocket.crm.repository.BranchRepository;
import com.rocket.crm.repository.LeadRepository;
import com.rocket.crm.service.LeadService;
import com.rocket.crm.service.UserService;
import com.rocket.crm.utility.AppUtility;
import com.rocket.crm.utility.ConversionUtils;

@Service
public class LeadServiceImpl implements LeadService {

	@Autowired
	LeadRepository leadRepository;

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	UserService userService;

	@Autowired
	EmployeeDao employeeDao;

	@Override
	@Transactional
	public Map<String, Object> create(Map<String, Object> map) {

		Employee employee = (Employee) ConversionUtils.convertMapToEntity(map, Employee.class);

		if (!AppUtility.isEmpty(employee.getId())) {
			Employee employeeExisit = leadRepository.getOne(employee.getId());

			if (!AppUtility.isEmpty(employee.getEmail()) && !(employee.getEmail().equals(employeeExisit.getEmail()))
					&& !(leadRepository.findByEmail(employee.getEmail()).isEmpty())) {
				throw new GenricException(MsgConstants.ERROR_EMAIL_ALREADY_EXIST);
			}

			if (!AppUtility.isEmpty(employee.getMobile()) && !(employee.getMobile().equals(employeeExisit.getMobile()))
					&& !(leadRepository.findByMobile(employee.getMobile()).isEmpty())) {
				throw new GenricException(MsgConstants.ERROR_PHONE_ALREADY_EXIST);
			}

			employee.setUser(employeeExisit.getUser());

		} else {

			if (AppUtility.isEmpty(map.get(AppConstants.PASSWORD))) {
				throw new ValidationException(MsgConstants.ERROR_PASSWORD_NOT_NULL);
			}

			if (!AppUtility.isEmpty(employee.getEmail())
					&& !AppUtility.isEmpty(leadRepository.findByEmail(employee.getEmail()))) {
				throw new GenricException(MsgConstants.ERROR_EMAIL_ALREADY_EXIST);
			}

			if (!AppUtility.isEmpty(leadRepository.findByMobile(employee.getMobile()))) {
				throw new GenricException(MsgConstants.ERROR_PHONE_ALREADY_EXIST);
			}

			// convert role string to list of role for user create
			List<Map<String, Object>> roles = new ArrayList<>();
			Map<String, Object> convertRole = new HashMap<>();
			convertRole.put(AppConstants.NAME, map.get(AppConstants.ROLE));
			roles.add(convertRole);

			map.put(AppConstants.ROLES, roles);
			employee.setUser(userService.save(map));
		}

		Optional<Branch> branch = branchRepository.findById(new Long((Integer) map.get(AppConstants.BRANCH_ID)));

		if (!branch.isPresent()) {
			throw new GenricException(MsgConstants.ERROR_INVALID_BRANCH);
		}

		if (!AppUtility.isEmpty(map.get(AppConstants.COUNTRY_ID))) {
			employee.setCountry(new CountryMaster(new Long((Integer) map.get(AppConstants.COUNTRY_ID))));
		}
		if (!AppUtility.isEmpty(map.get(AppConstants.STATE_ID))) {
			employee.setState(new StateMaster(new Long((Integer) map.get(AppConstants.STATE_ID))));
		}
		if (!AppUtility.isEmpty(map.get(AppConstants.CITY_ID))) {
			employee.setCity(new CityMaster(new Long((Integer) map.get(AppConstants.CITY_ID))));
		}

		employee.setBranch(branch.get());

		// AVATAR

		leadRepository.save(employee);

		return map;
	}

	@Override
	public Page<Map<String, Object>> getAll(String search, boolean advanceSearch, Map<String, Object> context,
			Pageable pageable) {
		return employeeDao.getAll(search, advanceSearch, context, pageable);
	}

}
