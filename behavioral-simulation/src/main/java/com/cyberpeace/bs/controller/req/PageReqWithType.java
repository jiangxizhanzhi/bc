package com.cyberpeace.bs.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class PageReqWithType extends PageReqBase {

	@ApiModelProperty(value = "用户类型")
	@Getter
	@Setter
	private Integer type;
}
