package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NameValidReq {

	@ApiModelProperty(value = "模板名称")
	String name;

	@ApiModelProperty(value = "训练类型")
	Integer type;

	@ApiModelProperty(value = "resource_id，模板编号")
	String id;

}
