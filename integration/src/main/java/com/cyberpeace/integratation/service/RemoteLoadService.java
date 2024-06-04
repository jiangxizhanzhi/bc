package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.mp.LoadHistory;

import java.util.List;

public interface RemoteLoadService {

	JSONArray getLoadLog(String sceneId);

	JSONArray getLoadByIds(List<String> ids);

	List<LoadHistory> getLoadHistory(String sceneId);

	void addLoad(JSONObject jsonObject);
}
