package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 态势动效映射
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
@TableName("situation_effect_mapping")
public class SituationEffectMapping extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 统治
	 */
	private String rule;

	/**
	 * 源动效
	 */
	private String sourceEffect;

	/**
	 * 目标动效
	 */
	private String targetEffect;

	/**
	 * 重点
	 */
	private Boolean focus;

	/**
	 * 颜色
	 */
	private Integer color;

	/**
	 * 线型
	 */
	private Integer lineType;

	/**
	 * 线宽
	 */
	private Integer lineWidth;
}
