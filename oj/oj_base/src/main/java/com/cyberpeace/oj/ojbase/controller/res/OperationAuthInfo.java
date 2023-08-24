package com.cyberpeace.oj.ojbase.controller.res;

import com.cyberpeace.common.context.user.OperationUserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OperationAuthInfo implements Serializable {

	@ApiModelProperty("授权操作类型 1=共享 2=自定义")
	private Integer operationAuthType;

	@ApiModelProperty("授权操作用户编号")
	private List<OperationUserInfo> operationAuthIds;

}
