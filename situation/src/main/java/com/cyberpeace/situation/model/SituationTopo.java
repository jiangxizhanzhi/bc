package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class SituationTopo implements Serializable {

    @ApiModelProperty("场景信息")
    private SceneInfo scene;

    @ApiModelProperty("区域信息")
    private Map<String, AreaInfo> areas;

    @ApiModelProperty("节点信息")
    private Map<String, ElementInfo> elements;

    @ApiModelProperty("连线信息")
    private List<ConnectionInfo> connections;

}
