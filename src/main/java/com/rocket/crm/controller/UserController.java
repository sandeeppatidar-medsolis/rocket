package com.rocket.crm.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rocket.crm.config.Translator;
import com.rocket.crm.constants.UriConstants;
import com.rocket.crm.service.UserService;
import com.rocket.crm.utility.ResponseMessage;
import com.rocket.crm.validator.wrapper.UserWrapper;

@RestController
@RequestMapping(value = UriConstants.USER)
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(value = "/create")
	public ResponseMessage<Map<String, Object>> save(@RequestBody @Valid UserWrapper map) {
		return new ResponseMessage<>(HttpStatus.OK.value(), Translator.toLocale("USER_CREATE"),
				userService.create(map.getMap()));
	}

	@GetMapping(value = "/otp_send")
	public ResponseMessage<Map<String, Object>> save(@RequestParam("number") String number) {
		return new ResponseMessage<>(HttpStatus.OK.value(), Translator.toLocale("OTP_SENT"),
				userService.sendOtp(number));
	}

//	@GetMapping(value = "/forgot")
//	public ResponseMessage<Map<String, Object>> forgot(@RequestParam("username") String username) {
//		return new ResponseMessage<>(HttpStatus.OK.value(), Translator.toLocale("RESET_LINK_SENT"),
//				userService.forgotPasswordLink(username));
//	}

	/*
	 * Getting All user with pagination API
	 */
//	@GetMapping("/get_all")
//	public ResponseMessage<Page<User>> getAll(Pageable pageable,
//			@RequestParam(value = AppConstants.KEY_SEARCH, required = false) String search) {
//		return new ResponseMessage<>(HttpStatus.OK.value(), userService.getAll(pageable, search));
//	}

}
