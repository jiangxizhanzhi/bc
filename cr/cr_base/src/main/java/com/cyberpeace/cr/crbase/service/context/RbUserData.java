package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝用户数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class RbUserData {

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 代表名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户姓名")
	private String repName;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户编号")
	private String resourceId;

	/**
	 * 群名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户组名")
	private String groupName;

	/**
	 * 角色名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色名")
	private String roleName;
}
