package com.rocket.crm.validator.wrapper;

import java.util.Map;

import com.rocket.crm.constants.AppConstants;
import com.rocket.crm.validator.ContainsKeys;
import com.rocket.crm.validator.NotEmptyValue;

public class UserWrapper extends GenricWrapper {

	@ContainsKeys({ AppConstants.NAME, AppConstants.PHONE })
	@NotEmptyValue({ AppConstants.NAME, AppConstants.PHONE })
	@Override
	public Map<String, Object> getMap() {
		return super.getMap();
	}
}
