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

	public Boolean isPaused() {

		return status.intValue() == TimeManagementStatus.PAUSE.getCode();
	}

	public Boolean isRunning() {
		boolean result = status.intValue() == TimeManagementStatus.RECOVERY.getCode();
		result = result || status.intValue() == TimeManagementStatus.START.getCode();

		return result;
	}

	public Boolean isRecovering() {
		return status.intValue() == TimeManagementStatus.RECOVERY.getCode();
	}

	public enum TimeManagementStatus {

		STOP(0, "停止"), PAUSE(1, "暂停"), START(2, "开始"), RECOVERY(3, "恢复");

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
