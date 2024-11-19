package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 动态方案
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("situation_dynamic_scheme")
public class DynamicScheme extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 场景id
	 *
	 * @see String
	 */
	private String sceneId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 方案数据
	 *
	 * @see String
	 */
	private String schemeData;

}
