package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 *
 * 任务得分
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class CrTaskScore implements Serializable {

	private String taskId;

	private String missionTemplateId;

	private Integer score;

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
