package com.cyberpeace.cr.tool.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ToolTypeCount implements Serializable {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("数量")
	private Integer count;

	@ApiModelProperty("本月新增")
	private Integer thisAdd;

	@ApiModelProperty("上个月新增")
	private Integer lastAdd;
}
