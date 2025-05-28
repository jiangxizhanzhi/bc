package com.cyberpeace.space.service.context.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间策略框架修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceStrategyFrameModifyReq{
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty(value = "")
    private String name;
}
