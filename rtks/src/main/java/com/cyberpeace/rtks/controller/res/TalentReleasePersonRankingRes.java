package com.cyberpeace.rtks.controller.res;

import com.cyberpeace.rtks.service.bo.situation.RoleMatchRecordContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TalentReleasePersonRankingRes implements Serializable {

	@ApiModelProperty("用户名")
	private String userName;

	@ApiModelProperty("优秀")
	private Integer excellent;

	@ApiModelProperty("合格")
	private Integer qualified;

	@ApiModelProperty("其他")
	private Integer other;

	@ApiModelProperty("满足度Top1")
	private Integer matchOne;

	@ApiModelProperty("满足度Top2")
	private Integer matchTwo;

	@ApiModelProperty("满足度Top3")
	private Integer matchThree;

	@ApiModelProperty("优秀岗位匹配总数")
	private Integer excellentMatchSum;

	@ApiModelProperty("最高匹配度")
	private Integer maxMatch;

	@ApiModelProperty("岗位名称")
	private List<String> jobs;

	@ApiModelProperty("岗位名称")
	private List<RoleMatchRecordContext> jobInfos;

	public TalentReleasePersonRankingRes() {
		this.excellent = 0;
		this.qualified = 0;
		this.other = 0;
		this.excellentMatchSum = 0;
	};


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