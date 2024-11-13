package com.cyberpeace.cr.crbase.service.context.evaluation;

import java.io.Serializable;

import lombok.Data;

/**
 * 评估任务Bo
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class EvaluationMissionBo implements Serializable {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 用户答案
	 */
	private String userAnswer;

	/**
	 * 答案
	 */
	private String answer;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 选项
	 */
	private String option;

	/**
	 * 技能点
	 */
	private String skillPoints;

	/**
	 * 题解
	 */
	private String writeUp;

	/**
	 * 得分
	 */
	private Integer score;
}
