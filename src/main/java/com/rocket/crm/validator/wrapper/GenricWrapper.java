package com.rocket.crm.validator.wrapper;

import java.util.Map;

import javax.validation.constraints.NotNull;

public abstract class GenricWrapper {

	@NotNull(message = "Request context is null")
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
