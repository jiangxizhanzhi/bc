package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IdWeight implements Serializable {

	/**
	 * id, 编号
	 */
	@ApiModelProperty("id, 编号")
	private String resourceId;

	/**
	 * 权重
	 */
	@ApiModelProperty("权重")
	private Integer weight;
}
