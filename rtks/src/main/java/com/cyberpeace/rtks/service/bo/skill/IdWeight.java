package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * id权重
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class IdWeight implements Serializable {

	/**
	 * id, 编号
	 *
	 * @see String
	 */
	@ApiModelProperty("id, 编号")
	private String resourceId;

	/**
	 * 权重
	 *
	 * @see Integer
	 */
	@ApiModelProperty("权重")
	private Integer weight;
}
