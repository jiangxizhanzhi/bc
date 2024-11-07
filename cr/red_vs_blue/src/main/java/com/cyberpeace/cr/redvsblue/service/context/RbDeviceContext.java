package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝靶标占用
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbDeviceContext implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "自增id")
	private String id;

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "红蓝对抗id")
	private String roomId;

	/**
	 * 装置id
	 */
	@ApiModelProperty(value = "主机id")
	private String deviceId;

	/**
	 * 创造用户id
	 */
	@ApiModelProperty(value = "用户id")
	private String createUserId;

	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "用户姓名")
	private String createUser;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

}
