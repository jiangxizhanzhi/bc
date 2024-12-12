package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 报告新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class ReportAddReq implements Serializable {

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("实验报告内容")
	private String content;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时资源编号")
	@NotBlank
	private String businessId;

	/**
	 * 操作
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "操作：存草稿=false,提交=true")
	private Boolean operate;

	public String getContent() {
		return null == content ? "" : content;
	}
}
