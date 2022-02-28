package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.model.hld.UpdateTaskZt;
import com.cyberpeace.integratation.service.HldTaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HldTaskServiceImpl implements HldTaskService {

	private String HLD_TASK_HOST;

	private String HLD_TASK_PORT;

	private String HLD_URL;

	private String HLD_GET_TASK_INFO_URL;

	private String HLD_UPDATE_TASK_ZT_URL;

	@PostConstruct
	public void init() {

		HLD_TASK_HOST = SystemPropUtil.getProp("HLD_HOST");

		HLD_TASK_PORT = SystemPropUtil.getProp("HLD_PORT");

		HLD_URL = "http://" + HLD_TASK_HOST + ":" + HLD_TASK_PORT;

		HLD_GET_TASK_INFO_URL = HLD_URL + ConfigUtil.getContextProperty("hld.task.info");

		HLD_UPDATE_TASK_ZT_URL = HLD_URL + ConfigUtil.getContextProperty("hld.task.zt");

	}

	@Override
	public JSONObject getTaskInfo(String taskId) {
		JSONObject jsonObject = null;

		try {
			jsonObject = HttpClientUtil.rawGet(HLD_GET_TASK_INFO_URL + "/" + taskId,
					JSONObject.class);
		} catch (Exception e) {
			log.error("Failed to get task info.", e);
		}

		if (null == jsonObject || jsonObject.isEmpty()) {
			return null;
		}

		return jsonObject;

	}

	@Override
	public Boolean updateTaskZt(String taskId, String sceneId) {

		UpdateTaskZt updateTaskZt = new UpdateTaskZt();
		updateTaskZt.setId(taskId);
		updateTaskZt.setSceneId(sceneId);

		try {
			HttpClientUtil.post(HLD_UPDATE_TASK_ZT_URL, updateTaskZt, JSONObject.class);
			log.info(String.format("Update TaskZt, taskId: %s, sceneId: %s", taskId, sceneId));
		} catch (Exception e) {
			log.error("Failed to update task info.", e);
			return false;
		}

		return true;
	}

}
