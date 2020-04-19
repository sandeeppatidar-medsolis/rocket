package com.rocket.crm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocket.crm.constants.AppConstants;
import com.rocket.crm.constants.MsgConstants;
import com.rocket.crm.entity.Branch;
import com.rocket.crm.entity.CityMaster;
import com.rocket.crm.entity.CountryMaster;
import com.rocket.crm.entity.StateMaster;
import com.rocket.crm.exception.UserRequestException;
import com.rocket.crm.repository.BranchRepository;
import com.rocket.crm.service.BranchService;
import com.rocket.crm.utility.AppUtility;
import com.rocket.crm.utility.ConversionUtils;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	BranchRepository branchRepository;

	@Override
	public Map<String, Object> create(Map<String, Object> map) {
		if (AppUtility.isEmpty(map.get(AppConstants.NAME))) {
			throw new UserRequestException(MsgConstants.ERROR_NAME_NOT_NULL);
		}
		Branch branch = new Branch();
		branch.setName((String) map.get(AppConstants.NAME));
		branch.setAddress((String) map.get(AppConstants.ADDRESS));
		if (!AppUtility.isEmpty(map.get(AppConstants.COUNTRY_ID))) {
			branch.setCountry(new CountryMaster(new Long((Integer) map.get(AppConstants.COUNTRY_ID))));
		}
		if (!AppUtility.isEmpty(map.get(AppConstants.STATE_ID))) {
			branch.setState(new StateMaster(new Long((Integer) map.get(AppConstants.STATE_ID))));
		}
		if (!AppUtility.isEmpty(map.get(AppConstants.CITY_ID))) {
			branch.setCity(new CityMaster(new Long((Integer) map.get(AppConstants.CITY_ID))));
		}

		return ConversionUtils.convertEntityToMap(branchRepository.save(branch), 1);
	}

	@Override
	public List<Map<String, Object>> getAllBranch() {
		return branchRepository.getAllBranch();
	}

}
