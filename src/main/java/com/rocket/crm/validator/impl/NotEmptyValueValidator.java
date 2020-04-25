package com.rocket.crm.validator.impl;

import java.util.Collection;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.rocket.crm.utility.ValidationUtility;
import com.rocket.crm.validator.NotEmptyValue;

public class NotEmptyValueValidator implements ConstraintValidator<NotEmptyValue, Map<String, ? extends Object>> {

	private String[] keys;

	@Override
	public void initialize(NotEmptyValue notEmptyValue) {
		this.keys = notEmptyValue.value();
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
				context.buildConstraintViolationWithTemplate(String.format("%s field is empty.", key))
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

			boolean isEmpty = false;
			if (value instanceof String)
				isEmpty = ValidationUtility.isEmpty((String) value);
			else if (value instanceof Map)
				isEmpty = ValidationUtility.isEmpty((Map<?, ?>) value);
			else if (value instanceof Collection)
				isEmpty = ValidationUtility.isEmpty((Collection<?>) value);
			else if (value instanceof Integer)
				isEmpty = ValidationUtility.isEmpty((Integer) value);
			else
				throw new IllegalArgumentException("Only apply string or collection classes");

			if (isEmpty)
				return false;

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
