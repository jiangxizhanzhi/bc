package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@TableName("cr_team_time_management")
public class TeamTimeManagement extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String roomId;

	private Date astronomicalTime;

	private Date combatTime;

	private Integer rate;

	private Integer status;

	private String createUserId;

	private String updateUserId;

	public Boolean isStopped() {
        return status.intValue() == TimeManagementStatus.STOP.getCode();
	}

	public enum TimeManagementStatus {

		NOT_START(0, "未开始"), START(1, "已开始"), STOP(2, "已结束");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		TimeManagementStatus(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum RateStatus {

		ONCE(1, "1"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		RateStatus(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
