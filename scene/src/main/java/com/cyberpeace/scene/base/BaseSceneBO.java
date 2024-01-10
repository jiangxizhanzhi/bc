package com.cyberpeace.scene.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseSceneBO{

    @ApiModelProperty(value = "服务端id")
    private String eid;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "场景文件")
    private String file;
    @ApiModelProperty(value = "显示上下文")
    private String displayContext;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime updateTime;
}
