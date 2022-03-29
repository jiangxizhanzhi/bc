package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import lombok.Data;

@Data
public class MpRiskCount implements Serializable {

	/**
	 * 高风险
	 */
	private Integer highCount;

	/**
	 * 中风险
	 */
	private Integer mediumCount;

	/**
	 * 低风险
	 */
	private Integer lowCount;

}
