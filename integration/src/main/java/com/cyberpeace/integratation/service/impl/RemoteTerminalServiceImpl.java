package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.integratation.service.bo.ImageByUuidsBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.mp.MpDeviceInfo;
import com.cyberpeace.integratation.service.RemoteTerminalService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RemoteTerminalServiceImpl implements RemoteTerminalService {

	private String DEVICE_INFO;

	private String DEVICE_UUIDS_INFO;

	private String DEVICE_IMAGE;

	@Autowired
	private MpEnvService mpEnvService;

	@PostConstruct
	public void init() {
		DEVICE_INFO = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("device.info");
		DEVICE_UUIDS_INFO = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("device.uuids.info");
		DEVICE_IMAGE = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("device.image");
	}

	@Override
	public DeviceInfo getById(String deviceId) {
		String targetUrl = DEVICE_INFO + deviceId;

		MpDeviceInfo mpDeviceInfo = HttpClientUtil.rawGet(targetUrl, MpDeviceInfo.class);

		return toLocalDeviceInfo(mpDeviceInfo);
	}

	private DeviceInfo toLocalDeviceInfo(MpDeviceInfo mpDeviceInfo) {
		DeviceInfo deviceInfo = new DeviceInfo();

		deviceInfo.setName(mpDeviceInfo.getName());
		deviceInfo.setResourceId(mpDeviceInfo.getResourceId());
		deviceInfo.setVulns(mpDeviceInfo.getVulns());

		return deviceInfo;
	}

	@Override
	public JSONObject getAllCategorie() {
		String targetUrl = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("device.categorie") + "?isAll=true";
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	public List<DeviceInfo> getByIds(List<String> ids) {
		String targetUrl = DEVICE_UUIDS_INFO;

		ImageByUuidsBo image = new ImageByUuidsBo();
		image.setUuids(ids);

		List<JSONObject> mpDeviceInfo = HttpClientUtil.post(targetUrl, image, List.class);

		List<DeviceInfo> deviceInfos = new ArrayList<>();
		for (JSONObject jsonObject : mpDeviceInfo) {
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setResourceId(jsonObject.getString("uuid"));
			deviceInfo.setName(jsonObject.getString("name"));
			JSONArray vulns = jsonObject.getJSONArray("vulns");
			if (vulns != null && vulns.size() != 1) {
				deviceInfo.setVulns(JSONObject.parseArray(vulns.toJSONString(),String.class));
			}
			deviceInfos.add(deviceInfo);
		}

		return deviceInfos;
	}

	public String queryImageId(String elementId) {
		String targetUrl = DEVICE_IMAGE + "?element_id=" + elementId;
		JSONObject jsonObject = HttpClientUtil.rawGet(targetUrl, JSONObject.class);
		if (jsonObject != null) {
			return jsonObject.getString("image_uuid");
		}
		return null;
	}
}
