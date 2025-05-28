package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号算法
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexAlgorithm {

    /**
     * 基础
     *
     * @see Integer
     */
    @ApiModelProperty(value = "基础算法")
    private Integer base = 0;

    /**
     * 能力
     *
     * @see Integer
     */
    @ApiModelProperty(value = "专用能力评估算法")
    private Integer ability = 0;

    /**
     * 设备
     *
     * @see Integer
     */
    @ApiModelProperty(value = "装备评估算法")
    private Integer equipment = 0;

    /**
     * 效率
     *
     * @see Integer
     */
    @ApiModelProperty(value = "防御效能评估算")
    private Integer efficiency = 0;

    /**
     * 和
     *
     * @see Integer
     */
    @ApiModelProperty(value = "总数")
    private Integer sum = 0;

    /**
     * 使用
     *
     * @see Integer
     */
    @ApiModelProperty(value = "使用次数")
    private Integer use = 0;

    /**
     * Cal Sum
     *
     * @throws
     */
    public void calSum() {
        this.sum = this.base + this.ability + this.efficiency + this.equipment;
    }
}
