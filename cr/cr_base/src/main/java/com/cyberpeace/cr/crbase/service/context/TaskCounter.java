package com.cyberpeace.cr.crbase.service.context;

import lombok.Data;
import lombok.Getter;

/**
 * 任务统计计数结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class TaskCounter {

	/**
	 * 完成
	 *
	 * @see Integer
	 */
	private Integer finished;

	/**
	 * 释放
	 *
	 * @see Integer
	 */
	private Integer released;

	/**
	 * 未发布
	 *
	 * @see Integer
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
