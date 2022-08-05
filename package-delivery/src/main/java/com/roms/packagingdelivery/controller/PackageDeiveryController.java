package com.roms.packagingdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roms.packagingdelivery.service.PackingDeliveryService;

@RestController
public class PackageDeiveryController {
	
	@Autowired
	public PackingDeliveryService packingDeliveryService;
	
	@GetMapping("/compute/packagingdeliverycost")
	public String getPackagingDeliveryCost(@RequestParam(name="componentType") String componentType,@RequestParam("quantity") int quantity) {
		return packingDeliveryService.getCostForPackageAndDelivery(componentType,quantity)+"";
	}

}
