package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.integratation.service.bo.ClusterImagePageReq;

/**
 * 靶标服务
 */
public interface RemoteTerminalService {

	DeviceInfo getById(String deviceId);

	JSONObject getAllCategorie();

	JSONObject pageImages(ClusterImagePageReq req);

}
