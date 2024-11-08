package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 不能增加和删除字段 也不能改变字段顺序
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class TrainPlanExportBo implements Serializable {

	/**
	 * 训练计划名称
	 */
	private String trainPLanName;

	/**
	 * 课程名称
	 */
	private String courseName;

	/**
	 * 课名称
	 */
	private String lessonName;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 组织路径
	 */
	private String orgPath;

}
