package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationTopo implements Serializable {

	/**
	 * 场景信息
	 */
	@ApiModelProperty("场景信息")
	private SceneInfo scene;

	/**
	 * 区域信息
	 */
	@ApiModelProperty("区域信息")
	private Map<String, AreaInfo> areas;

	/**
	 * 节点信息
	 */
	@ApiModelProperty("节点信息")
	private Map<String, ElementInfo> elements;

	/**
	 * 连线信息
	 */
	@ApiModelProperty("连线信息")
	private List<ConnectionInfo> connections;

	/**
	 * 部件信息
	 */
	@ApiModelProperty("部件信息")
	private Map<String, PieceInfo> pieces;

}
