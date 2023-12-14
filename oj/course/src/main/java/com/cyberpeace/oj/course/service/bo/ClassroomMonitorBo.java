package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ClassroomMonitorBo implements Serializable {

	@ApiModelProperty("用户编号")
	private String userId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("昵称")
	private String name;

	@ApiModelProperty("组织编号")
	private String organizationId;

	@ApiModelProperty("组织名称")
	private String organizationName;

	@ApiModelProperty("是否已签到")
	private Boolean signIn;

	@ApiModelProperty("签到时间")
	private Date signInTime;

	@ApiModelProperty("是否需要帮助")
	private Boolean needHelp;

	@ApiModelProperty("远程协助地址")
	private String helpUrl;
}
