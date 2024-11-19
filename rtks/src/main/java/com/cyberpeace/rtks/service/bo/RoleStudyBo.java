package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户学习记录
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("学习记录统计数据")
public class RoleStudyBo implements Serializable {

	/**
	 * 总课程数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("总课程数")
	private Integer totalCourses;

	/**
	 * 完成课程数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成课程数")
	private Integer finishedCourses;

	/**
	 * 完成理论课时数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成理论课时数量")
	private Integer finishedTheoryLessons;

	/**
	 * 未完成理论课时数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("未完成理论课时数量")
	private Integer unFinishedTheoryLessons;

	/**
	 * 完成实验课时数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成实验课时数量")
	private Integer finishedPracticeLessons;

	/**
	 * 未完成实验课时数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("未完成实验课时数量")
	private Integer unFinishedPracticeLessons;

}
