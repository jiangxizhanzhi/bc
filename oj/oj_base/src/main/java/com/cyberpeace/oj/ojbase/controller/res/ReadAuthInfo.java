package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 读取身份验证信息
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class ReadAuthInfo implements Serializable {

	/**
	 * 读取身份验证类型
	 */
	@ApiModelProperty("授权访问类型 1=共享 2=自定义")
	private Integer readAuthType;

	/**
	 * 读认证id列表
	 */
	@ApiModelProperty("授权访问组织编号")
	private List<String> readAuthIds;

}
