package com.rocket.crm.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocket.crm.constants.MsgConstants;
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
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.USER_CREATE_SUCCESSFULLY,
				userService.create(map.getMap()));
	}

	@PutMapping(value = "/update")
	public ResponseMessage<Map<String, Object>> update(@RequestBody @Valid UserWrapper map) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.USER_UPDATE_SUCCESSFULLY,
				userService.update(map.getMap()));
	}

	@GetMapping(value = "/welcome")
	public String index() {
		return "welcome to rocket";
	}

}
