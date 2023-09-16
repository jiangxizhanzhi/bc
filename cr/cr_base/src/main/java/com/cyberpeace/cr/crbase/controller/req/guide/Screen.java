package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Screen implements Serializable {

	@ApiModelProperty("开始时间")
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;

	@ApiModelProperty("事件名称")
	private String eventName;

	@ApiModelProperty("攻防类型")
	private Integer category;

	@ApiModelProperty("攻击源")
	private String source;

	@ApiModelProperty("攻击目标")
	private String target;

	@ApiModelProperty("攻击结果")
	private Integer result;

	@ApiModelProperty("归属角色")
	private Integer role;

}
