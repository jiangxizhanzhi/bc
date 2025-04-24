package com.cyberpeace.space.service.context.index;

import com.cyberpeace.space.constant.SpaceKTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeChartItem {

	private String name;

	private String resourceId;

	private String category;

	@ApiModelProperty(value = "类型")
	private Integer type;

	private Integer symbolSize;

	private Integer value;

	public KnowledgeChartItem(SpaceKTypeEnum typeEnum, String parentId) {
		this.name = typeEnum.getName();
		this.resourceId = typeEnum.getId();
		this.category = parentId;
		this.type = typeEnum.getType();
		this.symbolSize = 0;
		this.value = 0;
	}

	public KnowledgeChartItem(){
	}
}
