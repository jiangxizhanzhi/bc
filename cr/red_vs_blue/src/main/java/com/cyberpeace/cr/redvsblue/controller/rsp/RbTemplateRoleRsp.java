package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.common.enums.RbRoleGroup;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝对抗模板岗位响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbTemplateRoleRsp {

	/**
	 * 角色名
	 */
	@ApiModelProperty(value = "角色名")
	private String name;

	/**
	 * 角色组
	 */
	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	/**
	 * 子角色, 冗余
	 */
	@ApiModelProperty(value = "子角色, 冗余")
	private List<RbTemplateRoleRsp> children;

	public RbTemplateRoleRsp(Integer code, String name) {
		this.name = name;
		this.roleGroup = code;
		this.children = Lists.newArrayList();
	}

	public RbTemplateRoleRsp(RbRoleGroup roleGroup) {
		this(roleGroup, roleGroup.getDesc());
	}

	public RbTemplateRoleRsp(RbRoleGroup roleGroup, String name) {
		this(roleGroup.getCode(), name);
	}

}
