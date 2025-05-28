package com.cyberpeace.space.service.context.index;

import com.cyberpeace.space.constant.SpaceKTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 知识点图表类别
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
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
