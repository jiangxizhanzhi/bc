package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 角色任务编排阶段模型
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CrRoleMissionSchemeStage implements Serializable {

	/**
	 * 阶段
	 */
	private String stage;

	/**
	 * 任务
	 */
	private List<String> missions;

	/**
	 * 动态
	 */
	private List<String> dynamic;
}
