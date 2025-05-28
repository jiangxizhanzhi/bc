/**
 * 这段代码定义了一个名为 `RemoteTopoService` 的接口，该接口提供了与远程拓扑相关的各种操作。以下是对代码的简要分析： 
 * ### 接口概述 
 * - **包名**：`com.cyberpeace.integratation.service` 
 * - **接口名称**：`RemoteTopoService` 
 * - **功能**：提供与远程拓扑相关的操作和服务 
 * ### 主要方法 
 * 1. **创建拓扑** 
 * - `String create(SceneConfig sceneConfig);` 
 * - 创建一个新的拓扑并返回其标识符。 
 * 2. **获取拓扑** 
 * - `JSONObject getTopoById(String topoId);` 
 * - 根据拓扑ID获取拓扑数据。 
 * - `TopoDigest getTopoDigest(String topoId);` 
 * - 获取拓扑的摘要信息。 
 * - `Map<String, TopoDigest> batchGetTopoDigest(List<String> topoIds);` 
 * - 批量获取多个拓扑的摘要信息。 
 * 3. **保存拓扑** 
 * - `String save(JSONObject json);` 
 * - 保存一个拓扑（新建或修改）。 
 * - `String saveUnlimited(JSONObject json);` 
 * - 无限制地保存一个拓扑。 
 * 4. **同步拓扑** 
 * - `JSONObject sync(JSONObject json);` 
 * - 同步拓扑数据并返回结果。 
 * 5. **删除拓扑** 
 * - `Boolean deleteById(String topoId);` 
 * - 根据拓扑ID删除拓扑。 
 * - `Boolean batchDeleteTopo(List<String> topoIds);` 
 * - 批量删除多个拓扑。 
 * 6. **设置拓扑属性** 
 * - `void setSupportPartialStart(String topoId, Boolean supportPartialStart);` 
 * - 设置拓扑是否支持部分启动。 
 * 7. **获取节点** 
 * - `JSONArray getAllNodes(TopoNodeListReq req);` 
 * - 获取拓扑中的所有节点。 
 * - `JSONObject getNodeStatistics(String topoId);` 
 * - 获取某个拓扑的节点统计数据。 
 * 8. **获取拓扑模板** 
 * - `JSONObject getTopoTemplatePage(TopoTemplatePageReq pageReq);` 
 * - 获取拓扑模板列表。 
 * 9. **上传文件** 
 * - `JSONObject uploadSceneAttachment(MultipartFile file);` 
 * - 上传场景附件。 
 * - `JSONObject uploadIcon(MultipartFile file);` 
 * - 上传图标。 
 * - `JSONObject uploadSceneTemplateSnapshot(MultipartFile file);` 
 * - 上传场景模板快照。 
 * - `JSONObject uploadPrivateKey(MultipartFile file);` 
 * - 上传私钥。 
 * - `JSONObject uploadMib(MultipartFile file);` 
 * - 上传MIB文件。 
 * 10. **查询MIB节点信息** 
 * - `Object getMibNodeInfo(MibInfoQueryRequest queryRequest);` 
 * - 根据查询请求获取MIB节点信息。 
 * ### 总结 
 * `RemoteTopoService` 接口提供了一组丰富的功能来管理远程拓扑，包括创建、获取、保存、删除、设置属性、获取节点信息以及上传和查询相关文件等操作。这些方法使用了多种输入参数类型（如 `SceneConfig`, `TopoNodeListReq`, `TopoTemplatePageReq`, `MultipartFile`, `MibInfoQueryRequest` 等），并且返回类型也多样化（如 `String`, `JSONObject`, `JSONArray`, `Map<String, TopoDigest>` 等）。 
**/
package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.model.scene.TopoNodeListReq;
import com.cyberpeace.integratation.service.bo.TopoTemplatePageReq;
import com.cyberpeace.integratation.service.bo.mp.MibInfoQueryRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 远程拓扑Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface RemoteTopoService {

	/**
	 * 创造
	 *
	 * @param sceneConfig 场景配置
	 * @return {@code String }
	 */
	String create(SceneConfig sceneConfig);

	/**
	 * 获取拓扑由id
	 *
	 * @param topoId 拓扑id
	 * @return {@code JSONObject }
	 */
	JSONObject getTopoById(String topoId);

	/**
	 * 根据现有的topoid，生成一份新的拓扑拷贝数据
	 *
	 * @param originTopoId 原始拓扑id
	 * @return 返回拷贝后的拓扑id
	 */
	String copyTopo(String originTopoId);

	/**
	 * 获取拓扑消化
	 *
	 * @param topoId 拓扑id
	 * @return {@code TopoDigest }
	 */
	TopoDigest getTopoDigest(String topoId);

	/**
	 * 批量获取拓扑消化
	 *
	 * @param topoIds 拓扑id列表
	 * @return {@code Map<String, TopoDigest> }
	 */
	Map<String, TopoDigest> batchGetTopoDigest(List<String> topoIds);

	/**
	 * 保存mp拓扑
	 * 新建和修改用同一个接口
	 *
	 * @param json JSON 格式
	 * @return {@code String }
	 */
	String save(JSONObject json);

	/**
	 * 保存mp拓扑，但不限制节点数量
	 *
	 * @param json 拓扑数据
	 * @return 拓扑id
	 */
	String saveUnlimited(JSONObject json);

	/**
	 * 返回同步之后的拓扑数据
	 *
	 * @param json JSON 格式
	 * @return {@code JSONObject }
	 */
	JSONObject sync(JSONObject json);

	/**
	 * 删除中台拓扑
	 *
	 * @param topoId
	 * @return
	 */
	Boolean deleteById(String topoId);

	/**
	 * topo配置数据导出 数据可直接请求中台保存接口生成新的topo
	 *
	 * @param topoId
	 * @return
	 */
	JSONObject exportTopo(String topoId);

	/**
	 * 比较两个topo是否相同
	 *
	 * @param topo
	 * @param topoId
	 * @return
	 */
	Boolean equalTopo(JSONObject topo, String topoId);

	/**
	 * 批量删除topo
	 *
	 * @param topoIds
	 * @return
	 */
	Boolean batchDeleteTopo(List<String> topoIds);

	/**
	 * 设置topo部分启动
	 *
	 * @param topoId
	 * @param supportPartialStart 支持 Partial Start
	 */
	void setSupportPartialStart(String topoId, Boolean supportPartialStart);

	/**
	 * 获取所有节点
	 *
	 * @param req 请求体
	 * @return {@code JSONArray}
	 */
	JSONArray getAllNodes(TopoNodeListReq req);

	/**
	 * 获取节点统计数据
	 *
	 * @param topoId 拓扑id
	 * @return {@code JSONObject}
	 */
	JSONObject getNodeStatistics(String topoId);

	/**
	 * 获取拓扑模板列表
	 * @param pageReq
	 * @return
	 */
	JSONObject getTopoTemplatePage(TopoTemplatePageReq pageReq);

	/**
	 * 上传场景中的附件
	 *
	 * @param file 文件
	 * @return {@code Object }
	 * @throws
	 * @see MultipartFile
	 * @see Object
	 */
	JSONObject uploadSceneAttachment(MultipartFile file);

	/**
	 * 上传图标
	 *
	 * @param file 文件
	 * @return {@code Object }
	 * @throws
	 * @see MultipartFile
	 * @see Object
	 */
	JSONObject uploadIcon(MultipartFile file);

	/**
	 * 上传场景模板快照
	 *
	 * @param file 文件
	 * @return {@code Object }
	 * @throws
	 * @see MultipartFile
	 * @see JSONObject
	 */
	JSONObject uploadSceneTemplateSnapshot(MultipartFile file);

	/**
	 * 上传私钥
	 *
	 * @param file 文件
	 * @return {@code Object }
	 * @throws
	 * @see MultipartFile
	 * @see JSONObject
	 */
	JSONObject uploadPrivateKey(MultipartFile file);

	/**
	 * SNMP采集 上传mib文件
	 *
	 * @param file 文件
	 * @return {@code Object }
	 * @throws
	 * @see MultipartFile
	 * @see JSONObject
	 */
	JSONObject uploadMib(MultipartFile file);

	/**
	 * 获取mib节点信息
	 *
	 * @param queryRequest 查询请求
	 * @return {@code Object }
	 * @throws
	 * @see MibInfoQueryRequest
	 * @see Object
	 */
	Object getMibNodeInfo(MibInfoQueryRequest queryRequest);
}
