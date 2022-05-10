package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EngageDetail implements Serializable {

	@ApiModelProperty("阶段名称")
	private String categoryName;

	@ApiModelProperty("技术名称")
	private String subCategoryName;

	@ApiModelProperty("子技术id")
	private String resourceId;

	@ApiModelProperty("子技术名称")
	private String name;

	@ApiModelProperty("关联模板")
	private ListRsp<BusinessTemplateSimple> templates;
}
