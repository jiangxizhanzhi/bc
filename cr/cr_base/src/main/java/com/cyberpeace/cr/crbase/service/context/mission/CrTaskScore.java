package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务得分
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CrTaskScore implements Serializable {

	/**
	 * 任务id
	 *
	 * @see String
	 */
	private String taskId;

	/**
	 * 任务模板id
	 *
	 * @see String
	 */
	private String missionTemplateId;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	private Integer score;

	/**
	 * 满
	 *
	 * @see Integer
	 */
	private Integer full;

	public Double getPercent() {
		if (score == null || score == 0) {
			return 0.0;
		}
		// full marks
		if (full == null || full == 0) {
			return 100.0;
		}
		return (double) score * 100 / full;
	}
}
