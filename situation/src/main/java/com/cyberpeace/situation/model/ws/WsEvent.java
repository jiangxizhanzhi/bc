package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.LineStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * WS 事件
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class WsEvent implements Serializable {

	/**
	 * 线型
	 *
	 * @see LineStyle
	 */
	@ApiModelProperty("线型")
	private LineStyle lineStyle;

	/**
	 * 源节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("源节点id")
	private String sourceId;

	/**
	 * 目的节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("目的节点id")
	private String targetId;

	/**
	 * 攻击类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("攻击类型")
	private Integer attackType;

}
