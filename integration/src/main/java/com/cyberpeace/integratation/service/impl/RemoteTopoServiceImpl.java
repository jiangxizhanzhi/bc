package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.model.mp.MpTopoStatistic;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.model.scene.TopoNodeListReq;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.bo.TopoSupportPartialStartContext;
import com.cyberpeace.integratation.service.bo.TopoTemplatePageReq;
import com.cyberpeace.integratation.service.bo.mp.MibInfoQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 远程拓扑Service实现
 *
 * @author chenzhuo@date 2024/11/08
 */
/**
 * 这段代码是一个Spring服务类 `RemoteTopoServiceImpl`，实现了 `RemoteTopoService` 接口。这个类主要负责与远程拓扑相关的操作。以下是对代码的简要分析： 
 * ### 类定义和依赖注入 
 * - `RemoteTopoServiceImpl` 类使用 `@Service` 注解表明这是一个Spring服务组件。 
 * - 类中通过 `@Autowired` 注入了两个服务 `MpEnvService` 和 `CyberSpaceMpEnvService`。 
 * ### 成员变量 
 * - 类中定义了一系列字符串常量，表示不同的API端点（如复制拓扑、拓扑消化、拓扑同步等）。 
 * - 这些常量在 `init()` 方法中被初始化为具体的URL。 
 * ### 初始化方法 
 * - `@PostConstruct` 注解的方法 `init()` 在类实例化后自动调用，用于初始化URL常量。 
 * ### 主要方法 
 * 1. **创建拓扑** 
 * - `create(SceneConfig sceneConfig)`：目前返回一个生成的ID，但注释提示此方法尚未实现。 
 * 2. **获取拓扑信息** 
 * - `getTopoById(String topoId)`：通过拓扑ID获取拓扑信息。 
 * - `getTopoDigest(String topoId)`：获取某个拓扑的消化信息。 
 * - `batchGetTopoDigest(List<String> topoIds)`：批量获取多个拓扑的消化信息。 
 * 3. **保存拓扑** 
 * - `save(JSONObject json)`：保存拓扑。 
 * - `saveUnlimited(JSONObject json)`：无限制保存拓扑。 
 * 4. **设置支持部分开始** 
 * - `setSupportPartialStart(String topoId, Boolean enableRecording)`：设置拓扑是否支持部分开始。 
 * 5. **获取节点信息** 
 * - `getAllNodes(TopoNodeListReq req)`：获取所有节点信息。 
 * - `getNodeStatistics(String topoId)`：获取某个拓扑的节点统计信息。 
 * 6. **删除拓扑** 
 * - `deleteById(String topoId)`：通过ID删除拓扑。 
 * - `batchDeleteTopo(List<String> topoIds)`：批量删除拓扑。 
 * 7. **导出拓扑** 
 * - `exportTopo(String topoId)`：导出某个拓扑。 
 * 8. **上传文件** 
 * - `uploadSceneAttachment(MultipartFile file)`：上传场景附件。 
 * - `uploadIcon(MultipartFile file)`：上传图标。 
 * - `uploadSceneTemplateSnapshot(MultipartFile file)`：上传场景模板快照。 
 * - `uploadPrivateKey(MultipartFile file)`：上传私钥。 
 * - `uploadMib(MultipartFile file)`：上传MIB文件。 
 * 9. **获取MIB节点信息** 
 * - `getMibNodeInfo(MibInfoQueryRequest queryRequest)`：根据查询请求获取MIB节点信息。 
 * ### 辅助方法 
 * - `setUserContext(JSONObject json)`：设置用户上下文，将当前用户的ID添加到JSON对象中。 
 * ### 总结 
 * 该类提供了丰富的接口来处理与远程拓扑相关的各种操作，包括创建、获取、保存、删除、导出拓扑以及上传文件等。这些方法通过HTTP客户端工具 `HttpClientUtil` 与远程服务进行交互，并处理响应结果。 
**/
@Slf4j
@Service
public class RemoteTopoServiceImpl implements RemoteTopoService {

	/**
	 * 复制拓扑网址
	 */
	private String COPY_TOPO_URL;

	/**
	 * 拓扑消化网址
	 */
	private String TOPO_DIGEST_URL;

	/**
	 * 拓扑同步
	 */
	private String TOPO_SYNC;

