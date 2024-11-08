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
 * @date 2024/11/08
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

	public enum ActionCode {

		RELEASE(1, "下发任务"), OPEN_WRITEUP(2, "开放题解"), CLOSE_WRITEUP(3, "关闭题解"), SUBMIT(4,
				"上报任务"), ROBOT_SUCCESS(5, "机评成功"), ROBOT_FAILURE(6, "机评失败"), MAN_SUCCESS(7,
						"人评成功"), MAN_FAILURE(8, "人评失败"), MAN_REJECT(9, "人评驳回");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		ActionCode(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
