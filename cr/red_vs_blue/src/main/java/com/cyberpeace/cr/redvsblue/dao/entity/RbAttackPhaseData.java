package com.cyberpeace.cr.redvsblue.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class RbAttackPhaseData implements Serializable {

	// 攻击阶段数据

	@ApiModelProperty(value = "攻击阶段数据")
	private List<RbAttackDefenseStage> stage;

	@ApiModelProperty(value = "数量")
	private Integer count;

	@ApiModelProperty(value = "阶段")
	private Integer attackPhase;
}
