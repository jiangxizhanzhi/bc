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

	@ApiModelProperty("组织")
	private String organizationData;

	@ApiModelProperty("头像地址")
	private String avatarUrl;

	@ApiModelProperty("角色列表")
	private List<JSONObject> roles = Lists.newArrayList();

	@ApiModelProperty("角色")
	private String roleStr;

	@ApiModelProperty("第一岗位名称")
	private String roleNameTop1;

	@ApiModelProperty("第一岗位匹配度")
	private Integer roleTop1Match=0;

	@ApiModelProperty("第二岗位名称")
	private String roleNameTop2;

	@ApiModelProperty("第二岗位匹配度")
	private Integer roleTop2Match=0;

	@ApiModelProperty("第三岗位名称")
	private String roleNameTop3;

	@ApiModelProperty("第三岗位匹配度")
	private Integer roleTop3Match=0;


}