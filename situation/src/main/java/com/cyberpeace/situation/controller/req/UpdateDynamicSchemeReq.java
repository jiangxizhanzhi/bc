package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateDynamicSchemeReq extends DynamicSchemeReq implements Serializable {

    @ApiModelProperty("资源id")
    private String resourceId;

}
