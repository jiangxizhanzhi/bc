package com.cyberpeace.space.service.context.strategy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceStrategyModifyReq extends SpaceStrategyAddReq{
    private String resourceId;
}
