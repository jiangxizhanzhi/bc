package com.cyberpeace.iov.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("iov_frame")
public class Frame extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 车型
	 */
	private Integer model;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 拓扑描述
	 */
	private String description;

	/**
	 * 创建者
	 */
	private String creator;

	/**
	 * 节点规模
	 */
	private Integer scale;
}
