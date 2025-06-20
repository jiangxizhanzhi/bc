package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色任务编排阶段模型
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CrRoleMissionSchemeStage implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
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
