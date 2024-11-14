package com.cyberpeace.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("岗位信息")
public class UserRoleAddReq implements Serializable {

	/**
	 * 岗位编号
	 */
    @ApiModelProperty("岗位编号")
    private String roleId;

}
