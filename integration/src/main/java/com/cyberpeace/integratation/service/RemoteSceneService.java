package com.cyberpeace.integratation.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.scene.FlowOpsContext;
import com.cyberpeace.integratation.model.scene.NodeOpsContext;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;

/**
 * 场景事件状态代号： http://10.10.10.11/download/attachments/2461959/222.png?version=1&modificationDate=1607060293000&api=v2
 */
public interface RemoteSceneService {

	/**
	 * 启动前台场景
	 */
	String startScene(SceneStartContext sceneStartContext);

	/**
	 * 启动后台测试场景
	 */
	String testScene(SceneStartContext sceneStartContext);

	Boolean stopScene(SceneOpsContext sceneOpsContext);

	Boolean pauseScene(SceneOpsContext sceneOpsContext);

	JSONObject getById(String sceneId, String userId);

	Boolean unpauseScene(SceneOpsContext sceneOpContext);

	Boolean restartNode(NodeOpsContext nodeOpsContext);

	Boolean recreateNode(NodeOpsContext nodeOpsContext);

	/**
	 * 启动流量
	 */
	Boolean startFlow(FlowOpsContext flowOpsContext);

	/**
	 * 停止流量
	 * @param context 
	 * @return
	 */
	Boolean stopFlow(FlowOpsContext context);

	/**
	 * 获取场景节点会话
	 * @param sceneIds
	 * @return
	 */
	JSONArray getNodeSessions(List<String> sceneIds);

	/**
	 * 获取场景监控会话
	 * @param mpSceneId
	 * @param studentId
	 * @return
	 */
	JSONArray getMonitorSessions(String mpSceneId, String studentId);

}
