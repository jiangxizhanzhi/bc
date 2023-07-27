package com.cyberpeace.situation.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.req.FlowLogReq;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;
import com.cyberpeace.situation.model.ws.WsNodeStatus;

/**
 * 场景态势数据处理
 * 主要进行拓扑数据的分析处理
 */
public interface SituationHandlerService {

	/**
	 * 获取处理后的态势数据
	 * @param sceneData
	 * @return SituationTopo
	 */
	SituationTopo getSituationTopo(SceneData sceneData);

	/**
	 * 获取连线颜色
	 * @return String
	 */
	String getLineColor();

	/**
	 * 获取连线宽度
	 * @return Integer
	 */
	Integer getLineWidth();

	/**
	 * 获取连线类型
	 * @return String
	 */
	String getLineType();

	/**
	 * 获取线型
	 * @return LineStyle
	 */
	LineStyle getLineStyle();

	/**
	 * 获取连线动效
	 * @return List<String>
	 */
	List<String> getLineEffect();

	/**
	 * 获取场景范围
	 * @param xList x坐标列表
	 * @param yList y坐标列表
	 * @return List<PlaneCoordinate>
	 */
	List<PlaneCoordinate> getSceneScope(List<Float> xList, List<Float> yList);

	/**
	 * 获取区域形状
	 * @param area 区域数据
	 * @return String
	 */
	String getAreaShape(JSONObject area);

	/**
	 * 获取区域状态
	 * @param area 区域数据
	 * @return String
	 * @return
	 */
	String getAreaStatus(JSONObject area);

	/**
	 * 获取节点状态
	 * @param element 节点数据
	 * @return String
	 */
	String getElementStatus(JSONObject element);

	/**
	 * 获取节点动作状态
	 * @param element 节点数据
	 * @return String
	 */
	String getElementActionStatus(JSONObject element);

	/**
	 * 获取节点所处区域
	 * @param x x坐标
	 * @param y y坐标
	 * @param areasInfo 区域数据
	 * @return List<String>
	 */
	List<String> getElementAreas(Float x, Float y, Map<String, AreaInfo> areasInfo);

	/**
	 * 获取多节点动态消息
	 * @param flowLogs
	 * @param sceneId
	 * @return List<WsMultiNodeDynamic>
	 */
	List<WsMessage> getAttackEventList(List<JSONObject> flowLogs, String sceneId);

	/**
	 * 随机获取动态消息
	 * @param sceneId 场景id
	 * @param actionCode 动效类型
	 * @return 无
	 */
	WsMultiNodeDynamic getRandomWsMultNodeDynamic(String sceneId, Integer actionCode);

	/**
	 * 推送消息至房间
	 * @param data 消息
	 * @param sceneId 场景id
	 * @return 无
	 */
	<T> void pushDataToRoom(T data, String sceneId);

	/**
	 * 批量推送消息至房间
	 * @param sceneId 场景id
	 * @param datas 消息列表
	 * @param number 每批次推送数量
	 * @param interval 批次间隔时间
	 * @return 无
	 */
	void batchPushDataToRoom(String sceneId, List<WsMessage> datas, Integer number,
			Integer interval);

	/**
	 * 推送链路状态至房间
	 * @param sceneId 场景id
	 * @return WsMessage<WsNodeStatus>
	 */
	WsMessage<WsNodeStatus> pushLinkStatus(String sceneId);

	/**
	 * 推送流量事件至房间
	 * @param flowLogReq
	 * @return List<WsMessage>
	 */
	List<WsMessage> pushFlowEvents(FlowLogReq flowLogReq);

}
