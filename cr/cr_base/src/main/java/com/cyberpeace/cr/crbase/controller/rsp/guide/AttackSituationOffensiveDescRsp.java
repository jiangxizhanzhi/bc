package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 攻击态势进攻描述响应
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class AttackSituationOffensiveDescRsp implements Serializable {

	/**
	 * 代号
	 */
	@ApiModelProperty(value = "编码")
	private Integer code;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "类型(攻击/防御)")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String desc;

	/**
	 * 成功 URL
	 */
	@ApiModelProperty(value = "成功链接")
	private String successUrl;

	/**
	 * 失败 URL
	 */
	@ApiModelProperty(value = "失败链接")
	private String failUrl;

	/**
	 * 类型
	 */
	@ApiModelProperty("目标节点支持类型：compound: 同时支持节点和区域、element: 只支持节点、area: 只支持区域")
	private String type;

	public AttackSituationOffensiveDescRsp(Integer code, String name, String desc, String successUrl, String failUrl,
										   String type) {
		this.code = code;
		this.name = name;
		this.desc = desc;
		this.successUrl = successUrl;
		this.failUrl = failUrl;
		this.type = type;
	}
}
