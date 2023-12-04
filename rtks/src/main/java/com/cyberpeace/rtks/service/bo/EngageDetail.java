package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

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

}
