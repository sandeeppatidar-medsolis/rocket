package com.rocket.crm.validator.wrapper;

import java.util.Map;

import com.rocket.crm.constants.AppConstants;
import com.rocket.crm.validator.ContainsKeys;
import com.rocket.crm.validator.NotEmptyValue;

public class RoleWrapper extends GenricWrapper {

	@ContainsKeys({ AppConstants.NAME, AppConstants.DISPLAY_NAME})
	@NotEmptyValue({ AppConstants.NAME, AppConstants.DISPLAY_NAME })
	@Override
	public Map<String, Object> getMap() {
		return super.getMap();
	}
}
