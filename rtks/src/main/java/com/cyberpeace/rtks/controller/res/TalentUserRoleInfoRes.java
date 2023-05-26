package com.cyberpeace.rtks.controller.res;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TalentUserRoleInfoRes implements Serializable {

	@ApiModelProperty("姓名")
	private String userName;

	@ApiModelProperty("用户id")
	private String userId;

	@ApiModelProperty("组织")
	private String organizationData;

	@ApiModelProperty("匹配度总和")
	private Integer matchSum;

	@ApiModelProperty("匹配度总和")
	private Integer maxMatch;

	@ApiModelProperty("匹配度总和")
	private Integer excellent;

	@ApiModelProperty("合格")
	private Integer qualified;

	@ApiModelProperty("其他")
	private Integer other;

	@ApiModelProperty("优秀和")
	private Integer excellentMatchSum;

	@ApiModelProperty("角色")
	private List<JSONObject> roles;

	@ApiModelProperty("岗位名称")
	private String roleStr;

	@ApiModelProperty("综合排名")
	private Integer ranking;

	public TalentUserRoleInfoRes() {
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