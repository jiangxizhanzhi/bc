package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.rtks.controller.res.CourseRelatedRes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程知识 技能 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("课程与知识点关系绑定")
public class CourseKnowledgeSkillBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("课程资源编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("课程描述")
	private String description;

	/**
	 * 电子书
	 */
	@ApiModelProperty("类型 基础=0，攻击=2，防御=3")
	private Integer eBook;

	/**
	 * 方向
	 */
	@ApiModelProperty("课程方向")
	private String direction;

	/**
	 * 方向名称
	 */
	@ApiModelProperty("方向名称")
	private String directionName;

	/**
	 * 图标
	 */
	@ApiModelProperty("课程logo")
	private String logo;

	/**
	 * 困难
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 课程时间
	 */
	@ApiModelProperty("课程时长")
	private Integer courseDuration = 0;

	/**
	 * 课计数
	 */
	@ApiModelProperty("课时总数")
	private Integer lessonCount = 0;

	/**
	 * 完成计数
	 */
	@ApiModelProperty("完成学习数量")
	private Integer completeCount = 0;

	/**
	 * 理论计数
	 */
	@ApiModelProperty("理论课数量")
	private Integer theoryCount = 0;

	/**
	 * 完成理论
	 */
	@ApiModelProperty("完成理论课数量")
	private Integer completeTheory = 0;

	/**
	 * 锻炼计数
	 */
	@ApiModelProperty("实验课数量")
	private Integer exerciseCount = 0;

	/**
	 * 完成锻炼
	 */
	@ApiModelProperty("完成实验课数量")
	private Integer completeExercise = 0;

	/**
	 * 过程
	 */
	@ApiModelProperty("课程学习进度")
	private Integer process = 0;

	/**
	 * 上次学习时间
	 */
	@ApiModelProperty("最后学习时间")
	private Date lastStudyTime;

	/**
	 * 知识
	 */
	@ApiModelProperty("课程关联知识点信息")
	private List<CourseRelatedRes> knowledges;

	/**
	 * 技能
	 */
	@ApiModelProperty("课程关联技能点信息")
	private List<CourseRelatedRes> skills;

	/**
	 * 面试
	 */
	@ApiModelProperty("是否具有课程访问权限")
	private Boolean interview;
}
