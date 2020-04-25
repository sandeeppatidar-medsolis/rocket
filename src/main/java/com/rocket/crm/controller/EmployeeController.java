package com.rocket.crm.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rocket.crm.constants.AppConstants;
import com.rocket.crm.constants.MsgConstants;
import com.rocket.crm.constants.UriConstants;
import com.rocket.crm.service.EmployeeService;
import com.rocket.crm.utility.ResponseMessage;
import com.rocket.crm.validator.wrapper.EmployeeWrapper;

@RestController
@RequestMapping(value = UriConstants.EMPLOYEE_API)
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping(value = UriConstants.CREATE)
	public ResponseMessage<Map<String, Object>> countrySave(@RequestBody @Valid EmployeeWrapper map) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.EMPLOYEE_CREATE_SUCCESSFULLY,
				employeeService.create(map.getMap()));
	}
	
	@GetMapping(value = UriConstants.GET_ALL)
	public ResponseMessage<Page<Map<String, Object>>> getAll(
			@RequestParam(value = "advanceSearch", required = false) boolean advanceSearch,
			@RequestParam(value = AppConstants.KEY_SEARCH, required = false) String search,
			@RequestParam Map<String, Object> context, Pageable pageable) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.ROLE_GET_SUCCESSFULLY,
				employeeService.getAll(search, advanceSearch, context, pageable));
	}

}
