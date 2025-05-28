package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间战法Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see AttackListBo
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceTacticBo extends AttackListBo {
    /**
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty(value = "类型, 1攻击，2防御")
    private Integer type;
}
