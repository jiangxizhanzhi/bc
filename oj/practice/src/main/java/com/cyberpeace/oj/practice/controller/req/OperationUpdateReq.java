package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OperationUpdateReq extends OperationAddReq {

    @ApiModelProperty("资源编号")
    private String resourceId;

}
