package com.cyberpeace.scene.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BaseSceneTopologyBO {

    @ApiModelProperty(value = "场景基础数据")
    private BaseSceneBO scene;

    @ApiModelProperty(value = "当前区域id")
    private String areaId;

    @ApiModelProperty(value = "区域数据")
    private List<BaseSceneAreaBO> areas;

    @ApiModelProperty(value = "网元数据")
    private List<BaseSceneElementBO> elements;


    @ApiModelProperty(value = "连接数据")
    private List<BaseSceneElementPortConnectionBO> connections;

    @ApiModelProperty(value = "碎片数据")
    private List<BaseScenePieceBO> pieces;
}
