package com.cyberpeace.integratation.model.mp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
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

	public MpRiskCount() {
		this.highCount = 0;
		this.mediumCount = 0;
		this.lowCount = 0;
	}
}
