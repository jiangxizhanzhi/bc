package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于后台模板查询接口返回基本任务信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CrRoleConfigSchemeStage implements Serializable {

	/**
	 * 阶段
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "阶段名")
	private String stage;

	/**
	 * 任务
	 *
	 * @see List<CrRoleConfigMission>
	 */
	@ApiModelProperty(value = "任务列表")
	private List<CrRoleConfigMission> missions;
}
