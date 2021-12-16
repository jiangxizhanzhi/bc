package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务相关ws的data部分
 */
@Data
public class MissionWsData implements Serializable {

	private Integer code;

	public MissionWsData(Integer code) {
		this.code = code;
	}
}
