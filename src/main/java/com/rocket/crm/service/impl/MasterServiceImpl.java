package com.rocket.crm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocket.crm.constants.AppConstants;
import com.rocket.crm.constants.MsgConstants;
import com.rocket.crm.entity.CityMaster;
import com.rocket.crm.entity.CountryMaster;
import com.rocket.crm.entity.StateMaster;
import com.rocket.crm.exception.UserRequestException;
import com.rocket.crm.repository.CityRepository;
import com.rocket.crm.repository.CountryRepository;
import com.rocket.crm.repository.StateRepository;
import com.rocket.crm.service.MasterService;
import com.rocket.crm.utility.AppUtility;
import com.rocket.crm.utility.ConversionUtils;

@Service
public class MasterServiceImpl implements MasterService {

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	CityRepository cityRepository;

	@Override
	public Map<String, Object> countryCreate(Map<String, Object> map) {
		if (AppUtility.isEmpty(map.get(AppConstants.NAME))) {
			throw new UserRequestException(MsgConstants.ERROR_NAME_NOT_NULL);
		}
		CountryMaster country = new CountryMaster();
		country.setName((String) map.get(AppConstants.NAME));
		return ConversionUtils.convertEntityToMap(countryRepository.save(country), 1);
	}

	@Override
	public Map<String, Object> stateCreate(Map<String, Object> map) {

		if (AppUtility.isEmpty(map.get(AppConstants.NAME))) {
			throw new UserRequestException(MsgConstants.ERROR_NAME_NOT_NULL);
		}
		if (AppUtility.isEmpty(map.get(AppConstants.COUNTRY_ID))) {
			throw new UserRequestException(MsgConstants.ERROR_COUNTRY_NOT_NULL);
		}
		CountryMaster country = new CountryMaster(new Long((Integer) map.get(AppConstants.COUNTRY_ID)));
		StateMaster state = new StateMaster();
		state.setName((String) map.get(AppConstants.NAME));
		state.setCountry(country);

		return ConversionUtils.convertEntityToMap(stateRepository.save(state), 1);
	}

	@Override
	public Map<String, Object> cityCreate(Map<String, Object> map) {

		if (AppUtility.isEmpty(map.get(AppConstants.NAME))) {
			throw new UserRequestException(MsgConstants.ERROR_NAME_NOT_NULL);
		}
		if (AppUtility.isEmpty(map.get(AppConstants.STATE_ID))) {
			throw new UserRequestException(MsgConstants.ERROR_STATE_NOT_NULL);
		}

		StateMaster state = new StateMaster(new Long((Integer) map.get(AppConstants.STATE_ID)));
		CityMaster city = new CityMaster();
		city.setName((String) map.get(AppConstants.NAME));
		city.setState(state);

		return ConversionUtils.convertEntityToMap(cityRepository.save(city), 1);
	}

	@Override
	public List<Map<String, Object>> getAllCountry() {
		return countryRepository.findAllCountry();
	}

	@Override
	public List<Map<String, Object>> getAllState(Long id) {
		return stateRepository.findAllStateById(id);
	}

	@Override
	public List<Map<String, Object>> getAllCity(Long id) {
		return cityRepository.findAllCityById(id);
	}

}
