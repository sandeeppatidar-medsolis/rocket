package com.rocket.crm.utility;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocket.crm.constants.AppConstants;

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

	public static boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) authentication
				.getDetails();
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) oAuth2AuthenticationDetails.getDecodedDetails();
		@SuppressWarnings("unchecked")
		List<String> roles = (List<String>) userMap.get("role");
		boolean isAdmin = false;
		for (String role : roles) {
			if (role.equals(AppConstants.ROLE_ADMIN)) {
				isAdmin = true;
			}
		}
		return isAdmin;
	}

	public static String getCompanyId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) authentication
				.getDetails();
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) oAuth2AuthenticationDetails.getDecodedDetails();
		return (String) userMap.get("companyId");
	}

}
