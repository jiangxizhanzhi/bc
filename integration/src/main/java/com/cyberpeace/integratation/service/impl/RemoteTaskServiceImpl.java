package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.service.RemoteTaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteTaskServiceImpl implements RemoteTaskService {

	private String REMOTE_TASK_URL;

	private String REMOTE_USER_URL;

	private String CT_TASK_URL;

	private String CT_TASK_STOP_URL;

	@PostConstruct
	public void init() {
		REMOTE_TASK_URL = "http://" + SystemPropUtil.getProp("TASKS_PUBLISH_HOST") + ":"
				+ SystemPropUtil.getProp("TASKS_PUBLISH_PORT");
		CT_TASK_URL = "http://" + SystemPropUtil.getProp("AD_TASK_SERVER_HOST") + ":"
				+ SystemPropUtil.getProp("AD_TASK_SERVER_PORT");
		REMOTE_USER_URL = REMOTE_TASK_URL + ConfigUtil.getContextProperty("gx.task.users");
		CT_TASK_STOP_URL = CT_TASK_URL + ConfigUtil.getContextProperty("ct.task.stop");
	}

	@Override
	public String getTaskUsers(String taskId, String token) {
		String targetUrl = REMOTE_USER_URL + "?taskId=" + taskId;
		String data = HttpClientUtil.rawGet(targetUrl, token, String.class);
		return data;
	}

	@Override
	public Boolean stopExamTask(String taskId, String examId, Double duration) {
		String targetUrl = CT_TASK_STOP_URL + "?taskId=" + taskId + "&exam_id=" + examId
				+ "&duration=" + duration;
		Boolean result = HttpClientUtil.post(targetUrl, null, Boolean.class);
		return result;
	}

}
