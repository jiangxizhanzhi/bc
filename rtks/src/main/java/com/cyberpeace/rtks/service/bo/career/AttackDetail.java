package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttackDetail implements Serializable {

	/**
	 * 阶段id
	 */
	@ApiModelProperty("阶段id")
	private String categoryId;

	/**
	 * 阶段名称
	 */
	@ApiModelProperty("阶段名称")
	private String categoryName;

	/**
	 * 技术id
	 */
	@ApiModelProperty("技术id")
	private String resourceId;

	/**
	 * 技术名称
	 */
	@ApiModelProperty("技术名称")
	private String name;

	/**
	 * 关联模板
	 */
	@ApiModelProperty("关联模板")
	private List<BusinessTemplateSimple> templates;
}
