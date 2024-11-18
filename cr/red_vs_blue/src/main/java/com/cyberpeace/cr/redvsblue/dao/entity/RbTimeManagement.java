package com.cyberpeace.cr.redvsblue.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * RB 时间管理
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_rb_time_management")
public class RbTimeManagement extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

	/**
	 * 天文时间
	 *
	 * @see Date
	 */
	private Date astronomicalTime;

	/**
	 * 战斗时间
	 *
	 * @see Date
	 */
	private Date combatTime;

	/**
	 * 率
	 *
	 * @see Integer
	 */
	private Integer rate;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	private Integer status;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 更新用户id
	 *
	 * @see String
	 */
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
