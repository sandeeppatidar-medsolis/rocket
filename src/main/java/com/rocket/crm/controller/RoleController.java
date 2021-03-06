package com.rocket.crm.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rocket.crm.constants.AppConstants;
import com.rocket.crm.constants.MsgConstants;
import com.rocket.crm.constants.UriConstants;
import com.rocket.crm.service.RoleService;
import com.rocket.crm.utility.ResponseMessage;
import com.rocket.crm.validator.wrapper.RoleWrapper;

@RestController
@RequestMapping(value = UriConstants.ROLE_API)
public class RoleController {

	@Autowired
	RoleService roleService;

	@PostMapping(value = UriConstants.CREATE)
	public ResponseMessage<Map<String, Object>> roleSave(@RequestBody @Valid RoleWrapper map) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.ROLE_CREATE_SUCCESSFULLY,
				roleService.create(map.getMap()));
	}

	@GetMapping(value = UriConstants.GET_BY_NAME)
	public ResponseMessage<Map<String, Object>> getByName(@PathVariable(value = "name") String name) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.ROLE_GET_SUCCESSFULLY,
				roleService.getByName(name));
	}

	@GetMapping(value = UriConstants.GET_ALL)
	public ResponseMessage<Page<Map<String, Object>>> getAll(
			@RequestParam(value = "advanceSearch", required = false) boolean advanceSearch,
			@RequestParam(value = AppConstants.KEY_SEARCH, required = false) String search,
			@RequestParam Map<String, Object> context, Pageable pageable) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.ROLE_GET_SUCCESSFULLY,
				roleService.getAll(search, advanceSearch, context, pageable));
	}

	@DeleteMapping(value = UriConstants.DELETE_BY_NAME)
	public ResponseMessage<Map<String, Object>> delete(@PathVariable(value = "name") String name) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.ROLE_DELETE_SUCCESSFULLY,
				roleService.delete(name));
	}

}
