package com.cyberpeace.space.service.context.index;

import com.cyberpeace.space.constant.SpaceKTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 知识点图表项目
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class KnowledgeChartItem {

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
	 * 类别
	 *
	 * @see String
	 */
	private String category;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	/**
	 * 元件大小
	 *
	 * @see Integer
	 */
	private Integer symbolSize;

	/**
	 * 值
	 *
	 * @see Integer
	 */
	private Integer value;

	/**
	 * 知识点图表项目
	 *
	 * @param typeEnum 类型 enum
	 * @param parentId 父母id
	 * @param level    层级
	 * @throws
	 * @see SpaceKTypeEnum
	 * @see String
	 * @see int
	 */
	public KnowledgeChartItem(SpaceKTypeEnum typeEnum, String parentId, int level) {
		this.name = typeEnum.getName();
		this.id = typeEnum.getId();
		this.category = parentId;
		this.type = typeEnum.getType();
		this.symbolSize = this.calSymbolSize(level);
		this.value = 0;
	}

	/**
	 * 知识点图表项目
	 *
	 * @param level 层级
	 * @throws
	 * @see int
	 */
	public KnowledgeChartItem(int level){
		this.symbolSize = calSymbolSize(level);
	}

	/**
	 * CAL 符号大小
	 *
	 * @param level 层级
	 * @return int
	 * @throws
	 * @see int
	 * @see int
	 */
	private int calSymbolSize(int level) {
		return (64 >> level) + 4;
	}
}
