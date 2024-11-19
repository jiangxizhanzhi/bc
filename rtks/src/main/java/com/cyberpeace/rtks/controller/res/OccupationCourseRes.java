package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.service.bo.CourseKnowledgeSkillBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 职业课程结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("职业课程学习进度")
public class OccupationCourseRes implements Serializable {

	/**
	 * 课程进度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程进度")
    private Integer coursePercent;

	/**
	 * 理论课时进度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("理论课时进度")
    private Integer theoryPercent;

	/**
	 * 实验课时进度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("实验课时进度")
    private Integer exercisePercent;

	/**
	 * 课程知识点信息
	 *
	 * @see List<CourseKnowledgeSkillBo>
	 */
	@ApiModelProperty("课程知识点信息")
	private List<CourseKnowledgeSkillBo> knowledgeBos;

}
