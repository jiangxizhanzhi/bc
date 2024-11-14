package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneInfo implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	private String id;

	/**
	 * 场景名称
	 */
	@ApiModelProperty("场景名称")
	private String name;

	/**
	 * 场景描述
	 */
	@ApiModelProperty("场景描述")
	private String desc;

	/**
	 * 场景范围
	 */
	@ApiModelProperty("场景范围")
	private List<PlaneCoordinate> scope;

}
