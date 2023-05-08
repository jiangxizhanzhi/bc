package com.cyberpeace.cr.crbase.service.context;

import lombok.Data;
import lombok.Getter;

/**
 * 任务统计计数结果
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TaskCounter {

	private Integer finished;

	private Integer released;

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
