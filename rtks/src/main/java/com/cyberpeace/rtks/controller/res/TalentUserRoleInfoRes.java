package com.cyberpeace.rtks.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TalentUserRoleInfoRes implements Serializable {

	@ApiModelProperty("岗位名称")
	private String roleName;

	@ApiModelProperty("岗位满足度")
	private String match;

	@ApiModelProperty("岗位id")
	private Integer roleId;

}