package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号流量
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexTraffic {

    /**
     * 协议
     *
     * @see Integer
     */
    @ApiModelProperty(value = "协议流量")
    private Integer protocol = 0;

    /**
     * 行为
     *
     * @see Integer
     */
    @ApiModelProperty(value = "用户行为流量")
    private Integer behavior = 0;

    /**
     * 攻击
     *
     * @see Integer
     */
    @ApiModelProperty(value = "恶意攻击流量:")
    private Integer attack = 0;

    /**
     * 和
     *
     * @see Integer
     */
    @ApiModelProperty(value = "")
    private Integer sum = 0;

}
