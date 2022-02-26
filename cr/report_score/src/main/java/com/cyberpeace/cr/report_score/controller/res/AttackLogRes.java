package com.cyberpeace.cr.report_score.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("攻击日志信息")
@Data
public class AttackLogRes implements Serializable {

	@ApiModelProperty("攻击时间")
	private String attackTime;

	@ApiModelProperty("队伍名称")
	private String userName;

	@ApiModelProperty("源IP")
	private String sourceIp;

	@ApiModelProperty("目标设备")
	private String targetNodeName;

	@ApiModelProperty("目标IP")
	private String targetIp;

}
