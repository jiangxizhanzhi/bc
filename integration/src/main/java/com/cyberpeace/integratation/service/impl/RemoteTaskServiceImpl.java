package com.cyberpeace.integratation.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.ChatException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.service.RemoteTaskService;
import com.google.common.collect.Lists;

@Service
public class RemoteTaskServiceImpl implements RemoteTaskService {

	private static final Logger log = LoggerFactory.getLogger("gx");

	private String REMOTE_TASK_URL;

	private String REMOTE_USER_URL;

	private String REMOTE_TASK_LIST_URL;

	private String CT_TASK_URL;

	private String CT_TASK_STOP_URL;

	@PostConstruct
	public void init() {
		REMOTE_TASK_URL = "http://" + SystemPropUtil.getProp("TASKS_PUBLISH_HOST") + ":"
				+ SystemPropUtil.getProp("TASKS_PUBLISH_PORT");
		CT_TASK_URL = "http://" + SystemPropUtil.getProp("AD_TASK_SERVER_HOST") + ":"
				+ SystemPropUtil.getProp("AD_TASK_SERVER_PORT");
		REMOTE_USER_URL = REMOTE_TASK_URL + ConfigUtil.getContextProperty("gx.task.users");
		REMOTE_TASK_LIST_URL = REMOTE_TASK_URL + ConfigUtil.getContextProperty("gx.task.list");
		CT_TASK_STOP_URL = CT_TASK_URL + ConfigUtil.getContextProperty("ct.task.stop");
	}

	@Override
	public String getTaskUsers(String taskId, String token) {
		String targetUrl = REMOTE_USER_URL + "?taskId=" + taskId;

		try {
			return HttpClientUtil.rawGet(targetUrl, token, String.class);
		} catch (Exception e) {
			log.error("Failed to get task users.", e);
			throw new ChatException(ErrorCode.USER_INFO_MISSING);
		}
	}

	@Override
	public Boolean stopExamTask(String taskId, String examId, Double duration) {

		String targetUrl = CT_TASK_STOP_URL + "?taskId=" + taskId + "&exam_id=" + examId
				+ "&duration=" + duration;

		log.info(String.format("Stop task, taskId: %s, examId: %s", taskId, examId));
		try {
			Boolean result = HttpClientUtil.post(targetUrl, null, Boolean.class);
			log.info("Stop task successfully.");
			return result;
		} catch (Exception e) {
			log.error("Failed to stop task.", e);
			return false;
		}
	}

	@Override
	public List<JSONObject> getTasks(String userId, String softId, String token) {
		String targetUrl = REMOTE_TASK_LIST_URL + "?userId=" + userId + "&softId=" + softId;

		try {
			String data = HttpClientUtil.rawGet(targetUrl, token, String.class);
			return JsonUtil.toJsonObjectArray(data, JSONObject.class);
		} catch (Exception e) {
			log.error("Failed to get task list.", e);
			return Lists.newArrayList();
		}

	}

}
