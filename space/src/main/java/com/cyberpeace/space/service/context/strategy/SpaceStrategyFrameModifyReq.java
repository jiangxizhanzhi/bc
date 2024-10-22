package com.cyberpeace.space.service.context.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpaceStrategyFrameModifyReq{
    private String resourceId;

    @ApiModelProperty(value = "")
    private String name;
}
