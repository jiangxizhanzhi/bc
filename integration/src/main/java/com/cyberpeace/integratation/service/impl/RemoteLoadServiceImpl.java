package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.mp.LoadHistory;
import com.cyberpeace.integratation.service.RemoteLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class RemoteLoadServiceImpl implements RemoteLoadService {

	@Autowired
	private MpEnvService mpEnvService;

	private String GET_LOAD_LOG_URL;

	private String GET_LOAD_LIST_URL;

	private String GET_LOAD_HISTORY_URL;

	private String ADD_LOAD_URL;

	@PostConstruct
	public void init() {
		GET_LOAD_LOG_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("get.load.log");
		GET_LOAD_LIST_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("get.load.list");
		GET_LOAD_HISTORY_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("get.load.history");
		ADD_LOAD_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("add.load.url");
	}

	@Override
	public JSONArray getLoadLog(String sceneId) {
		String targetUrl = GET_LOAD_LOG_URL + sceneId;
		return HttpClientUtil.rawGet(targetUrl, JSONArray.class);
	}

	@Override
	public JSONArray getLoadByIds(List<String> ids) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ids", ids);
		return HttpClientUtil.post(GET_LOAD_LIST_URL, jsonObject, JSONArray.class);
	}

	@Override
	public List<LoadHistory> getLoadHistory(String sceneId) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("scene_id", sceneId);
		JSONObject history = HttpClientUtil.post(GET_LOAD_HISTORY_URL, jsonObject, JSONObject.class);
		return JsonUtil.toJsonObjectArray(history.getString("list"), LoadHistory.class);
	}

	@Override
	public void addLoad(JSONObject jsonObject) {
		HttpClientUtil.post(ADD_LOAD_URL, jsonObject, String.class);
	}

}
