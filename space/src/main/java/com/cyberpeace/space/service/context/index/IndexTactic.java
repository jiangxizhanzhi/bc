package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexTactic {

    @ApiModelProperty(value = "攻击战法库")
    private Integer attack = 0;

    @ApiModelProperty(value = "防御战法库")
    private Integer defense = 0;

    @ApiModelProperty(value = "战法库使用次数")
    private Integer use = 0;

    @ApiModelProperty(value = "总数:")
    private Integer sum = 0;

    public void calSum() {
        this.sum = this.attack + this.defense;
    }
}
