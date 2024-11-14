package com.cyberpeace.situation.model;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.dao.entity.SituationConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneData {

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

	/**
	 * 场景名称
	 */
	@ApiModelProperty("场景名称")
	private String sceneName;

	/**
	 * 场景描述
	 */
	@ApiModelProperty("场景描述")
	private String sceneDesc;

	/**
	 * 区域
	 */
	@ApiModelProperty("区域")
	private JSONArray areas;

	/**
	 * 节点数据
	 */
	@ApiModelProperty("节点数据")
	private JSONArray elements;

	/**
	 * 连线
	 */
	@ApiModelProperty("连线")
	private JSONArray connections;

	/**
	 * 部件
	 */
	@ApiModelProperty("部件")
	private JSONArray pieces;

	/**
	 * 场景补充信息
	 */
	@ApiModelProperty("场景补充信息")
	private JSONObject extendElementInfo;

	/**
	 * X坐标列表
	 */
	@ApiModelProperty("X坐标列表")
	private List<Float> xList;

	/**
	 * Y坐标列表
	 */
	@ApiModelProperty("Y坐标列表")
	private List<Float> yList;

	/**
	 * 外部网络列表
	 */
	@ApiModelProperty("外部网络列表")
	private List<String> externalNetwork;

	/**
	 * 端口、节点映射
	 */
	@ApiModelProperty("端口、节点映射")
	private Map<String, String> portElementMap;

	/**
	 * 靶标类型映射
	 */
	@ApiModelProperty("靶标类型映射")
	private CategorieMap categorieMap;

	/**
	 * 态势配置列表
	 */
	@ApiModelProperty("态势配置列表")
	private Map<Integer, Map<String, List<SituationConfig>>> situationConfigMap;

}
