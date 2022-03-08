package com.cyberpeace.integratation.service.impl;

import static com.cyberpeace.common.constant.WebConstant.SCENE_ID_LENGTH;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.scene.*;
import com.cyberpeace.integratation.service.RemoteSceneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteSceneServiceImpl implements RemoteSceneService {

	@Autowired
	private MpEnvService mpEnvService;

	private String SCENE_START_URL;

	private String SCENE_START_TEST_URL;

	private String SCENE_DELETE_URL;

	private String SCENE_CLEAR_URL;

	private String SCENE_RETRIEVE;

	private String SCENE_PAUSE_URL;

	private String SCENE_UNPAUSE_URL;

	private String NODE_RESTART_URL;

	private String NODE_RECREATE_URL;

	private String FLOW_START_URL;

	private String FLOW_STOP_URL;

	private String NODE_SESSION_URL;

	private String FLOW_LOG_URL;

	@PostConstruct
	public void init() {
		SCENE_START_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.start.app");

		SCENE_START_TEST_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.start.test");

		SCENE_DELETE_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.delete");

		SCENE_CLEAR_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.clear");

		SCENE_RETRIEVE = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.retrieve");

		SCENE_PAUSE_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.pause");

		SCENE_UNPAUSE_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.unpause");

		NODE_RESTART_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.restart");

		NODE_RECREATE_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.recreate");

		FLOW_START_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.flow.start");

		FLOW_STOP_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.flow.stop");

		NODE_SESSION_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.session");

		FLOW_LOG_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.flow.log");

	}

	@Override
	public String startScene(SceneStartContext sceneStartContext) {
		return startScene(sceneStartContext, SCENE_START_URL);
	}

	@Override
	public String testScene(SceneStartContext sceneStartContext) {
		return startScene(sceneStartContext, SCENE_START_TEST_URL);
	}

	@Override
	public Boolean stopScene(SceneOpsContext sceneOpsContext) {
		String targetUrl = SCENE_DELETE_URL;

		Boolean result = HttpClientUtil.delete(targetUrl, sceneOpsContext, Boolean.class);

		return result;
	}

	@Override
	public Boolean pauseScene(SceneOpsContext sceneOpsContext) {
		return HttpClientUtil.post(SCENE_PAUSE_URL, sceneOpsContext, Boolean.class);
	}

	@Override
	public JSONObject getById(String sceneId, String userId) {
		String targetUrl = SCENE_RETRIEVE + "?id=" + sceneId + "&accessUserId=" + userId;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);

	}

	@Override
	public Boolean unpauseScene(SceneOpsContext sceneOpContext) {
		return HttpClientUtil.post(SCENE_UNPAUSE_URL, sceneOpContext, Boolean.class);

	}

	@Override
	public Boolean restartNode(NodeOpsContext nodeOpsContext) {
		return HttpClientUtil.post(NODE_RESTART_URL, nodeOpsContext, Boolean.class);
	}

	@Override
	public Boolean recreateNode(NodeOpsContext nodeOpsContext) {
		return HttpClientUtil.post(NODE_RECREATE_URL, nodeOpsContext, Boolean.class);
	}

	@Override
	public Boolean startFlow(FlowOpsContext flowOpsContext) {
		return HttpClientUtil.post(FLOW_START_URL, flowOpsContext, Boolean.class);
	}

	@Override
	public Boolean stopFlow(FlowOpsContext context) {
		return HttpClientUtil.post(FLOW_STOP_URL, context, Boolean.class);
	}

	private String startScene(SceneStartContext context, String url) {
		JSONObject data = HttpClientUtil.post(url, context, JSONObject.class);

		if (null == data) {
			log.error("topoId=[{}] startScene error, response data is null", context.getId());
			throw new RemoteServiceException();
		}

		String sceneId = data.getString("id");
		if (StringUtils.isBlank(sceneId)) {
			log.error("topoId=[{}] startScene error, sceneId is blank", context.getId());
			throw new RemoteServiceException();
		}
		// 校验sceneId长度
		if (sceneId.length() != SCENE_ID_LENGTH) {
			log.error("Illegal sceneId {}", sceneId);
			throw new RemoteServiceException();
		}

		return sceneId;
	}

	@Override
	public JSONArray getNodeSessions(List<String> sceneIds) {

		if (CollectionUtils.isEmpty(sceneIds)) {
			return new JSONArray();
		}

		String sceneIdStr = String.join(CommonConstant.CODE_COMMA, sceneIds);
		return HttpClientUtil.rawGet(String.format(NODE_SESSION_URL, sceneIdStr, "", "", ""),
				JSONArray.class);
	}

	@Override
	public JSONArray getNodeSessions(List<String> sceneIds, List<String> nodeIds,
			List<String> userIds, String type) {
		if (CollectionUtils.isEmpty(sceneIds)) {
			return new JSONArray();
		}

		String sceneIdsStr = String.join(CommonConstant.CODE_COMMA, sceneIds);
		String nodeIdsStr = "";
		String userIdsStr = "";
		String typeStr = StringUtils.isNotBlank(type) ? type : "";
		if (!CollectionUtils.isEmpty(nodeIds)) {
			nodeIdsStr = String.join(CommonConstant.CODE_COMMA, nodeIds);
		}

		if (!CollectionUtils.isEmpty(userIds)) {
			userIdsStr = String.join(CommonConstant.CODE_COMMA, userIds);
		}

		return HttpClientUtil.rawGet(
				String.format(NODE_SESSION_URL, sceneIdsStr, nodeIdsStr, userIdsStr, typeStr),
				JSONArray.class);
	}

	@Override
	public JSONArray getMonitorSessions(String mpSceneId, String studentId) {

		String url = NODE_SESSION_URL + mpSceneId;
		if (StringUtils.isNotBlank(studentId)) {
			url = url + "&userIds=" + studentId;
		}
		return HttpClientUtil.rawGet(url, JSONArray.class);
	}

	@Override
	public JSONObject getFlowLogs(FlowLogsPageContext flowLogsPageContext) {
		return HttpClientUtil.rawGetByParam(flowLogsPageContext, FLOW_LOG_URL, JSONObject.class);
	}
}
