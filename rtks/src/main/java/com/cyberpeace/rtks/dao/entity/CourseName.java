package com.cyberpeace.rtks.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 课程名称
 *
 * @author SN
 * @date 2024/11/07
 */
@TableName(value = "rtks_course_name")
@Data
public class CourseName implements Serializable {

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
	 * 方向
	 */
	private String direction;

	/**
	 * 电子书
	 */
	private Integer eBook;

	/**
	 * 课程数量
	 */
	private String courseNum;

	/**
	 * 方向名称
	 */
	private String directionName;

	/**
	 * 困难
	 */
	private Integer difficulty;

	/**
	 * 课计数
	 */
	private Integer lessonCount;

	/**
	 * 理论计数
	 */
	private Integer theoryCount = 0;

	/**
	 * 锻炼计数
	 */
	private Integer exerciseCount = 0;

	/**
	 * 面试
	 */
	private Boolean interview = false;
}