package com.cyberpeace.cr.crbase.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoomDeviceApplyListReq extends PageReqBase {
    @ApiModelProperty(value = "设备ID", required = true)
    private String deviceId;
}
