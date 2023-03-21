package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SetMissionEventReq implements Serializable {

    @ApiModelProperty(value = "任务id", required = true)
    private String missionId;

    @ApiModelProperty(value = "事件id", required = true)
    private String eventId;

    @ApiModelProperty(value = "靶标id", required = true)
    private String deviceId;

}
