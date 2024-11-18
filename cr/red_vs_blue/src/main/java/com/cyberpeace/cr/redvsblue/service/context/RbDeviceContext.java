package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝靶标占用
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RbDeviceContext implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "自增id")
	private String id;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "红蓝对抗id")
	private String roomId;

	/**
	 * 装置id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "主机id")
	private String deviceId;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户id")
	private String createUserId;

	/**
	 * 创建用户
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户姓名")
	private String createUser;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

}
