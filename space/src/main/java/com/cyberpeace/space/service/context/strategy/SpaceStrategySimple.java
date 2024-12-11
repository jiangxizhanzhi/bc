package com.cyberpeace.space.service.context.strategy;

import lombok.Data;

/**
 * 空间策略
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/12/11
 * @since 1.0.0
 */
@Data
public class SpaceStrategySimple {

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
    private String name;

    /**
     * 内容
     *
     * @see String
     */
    private String content;
}
