package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 工具更新请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class ToolUpdateReq extends ToolAddReq{
    /**
     * 资源id
     */
    @ApiModelProperty("resourceId")
    @NotBlank
    private String resourceId;
}
