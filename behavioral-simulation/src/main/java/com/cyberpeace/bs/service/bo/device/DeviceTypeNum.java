package com.cyberpeace.bs.service.bo.device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备类型和数量要求
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class DeviceTypeNum implements Serializable {
    /**
     * 设备类型
     */
    @ApiModelProperty(value = "实体设备类型")
    private Integer deviceType;

    /**
     * 设备数量
     */
    @ApiModelProperty(value = "实体设备数量")
    private Integer num;
}
