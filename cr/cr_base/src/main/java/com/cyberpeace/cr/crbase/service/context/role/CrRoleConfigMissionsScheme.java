package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用于后台模板查询接口返回基本任务信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CrRoleConfigMissionsScheme implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 颜色
	 */
	@ApiModelProperty(value = "色调")
	private Integer color;

	/**
	 * 模板
	 */
	@ApiModelProperty(value = "模板")
	private Integer template;

	/**
	 * 方案数据
	 */
	@ApiModelProperty(value = "任务配置")
	private List<CrRoleConfigSchemeStage> schemeData;
}