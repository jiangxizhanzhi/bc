package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseDataRuleStatistics {

	@ApiModelProperty(value = "漏洞库数量")
	private Integer vuln = 0;

	@ApiModelProperty(value = "指纹库")
	private Integer fingerPrint;

	@ApiModelProperty(value = "apt特征")
	private Integer apt;

	@ApiModelProperty(value = "ioc威胁情报")
	private Integer ioc;
}
