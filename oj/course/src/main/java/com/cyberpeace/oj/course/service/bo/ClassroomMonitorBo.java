package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 教室监视器 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ClassroomMonitorBo implements Serializable {

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("姓名")
	private String name;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty("组织编号")
	private String organizationId;

	/**
	 * 组织名称
	 *
	 * @see String
	 */
	@ApiModelProperty("组织名称")
	private String organizationName;

	/**
	 * 登录
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否已签到")
	private Boolean signIn;

	/**
	 * 签到时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("签到时间")
	private Date signInTime;

	/**
	 * 需要帮助
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否需要帮助")
	private Boolean needHelp;

	/**
	 * 帮助 URL
	 *
	 * @see String
	 */
	@ApiModelProperty("远程协助地址")
	private String helpUrl;
}
