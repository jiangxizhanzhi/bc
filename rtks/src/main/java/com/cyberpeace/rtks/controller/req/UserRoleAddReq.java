package com.cyberpeace.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色新增请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("岗位信息")
public class UserRoleAddReq implements Serializable {

    /**
     * 角色id
     */
    @ApiModelProperty("岗位编号")
    private String roleId;

}
