package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LineStyle implements Serializable {

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("线宽")
    private Integer width;

    @ApiModelProperty("线型")
    private String lineType;

}
