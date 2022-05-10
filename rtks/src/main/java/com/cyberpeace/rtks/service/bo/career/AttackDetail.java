package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttackDetail implements Serializable {

	@ApiModelProperty("阶段id")
	private String categoryId;

	@ApiModelProperty("阶段名称")
	private String categoryName;

	@ApiModelProperty("技术id")
	private String resourceId;

	@ApiModelProperty("技术名称")
	private String name;

	@ApiModelProperty("关联模板")
	private List<BusinessTemplateSimple> templates;
}
