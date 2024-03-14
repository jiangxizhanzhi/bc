package com.cyberpeace.cr.evaluation.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TechniqueWarfare implements Serializable {

	@ApiModelProperty("颜色")
	private Integer color;

	@ApiModelProperty("模板样式")
	private Integer templateCategory;

	@ApiModelProperty("工具")
	private List<MissionData> tools;
}
