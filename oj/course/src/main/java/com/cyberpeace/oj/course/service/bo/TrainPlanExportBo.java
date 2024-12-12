package com.cyberpeace.oj.course.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 不能增加和删除字段 也不能改变字段顺序
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class TrainPlanExportBo implements Serializable {

	/**
	 * 训练计划名称
	 *
	 * @see String
	 */
	private String trainPLanName;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	private String courseName;

	/**
	 * 课名称
	 *
	 * @see String
	 */
	private String lessonName;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	private Date endTime;

	/**
	 * 组织路径
	 *
	 * @see String
	 */
	private String orgPath;

}
