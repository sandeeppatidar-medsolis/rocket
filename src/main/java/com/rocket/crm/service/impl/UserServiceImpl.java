package com.rocket.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.rocket.crm.config.Translator;
import com.rocket.crm.constants.AppConstants;
import com.rocket.crm.entity.User;
import com.rocket.crm.exception.GenricException;
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
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public Map<String, Object> create(Map<String, Object> map) {
		String phone = ((String) map.get(AppConstants.PHONE));
		@SuppressWarnings("unchecked")
		List<String> roles = (List<String>) map.get(AppConstants.ROLES_KEY);
		if (roles == null || roles.isEmpty()) {
			throw new ValidationException(Translator.toLocale("ERROR_ROLE_NOT_FOUND"));
		}
		for (String name : roles) {
			if (AppConstants.ROLE_SUPER_ADMIN.equals(name)) {
				throw new ValidationException(Translator.toLocale("ERROR_INVALID_ROLE"));
			}
		}
		User user;
		if (map.get("id") != null) {
			User existUser = userRepository.findById((String) map.get("id"))
					.orElseThrow(() -> new UsernameNotFoundException(Translator.toLocale("USER_NOT_REGISTERED")));
			if (!AppUtility.isEmpty(map.get(AppConstants.EMAIL))
					&& !(existUser.getEmail().equals((String) map.get(AppConstants.EMAIL)))
					&& !(userRepository.findByEmail((String) map.get(AppConstants.EMAIL)).isEmpty())) {
				throw new GenricException(Translator.toLocale("ERROR_EMAIL_ALREADY_EXIST"));
			}
			if (!(existUser.getPhone().equals((String) map.get(AppConstants.PHONE)))
					&& (userRepository.findByPhone(phone) != null)) {
				throw new GenricException(Translator.toLocale("ERROR_PHONE_ALREADY_EXIST"));
			}
			existUser.setEmail((String) map.get(AppConstants.EMAIL));
			existUser.setName((String) map.get(AppConstants.NAME));
			existUser.setPhone((String) map.get(AppConstants.PHONE));
			existUser.setRoles(new HashSet<>(roles));
			user = existUser;
		} else {
			user = (User) ConversionUtils.convertMapToEntity(map, User.class);
			if (!(AppUtility.isEmpty(user.getEmail()) || userRepository.findByEmail(user.getEmail()).isEmpty())) {
				throw new GenricException(Translator.toLocale("ERROR_EMAIL_ALREADY_EXIST"));
			}
			if (!userRepository.findByPhone(user.getPhone()).isEmpty()) {
				throw new GenricException(Translator.toLocale("ERROR_PHONE_ALREADY_EXIST"));
			}
//			if (AppUtility.isEmpty(user.getPassword())) {
//				throw new UserRequestException(MsgConstants.ERROR_PASSWORD_NOT_NULL);
//			}
			user.setEnabled(true);
			user.setAccountNonExpired(true);
			user.setCredentialsNonExpired(true);
			user.setAccountNonLocked(true);
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setUsername(UUID.randomUUID().toString());
			user.setRoles(new HashSet<>(roles));
		}
		user = userRepository.save(user);
		map.put("id", user);
		return map;
	}

//	@Override
//	public User getByUsername(String username) {
//		Query query = new Query();
//		query.fields().include("id");
//		query.fields().include("roles");
//		query.fields().include("name");
//		query.fields().include("phone");
//		query.fields().include("email");
//		query.fields().include("enabled");
//		query.addCriteria(Criteria.where(AppConstants.USERNAME).is(username));
//		List<User> user = JdbcTemplate.find(query, User.class);
//		if (AppUtility.isEmpty(user)) {
//			throw new GenricException(Translator.toLocale("USER_NOT_REGISTERED"));
//		}
//		return user.get(0);
//	}

	@Override
	public UserDetails loadUserByUsername(String phone) {
		// User user = userRepository.findByEmail(username);
		List<User> users = userRepository.findByPhone(phone);
		if (users.isEmpty()) {
			throw new UsernameNotFoundException(Translator.toLocale("ERROR_PHONE_NOT_REGISTERED"));
		}
		User user = users.get(0);
		if (user.getPassword() == null) {
			throw new GenricException(Translator.toLocale("ERROR_INTERNAL_OTP"));
		}
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getPhone(),
				user.getPassword(), user.getAuthorities());
		if (user.getOtpDate() < (System.currentTimeMillis())) {
			user.setPassword(null);
			user.setOtpDate(null);
			userRepository.save(user);
			throw new BadCredentialsException(Translator.toLocale("ERROR_OTP_EXPIRED"));
		}
		return userDetails;
	}

