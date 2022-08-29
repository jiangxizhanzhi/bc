package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MultiRoleRankingBo implements Serializable {

	@ApiModelProperty("用户id")
	private String userId;

	@ApiModelProperty("用户名")
	private String name;

	@ApiModelProperty("优秀岗位数")
	private Integer excellent;

	@ApiModelProperty("合格岗位数")
	private Integer qualified;

	@ApiModelProperty("优秀岗位匹配总数")
	private Integer excellentMatchSum;

	public MultiRoleRankingBo(String userId, String name) {
		this.userId = userId;
		this.name = name;
		this.excellent = 0;
		this.qualified = 0;
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
