package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexAlgorithm {

    @ApiModelProperty(value = "基础算法")
    private Integer base = 0;

    @ApiModelProperty(value = "专用能力评估算法")
    private Integer ability = 0;

    @ApiModelProperty(value = "装备评估算法")
    private Integer equipment = 0;

    @ApiModelProperty(value = "防御效能评估算")
    private Integer efficiency = 0;

    @ApiModelProperty(value = "总数")
    private Integer sum = 0;

    @ApiModelProperty(value = "使用次数")
    private Integer use = 0;

    public void calSum() {
        this.sum = this.base + this.ability + this.efficiency + this.equipment;
    }
}