//	@Override
//	public Map<String, Object> forgotPasswordLink(String username) {
//		Map<String, Object> map = new HashMap<>();
//		User user = userRepository.findByUsername(username);
//		if (AppUtility.isEmpty(user)) {
//			throw new GenricException(Translator.toLocale("ERROR_INVALID_EMAIL_ADDRESS"));
//		}
//		map.put("message", Translator.toLocale("PASSWORD_RESET_LINK_SENT"));
//		return map;
//	}

//	@Override
//	public Page<User> getAll(Pageable pageable, String search) {
//		Query query = new Query().with(pageable);
//		String i = "i";
//		query.fields().include("id");
//		query.fields().include("roles");
//		query.fields().include("name");
//		query.fields().include("phone");
//		query.fields().include("email");
//		query.fields().include("enabled");
//		query.fields().include("username");
//		List<String> list = new ArrayList<>();
//		list.add(AppConstants.ROLE_SUPER_ADMIN);
//
//		Criteria criteria = new Criteria();
//		if (!AppUtility.isEmpty(search)) {
//			criteria.orOperator(Criteria.where(AppConstants.NAME).regex(search.toLowerCase(), i),
//					Criteria.where(AppConstants.EMAIL).regex(search.toLowerCase(), i),
//					Criteria.where(AppConstants.PHONE).regex(search.toLowerCase(), i));
//		} else {
//			criteria.andOperator(Criteria.where(AppConstants.ROLES_KEY).nin(list));
//		}
//		query.addCriteria(criteria);
//		List<User> userList = mongoTemplate.find(query, User.class);
//		return PageableExecutionUtils.getPage(userList, pageable, () -> mongoTemplate.count(query, User.class));
//	}

	@Override
	public Map<String, Object> getAdditionalInformation(OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		additionalInfo.put(AppConstants.PHONE, authentication.getName());
		List<User> users = userRepository.findByPhone(authentication.getName());
		if (users.isEmpty()) {
			throw new UsernameNotFoundException(Translator.toLocale("ERROR_PHONE_NOT_REGISTERED"));
		}
		User user = users.get(0);
		additionalInfo.put("name", user.getName());
		List<String> roles = new ArrayList<>();
		additionalInfo.put("role", roles);
		return additionalInfo;
	}

	@Override
	public Map<String, Object> sendOtp(String number) {
		if (!AppUtility.isEmpty(number) || number.length() != 10) {
			List<User> users = userRepository.findByPhone(number);
			if (!AppUtility.isEmpty(users)) {
				User user = users.get(0);
				if (!AppUtility.isEmpty(users)) {
					String otp = String.valueOf(generateRandom());
					user.setPassword(passwordEncoder.encode(otp));
					user.setOtpDate(System.currentTimeMillis() + 1 * 60 * 1000);
					userRepository.save(user);
					Map<String, Object> map = new HashMap<>();
					map.put("OTP", otp);
					return map;

				} else {
					throw new UsernameNotFoundException(Translator.toLocale("ERROR_PHONE_NOT_REGISTERED"));
				}
			} else {
				throw new UsernameNotFoundException(Translator.toLocale("ERROR_PHONE_NOT_REGISTERED"));
			}
		} else {
			throw new GenricException(Translator.toLocale("INVALID_PHONE_NUMBER"));
		}

	}

	private int generateRandom() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}
}
