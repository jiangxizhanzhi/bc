package com.cyberpeace.cr.redvsblue.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RbAttackDefenseStage implements Serializable {

	// 攻击阶段数据

	@ApiModelProperty(value = "")
	private String resourceId;

	@ApiModelProperty(value = "角色")
	private Integer group;

	@ApiModelProperty(value = "攻击阶段")
	private Integer attackPhase;

	@ApiModelProperty(value = "")
	private String content;
}
