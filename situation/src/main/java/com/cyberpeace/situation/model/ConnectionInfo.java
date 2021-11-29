package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConnectionInfo implements Serializable {

    @ApiModelProperty("源节点id")
    private String source;

    @ApiModelProperty("目标节点id")
    private String target;

    @ApiModelProperty("连线样式")
    private LineStyle style;

    @ApiModelProperty("连线动效")
    private String[] effect;

}
