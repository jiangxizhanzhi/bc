package com.cyberpeace.oj.rtks.dao.entity;

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

	private String resourceId;

	private String relatedId;

	private Integer weight;

	private Integer type;
}
