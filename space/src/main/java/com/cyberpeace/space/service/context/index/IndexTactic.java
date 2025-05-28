package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号战法
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexTactic {

    /**
     * 攻击
     *
     * @see Integer
     */
    @ApiModelProperty(value = "攻击战法库")
    private Integer attack = 0;

    /**
     * 防御
     *
     * @see Integer
     */
    @ApiModelProperty(value = "防御战法库")
    private Integer defense = 0;

    /**
     * 使用
     *
     * @see Integer
     */
    @ApiModelProperty(value = "战法库使用次数")
    private Integer use = 0;

    /**
     * 和
     *
     * @see Integer
     */
    @ApiModelProperty(value = "总数:")
    private Integer sum = 0;

    /**
     * 今天添加
     *
     * @see Integer
     */
    @ApiModelProperty(value = "今日新增战法库")
    private Integer addedToday = 0;

    /**
     * 今天更新
     *
     * @see Integer
     */
    @ApiModelProperty(value = "今日修改战法库")
    private Integer updatedToday = 0;

    /**
     * Cal Sum
     *
     * @throws
     */
    public void calSum() {
        this.sum = this.attack + this.defense;
    }
}
