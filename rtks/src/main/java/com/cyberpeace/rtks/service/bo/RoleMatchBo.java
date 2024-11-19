package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位火柴Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("岗位匹配度数据统计计算")
public class RoleMatchBo implements Serializable {

	/**
	 * 岗位课程完成进度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("岗位课程完成进度")
    private Integer process;

	/**
	 * 完成理论总课时
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成理论总课时")
    private Integer theoryCount;

	/**
	 * 完成实验总课时
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成实验总课时")
    private Integer exerciseCount;

	/**
	 * 课程进度信息
	 *
	 * @see List<CourseKnowledgeSkillBo>
	 */
	@ApiModelProperty("课程进度信息")
	private List<CourseKnowledgeSkillBo> courses;

}
