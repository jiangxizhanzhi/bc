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

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 知识点图表类别
	 *
	 * @param typeEnum 类型 enum
	 * @throws
	 * @see SpaceKTypeEnum
	 */
	public KnowledgeChartCategory(SpaceKTypeEnum typeEnum) {
		this.name = typeEnum.getName();
		this.id = typeEnum.getId();
	}

	/**
	 * 知识点图表类别
	 *
	 * @param name 名称
	 * @param id   id
	 * @throws
	 * @see String
	 * @see String
	 */
	public KnowledgeChartCategory(String name, String id) {
		this.name = name;
		this.id = id;
	}
}
