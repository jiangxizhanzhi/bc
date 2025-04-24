package com.cyberpeace.space.service.context.index;

import com.cyberpeace.space.constant.SpaceKTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeChartCategory {

	private String name;

	private String id;

	public KnowledgeChartCategory(SpaceKTypeEnum typeEnum) {
		this.name = typeEnum.getName();
		this.id = typeEnum.getId();
	}

	public KnowledgeChartCategory(String name, String id) {
		this.name = name;
		this.id = id;
	}
}
