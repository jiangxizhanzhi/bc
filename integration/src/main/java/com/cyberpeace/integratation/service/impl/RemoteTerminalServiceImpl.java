package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.mp.MpDeviceInfo;
import com.cyberpeace.integratation.service.RemoteTerminalService;

/**
 * 远程终端Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see RemoteTerminalService
 * @since 1.0.0
 */
@Service
public class RemoteTerminalServiceImpl implements RemoteTerminalService {

	/**
	 * 设备信息
	 *
	 * @see String
	 */
	private String DEVICE_INFO;

	/**
	 * 中台环境Service
	 *
	 * @see MpEnvService
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 初始化
	 *
	 * @throws
	 */
	@PostConstruct
	public void init() {
		DEVICE_INFO = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("device.info");
	}

	/**
	 * 获取由id
	 *
	 * @param deviceId 装置id
	 * @return {@code DeviceInfo }
	 * @throws
	 * @see String
	 * @see DeviceInfo
	 */
	@Override
	public DeviceInfo getById(String deviceId) {
		String targetUrl = DEVICE_INFO + deviceId;

		MpDeviceInfo mpDeviceInfo = HttpClientUtil.rawGet(targetUrl, MpDeviceInfo.class);

		return toLocalDeviceInfo(mpDeviceInfo);
	}

	/**
	 * 到本地设备信息
	 *
	 * @param mpDeviceInfo 中台装置信息
	 * @return {@code DeviceInfo }
	 * @throws
	 * @see MpDeviceInfo
	 * @see DeviceInfo
	 */
	private DeviceInfo toLocalDeviceInfo(MpDeviceInfo mpDeviceInfo) {
		DeviceInfo deviceInfo = new DeviceInfo();

		deviceInfo.setName(mpDeviceInfo.getName());
		deviceInfo.setResourceId(mpDeviceInfo.getResourceId());

		return deviceInfo;
	}

	/**
	 * 获取全部分类
	 *
	 * @return {@code JSONObject }
	 * @throws
	 * @see JSONObject
	 */
	@Override
	public JSONObject getAllCategorie() {
		String targetUrl = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("device.categorie") + "?isAll=true";
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}
}
