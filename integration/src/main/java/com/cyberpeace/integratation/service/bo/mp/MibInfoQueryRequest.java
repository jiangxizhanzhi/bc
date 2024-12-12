package com.cyberpeace.integratation.service.bo.mp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MibInfoQueryRequest {

	@ApiModelProperty(value = "场景id")
	@NotNull
	@NotEmpty(message = "sceneId can not be empty")
	private String scene_id;

	@ApiModelProperty(value = "节点id")
	@NotNull
	@NotEmpty(message = "elementId can not be empty")
	private String element_id;

	@ApiModelProperty(value = "oid")
	@NotNull
	@NotEmpty(message = "oid can not be empty")
	private String oid;

	// snmp版本
	@ApiModelProperty(value = "snmp版本 V1:1,V2C:2,V3:3")
	@NotNull
	private Integer version;

	// 团体字，一般为public
	@ApiModelProperty(value = "团体字")
	private String communityName;

	/**
	 * snmp版本为v3时，会出现以下三个参数
	 *  用户名
	 */
	@ApiModelProperty(value = "v3用户名")
	private String username;

	@ApiModelProperty(value = "v3认证密码")
	private String auth_key;

	@ApiModelProperty(value = "v3加密密码")
	private String priv_key;
}
