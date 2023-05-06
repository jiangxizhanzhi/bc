package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * cr角色配置任务计划
 * 用于后台模板查询接口返回基本任务信息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class CrRoleConfigMissionsScheme implements Serializable {

	@ApiModelProperty(value = "色调")
	private Integer color;

	@ApiModelProperty(value = "模板")
	private Integer template;

	@ApiModelProperty(value = "任务配置")
	private List<CrRoleConfigSchemeStage> schemeData;
}