package com.cyberpeace.bs.service.bo.simulator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SceneIdName implements Serializable {
    @ApiModelProperty(value = "试验任务id")
    private String sceneId;

    @ApiModelProperty(value = "试验任务名称")
    private String sceneName;
}
