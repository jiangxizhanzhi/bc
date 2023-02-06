package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RbCombatRecordsRsp implements Serializable {

	@ApiModelProperty(value = "攻击阶段")
	private RbCombatRecordsAddDataRsp group;

	@ApiModelProperty(value = "攻击动态")
	private List<RbAttackEvent> noGroup;
}
