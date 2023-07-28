package com.cyberpeace.common.context.role;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 角色任务编排阶段模型
 */
@Data
public class CrRoleMissionSchemeStage implements Serializable {

	private String stage;

	private List<String> missions;

	private List<String> dynamic;
}
