package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.cyberpeace.cr.redvsblue.dao.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class RbCombatRecordsAllDataRsp implements Serializable {

	@ApiModelProperty(value = "攻击阶段")
	private List<RbAttackDefenseStage> rbAttackDefenseStages;

	@ApiModelProperty(value = "失陷主机")
	private List<RbLostHost> lostHosts;

	@ApiModelProperty(value = "攻击动态")
	private List<RbAttackEvent> rbAttackDefenseEvents;

	public RbCombatRecordsAllDataRsp() {
		this.rbAttackDefenseStages = new ArrayList<>();
		this.lostHosts = new ArrayList<>();
		this.rbAttackDefenseEvents = new ArrayList<>();
	}
}
