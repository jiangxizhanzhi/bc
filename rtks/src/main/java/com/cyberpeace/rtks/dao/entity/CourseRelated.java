package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * @TableName rtks_course_related
 */

@Data
@TableName("rtks_course_related")
public class CourseRelated {

	public final static Integer RELATED_TYPE_KNOWLEDGE = 1;

	public final static Integer RELATED_TYPE_SKILL = 2;

	public final static Integer DEFAULT_WEIGHT = 1;

	private String resourceId;

	private String relatedId;

	private Integer weight = DEFAULT_WEIGHT;

	private Integer type;
}
