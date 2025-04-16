package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeChartItem {

	private String name;

	private String resourceId;

	private Integer category;

	@ApiModelProperty(value = "类型")
	private Integer type;

	private Integer symbolSize;

	private Integer value;
}
