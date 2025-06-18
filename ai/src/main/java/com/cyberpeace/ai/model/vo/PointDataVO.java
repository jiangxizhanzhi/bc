package com.cyberpeace.ai.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("知识/技能点掌握情况")
public class PointDataVO {

	@ApiModelProperty("知识点内容")
	private String content;

	@ApiModelProperty("掌握度")
	private Integer mastery;

}