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
 * @date 2024/11/08
 */
@Data
@ApiModel
public class ClassroomMonitorBo implements Serializable {

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 名称
	 */
	@ApiModelProperty("姓名")
	private String name;

	/**
	 * 组织id
	 */
	@ApiModelProperty("组织编号")
	private String organizationId;

	/**
	 * 组织名称
	 */
	@ApiModelProperty("组织名称")
	private String organizationName;

	/**
	 * 登录
	 */
	@ApiModelProperty("是否已签到")
	private Boolean signIn;

	/**
	 * 签到时间
	 */
	@ApiModelProperty("签到时间")
	private Date signInTime;

	/**
	 * 需要帮助
	 */
	@ApiModelProperty("是否需要帮助")
	private Boolean needHelp;

	/**
	 * 帮助 URL
	 */
	@ApiModelProperty("远程协助地址")
	private String helpUrl;
}
