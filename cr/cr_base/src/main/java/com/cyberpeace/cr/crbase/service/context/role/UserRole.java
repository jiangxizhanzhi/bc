package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@ApiModel("单个用户角色")

public class UserRole {

	@ApiModelProperty("用户id")
	private String resourceId;

	@ApiModelProperty("用户名")
	private String name;

	@ApiModelProperty("username")
	private String username;

	@ApiModelProperty("用户头像")
	private String logo;

	@ApiModelProperty("角色")
	private String roleName;

	@ApiModelProperty("角色描述")
	private String roleDesc;

	@ApiModelProperty("角色组：4红方 5蓝方")
	private Integer type;

	public static String getName(Function<UserRole,String> function, List<UserRole> userRoleList) {
		if (CollectionUtils.isEmpty(userRoleList)) {
			return "";
		}
		return userRoleList.stream().map(function).collect(Collectors.joining(","));
	}
}
