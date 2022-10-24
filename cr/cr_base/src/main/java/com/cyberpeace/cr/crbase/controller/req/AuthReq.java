package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AuthReq {
    @ApiModelProperty(value = "模板id")
    @NotNull
    private List<String> ids;

    @ApiModelProperty(value = "授权类别")
    @NotNull
    private Integer authMode;

    @ApiModelProperty(value = "授权组织id")
    @NotNull
    private List<String> authIds;
}
