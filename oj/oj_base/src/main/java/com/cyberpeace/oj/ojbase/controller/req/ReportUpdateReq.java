package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReportUpdateReq extends ReportAddReq{

	@ApiModelProperty("实验报告资源编号")
	@NotBlank
	private String resourceId;

}
