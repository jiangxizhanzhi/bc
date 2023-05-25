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

	@ApiModelProperty("角色")
	private List<JSONObject> roles;

	@ApiModelProperty("岗位名称")
	private String roleStr;

	@ApiModelProperty("综合排名")
	private Integer ranking;
}