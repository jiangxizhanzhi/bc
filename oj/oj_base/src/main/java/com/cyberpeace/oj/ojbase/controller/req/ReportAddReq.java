package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ReportAddReq implements Serializable {

	@ApiModelProperty("实验报告内容")
	private String content;

	@ApiModelProperty("课时资源编号")
	@NotBlank
	private String businessId;

	@ApiModelProperty(value = "操作：存草稿=false,提交=true")
	private Boolean operate;

	@ApiModelProperty("报告类型 2=实验报告 3=课堂实验报告")
	@NotNull
	private Integer type;

	public String getContent() {
		return null == content ? "" : content;
	}
}
