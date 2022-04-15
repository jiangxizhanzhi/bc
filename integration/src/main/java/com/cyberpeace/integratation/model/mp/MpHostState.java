package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MpHostState implements Serializable {

	/**
	 * CPU核数
	 */
	private Integer cpuCount;

	/**
	 * CPU使用率
	 */
	private Double cpuPercent;

	/**
	 * 磁盘使用率
	 */
	private Double diskPercent;

	/**
	 * 磁盘空间(GB)
	 */
	private Integer diskSpace;

	/**
	 * 节点id
	 */
	private String elementId;

	/**
	 * 空闲内存(MB)
	 */
	private Integer memoryFree;

	/**
	 * 内存使用率
	 */
	private Double memoryPercent;

	/**
	 * 总内存(MB)
	 */
	private Integer memoryTotal;

	/**
	 * 场景id
	 */
	private String sceneId;

	/**
	 * 时间
	 */
	private Date time;

}
