package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课堂监控Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class ClassroomMonitorBo implements Serializable {

	/**
	 * 用户编号
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 姓名
	 */
	@ApiModelProperty("姓名")
	private String name;

	/**
	 * 组织编号
	 */
	@ApiModelProperty("组织编号")
	private String organizationId;

	/**
	 * 组织名称
	 */
	@ApiModelProperty("组织名称")
	private String organizationName;

	/**
	 * 是否已签到
	 */
	@ApiModelProperty("是否已签到")
	private Boolean signIn;

	/**
	 * 签到时间
	 */
	@ApiModelProperty("签到时间")
	private Date signInTime;

	/**
	 * 是否需要帮助
	 */
	@ApiModelProperty("是否需要帮助")
	private Boolean needHelp;

	/**
	 * 远程协助地址
	 */
	@ApiModelProperty("远程协助地址")
	private String helpUrl;
}
