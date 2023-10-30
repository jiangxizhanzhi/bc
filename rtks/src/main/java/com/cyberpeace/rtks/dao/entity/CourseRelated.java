package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;

import lombok.Data;

/**
 * 
 * @TableName rtks_course_related
 */

@Data
@TableName("rtks_course_related")
public class CourseRelated {

	public static final Integer RELATED_TYPE_KNOWLEDGE = 1;

	public static final Integer RELATED_TYPE_SKILL = 2;

	private String resourceId;

	private String relatedId;

	private Integer weight = CourseInfoBo.DEFAULT_WEIGHT;

	private Integer type;
}
