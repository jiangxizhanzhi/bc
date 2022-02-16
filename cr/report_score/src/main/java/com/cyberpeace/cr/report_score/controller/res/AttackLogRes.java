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
	private Date attack_time;

	@ApiModelProperty("队伍ID")
	private String userId;

	@ApiModelProperty("队伍名称")
	private String userName;

	@ApiModelProperty("目标设备")
	private String target;

	@ApiModelProperty("目标IP")
	private String IP;

}
