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
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@TableName(value = "rtks_course_name")
@Data
public class CourseName implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 方向
	 *
	 * @see String
	 */
	private String direction;

	/**
	 * 电子书
	 *
	 * @see Integer
	 */
	private Integer eBook;

	/**
	 * 课程数量
	 *
	 * @see String
	 */
	private String courseNum;

	/**
	 * 方向名称
	 *
	 * @see String
	 */
	private String directionName;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 课时计数
	 *
	 * @see Integer
	 */
	private Integer lessonCount;

	/**
	 * 理论计数
	 *
	 * @see Integer
	 */
	private Integer theoryCount = 0;

	/**
	 * 练习计数
	 *
	 * @see Integer
	 */
	private Integer exerciseCount = 0;

	/**
	 * 面试
	 *
	 * @see Boolean
	 */
	private Boolean interview = false;
}