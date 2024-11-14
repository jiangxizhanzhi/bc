package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RtksSituationRankings implements Serializable {

	/**
	 * 网安岗位个人排名
	 */
	@ApiModelProperty("网安岗位个人排名")
	private List<UserRankingBo> userRanking = Lists.newArrayList();

	/**
	 * 网安优秀组织
	 */
	@ApiModelProperty("网安优秀组织")
	private List<OrgRankingBo> orgRanking = Lists.newArrayList();

	/**
	 * 网安复合人才
	 */
	@ApiModelProperty("网安复合人才")
	private List<MultiRoleRankingBo> multiRoleRanking = Lists.newArrayList();
}
