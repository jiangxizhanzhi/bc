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

	@ApiModelProperty(value = "漏洞库数量")
	private Integer vuln = 0;

	@ApiModelProperty(value = "指纹库")
	private Integer fingerPrint = 0;

	@ApiModelProperty(value = "apt特征")
	private Integer apt = 0;

	@ApiModelProperty(value = "ioc威胁情报")
	private Integer ioc = 0;
}
