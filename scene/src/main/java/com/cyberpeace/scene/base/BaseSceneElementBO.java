package com.cyberpeace.scene.base;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.List;

@Data
public  class BaseSceneElementBO extends BaseSceneNodeBO {
    @ApiModelProperty(value = "网元类型")
    private Integer type;
    @ApiModelProperty(value = "节点文件")
    private String file;
    @ApiModelProperty(value = "系统类型")
    private String systemType;
    @ApiModelProperty(value = "系统架构")
    private String systemArchitecture;
    @ApiModelProperty(value = "系统语言")
    private String systemLanguage;
    @ApiModelProperty(value = "cpu数量")
    private Integer cpu;
    @ApiModelProperty(value = "内存大小")
    private Integer ram;
    @ApiModelProperty(value = "磁盘大小")
    private Integer disk;
    @ApiModelProperty(value = "初始化命令")
    private List<String> initCommands;
    @ApiModelProperty(value = "最大端口数量")
    private Integer maxPortNumber;

    @ApiModelProperty(value = "网元端口")
    private List<BaseSceneElementPortBO> ports;
    @ApiModelProperty(value = "节点文件命令")
    private String nodeCommand;
    @ApiModelProperty(value = "是否辅助节点")
    private Boolean isAuxiliary;


}
