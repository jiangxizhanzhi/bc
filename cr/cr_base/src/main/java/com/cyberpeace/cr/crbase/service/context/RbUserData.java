package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 红蓝用户数据
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class RbUserData {

	@ApiModelProperty(value = "LOGO")
	private String logo;

	@ApiModelProperty(value = "用户姓名")
	private String repName;

	@ApiModelProperty(value = "用户编号")
	private String resourceId;

	@ApiModelProperty(value = "用户组名")
	private String groupName;

	@ApiModelProperty(value = "角色名")
	private String roleName;
}
