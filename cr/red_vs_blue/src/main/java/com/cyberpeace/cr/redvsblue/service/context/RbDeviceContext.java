package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝靶标占用
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbDeviceContext implements Serializable {

	/**
	 * 自增id
	 */
	@ApiModelProperty(value = "自增id")
	private String id;

	/**
	 * 红蓝对抗id
	 */
	@ApiModelProperty(value = "红蓝对抗id")
	private String roomId;

	/**
	 * 主机id
	 */
	@ApiModelProperty(value = "主机id")
	private String deviceId;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private String createUserId;

	/**
	 * 用户姓名
	 */
	@ApiModelProperty(value = "用户姓名")
	private String createUser;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

}
