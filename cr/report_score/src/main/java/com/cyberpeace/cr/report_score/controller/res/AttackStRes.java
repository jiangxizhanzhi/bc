package com.cyberpeace.cr.report_score.controller.res;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("攻击次数统计信息")
@Data
public class AttackStRes implements Serializable {

	@ApiModelProperty("队伍名称")
	private String userName;

	@ApiModelProperty("队伍ID")
	private String userId;

	@ApiModelProperty("攻击次数")
	private String count;

	@ApiModelProperty("队伍IP")
	private String ips;

}
