package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ElementInfo implements Serializable {

    @ApiModelProperty("节点id")
    private String elementId;

    @ApiModelProperty("节点名称")
    private String name;

    @ApiModelProperty("节点角色")
    private String role;

    @ApiModelProperty("节点描述")
    private String desc;

    @ApiModelProperty("节点设备类型")
    private String deviceType;

    @ApiModelProperty("节点类型")
    private String type;

    @ApiModelProperty("节点动作状态")
    private String actionStatus;

    @ApiModelProperty("节点状态")
    private String status;

    @ApiModelProperty("节点所属区域")
    private List<String> areas;

    @ApiModelProperty("节点坐标")
    private PlaneCoordinate position;

    @ApiModelProperty("节点端口")
    private Map<String, PortInfo> ports;

    @ApiModelProperty("节点层级")
    private Integer hierarchy;

}
