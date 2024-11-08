package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.mp.MpDeviceInfo;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.integratation.service.bo.ClusterImagePageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 远程终端Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class RemoteTerminalServiceImpl implements RemoteTerminalService {

	/**
	 * 设备信息
	 */
	private String DEVICE_INFO;

	/**
	 * 图片列表
	 */
	private String IMAGE_LIST;

	/**
	 * 中台环境Service
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 网络空间中台环境Service
	 */
	@Autowired
	private CyberSpaceMpEnvService cyberSpaceMpEnvService;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		DEVICE_INFO = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("device.info");
		IMAGE_LIST = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("device.list");
	}

	/**
	 * 获取由id
	 *
	 * @param deviceId 装置id
	 * @return {@code DeviceInfo }
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
	 */
	private DeviceInfo toLocalDeviceInfo(MpDeviceInfo mpDeviceInfo) {
		DeviceInfo deviceInfo = new DeviceInfo();

		deviceInfo.setName(mpDeviceInfo.getName());
		deviceInfo.setResourceId(mpDeviceInfo.getResourceId());

		return deviceInfo;
	}

	/**
	 * 获取都分类
	 *
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject getAllCategorie() {
		String targetUrl = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("device.categorie") + "?isAll=true";
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	/**
	 * 分页图像
	 *
	 * @param req 请求体
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject pageImages(ClusterImagePageReq req) {
		String targetUrl = IMAGE_LIST + req.toQuery();
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}
}
