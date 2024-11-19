package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RTKS态势排名
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RtksSituationRankings implements Serializable {

	/**
	 * 网安岗位个人排名
	 *
	 * @see List<UserRankingBo>
	 */
	@ApiModelProperty("网安岗位个人排名")
	private List<UserRankingBo> userRanking = Lists.newArrayList();

	/**
	 * 网安优秀组织
	 *
	 * @see List<OrgRankingBo>
	 */
	@ApiModelProperty("网安优秀组织")
	private List<OrgRankingBo> orgRanking = Lists.newArrayList();

	/**
	 * 网安复合人才
	 *
	 * @see List<MultiRoleRankingBo>
	 */
	@ApiModelProperty("网安复合人才")
	private List<MultiRoleRankingBo> multiRoleRanking = Lists.newArrayList();
}
