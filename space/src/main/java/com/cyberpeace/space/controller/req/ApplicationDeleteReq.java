package com.cyberpeace.space.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApplicationDeleteReq implements Serializable {

    @ApiModelProperty("人机对抗id")
    private String rvbId;

    @ApiModelProperty("框架id")
    private String frameId;
}
