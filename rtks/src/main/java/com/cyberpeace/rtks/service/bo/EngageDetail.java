package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EngageDetail implements Serializable {

	/**
	 * 阶段名称
	 */
	@ApiModelProperty("阶段名称")
	private String categoryName;

	/**
	 * 技术名称
	 */
	@ApiModelProperty("技术名称")
	private String subCategoryName;

	/**
	 * 子技术id
	 */
	@ApiModelProperty("子技术id")
	private String resourceId;

	/**
	 * 子技术名称
	 */
	@ApiModelProperty("子技术名称")
	private String name;

	/**
	 * 关联模板
	 */
	@ApiModelProperty("关联模板")
	private List<BusinessTemplateSimple> templates;
}
