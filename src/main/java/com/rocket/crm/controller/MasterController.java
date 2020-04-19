package com.rocket.crm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rocket.crm.constants.MsgConstants;
import com.rocket.crm.constants.UriConstants;
import com.rocket.crm.service.MasterService;
import com.rocket.crm.utility.ResponseMessage;

@RestController
@RequestMapping(value = UriConstants.MASTER_API)
public class MasterController {

	@Autowired
	MasterService masterService;

	@PostMapping(value = UriConstants.COUNTRY + UriConstants.CREATE)
	public ResponseMessage<Map<String, Object>> countrySave(@RequestBody Map<String, Object> map) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.COUNTRY_CREATE_SUCCESSFULLY,
				masterService.countryCreate(map));
	}

	@PostMapping(value = UriConstants.STATE + UriConstants.CREATE)
	public ResponseMessage<Map<String, Object>> stateSave(@RequestBody Map<String, Object> map) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.STATE_CREATE_SUCCESSFULLY,
				masterService.stateCreate(map));
	}

	@PostMapping(value = UriConstants.CITY + UriConstants.CREATE)
	public ResponseMessage<Map<String, Object>> citySave(@RequestBody Map<String, Object> map) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.CITY_CREATE_SUCCESSFULLY,
				masterService.cityCreate(map));
	}

	@GetMapping(value = UriConstants.COUNTRY + UriConstants.ALL)
	public ResponseMessage<List<Map<String, Object>>> getAllCountry() {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.COUNTRY_GET_SUCCESSFULLY,
				masterService.getAllCountry());
	}

	@GetMapping(value = UriConstants.STATE + UriConstants.ALL)
	public ResponseMessage<List<Map<String, Object>>> getAllState(@RequestParam(name = "countryId") Long id) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.STATE_GET_SUCCESSFULLY,
				masterService.getAllState(id));
	}

	@GetMapping(value = UriConstants.CITY + UriConstants.ALL)
	public ResponseMessage<List<Map<String, Object>>> getAllCity(@RequestParam(name = "stateId") Long id) {
		return new ResponseMessage<>(HttpStatus.OK.value(), MsgConstants.CITY_GET_SUCCESSFULLY,
				masterService.getAllCity(id));
	}

}
