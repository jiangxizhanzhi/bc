package com.cyberpeace.assess.controller.res;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程目录配置信息")
public class AssessMaterialExcelDatalRes implements Serializable {


	@ApiModelProperty("类型")
	private Integer type;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("描述")
	private String description;

	@ApiModelProperty("标签")
	private List<String> tag;

	@ApiModelProperty("资源编号")
	private String resourceId;

}
