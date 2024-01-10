package com.cyberpeace.scene.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class BaseSceneNodeBO{
    @ApiModelProperty(value = "服务端id")
    private String eid;
    @ApiModelProperty(value = "节点id")
    private String id;
    @ApiModelProperty(value = "所属区域id")
    private String area;
    @ApiModelProperty(value = "2d图标地址")
    private String icon2;
    @ApiModelProperty(value = "2d坐标")
    private String position2;
    @ApiModelProperty(value = "3d图标地址")
    private String icon3;
    @ApiModelProperty(value = "3d坐标")
    private String position3;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "显示参数")
    private String displayContext;
}
