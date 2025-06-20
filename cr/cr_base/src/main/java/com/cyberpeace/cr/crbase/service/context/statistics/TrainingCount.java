package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 训练统计中间体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TrainingCount implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 完成
	 */
	private Integer completed = 0;

	/**
	 * 未完成
	 */
	private Integer uncompleted = 0;
}
