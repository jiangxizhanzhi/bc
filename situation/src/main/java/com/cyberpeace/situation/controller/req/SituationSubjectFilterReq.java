package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SituationSubjectFilterReq implements Serializable {

    @ApiModelProperty(value = "任务id")
    @NotBlank
    private String resourceId;

}
