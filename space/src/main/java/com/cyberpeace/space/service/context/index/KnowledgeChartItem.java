package com.cyberpeace.space.service.context.index;

import com.cyberpeace.space.constant.SpaceKTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeChartItem {

	private String name;

	private String id;

	private String category;

	@ApiModelProperty(value = "类型")
	private Integer type;

	private Integer symbolSize;

	private Integer value;

	public KnowledgeChartItem(SpaceKTypeEnum typeEnum, String parentId, int level) {
		this.name = typeEnum.getName();
		this.id = typeEnum.getId();
		this.category = parentId;
		this.type = typeEnum.getType();
		this.symbolSize = this.calSymbolSize(level);
		this.value = 0;
	}

	public KnowledgeChartItem(int level){
		this.symbolSize = calSymbolSize(level);
	}

	private int calSymbolSize(int level) {
		return (64 >> level) + 4;
	}
}
