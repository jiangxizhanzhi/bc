package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import com.cyberpeace.integratation.constant.RealDeviceApplyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.mp.MpDeviceInfo;
import com.cyberpeace.integratation.service.RemoteTerminalService;

@Service
public class RemoteTerminalServiceImpl implements RemoteTerminalService {

	private String DEVICE_INFO;

	private String DEVICE_REAL_APPLY;

	private String DEVICE_REAL_APPLY_RECYCLE;

	@Autowired
	private MpEnvService mpEnvService;

	@PostConstruct
	public void init() {
		DEVICE_INFO = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("device.info");
		DEVICE_REAL_APPLY = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("device.real.apply");
		DEVICE_REAL_APPLY_RECYCLE = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("device.real.apply.recycle");
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

		return deviceInfo;
	}

	@Override
	public JSONObject getAllCategorie() {
		String targetUrl = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("device.categorie") + "?isAll=true";
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	@Override
	public Boolean apply(String deviceId) {
		JSONObject payload = new JSONObject();
		payload.put("operate", RealDeviceApplyType.Apply.ADD.getCode());
		payload.put("uuid", deviceId);

		Boolean result = HttpClientUtil.post(DEVICE_REAL_APPLY, payload, Boolean.class);
		return true;
	}

	@Override
	public Boolean refuse(String deviceId) {
		JSONObject payload = new JSONObject();
		payload.put("operate", RealDeviceApplyType.Apply.DELETE.getCode());
		payload.put("uuid", deviceId);

		Boolean result = HttpClientUtil.post(DEVICE_REAL_APPLY, payload, Boolean.class);
		return true;
	}

	@Override
	public Boolean allot(String deviceId) {
		JSONObject payload = new JSONObject();
		payload.put("operate", RealDeviceApplyType.Allot.ALLOT.getCode());
		payload.put("uuid", deviceId);

		Boolean result = HttpClientUtil.post(DEVICE_REAL_APPLY_RECYCLE, payload, Boolean.class);
		return true;
	}

	@Override
	public Boolean recycle(String deviceId) {
		JSONObject payload = new JSONObject();
		payload.put("operate", RealDeviceApplyType.Allot.RECYCLE.getCode());
		payload.put("uuid", deviceId);

		Boolean result = HttpClientUtil.post(DEVICE_REAL_APPLY_RECYCLE, payload, Boolean.class);
		return true;
	}
}
