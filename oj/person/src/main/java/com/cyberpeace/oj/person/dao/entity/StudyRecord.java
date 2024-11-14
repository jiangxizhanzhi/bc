package com.cyberpeace.oj.person.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 学习记录
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class StudyRecord implements Serializable {

	/**
	 * 完成课程数
	 */
	@ApiModelProperty("完成课程数")
  private Integer courseCount;

	/**
	 * 理论课时数
	 */
	@ApiModelProperty("理论课时数")
  private Integer theoryCount;

	/**
	 * 实验课时数
	 */
	@ApiModelProperty("实验课时数")
  private Integer ExpCount;

	/**
	 * 学习时长
	 */
	@ApiModelProperty("学习时长")
  private String duration;

	/**
	 * 完成课后练习数
	 */
	@ApiModelProperty("完成课后练习数")
  private Integer practiceFinishCount;

	/**
	 * 完成报告数
	 */
	@ApiModelProperty("完成报告数")
  private Integer reportCount;

	/**
	 * 课后练习得分
	 */
	@ApiModelProperty("课后练习得分")
  private double score;

	/**
	 * 练习排名
	 */
	@ApiModelProperty("练习排名")
  private Integer practiceRanking;

	/**
	 * 用户编号
	 */
	@ApiModelProperty("用户编号")
  private String userId;

	/**
	 * 记录
	 */
	@ApiModelProperty("记录")
  private String record;

	/**
	 * 完成理论题数
	 */
	@ApiModelProperty("完成理论题数")
  private Integer theoryPracticeCount;

	/**
	 * 完成实操题数
	 */
	@ApiModelProperty("完成实操题数")
  private Integer operationPracticeCount;
}
