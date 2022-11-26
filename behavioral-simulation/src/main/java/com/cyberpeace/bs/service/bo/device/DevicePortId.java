package com.cyberpeace.bs.service.bo.device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DevicePortId implements Serializable {
    @ApiModelProperty(value = "实体设备在虚实结合交换机上的端口")
    private String port;

    @ApiModelProperty(value = "实体设备id")
    private String id;
}
