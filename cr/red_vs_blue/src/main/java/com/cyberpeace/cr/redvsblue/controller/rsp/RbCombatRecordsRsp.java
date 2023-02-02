package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseStage;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbLostHost;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RbCombatRecordsRsp implements Serializable {

	@ApiModelProperty(value = "攻击阶段")
	private RbCombatRecordsAddDataRsp group;

	@ApiModelProperty(value = "失陷主机")
	private List<RbAttackDefenseEvent> noGroup;
}
