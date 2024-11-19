package com.cyberpeace.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户岗位新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("岗位信息")
public class UserRoleAddReq implements Serializable {

	/**
	 * 岗位编号
	 *
	 * @see String
	 */
	@ApiModelProperty("岗位编号")
    private String roleId;

}
