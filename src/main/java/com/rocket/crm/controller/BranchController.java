package com.rocket.crm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocket.crm.constants.MsgConstants;
import com.rocket.crm.constants.UriConstants;
import com.rocket.crm.service.BranchService;
import com.rocket.crm.utility.ResponseMessage;

@RestController
@RequestMapping(value = UriConstants.BRANCH_API)
public class BranchController {

	@Autowired
	BranchService branchService;

	@PostMapping(value = UriConstants.CREATE)
	public ResponseMessage<Map<String, Object>> countrySave(@RequestBody Map<String, Object> map) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.BRANCH_CREATE_SUCCESSFULLY,
				branchService.create(map));
	}

	@GetMapping(value = UriConstants.ALL)
	public ResponseMessage<List<Map<String, Object>>> getAllBranch() {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.BRANCH_GET_SUCCESSFULLY,
				branchService.getAllBranch());
	}

}
