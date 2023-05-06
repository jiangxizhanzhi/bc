package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**cr角色配置任务
 * 用于后台模板查询接口返回基本任务信息
 @author cyberpeace
 @date 2023/05/06
 */
@Data
public class CrRoleConfigMission implements Serializable {

	@ApiModelProperty(value = "任务名称")
	private String title;

	@ApiModelProperty(value = "任务id")
	private String resourceId;

	@ApiModelProperty(value = "任务描述")
	private String description;
}
