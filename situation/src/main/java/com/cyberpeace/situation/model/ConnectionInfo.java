package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 连接信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ConnectionInfo implements Serializable {

	/**
	 * 链路id
	 *
	 * @see String
	 */
	@ApiModelProperty("链路id")
	private String connectionId;

	/**
	 * 源节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("源节点id")
	private String source;

	/**
	 * 目标节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("目标节点id")
	private String target;

	/**
	 * 连线样式
	 *
	 * @see LineStyle
	 */
	@ApiModelProperty("连线样式")
	private LineStyle style;

	/**
	 * 连线动效
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("连线动效")
	private List<String> effect;

	/**
	 * 连线状态
	 *
	 * @see String
	 */
	@ApiModelProperty("连线状态")
	private String status;

}
