package com.cyberpeace.integratation.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.model.mp.MpTopoStatistic;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteTopoServiceImpl implements RemoteTopoService {

	private String COPY_TOPO_URL;

	private String TOPO_DIGEST_URL;

	private String TOPO_SYNC;

	private String TOPO_RETRIEVE;

	@Autowired
	private MpEnvService mpEnvService;

	@PostConstruct
	public void init() {
		COPY_TOPO_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.copy");
		TOPO_DIGEST_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.topo.terminal.statistic");
		TOPO_SYNC = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.topo.sync");
		TOPO_RETRIEVE = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.topo.retrieve");
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

		JSONObject jsonObject = HttpClientUtil.post(targetUrl, null, JSONObject.class);

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
		topoDigest.setNodeCounts(mpTopoStatistic.getTerminalCount());

		return topoDigest;
	}

	@Override
	public Map<String, TopoDigest> batchGetTopoDigest(List<String> topoIds) {
		String targetUrl = TOPO_DIGEST_URL + "?ids=" + String.join("&ids=", topoIds);
		String data = HttpClientUtil.rawGet(targetUrl, String.class);
		List<MpTopoStatistic> mpTopoStatistics = JsonUtil.toJsonObjectArray(data, MpTopoStatistic.class);

		if (topoIds.size() != mpTopoStatistics.size()) {
			log.error("batchGetTopoDigest expect {}, got {}. [{}]", topoIds.size(), mpTopoStatistics.size(), String.join(", ", topoIds));
			throw new RemoteServiceException();
		}

		return mpTopoStatistics.stream().collect(Collectors.toMap(MpTopoStatistic::getCsceneId, mpTopoStatistic -> {
			TopoDigest topoDigest = new TopoDigest();
			topoDigest.setTopoId(mpTopoStatistic.getCsceneId());
			// todo nodeCount ? terminalCount
			topoDigest.setNodeCounts(mpTopoStatistic.getTerminalCount());
			return topoDigest;
		}));
	}

	@Override
	public String save(JSONObject json) {
		String targetUrl = TOPO_SYNC;
		JSONObject jsonObject = HttpClientUtil.post(targetUrl, json, JSONObject.class);
		return jsonObject.getString("id");
	}

	@Override
	public SceneConfig getById(String topoId) {
		return null;
	}
}
