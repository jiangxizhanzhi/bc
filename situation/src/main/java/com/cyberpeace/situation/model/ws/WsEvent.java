package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.LineStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsEvent implements Serializable {

	/**
	 * 线型
	 */
	@ApiModelProperty("线型")
	private LineStyle lineStyle;

	/**
	 * 源节点id
	 */
	@ApiModelProperty("源节点id")
	private String sourceId;

	/**
	 * 目的节点id
	 */
	@ApiModelProperty("目的节点id")
	private String targetId;

	/**
	 * 攻击类型
	 */
	@ApiModelProperty("攻击类型")
	private Integer attackType;

}
