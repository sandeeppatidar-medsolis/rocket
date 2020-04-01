package com.rocket.crm.utility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversionUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConversionUtils.class);

	private ConversionUtils() {

	}

	public static Map<String, Object> convertEntityToMap(Object object, int level) {
		Map<String, Object> result = new HashMap<>();
		switch (level) {
		case 0:
		case 1:
			convertEntityToMapR2(object, result);
			break;
		case 2:
			convertEntityToMapR1(object, result);
			break;
		default:
			convertEntityToMapR(object, result);
		}

		return result;
	}

	public static List<Map<String, Object>> convertEntityToMapList(List<?> object, int level) {
		List<Map<String, Object>> result = new ArrayList<>();
		for (Object obj : object) {
			result.add(convertEntityToMap(obj, level));
		}
		return result;
	}

	public static Object convertMapToEntity(Map<String, Object> object, Class<?> objClass) {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return objectMapper.convertValue(object, objClass);

	}

	public static Map<String, Object> convertEntityToMapR(Object object, Map<String, Object> jgen) {
		for (Method m : object.getClass().getMethods()) {
			if (checkMethodeType(m)) {
				try {
					putValuesToMapR(m.invoke(object), jgen, getFieldName(m.getName()));
				} catch (Exception e) {
					LOGGER.error("Error in ConversionUtils.convertEntityToMapR ::", e);
				}
			}
		}
		return jgen;
	}

	public static Map<String, Object> convertEntityToMapR1(Object object, Map<String, Object> jgen) {
		for (Method m : object.getClass().getMethods()) {
			if (checkMethodeType(m)) {
				try {
					putValuesToMapR1(m.invoke(object), jgen, getFieldName(m.getName()));
				} catch (Exception e) {
					LOGGER.error("Error in ConversionUtils.convertEntityToMapR1 ::", e);
				}
			}
		}
		return jgen;
	}

	public static Map<String, Object> convertEntityToMapR2(Object object, Map<String, Object> jgen) {
		for (Method m : object.getClass().getMethods()) {
			if (checkMethodeType(m)) {
				try {
					putValuesToMapR2(m.invoke(object), jgen, getFieldName(m.getName()));
				} catch (Exception e) {
					LOGGER.error("Error in ConversionUtils.convertEntityToMapR2 ::", e);
				}
			}
		}
		return jgen;
	}

	private static void putValuesToMapR(Object value, Map<String, Object> jgen, String fieldName) {
		if (value != null) {
			if (value instanceof JavassistLazyInitializer || value instanceof PersistentBag) {
				return;
			} else if (value.getClass().getPackage().getName().contains("erp")) {
				if (!value.getClass().isEnum()) {
					jgen.put(fieldName, convertEntityToMapR1(value, new HashMap<String, Object>()));
				} else {
					jgen.put(fieldName, value);
				}
			} else if (value instanceof Set) {
				jgen.put(fieldName, getListFromObject(value));
			} else if (value.getClass().isPrimitive()) {
				jgen.put(fieldName, value);
			} else {
				jgen.put(fieldName, (Object) value);
			}
		} else {
			jgen.put(fieldName, null);
		}
	}

	private static void putValuesToMapR1(Object value, Map<String, Object> jgen, String fieldName) {
		if (value != null) {
			if (value instanceof JavassistLazyInitializer || value instanceof PersistentBag) {
				return;
			} else if (value.getClass().getPackage().getName().contains("erp")) {
				if (!value.getClass().isEnum()) {
					jgen.put(fieldName, convertEntityToMapR2(value, new HashMap<String, Object>()));
				} else {
					jgen.put(fieldName, value);
				}
			} else if (value instanceof Set) {
				jgen.put(fieldName, null);
			} else if (value.getClass().isPrimitive()) {
				jgen.put(fieldName, value);
			} else {
				jgen.put(fieldName, (Object) value);
			}
		} else {
			jgen.put(fieldName, null);
		}
	}

	private static void putValuesToMapR2(Object value, Map<String, Object> jgen, String fieldName) {
		if (value != null) {
			if (value instanceof JavassistLazyInitializer || value instanceof PersistentBag) {
				return;
			} else if (value.getClass().getPackage().getName().contains("erp")) {
				if (!value.getClass().isEnum()) {
					jgen.put(fieldName, null);
				} else {
					jgen.put(fieldName, value);
				}
			} else if (value instanceof Set) {
				jgen.put(fieldName, null);
			} else if (value.getClass().isPrimitive()) {
				jgen.put(fieldName, value);
			} else {
				jgen.put(fieldName, (Object) value);
			}
		} else {
			jgen.put(fieldName, null);
		}
	}

	private static ArrayList<Map<String, Object>> getListFromObject(Object value) {
		ArrayList<Map<String, Object>> list = new ArrayList<>();
		for (Object val : (Set<?>) value) {
			list.add(convertEntityToMapR1(val, new HashMap<String, Object>()));
		}
		return list;
	}

	private static String getFieldName(String methodName) {
		return methodName.startsWith("is") ? methodName.substring(2, 3).toLowerCase() + methodName.substring(3)
				: methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
	}

	private static boolean checkMethodeType(Method m) {
		return (m.getName().startsWith("get") || m.getName().startsWith("is")) && !m.getName().startsWith("getClass");
	}

	public static Map<String, Object> convertPageToMap(Page<?> page, int level) {
		Map<String, Object> result = new HashMap<>();
		result.put("content", ConversionUtils.convertEntityToMapList(new ArrayList<Object>(page.getContent()), level));
		result.put("totalElements", page.getTotalElements());
		result.put("number", page.getNumber());
		result.put("size", page.getSize());
		result.put("last", page.isLast());
		result.put("totalPages", page.getTotalPages());
		result.put("sort", page.getSort());
		result.put("first", page.isFirst());
		result.put("numberOfElements", page.getNumberOfElements());
		return result;
	}

}