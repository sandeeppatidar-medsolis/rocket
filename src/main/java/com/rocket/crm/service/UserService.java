package com.rocket.crm.service;

import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface UserService {

	Map<String, Object> create(Map<String, Object> map);
//
//	User getByUsername(String username);
//
//	Page<User> getAll(Pageable pageable, String search);
//
//	Map<String, Object> forgotPasswordLink(String username);

	Map<String, Object> sendOtp(String number);

	Map<String, Object> getAdditionalInformation(OAuth2Authentication authentication);

}
