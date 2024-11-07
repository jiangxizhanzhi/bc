package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;

import lombok.Data;

/**
 * 课程相关
 *
 * @author SN
 * @date 2024/11/07
 */

@Data
@TableName("rtks_course_related")
public class CourseRelated {

	/**
	 * 相关类型知识
	 */
	public final static Integer RELATED_TYPE_KNOWLEDGE = 1;

	/**
	 * 相关类型技能
	 */
	public final static Integer RELATED_TYPE_SKILL = 2;

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 相关id
	 */
	private String relatedId;

	/**
	 * 重量
	 */
	private Integer weight = CourseInfoBo.DEFAULT_WEIGHT;

	/**
	 * 类型
	 */
	private Integer type;
}
