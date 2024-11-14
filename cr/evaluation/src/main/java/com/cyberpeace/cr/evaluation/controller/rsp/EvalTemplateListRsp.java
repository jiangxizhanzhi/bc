package com.cyberpeace.cr.evaluation.controller.rsp;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估模板列表响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalTemplateListRsp {

	/**
	 * 测评靶场模板id
	 */
	@ApiModelProperty(value = "测评靶场模板id")
	private String resourceId;

	/**
	 * 测评名称
	 */
	@ApiModelProperty(value = "测评名称")
	@NotNull
	private String name;

	/**
	 * 测评描述
	 */
	@ApiModelProperty(value = "测评描述")
	private String description;

	/**
	 * Logo文件路径
	 */
	@ApiModelProperty(value = "Logo文件路径")
	private String logoPath;

	/**
	 * Logo文件Id
	 */
	@ApiModelProperty(value = "Logo文件Id")
	private String logoId;

	/**
	 * 测评类型
	 */
	@ApiModelProperty(value = "测评类型")
	private Integer categoryCode;

	public void setLogo(String logo) {
		this.logoId = logo;
	}

}
