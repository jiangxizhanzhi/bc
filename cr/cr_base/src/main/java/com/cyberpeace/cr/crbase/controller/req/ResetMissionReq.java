package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @BelongsPackage: com.cyberpeace.cr.crbase.controller.req
 * @Author: hhuang
 * @CreateTime: 2025/03/21  15:48
 * @Description: 重置任务状态请求
 * @Version: 1.0
 */
@Data
public class ResetMissionReq {

    /**
     * 岗位资源id
     */
    @ApiModelProperty(value = "角色id")
    private String roleId;


    /**
     * 任务资源id
     */
    @ApiModelProperty(value = "训练资源id", required = true)
    @NotEmpty(message = "训练资源id不能为空")
    private String resourceId;
}
