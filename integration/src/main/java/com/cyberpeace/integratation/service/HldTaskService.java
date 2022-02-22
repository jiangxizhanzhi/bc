package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;

public interface HldTaskService {

	/**
	 * 新增操作日志
	 */

	JSONObject getTaskInfo(String taskId);

	Boolean updateTaskZt(String taskId, String sceneId);

}
