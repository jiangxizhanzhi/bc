package com.cyberpeace.bs.dao.entity;

import java.time.LocalTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("bs_user_simulator")
public class UserSimulator extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 用户类型id
	 */
	private String userTypeId;

	/**
	 * 用户类型名称
	 * 保存名称
	 */
	private String userType;

	/**
	 * 开始时间
	 */
	private LocalTime startTime;

	/**
	 * 结束时间
	 */
	private LocalTime endTime;

	/**
	 * 访问习惯
	 */
	private Integer accessOs;

	/**
	 * 访问配置
	 */
	private Integer accessConfig;

	/**
	 * 访问行为json
	 */
	private String behaviorList;

	/**
	 * 行为变量json
	 */
	private String behaviorVariable;

	/**
	 * 行为数量
	 */
	private Integer behaviorCount;

	/**
	 * 访问习惯
	 */
	private String accessHabit;

	/**
	 * 是否限制带宽
	 */
	private Boolean bandwidthLimited;

	private Integer bandwidth;

	/**
	 * 最后已执行编号（倒排）
	 */
	private Integer executedIndex;

	/**
	 * 执行状态
	 */
	private Integer status;

	/**
	 * 执行设备id
	 */
	private String deviceId;

	/**
	 * 执行设备名称
	 */
	private String deviceName;

	/**
	 * agent设备的ip
	 */
	private String deviceIp;

	/**
	 * 设备是否实体设备
	 */
	private Boolean isEntity;

	/**
	 * 关联场景id
	 */
	private String sceneId;

	/**
	 * 关联场景名称
	 */
	private String sceneName;

	/**
	 * 用户行为模型id
	 */
	private String userModelId;

	public enum Status implements GenericEnum {

		NOT_STARTED(0, "未开始"), RUNNING(1, "执行中"), PAUSED(2, "暂停中"), STOPPED(3, "已停止");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Status(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
