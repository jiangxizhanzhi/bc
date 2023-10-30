package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import lombok.Data;

@Data
public class MpTopoStatistic implements Serializable {

	private String csceneId;

	private Integer firewallCount;

	private Integer routerCount;

	private Integer switchCount;

	private Integer terminalCount;

	private Integer npcCount;

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
