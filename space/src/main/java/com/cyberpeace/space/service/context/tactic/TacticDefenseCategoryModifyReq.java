package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 战法防御类别修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see TacticDefenseCategoryAddReq
 * @since 1.0.0
 */
@Getter
@Setter
public class TacticDefenseCategoryModifyReq extends TacticDefenseCategoryAddReq{
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;
}
