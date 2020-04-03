package com.rocket.crm.service;

import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface UserService {

	Map<String, Object> create(Map<String, Object> map);

	Map<String, Object> getAdditionalInformation(OAuth2Authentication authentication);
	
	Map<String, Object> update(Map<String, Object> map);


}
