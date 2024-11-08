package com.cyberpeace.cr.crbase.service.context.mp;

import lombok.Data;

/**
 * 攻击类型计数
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class AttackTypeCount {

    /**
     * 攻击类型
     */
    private String attack_type;

    /**
     * 次
     */
    private Integer times;

}