	/**
	 * 拓扑同步无限
	 */
	private String TOPO_SYNC_UNLIMITED;

	/**
	 * 拓扑取回
	 */
	private String TOPO_RETRIEVE;

	/**
	 * 拓扑删除
	 */
	private String TOPO_DELETE;

	/**
	 * 拓扑导出
	 */
	private String TOPO_EXPORT;

	/**
	 * 批量删除拓扑
	 */
	private String BATCH_DELETE_TOPO;

	/**
	 * 拓扑节点列表
	 */
	private String TOPO_NODE_LIST;

	/**
	 * 拓扑节点统计学
	 */
	private String TOPO_NODE_STATISTICS;

	private String TOPO_TEMPLATE_LIST;

	private String CSCENE_ATTACHMENT_UPLOAD = "/api/mp/cscene/file/attachment_upload";

	private String CSCENE_ICON_UPLOAD = "/api/mp/cscene/file/icon_upload";

	private String CSCENE_SCENE_SNAPSHOT_UPLOAD = "/api/mp/cscene/file/scene_template_screenshot_upload";

	private String CSCENE_PRIVATEKEY_UPLOAD = "/api/mp/cscene/file/private_key_upload";

	private String CSCENE_MIB_UPLOAD = "/api/mp/cscene/file/mib_import";

	private String MIB_NODE_INFO = "/cscene/protocol/collection/mib_nodeinfo";

	/**
	 * 中台环境Service
	 */
	@Autowired
	private MpEnvService mpEnvService;

	@Autowired
	private CyberSpaceMpEnvService cyberSpaceMpEnvService;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		//String MP_HOST = mpEnvService.getMpHost();
		String CYBER_MP_HOST = cyberSpaceMpEnvService.getMpHost();
		COPY_TOPO_URL = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.copy");
		TOPO_DIGEST_URL = cyberSpaceMpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.topo.terminal.statistic");
		TOPO_SYNC = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.sync");
		TOPO_SYNC_UNLIMITED = cyberSpaceMpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.topo.sync.unlimited");
		TOPO_RETRIEVE = cyberSpaceMpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.topo.retrieve");
		TOPO_DELETE = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.delete");
		TOPO_EXPORT = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.export");

		BATCH_DELETE_TOPO = CYBER_MP_HOST + ConfigUtil.getContextProperty("scene.topo.delete.batch");

		TOPO_NODE_LIST = CYBER_MP_HOST + ConfigUtil.getContextProperty("topo.node.list");

		TOPO_NODE_STATISTICS = CYBER_MP_HOST + ConfigUtil.getContextProperty("topo.node.statistics");

