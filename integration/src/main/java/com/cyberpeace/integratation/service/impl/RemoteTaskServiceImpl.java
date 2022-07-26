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
import com.cyberpeace.integratation.model.gx.TrainResult;
import com.cyberpeace.integratation.model.mp.RemoteUser;
import com.cyberpeace.integratation.service.RemoteTaskService;
import com.google.common.collect.Lists;

@Service
public class RemoteTaskServiceImpl implements RemoteTaskService {

	private static final Logger log = LoggerFactory.getLogger("gx");

	private String REMOTE_TASK_URL;

	private String REMOTE_USER_URL;

	private String REMOTE_TRAIN_URL;

	private String REMOTE_TASK_LIST_URL;

	private String CT_TASK_URL;

	private String CT_TASK_STOP_URL;

	private String REMOTE_TRAIN_REGISTER_URL;

	private String REMOTE_TASK_STOP_URL;

	private String ADMIN_NAME;

	@PostConstruct
	public void init() {
		REMOTE_TASK_URL = "http://" + SystemPropUtil.getProp("TASKS_PUBLISH_HOST") + ":"
				+ SystemPropUtil.getProp("TASKS_PUBLISH_PORT");
		REMOTE_TRAIN_URL = "http://" + SystemPropUtil.getProp("TRAIN_PUBLISH_HOST") + ":"
				+ SystemPropUtil.getProp("TRAIN_PUBLISH_PORT");
		CT_TASK_URL = "http://" + SystemPropUtil.getProp("AD_TASK_SERVER_HOST") + ":"
				+ SystemPropUtil.getProp("AD_TASK_SERVER_PORT");
		REMOTE_USER_URL = REMOTE_TASK_URL + ConfigUtil.getContextProperty("gx.task.users");
		REMOTE_TASK_LIST_URL = REMOTE_TASK_URL + ConfigUtil.getContextProperty("gx.task.list");
		REMOTE_TASK_STOP_URL = REMOTE_TASK_URL + ConfigUtil.getContextProperty("gx.task.stop");
		CT_TASK_STOP_URL = CT_TASK_URL + ConfigUtil.getContextProperty("ct.task.stop");
		REMOTE_TRAIN_REGISTER_URL = REMOTE_TRAIN_URL
				+ ConfigUtil.getContextProperty("gx.train.register");
		ADMIN_NAME = SystemPropUtil.getProp("ADMIN_NAME");
	}

	@Override
	public String getTaskUsers(String taskId, String token) {
		String targetUrl = REMOTE_USER_URL + "?taskId=" + taskId;

		try {

			String data = HttpClientUtil.rawGet(targetUrl, token, String.class);
			log.info(String.format("Try to get task users, token is %s", token));
			return data;
		} catch (Exception e) {
			log.error("Failed to get task users.", e);
			throw new ChatException(ErrorCode.USER_INFO_MISSING);
		}
	}

	@Override
	public Boolean stopExamTask(String taskId, String examId, Double duration) {

		String targetUrl = CT_TASK_STOP_URL + "?task_id=" + taskId + "&exam_id=" + examId
				+ "&duration=" + duration;

		log.info(String.format("Stop task, taskId: %s, examId: %s", taskId, examId));
		try {
			Boolean result = HttpClientUtil.rawGet(targetUrl, Boolean.class);
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
			log.info(String.format("Try to get user tasks, user id is %s, user token is %s", userId,
					token));
			return JsonUtil.toJsonObjectArray(data, JSONObject.class);
		} catch (Exception e) {
			log.error("Failed to get task list.", e);
			return Lists.newArrayList();
		}

	}

	@Override
	public String getAdminToken(List<RemoteUser> remoteUsers) {
		for (RemoteUser remoteUser : remoteUsers) {
			if (remoteUser.getUserName().equals(ADMIN_NAME)) {
				return remoteUser.getThirdToken();
			}
		}
		return null;
	}

	@Override
	public Boolean stopClassroom(String taskId, String userId, String token) {
		String targetUrl = REMOTE_TASK_STOP_URL + "?taskId=" + taskId + "&userId=" + userId;
		HttpClientUtil.rawGet(targetUrl, token, String.class);
		return true;
	}

	@Override
	public Boolean stopClassroomTask(TrainResult trainResult, String token) {
		String targetUrl = REMOTE_TRAIN_REGISTER_URL;
		HttpClientUtil.post(targetUrl, trainResult, String.class, token);
		return true;
	}

}
