package com.cyberpeace.situation.model;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.dao.entity.SituationConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class SceneData {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

	/**
	 * 场景名称
	 *
	 * @see String
	 */
	@ApiModelProperty("场景名称")
	private String sceneName;

	/**
	 * 场景描述
	 *
	 * @see String
	 */
	@ApiModelProperty("场景描述")
	private String sceneDesc;

	/**
	 * 区域
	 *
	 * @see JSONArray
	 */
	@ApiModelProperty("区域")
	private JSONArray areas;

	/**
	 * 节点数据
	 *
	 * @see JSONArray
	 */
	@ApiModelProperty("节点数据")
	private JSONArray elements;

	/**
	 * 连线
	 *
	 * @see JSONArray
	 */
	@ApiModelProperty("连线")
	private JSONArray connections;

	/**
	 * 部件
	 *
	 * @see JSONArray
	 */
	@ApiModelProperty("部件")
	private JSONArray pieces;

	/**
	 * 场景补充信息
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("场景补充信息")
	private JSONObject extendElementInfo;

	/**
	 * X坐标列表
	 *
	 * @see List<Float>
	 */
	@ApiModelProperty("X坐标列表")
	private List<Float> xList;

	/**
	 * Y坐标列表
	 *
	 * @see List<Float>
	 */
	@ApiModelProperty("Y坐标列表")
	private List<Float> yList;

	/**
	 * 外部网络列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("外部网络列表")
	private List<String> externalNetwork;

	/**
	 * 端口、节点映射
	 *
	 * @see Map<String, String>
	 */
	@ApiModelProperty("端口、节点映射")
	private Map<String, String> portElementMap;

	/**
	 * 靶标类型映射
	 *
	 * @see CategorieMap
	 */
	@ApiModelProperty("靶标类型映射")
	private CategorieMap categorieMap;

	/**
	 * 态势配置列表
	 *
	 * @see Map<Integer, Map<String, List<SituationConfig>>>
	 */
	@ApiModelProperty("态势配置列表")
	private Map<Integer, Map<String, List<SituationConfig>>> situationConfigMap;

}
