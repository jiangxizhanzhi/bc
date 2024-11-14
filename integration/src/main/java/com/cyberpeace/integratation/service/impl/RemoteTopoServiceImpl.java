package com.cyberpeace.integratation.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import lombok.extern.slf4j.Slf4j;

/**
 * 远程拓扑Service实现
 *
 * @author chenzhuo@date 2024/11/14
 */
@Slf4j
@Service
public class RemoteTopoServiceImpl implements RemoteTopoService {

	/**
	 * 复制拓扑网址
	 */
	private String COPY_TOPO_URL;

	/**
	 * 拓扑计数网址
	 */
	private String TOPO_DIGEST_URL;

	/**
	 * 拓扑同步
	 */
	private String TOPO_SYNC;

	/**
	 * 拓扑同步无限制
	 */
	private String TOPO_SYNC_UNLIMITED;

	/**
	 * 拓扑取回
	 */
	private String TOPO_RETRIEVE;
	/**
	 * 拓扑取回由面积为三维
	 */
	private String TOPO_RETRIEVE_BY_AREA_FOR_3D;

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

	/**
	 * 中台环境Service
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		String MP_HOST = mpEnvService.getMpHost();
		COPY_TOPO_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.copy");
		TOPO_DIGEST_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.topo.terminal.statistic");
		TOPO_SYNC = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.sync");
		TOPO_SYNC_UNLIMITED = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.topo.sync.unlimited");
		TOPO_RETRIEVE = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.topo.retrieve");
		TOPO_RETRIEVE_BY_AREA_FOR_3D = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.topo.retrieve_by_area_for_3d");
		TOPO_DELETE = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.delete");
		TOPO_EXPORT = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.export");

		BATCH_DELETE_TOPO = MP_HOST + ConfigUtil.getContextProperty("scene.topo.delete.batch");

		TOPO_NODE_LIST = MP_HOST + ConfigUtil.getContextProperty("topo.node.list");

		TOPO_NODE_STATISTICS = MP_HOST + ConfigUtil.getContextProperty("topo.node.statistics");
	}

	/**
	 * 创建
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
	 * 获取拓扑由id和面积idfor3d
	 *
	 * @param topoId 拓扑id
	 * @param areaId 面积id
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject getTopoByIdAndAreaIdFor3D(String topoId, String areaId) {

		String targetUrl = TOPO_RETRIEVE_BY_AREA_FOR_3D + "?id=" + topoId;
		if (StringUtils.isNotBlank(areaId)) {
			targetUrl = targetUrl + "&area_id=" + areaId;

		}
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



		return mpTopoStatistics.stream()
				.collect(Collectors.toMap(MpTopoStatistic::getCsceneId, mpTopoStatistic -> {
					TopoDigest topoDigest = new TopoDigest();
					topoDigest.setTopoId(mpTopoStatistic.getCsceneId());
					topoDigest.setNodeCounts(mpTopoStatistic.getNodesCount());
					topoDigest.setResourceOccupiedNums(mpTopoStatistic.getResourceOccupiedNums());
					topoDigest.setTotalRam(mpTopoStatistic.getTotalRam());
					topoDigest.setTotalCpu(mpTopoStatistic.getTotalCpu());
					topoDigest.setTotalDisk(mpTopoStatistic.getTotalDisk());
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
	 * 设置支持部分启动
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
	 * 获取全部节点
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
	 */
	@Override
	public void batchDeleteTopo(List<String> topoIds) {

		List<String> notBlankIds = StreamApiUtils.filter(topoIds,
				topoId -> StringUtils.isNotBlank(topoId));
		if (CollectionUtils.isEmpty(notBlankIds)) {
			return ;
		}

		 HttpClientUtil.delete(BATCH_DELETE_TOPO, notBlankIds, String.class);
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
