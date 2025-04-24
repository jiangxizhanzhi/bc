package com.cyberpeace.space.service.context.index;

import com.cyberpeace.space.constant.SpaceKTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeChartCategory {

	private String name;

	private String resourceId;

	public KnowledgeChartCategory(SpaceKTypeEnum typeEnum) {
		this.name = typeEnum.getName();
		this.resourceId = typeEnum.getId();
	}

	public KnowledgeChartCategory(String name, String resourceId) {
		this.name = name;
		this.resourceId = resourceId;
	}
}
