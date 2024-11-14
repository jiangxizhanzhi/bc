package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中台拓扑统计
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class MpTopoStatistic implements Serializable {

	/**
	 * CSenseid
	 */
	private String csceneId;

	/**
	 * 防火墙计数
	 */
	private Integer firewallCount;

	/**
	 * 路由器计数
	 */
	private Integer routerCount;

	/**
	 * 开关计数
	 */
	private Integer switchCount;

	/**
	 * 终端计数
	 */
	private Integer terminalCount;

	/**
	 * npc计数
	 */
	private Integer npcCount;

	/**
	 * 资源占用数
	 */
	private Integer resourceOccupiedNums;

	/**
	 * 总数CPU
	 */
	private Integer totalCpu;

	/**
	 * 总数磁盘
	 */
	private Integer totalDisk;

	/**
	 * 总数内存
	 */
	private Integer totalRam;

	public Integer getNodesCount() {
		int count = 0;
		if (this.firewallCount != null) {
			count += this.firewallCount;
		}
		if (this.routerCount != null) {
			count += this.routerCount;
		}
		if (this.switchCount != null) {
			count += this.switchCount;
		}
		if (this.terminalCount != null) {
			count += this.terminalCount;
		}
		if (this.npcCount != null) {
			count += this.npcCount;
		}
		return count;
	}
}
