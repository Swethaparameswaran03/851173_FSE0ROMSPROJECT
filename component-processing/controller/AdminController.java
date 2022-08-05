package com.roms.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.roms.admin.entity.ReturnProcess;
import com.roms.returnorder.service.ReturnOrderAdminService;
import com.romsadmin.model.ReturnOrderRequest;
import com.romsadmin.model.UserDetail;

@RestController
public class AdminController {
	final static String URL = "http://localhost:8080/api/validate";
	@Autowired
	private ReturnOrderAdminService returnOrderService;

	@RequestMapping(value = "/api/adminprocess", method = RequestMethod.POST)
	public ResponseEntity<?> handleRequest(@RequestBody ReturnOrderRequest returnOrderRequest,
			@RequestHeader("Authorization") String token) throws Exception {

		if (returnOrderService.validateToken(token, URL) == 200) {
			UserDetail user = returnOrderService.decodeToken(token);
			returnOrderService.saveChanges(user, returnOrderRequest);
			return ResponseEntity.ok(returnOrderService.getOrderData
					(returnOrderService.getUserId(user)));
		} else {
			return ResponseEntity.ok(false);
		}

	}
	@RequestMapping(value = "/api/adminprocess", method = RequestMethod.GET)
	public ResponseEntity<List<ReturnProcess>> handleRequestCheck(@RequestHeader("Authorization") String token) throws Exception {
		
		UserDetail user = returnOrderService.decodeToken(token);
		if ((returnOrderService.validateToken(token, URL) == 200) &&
				(user.getRoles().contains("ROLE_ADMIN")))
				return ResponseEntity.ok(returnOrderService.getOrderData(returnOrderService.
					getUserId(user)));		
		return ResponseEntity.ok(new ArrayList<ReturnProcess>());
	}
}
