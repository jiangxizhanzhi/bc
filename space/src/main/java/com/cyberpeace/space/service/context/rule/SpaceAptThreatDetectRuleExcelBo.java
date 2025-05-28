package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间容易威胁检测统治胜过Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Data
public class SpaceAptThreatDetectRuleExcelBo {

	/**
	 * 容易代号
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "APT代号")
	private String aptCode;

	/**
	 * 类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "检测类型")
	private String type;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容简介")
	private String description;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容详情")
	private String content;

	/**
	 * 工具
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "应用工具")
	private String tool;
}
