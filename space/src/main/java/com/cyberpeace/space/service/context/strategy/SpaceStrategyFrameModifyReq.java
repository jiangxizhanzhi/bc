package com.cyberpeace.space.service.context.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceStrategyFrameModifyReq{
    private String resourceId;

    @ApiModelProperty(value = "")
    private String name;
}
