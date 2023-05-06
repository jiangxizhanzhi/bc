package com.cyberpeace.common.model.room;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 仪表板用户
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class DashboardUser implements Serializable {

	@ApiModelProperty("用户名")
	private String username;

	@ApiModelProperty("角色")
	private String role;

	@ApiModelProperty("编号")
	private String id;

	@ApiModelProperty("头像地址")
	private String avatar;

	@ApiModelProperty("登陆")
	private String sign;

	@ApiModelProperty("状态")
	private String status;
}
