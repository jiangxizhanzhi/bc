package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import com.cyberpeace.common.model.cache.RtksMatch;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrgRankingBo implements Serializable {

	@ApiModelProperty("组织id")
	private String orgId;

	@ApiModelProperty("组织名称")
	private String orgName;

	@ApiModelProperty("优秀人数")
	private Integer excellent;

	@ApiModelProperty("合格人数")
	private Integer qualified;

	@ApiModelProperty("合计")
	private Integer sum;

	/**
	 * 排序用，优秀人数>合格人数>优秀人员的匹配度合计
	 */
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
