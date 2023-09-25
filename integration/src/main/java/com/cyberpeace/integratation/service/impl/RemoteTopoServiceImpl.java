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
import com.cyberpeace.integratation.service.RemoteTopoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenzhuo
 */
@Slf4j
@Service
public class RemoteTopoServiceImpl implements RemoteTopoService {

	private String COPY_TOPO_URL;

	private String TOPO_DIGEST_URL;

	private String TOPO_SYNC;

	private String TOPO_SYNC_UNLIMITED;

	private String TOPO_RETRIEVE;

	private String TOPO_DELETE;

	private String TOPO_EXPORT;

	private String BATCH_DELETE_TOPO;

	private String ELEMENT_LEAKS_URL;

	private String ALL_LEAKS_URL;

	@Autowired
	private MpEnvService mpEnvService;

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
		TOPO_DELETE = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.delete");
		TOPO_EXPORT = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.export");

		BATCH_DELETE_TOPO = MP_HOST + ConfigUtil.getContextProperty("scene.topo.delete.batch");
		ELEMENT_LEAKS_URL = MP_HOST + ConfigUtil.getContextProperty("scene.topo.element.leak");
		ALL_LEAKS_URL = MP_HOST + ConfigUtil.getContextProperty("scene.topo.all.leak");
	}

	@Override
	public String create(SceneConfig sceneConfig) {
		return IDGeneratorUtil.getID();
		// todo
	}

	@Override
	public JSONObject getTopoById(String topoId) {
		String targetUrl = TOPO_RETRIEVE + "?id=" + topoId;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	@Override
	public String copyTopo(String originTopoId) {
		// 兼容mp在url中传递参数的问题
		String targetUrl = COPY_TOPO_URL + "?id=" + originTopoId;

		JSONObject body = new JSONObject();
		setUserContext(body);

		JSONObject jsonObject = HttpClientUtil.post(targetUrl, body, JSONObject.class);

		String result = jsonObject.getString("id");

		return result;
	}

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

		return topoDigest;
	}

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
					return topoDigest;
				}));
	}

	@Override
	public String save(JSONObject json) {
		// set userId
		setUserContext(json);

		String targetUrl = TOPO_SYNC;
		JSONObject jsonObject = HttpClientUtil.post(targetUrl, json, JSONObject.class);
		return jsonObject.getString("id");
	}

	@Override
	public String saveUnlimited(JSONObject json) {
		String targetUrl = TOPO_SYNC_UNLIMITED;
		JSONObject res = HttpClientUtil.post(targetUrl, json, JSONObject.class);
		return res.getString("id");
	}

	@Override
	public JSONObject sync(JSONObject json) {
		String targetUrl = TOPO_SYNC;
		return HttpClientUtil.post(targetUrl, json, JSONObject.class);
	}

	@Override
	public Boolean deleteById(String topoId) {
		JSONObject body = new JSONObject();
		body.put("id", topoId);
		setUserContext(body);

		String targetUrl = TOPO_DELETE;
		return HttpClientUtil.delete(targetUrl, body, Boolean.class);
	}

	@Override
	public JSONObject exportTopo(String topoId) {
		String targetUrl = TOPO_EXPORT + topoId;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	@Override
	public Boolean equalTopo(JSONObject topo, String topoId) {

		if (null == topo || StringUtils.isBlank(topoId)) {
			log.warn("Topo data is null or topo_id is blank!");
			return CommonConstant.FALSE;
		}

		JSONObject topoById = getTopoById(topoId);
		return JsonUtil.equalJson(topo, topoById);
	}

	@Override
	public Boolean batchDeleteTopo(List<String> topoIds) {

		List<String> notBlankIds = StreamApiUtils.filter(topoIds,
				topoId -> StringUtils.isNotBlank(topoId));
		if (CollectionUtils.isNotEmpty(notBlankIds)) {
			String result = HttpClientUtil.delete(BATCH_DELETE_TOPO, notBlankIds, String.class);
		}
		return Boolean.TRUE;
	}

	@Override
	public JSONObject getElementLeaks(String elementId) {
		String targetUrl = ELEMENT_LEAKS_URL + elementId;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	@Override
	public JSONArray getAllLeak() {
		return HttpClientUtil.rawGet(ALL_LEAKS_URL, JSONArray.class);
	}

	/**
	 * 审计需求：sync、copy、delete topo的接口，增加context.accessUserId
	 * @return 
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
