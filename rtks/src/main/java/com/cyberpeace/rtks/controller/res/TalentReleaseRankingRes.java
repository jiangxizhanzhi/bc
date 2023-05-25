package com.cyberpeace.rtks.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TalentReleaseRankingRes implements Serializable {

	@ApiModelProperty("用户名")
	private String userName;

	@ApiModelProperty("优秀")
	private Integer excellent;

	@ApiModelProperty("合格")
	private Integer qualified;

	@ApiModelProperty("其他")
	private Integer other;

	@ApiModelProperty("优秀岗位匹配总数")
	private Integer excellentMatchSum;

	public TalentReleaseRankingRes() {
		this.excellent = 0;
		this.qualified = 0;
		this.other = 0;
		this.excellentMatchSum = 0;
	}


	public void addQualified() {
		this.qualified++;
	}

	public void addExcellent() {
		this.excellent++;
	}

	public void addOther() {
		this.other++;
	}
	public void addMatchSum(Integer match) {
		this.excellentMatchSum += match;
	}



}