package com.cyberpeace.space.service.context.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SpaceStrategyBo {

	private String resourceId;

	@ApiModelProperty(value = "")
	private String name;

	private String userId;

	private String user;

	private String userAvatar;

	@ApiModelProperty(value = "策略内容")
	private String content;

	@ApiModelProperty(value = "操作步骤")
	private String step;

	@ApiModelProperty(value = "配置举例")
	private String example;

	@ApiModelProperty(value = "执行脚本")
	private String script;

	@ApiModelProperty(value = "脚本类型，常量type=44")
	private Integer scriptType;

	@ApiModelProperty(value = "框架id")
	private String frameId;

	@ApiModelProperty(value = "浏览次数")
	private Integer view;

	private Date createTime;

	@ApiModelProperty("策略执行成功判定方法")
	private String method;
}
