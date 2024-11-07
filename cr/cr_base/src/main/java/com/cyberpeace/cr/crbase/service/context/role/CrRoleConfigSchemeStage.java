package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于后台模板查询接口返回基本任务信息
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class CrRoleConfigSchemeStage implements Serializable {

	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "阶段名")
	private String stage;

	/**
	 * 任务
	 */
	@ApiModelProperty(value = "任务列表")
	private List<CrRoleConfigMission> missions;
}
