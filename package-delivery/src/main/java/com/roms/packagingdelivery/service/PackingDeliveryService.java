package com.roms.packagingdelivery.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PackingDeliveryService {

	@Autowired
	Environment env;
	
	public double getCostForPackageAndDelivery(String componentType, int quantity) {
		if(quantity<0)
			return 0.0;
		return (getDeliveryCostMap().get(componentType)+
				getPackagingCostMap().get(componentType)+
				getPackagingCostMap().get("Protective Sheath"))*quantity;
				
	}
	
	public Map<String,Double> getDeliveryCostMap()
	{
		Map<String,Double> deliveryMap=new HashMap<>();
		deliveryMap.put("Integral",Double.parseDouble(env.getProperty("deliverycost.integral")));
		deliveryMap.put("Accessory",Double.parseDouble(env.getProperty("deliverycost.accessory")));
		return deliveryMap;
	}
	public Map<String,Double> getPackagingCostMap()
	{
		Map<String,Double> packagingMap=new HashMap<>();
		packagingMap.put("Integral",Double.parseDouble(env.getProperty("packagingcost.integral")));
		packagingMap.put("Accessory",Double.parseDouble(env.getProperty("packagingcost.accessory")));
		packagingMap.put("Protective Sheath",Double.parseDouble(env.getProperty("packagingcost.protective")));
		return packagingMap;
	}

}
