package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SetMissionEvent implements Serializable {

    @ApiModelProperty(value = "任务id", required = true)
    private String missionId;

    @ApiModelProperty(value = "事件id", required = true)
    private Integer eventTypeId;

    @ApiModelProperty(value = "靶标id", required = true)
    private List<String> deviceIds;

}
