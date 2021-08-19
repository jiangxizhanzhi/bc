package com.cyberpeace.oj.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.common.model.BaseTable;

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

	private String system;

	private String categaryId;

	private String categary;

	private String content;

	private String courses;

	// private List<SkillCourse> courseList;
}