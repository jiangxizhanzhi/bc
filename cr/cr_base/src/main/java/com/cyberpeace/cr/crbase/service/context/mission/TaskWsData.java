package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务ws数据
 * 任务相关ws的data部分
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TaskWsData implements Serializable {

	private Integer code;

	public TaskWsData(Integer code) {
		this.code = code;
	}
}
