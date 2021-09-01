package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.integratation.service.mp.HttpClient;
import org.springframework.stereotype.Service;

import com.cyberpeace.integratation.service.RemoteTerminalService;

@Service
public class RemoteTerminalServiceImpl implements RemoteTerminalService {
	@Override
	public DeviceInfo getById(String deviceId) {
		HttpClient httpClient = new HttpClient();
		JSONObject jsonObject = httpClient.get("/api/mp/devops/cluster/images/search/" + deviceId);
		return deviceFromJsonObject(jsonObject);
	}

	private DeviceInfo deviceFromJsonObject(JSONObject jsonObject) {
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setName((String) jsonObject.get("name"));
		deviceInfo.setName((String) jsonObject.get("resource_id"));
		return deviceInfo;
	}
}
