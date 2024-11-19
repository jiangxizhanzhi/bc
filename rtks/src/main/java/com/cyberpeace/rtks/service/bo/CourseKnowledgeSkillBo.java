package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.rtks.controller.res.CourseRelatedRes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程知识点技能Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("课程与知识点关系绑定")
public class CourseKnowledgeSkillBo implements Serializable {

	/**
	 * 课程资源编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程资源编号")
	private String resourceId;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 课程描述
	 *
	 * @see String
	 */
	@ApiModelProperty("课程描述")
	private String description;

	/**
	 * 类型 基础=0，攻击=2，防御=3
	 *
	 * @see Integer
	 */
	@ApiModelProperty("类型 基础=0，攻击=2，防御=3")
	private Integer eBook;

	/**
	 * 课程方向
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向")
	private String direction;

	/**
	 * 方向名称
	 *
	 * @see String
	 */
	@ApiModelProperty("方向名称")
	private String directionName;

	/**
	 * 课程logo
	 *
	 * @see String
	 */
	@ApiModelProperty("课程logo")
	private String logo;

	/**
	 * 课程难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 课程时长
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程时长")
	private Integer courseDuration = 0;

	/**
	 * 课时总数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时总数")
	private Integer lessonCount = 0;

	/**
	 * 完成学习数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成学习数量")
	private Integer completeCount = 0;

	/**
	 * 理论课数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("理论课数量")
	private Integer theoryCount = 0;

	/**
	 * 完成理论课数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成理论课数量")
	private Integer completeTheory = 0;

	/**
	 * 实验课数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("实验课数量")
	private Integer exerciseCount = 0;

	/**
	 * 完成实验课数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成实验课数量")
	private Integer completeExercise = 0;

	/**
	 * 课程学习进度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程学习进度")
	private Integer process = 0;

	/**
	 * 最后学习时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("最后学习时间")
	private Date lastStudyTime;

	/**
	 * 课程关联知识点信息
	 *
	 * @see List<CourseRelatedRes>
	 */
	@ApiModelProperty("课程关联知识点信息")
	private List<CourseRelatedRes> knowledges;

	/**
	 * 课程关联技能点信息
	 *
	 * @see List<CourseRelatedRes>
	 */
	@ApiModelProperty("课程关联技能点信息")
	private List<CourseRelatedRes> skills;

	/**
	 * 是否具有课程访问权限
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否具有课程访问权限")
	private Boolean interview;
}
