package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("包含用户的角色")
@Data
public class RoleUsers {

	private String roleName;

	private Integer roleGroup;

	private List<String> users;

}
