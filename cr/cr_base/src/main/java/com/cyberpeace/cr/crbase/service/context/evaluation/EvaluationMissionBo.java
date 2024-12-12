package com.cyberpeace.cr.crbase.service.context.evaluation;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估任务博
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
	 * 选择
	 */
	private String option;

	/**
	 * 技能点
	 */
	private String skillPoints;

	/**
	 * 写入向上
	 */
	private String writeUp;

	/**
	 * 得分
	 */
	private Integer score;
}
