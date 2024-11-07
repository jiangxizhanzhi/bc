package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户学习记录
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("学习记录统计数据")
public class RoleStudyBo implements Serializable {

	/**
	 * 总课程数
	 */
	@ApiModelProperty("总课程数")
	private Integer totalCourses;

	/**
	 * 已完成课程
	 */
	@ApiModelProperty("完成课程数")
	private Integer finishedCourses;

	/**
	 * 完成理论课
	 */
	@ApiModelProperty("完成理论课时数量")
	private Integer finishedTheoryLessons;

	/**
	 * 联合国完成理论课
	 */
	@ApiModelProperty("未完成理论课时数量")
	private Integer unFinishedTheoryLessons;

	/**
	 * 已完成练习课
	 */
	@ApiModelProperty("完成实验课时数量")
	private Integer finishedPracticeLessons;

	/**
	 * UN 完成练习课
	 */
	@ApiModelProperty("未完成实验课时数量")
	private Integer unFinishedPracticeLessons;

}
