package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_task")
public class CrTask extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Integer status;

	private Integer score;

	private String missionId;

	private String userId;

	private String userAnswer;

	private Date releaseTime;

	private Date reportTime;

	private Date taskStartTime;

	private String roomId;

	private String roleId;

	private Integer businessType;

	/**
	 * task 状态枚举
	 */
	public enum TaskStatus {

		RELEASED(11, "已下发"), STARTED(12, "已开始"), WAIT(0, "待评估"), SUCCESS(1, "成功"), FAIL(2,
				"失败"), REJECT(3, "驳回");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		TaskStatus(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		@Getter
		private static final int[] FINISHED_STATUS_LIST = { SUCCESS.getCode(), FAIL.getCode(), };

		@Getter
		private static final int[] NOT_FINISHED_STATUS_LIST = { WAIT.getCode(), REJECT.getCode(), };

		@Getter
		private static final int[] NOT_SUBMITTED_STATUS_LIST = { RELEASED.getCode(),
				STARTED.getCode(), };

	}
}
