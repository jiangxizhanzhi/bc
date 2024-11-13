package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务相关ws的data部分
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class TaskWsData implements Serializable {

	/**
	 * 代号
	 */
	private Integer code;

	public TaskWsData(Integer code) {
		this.code = code;
	}
}
