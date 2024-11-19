package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中台风险计数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MpRiskCount implements Serializable {

	/**
	 * 高风险
	 *
	 * @see Integer
	 */
	@ApiModelProperty("高风险")
	private Integer highCount;

	/**
	 * 中风险
	 *
	 * @see Integer
	 */
	@ApiModelProperty("中风险")
	private Integer mediumCount;

	/**
	 * 低风险
	 *
	 * @see Integer
	 */
	@ApiModelProperty("低风险")
	private Integer lowCount;

}
