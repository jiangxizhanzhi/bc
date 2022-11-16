package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SituationTaskFilterReq implements Serializable {

    @ApiModelProperty(value = "任务类型")
    private String type;

}
