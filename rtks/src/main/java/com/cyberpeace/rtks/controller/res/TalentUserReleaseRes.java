package com.cyberpeace.rtks.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TalentUserReleaseRes implements Serializable {

	@ApiModelProperty("用户")
	private String userId;

	@ApiModelProperty("用户名")
	private String userName;

	@ApiModelProperty("组织")
	private Integer orgName;

	@ApiModelProperty("角色")
	private Integer role;

	@ApiModelProperty("岗位名称")
	private String roleName;

	@ApiModelProperty("综合排名")
	private Integer ranking;

	@ApiModelProperty("优秀")
	private Integer excellent;

	@ApiModelProperty("合格")
	private Integer qualified;


}