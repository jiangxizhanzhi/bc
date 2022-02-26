package com.cyberpeace.cr.report_score.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("科目排行榜")
public class SubjectRankingRes implements Serializable {

	@ApiModelProperty("科目")
	private Integer subject;

	@ApiModelProperty("排行榜")
	private List<RankingRep> rankingRepList;

}
