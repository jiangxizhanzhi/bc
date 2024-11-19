package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 个人统计详情4计数Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PersonalStatsDetail4CountBo implements Serializable {
	/**
	 * 课程总数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程总数")
    private Integer courseTotalCount = 0;

	/**
	 * 完成理论课程数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成理论课程数量")
    private Integer theoryCourseTotalCount = 0;

	/**
	 * 实验课程数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("实验课程数量")
    private Integer expCourseTotalCount = 0;

	/**
	 * 课后练习数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课后练习数量")
    private Integer afterClassPracticeTotalCount = 0;

	/**
	 * 完成课程数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成课程数量")
    private Integer finishCoursesCount = 0;

	/**
	 * 完成理论课程数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成理论课程数量")
    private Integer finishTheoryCount = 0;

	/**
	 * 完成实验课程数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成实验课程数量")
    private Integer finishExpCount = 0;

	/**
	 * 完成课后练习数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成课后练习数量")
    private Integer afterClassPractice = 0;

}
