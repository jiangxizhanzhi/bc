package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务相关ws的data部分
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TaskWsData implements Serializable {

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	private Integer code;

	public TaskWsData(Integer code) {
		this.code = code;
	}
}
