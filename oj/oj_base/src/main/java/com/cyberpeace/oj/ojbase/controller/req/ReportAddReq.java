package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ReportAddReq implements Serializable {

	@ApiModelProperty("实验报告内容")
	private String content;

	@ApiModelProperty("资源编号 课程 课时 习题")
	@NotBlank
	private String businessId;

	@ApiModelProperty(value = "操作：存草稿=false,提交=true")
	@NotBlank
	private Boolean operate;

	public String getContent() {
		return null == content ? "" : content;
	}
}
