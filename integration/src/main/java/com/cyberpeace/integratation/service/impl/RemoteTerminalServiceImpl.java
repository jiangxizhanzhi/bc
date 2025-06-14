package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONArray;
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
/**
 * 这段代码定义了一个名为 `RemoteTerminalServiceImpl` 的 Spring 服务类，实现了 `RemoteTerminalService` 接口。以下是该代码的主要组成部分和功能分析： 
 * ### 类定义和注释 
 * - **类名**：`RemoteTerminalServiceImpl` 
 * - **包名**：`com.cyberpeace.integratation.service.impl` 
 * - **继承接口**：`RemoteTerminalService` 
 * ### 成员变量 
 * - 定义了一系列字符串变量（如 `DEVICE_INFO`, `IMAGE_LIST` 等），用于存储不同请求路径的前缀。 
 * - 使用 `@Autowired` 注解注入了两个依赖服务：`MpEnvService` 和 `CyberSpaceMpEnvService`。 
 * ### 初始化方法 
 * - `@PostConstruct` 注解的方法 `init()` 在 Bean 初始化之后被调用，用于初始化上述字符串变量，将其设置为具体的 URL 地址。 
 * ### 主要方法 
 * 1. **获取设备信息** 
 * - `getById(String deviceId)`：根据设备 ID 获取设备信息。 
 * - `toLocalDeviceInfo(MpDeviceInfo mpDeviceInfo)`：将中台返回的设备信息转换成本地使用的 `DeviceInfo` 对象。 
 * 2. **获取其他数据** 
 * - `getAllCategorie()`：获取所有分类信息。 
 * - `pageImages(ClusterImagePageReq req)`：分页获取镜像信息。 
 * - `getDynamicImageInfo()`：获取 Dynamips 相关信息。 
 * - `getAgentSupport()`：获取代理支持信息。 
 * - `getCommonTargets()`：获取通用目标信息。 
 * - `targetList(TargetListReq req)`：根据请求参数获取目标列表。 
 * - `getL2categories(Integer firstTypeId)`：获取 L2 分类信息。 
 * - `snapshotByName(String name)`：根据名称获取快照信息。 
 * - `getLinkStatus(String id)`：获取链接状态信息。 
 * 3. **特殊请求处理** 
 * - `listSnapshot(String uuid)`：获取指定 UUID 的快照列表。 
 * - `pageRealEquipment(RealEquipmentPageRequest request)`：分页获取真实设备信息。 
 * - `pageUsb(VirtualUsbQueryParams queryParams)`：分页获取虚拟 USB 信息。 
 * ### 总结 
 * - 该服务类主要负责与远程终端进行交互，通过 HTTP GET 请求从远程服务器获取设备信息、分类信息、快照信息等，并将这些信息封装成相应的对象返回给调用者。 
 * - 通过依赖注入的方式，该类可以方便地访问其他服务，如 `MpEnvService` 和 `CyberSpaceMpEnvService`，以获取必要的配置信息。 
 * - 整个类的设计遵循了良好的面向对象原则，通过接口和依赖注入提高了代码的可维护性和扩展性。 
**/
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
		JSONObject jsonObject =  HttpClientUtil.rawSpecialGet(targetUrl, JSONObject.class);
		// 修改jsonObject里面的数据， 数据结构{list: [{name: xxxx}, logo: xxxx], total}
		// 需要判断logo是否存在/api/mp/file/前缀，没有的话，进行添加
		if (jsonObject.containsKey("list")) {
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject1 = jsonArray.getJSONObject(i);
				if (jsonObject1.containsKey("logo")) {
					String logo = jsonObject1.getString("logo");
					if (!logo.startsWith("/api/mp/file/")) {
						jsonObject1.put("logo", "/api/mp/file/" + logo);
					}
				}
			}
		}
		return jsonObject;
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
		return HttpClientUtil.rawSpecialGet(targetUrl, JSONObject.class);
	}

	@Override
	public JSONObject getL2categories(Integer firstTypeId) {
		String targetUrl = L2_CATEGORIES + firstTypeId;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	@Override
	public JSONObject snapshotByName(String name) {
		String targetUrl = SNAPSHOT_BY_NAME + name;
		return HttpClientUtil.rawSpecialGet(targetUrl, JSONObject.class);
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

