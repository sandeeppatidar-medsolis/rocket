package com.rocket.crm.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.rocket.crm.constants.AppConstants;
import com.rocket.crm.constants.MsgConstants;
import com.rocket.crm.entity.Role;
import com.rocket.crm.entity.User;
import com.rocket.crm.exception.GenricException;
import com.rocket.crm.exception.UserRequestException;
import com.rocket.crm.repository.RoleRepository;
import com.rocket.crm.repository.UserRepository;
import com.rocket.crm.service.UserService;
import com.rocket.crm.utility.AppUtility;
import com.rocket.crm.utility.ConversionUtils;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public Map<String, Object> create(Map<String, Object> map) {
		User user = save(map);
		return ConversionUtils.convertEntityToMap(user, 1);
	}

	@Override
	@Transactional
	public User save(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rolesMap = (List<Map<String, Object>>) map.get(AppConstants.ROLES);

		if (AppUtility.isEmpty(rolesMap)) {
			throw new ValidationException(MsgConstants.ERROR_ROLE_NOT_NULL);
		}

		Set<Role> roles = new HashSet<>();
		for (Map<String, Object> roleMap : rolesMap) {
			String name = (String) roleMap.get(AppConstants.NAME);
			if (AppUtility.isEmpty(name)) {
				throw new ValidationException(MsgConstants.ERROR_ROLE_NAME_NOT_NULL);
			}
			Role role = roleRepository.findByName(name);
			if (AppUtility.isEmpty(role)) {
				throw new GenricException(MsgConstants.ERROR_ROLE_NOT_EXIST);
			}
			roles.add(role);
		}

		User user = (User) ConversionUtils.convertMapToEntity(map, User.class);
		if (AppUtility.isEmpty(user.getPassword())) {
			throw new UserRequestException(MsgConstants.ERROR_PASSWORD_NOT_NULL);
		}
		User existUser = userRepository.findByUsername((String) map.get(AppConstants.USERNAME));
		if (!AppUtility.isEmpty(existUser)) {
			throw new GenricException(MsgConstants.ERROR_USERNAME_ALREADY_EXIST);
		}
		user.setEnabled(true);
		user.setAccountNonExpired(true);
		user.setCredentialsNonExpired(true);
		user.setAccountNonLocked(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setUsername(user.getUsername());
		user.setRoles(new HashSet<>(roles));
		user = userRepository.save(user);
		return user;
	}

	@Override
	public Map<String, Object> update(Map<String, Object> map) {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rolesMap = (List<Map<String, Object>>) map.get(AppConstants.ROLES);

		if (AppUtility.isEmpty(rolesMap)) {
			throw new ValidationException(MsgConstants.ERROR_ROLE_NOT_NULL);
		}

		User existUser = userRepository.findById((String) map.get(AppConstants.USERNAME))
				.orElseThrow(() -> new UsernameNotFoundException(MsgConstants.USER_NOT_REGISTERED));

		Set<Role> roles = new HashSet<>();
		for (Map<String, Object> roleMap : rolesMap) {
			String name = (String) roleMap.get(AppConstants.NAME);
			if (AppUtility.isEmpty(name)) {
				throw new ValidationException(MsgConstants.ERROR_ROLE_NAME_NOT_NULL);
			}
			Role role = roleRepository.findByName(name);
			if (AppUtility.isEmpty(role)) {
				throw new GenricException(MsgConstants.ERROR_ROLE_NOT_EXIST);
			}
			roles.add(role);
		}

		// User user = (User) ConversionUtils.convertMapToEntity(map, User.class);
		existUser.setRoles(new HashSet<>(roles));
		existUser = userRepository.save(existUser);
		return ConversionUtils.convertEntityToMap(existUser, 1);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (AppUtility.isEmpty(user)) {
			throw new UsernameNotFoundException(MsgConstants.ERROR_INVALID_CREDENTIAL);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getAuthorities());
	}

	@Override
	public Map<String, Object> getAdditionalInformation(OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		Set<String> roles = new HashSet<>();
		for (GrantedAuthority autority : authentication.getAuthorities()) {
			Role role = (Role) autority;
			roles.add(role.getName());
		}
		User user = userRepository.findByUsername(authentication.getName());
		if (AppUtility.isEmpty(user)) {
			throw new UsernameNotFoundException(MsgConstants.ERROR_INVALID_USER);
		}
		additionalInfo.put(AppConstants.USERNAME, authentication.getName());
		// additionalInfo.put(AppConstants.NAME, user.getName());
		additionalInfo.put(AppConstants.ROLES, roles);
		return additionalInfo;
	}

	@Override
	public Map<String, Object> getByUsername(String username) {
		Map<String, Object> user = userRepository.findUserByUsername(username);
		if (AppUtility.isEmpty(user)) {
			throw new GenricException(MsgConstants.ERROR_INVALID_USER);
		}
		return user;
	}

}
