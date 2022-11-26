package com.cyberpeace.bs.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 记录场景对实体靶标的占用
 * @author SN
 */
@Data
@TableName("bs_device_occupation")
public class DeviceOccupation extends BaseTable {

	/**
	 * 实体设备id
	 */
	private String deviceId;

	/**
	 * 实体设备类型
	 */
	private Integer deviceType;

	/**
	 * 设备连接的虚实结合交换机上的端口
	 */
	private String port;

	/**
	 * 是否被占用
	 */
	private Boolean isOccupied;

	/**
	 * 设备所在场景id
	 */
	private String sceneId;

	/**
	 * 用户行为模拟id（仪表id）
	 */
	private String simulatorId;
}
