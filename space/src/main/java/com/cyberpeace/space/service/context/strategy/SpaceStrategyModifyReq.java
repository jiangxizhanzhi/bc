package com.cyberpeace.space.service.context.strategy;

import lombok.Data;

@Data
public class SpaceStrategyModifyReq extends SpaceStrategyAddReq{
    private String resourceId;
}
