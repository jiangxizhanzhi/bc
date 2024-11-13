package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 态势动效类别
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
@TableName("situation_effect_category")
public class SituationEffectCategory extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

}
