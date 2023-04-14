package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动态
 */
@Data
public class NewsBo implements Serializable {

	@ApiModelProperty("用户id")
	private String userId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("岗位id")
	private String roleId;

	@ApiModelProperty("岗位名称")
	private String roleName;

	@ApiModelProperty("1：合格，2：优秀")
	private Integer type;

	@ApiModelProperty("记录时间")
	private String createTime;

	public NewsBo(SituationLogLocal log, String formattedTime) {
		this.userId = log.getUserId();
		this.roleId = log.getRoleId();
		this.roleName = log.getRoleName();
		this.type = log.getType();
		this.createTime = formattedTime;
	}
}
