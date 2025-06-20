package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务相关ws的data部分
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TaskWsData implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 代号
	 */
	private Integer code;

	public TaskWsData(Integer code) {
		this.code = code;
	}
}
