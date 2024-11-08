package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估模板列表响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvalTemplateListRsp implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "测评靶场模板id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "测评名称")
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "测评描述")
	private String description;

	/**
	 * 图标路径
	 */
	@ApiModelProperty(value = "Logo文件路径")
	private String logoPath;

	/**
	 * 图标id
	 */
	@ApiModelProperty(value = "Logo文件Id")
	private String logoId;

	/**
	 * 类别代号
	 */
	@ApiModelProperty(value = "测评类型")
	private Integer categoryCode;

	public void setLogo(String logo) {
		this.logoId = logo;
	}

}
