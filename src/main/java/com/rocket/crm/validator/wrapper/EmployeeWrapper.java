package com.rocket.crm.validator.wrapper;

import java.util.Map;

import com.rocket.crm.constants.AppConstants;
import com.rocket.crm.validator.ContainsKeys;
import com.rocket.crm.validator.NotEmptyValue;

public class EmployeeWrapper extends GenricWrapper {

	@ContainsKeys({ AppConstants.FIRSTNAME, AppConstants.LASTNAME, AppConstants.GENDER, AppConstants.DOB,
			AppConstants.DOJ, AppConstants.MOBILE, AppConstants.BRANCH_ID, AppConstants.USERNAME, AppConstants.ROLE,
			AppConstants.EMPLOYEE_TYPE, AppConstants.CITY_ID, AppConstants.STATE_ID, AppConstants.COUNTRY_ID })
	@NotEmptyValue({ AppConstants.FIRSTNAME, AppConstants.LASTNAME, AppConstants.GENDER, AppConstants.DOB,
			AppConstants.DOJ, AppConstants.MOBILE, AppConstants.BRANCH_ID, AppConstants.USERNAME, AppConstants.ROLE,
			AppConstants.EMPLOYEE_TYPE, AppConstants.CITY_ID, AppConstants.STATE_ID, AppConstants.COUNTRY_ID })
	@Override
	public Map<String, Object> getMap() {
		return super.getMap();
	}
}
