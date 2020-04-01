package com.rocket.crm.validator.impl;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.rocket.crm.utility.ValidationUtility;
import com.rocket.crm.validator.NotNullValue;

public class NotNullValueValidator implements ConstraintValidator<NotNullValue, Map<String, ? extends Object>> {

	private String[] keys;

	@Override
	public void initialize(NotNullValue notNullValue) {
		this.keys = notNullValue.value();
	}

	@Override
	public boolean isValid(Map<String, ? extends Object> map, ConstraintValidatorContext context) {
		if (keys == null || keys.length == 0) {
			return true;
		}

		if (ValidationUtility.isEmpty(map)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Request context is null").addConstraintViolation();
			return false;
		}

		for (String key : keys) {
			if (!this.validate(map, key)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(String.format("%s field is null.", key))
						.addConstraintViolation();
				return false;
			}
		}

		return true;
	}

	private boolean validate(Map<String, ? extends Object> map, String key) {
		key = key.trim().replaceAll(" ", "");
		String[] splitKey = key.split("\\.");
		for (String splitedKey : splitKey) {

			Object value = map.get(splitedKey);

			if (ValidationUtility.isEmpty(value)) {
				return false;
			}

			if (value instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, ? extends Object> subMap = (Map<String, ? extends Object>) value;
				if (key.indexOf('.') != -1) {
					String subKey = key.substring(key.indexOf('.') + 1);
					return this.validate(subMap, subKey);
				}
			}
		}

		return true;
	}

}
