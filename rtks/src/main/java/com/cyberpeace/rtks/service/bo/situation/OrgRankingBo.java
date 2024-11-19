package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import com.cyberpeace.common.model.cache.RtksMatch;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织排名Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class OrgRankingBo implements Serializable {

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty("组织id")
	private String orgId;

	/**
	 * 组织名称
	 *
	 * @see String
	 */
	@ApiModelProperty("组织名称")
	private String orgName;

	/**
	 * 优秀人数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("优秀人数")
	private Integer excellent;

	/**
	 * 合格人数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("合格人数")
	private Integer qualified;

	/**
	 * 合计
	 *
	 * @see Integer
	 */
	@ApiModelProperty("合计")
	private Integer sum;

	/**
	 * 排序用，优秀人数>合格人数>优秀人员的匹配度合计
	 *
	 * @see Integer
	 */
	@ApiModelProperty("优秀岗位匹配总数")
	private Integer excellentMatchSum;

	public OrgRankingBo(RtksMatch match) {
		this.orgId = match.getOrgId();
		this.orgName = match.getOrgName();
		this.qualified = 0;
		this.excellent = 0;
		this.excellentMatchSum = 0;
	}

	public void addQualified() {
		this.qualified++;
	}

	public void addExcellent() {
		this.excellent++;
	}

	public void addMatchSum(Integer match) {
		this.excellentMatchSum += match;
	}
}
