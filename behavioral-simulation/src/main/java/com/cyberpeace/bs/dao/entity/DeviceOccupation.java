package com.cyberpeace.bs.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 记录场景对实体靶标的占用
 * @author SN
 */
@Data
@TableName("bs_device_occupation")
public class DeviceOccupation {

	/**
	 * 实体设备id
	 */
	private String deviceId;

	/**
	 * 实体设备类型
	 */
	private Integer deviceType;

	/**
	 * 设备所在场景id
	 */
	private String sceneId;

	/**
	 * 用户行为模拟id（仪表id）
	 */
	private String simulatorId;

	/**
	 * 创建时间
	 */
	private Date createTime;
}
