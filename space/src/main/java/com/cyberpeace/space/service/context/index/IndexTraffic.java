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

    @ApiModelProperty(value = "协议流量")
    private Integer protocol = 0;

    @ApiModelProperty(value = "用户行为流量")
    private Integer behavior = 0;

    @ApiModelProperty(value = "恶意攻击流量:")
    private Integer attack = 0;

    @ApiModelProperty(value = "")
    private Integer sum = 0;

}
