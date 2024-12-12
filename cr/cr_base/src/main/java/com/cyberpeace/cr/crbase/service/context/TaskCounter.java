package com.cyberpeace.cr.crbase.service.context;

import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 任务统计计数结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TaskCounter {

	/**
	 * 完成
	 */
	private Integer finished;

	/**
	 * 释放
	 */
	private Integer released;

	/**
	 * 未发布
	 */
	private Integer notReleased;

	public enum TaskType {

		FINISHED(0, "已完成"), RELEASED(1, "进行中"), NOTRELEASED(2, "未下发");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private TaskType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
