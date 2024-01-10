package com.cyberpeace.scene.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public  class BaseSceneElementPortConnectionBO  {
    @ApiModelProperty(value = "连接id")
    private String id;
    @ApiModelProperty(value = "源端口id")
    private String source;
    @ApiModelProperty(value = "目标端口id")
    private String target;
    @ApiModelProperty(value = "连接名称")
    private String name;
    @ApiModelProperty(value = "是否单向连接")
    private Boolean unidirectional;
    @ApiModelProperty(value = "显示参数")
    private String displayContext;
    @ApiModelProperty(value = "连线类型")
    private String type;
    @ApiModelProperty(value = "路径点")
    private List<Map<String, Object>> vertices;

}
