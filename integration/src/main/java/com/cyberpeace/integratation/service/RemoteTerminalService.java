package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;

/**
 * 靶标服务
 */
public interface RemoteTerminalService {

	DeviceInfo getById(String deviceId);

	JSONObject getAllCategorie();

	Boolean apply(String deviceId);

	Boolean refuse(String deviceId);

	Boolean allot(String deviceId);

	Boolean recycle(String deviceId);
}
