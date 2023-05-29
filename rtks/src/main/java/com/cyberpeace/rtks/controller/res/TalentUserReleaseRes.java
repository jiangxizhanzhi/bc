package com.cyberpeace.rtks.controller.res;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TalentUserReleaseRes implements Serializable {

	@ApiModelProperty("用户")
	private String userId;

	@ApiModelProperty("用户名")
	private String name;

	@ApiModelProperty("姓名")
	private String userName;

	@ApiModelProperty("组织")
	private String organizationData;

	@ApiModelProperty("头像地址")
	private String avatarUrl;

	@ApiModelProperty("角色列表")
	private List<JSONObject> roles = Lists.newArrayList();

	@ApiModelProperty("角色")
	private String roleStr;

	@ApiModelProperty("岗位信息")
	private List<TalentUserReleaseModelRes> roleInfos;


}