package com.cyberpeace.situation.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DynamicSchemeNameRes implements Serializable {

    @ApiModelProperty("方案id")
    private String resourceId;

    @ApiModelProperty("方案名称")
    private String name;

}
