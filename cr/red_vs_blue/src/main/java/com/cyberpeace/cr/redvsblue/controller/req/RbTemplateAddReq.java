package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import com.cyberpeace.cr.redvsblue.entity.RbAttackPath;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTemplateAddReq extends BasicTemplate {

	@ApiModelProperty(required = true, value = "红方角色")
	@NotNull
	private List<CrRoleConfig> redRoles;

	@ApiModelProperty(required = true, value = "蓝方角色")
	@NotNull
	private List<CrRoleConfig> blueRoles;

	@ApiModelProperty(value = "攻击路径", required = false)
	private List<RbAttackPath> attackPaths;
}