package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AuthContext {

    @ApiModelProperty(value = "模板id")
    private List<String> ids;

    @ApiModelProperty(value = "授权类别")
    private Integer authMode;

    @ApiModelProperty(value = "授权组织id")
    private List<String> authIds;
}
