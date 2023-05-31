package com.cyberpeace.cr.teamtrain.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TeamTrainDeviceReq {
    @ApiModelProperty(value = "团队房间id")
    @NotEmpty
    private String roomId;

    @ApiModelProperty(value = "设备id")
    @NotEmpty
    private String deviceId;
}
