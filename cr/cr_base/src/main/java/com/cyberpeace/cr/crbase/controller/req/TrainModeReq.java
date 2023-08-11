package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainModeReq {

    @ApiModelProperty("resource_id列表")
    private String id;
    @ApiModelProperty("类型")
    private Integer type;

}