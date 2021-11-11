package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WebOperationListReq extends PageReqBase {

	@ApiModelProperty("方向")
	private String categoryId;

	@ApiModelProperty("习题集")
	private String practiceSetId;

	@ApiModelProperty("是否解出")
	private Boolean solved;

	@ApiModelProperty("难度")
	private Integer difficulty;

}
