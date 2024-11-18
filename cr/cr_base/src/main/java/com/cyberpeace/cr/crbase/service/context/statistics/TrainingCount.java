package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import lombok.Data;

/**
 * 训练统计中间体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TrainingCount implements Serializable {

	/**
	 * 完成
	 *
	 * @see Integer
	 */
	private Integer completed = 0;

	/**
	 * 未完成
	 *
	 * @see Integer
	 */
	private Integer uncompleted = 0;
}
