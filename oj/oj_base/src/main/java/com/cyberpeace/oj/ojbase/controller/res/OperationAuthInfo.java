package com.cyberpeace.oj.ojbase.controller.res;

import com.cyberpeace.common.context.user.OperationUserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 实操认证信息
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class OperationAuthInfo implements Serializable {

    /**
     * 授权操作类型 1=共享 2=自定义
     */
    @ApiModelProperty("授权操作类型 1=共享 2=自定义")
	private Integer operationAuthType;

    /**
     * 授权操作用户编号
     */
    @ApiModelProperty("授权操作用户编号")
	private List<OperationUserInfo> operationAuthIds;

}
