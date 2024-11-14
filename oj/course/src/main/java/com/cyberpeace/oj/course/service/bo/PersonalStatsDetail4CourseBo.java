package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 个人统计详情4课程Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PersonalStatsDetail4CourseBo implements Serializable {
	/**
	 * 课程resource_id
	 */
	@ApiModelProperty("课程resource_id")
    private String resourceId;

	/**
	 * 课程名称
	 */
	@ApiModelProperty("课程名称")
    private String name;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
    private Integer difficulty;

	/**
	 * 方向名称
	 */
	@ApiModelProperty("方向名称")
    private String directionName;

	/**
	 * 课程图片地址
	 */
	@ApiModelProperty("课程图片地址")
    private String logo;

	/**
	 * 课时数量
	 */
	@ApiModelProperty("课时数量")
    private Integer lessonCount;

	/**
	 * 已学习理论课时
	 */
	@ApiModelProperty("已学习理论课时")
    private Integer learnedTheory;

	/**
	 * 已学习实验课时
	 */
	@ApiModelProperty("已学习实验课时")
    private Integer learnedExp;

	/**
	 * 已完成课后练习
	 */
	@ApiModelProperty("已完成课后练习")
    private Integer learnedAfterClassPractice;

	/**
	 * 已提交实验报告
	 */
	@ApiModelProperty("已提交实验报告")
    private Integer learnedExpReport;

	/**
	 * 正在学习课时
	 */
	@ApiModelProperty("正在学习课时")
    private String lessonId;

	/**
	 * 最近一次学习时间
	 */
	@ApiModelProperty("最近一次学习时间")
    private String createTime;

	/**
	 * 课程学习类型
	 */
	@ApiModelProperty("课程学习类型")
    private Integer learnedType;

	/**
	 * 课堂id
	 */
	@ApiModelProperty("课堂id")
    private String roomId;
}
