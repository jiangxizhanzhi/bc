package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 战法攻击类别新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class TacticAttackCategoryAddReq {
    /**
     * 名称
     *
     * @see String
     */
    private String name;
    }
