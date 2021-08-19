package com.cyberpeace.oj.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.common.model.BaseTable;

import lombok.Data;

/**
 * 
 * @TableName rtks_knowledge
 */
@TableName(value = "rtks_knowledge")
@Data
public class Knowledge extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String systemId;

	private String categoryId;

	private String content;

	private String courses;
}