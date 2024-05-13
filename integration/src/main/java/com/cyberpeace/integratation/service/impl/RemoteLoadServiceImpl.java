package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
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

	@PostConstruct
	public void init() {
		GET_LOAD_LOG_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("get.load.history");
		GET_LOAD_LIST_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("get.load.list");
	}

	@Override
	public JSONArray getLoadHistory(String sceneId) {
		String targetUrl = GET_LOAD_LOG_URL + sceneId;
		return HttpClientUtil.rawGet(targetUrl, JSONArray.class);
	}

	@Override
	public JSONArray getLoadByIds(List<String> ids) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ids", ids);
		return HttpClientUtil.post(GET_LOAD_LIST_URL, jsonObject, JSONArray.class);
	}

}
