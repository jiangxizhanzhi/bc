package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.redvsblue.dao.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RbCombatRecordsAllDataRsp implements Serializable {

	@ApiModelProperty(value = "攻击阶段")
	private List<RbAttackDefenseStage> rbAttackStages;

	@ApiModelProperty(value = "防御阶段")
	private List<RbAttackDefenseStage> rbDefenseStages;

	@ApiModelProperty(value = "失陷主机")
	private List<RbLostHost> lostHosts;

	@ApiModelProperty(value = "攻击动态")
	private List<RbAttackEvent> rbAttackDefenseEvents;

	@ApiModelProperty()
	private Map<Integer, JSONObject> other;

	public RbCombatRecordsAllDataRsp() {
		this.rbAttackStages = new ArrayList<>();
		this.rbDefenseStages = new ArrayList<>();
		this.lostHosts = new ArrayList<>();
		this.rbAttackDefenseEvents = new ArrayList<>();
		this.other = new HashMap<>();
	}
}
