package com.cyberpeace.situation.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.controller.req.ChangeTopoReq;
import com.cyberpeace.situation.req.FlowLogReq;
import com.cyberpeace.situation.controller.res.ElementStateRes;
import com.cyberpeace.situation.model.CategorieMap;
import com.cyberpeace.situation.model.MpSceneData;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.ws.WsNodeStatus;

/**
 * 场景拓扑数据处理
 * 主要进行中台拓扑数据的初步转换
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface TopoHandlerService {

	/**
	 * 获取场景数据
	 *
	 * @param sceneId
	 * @param topoId
	 * @return SceneData
	 * @throws
	 * @see String
	 * @see String
	 * @see SceneData
	 */
	SceneData getSceneData(String sceneId, String topoId);


	/**
	 * 获取场景数据由CSenseid和面积id
	 *
	 * @param csceneInstanceId CSense实例id
	 * @param csceneId         CSenseid
	 * @param areaId           面积id
	 * @return {@code SceneData }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see SceneData
	 */
	SceneData getSceneDataByCsceneIdAndAreaId(String csceneInstanceId, String csceneId, String areaId);


	SceneData changeSceneData(ChangeTopoReq changeTopoReq);

	/**
	 * 获取中台场景数据
	 *
	 * @param sceneId
	 * @return JSONObject
	 * @throws
	 * @see String
	 * @see JSONObject
	 */
	JSONObject getSceneTopoBySceneId(String sceneId);

	/**
	 * 获取靶标类型映射
	 *
	 * @param deviceCategorie
	 * @return CategorieMap
	 * @throws
	 * @see JSONObject
	 * @see CategorieMap
	 */
	CategorieMap getCategorieMap(JSONObject deviceCategorie);

	/**
	 * 获取随机节点
	 *
	 * @param nodes 节点
	 * @param num   数量
	 * @return {@code List<JSONObject> }
	 * @throws
	 * @see JSONArray
	 * @see Integer
	 * @see List<JSONObject>
	 */
	List<JSONObject> getRandomNodes(JSONArray nodes, Integer num);

	/**
	 * 获取流量日志
	 *
	 * @param flowLogReq
	 * @return List<JSONObject>
	 * @throws
	 * @see FlowLogReq
	 * @see List<JSONObject>
	 */
	List<JSONObject> getFlowLogs(FlowLogReq flowLogReq);

	/**
	 * 获取节点信息
	 *
	 * @param sceneId
	 * @param elementId
	 * @return ElementStateRes
	 * @throws
	 * @see String
	 * @see String
	 * @see ElementStateRes
	 */
	ElementStateRes getElementState(String sceneId, String elementId);

	/**
	 * 获取节点状态信息
	 *
	 * @param sceneId
	 * @return WsNodeStatus
	 * @throws
	 * @see String
	 * @see WsNodeStatus
	 */
	WsNodeStatus getNodeStatusMessage(String sceneId);

	/**
	 * 获取中台场景数据
	 *
	 * @param sceneId
	 * @return MpSceneData
	 * @throws
	 * @see String
	 * @see MpSceneData
	 */
	MpSceneData getMpSceneData(String sceneId);

	/**
	 * 校验场景状态
	 *
	 * @param mpSceneData
	 * @return Boolean true-活动 false-非活动
	 * @throws
	 * @see MpSceneData
	 * @see Boolean
	 */
	Boolean checkSceneStatus(MpSceneData mpSceneData);

	/**
	 * 校验场景是否允许自动运行流量
	 *
	 * @param mpSceneData
	 * @return Boolean true-允许 false-不允许
	 * @throws
	 * @see MpSceneData
	 * @see Boolean
	 */
	Boolean enableFlowAutoRun(MpSceneData mpSceneData);

	/**
	 * 校验场景是否允许自动推送流量事件
	 *
	 * @param mpSceneData
	 * @return Boolean true-允许 false-不允许
	 * @throws
	 * @see MpSceneData
	 * @see Boolean
	 */
	Boolean enableFlowCollect(MpSceneData mpSceneData);

	/**
	 * 自动运行流量
	 *
	 * @param sceneId
	 * @param mpSceneData
	 * @param interactiveType 交互式类型
	 * @return Boolean
	 * @throws
	 * @see String
	 * @see MpSceneData
	 * @see Integer
	 * @see Boolean
	 */
	Boolean flowAutoRun(String sceneId, MpSceneData mpSceneData, Integer interactiveType);

}
