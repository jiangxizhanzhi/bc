package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
public class UpdateAttackMessageReq extends AttackMessageReq{

    @ApiModelProperty("资源id")
    private String resourceId;

}
