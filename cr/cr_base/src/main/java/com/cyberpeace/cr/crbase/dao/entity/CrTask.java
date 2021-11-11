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

	public enum taskStatus {

		RELEASED(11, "已下发"), STARTED(12, "已开始"), WAIT(0, "待评估"), SUCCESS(1, "成功"), FAIL(2,
				"失败"), REJECT(3, "驳回");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		taskStatus(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	private String missionId;

	private String userId;

	private String userAnswer;

	private Date releaseTime;

	private Date reportTime;

	private Date taskStartTime;

	private String roomId;

	private String roleId;

	private Integer businessType;

}
