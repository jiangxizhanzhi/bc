package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.mp.MpDeviceInfo;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.integratation.service.bo.ClusterImagePageReq;
import com.cyberpeace.integratation.service.bo.TargetListReq;
import com.cyberpeace.integratation.service.bo.mp.RealEquipmentPageRequest;
import com.cyberpeace.integratation.service.bo.mp.VirtualUsbQueryParams;
import com.mysql.cj.xdevapi.JsonArray;
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
	 * 镜像类型
	 */
	private String IMAG_CATEGORY;

	/**
	 * 查询dynamips 相关的信息
	 */
	private String DYNAMIC_IMAGE_INFO;

	private String AGENT_SUPPORT;

	private String COMMON_TARGETS;

	private String TARGET_LIST;

	private String L2_CATEGORIES;

	private String SNAPSHOT_BY_NAME;

	private String CSCENE_LINK_STATUS;

	private String DEVOPS_SNAPSHOT_LIST = "/api/mp/devops/cluster/images/%s/snapshot/list";

	private String DEVOPS_REAL_EQUIPMENT_LIST = "/api/mp/devops/cluster/real-equipment/page";

	private String DEVOPS_USB_LIST = "/api/mp/devops/cluster/virtual_usb/page";

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
		IMAG_CATEGORY = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("device.categorie");
		DYNAMIC_IMAGE_INFO = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("dynamic.image.info");
		AGENT_SUPPORT = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("agent.support");
		COMMON_TARGETS = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("common.targets");
		TARGET_LIST = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("target.list");
		L2_CATEGORIES = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("l2.targets");
		SNAPSHOT_BY_NAME = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("snapshot.by.name");
		CSCENE_LINK_STATUS = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.node.connection.status");
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
		return HttpClientUtil.rawGet(IMAG_CATEGORY, JSONObject.class);
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

	/**
	 * 查询dynamips 相关的信息
	 * @return
	 */
	@Override
	public JSONObject getDynamicImageInfo() {
		return HttpClientUtil.rawGet(DYNAMIC_IMAGE_INFO, JSONObject.class);
	}

	/**
	 * 查询靶标对应的探针系统分类
	 * @return
	 */
	@Override
	public JSONObject getAgentSupport() {
		return HttpClientUtil.rawGet(AGENT_SUPPORT, JSONObject.class);
	}

	@Override
	public JSONObject getCommonTargets() {
		return HttpClientUtil.rawGet(COMMON_TARGETS, JSONObject.class);
	}

	@Override
	public JSONObject targetList(TargetListReq req) {
		String targetUrl = TARGET_LIST + req.toQuery();
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	@Override
	public JSONObject getL2categories(Integer firstTypeId) {
		String targetUrl = L2_CATEGORIES + firstTypeId;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	@Override
	public JSONObject snapshotByName(String name) {
		String targetUrl = SNAPSHOT_BY_NAME + name;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	@Override
	public JSONObject getLinkStatus(String id) {
		String targetUrl = CSCENE_LINK_STATUS + id;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	@Override
	public JsonArray listSnapshot(String uuid) {
		String targetUrl = cyberSpaceMpEnvService.getMpHost() + String.format(DEVOPS_SNAPSHOT_LIST, uuid);
		return HttpClientUtil.rawGet(targetUrl, JsonArray.class);
	}

	@Override
	public JSONObject pageRealEquipment(RealEquipmentPageRequest request) {
		String targetUrl = cyberSpaceMpEnvService.getMpHost() + DEVOPS_REAL_EQUIPMENT_LIST;
		return HttpClientUtil.rawGetByParam(request, targetUrl, JSONObject.class);
	}

	@Override
	public JSONObject pageUsb(VirtualUsbQueryParams queryParams) {
		String targetUrl = cyberSpaceMpEnvService.getMpHost() + DEVOPS_USB_LIST;
		return HttpClientUtil.rawGetByParam(queryParams, targetUrl, JSONObject.class);
	}
}
