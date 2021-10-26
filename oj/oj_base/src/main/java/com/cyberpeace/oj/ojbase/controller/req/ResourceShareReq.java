package com.cyberpeace.oj.ojbase.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("资源授权操作")
public class ResourceShareReq {

	@ApiModelProperty("资源ID")
	private List<String> resourceIds;

	@ApiModelProperty("授权人编号集合")
	private List<String> operationAuthIds;

	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	private Integer operationAuthType;

}
