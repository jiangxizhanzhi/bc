package com.cyberpeace.scene.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StaticRoute {
    @ApiModelProperty(value = "目的地址(cidr)")
    private String destination;
    @ApiModelProperty(value = "下一跳地址")
    private String nexthop;
    @ApiModelProperty(value = "下一跳接口Id")
    private String nexthopPortId;
}
