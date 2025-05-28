package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 防御修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see DefenseAddReq
 * @since 1.0.0
 */
@Getter
@Setter
public class DefenseModifyReq extends DefenseAddReq{
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;
}
