package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import com.cyberpeace.cr.redvsblue.entity.RbAttackPath;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTemplateRsp {

	@ApiModelProperty(value = "基础信息")
	private BaseInfoRsp baseInfo;

	@ApiModelProperty(value = "模板id")
	private String resourceId;

	@ApiModelProperty(required = true, value = "红方角色")
	@NotNull
	private List<JSONObject> redRoles;

	@ApiModelProperty(required = true, value = "蓝方角色")
	@NotNull
	private List<JSONObject> blueRoles;

	@ApiModelProperty(value = "攻击路径", required = true)
	private List<RbAttackPath> attackPaths;
}
