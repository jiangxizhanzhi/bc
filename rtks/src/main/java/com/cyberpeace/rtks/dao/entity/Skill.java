package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * @TableName rtks_skill
 */
@TableName(value = "rtks_skill")
@Data
public class Skill extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String systemId;

	private String categoryId;

	private String content;

	private String courses;

	private Integer weight;

}