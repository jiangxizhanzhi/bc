package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 实操认证信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class OperationAuthInfo implements Serializable {

	/**
	 * 实操认证类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权操作类型 1=共享 2=自定义")
	private Integer operationAuthType;

	/**
	 * 实操认证id列表
	 *
	 * @see List<OperationUserInfo>
	 */
	@ApiModelProperty("授权操作用户编号")
	private List<OperationUserInfo> operationAuthIds;

}
