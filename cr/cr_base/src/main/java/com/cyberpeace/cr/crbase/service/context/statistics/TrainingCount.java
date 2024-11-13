package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import lombok.Data;

/**
 * 训练统计中间体
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class TrainingCount implements Serializable {

	/**
	 * 完成
	 */
	private Integer completed = 0;

	/**
	 * 未完成
	 */
	private Integer uncompleted = 0;
}
