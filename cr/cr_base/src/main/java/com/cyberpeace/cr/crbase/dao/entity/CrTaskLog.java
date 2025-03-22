package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.*;

/**
 * 靶场任务日志
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("cr_task_log")
public class CrTaskLog extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 任务id
	 */
	private String missionId;

	/**
	 * 任务id
	 */
	private String taskId;

	/**
	 * 行动代号
	 */
	private Integer actionCode;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 行动代号
	 *
	 * @author SN
	 * @date 2024/11/13
	 */
	public enum ActionCode {

		/**
		 * 下发
		 */
		RESET(0, "撤回任务"),

		/**
		 * 下发
		 */
		RELEASE(1, "下发任务"),
		/**
		 * 开放题解
		 */
		OPEN_WRITEUP(2, "开放题解"),
		/**
		 * 关闭 Writeup
		 */
		CLOSE_WRITEUP(3, "关闭题解"),
		/**
		 * 提交
		 */
		SUBMIT(4,
				"上报任务"),
		/**
		 * 机评成功
		 */
		ROBOT_SUCCESS(5, "机评成功"),
		/**
		 * 机评失败
		 */
		ROBOT_FAILURE(6, "机评失败"),
		/**
		 * 人评成功
		 */
		MAN_SUCCESS(7,
						"人评成功"),
		/**
		 * 人评失败
		 */
		MAN_FAILURE(8, "人评失败"),
		/**
		 * 人评驳回
		 */
		MAN_REJECT(9, "人评驳回");

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

		ActionCode(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
