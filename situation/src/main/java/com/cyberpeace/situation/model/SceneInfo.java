package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SceneInfo implements Serializable {

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("场景名称")
    private String name;

    @ApiModelProperty("场景描述")
    private String description;

    @ApiModelProperty("场景范围")
    private PlaneCoordinate[] scope;

}
