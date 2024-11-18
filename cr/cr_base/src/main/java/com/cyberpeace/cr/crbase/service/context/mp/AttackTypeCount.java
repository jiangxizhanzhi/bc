package com.cyberpeace.cr.crbase.service.context.mp;

import lombok.Data;

/**
 * 攻击类型计数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class AttackTypeCount {

    /**
     * 攻击类型
     *
     * @see String
     */
    private String attack_type;

    /**
     * 次
     *
     * @see Integer
     */
    private Integer times;

}
