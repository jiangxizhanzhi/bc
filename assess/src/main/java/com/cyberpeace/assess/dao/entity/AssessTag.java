package com.cyberpeace.assess.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估标签表
 * @TableName assess_tag
 */
@TableName(value = "assess_tag")
@Data
public class AssessTag extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 标签内容
	 */
	private String content;

}