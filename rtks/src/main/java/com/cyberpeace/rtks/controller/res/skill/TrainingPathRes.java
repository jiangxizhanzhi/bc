package com.cyberpeace.rtks.controller.res.skill;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.service.bo.skill.MissionPathBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练路径结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TrainingPathRes implements Serializable {

	/**
	 * 训练路径
	 *
	 * @see List<MissionPathBo>
	 */
	@ApiModelProperty(value = "训练路径")
	private List<MissionPathBo> path;

	/**
	 * 全部训练
	 *
	 * @see List<RelatedTrainingRes>
	 */
	@ApiModelProperty(value = "全部训练")
	private List<RelatedTrainingRes> related;

	/**
	 * 已完成课程数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("已完成课程数")
	private Integer completedCourseCount;

	/**
	 * 课程数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程数")
	private Integer courseCount;

	@ApiModelProperty("训练进度")
	private Integer progress;

}
