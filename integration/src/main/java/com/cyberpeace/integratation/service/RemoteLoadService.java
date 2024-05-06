package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

public interface RemoteLoadService {

	JSONArray getLoadHistory(String sceneId);

	JSONArray getLoadByIds(List<String> ids);
}
