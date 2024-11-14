package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;

/**
 * 靶标服务
 *
 * @author SN
 * @date 2024/11/14
 */
public interface RemoteTerminalService {

	/**
	 * 获取由id
	 *
	 * @param deviceId 装置id
	 * @return {@code DeviceInfo }
	 */
	DeviceInfo getById(String deviceId);

	/**
	 * 获取全部分类
	 *
	 * @return {@code JSONObject }
	 */
	JSONObject getAllCategorie();

}
