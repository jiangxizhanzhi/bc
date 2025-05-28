package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 基本数据规则统计信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class BaseDataRuleStatistics {

	/**
	 * 漏洞
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "漏洞库数量")
	private Integer vuln = 0;

	/**
	 * 指纹
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "指纹库")
	private Integer fingerPrint = 0;

	/**
	 * 容易
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "apt特征")
	private Integer apt = 0;

	/**
	 * 国际 奥委会
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "ioc威胁情报")
	private Integer ioc = 0;
}
