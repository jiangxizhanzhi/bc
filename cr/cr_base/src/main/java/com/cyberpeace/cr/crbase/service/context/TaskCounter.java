package com.cyberpeace.cr.crbase.service.context;

import lombok.Data;
import lombok.Getter;

/**
 * 任务统计计数结果
 */
@Data
public class TaskCounter {

	private Integer finished;

	private Integer released;

	private Integer notReleased;

	private Integer paused;

	private Integer closed;

	public enum TaskType {

		FINISHED(0, "已完成"), RELEASED(1, "进行中"), NOTRELEASED(2, "未下发"),
		PAUSED(4, "已暂停"), CLOSED(5, "已结束");

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
