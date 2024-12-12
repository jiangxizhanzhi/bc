package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 中台风险计数
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MpRiskCount implements Serializable {

	/**
	 * 高风险
	 */
	@ApiModelProperty("高风险")
	private Integer highCount;

	/**
	 * 中风险
	 */
	@ApiModelProperty("中风险")
	private Integer mediumCount;

	/**
	 * 低风险
	 */
	@ApiModelProperty("低风险")
	private Integer lowCount;

}
