package com.cyberpeace.bs.service.bo.device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备信息
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class DevicePortId implements Serializable {
    /**
     * 实体设备在虚实结合交换机上的端口
     */
    @ApiModelProperty(value = "实体设备在虚实结合交换机上的端口")
    private String port;

    /**
     * 实体设备id
     */
    @ApiModelProperty(value = "实体设备id")
    private String id;
}
