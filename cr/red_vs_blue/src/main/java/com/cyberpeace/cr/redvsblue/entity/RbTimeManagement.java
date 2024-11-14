package com.cyberpeace.cr.redvsblue.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * 红蓝对抗时间管理
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("cr_rb_time_management")
public class RbTimeManagement extends BaseTable {

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
	 * 天文时间
	 */
	private Date astronomicalTime;

	/**
	 * 战斗时间
	 */
	private Date combatTime;

	/**
	 * 训练时长
	 */
	private Long length;

	/**
	 * 恢复时间
	 */
	private Date recoverTime;

	/**
	 * 比率
	 */
	private Integer rate;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 创建用户id
	 */
	private String createUserId;

	/**
	 * 更新用户id
	 */
	private String updateUserId;

	/**
	 * 结束时间
	 */
	private Date endTime;

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
