package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务得分
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CrTaskScore implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 任务id
	 */
	private String taskId;

	/**
	 * 任务模板id
	 */
	private String missionTemplateId;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 满
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
