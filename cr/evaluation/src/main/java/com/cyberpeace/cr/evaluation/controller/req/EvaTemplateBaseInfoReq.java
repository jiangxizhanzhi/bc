package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 伊娃模板基础信息请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvaTemplateBaseInfoReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "测评靶场模板id", required = false)
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "测评靶场模板名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "测评靶场描述", required = false)
	private String description;

	/**
	 * 图标id
	 */
	@ApiModelProperty(value = "测评靶场Logo文件id", required = true)
	@NotNull
	private String logoId;

	/**
	 * 类别代号
	 */
	@ApiModelProperty(value = "测评靶场类型", required = true)
	@NotNull
	private Integer categoryCode;

	/**
	 * 启用录制
	 */
	@ApiModelProperty(value = "是否录屏", required = true)
	@NotNull
	private Boolean enableRecording;

}