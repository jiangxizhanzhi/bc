package com.cyberpeace.bs.service.bo.device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceTypeNum implements Serializable {
    @ApiModelProperty(value = "实体设备类型")
    private Integer deviceType;

    @ApiModelProperty(value = "实体设备数量")
    private Integer num;
}
