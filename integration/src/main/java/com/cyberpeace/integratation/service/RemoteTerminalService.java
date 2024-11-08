package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.integratation.service.bo.ClusterImagePageReq;

/**
 * 靶标服务
 *
 * @author SN
 * @date 2024/11/08
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
	 * 获取都分类
	 *
	 * @return {@code JSONObject }
	 */
	JSONObject getAllCategorie();

	/**
	 * 分页图像
	 *
	 * @param req 请求体
	 * @return {@code JSONObject }
	 */
	JSONObject pageImages(ClusterImagePageReq req);

}
