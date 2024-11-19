package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势拓扑
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SituationTopo implements Serializable {

	/**
	 * 场景信息
	 *
	 * @see SceneInfo
	 */
	@ApiModelProperty("场景信息")
	private SceneInfo scene;

	/**
	 * 区域信息
	 *
	 * @see Map<String, AreaInfo>
	 */
	@ApiModelProperty("区域信息")
	private Map<String, AreaInfo> areas;

	/**
	 * 节点信息
	 *
	 * @see Map<String, ElementInfo>
	 */
	@ApiModelProperty("节点信息")
	private Map<String, ElementInfo> elements;

	/**
	 * 连线信息
	 *
	 * @see List<ConnectionInfo>
	 */
	@ApiModelProperty("连线信息")
	private List<ConnectionInfo> connections;

	/**
	 * 部件信息
	 *
	 * @see Map<String, PieceInfo>
	 */
	@ApiModelProperty("部件信息")
	private Map<String, PieceInfo> pieces;

}
