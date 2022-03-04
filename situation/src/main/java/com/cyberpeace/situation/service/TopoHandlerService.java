package com.cyberpeace.situation.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.model.*;

/**
 * 5.3版本场景拓扑数据到3D拓扑格式转换
 */
public interface TopoHandlerService {

	/**
	 * 获取靶标类型映射
	 * @param deviceCategorie
	 * @return CategorieMap
	 */
	CategorieMap getCategorieMap(JSONObject deviceCategorie);

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
	 * 随机获取节点
	 * @param elements 节点数据
	 * @param num 节点数量
	 * @return List<ElementInfo>
	 */
	List<ElementInfo> getRandomElements(JSONArray elements, Integer num);

	/**
	 * 推送消息至房间
	 * @param data 消息
	 * @param sceneId 场景id
	 * @return 无
	 */
	void pushDataToRoom(JSONObject data, String sceneId);
}
