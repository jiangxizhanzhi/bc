package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;

import lombok.Data;

/**
 * 分数和进度
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ScoreAndProgress implements Serializable {

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	private Integer score;

	/**
	 * 进展
	 *
	 * @see Integer
	 */
	private Integer progress;
}
