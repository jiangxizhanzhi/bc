package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝对抗模板响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbTemplateRsp {

	/**
	 * 基础信息
	 */
	@ApiModelProperty(value = "基础信息")
	private BaseInfoRsp baseInfo;

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "模板id")
	private String resourceId;

	/**
	 * 红方角色
	 */
	@ApiModelProperty(required = true, value = "红方角色")
	@NotNull
	private List<JSONObject> redRoles;

	/**
	 * 蓝方角色
	 */
	@ApiModelProperty(required = true, value = "蓝方角色")
	@NotNull
	private List<JSONObject> blueRoles;
}
