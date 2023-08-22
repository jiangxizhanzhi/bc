package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用于后台模板查询的返回
 */
@Data
public class CrDailyRoleConfig  {

	@ApiModelProperty(value = "组织名称")
	private String name;

	/**
	 * 辅助前端切换不同角色任务编排的字段
	 */
	@ApiModelProperty(value = "辅助前端切换不同角色任务编排的字段")
	private String id;

	@ApiModelProperty(value = "组织类型")
	private String type;

	@ApiModelProperty(value = "角色列表", required = true)
	private List<CrRoleConfig> children;
}
