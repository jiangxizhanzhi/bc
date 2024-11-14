package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 报告新增请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ReportAddReq implements Serializable {

	/**
	 * 实验报告内容
	 */
	@ApiModelProperty("实验报告内容")
	private String content;

	/**
	 * 课时资源编号
	 */
	@ApiModelProperty("课时资源编号")
	@NotBlank
	private String businessId;

	/**
	 * 操作：存草稿=false,提交=true
	 */
	@ApiModelProperty(value = "操作：存草稿=false,提交=true")
	private Boolean operate;

	public String getContent() {
		return null == content ? "" : content;
	}
}
