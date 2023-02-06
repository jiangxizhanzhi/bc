package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackPhaseData;
import com.cyberpeace.cr.redvsblue.dao.entity.RbLostHost;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RbCombatRecordsGroupDataRsp implements Serializable {

	@ApiModelProperty(value = "攻击阶段")
	private List<RbAttackPhaseData> attackPhase;

	@ApiModelProperty(value = "失陷主机")
	private List<RbLostHost> lostHost;

	@ApiModelProperty(value = "防御阶段")
	private List<RbAttackPhaseData> defensePhase;
}
