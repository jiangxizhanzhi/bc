package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SituationSubjectFilterReq implements Serializable {

    @ApiModelProperty(value = "任务id")
    private String resourceId;

}
