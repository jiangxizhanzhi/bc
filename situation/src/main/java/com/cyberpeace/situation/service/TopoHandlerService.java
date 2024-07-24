package com.cyberpeace.situation.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.req.FlowLogReq;
import com.cyberpeace.situation.controller.res.ElementStateRes;
import com.cyberpeace.situation.model.CategorieMap;
import com.cyberpeace.situation.model.MpSceneData;
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


	SceneData getSceneDataByCsceneIdAndAreaId(String csceneInstanceId, String csceneId, String areaId);

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
	 * 获取中台场景数据
	 * @param sceneId
	 * @return MpSceneData
	 */
	MpSceneData getMpSceneData(String sceneId);

	/**
	 * 校验场景状态
	 * @param mpSceneData
	 * @return Boolean true-活动 false-非活动
	 */
	Boolean checkSceneStatus(MpSceneData mpSceneData);

	/**
	 * 校验场景是否允许自动运行流量
	 * @param mpSceneData
	 * @return Boolean true-允许 false-不允许
	 */
	Boolean enableFlowAutoRun(MpSceneData mpSceneData);

	/**
	 * 校验场景是否允许自动推送流量事件
	 * @param mpSceneData
	 * @return Boolean true-允许 false-不允许
	 */
	Boolean enableFlowCollect(MpSceneData mpSceneData);

	/**
	 * 自动运行流量
	 * @param mpSceneData
	 * @param sceneId
	 * @return Boolean
	 */
	Boolean flowAutoRun(String sceneId, MpSceneData mpSceneData, Integer interactiveType);

}
