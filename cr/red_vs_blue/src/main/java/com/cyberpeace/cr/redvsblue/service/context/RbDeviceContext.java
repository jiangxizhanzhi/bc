package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝靶标占用
 */
@Data
public class RbDeviceContext implements Serializable {

	@ApiModelProperty(value = "自增id")
	private String id;

	@ApiModelProperty(value = "红蓝对抗id")
	private String roomId;

	@ApiModelProperty(value = "主机id")
	private String deviceId;

	@ApiModelProperty(value = "用户id")
	private String createUserId;

	@ApiModelProperty(value = "用户昵称")
	private String createUser;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

}
