package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测评模板基础信息请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvaTemplateBaseInfoReq {

	/**
	 * 测评靶场模板id
	 */
	@ApiModelProperty(value = "测评靶场模板id", required = false)
	private String resourceId;

	/**
	 * 测评靶场模板名称
	 */
	@ApiModelProperty(value = "测评靶场模板名称", required = true)
	@NotNull
	private String name;

	/**
	 * 测评靶场描述
	 */
	@ApiModelProperty(value = "测评靶场描述", required = false)
	private String description;

	/**
	 * 测评靶场Logo文件id
	 */
	@ApiModelProperty(value = "测评靶场Logo文件id", required = true)
	@NotNull
	private String logoId;

	/**
	 * 测评靶场类型
	 */
	@ApiModelProperty(value = "测评靶场类型", required = true)
	@NotNull
	private Integer categoryCode;

	/**
	 * 是否录屏
	 */
	@ApiModelProperty(value = "是否录屏", required = true)
	@NotNull
	private Boolean enableRecording;

}