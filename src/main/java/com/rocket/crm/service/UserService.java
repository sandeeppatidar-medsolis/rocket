package com.rocket.crm.service;

import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.rocket.crm.entity.User;

public interface UserService {

	Map<String, Object> create(Map<String, Object> map);

	Map<String, Object> getAdditionalInformation(OAuth2Authentication authentication);

	Map<String, Object> update(Map<String, Object> map);

	Map<String, Object> getByUsername(String username);

	User save(Map<String, Object> map);
}
