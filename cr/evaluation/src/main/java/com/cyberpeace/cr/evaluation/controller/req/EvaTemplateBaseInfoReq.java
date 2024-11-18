package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 伊娃模板基础信息请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvaTemplateBaseInfoReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "测评靶场模板id", required = false)
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "测评靶场模板名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "测评靶场描述", required = false)
	private String description;

	/**
	 * 图标id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "测评靶场Logo文件id", required = true)
	@NotNull
	private String logoId;

	/**
	 * 类别代号
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "测评靶场类型", required = true)
	@NotNull
	private Integer categoryCode;

	/**
	 * 启用录制
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否录屏", required = true)
	@NotNull
	private Boolean enableRecording;

}