		TOPO_TEMPLATE_LIST = CYBER_MP_HOST + ConfigUtil.getContextProperty("topo.template.list");
	}

	/**
	 * 创造
	 *
	 * @param sceneConfig 场景配置
	 * @return {@code String }
	 */
	@Override
	public String create(SceneConfig sceneConfig) {
		return IDGeneratorUtil.getID();
		// todo
	}

	/**
	 * 获取拓扑由id
	 *
	 * @param topoId 拓扑id
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject getTopoById(String topoId) {
		String targetUrl = TOPO_RETRIEVE + "?id=" + topoId;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	/**
	 * 复制拓扑
	 *
	 * @param originTopoId 起源拓扑id
	 * @return {@code String }
	 */
	@Override
	public String copyTopo(String originTopoId) {
		// 兼容mp在url中传递参数的问题
		String targetUrl = COPY_TOPO_URL + "?id=" + originTopoId;

		JSONObject body = new JSONObject();
		setUserContext(body);

		JSONObject jsonObject = HttpClientUtil.post(targetUrl, body, JSONObject.class);

		return jsonObject.getString("id");
	}

	/**
	 * 获取拓扑消化
	 *
	 * @param topoId 拓扑id
	 * @return {@code TopoDigest }
	 */
	@Override
	public TopoDigest getTopoDigest(String topoId) {
		String targetUrl = TOPO_DIGEST_URL + "?ids=" + topoId;

		String data = HttpClientUtil.rawGet(targetUrl, String.class);
		List<MpTopoStatistic> mpTopoStatistics = JsonUtil.toJsonObjectArray(data,
				MpTopoStatistic.class);

		if (null == mpTopoStatistics || mpTopoStatistics.size() != 1) {
			log.error("getTopoDigest expect 1, topoId=[{}]", topoId);
			throw new RemoteServiceException();
		}

		MpTopoStatistic mpTopoStatistic = mpTopoStatistics.get(0);

		TopoDigest topoDigest = new TopoDigest();
		topoDigest.setTopoId(mpTopoStatistic.getCsceneId());
		topoDigest.setNodeCounts(mpTopoStatistic.getNodesCount());
		topoDigest.setResourceOccupiedNums(mpTopoStatistic.getResourceOccupiedNums());

		return topoDigest;
	}

	/**
	 * 批量获取拓扑消化
	 *
	 * @param topoIds 拓扑id列表
	 * @return {@code Map<String, TopoDigest> }
	 */
	@Override
	public Map<String, TopoDigest> batchGetTopoDigest(List<String> topoIds) {
		String targetUrl = TOPO_DIGEST_URL + "?ids=" + String.join("&ids=", topoIds);
		String data = HttpClientUtil.rawGet(targetUrl, String.class);
		List<MpTopoStatistic> mpTopoStatistics = JsonUtil.toJsonObjectArray(data,
				MpTopoStatistic.class);

		// if (topoIds.size() != mpTopoStatistics.size()) {
		// log.error("batchGetTopoDigest expect {}, got {}. [{}]", topoIds.size(),
		// mpTopoStatistics.size(), String.join(", ", topoIds));
		// throw new RemoteServiceException();
		// }

		return mpTopoStatistics.stream()
				.collect(Collectors.toMap(MpTopoStatistic::getCsceneId, mpTopoStatistic -> {
					TopoDigest topoDigest = new TopoDigest();
					topoDigest.setTopoId(mpTopoStatistic.getCsceneId());
					topoDigest.setNodeCounts(mpTopoStatistic.getNodesCount());
					topoDigest.setResourceOccupiedNums(mpTopoStatistic.getResourceOccupiedNums());
					return topoDigest;
				}));
	}

	/**
	 * 保存
	 *
	 * @param json JSON 格式
	 * @return {@code String }
	 */
	@Override
	public String save(JSONObject json) {
		// set userId
		setUserContext(json);

		String targetUrl = TOPO_SYNC;
		JSONObject jsonObject = HttpClientUtil.post(targetUrl, json, JSONObject.class);
		return jsonObject.getString("id");
	}

	/**
	 * 保存无限
	 *
	 * @param json JSON 格式
	 * @return {@code String }
	 */
	@Override
	public String saveUnlimited(JSONObject json) {
		String targetUrl = TOPO_SYNC_UNLIMITED;
		JSONObject res = HttpClientUtil.post(targetUrl, json, JSONObject.class);
		return res.getString("id");
	}


	/**
	 * 设置支持部分开始
	 *
	 * @param topoId          拓扑id
	 * @param enableRecording 启用录制
	 */
	@Override
	public void setSupportPartialStart(String topoId, Boolean enableRecording) {
		TopoSupportPartialStartContext context = new TopoSupportPartialStartContext(topoId,
				enableRecording);
		sync(JsonUtil.java2JsonObject(context));
	}

	/**
	 * 获取都节点
	 *
	 * @param req 请求体
	 * @return {@code JSONArray }
	 */
	@Override
	public JSONArray getAllNodes(TopoNodeListReq req) {
		String targetUrl = TOPO_NODE_LIST + req.toUrl();
		return HttpClientUtil.rawGet(targetUrl, JSONArray.class);
	}

	/**
	 * 获取节点统计学
	 *
	 * @param topoId 拓扑id
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject getNodeStatistics(String topoId) {
		String targetUrl = TOPO_NODE_STATISTICS + "?id=" + topoId;
		JSONArray rsp = HttpClientUtil.rawGet(targetUrl, JSONArray.class);
		return rsp.getJSONObject(0);
	}

	/**
	 * 同步
	 *
	 * @param json JSON 格式
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject sync(JSONObject json) {
		String targetUrl = TOPO_SYNC;
		return HttpClientUtil.post(targetUrl, json, JSONObject.class);
	}

	/**
	 * 删除由id
	 *
	 * @param topoId 拓扑id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean deleteById(String topoId) {
		JSONObject body = new JSONObject();
		body.put("id", topoId);
		setUserContext(body);

		String targetUrl = TOPO_DELETE;
		return HttpClientUtil.delete(targetUrl, body, Boolean.class);
	}

	/**
	 * 导出拓扑
	 *
	 * @param topoId 拓扑id
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject exportTopo(String topoId) {
		String targetUrl = TOPO_EXPORT + topoId;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	/**
	 * 平等拓扑
	 *
	 * @param topo   拓扑
	 * @param topoId 拓扑id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean equalTopo(JSONObject topo, String topoId) {

		if (null == topo || StringUtils.isBlank(topoId)) {
			log.warn("Topo data is null or topo_id is blank!");
			return CommonConstant.FALSE;
		}

		JSONObject topoById = getTopoById(topoId);
		return JsonUtil.equalJson(topo, topoById);
	}

	/**
	 * 批量删除拓扑
	 *
	 * @param topoIds 拓扑id列表
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean batchDeleteTopo(List<String> topoIds) {

		List<String> notBlankIds = StreamApiUtils.filter(topoIds,
				topoId -> StringUtils.isNotBlank(topoId));
		if (CollectionUtils.isEmpty(notBlankIds)) {
			return Boolean.TRUE;
		}

		String result = HttpClientUtil.delete(BATCH_DELETE_TOPO, notBlankIds, String.class);
		return Boolean.TRUE;
	}

	/**
	 * 获取拓扑模板列表
	 * @param pageReq
	 * @return
	 */
	@Override
	public JSONObject getTopoTemplatePage(TopoTemplatePageReq pageReq) {
		String targetUrl = TOPO_TEMPLATE_LIST + pageReq.toQuery();
		return HttpClientUtil.rawSpecialGet(targetUrl, JSONObject.class);
	}

	@Override
	public JSONObject uploadSceneAttachment(MultipartFile file) {
		String targetUrl = cyberSpaceMpEnvService.getMpHost() + CSCENE_ATTACHMENT_UPLOAD;
		return HttpClientUtil.uploadFile2Mp(targetUrl, file, JSONObject.class);
	}

	@Override
	public JSONObject uploadIcon(MultipartFile file) {
		String targetUrl = cyberSpaceMpEnvService.getMpHost() + CSCENE_ICON_UPLOAD;
		return HttpClientUtil.uploadFile2Mp(targetUrl, file, JSONObject.class);
	}

	@Override
	public JSONObject uploadSceneTemplateSnapshot(MultipartFile file) {
		String targetUrl = cyberSpaceMpEnvService.getMpHost() + CSCENE_SCENE_SNAPSHOT_UPLOAD;
		return HttpClientUtil.uploadFile2Mp(targetUrl, file, JSONObject.class);
	}

	@Override
	public JSONObject uploadPrivateKey(MultipartFile file) {
		String targetUrl = cyberSpaceMpEnvService.getMpHost() + CSCENE_PRIVATEKEY_UPLOAD;
		return HttpClientUtil.uploadFile2Mp(targetUrl, file, JSONObject.class);
	}

	@Override
	public JSONObject uploadMib(MultipartFile file) {
		String targetUrl = cyberSpaceMpEnvService.getMpHost() + CSCENE_MIB_UPLOAD;
		return HttpClientUtil.uploadFile2Mp(targetUrl, file, JSONObject.class);
	}

	@Override
	public Object getMibNodeInfo(MibInfoQueryRequest queryRequest) {
		String targetUrl = cyberSpaceMpEnvService.getMpHost() + MIB_NODE_INFO;
		return HttpClientUtil.rawGetByParam(queryRequest, targetUrl, JSONObject.class);
	}

	/**
	 * 审计需求：sync、copy、delete topo的接口，增加context.accessUserId
	 *
	 * @param json JSON 格式
	 */
	private void setUserContext(JSONObject json) {
		String currentUserId;
		try {
			currentUserId = UserInfoContext.getUserContext().getUserId();
		} catch (NullPointerException e) {
			currentUserId = CommonConstant.ADMIN_ID;
		}

		JSONObject userContext = new JSONObject();
		userContext.put("access_user_id", currentUserId);
		json.put("context", userContext);
	}
}

