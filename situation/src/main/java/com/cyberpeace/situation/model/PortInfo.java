package com.cyberpeace.situation.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PortInfo implements Serializable {

    @ApiModelProperty("端口id")
    private String portId;

    @ApiModelProperty("端口名称")
    private String name;

}
