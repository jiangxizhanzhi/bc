package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NameValidReq {

	@ApiModelProperty(value = "模板名称")
	private String name;

	@ApiModelProperty(value = "训练类型")
	private Integer type;

	@ApiModelProperty(value = "resource_id，模板编号")
	private String id;

	@ApiModelProperty(value = "训练类型(红队1,蓝队2,日常3)")
	private Integer trainCategory;
}
