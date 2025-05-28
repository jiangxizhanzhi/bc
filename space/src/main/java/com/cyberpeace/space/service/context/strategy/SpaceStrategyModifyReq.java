package com.cyberpeace.space.service.context.strategy;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间策略修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceStrategyAddReq
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceStrategyModifyReq extends SpaceStrategyAddReq{
    private String resourceId;
}
