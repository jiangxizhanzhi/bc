package com.cyberpeace.situation.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.controller.req.FlowLogReq;
import com.cyberpeace.situation.controller.res.ElementStateRes;
import com.cyberpeace.situation.model.CategorieMap;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.ws.WsNodeStatus;

/**
 * 场景拓扑数据处理
 * 主要进行中台拓扑数据的初步转换
 */
public interface TopoHandlerService {

	/**
	 * 获取场景数据
	 * @param sceneId
	 * @param topoId
	 * @return SceneData
	 */
	SceneData getSceneData(String sceneId, String topoId);

	/**
	 * 获取中台场景数据
	 * @param sceneId
	 * @return JSONObject
	 */
	JSONObject getSceneTopoBySceneId(String sceneId);

	/**
	 * 获取靶标类型映射
	 * @param deviceCategorie
	 * @return CategorieMap
	 */
	CategorieMap getCategorieMap(JSONObject deviceCategorie);

	List<JSONObject> getRandomNodes(JSONArray nodes, Integer num);

	/**
	 * 获取流量日志
	 * @param flowLogReq
	 * @return List<JSONObject>
	 */
	List<JSONObject> getFlowLogs(FlowLogReq flowLogReq);

	/**
	 * 获取节点信息
	 * @param sceneId
	 * @param elementId
	 * @return ElementStateRes
	 */
	ElementStateRes getElementState(String sceneId, String elementId);

	/**
	 * 获取节点状态信息
	 * @param sceneId
	 * @return WsNodeStatus
	 */
	WsNodeStatus getNodeStatusMessage(String sceneId);

	/**
	 * 获取场景节点/区域的id列表
	 * @param sceneIds
	 * @return Map<String, List<String>>
	 */
	Map<String, List<String>> getSceneNodeIds(List<String> sceneIds);

}
