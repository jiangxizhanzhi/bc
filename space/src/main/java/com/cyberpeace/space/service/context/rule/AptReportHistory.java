package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * APT 报告历史
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class AptReportHistory {

	@ApiModelProperty(value = "发现时间")
	private LocalDate time;

	@ApiModelProperty(value = "报告名称")
	private String name;

	@ApiModelProperty(value = "发布厂商")
	private String firm;
}
