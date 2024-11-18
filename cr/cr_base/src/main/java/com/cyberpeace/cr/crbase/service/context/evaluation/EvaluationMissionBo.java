package com.cyberpeace.cr.crbase.service.context.evaluation;

import java.io.Serializable;

import lombok.Data;

/**
 * 评估任务博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvaluationMissionBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	private String title;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 用户答案
	 *
	 * @see String
	 */
	private String userAnswer;

	/**
	 * 答案
	 *
	 * @see String
	 */
	private String answer;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 选择
	 *
	 * @see String
	 */
	private String option;

	/**
	 * 技能点
	 *
	 * @see String
	 */
	private String skillPoints;

	/**
	 * 写入向上
	 *
	 * @see String
	 */
	private String writeUp;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	private Integer score;
}
