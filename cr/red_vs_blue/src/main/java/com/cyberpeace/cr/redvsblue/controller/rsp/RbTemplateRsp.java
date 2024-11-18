package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB模板响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class RbTemplateRsp {

	/**
	 * 基地信息
	 *
	 * @see BaseInfoRsp
	 */
	@ApiModelProperty(value = "基础信息")
	private BaseInfoRsp baseInfo;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "模板id")
	private String resourceId;

	/**
	 * RED 角色
	 *
	 * @see List<JSONObject>
	 */
	@ApiModelProperty(required = true, value = "红方角色")
	@NotNull
	private List<JSONObject> redRoles;

	/**
	 * 蓝色角色
	 *
	 * @see List<JSONObject>
	 */
	@ApiModelProperty(required = true, value = "蓝方角色")
	@NotNull
	private List<JSONObject> blueRoles;
}
