package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class ToolUpdateReq extends ToolAddReq{
    @ApiModelProperty("resourceId")
    @NotBlank
    private String resourceId;
}
