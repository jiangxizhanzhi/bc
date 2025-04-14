package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
@ApiModel
public class ToolUpdateInfoReq extends ToolAddReq implements Serializable {
    @ApiModelProperty("resourceId")
    @NotBlank
    private String resourceId;
}
