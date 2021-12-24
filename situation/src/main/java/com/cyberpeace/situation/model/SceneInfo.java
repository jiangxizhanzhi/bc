package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SceneInfo implements Serializable {

    @ApiModelProperty("场景id")
    private String id;

    @ApiModelProperty("场景名称")
    private String name;

    @ApiModelProperty("场景描述")
    private String desc;

    @ApiModelProperty("场景范围")
    private List<PlaneCoordinate> scope;

}
