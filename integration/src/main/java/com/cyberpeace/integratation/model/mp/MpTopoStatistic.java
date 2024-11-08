package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中台拓扑统计
 *
 * @author SN
 * @date 2024/11/08
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
	 * 全国 人大计数
	 */
	private Integer npcCount;

	/**
	 * 资源占用数
	 */
	private Integer resourceOccupiedNums;

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
