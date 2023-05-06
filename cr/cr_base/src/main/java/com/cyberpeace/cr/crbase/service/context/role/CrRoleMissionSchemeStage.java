package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * cr角色任务计划阶段
 * 角色任务编排阶段模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class CrRoleMissionSchemeStage implements Serializable {

	private String stage;

	private List<String> missions;

	private List<String> dynamic;
}
