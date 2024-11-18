package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB模板角色响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class RbTemplateRoleRsp {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色名")
	private String name;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	/**
	 * 子级
	 *
	 * @see List<RbTemplateRoleRsp>
	 */
	@ApiModelProperty(value = "子角色, 冗余")
	private List<RbTemplateRoleRsp> children;

	public RbTemplateRoleRsp(Integer code, String name) {
		this.name = name;
		this.roleGroup = code;
		this.children = Lists.newArrayList();
	}

	public RbTemplateRoleRsp(CrRole.RbRoleGroup roleGroup) {
		this(roleGroup, roleGroup.getDesc());
	}

	public RbTemplateRoleRsp(CrRole.RbRoleGroup roleGroup, String name) {
		this(roleGroup.getCode(), name);
	}

}
