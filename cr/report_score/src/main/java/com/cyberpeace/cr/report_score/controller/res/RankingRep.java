package com.cyberpeace.cr.report_score.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("排行榜信息")
@Data
public class RankingRep implements Serializable {

	@ApiModelProperty("排名")
	private String rank;

	@ApiModelProperty("队伍头像")
	private String logo;

	@ApiModelProperty("队伍ID")
	private String userId;

	@ApiModelProperty("队伍名称")
	private String userName;

	@ApiModelProperty("队伍分数")
	private String sumScore;

}
