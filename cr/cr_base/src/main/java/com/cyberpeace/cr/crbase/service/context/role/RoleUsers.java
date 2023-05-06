package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色用户
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@ApiModel("查询实例列表时包含所有用户的角色")
@Data
public class RoleUsers {

	@ApiModelProperty(value = "角色名")
	private String roleName;

	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	@ApiModelProperty(value = "用户列表")
	private List<String> users;

}
