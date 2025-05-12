package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceTacticBo extends AttackListBo {
    @ApiModelProperty(value = "类型, 1攻击，2防御")
    private Integer type;
}
