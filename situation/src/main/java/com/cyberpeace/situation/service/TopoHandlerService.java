package com.cyberpeace.situation.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.controller.req.FlowLogReq;
import com.cyberpeace.situation.model.CategorieMap;
import com.cyberpeace.situation.model.SceneData;

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
	JSONObject getsceneTopoBySceneId(String sceneId);

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

}
