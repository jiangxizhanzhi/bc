package com.cyberpeace.rtks.service.bo.career;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

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

}
