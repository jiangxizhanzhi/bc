package com.cyberpeace.bs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("bs_behavior_collection")
public class BehaviorCollection extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 设备类型
	 */
	private Integer accessOs;

	/**
	 * 行为列表
	 */
	private String behaviorList;

	/**
	 * 行为数
	 */
	private Integer behaviorCount;

	/**
	 * 步骤数
	 */
	private Integer stepCount;

}
