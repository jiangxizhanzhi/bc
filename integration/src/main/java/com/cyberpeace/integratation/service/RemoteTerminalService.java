package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.integratation.service.bo.ClusterImagePageReq;
import com.cyberpeace.integratation.service.bo.TargetListReq;
import com.cyberpeace.integratation.service.bo.mp.RealEquipmentPageRequest;
import com.cyberpeace.integratation.service.bo.mp.VirtualUsbQueryParams;
import com.mysql.cj.xdevapi.JsonArray;

/**
 * 靶标服务
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `RemoteTerminalService` 的接口，该接口包含多个方法，用于处理与远程终端相关的操作。以下是对其主要功能和结构的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.integratation.service` 
 * - **导入的类和库**： 
 * - `com.alibaba.fastjson.JSONObject`: 用于处理JSON对象。 
 * - `com.cyberpeace.common.model.scene.DeviceInfo`: 设备信息模型。 
 * - 自定义的业务对象（BO）：`ClusterImagePageReq`, `TargetListReq`, `RealEquipmentPageRequest`, `VirtualUsbQueryParams`。 
 * - `com.mysql.cj.xdevapi.JsonArray`: 用于处理JSON数组。 
 * ### 接口定义 
 * - **接口名称**：`RemoteTerminalService` 
 * - **作者**：`SN` 
 * - **日期**：`2024/11/08` 
 * ### 方法摘要 
 * 1. **获取设备信息** 
 * ```java 
 * DeviceInfo getById(String deviceId); 
 * ``` 
 * - 根据设备ID获取设备信息。 
 * 2. **获取所有类别** 
 * ```java 
 * JSONObject getAllCategorie(); 
 * ``` 
 * - 获取所有的类别信息，并以JSON对象形式返回。 
 * 3. **分页图像** 
 * ```java 
 * JSONObject pageImages(ClusterImagePageReq req); 
 * ``` 
 * - 根据请求参数进行图像分页查询，并返回结果。 
 * 4. **查询Dynamips相关信息** 
 * ```java 
 * JSONObject getDynamicImageInfo(); 
 * ``` 
 * - 获取Dynamips的相关信息。 
 * 5. **查询靶标支持的探针系统分类** 
 * ```java 
 * JSONObject getAgentSupport(); 
 * ``` 
 * - 获取靶标支持的探针系统分类信息。 
 * 6. **查询常用靶标信息** 
 * ```java 
 * JSONObject getCommonTargets(); 
 * ``` 
 * - 获取常用的靶标信息。 
 * 7. **查询靶标列表** 
 * ```java 
 * JSONObject targetList(TargetListReq req); 
 * ``` 
 * - 根据请求参数查询靶标列表。 
 * 8. **查询二级类别** 
 * ```java 
 * JSONObject getL2categories(Integer firstTypeId); 
 * ``` 
 * - 根据一级类别ID查询二级类别信息。 
 * 9. **根据名称获取快照** 
 * ```java 
 * JSONObject snapshotByName(String name); 
 * ``` 
 * - 根据名称获取快照信息。 
 * 10. **获取链接状态** 
 * ```java 
 * JSONObject getLinkStatus(String id); 
 * ``` 
 * - 根据ID获取链接状态。 
 * 11. **列出快照** 
 * ```java 
 * JsonArray listSnapshot(String uuid); 
 * ``` 
 * - 根据UUID列出快照信息。 
 * 12. **查询实体设备信息** 
 * ```java 
 * JSONObject pageRealEquipment(RealEquipmentPageRequest request); 
 * ``` 
 * - 根据请求参数查询实体设备信息。 
 * 13. **查询虚拟USB列表** 
 * ```java 
 * JSONObject pageUsb(VirtualUsbQueryParams queryParams); 
 * ``` 
 * - 根据查询参数查询虚拟USB列表。 
 * ### 总结 
 * `RemoteTerminalService` 接口提供了多种方法来处理与远程终端相关的操作，包括设备信息管理、图像分页、类别查询、探针系统分类查询等。这些方法通过不同的请求参数来实现特定的功能，并返回相应的结果。 
**/
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
	JsonArray listSnapshot(String uuid);

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

