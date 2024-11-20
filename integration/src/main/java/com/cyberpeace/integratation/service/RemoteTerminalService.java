package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.integratation.service.bo.ClusterImagePageReq;
import com.cyberpeace.integratation.service.bo.TargetListReq;
import com.cyberpeace.integratation.service.bo.mp.RealEquipmentPageRequest;
import com.cyberpeace.integratation.service.bo.mp.VirtualUsbQueryParams;

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

	/**
	 * 获取链接状态
	 *
	 * @param id id
	 * @return {@code Object }
	 * @throws
	 * @see String
	 * @see Object
	 */
	JSONObject getLinkStatus(String id);

	/**
	 * 列表快照
	 *
	 * @param uuid uuid
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see JSONObject
	 */
	JSONObject listSnapshot(String uuid);

	/**
	 * 查询实体设备信息，鼠标悬浮数据信息框
	 *
	 * @param request 请求
	 * @return {@code Object }
	 * @throws
	 * @see RealEquipmentPageRequest
	 * @see Object
	 */
	JSONObject pageRealEquipment(RealEquipmentPageRequest request);

	/**
	 * 查询虚拟usb列表
	 *
	 * @param queryParams 查询参数
	 * @return {@code Object }
	 * @throws
	 * @see VirtualUsbQueryParams
	 * @see Object
	 */
	JSONObject pageUsb(VirtualUsbQueryParams queryParams);
}
