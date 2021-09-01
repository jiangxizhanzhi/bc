package com.cyberpeace.integratation.service;

import com.cyberpeace.common.model.scene.DeviceInfo;

/**
 * 靶标服务
 */
public interface RemoteTerminalService {

	DeviceInfo getById(String deviceId);
}
