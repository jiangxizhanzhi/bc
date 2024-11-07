package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;

import lombok.Data;

/**
 * 分数和进度
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class ScoreAndProgress implements Serializable {

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 进展
	 */
	private Integer progress;
}
