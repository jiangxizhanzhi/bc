package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoomDeviceApplyAllowReq implements Serializable {
    @ApiModelProperty(value = "申请请求ID", required = true)
    private String applyId;
}
