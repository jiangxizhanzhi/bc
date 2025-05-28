package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 战法攻击类别修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see TacticAttackCategoryAddReq
 * @since 1.0.0
 */
@Getter
@Setter
public class TacticAttackCategoryModifyReq extends TacticAttackCategoryAddReq {
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;
}
