package com.rocket.crm.utility;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppUtility {

	private AppUtility() {

	}

	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}

	public static boolean isEmpty(Object object) {
		return (object == null);
	}

	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	public static boolean isEmpty(String string) {
		return (string == null || string.trim().length() == 0);
	}

	public static JsonNode converteStringToJson(String text) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readTree(text);
	}

	public static Object getObject(Map<String, Object> params, Class<?> clazz) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode json = objectMapper.convertValue(params, JsonNode.class);
		return objectMapper.treeToValue(json, clazz);
	}

}
