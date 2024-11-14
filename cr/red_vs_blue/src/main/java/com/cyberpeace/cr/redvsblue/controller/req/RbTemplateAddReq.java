package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝对抗模板新增请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbTemplateAddReq extends BasicTemplate {

	/**
	 * 红方角色
	 */
	@ApiModelProperty(required = true, value = "红方角色")
	@NotNull
	private List<CrRoleConfig> redRoles;

	/**
	 * 蓝方角色
	 */
	@ApiModelProperty(required = true, value = "蓝方角色")
	@NotNull
	private List<CrRoleConfig> blueRoles;

}