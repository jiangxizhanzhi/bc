package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface RemoteTaskService {

	String getTaskUsers(String taskId, String token);

	Boolean stopExamTask(String taskId, String examId, Double duration);

	List<JSONObject> getTasks(String userId, String softId, String token);

}
