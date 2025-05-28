package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 行动框架步骤
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class ActionFrameStep {

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "状态，0未下发，1已完成，2已下发未完成")
	private Integer status = 0;

	@ApiModelProperty(value = "行动战法")
	private List<ActionFrameTactic> tactics;
}
