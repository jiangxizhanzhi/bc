package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneInfo implements Serializable {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	private String id;

	/**
	 * 场景名称
	 *
	 * @see String
	 */
	@ApiModelProperty("场景名称")
	private String name;

	/**
	 * 场景描述
	 *
	 * @see String
	 */
	@ApiModelProperty("场景描述")
	private String desc;

	/**
	 * 场景范围
	 *
	 * @see List<PlaneCoordinate>
	 */
	@ApiModelProperty("场景范围")
	private List<PlaneCoordinate> scope;

}
