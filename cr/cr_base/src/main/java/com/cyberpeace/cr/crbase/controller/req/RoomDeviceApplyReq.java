package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoomDeviceApplyReq implements Serializable {
    @ApiModelProperty(value = "训练实例ID", required = true)
    private String roomId;

    @ApiModelProperty(value = "实装设备ID", required = true)
    private String deviceId;
}
