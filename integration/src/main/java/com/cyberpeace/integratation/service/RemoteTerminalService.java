package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.integratation.service.bo.ClusterImagePageReq;
import com.cyberpeace.integratation.service.bo.TargetListReq;

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

	/**
	 * 查询dynamips 相关的信息
	 * @return
	 */
	JSONObject getDynamicImageInfo();

	/**
	 * 查询靶标对应的探针系统分类
	 * @return
	 */
	JSONObject getAgentSupport();

	/**
	 * 查询常用靶标信息
	 * @return
	 */
	JSONObject getCommonTargets();

	JSONObject targetList(TargetListReq req);

	JSONObject getL2categories(Integer firstTypeId);

	JSONObject snapshotByName(String name);

}
