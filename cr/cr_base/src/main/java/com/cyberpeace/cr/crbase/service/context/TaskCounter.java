package com.cyberpeace.cr.crbase.service.context;

import lombok.Data;
import lombok.Getter;

/**
 * 任务统计计数结果
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class TaskCounter {

	/**
	 * 完成
	 */
	private Integer finished;

	/**
	 * 已下发
	 */
	private Integer released;

	/**
	 * 未下发
	 */
	private Integer notReleased;

	/**
	 * 任务类型
	 *
	 * @author SN
	 * @date 2024/11/13
	 */
	public enum TaskType {

		/**
		 * 完成
		 */
		FINISHED(0, "已完成"),
		/**
		 * 已下发
		 */
		RELEASED(1, "进行中"),
		/**
		 * 未发布
		 */
		NOTRELEASED(2, "未下发");

		/**
		 * 代号
		 */
		@Getter
		private Integer code;

		/**
		 * 描述
		 */
		@Getter
		private String desc;

		private TaskType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
