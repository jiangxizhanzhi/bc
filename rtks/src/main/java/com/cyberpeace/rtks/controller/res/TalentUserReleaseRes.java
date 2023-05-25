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

	@ApiModelProperty("第一岗位名称")
	private String roleNameTop1;

	@ApiModelProperty("第一岗位匹配度")
	private Integer roleTop1Match;

	@ApiModelProperty("第二岗位名称")
	private String roleNameTop2;

	@ApiModelProperty("第二岗位匹配度")
	private Integer roleTop2Match;

	@ApiModelProperty("第三岗位名称")
	private String roleNameTop3;

	@ApiModelProperty("第三岗位匹配度")
	private Integer roleTop3Match;


}