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
		if (!(AppUtility.isEmpty(user.getEmail()) || userRepository.findByEmail(user.getEmail()).isEmpty())) {
			throw new GenricException(MsgConstants.ERROR_EMAIL_ALREADY_EXIST);
		}
		if (!userRepository.findByPhone(user.getPhone()).isEmpty()) {
			throw new GenricException(MsgConstants.ERROR_PHONE_ALREADY_EXIST);
		}

		user.setEnabled(true);
		user.setAccountNonExpired(true);
		user.setCredentialsNonExpired(true);
		user.setAccountNonLocked(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setUsername(user.getUsername());
		user.setRoles(new HashSet<>(roles));
		user = userRepository.save(user);
		return ConversionUtils.convertEntityToMap(user, 1);
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

		User user = (User) ConversionUtils.convertMapToEntity(map, User.class);
		if (!AppUtility.isEmpty(map.get(AppConstants.EMAIL))
				&& !(existUser.getEmail().equals((String) map.get(AppConstants.EMAIL)))
				&& !(userRepository.findByEmail((String) map.get(AppConstants.EMAIL)).isEmpty())) {
			throw new GenricException(MsgConstants.ERROR_EMAIL_ALREADY_EXIST);
		}

		String phone = ((String) map.get(AppConstants.PHONE));
		if (!(existUser.getPhone().equals((String) map.get(AppConstants.PHONE)))
				&& (userRepository.findByPhone(phone) != null)) {
			throw new GenricException(MsgConstants.ERROR_PHONE_ALREADY_EXIST);
		}

		existUser.setRoles(new HashSet<>(roles));
		existUser.setName(user.getName());
		existUser.setLocation(user.getLocation());
		existUser.setCity(user.getCity());
		existUser.setEmail(user.getEmail());
		existUser.setPhone(user.getPhone());
		user = userRepository.save(existUser);
		return ConversionUtils.convertEntityToMap(user, 1);
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
		additionalInfo.put(AppConstants.NAME, user.getName());
		additionalInfo.put(AppConstants.ROLES, roles);
		return additionalInfo;
	}

	@Override
	public Map<String, Object> getByUserId(String username) {
		Map<String, Object> user = userRepository.findUserByUsername(username);
		if (AppUtility.isEmpty(user)) {
			throw new GenricException(MsgConstants.ERROR_INVALID_USER);
		}
		return user;
	}

	@Override
	public Map<String, Object> roleCreate(Map<String, Object> map) {
		Role role = (Role) ConversionUtils.convertMapToEntity(map, Role.class);
		if (AppUtility.isEmpty(role.getName())) {
			throw new GenricException(MsgConstants.ERROR_ROLE_NAME_NOT_NULL);
		}
		role = roleRepository.save(role);
		return ConversionUtils.convertEntityToMap(role, 1);
	}

	@Override
	public Map<String, Object> getRoleByRoleName(String name) {
		Map<String, Object> role = roleRepository.findRoleByName(name);
		if (AppUtility.isEmpty(role)) {
			throw new GenricException(MsgConstants.ERROR_INVALID_ROLE);
		}
		return role;
	}

}
